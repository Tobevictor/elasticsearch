package com.learn.service;

import com.learn.common.ServiceResult;
import com.learn.elasticsearch.model.IndexEnity;
import com.learn.elasticsearch.query.condition.BaseCondition;
import com.learn.elasticsearch.query.condition.FullTextCondition;
import com.learn.elasticsearch.query.condition.GeoCondition;
import com.learn.elasticsearch.query.condition.TermsLevelCondition;

import java.util.Map;

/**
 * @author dshuyou
 * @Date 2019/8/30 14:48
 */
public interface ElasticsearchService {
	ServiceResult createIndex(String indexName);

	ServiceResult createIndex(String indexName,Object setting);

	ServiceResult createIndex(String indexName, Object setting, Object mapping);

	ServiceResult createIndexFromObj(IndexEnity indexEnity);

	ServiceResult putMapping(String indexName,Object mapping);

	ServiceResult getMapping(String indexName);

	ServiceResult getSetting(String indexName);

	ServiceResult reflush(String indexName);

	ServiceResult index(String index, Object source);

	ServiceResult index(String index, String id, Object source);

	ServiceResult update(String index, String id, Object source);

	ServiceResult delete(String index, String id);

	ServiceResult bulkIndex(String index, Object source);

	ServiceResult bulkUpdate(String index, Object source);

	ServiceResult bulkDelete(String index, Object source);

	ServiceResult fulltextQuery(String index, String queryType, FullTextCondition condition);

	ServiceResult termsQuery(String index, String queryType, TermsLevelCondition condition);

	ServiceResult geoQuery(String index, String queryType, GeoCondition condition);

	ServiceResult boolQuery(String index, Map<String, BaseCondition> conditions);

}
