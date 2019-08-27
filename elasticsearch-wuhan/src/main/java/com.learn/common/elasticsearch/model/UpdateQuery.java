package com.learn.common.elasticsearch.model;

/**
 * @Date 2019/8/22 11:39
 * @Created by dshuyou
 */
public class UpdateQuery {

	private String id;
	private String indexName;
	private Object source;
	private Class clazz;
	private boolean doUpsert;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public boolean DoUpsert() {
		return doUpsert;
	}

	public void setDoUpsert(boolean doUpsert) {
		this.doUpsert = doUpsert;
	}
}