/*
 * 创建日期 2005-8-5
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.dzf.dao.jdbc.framework.util;

import java.io.Serializable;

/**
 * 缓存关键字
 * 
 * @author： 贺扬 Date: 2004-9-9 Time: 16:17:02
 */
public class CacheKey implements Serializable {

	private static final int DEFAULT_MULTIPLYER = 37;

	private static final int DEFAULT_HASHCODE = 17;

	protected Object obj;

	private int multiplier;

	private int hashcode;

	private long checksum;

	private int count;

	private int hits = 0;

	public CacheKey() {
		hashcode = DEFAULT_HASHCODE;
		multiplier = DEFAULT_MULTIPLYER;
		count = 0;

	}

	/**
	 * @param initialNonZeroOddNumber
	 *            非零奇数
	 */
	public CacheKey(int initialNonZeroOddNumber) {
		hashcode = initialNonZeroOddNumber;
		multiplier = DEFAULT_MULTIPLYER;
		count = 0;
	}

	/**
	 * 构造函数
	 * 
	 * @param initialNonZeroOddNumber -
	 *            hashcode值
	 * @param multiplierNonZeroOddNumber -
	 *            乘积因子
	 */
	public CacheKey(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber) {
		hashcode = initialNonZeroOddNumber;
		multiplier = multiplierNonZeroOddNumber;
		count = 0;
	}

	/**
	 * 更新keyObject根据一个整数变量
	 * 
	 * @param x -
	 *            the int value
	 * @return the dbcache key
	 */
	public CacheKey update(int x) {
		count++;
		checksum += x;
		x *= count;

		hashcode = multiplier * hashcode + x;

		return this;
	}

	/**
	 * 更新keyObject根据一个对象
	 * 
	 * @param object -
	 *            the object
	 * @return the cachekey
	 */
	public CacheKey update(Object object) {
		obj = object;
		update(object.hashCode());
		return this;
	}

	public CacheKey update(String sql) {
		obj = sql;
		update(sql.hashCode());
		return this;
	}

	public Object getObject() {
		return obj;
	}

	/**
	 * @param object
	 * @return
	 */
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (!(object instanceof CacheKey))
			return false;

		final CacheKey cacheKey = (CacheKey) object;

		if (hashcode != cacheKey.hashcode)
			return false;
		if (checksum != cacheKey.checksum)
			return false;
		if (count != cacheKey.count)
			return false;
		if (!obj.equals(cacheKey.obj))
			return false;
		return true;

	}

	/**
	 * @return
	 */
	public int hashCode() {
		return hashcode;
	}

	public int getHit() {
		return hits;
	}

	public void increaseRequest() {
		hits++;
	}

	public static CacheKey createKey(String obj) {
		CacheKey key = new CacheKey();
		key.update(obj);
		return key;
	}

	public static CacheKey createKey(Object obj) {
		CacheKey key = new CacheKey();
		key.update(obj);
		return key;
	}

	/**
	 * @return
	 */
	public String toString() {
		return new StringBuffer().append(hashcode).append('-').append(checksum)
				.toString();
	}


}