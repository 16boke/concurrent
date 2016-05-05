package com.sync;

/**
 * 线程同步 子线程循环10次，接着主线程循环100次，接着又回到子线程循环10次，接着再回到主线程循环100次，如此循环50次
 * 
 * @author wyc
 * 
 */
public class SyncTest {
	public static void main(String[] args) {
		final Business business = new Business();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 50; i++) {
					business.sub(i);
				}
			}
		}).start();

		for (int i = 1; i <= 50; i++) {
			business.main(i);
		}

	}
}
/**
 * 互斥方法尽量放在同一个类中，更容易使用相同类的字节码，才可以使用同步锁
 * 要用到共同数据（包括同步锁）的若干方法应该写在同一个类中
 * @author wyc
 *
 */
class Business {
	boolean subShould = true;
	public synchronized void sub(int i) {
		//如果进来的是主进程，则暂停
		if(!subShould){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//如果进来的是子进程，则可以执行
		for (int j = 1; j <= 10; j++) {
			System.out.println("sub thread:" + j + ",loop:" + i);
		}
		//注意：执行完之后一定要记得将暂停的线程唤醒，否则此进程永远会处于暂停状态
		subShould = false;
		this.notify();
	}

	public synchronized void main(int i) {
		//如果进来的是子线程，则暂停
		if(subShould){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//如果进来的是主线程，则可以执行
		for (int j = 1; j <= 100; j++) {
			System.out.println("main thread:" + j + ",loop:" + i);
		}
		//执行完之后一定要记得将此线程唤醒
		subShould = true;
		this.notify();
	}
}
