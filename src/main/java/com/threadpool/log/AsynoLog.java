package com.threadpool.log;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsynoLog {
	private static ExecutorService threadPool = Executors.newCachedThreadPool();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Class targetClass;
	private static String targetClassName;

	public static void getLogger(Class targetClass) {
		setTargetClass(targetClass);
		setTargetClassName(targetClass.getName());
	}

	public static void getLogger(String targetClassName) {
		setTargetClassName(targetClassName);
	}

	/**
	 * debug级别的日志记录
	 * 
	 * @param obj
	 */
	public static void debug(final Object obj) {
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				String content = String.format("[%s %s] %s", sdf.format(new Date()), targetClassName, obj);
				System.out.println(content);
				output(content);
			}
		});
	}

	public static void output(String content) {
		try {
			String filePath = "E:\\asynolog.log";
			File file = new File(filePath);
			BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(
					new FileOutputStream(file, true)), "GBK"));
			bufferWriter.append(content.concat("\r\n"));
			bufferWriter.flush();
			bufferWriter.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		AsynoLog.getLogger(AsynoLog.class);
		System.out.println("开始日志...");
		for (int i = 1; i <= 1000000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 1; j <=10; j++) {
						AsynoLog.debug(Thread.currentThread().getName() + "-log-" + j);
					}
				}
			}).start();
		}
		System.out.println("结束日志，执行下面代码...");
		
	}

	public static Class getTargetClass() {
		return targetClass;
	}

	public static void setTargetClass(Class targetClass) {
		AsynoLog.targetClass = targetClass;
	}

	public static String getTargetClassName() {
		return targetClassName;
	}

	public static void setTargetClassName(String targetClassName) {
		AsynoLog.targetClassName = targetClassName;
	}
}
