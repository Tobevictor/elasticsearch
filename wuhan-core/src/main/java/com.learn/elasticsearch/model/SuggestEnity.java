package com.learn.elasticsearch.model;

/**
 * @author dshuyou
 * @Date 2019/9/24 15:19
 */
public class SuggestEnity {

	private String name;
	private String[] field;
	private String keyword;
	private int size;

	public SuggestEnity(){}

	public SuggestEnity(String[] field, String keyword, int size) {
		this.field = field;
		this.keyword = keyword;
		this.size = size;
	}

	public String[] getField() {
		return field;
	}

	public void setField(String[] field) {
		this.field = field;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
