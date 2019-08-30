package com.learn.elasticsearch.test;/*
package com.learn.common.elasticsearch.test;

import com.learn.common.elasticsearch.model.IndexQuery;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.ElasticsearchException;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.*;

import static org.elasticsearch.client.Requests.refreshRequest;
import static org.junit.Assert.assertNull;

*/
/**
 * @Date 2019/8/22 9:49
 * @Created by dshuyou
 *//*

public class ElasticsearchTemplate implements ElasticsearchOperations {
	private final int SHARDS = 3;
	private final int REPLICAS = 2;
	private RestHighLevelClient client;
	private String searchTimeout;

	public ElasticsearchTemplate(RestHighLevelClient client){
		this.client = client;
	}

	@Override
	public RestHighLevelClient getClient() {
		return client;
	}

	public void setSearchTimeout(String searchTimeout) {
		this.searchTimeout = searchTimeout;
	}

	@Override
	public boolean createIndex(String indexName) throws IOException {
		Assert.notNull(indexName, "No index defined for Query");
		CreateIndexRequest request = new CreateIndexRequest(indexName);
		request.settings(Settings.builder()
				.put("index.number_of_shards", SHARDS)
				.put("index.number_of_replicas", REPLICAS)
		);
		request.setTimeout(TimeValue.timeValueMinutes(2));
		request.setMasterTimeout(TimeValue.timeValueMinutes(1));
		request.waitForActiveShards(ActiveShardCount.DEFAULT);
		return client.indices().create(request, RequestOptions.DEFAULT).isAcknowledged();
	}

	private <T> boolean createIndexIfNotCreated(String indexname) throws IOException {
		if(indexExists(indexname)){
			return false;
		}
		createIndex(indexname);
		return true;
	}

	private <T> boolean createIndexIfNotCreated(String indexname,Object settings) throws IOException {
		if(indexExists(indexname)){
			return false;
		}
		createIndexWithSettings(indexname,settings);
		return true;
	}

	@Override
	public boolean createIndexWithSettings(String indexName, Object settings) throws IOException {
		CreateIndexRequest request = new CreateIndexRequest(indexName);
		if (settings instanceof String) {
			request.settings(String.valueOf(settings), Requests.INDEX_CONTENT_TYPE);
		} else if (settings instanceof Map) {
			request.settings((Map) settings);
		} else if (settings instanceof XContentBuilder) {
			request.settings((XContentBuilder) settings);
		}
		return client.indices().create(request, RequestOptions.DEFAULT).isAcknowledged();
	}

	@Override
	public boolean putMapping(String indexName, Object mapping) throws IOException {
		Assert.notNull(indexName, "No index defined for putMapping()");
		PutMappingRequest request = new PutMappingRequest(indexName);

		if (mapping instanceof String) {
			request.source(String.valueOf(mapping), XContentType.JSON);
		} else if (mapping instanceof Map) {
			request.source((Map) mapping);
		} else if (mapping instanceof XContentBuilder) {
			request.source((XContentBuilder) mapping);
		}
		return client.indices().putMapping(request,RequestOptions.DEFAULT).isAcknowledged();
	}

	@Override
	public Map getMapping(String indexName) {
		Assert.notNull(indexName, "No index defined for putMapping()");
		Map mappings = null;
		try {
			GetMappingsRequest request = new GetMappingsRequest().indices(indexName);
			mappings = client.indices().getMapping(request,RequestOptions.DEFAULT).mappings();
		} catch (Exception e) {
			throw new ElasticsearchException(
					"Error while getting mapping for indexName : " + indexName  + " " + e.getMessage());
		}
		return mappings;
	}

	@Override
	public String getSetting(String indexName) throws IOException {
		Assert.notNull(indexName, "No index defined for getSettings");
		GetSettingsRequest request = new GetSettingsRequest();
		return client.indices().getSettings(request,RequestOptions.DEFAULT).getSetting(indexName,null);
	}

	@Override
	public <T> Page<T> queryForPage(SearchQuery query, Class<T> clazz) {
		return null;
	}

	@Override
	public <T> Page<T> queryForPage(SearchQuery query, Class<T> clazz, SearchResultMapper mapper) {
		return null;
	}

	@Override
	public <T> Page<T> queryForPage(CriteriaQuery query, Class<T> clazz) {
		return null;
	}

	@Override
	public <T> Page<T> queryForPage(StringQuery query, Class<T> clazz) {
		return null;
	}

	@Override
	public <T> Page<T> queryForPage(StringQuery query, Class<T> clazz, SearchResultMapper mapper) {
		return null;
	}

	@Override
	public <T> List<T> queryForList(CriteriaQuery query, Class<T> clazz) {
		return null;
	}

	@Override
	public <T> List<T> queryForList(StringQuery query, Class<T> clazz) {
		return null;
	}

	@Override
	public <T> List<T> queryForList(SearchQuery query, Class<T> clazz) {
		return null;
	}

	@Override
	public <T> List<String> queryForIds(SearchQuery query) {
		return null;
	}

	@Override
	public <T> long count(CriteriaQuery query, Class<T> clazz) {
		return 0;
	}

	@Override
	public <T> long count(CriteriaQuery query) {
		return 0;
	}

	@Override
	public <T> long count(SearchQuery query) {
		return 0;
	}

	@Override
	public Map<String, Object> get(String index, String id) throws IOException {
		GetRequest request = new GetRequest(index,id);
		GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
		if (getResponse.isExists()) {
			return getResponse.getSourceAsMap();
		}
		return null;
	}

	@Override
	public <T> LinkedList<T> multiGet(String indexname, String[] ids) throws IOException {
		MultiGetRequest request = new MultiGetRequest();
		for (String id:ids){
			request.add(new MultiGetRequest.Item(indexname,id));
		}
		MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
		LinkedList<T> list = new LinkedList<>();
		for (int i = 0;i<response.getResponses().length;i++){
			MultiGetItemResponse firstItem = response.getResponses()[i];
			assertNull(firstItem.getFailure());
			GetResponse firstGet = firstItem.getResponse();
			if (firstGet.isExists()) {
				Map<String,Object> sourceAsMap = firstGet.getSourceAsMap();
				list.add((T) sourceAsMap);
			}
		}
		return list;
	}

	public Object index(String indexname,Object source) throws IOException {
		IndexRequest request = new IndexRequest(indexname);
		if (source instanceof String) {
			request.source(String.valueOf(source), Requests.INDEX_CONTENT_TYPE);
		} else if (source instanceof Map) {
			request.source((Map) source);
		} else if (source instanceof XContentBuilder) {
			request.source((XContentBuilder) source);
		}
		IndexResponse response = client.index(request,RequestOptions.DEFAULT);
		if(response.status() == RestStatus.OK){
			return source;
		}
		return null;
	}

	@Override
	public Object index(String indexname, String id, Object source) throws IOException {
		if(id.equals(null)){
			return index(indexname,source);
		}
		IndexRequest request = new IndexRequest(indexname).id(id);
		if (source instanceof String) {
			request.source(String.valueOf(source), Requests.INDEX_CONTENT_TYPE);
		} else if (source instanceof Map) {
			request.source((Map) source);
		} else if (source instanceof XContentBuilder) {
			request.source((XContentBuilder) source);
		}
		IndexResponse response = client.index(request,RequestOptions.DEFAULT);
		if(response.status() == RestStatus.OK){
			return source;
		}
		return null;
	}

	@Override
	public long bulkIndex(List<com.learn.common.elasticsearch.model.IndexQuery> queries) throws IOException {
		BulkRequest bulkRequest = new BulkRequest();
		List<IndexRequest> requests = new ArrayList<>();
		for(IndexQuery indexQuery:queries) {
			IndexRequest indexRequest = new IndexRequest(indexQuery.getIndexName());
			indexRequest.source(indexQuery.getSource(), XContentType.JSON);
			requests.add(indexRequest);
			if (requests.size() % 50000 == 0) {
				for (IndexRequest request : requests) {
					bulkRequest.add(request);
				}
				client.bulk(bulkRequest, RequestOptions.DEFAULT);
				bulkRequest = new BulkRequest();
				requests = new ArrayList<>();
			}
		}
		for (IndexRequest request : requests) {
			bulkRequest.add(request);
		}
		client.bulk(bulkRequest, RequestOptions.DEFAULT);
		return Objects.requireNonNull(requests).size();
	}

	@Override
	public long bulkUpdate(List<com.learn.common.elasticsearch.model.UpdateQuery> queries) throws IOException {
		BulkRequest bulkRequest = new BulkRequest();
		List<UpdateRequest> requests = new ArrayList<>();
		for(com.learn.common.elasticsearch.model.UpdateQuery updateQuery:queries) {
			UpdateRequest updateRequest = new UpdateRequest(updateQuery.getIndexName(),updateQuery.getId());
			updateRequest.doc().source(updateQuery.getSource(), XContentType.JSON);
			requests.add(updateRequest);
			if(requests.size()%50000 == 0){
				for (UpdateRequest request : requests) {
					bulkRequest.add(request);
				}
				client.bulk(bulkRequest, RequestOptions.DEFAULT);
				bulkRequest.getRefreshPolicy();
				requests.clear();
			}
		}
		for (UpdateRequest request : requests) {
			bulkRequest.add(request);
		}
		client.bulk(bulkRequest, RequestOptions.DEFAULT);
		return Objects.requireNonNull(requests).size();
	}

	@Override
	public String delete(String indexName, String id) throws IOException {
		DeleteRequest request = new DeleteRequest(indexName,id);

		DeleteResponse response = client.delete(request,RequestOptions.DEFAULT);
		if (response.getResult() == DocWriteResponse.Result.NOT_FOUND) {
			return null;
		}
		return id;
	}

	@Override
	public boolean deleteIndex(String indexName) throws IOException {
		Assert.notNull(indexName, "No index defined for delete operation");
		if (indexExists(indexName)) {
			return client.indices().delete(new DeleteIndexRequest(indexName),RequestOptions.DEFAULT).isAcknowledged();
		}
		return false;
	}

	@Override
	public boolean indexExists(String indexName) throws IOException {
		return client.indices().exists(new GetIndexRequest(indexName),RequestOptions.DEFAULT);
	}

	@Override
	public void refresh(String indexName) throws IOException {
		Assert.notNull(indexName, "No index defined for refresh()");
		client.indices().refresh(refreshRequest(indexName),RequestOptions.DEFAULT);
	}

	@Override
	public <T> Page<T> startScroll(long scrollTimeInMillis, SearchQuery query, Class<T> clazz, SearchResultMapper mapper) {
		return null;
	}

	@Override
	public <T> void clearScroll(String scrollId) {

	}

	@Override
	public <T> Page<T> moreLikeThis(MoreLikeThisQuery query, Class<T> clazz) {
		return null;
	}

	@Override
	public Boolean addAlias(String indexname,String aliasname) throws IOException {
		Assert.notNull(indexname, "No index defined for Alias");
		Assert.notNull(aliasname, "No alias defined");
		IndicesAliasesRequest.AliasActions aliasActions = IndicesAliasesRequest.AliasActions.add().alias(aliasname).index(indexname);

		return client.indices().updateAliases(new IndicesAliasesRequest().addAliasAction(aliasActions),RequestOptions.DEFAULT).isAcknowledged();

	}
}
*/
