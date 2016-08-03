package com.threadpool.log;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AsynoLog {
	//private static ExecutorService threadPool = Executors.newCachedThreadPool();
	private static int corePoolSize = 2;
	private static int maximumPoolSize = 10;
	private static long keepAliveTime = 30l;
	private static TimeUnit unit = TimeUnit.SECONDS;
	private static ThreadPoolExecutor threadPool = SingletonThreadPoolExecutor.getThreadPoolExecutor(corePoolSize,
			maximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<Runnable>());
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Class targetClass;
	private static String targetClassName;
	private static Object object = new Object();

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
				output(content);
			}
		});
	}

	public static void output(String content) {
		try {
			synchronized (object) {
				String filePath = "D:\\asynolog.log";
				File file = new File(filePath);
				BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(
						new FileOutputStream(file, true)), "GBK"));
				bufferWriter.append(content.concat("\r\n"));
				bufferWriter.flush();
				bufferWriter.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		AsynoLog.getLogger(AsynoLog.class);
		System.out.println("开始日志...");
		for (int i = 1; i <= 1000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 1; j <= 10; j++) {
						AsynoLog.debug(Thread.currentThread().getName() + "-log-" + j);
					}
				}
			}).start();
		}
		monitor();
		System.out.println("结束日志，执行下面代码...");
	}

	public static void monitor() {
		final long begin = System.currentTimeMillis();
		new Thread(new Runnable() {
			boolean flag = true;

			@Override
			public void run() {
				while (flag) {
					StringBuffer strBuff = new StringBuffer();
					strBuff.append("CurrentPoolSize : ").append(threadPool.getPoolSize());
					strBuff.append(" - CorePoolSize : ").append(threadPool.getCorePoolSize());
					strBuff.append(" - MaximumPoolSize : ").append(threadPool.getMaximumPoolSize());
					strBuff.append(" - ActiveTaskCount : ").append(threadPool.getActiveCount());
					strBuff.append(" - CompletedTaskCount : ").append(threadPool.getCompletedTaskCount());
					strBuff.append(" - TotalTaskCount : ").append(threadPool.getTaskCount());
					strBuff.append(" - isTerminated : ").append(threadPool.isTerminated());
					System.out.println(strBuff);
					if (threadPool.getCompletedTaskCount() == threadPool.getTaskCount()) {
						flag = false;
						System.out.println("共耗时：" + (System.currentTimeMillis() - begin) + "ms");
						// threadPool.shutdown();
					}
					try {
						Thread.sleep(500);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
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
