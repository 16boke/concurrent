package com.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheTest {
	private Map<String, Object> cache = new HashMap<String, Object>();
	private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	public static void main(String[] args) {
		final CacheTest cache = new CacheTest();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(cache.getData("f"));
				}
			}).start();
		}
	}

	public Object getData(String key) {
		readWriteLock.readLock().lock();
		try {
			Object value = cache.get(key);
			if (value == null) {
				readWriteLock.readLock().unlock();
				readWriteLock.writeLock().lock();
				try {
					if (value == null) {
						value = "value";// 去查数据库等其它操作
						cache.put(key, value);
					}
				} finally {
					readWriteLock.writeLock().unlock();
				}
				readWriteLock.readLock().lock();
			}
			return value;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			readWriteLock.readLock().unlock();
		}
		return null;
	}

}
