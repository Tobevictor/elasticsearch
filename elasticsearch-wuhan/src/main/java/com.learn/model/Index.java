package com.learn.model;

import com.learn.elasticsearch.Indice;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author dshuyou
 * @Date 2019/8/30 14:59
 */
public class Index {

	private String indexName;
	private int shards;
	private int replicas;
	private int reflushInterval;

	public Index(String indexName, int shards, int replicas,int reflushInterval) {
		this.indexName = indexName;
		this.shards = shards;
		this.replicas = replicas;
		this.reflushInterval = reflushInterval;
	}

	public Settings.Builder createSetting(){
		return Settings.builder()
				.put("index.number_of_shards", shards)
				.put("index.number_of_replicas", replicas)
				.put("index.refresh_interval", TimeValue.timeValueSeconds(reflushInterval));
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
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
