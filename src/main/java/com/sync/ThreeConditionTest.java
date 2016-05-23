package com.sync;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 3个线程顺序执行
 * 
 * @author wyc
 * 
 */
public class ThreeConditionTest {
	public static void main(String[] args) {
		final Business business = new Business();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 10; i++) {
					business.sub1(i);
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 10; i++) {
					business.sub2(i);
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 10; i++) {
					business.sub3(i);
				}
			}
		}).start();
	}

	/**
	 * 互斥方法尽量放在同一个类中，更容易使用相同类的字节码，才可以使用同步锁 要用到共同数据（包括同步锁）的若干方法应该写在同一个类中
	 * 
	 * @author wyc
	 * 
	 */
	static class Business {
		Lock lock = new ReentrantLock();
		Condition condition1 = lock.newCondition();
		Condition condition2 = lock.newCondition();
		Condition condition3 = lock.newCondition();
		int subShould = 1;

		public void sub1(int i) {
			lock.lock();
			try {
				// 如果进来的是主进程，则暂停
				while (subShould != 1) {
					try {
						// this.wait();
						condition1.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// 如果进来的是子进程，则可以执行
				for (int j = 1; j <= 10; j++) {
					System.out.println("sub1 thread:" + j + ",loop:" + i);
				}
				// 注意：执行完之后一定要记得将暂停的线程唤醒，否则此进程永远会处于暂停状态
				subShould = 2;
				// this.notify();
				condition2.signal();
			} finally {
				lock.unlock();
			}
		}

		public void sub2(int i) {
			lock.lock();
			try {
				// 如果进来的是子线程，则暂停
				while (subShould != 2) {
					try {
						// this.wait();
						condition2.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// 如果进来的是主线程，则可以执行
				for (int j = 1; j <= 20; j++) {
					System.out.println("sub2 thread:" + j + ",loop:" + i);
				}
				// 执行完之后一定要记得将此线程唤醒
				subShould = 3;
				// this.notify();
				condition3.signal();
			} finally {
				lock.unlock();
			}
		}

		public void sub3(int i) {
			lock.lock();
			try {
				// 如果进来的是子线程，则暂停
				while (subShould != 3) {
					try {
						// this.wait();
						condition3.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// 如果进来的是主线程，则可以执行
				for (int j = 1; j <= 30; j++) {
					System.out.println("sub3 thread:" + j + ",loop:" + i);
				}
				// 执行完之后一定要记得将此线程唤醒
				subShould = 1;
				// this.notify();
				condition1.signal();
			} finally {
				lock.unlock();
			}
		}
	}
}
