package cn.dazd.iris.core.tchannel.handler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializable;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransport;

import com.uber.tchannel.api.handlers.ThriftRequestHandler;
import com.uber.tchannel.messages.ThriftRequest;
import com.uber.tchannel.messages.ThriftResponse;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;

public class ApiHandlerImpl extends ThriftRequestHandler<TBase<?, ?>, TBase<?, ?>> {

	final Logger l = Logger.getLogger("ApiHandlerImpl");
	// 实例对象
	final Object instance;
	// 实例中的方法
	final Method method;
	// 方法中参数的对象集
	final Class<?>[] methodTypes;
	// service里参数构造体类的路径前缀
	final String structClassNamePrefix;

	/**
	 * 
	 * @param servicePackage
	 *            thrift生成的service类的全路径
	 * @param instance
	 * @param method
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public ApiHandlerImpl(String serviceClassName, Object instance, Method method)
			throws InstantiationException, IllegalAccessException {
		this.instance = instance;
		this.method = method;
		methodTypes = this.method.getParameterTypes();
		structClassNamePrefix = new StringBuffer(serviceClassName).append("$").append(this.method.getName()).append("_")
				.toString();
	}

	@Override
	public ThriftResponse<TBase<?, ?>> handleImpl(ThriftRequest<TBase<?, ?>> request) {
		long stime = Calendar.getInstance().getTimeInMillis();
		Class<?> args_class = null;
		Object args_object = null;
		Object return_object = null;
		Class<?> result_class = null;
		Constructor<?> result_struct = null;
		Object result_object = null;
		try {
			// 获取args类
			args_class = ClassUtils.getClass(getClass().getClassLoader(), structClassNamePrefix + "args");
			// 实例化一个args对象
			args_object = args_class.newInstance();

			// 获取args里获取构造体参数的方法名,反射的时候需要用到
			List<String> args_methods = new ArrayList<String>();
			Pattern p = Pattern.compile("(\\w+)[:]", Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(args_object.toString());
			while (m.find()) {
				char[] mstr = m.group(1).toCharArray();
				if (mstr[0] > 96 && mstr[0] < 123) {
					mstr[0] -= 32;
				}
				args_methods.add("get" + new String(mstr));
			}

			// 填充args对象
			ByteBuf bb = request.getArg3();
			ByteBufInputStream bis = new ByteBufInputStream(bb);
			TTransport transport = new TIOStreamTransport(bis);
			TBinaryProtocol tp = new TBinaryProtocol(transport);
			((TSerializable) args_object).read(tp);
			// 获取args对象里，客户端传送过来的构造体列表
			List<Object> args_subobjs = new ArrayList<Object>();
			for (String key : args_methods) {
				args_subobjs.add(MethodUtils.invokeExactMethod(args_object, key));
			}
			// 获取方法执行后的返回值，参数必须是thrift构造体
			return_object = MethodUtils.invokeExactMethod(instance, method.getName(), args_subobjs.toArray());

			// 获取result类
			result_class = ClassUtils.getClass(getClass().getClassLoader(), structClassNamePrefix + "result");
			result_struct = result_class.getConstructor(this.method.getReturnType());
			// 获取result对象
			result_object = result_struct.newInstance(return_object);

		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		long etime = Calendar.getInstance().getTimeInMillis();
		l.info("=======耗时：" + (etime - stime));
		return new ThriftResponse.Builder<TBase<?, ?>>(request).setTransportHeaders(request.getTransportHeaders())
				.setBody((TBase<?, ?>) result_object).build();
	}

}
