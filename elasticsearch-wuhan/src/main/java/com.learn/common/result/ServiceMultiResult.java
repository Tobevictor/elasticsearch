package com.learn.common.result;

import java.util.List;

/**
 * @Date 2019/8/19 10:03
 * @Created by dshuyou
 */
public class ServiceMultiResult<T> {
	private long total;
	private List<T> result;

	public ServiceMultiResult(long total, List<T> result) {
		this.total = total;
		this.result = result;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public int getResultSize() {
		if (this.result == null) {
			return 0;
		}
		return this.result.size();
	}
}
