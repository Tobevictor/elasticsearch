package com.learn.service.impl;

import com.learn.common.elastic.common.result.ElasticResult;
import com.learn.common.elastic.condition.FullTextCondition;
import com.learn.common.elastic.condition.GeoCondition;
import com.learn.common.elastic.condition.QueryCondition;
import com.learn.common.elastic.condition.TermLevelCondition;
import com.learn.common.elastic.query.search.SearchTypeEnum;
import com.learn.common.elastic.query.search.SimpleSearch;
import com.learn.service.QueryService;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2019/8/14 19:17
 * @Created by dshuyou
 */
@Service
public class QueryServiceImpl implements QueryService {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryServiceImpl.class);

	@Autowired
	private RestHighLevelClient client;

	@Override
	public ElasticResult fulltextQuery(String index, FullTextCondition condition) {
		SimpleSearch simpleSearch = new SimpleSearch(index,client);
		ElasticResult result;
		try {
			List<String> list = simpleSearch.executeQuery(condition);
			result =  ElasticResult.success("simpleQuery success",list);
		} catch (IOException e) {
			LOGGER.error("IOException:" + e);
			result = ElasticResult.failed(RestStatus.CONFLICT.getStatus(),
					"Fulltext Query failed,error:"+e,new ArrayList<>());
		}
		return result;
	}

	@Override
	public ElasticResult fulltextQuery(String type, String index, FullTextCondition condition) {
		SearchTypeEnum searchTypeEnum = SearchTypeEnum.valueOf(type);
		SimpleSearch fulltext = new SimpleSearch(index,client,searchTypeEnum);
		ElasticResult result;
		try {
			List<String> list = fulltext.executeQuery(condition);
			result =  ElasticResult.success(type+"query success",list);
		} catch (IOException e) {
			LOGGER.error("IOException:" + e);
			result = ElasticResult.failed(RestStatus.CONFLICT.getStatus(),
					"Fulltext Query failed,error:"+e,new ArrayList<>());
		}
		return result;
	}

	@Override
	public ElasticResult termLevelQuery(String type, String index, TermLevelCondition condition) {
		SearchTypeEnum searchTypeEnum = SearchTypeEnum.valueOf(type);
		SimpleSearch termLevelSearch = new SimpleSearch(index,client,searchTypeEnum);
		ElasticResult result;
		try {
			List<String> list = termLevelSearch.executeQuery(condition);
			result =  ElasticResult.success(type+"query success",list);
		} catch (IOException e) {
			LOGGER.error("IOException:" + e);
			result = ElasticResult.failed(RestStatus.CONFLICT.getStatus(),
					"TermLevel Query failed,error:"+e,new ArrayList<>());
		}
		return result;
	}


	@Override
	public ElasticResult geoQuery(String type, String index, GeoCondition condition) {
		SearchTypeEnum searchTypeEnum = SearchTypeEnum.valueOf(type);
		SimpleSearch geoSearch = new SimpleSearch(index,client,searchTypeEnum);
		ElasticResult result;
		try {
			List<String> list = geoSearch.executeQuery(condition);
			result =  ElasticResult.success(type+"query success",list);
		} catch (Throwable throwable) {
			LOGGER.error("Throwable throwable:" + throwable);
			result = ElasticResult.failed(RestStatus.CONFLICT.getStatus(),
					"GeoQuery failed,error:"+throwable,new ArrayList<>());
		}
		return result;
	}

	@Override
	public ElasticResult multiQuery(String index, QueryCondition condition) {
		return null;
	}

}
