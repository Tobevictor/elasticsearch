package com.learn.elasticsearch.model;

/**
 * @author dshuyou
 * @Date 2019/8/30 16:05
 */
public class SourceEntity<T> {
	private String id;
	private Long version;
	private T source;

	public SourceEntity(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Object getSource() {
		return source;
	}

	public void setSource(T source) {
		this.source = source;
	}

}
