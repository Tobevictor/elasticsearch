package com.learn.service;

import com.learn.common.elastic.common.result.ElasticResult;
import com.learn.common.elastic.condition.QueryCondition;
import com.learn.common.elastic.query.search.SearchTypeEnum;

import java.util.List;

/**
 * @Date 2019/8/14 19:14
 * @Created by dshuyou
 */
public interface QueryService {

	ElasticResult simpleQuery(String index, QueryCondition condition);

	ElasticResult simpleQuery(String type, String index, QueryCondition condition);

	ElasticResult geoQuery(String type, String index, QueryCondition condition);

	ElasticResult multiQuery(String index, QueryCondition condition);
}
