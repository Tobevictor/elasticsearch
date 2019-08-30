package com.learn.elasticsearch.query.condition;

/**
 * @Date 2019/8/21 10:06
 * @author dshuyou
 */
public abstract class BaseCondition {
	private static final int DEFAULT_FROM = 0;
	private static final int DEFAULT_SIZE = 100;

	protected int from;
	protected int size;

	public BaseCondition() {
		this.from = DEFAULT_FROM;
		this.size = DEFAULT_SIZE;
	}

	public BaseCondition(int from,int size) {
		this.from = from >= 0 ? from : DEFAULT_FROM;
		this.size = size > 0 ? size :DEFAULT_SIZE;
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

