package cn.dazd.iris.core.tchannel.handler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

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

// API接口执行器
public final class ApiHandlerImpl extends ThriftRequestHandler<TBase<?, ?>, TBase<?, ?>> {

	final Logger l = Logger.getLogger("ApiHandlerImpl");
	// 实例对象
	final Object instance;
	// 实例中的方法
	final Method method;
	// service的接口里构造体的getXxx()方法名集合
	final List<String> argsMethods = new ArrayList<String>();
	// args对象
	final Class<?> args_class;
	// result对象
	final Class<?> result_class;

	public ApiHandlerImpl(Object instance, Method method, Class<?> args_class, Class<?> result_class,
			List<String> argsMethods) throws InstantiationException, IllegalAccessException {
		this.instance = instance;
		this.method = method;
		this.args_class = args_class;
		this.result_class = result_class;
		this.argsMethods.addAll(argsMethods);
	}

	@Override
	public ThriftResponse<TBase<?, ?>> handleImpl(ThriftRequest<TBase<?, ?>> request) {
		long stime = Calendar.getInstance().getTimeInMillis();
		Object result_object = null;
		try {
			Constructor<?> result_struct = null;
			Object args_object = args_class.newInstance();
			// 填充args对象
			ByteBuf bb = request.getArg3();
			ByteBufInputStream bis = new ByteBufInputStream(bb);
			TTransport transport = new TIOStreamTransport(bis);
			TBinaryProtocol tp = new TBinaryProtocol(transport);
			((TSerializable) args_object).read(tp);
			// 获取args对象里，客户端传送过来的构造体列表
			List<Object> args_subobjs = new ArrayList<Object>();
			for (String key : argsMethods) {
				args_subobjs.add(MethodUtils.invokeExactMethod(args_object, key));
			}
			// 获取业务执行后的返回值，返回值类型必须是thrift构造体
			Object return_object = MethodUtils.invokeExactMethod(instance, method.getName(), args_subobjs.toArray(),
					method.getParameterTypes());
			// 设置result的构造函数
			result_struct = result_class.getConstructor(this.method.getReturnType());
			// 实例化result对象
			result_object = result_struct.newInstance(return_object);
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
		// 返回实例化后的result对象
		return new ThriftResponse.Builder<TBase<?, ?>>(request).setTransportHeaders(request.getTransportHeaders())
				.setBody((TBase<?, ?>) result_object).build();
	}

}
