package com.learn.common.elastic.query.search;

import com.learn.common.elastic.condition.QueryCondition;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.List;

/**
 * @Date 2019/7/26 16:03
 * @Created by dshuyou
 */
public class GeoSearch extends QueryOptions {

	private SearchTypeEnum searchType;

	public GeoSearch(String indice, RestHighLevelClient client,SearchTypeEnum type){
		super(indice,client);
		searchType = type;
	}

	@Override
	public List<String> executeQuery(QueryCondition queryCondition) throws Throwable {
		return null;
	}
}
