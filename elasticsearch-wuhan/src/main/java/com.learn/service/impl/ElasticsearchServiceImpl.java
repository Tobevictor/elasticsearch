package com.learn.service.impl;

import com.learn.common.ServiceResult;
import com.learn.elasticsearch.Document;
import com.learn.elasticsearch.Indice;
import com.learn.elasticsearch.query.condition.BaseCondition;
import com.learn.elasticsearch.query.condition.FullTextCondition;
import com.learn.elasticsearch.query.condition.GeoCondition;
import com.learn.elasticsearch.query.condition.TermsLevelCondition;
import com.learn.mapper.CommentMapper;
import com.learn.mapper.EarthquakeMapper;
import com.learn.service.ElasticsearchService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

/**
 * @author dshuyou
 * @Date 2019/8/30 16:17
 */
public class ElasticsearchServiceImpl implements ElasticsearchService {

	@Autowired
	private RestHighLevelClient client;
	@Autowired
	private EarthquakeMapper earthquakeMapper;
	@Autowired
	private CommentMapper commentMapper;
	private Indice indice;
	private Document document;
	private boolean result = true;
	@PostConstruct
	public void init(){
		indice = new Indice(client);
		document = new Document(client);
	}
	@Override
	public ServiceResult createIndex(String indexName) {
		try {
			indice.create(indexName);
		} catch (IOException e) {
			return ServiceResult.failedOf(indexName);
		}
		return ServiceResult.successOf(indexName);
	}

	@Override
	public ServiceResult createIndex(String indexName, Object mapping) {
		return null;
	}

	@Override
	public ServiceResult createIndex(String indexName, Object setting, Object mapping) {
		return null;
	}

	@Override
	public ServiceResult getMapping(String indexName) {
		return null;
	}

	@Override
	public ServiceResult getSetting(String indexName) {
		return null;
	}

	@Override
	public ServiceResult reflush(String indexName) {
		return null;
	}

	@Override
	public ServiceResult index(String index, Object source) {
		return null;
	}

	@Override
	public ServiceResult index(String index, String id, Object source) {
		return null;
	}

	@Override
	public ServiceResult update(String index, String id, Object source) {
		return null;
	}

	@Override
	public ServiceResult delete(String index, String id) {
		return null;
	}

	@Override
	public ServiceResult bulkIndex(String index, Object source) {
		return null;
	}

	@Override
	public ServiceResult bulkUpdate(String index, Object source) {
		return null;
	}

	@Override
	public ServiceResult bulkDelete(String index, Object source) {
		return null;
	}

	@Override
	public ServiceResult fulltextQuery(String index, String queryType, FullTextCondition condition) {
		return null;
	}

	@Override
	public ServiceResult termsQuery(String index, String queryType, TermsLevelCondition condition) {
		return null;
	}

	@Override
	public ServiceResult geoQuery(String index, String queryType, GeoCondition condition) {
		return null;
	}

	@Override
	public ServiceResult boolQuery(String index, Map<String, BaseCondition> conditions) {
		return null;
	}
}
