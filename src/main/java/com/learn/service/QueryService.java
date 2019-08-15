package com.learn.service;

import com.learn.common.elastic.condition.QueryCondition;

import java.util.List;

/**
 * @Date 2019/8/14 19:14
 * @Created by dshuyou
 */
public interface QueryService {

	List<String> simpleQuery(QueryCondition condition);

	List<String> geoQuery(QueryCondition condition);

	List<String> multiQuery(QueryCondition condition);
}
