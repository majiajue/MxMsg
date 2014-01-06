package com.mx.client.cache;

import java.util.HashMap;

/**
 * 就是一个普通的hashmap，区别是在于这个hashmap有大小限制，默认限制是25，如果插入的条目的数量大于了限制，
 * 则会自动删除其中的任意一个条目来保证条目的数量不超过限制
 * 
 * @author tweety 2012.08.10
 * 
 * @param <K>
 * @param <V>
 */
public class SizedHashMap<K, V> extends HashMap<K, V> {
	private int mCacheHit = 0;
	private int mTotalHit = 0;
	private String mCacheName;

	/**
	 * 我也不知道这个是干什么的
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 缓存条目的数量，默认是25，可以通过setsize方法设置大小
	 */
	private int size = 25;

	public SizedHashMap(String name) {
		mCacheName = name;
	}

	public V put(K key, V value) {
		V p = super.put(key, value);
		checkSize();
		return p;
	};

	@Override
	public V get(Object key) {
		mTotalHit = mTotalHit + 1;
		if (super.get(key) != null) {
			mCacheHit = mCacheHit + 1;
		}
		// LOG.v("cache", mCacheName + ":" + mTotalHit + " " + mCacheHit);
		return super.get(key);
	}

	public int getTotalHit() {
		return mTotalHit;
	}

	public int getTotalCached() {
		return mCacheHit;
	}

	/**
	 * 设置该hashmap的大小
	 * 
	 * @param newsize
	 */
	public void setSize(int newsize) {
		this.size = newsize;
	}

	/**
	 * 获取大小
	 * 
	 * @return
	 */
	public int getsize() {
		return this.size;
	}

	/**
	 * // 这里必须保证 缓存大小的数量不超过限额,如果数量超过限制，则删除其中的任意一个对象
	 */
	private void checkSize() {
		// LOG.v("cache", mCacheName + " check:" + keySet().size() + " " +
		// size);
		if (this.keySet().size() >= size) {
			Object prevkey = this.keySet().iterator().next();
			this.remove(prevkey);
		}
	}
}
