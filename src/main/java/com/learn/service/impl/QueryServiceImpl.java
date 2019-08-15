package com.learn.service.impl;

import com.learn.common.elastic.condition.QueryCondition;
import com.learn.service.QueryService;

import java.util.List;

/**
 * @Date 2019/8/14 19:17
 * @Created by dshuyou
 */
public class QueryServiceImpl implements QueryService {
	@Override
	public List<String> simpleQuery(QueryCondition condition) {
		return null;
	}

	@Override
	public List<String> geoQuery(QueryCondition condition) {
		return null;
	}

	@Override
	public List<String> multiQuery(QueryCondition condition) {
		return null;
	}
}
