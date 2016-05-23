package com.container;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 仿照CopyOnWriteArrayList实现一个“写时复制”的Map
 * 
 * @author wangyc
 * 
 */
public class CopyOnWriteMap<K, V> implements Map<K, V>, Cloneable {
	private volatile Map<K, V> map;
	transient final ReentrantLock lock = new ReentrantLock();

	public CopyOnWriteMap() {
		this.map = new HashMap<K, V>();
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return map.get(key);
	}

	@Override
	public V put(K key, V value) {
		lock.lock();
		try {
			Map<K, V> newMap = new HashMap<K, V>(map);
			V val = newMap.put(key, value);
			map = newMap;
			return val;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public V remove(Object key) {
		lock.lock();
		try {
			Map<K, V> newMap = new HashMap<K, V>(map);
			V val = newMap.remove(key);
			map = newMap;
			return val;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		lock.lock();
		try {
			Map<K, V> newMap = new HashMap<K, V>(map);
			newMap.putAll(m);
			map = newMap;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void clear() {
		lock.lock();
		try {
			Map<K, V> newMap = new HashMap<K, V>(map);
			newMap.clear();
			map = newMap;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}

}
