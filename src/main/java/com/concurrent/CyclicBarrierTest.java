package com.concurrent;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierTest {
	public static void main(String[] args) {
		final int N = 4;
		// 如果说想在所有线程写入操作完之后，进行额外的其他操作可以为CyclicBarrier提供Runnable参数：
		CyclicBarrier cyclicBarrier = new CyclicBarrier(N, new Runnable() {
			@Override
			public void run() {
				System.out.println("当前线程" + Thread.currentThread().getName());
			}
		});
		for (int i = 0; i < N; i++) {
			new Thread(new Write(cyclicBarrier)).start();
		}
		try {
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("CyclicBarrier重用");

		for (int i = 0; i < N; i++) {
			new Thread(new Write(cyclicBarrier)).start();
		}
	}
}

class Write implements Runnable {
	private CyclicBarrier cyclicBarrier;

	public Write(CyclicBarrier cyclicBarrier) {
		this.cyclicBarrier = cyclicBarrier;
	}

	@Override
	public void run() {
		System.out.println("线程" + Thread.currentThread().getName() + "正在写入数据...");
		try {
			Thread.sleep(5000);
			System.out.println("线程" + Thread.currentThread().getName() + "写入数据完毕，等待其他线程写入完毕");
			cyclicBarrier.await(2000, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("所有线程写入完毕，继续处理其他任务...");
	}

}
