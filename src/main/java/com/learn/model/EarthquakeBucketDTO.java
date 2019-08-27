package com.learn.model;

/**
 * @Date 2019/8/20 10:45
 * @Created by dshuyou
 */
public class EarthquakeBucketDTO {

	/**
	 * 聚合bucket的key
	 */
	private String key;

	/**
	 * 聚合结果值
	 */
	private long count;

	public EarthquakeBucketDTO(String key, long count) {
		this.key = key;
		this.count = count;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
}
