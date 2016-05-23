package com.lock;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * java读写锁应用
 * 3个线程进行读，3个线程进行写，读加了读锁，写加了写锁。
 * 读可以3个线程同时来读，所以会有交叉
 * 但是写的时候只能是一个线程写完后才可以允许其它线程进行读或者写操作。
 * @author wangyc
 *
 */
public class ReadWriteLock {
	public static void main(String[] args) {
		final ReadWrite readWrite = new ReadWrite();
		for(int i=1;i<=3;i++){
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					readWrite.get();
				}
			});
			thread.start();
			Thread thread1 = new Thread(new Runnable() {
				@Override
				public void run() {
					readWrite.put();
				}
			});
			thread1.start();
		}
	}
}
class ReadWrite{
	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	public void get(){
		rwl.readLock().lock();
		try{
			for(int i=0;i<100;i++)
				System.out.println(Thread.currentThread().getName()+", read i = "+i);
		}finally{
			rwl.readLock().unlock();
		}
	}
	
	public void put(){
		rwl.writeLock().lock();
		try{
			for(int i=0;i<100;i++)
				System.out.println(Thread.currentThread().getName()+",write i="+i+",value = "+new Random().nextInt());
		}finally{
			rwl.writeLock().unlock();
		}
	}
	
}
