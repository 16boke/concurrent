package com.thread;

public class MultTest {
	private static Mult mult1 = new Mult();

	public static void main(String[] args) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				mult1.add();
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				mult1.del();
			}
		}).start();

	}

}

class Mult {
	private int j = 0;

	public synchronized void add() {
		j++;
	}

	public synchronized void del() {
		j--;
	}

}
