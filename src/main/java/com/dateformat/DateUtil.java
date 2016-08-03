package com.dateformat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// 第一种线程安全的方法：使用synchronized传入sdf不变对象
	/*public static String formatDate(Date date) throws ParseException {
		synchronized (sdf) {
			return sdf.format(date);
		}
	}

	public static Date parse(String strDate) throws ParseException {
		synchronized (sdf) {
			return sdf.parse(strDate);
		}
	}*/

	//第二种线程安全的方法：使用ThreadLocal线程局部变量，每来一个线程就生成一个局部变量，并且在循环中initalValue方法也只会调用一次，保证每个线程拿到的对象都是唯一不变的。
	/*private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			System.out.println("new...");
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	public static Date parse(String dateStr) throws ParseException {
		return threadLocal.get().parse(dateStr);
	}

	public static String format(Date date) {
		return threadLocal.get().format(date);
	}*/
	
	//第三种线程安全的方法：也是采用ThreadLocal，只不过将ThreadLocal当作缓存来使用，生成有限个数和局部变量，在多线程循环的时候会不会再重新生成局部变量，也第二种线程安全是一样的效果
	private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();
	private static DateFormat getDateFormat(){
		DateFormat df = threadLocal.get();
		System.out.println("df = "+df);
		if(df==null){
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			threadLocal.set(df);
		}
		return df;
	}
	
	public static Date parse(String dateStr) throws ParseException {
		return getDateFormat().parse(dateStr);
	}

	public static String format(Date date) {
		return getDateFormat().format(date);
	}
}
