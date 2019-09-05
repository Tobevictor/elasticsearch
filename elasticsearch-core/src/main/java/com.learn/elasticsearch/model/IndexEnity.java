package com.learn.elasticsearch.model;

public class IndexEnity {

	private String indexName;
	private Object objectEnity;
	private int shards;
	private int replicas;
	private int reflushInterval;

	public IndexEnity(){}

	public IndexEnity(String indexName, Object objectEnity, int shards, int replicas, int reflushInterval) {
		this.indexName = indexName;
		this.objectEnity = objectEnity;
		this.shards = shards;
		this.replicas = replicas;
		this.reflushInterval = reflushInterval;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public Object getObjectEnity() {
		return objectEnity;
	}

	public void setObjectEnity(Object objectEnity) {
		this.objectEnity = objectEnity;
	}

	public int getShards() {
		return shards;
	}

	public void setShards(int shards) {
		this.shards = shards;
	}

	public int getReplicas() {
		return replicas;
	}

	public void setReplicas(int replicas) {
		this.replicas = replicas;
	}

	public int getReflushInterval() {
		return reflushInterval;
	}

	public void setReflushInterval(int reflushInterval) {
		this.reflushInterval = reflushInterval;
	}
}