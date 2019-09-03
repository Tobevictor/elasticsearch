package com.learn.elasticsearch.model;

/**
 * @author dshuyou
 * @Date 2019/8/30 16:05
 */
public class IndexEntity {
	private String id;
	private Object object;
	private Long version;
	private String indexName;
	private Object source;

	public IndexEntity(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
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

}
