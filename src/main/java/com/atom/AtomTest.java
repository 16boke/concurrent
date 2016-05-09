package com.atom;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomTest {
	//private static AtomicInteger age = new AtomicInteger();

	// private static int age = 0;

	private static volatile AtomicIntegerFieldUpdater<Atom> atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(Atom.class, "age");
	public static void main(String[] args) {
		final Atom atom = new Atom();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					atomicIntegerFieldUpdater.incrementAndGet(atom);
					System.out.println(Thread.currentThread().getName() + " , " + atomicIntegerFieldUpdater.get(atom));
					// age++;
					// System.out.println(Thread.currentThread().getName()+" , "+age);
				}
			}).start();
		}
	}
}

class Atom {
	public volatile int age;
	public String name;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
