package com.learn.service.impl;

import com.learn.common.elastic.condition.QueryCondition;
import com.learn.common.elastic.query.search.GeoSearch;
import com.learn.common.elastic.query.search.SearchTypeEnum;
import com.learn.common.elastic.query.search.SimpleSearch;
import com.learn.service.QueryService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @Date 2019/8/14 19:17
 * @Created by dshuyou
 */
@Service
public class QueryServiceImpl implements QueryService {

	@Autowired
	private RestHighLevelClient client;

	@Override
	public List<String> simpleQuery(String index,QueryCondition condition) {
		SimpleSearch simpleSearch = new SimpleSearch(index,client);
		List<String> list;
		try {
			list = simpleSearch.executeQuery(condition);
		} catch (IOException e) {
			return null;
		}
		return list;
	}

	@Override
	public List<String> simpleQuery(SearchTypeEnum type, String index, QueryCondition condition) {
		SimpleSearch simpleSearch = new SimpleSearch(index,client,type);
		List<String> list;
		try {
			list = simpleSearch.executeQuery(condition);
		} catch (IOException e) {
			return null;
		}
		return list;
	}


	@Override
	public List<String> geoQuery(SearchTypeEnum type, String index, QueryCondition condition) {
		GeoSearch geoSearch = new GeoSearch(index,client,type);
		List<String> list;
		try {
			list = geoSearch.executeQuery(condition);
		} catch (Throwable throwable) {
			return null;
		}
		return list;
	}

	@Override
	public List<String> multiQuery(String index, QueryCondition condition) {
		return null;
	}

}
