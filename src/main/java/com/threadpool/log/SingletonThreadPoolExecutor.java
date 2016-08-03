package com.threadpool.log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class SingletonThreadPoolExecutor {
	private static ReentrantLock lock = new ReentrantLock();

	private SingletonThreadPoolExecutor() {
	}

	private static ThreadPoolExecutor threadPoolExecutor;

	public static ThreadPoolExecutor getThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		lock.lock();
		try {
			if (threadPoolExecutor == null)
				threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
			return threadPoolExecutor;
		} finally {
			lock.unlock();
		}
	}
}
