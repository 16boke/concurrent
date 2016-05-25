package com.threadpool.log;

public class AsynoLogTest {
	public static void main(String[] args) {
		AsynoLog.getLogger(AsynoLogTest.class);
		System.out.println("开始日志...");
		for (int i = 1; i <= 3; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 10; j++) {
						AsynoLog.debug(Thread.currentThread().getName() + "-log-" + j);
					}
				}
			}).start();
		}
		System.out.println("结束日志，执行下面代码...");
	}
}
