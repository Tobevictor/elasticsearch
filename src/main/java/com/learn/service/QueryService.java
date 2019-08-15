package com.learn.service;

import com.learn.common.elastic.condition.QueryCondition;
import com.learn.common.elastic.query.search.SearchTypeEnum;

import java.util.List;

/**
 * @Date 2019/8/14 19:14
 * @Created by dshuyou
 */
public interface QueryService {

	List<String> simpleQuery(String index, QueryCondition condition);

	List<String> simpleQuery(SearchTypeEnum type, String index, QueryCondition condition);

	List<String> geoQuery(SearchTypeEnum type, String index, QueryCondition condition);

	List<String> multiQuery(String index, QueryCondition condition);
}
