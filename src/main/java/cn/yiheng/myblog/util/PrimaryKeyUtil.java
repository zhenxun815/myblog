package cn.yiheng.myblog.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class PrimaryKeyUtil {
	private static Lock lock = new ReentrantLock();
	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	public static synchronized  String getDateCode()
	{	
			StringBuffer strdate=new StringBuffer();
			lock.lock();
			try{
				strdate.append(new SimpleDateFormat("yyyyMMddHHmmssSSS") .format(new Date()));
			}
			finally {
		           lock.unlock();
		       }
		return strdate.toString();
	}

}
