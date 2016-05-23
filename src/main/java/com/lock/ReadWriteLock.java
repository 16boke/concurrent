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
					while(true){
						readWrite.get();
					}
				}
			});
			thread.start();
			Thread thread1 = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						readWrite.put(new Random().nextInt(1000));
					}
				}
			});
			thread1.start();
		}
	}
}
class ReadWrite{
	private Object data = null;//共享数据，只能有一个线程写，可以有多个线程同时读取
	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	public void get(){
		rwl.readLock().lock();
		try{
			System.out.println(Thread.currentThread().getName()+" be ready to read data!");
			Thread.sleep((long)(Math.random()*1000));
			System.out.println(Thread.currentThread().getName()+" have read data:"+data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			rwl.readLock().unlock();
		}
	}

	public void put(Object data){
		rwl.writeLock().lock();
		try{
			System.out.println(Thread.currentThread().getName()+" be ready to write data!");
			Thread.sleep((long)(Math.random()*1000));
			this.data=data;
			System.out.println(Thread.currentThread().getName()+" have write data:"+data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			rwl.writeLock().unlock();
		}
	}
	
}
