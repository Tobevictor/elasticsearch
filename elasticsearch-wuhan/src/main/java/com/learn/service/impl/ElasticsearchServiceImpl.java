package com.learn.service.impl;

import com.learn.common.ServiceResult;
import com.learn.elasticsearch.Document;
import com.learn.elasticsearch.Indice;
import com.learn.elasticsearch.model.SourceEntity;
import com.learn.elasticsearch.query.*;
import com.learn.elasticsearch.query.condition.*;
import com.learn.elasticsearch.query.query_enum.FulltextEnum;
import com.learn.elasticsearch.query.query_enum.GeoEnum;
import com.learn.elasticsearch.query.query_enum.TermsEnum;
import com.learn.elasticsearch.suggest.Suggestion;
import com.learn.mapper.CommentMapper;
import com.learn.mapper.EarthquakeMapper;
import com.learn.service.ElasticsearchService;
import org.apache.log4j.Logger;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.learn.elasticsearch.Indice.*;

/**
 * @author dshuyou
 * @date 2019/8/30 16:17
 */
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {
	private Logger logger = Logger.getLogger(ElasticsearchServiceImpl.class);
	@Autowired
	private RestHighLevelClient client;

	private Indice indice;
	private Document document;
	@PostConstruct
	public void init(){
		indice = new Indice(client);
		document = new Document(client);
	}

	@Override
	public ServiceResult createIndex(String indexName) {
		ServiceResult result = isIndexExist(indexName);
		if(! result.equals(ServiceResult.notFound())){
			return result;
		}
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
		ServiceResult result = isIndexExist(indexName);
		if(! result.equals(ServiceResult.notFound())){
			return result;
		}
		try {
			if(indice.create(indexName,setting) && indice.putMapping(indexName,mapping)){
				return ServiceResult.success(indexName);
			}
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
		return ServiceResult.internalServerError();
	}

	private ServiceResult isIndexExist(String index) {
		try {
			if(indice.isExists(index)){
				return ServiceResult.isExist();
			}
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
		return ServiceResult.notFound();
	}

	@Override
	public ServiceResult deleteIndex(String indexName) {
		ServiceResult result = isIndexExist(indexName);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
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
		ServiceResult result = isIndexExist(indexName);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
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
		ServiceResult result = isIndexExist(indexName);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
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
		ServiceResult result = isIndexExist(indexName);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		return null;
	}

	@Override
	public ServiceResult getSetting(String indexName) {
		ServiceResult result = isIndexExist(indexName);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
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
		ServiceResult result = isIndexExist(indexName);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		return null;
	}

	@Override
	public ServiceResult getAllIndex() {
		try {
			Set<String> set = indice.getAllIndices();
			String[] indices = set.toArray(new String[0]);
			return ServiceResult.success(indices);
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult index(String index, String id, Map<String, Object> source) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
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
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		if(result != ServiceResult.isExist()){
			return result;
		}
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
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
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
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		try {
			//索引数据前调整副本和刷新时间，完成后再更改回来，以提升索引效率和稳定性
			indice.updateSetting(index,INIT_REPLICAS,String.valueOf(INIT_REFLUSH_INTERVAL));
			long count = document.bulkIndex(index, source);
			indice.updateSetting(index,REPLICAS,String.valueOf(REFRESH_INTERVAL)+"s");
			return ServiceResult.success(count);
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult bulkUpdate(String index, List<SourceEntity> source) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		try {
			long count = document.bulkUpdate(index, source);
			return ServiceResult.success(count);
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult bulkDelete(String index, List<SourceEntity> source) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		try {
			long count = document.bulkDelete(index, source);
			return ServiceResult.success(count);
		} catch (IOException e) {
			return ServiceResult.internalServerError();
		}
	}

	@Override
	public ServiceResult fulltextQuery(String index, String queryType, FullTextCondition condition) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		FulltextQuery query = new FulltextQuery(index,client, FulltextEnum.valueOf(queryType));
		return executeAndReturn(query,condition);
	}

	@Override
	public ServiceResult termsQuery(String index, String queryType, TermsLevelCondition condition) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		TermsQuery query = new TermsQuery(index,client, TermsEnum.valueOf(queryType));
		return executeAndReturn(query,condition);
	}

	@Override
	public ServiceResult geoQuery(String index, String queryType, GeoCondition condition) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		GeoQuery query = new GeoQuery(index,client, GeoEnum.valueOf(queryType));
		return executeAndReturn(query,condition);
	}

	@Override
	public ServiceResult boolQuery(String index, BoolCondition conditions) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		BoolQuery query = new BoolQuery(index,client);
		return executeAndReturn(query,conditions);
	}

	@Override
	public ServiceResult fulltextQuery(String index, String queryType, FullTextCondition condition, int pageNum, int pageSize) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		FulltextQuery query = new FulltextQuery(index,client, FulltextEnum.valueOf(queryType));
		condition.setFrom(from(pageNum,pageSize));
		condition.setSize(pageSize);

		return executeAndReturn(query,condition);
	}

	@Override
	public ServiceResult termsQuery(String index, String queryType, TermsLevelCondition condition, int pageNum, int pageSize) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		TermsQuery query = new TermsQuery(index,client, TermsEnum.valueOf(queryType));
		condition.setFrom(from(pageNum,pageSize));
		condition.setSize(pageSize);

		return executeAndReturn(query,condition);
	}

	@Override
	public ServiceResult geoQuery(String index, String queryType, GeoCondition condition, int pageNum, int pageSize) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		GeoQuery query = new GeoQuery(index,client, GeoEnum.valueOf(queryType));
		condition.setFrom(from(pageNum,pageSize));
		condition.setSize(pageSize);

		return executeAndReturn(query,condition);
	}

	@Override
	public ServiceResult boolQuery(String index, BoolCondition conditions, int pageNum, int pageSize) {
		ServiceResult result = isIndexExist(index);
		if(! result.equals(ServiceResult.isExist())){
			return result;
		}
		BoolQuery query = new BoolQuery(index,client);
		conditions.setFrom(from(pageNum,pageSize));
		conditions.setSize(pageSize);

		return executeAndReturn(query,conditions);
	}

	@Override
	public ServiceResult extendWord(String index, String keyWord, int size, String[] fields) {
		if(!index.isEmpty()){
			ServiceResult result = isIndexExist(index);
			if(! result.equals(ServiceResult.isExist())){
				return result;
			}
		}
		Suggestion suggestion = new Suggestion(client);
		try {
			List<String> list = suggestion.suggest(keyWord,size,fields,index);
			logger.debug("debug --- method extendWord:"+keyWord);
			return ServiceResult.success(list);
		} catch (IOException e) {
			logger.error("error --- method extendWord:"+e.getMessage());
			return ServiceResult.internalServerError();
		}
	}

	private ServiceResult executeAndReturn(BaseQuery query,BaseCondition condition){
		try {
			List<String> list = query.executeQuery(condition);
			if(list == null || list.isEmpty()){
				return ServiceResult.isNull();
			}
			return ServiceResult.success(list);
		} catch (IOException e) {
			logger.error("method: executeAndReturn"+ e.getMessage());
			return ServiceResult.internalServerError();
		}
	}

	private int from(int pageNum, int size) {
		return (pageNum - 1) * size < 0 ? 0 : (pageNum - 1) * size;
	}
}
