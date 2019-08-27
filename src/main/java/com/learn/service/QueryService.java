package com.learn.service;

import com.learn.common.elastic.common.result.ElasticResult;
import com.learn.common.elastic.condition.FullTextCondition;
import com.learn.common.elastic.condition.GeoCondition;
import com.learn.common.elastic.condition.QueryCondition;
import com.learn.common.elastic.condition.TermLevelCondition;


/**
 * @Date 2019/8/14 19:14
 * @Created by dshuyou
 */
public interface QueryService {

	ElasticResult fulltextQuery(String index, FullTextCondition condition);

	ElasticResult fulltextQuery(String type, String index, FullTextCondition condition);

	ElasticResult termLevelQuery(String type, String index, TermLevelCondition condition);

	ElasticResult geoQuery(String type, String index, GeoCondition condition);

	ElasticResult multiQuery(String index, QueryCondition condition);
}
