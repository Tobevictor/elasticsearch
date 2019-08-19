package com.learn.service.impl;

import com.learn.common.elastic.common.result.ElasticResult;
import com.learn.common.elastic.condition.QueryCondition;
import com.learn.common.elastic.query.search.GeoSearch;
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
	public ElasticResult simpleQuery(String index, QueryCondition condition) {
		SimpleSearch simpleSearch = new SimpleSearch(index,client);
		List<String> list = null;
		ElasticResult result;
		try {
			list = simpleSearch.executeQuery(condition);
			result =  ElasticResult.success("simpleQuery success",list);
		} catch (IOException e) {
			LOGGER.error("IOException");
			result = ElasticResult.failed(RestStatus.CONFLICT.getStatus(),"simpleQuery failed,error:"+e,list);
		}
		return result;
	}

	@Override
	public ElasticResult simpleQuery(String type, String index, QueryCondition condition) {
		SearchTypeEnum searchTypeEnum = SearchTypeEnum.valueOf(type);
		SimpleSearch simpleSearch = new SimpleSearch(index,client,searchTypeEnum);
		List<String> list = null;
		ElasticResult result;
		try {
			list = simpleSearch.executeQuery(condition);
			result =  ElasticResult.success(type+"query success",list);
		} catch (IOException e) {
			LOGGER.error("IOException");
			result = ElasticResult.failed(RestStatus.CONFLICT.getStatus(),"simpleQuery failed,error:"+e,list);
		}
		return result;
	}


	@Override
	public ElasticResult geoQuery(String type, String index, QueryCondition condition) {
		SearchTypeEnum searchTypeEnum = SearchTypeEnum.valueOf(type);
		GeoSearch geoSearch = new GeoSearch(index,client,searchTypeEnum);
		List<String> list = null;
		ElasticResult result;
		try {
			list = geoSearch.executeQuery(condition);
			result =  ElasticResult.success(type+"query success",list);
		} catch (Throwable throwable) {
			LOGGER.error("IOException");
			result = ElasticResult.failed(RestStatus.CONFLICT.getStatus(),"geoQuery failed,error:"+throwable,list);

		}
		return result;
	}

	@Override
	public ElasticResult multiQuery(String index, QueryCondition condition) {
		return null;
	}

}
