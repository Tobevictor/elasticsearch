package com.learn.common.elastic.condition;

/**
 * @author dshuyou
 * @create 2019/7/11
 *
 */
public abstract class QueryCondition {
	private static final int DEFAULT_FROM = 0;
	private static final int DEFAULT_SIZE = 100;

	protected int from;
	protected int size;

	public QueryCondition() {
		this.from = DEFAULT_FROM;
		this.size = DEFAULT_SIZE;
	}

	public QueryCondition(int from,int size) {
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
