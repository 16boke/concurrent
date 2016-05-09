package com.threadlocal;

import java.util.Random;

public class ThreadLocalTest {
	private static ThreadLocal<Integer> x = new ThreadLocal<Integer>();
	public static void main(String[] args) {
		for (int i = 0; i < 2; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					int data = new Random().nextInt();
					System.out.println(Thread.currentThread().getName() + " has put data : " + data);;
					x.set(data);
					MyThreadScopeData myData = MyThreadScopeData.getInstance();
					myData.setName("name " + data);
					myData.setAge(data);
					new A().get();
					new B().get();
				}
			}).start();
		}
	}

	static class A {
		public void get() {
			System.out.println("A from " + x.get());
			MyThreadScopeData myData = MyThreadScopeData.getInstance();
			System.out.println("A from " + Thread.currentThread().getName() + ",name " + myData.getName() + ",age "
					+ myData.getAge());
		}
	}

	static class B {
		public void get() {
			System.out.println("B from " + x.get());
			MyThreadScopeData myData = MyThreadScopeData.getInstance();
			System.out.println("B from " + Thread.currentThread().getName() + ",name " + myData.getName() + ",age "
					+ myData.getAge());

		}
	}
}

class MyThreadScopeData {
	private static ThreadLocal<MyThreadScopeData>map = new ThreadLocal<MyThreadScopeData>();
	private MyThreadScopeData() {
	}

	public static MyThreadScopeData getInstance() {
		MyThreadScopeData myData = map.get();
		if (myData == null){
			myData = new MyThreadScopeData();
			map.set(myData);
		}
		return myData;
	}

	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
