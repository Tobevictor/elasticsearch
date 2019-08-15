package com.learn.common.elastic.condition;

/**
 * @author dshuyou
 * @create 2019/7/11
 *
 */
public abstract class QueryCondition {
	protected int from;
	protected int size;
	private static final int DEFAULT_FROM = 0;
	private static final int DEFAULT_SIZE = Integer.MAX_VALUE;

	public QueryCondition() {
		this.from = DEFAULT_FROM;
		this.size = DEFAULT_SIZE;
	}

	public QueryCondition(int from,int size) {
		this.from = from;
		this.size = size;
		if(from < 0){
			throw new IllegalArgumentException("Illegal initial from: " + from);
		}
		if(size <= 0){
			throw new IllegalArgumentException("Illegal initial size: " + size);
		}
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
