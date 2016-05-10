package com.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {
	public static void main(String[] args) {
		final DLock dLock = new DLock();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					dLock.add();
				}
			}).start();
		}
	}
}

class DLock {
	private Lock lock = new ReentrantLock();
	private int i = 0;

	/**
	 * 使用Lock类来控制同步，注意unlock必须放在try catch中，否则可能会引起死锁的问题
	 */
	public void add() {
		try {
			//lock.tryLock(1,TimeUnit.MILLISECONDS)
			if (lock.tryLock()) {
				try {
					System.out.println(Thread.currentThread().getName() + " 得到了锁");
					i++;
					System.out.println(Thread.currentThread().getName() + " , " + i);
				} finally {
					lock.unlock();
					System.out.println(Thread.currentThread().getName() + " 释放了锁");
				}
			} else {
				System.out.println(Thread.currentThread().getName() + " 获取锁失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}