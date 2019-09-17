package com.learn.service.impl;

import com.github.pagehelper.PageInfo;
import com.learn.common.ServiceResult;
import com.learn.elasticsearch.Document;
import com.learn.elasticsearch.Indice;
import com.learn.elasticsearch.model.SourceEntity;
import com.learn.elasticsearch.query.BoolQuery;
import com.learn.elasticsearch.query.FulltextQuery;
import com.learn.elasticsearch.query.GeoQuery;
import com.learn.elasticsearch.query.TermsQuery;
import com.learn.elasticsearch.query.condition.*;
import com.learn.elasticsearch.query.query_enum.FulltextEnum;
import com.learn.elasticsearch.query.query_enum.GeoEnum;
import com.learn.elasticsearch.query.query_enum.TermsEnum;
import com.learn.mapper.CommentMapper;
import com.learn.mapper.EarthquakeMapper;
import com.learn.service.ElasticsearchService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.learn.elasticsearch.Indice.*;

/**
 * @author dshuyou
 * @Date 2019/8/30 16:17
 */
@Service
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
			if(indice.create(indexName)){
				return ServiceResult.success(indexName);
			}
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
		return ServiceResult.internalServerError();
	}

	@Override
	public ServiceResult createIndex(String indexName,String setting,String mapping) {
		if(indice.isExists(indexName)){return ServiceResult.isExist(); }
		try {
			if(indice.create(indexName,setting) && indice.putMapping(indexName,mapping)){
				return ServiceResult.success(indexName);
			}
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
		return ServiceResult.internalServerError();
	}

	@Override
	public ServiceResult deleteIndex(String indexName) {
		if(!indice.isExists(indexName)){return ServiceResult.notFound(); }
		try {
			if(indice.deleteIndex(indexName)){
				return ServiceResult.success(indexName);
			}
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
		return ServiceResult.internalServerError();
	}

	@Override
	public ServiceResult putMapping(String indexName, String mapping) {
		if(!indice.isExists(indexName)){return ServiceResult.notFound(); }
		try {
			if(indice.putMapping(indexName,mapping)){
				return ServiceResult.success(indexName);
			}
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
		return ServiceResult.internalServerError();
	}

	@Override
	public ServiceResult getMapping(String indexName) {
		if(!indice.isExists(indexName)){return ServiceResult.notFound(); }
		Map<String,Object> mapping;
		try {
			mapping = indice.getMapping(indexName);
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
		return ServiceResult.success(mapping);
	}

	@Override
	public ServiceResult updateSetting(String indexName, String setting) {
		if(!indice.isExists(indexName)){return ServiceResult.notFound(); }
		return null;
	}

	@Override
	public ServiceResult getSetting(String indexName) {
		if(!indice.isExists(indexName)){return ServiceResult.notFound(); }
		String setting;
		try {
			setting = indice.getSetting(indexName);
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
		return ServiceResult.success(setting);
	}

	@Override
	public ServiceResult reflush(String indexName) {
		if(!indice.isExists(indexName)){return ServiceResult.notFound(); }
		return null;
	}

	@Override
	public ServiceResult index(String index, String id, Map<String, Object> source) {
		if(!indice.isExists(index)){return ServiceResult.notFound(); }
		try {
			if(document.index(index,id,source)){
				return ServiceResult.success(id);
			}
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
		return ServiceResult.internalServerError();
	}

	@Override
	public ServiceResult update(String index, String id, Map<String, Object> source) {
		if(!indice.isExists(index)){return ServiceResult.notFound(); }
		try {
			if(document.update(index,id,source)){
				return ServiceResult.success(id);
			}
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
		return ServiceResult.internalServerError();
	}

	@Override
	public ServiceResult delete(String index, String id) {
		if(!indice.isExists(index)){return ServiceResult.notFound(); }
		try {
			if(document.delete(index,id)){
				return ServiceResult.success(id);
			}
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
		return ServiceResult.internalServerError();
	}

	@Override
	public ServiceResult bulkIndex(String index, List<SourceEntity> source) {
		if(!indice.isExists(index)){return ServiceResult.notFound(); }
		try {
			//索引数据前调整副本和刷新时间，完成后再更改回来，以提升索引效率和稳定性
			indice.updateSetting(index,INIT_REPLICAS,INIT_REFLUSH_INTERVAL);
			long result = document.bulkIndex(index, source);
			indice.updateSetting(index,REPLICAS,REFLUSH_INTERVAL);
			return ServiceResult.success(result);
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult bulkUpdate(String index, List<SourceEntity> source) {
		if(!indice.isExists(index)){return ServiceResult.notFound(); }
		try {
			long result = document.bulkUpdate(index, source);
			return ServiceResult.success(result);
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult bulkDelete(String index, List<SourceEntity> source) {
		if(!indice.isExists(index)){return ServiceResult.notFound(); }
		try {
			long result = document.bulkDelete(index, source);
			return ServiceResult.success(result);
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult fulltextQuery(String index, String queryType, FullTextCondition condition) {
		if(!indice.isExists(index)){return ServiceResult.notFound(); }

		FulltextQuery query = new FulltextQuery(index,client, FulltextEnum.valueOf(queryType));
		try {
			List<String> list = query.executeQuery(condition);
			if(list == null || list.size() == 0){
				return ServiceResult.isNull();
			}
			return ServiceResult.success(list);
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult termsQuery(String index, String queryType, TermsLevelCondition condition) {
		if(!indice.isExists(index)){return ServiceResult.notFound(); }
		TermsQuery query = new TermsQuery(index,client, TermsEnum.valueOf(queryType));
		try {
			List<String> list = query.executeQuery(condition);
			if(list == null || list.size() == 0){
				return ServiceResult.isNull();
			}
			return ServiceResult.success(list);
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult geoQuery(String index, String queryType, GeoCondition condition) {
		if(!indice.isExists(index)){return ServiceResult.notFound(); }
		GeoQuery query = new GeoQuery(index,client, GeoEnum.valueOf(queryType));
		try {
			List<String> list = query.executeQuery(condition);
			if(list == null || list.size() == 0){
				return ServiceResult.isNull();
			}
			return ServiceResult.success(list);
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult boolQuery(String index, BoolCondition conditions) {
		if(indice.isExists(index)){return ServiceResult.isExist(); }
		BoolQuery query = new BoolQuery(index,client);
		try {
			List<String> list = query.executeQuery(conditions);
			if(list == null || list.size() == 0){
				return ServiceResult.isNull();
			}
			return ServiceResult.success(list);
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult fulltextQuery(String index, String queryType, FullTextCondition condition, PageInfo pageInfo) {
		if(!indice.isExists(index)){return ServiceResult.notFound(); }

		FulltextQuery query = new FulltextQuery(index,client, FulltextEnum.valueOf(queryType));
		try {
			int pageNum = pageInfo.getPageNum();
			int pagesize = pageInfo.getSize();
			condition.setFrom(pageNum * pagesize);
			condition.setSize(pagesize * pagesize + pagesize);

			List<String> list = query.executeQuery(condition);
			if(list == null || list.size() == 0){
				return ServiceResult.isNull();
			}
			return ServiceResult.success(list);
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult termsQuery(String index, String queryType, TermsLevelCondition condition, PageInfo pageInfo) {
		if(!indice.isExists(index)){return ServiceResult.notFound(); }
		TermsQuery query = new TermsQuery(index,client, TermsEnum.valueOf(queryType));
		try {
			int pageNum = pageInfo.getPageNum();
			int pagesize = pageInfo.getSize();
			condition.setFrom(pageNum * pagesize);
			condition.setSize(pagesize * pagesize + pagesize);

			List<String> list = query.executeQuery(condition);
			if(list == null || list.size() == 0){
				return ServiceResult.isNull();
			}
			return ServiceResult.success(list);
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult geoQuery(String index, String queryType, GeoCondition condition, PageInfo pageInfo) {
		if(!indice.isExists(index)){return ServiceResult.notFound(); }
		GeoQuery query = new GeoQuery(index,client, GeoEnum.valueOf(queryType));
		try {
			int pageNum = pageInfo.getPageNum();
			int pagesize = pageInfo.getSize();
			condition.setFrom(pageNum * pagesize);
			condition.setSize(pagesize * pagesize + pagesize);

			List<String> list = query.executeQuery(condition);
			if(list == null || list.size() == 0){
				return ServiceResult.isNull();
			}
			return ServiceResult.success(list);
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult boolQuery(String index, BoolCondition conditions, PageInfo pageInfo) {
		if(indice.isExists(index)){return ServiceResult.isExist(); }
		BoolQuery query = new BoolQuery(index,client);
		try {
			int pageNum = pageInfo.getPageNum();
			int pagesize = pageInfo.getSize();
			conditions.setFrom(pageNum * pagesize);
			conditions.setSize(pagesize * pagesize + pagesize);

			List<String> list = query.executeQuery(conditions);
			if(list == null || list.size() == 0){
				return ServiceResult.isNull();
			}
			return ServiceResult.success(list);
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
	}
}
