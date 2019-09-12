package com.learn.service;

import com.learn.common.ServiceResult;
import com.learn.elasticsearch.model.IndexEnity;
import com.learn.elasticsearch.model.SourceEntity;
import com.learn.elasticsearch.query.condition.BaseCondition;
import com.learn.elasticsearch.query.condition.FullTextCondition;
import com.learn.elasticsearch.query.condition.GeoCondition;
import com.learn.elasticsearch.query.condition.TermsLevelCondition;

import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @Date 2019/8/30 14:48
 */
public interface ElasticsearchService {
	ServiceResult createIndex(String indexName);

	ServiceResult createIndex(String indexName, String setting, String mapping);

	ServiceResult putMapping(String indexName, String mapping);

	ServiceResult getMapping(String indexName);

	ServiceResult updateSetting(String indexName, String source);

	ServiceResult getSetting(String indexName);

	ServiceResult reflush(String indexName);

	ServiceResult index(String index, String id, Map<String, Object> source);

	ServiceResult update(String index, String id, Map<String, Object> source);

	ServiceResult delete(String index, String id);

	ServiceResult bulkIndex(String index, List<SourceEntity> source);

	ServiceResult bulkUpdate(String index, List<SourceEntity> source);

	ServiceResult bulkDelete(String index, List<SourceEntity> source);

	ServiceResult fulltextQuery(String index, String queryType, FullTextCondition condition);

	ServiceResult termsQuery(String index, String queryType, TermsLevelCondition condition);

	ServiceResult geoQuery(String index, String queryType, GeoCondition condition);

	ServiceResult boolQuery(String index, Map<String, BaseCondition> conditions);

}
