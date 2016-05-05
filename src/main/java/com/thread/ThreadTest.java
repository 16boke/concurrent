package com.thread;

public class ThreadTest {
	public static void main(String[] args) {
		Thread thread = new Thread(){
			public void run() {
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(System.currentTimeMillis()+", "+Thread.currentThread().getName()+", "+this.getName());
				}
			}
		};
		thread.start();
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(System.currentTimeMillis()+", "+Thread.currentThread().getName());
				}
			}
		});
		thread2.start();
		
		//运行结果为thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("runnable : "+System.currentTimeMillis()+", "+Thread.currentThread().getName());
				}
			}
		}){
			public void run() {
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("thread "+System.currentTimeMillis()+", "+Thread.currentThread().getName()+", "+this.getName());
				}
			};
		}.start();
	}
}
