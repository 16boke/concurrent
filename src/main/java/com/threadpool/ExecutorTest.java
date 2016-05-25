package com.threadpool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class ExecutorTest implements Executor {
	public static void main(String[] args) {
		ExecutorTest executorTest = new ExecutorTest();
		executorTest.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + "...");
				}
			}
		});
	}

	@Override
	public void execute(Runnable r) {
		r.run();
	}
}
