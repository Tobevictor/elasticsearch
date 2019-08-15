package com.learn.common.elastic.query.builder;

import com.learn.common.elastic.condition.QueryCondition;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * @author dshuyou
 * @create 2019/7/10
 *
 */
public abstract class BaseSearchBuilder {

	protected int from;
	protected int size;

	protected SearchSourceBuilder sourceBuilder;

	protected BaseSearchBuilder(QueryCondition condition) {
		this.size = condition.getSize();
		this.from = condition.getFrom();

		this.sourceBuilder = new SearchSourceBuilder();
		this.sourceBuilder.from(from);
		this.sourceBuilder.size(size);
	}

	protected BaseSearchBuilder(){
		this.sourceBuilder = new SearchSourceBuilder();
	}

	protected abstract SearchSourceBuilder builder() throws IOException;

}
