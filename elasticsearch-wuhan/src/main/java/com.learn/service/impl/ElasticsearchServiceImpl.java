package com.learn.service.impl;

import com.learn.common.ServiceResult;
import com.learn.elasticsearch.Document;
import com.learn.elasticsearch.Indice;
import com.learn.elasticsearch.model.IndexEnity;
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
import java.util.Objects;

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
	@PostConstruct
	public void init(){
		indice = new Indice(client);
		document = new Document(client);
	}
	@Override
	public ServiceResult createIndex(String indexName) {
		if(indice.isExists(indexName)){return ServiceResult.isExist(); }
		try {
			indice.create(indexName);
		} catch (IOException e) {
			return ServiceResult.ioException();
		}
		return ServiceResult.success(indexName);
	}

	@Override
	public ServiceResult createIndex(String indexName, Object setting) {
		if(indice.isExists(indexName)){return ServiceResult.isExist(); }
		try {
			indice.create(indexName,setting);
		} catch (IOException e) {
			return ServiceResult.ioException();
		}
		return ServiceResult.success(indexName);
	}

	@Override
	public ServiceResult createIndex(String indexName, Object setting, Object mapping) {
		if(indice.isExists(indexName)){return ServiceResult.isExist(); }
		try {
			indice.create(indexName,setting,mapping);
		} catch (IOException e) {
			return ServiceResult.ioException();
		}
		return ServiceResult.success(indexName);
	}

	@Override
	public ServiceResult createIndexFromObj(IndexEnity indexEnity) {
		if(indice.isExists(indexEnity.getIndexName())){return ServiceResult.isExist(); }
		Object mapping = indice.createMapping(indexEnity.getObjectEnity());
		Object setting = indice.createSetting(indexEnity.getShards(),indexEnity.getReplicas(),indexEnity.getReflushInterval());
		if(Objects.isNull(mapping)||Objects.isNull(setting)){
			return ServiceResult.isNull();
		}
		return createIndex(indexEnity.getIndexName(),setting,mapping);
	}

	@Override
	public ServiceResult putMapping(String indexName, Object mapping) {
		if(indice.isExists(indexName)){return ServiceResult.isExist(); }
		try {
			indice.putMapping(indexName,mapping);
		} catch (IOException e) {
			return ServiceResult.ioException();
		}
		return ServiceResult.success(indexName);
	}

	@Override
	public ServiceResult getMapping(String indexName) {
		if(indice.isExists(indexName)){return ServiceResult.isExist(); }
		Map<String,Object> mapping;
		try {
			mapping = indice.getMapping(indexName);
		} catch (IOException e) {
			return ServiceResult.ioException();
		}
		return ServiceResult.success(mapping);
	}

	@Override
	public ServiceResult getSetting(String indexName) {
		if(indice.isExists(indexName)){return ServiceResult.isExist(); }
		String setting;
		try {
			setting = indice.getSetting(indexName);
		} catch (IOException e) {
			return ServiceResult.ioException();
		}
		return ServiceResult.success(setting);
	}

	@Override
	public ServiceResult reflush(String indexName) {
		if(indice.isExists(indexName)){return ServiceResult.isExist(); }
		return null;
	}

	@Override
	public ServiceResult index(String index, Object source) {
		if(indice.isExists(index)){return ServiceResult.isExist(); }
		return null;
	}

	@Override
	public ServiceResult index(String index, String id, Object source) {
		if(indice.isExists(index)){return ServiceResult.isExist(); }
		return null;
	}

	@Override
	public ServiceResult update(String index, String id, Object source) {
		if(indice.isExists(index)){return ServiceResult.isExist(); }
		return null;
	}

	@Override
	public ServiceResult delete(String index, String id) {
		if(indice.isExists(index)){return ServiceResult.isExist(); }
		return null;
	}

	@Override
	public ServiceResult bulkIndex(String index, Object source) {
		if(indice.isExists(index)){return ServiceResult.isExist(); }
		return null;
	}

	@Override
	public ServiceResult bulkUpdate(String index, Object source) {
		if(indice.isExists(index)){return ServiceResult.isExist(); }
		return null;
	}

	@Override
	public ServiceResult bulkDelete(String index, Object source) {
		if(indice.isExists(index)){return ServiceResult.isExist(); }
		return null;
	}

	@Override
	public ServiceResult fulltextQuery(String index, String queryType, FullTextCondition condition) {
		if(indice.isExists(index)){return ServiceResult.isExist(); }
		return null;
	}

	@Override
	public ServiceResult termsQuery(String index, String queryType, TermsLevelCondition condition) {
		if(indice.isExists(index)){return ServiceResult.isExist(); }
		return null;
	}

	@Override
	public ServiceResult geoQuery(String index, String queryType, GeoCondition condition) {
		if(indice.isExists(index)){return ServiceResult.isExist(); }
		return null;
	}

	@Override
	public ServiceResult boolQuery(String index, Map<String, BaseCondition> conditions) {
		if(indice.isExists(index)){return ServiceResult.isExist(); }
		return null;
	}
}
