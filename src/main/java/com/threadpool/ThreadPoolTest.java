package com.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 任务提交给整个线程池，而不是直接交给某个线程，线程池在拿到任务后，它就在内部 找有无空闲的线程，再把任务交给内部某个空闲的线程
 * 
 * @author wyc
 * 
 */
public class ThreadPoolTest {
	public static void main(String[] args) {
		// 创建固定大小的线程池
		ExecutorService threadPool = Executors.newFixedThreadPool(3);
		// 创建缓存线程池
		// ExecutorService threadPool = Executors.newCachedThreadPool();
		// 创建单一线程池（实现线程死掉后重新创建一个新的线程）
		//ExecutorService threadPool = Executors.newSingleThreadExecutor();
		for (int i = 1; i <= 10; i++) {
			final int task = i;
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					for (int j = 1; j <= 10; j++) {
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName() + " , loop of " + j + ", for task is " + task);
					}
				}
			});
		}
		System.out.println("all of 10 tasks have commit.");
		threadPool.shutdown();

		//固定频率定时任务
		/*Executors.newScheduledThreadPool(3).scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				System.out.println("schedule threadpool...");
			}
		}, 5, 2, TimeUnit.SECONDS);*/
	}
}
