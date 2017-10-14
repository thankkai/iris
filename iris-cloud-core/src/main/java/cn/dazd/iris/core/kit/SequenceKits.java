package cn.dazd.iris.core.kit;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * 分布式唯一id生成器
 * 
 * @author Administrator
 *
 */
public class SequenceKits {
	final static Logger l = Logger.getLogger("Kits");
	private static short sequencemin = 0;
	private static short sequencemax = 512;
	private static long oldtimemilies = 0;
	private static ReentrantLock rl = new ReentrantLock(true);
	private static Calendar beginTimestamp = Calendar.getInstance();
	static {
		beginTimestamp.clear();
		beginTimestamp.set(2017, 2, 1);
		TimeZone tz = TimeZone.getTimeZone("GMT");
		beginTimestamp.setTimeZone(tz);
	}

	/**
	 * 返回毫秒数时间差
	 * 
	 * @return
	 */
	public static long getTimeInMillis() {

		return Calendar.getInstance().getTimeInMillis() - beginTimestamp.getTimeInMillis();
	}

	/**
	 * 返回唯一序号中的进程号
	 * 
	 * @param id
	 * @return
	 */
	public static int getProcessid(long id) {
		long y = (id >>> 23) << 23;
		long z = (id >>> 9) << 9;
		return Long.valueOf(((z - y) >> 9)).intValue();
	}

	/**
	 * 获取唯一序号
	 * 
	 * @param processid
	 * @return
	 */
	public static long getSequenceId(int processid) {
		rl.lock();
		long sequenceid = -1;
		try {
			long tm = getTimeInMillis();
			while (oldtimemilies == tm) {
				tm = getTimeInMillis();
				if (sequencemin >= sequencemax) {
					continue;
				}
			}
			sequencemin = (short) ((++sequencemin) % sequencemax);
			oldtimemilies = tm;
			sequenceid = (tm << 23) + (processid << 9) + sequencemin;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			rl.unlock();
		}
		return sequenceid;
	}
}
