package com.learn.common.elastic.data;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertNull;


/**
 * @author dshuyou
 * @create 2019/7/9
 * 文档工具类
 */
public class Document {

	private RestHighLevelClient client;
	private Indices indices;
	public Document(RestHighLevelClient client){
		this.client = client;
		indices = new Indices(client);
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(Document.class);
	/**
	 * 插入数据(指定ID)
	 * @param index
	 * @param id
	 * @param jsonString
	 */
	public boolean inert(String index, String id, String jsonString) throws IOException {
		if(!indices.isExists(index)){
			LOGGER.debug("index not found");
			return false;
		}
		IndexRequest request = new IndexRequest(index).id(id);
		request.source(jsonString, XContentType.JSON);
		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		return response.status() == RestStatus.OK;
	}
	/**
	 * 插入数据
	 * @param index
	 * @param jsonString
	 */
	public boolean insert(String index, String jsonString) throws IOException {
		if(!indices.isExists(index)){
			LOGGER.debug("index not found");
			return false;
		}
		IndexRequest request = new IndexRequest(index);
		request.source(jsonString, XContentType.JSON);
		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		return response.status() == RestStatus.OK;
	}

	/**
	 * 插入数据(指定ID)
	 * @param index
	 * @param id
	 * @param source
	 */
	public boolean insert(String index, String id, Map<String,Object> source) throws IOException {
		if(!indices.isExists(index)){
			LOGGER.debug("index not found");
			return false;
		}
		Map<String, Object> jsonMap = new HashMap<>();
		for (Map.Entry<String,Object> entry : source.entrySet()) {
			jsonMap.put(entry.getKey(),entry.getValue());
		}
		IndexRequest request = new IndexRequest(index).id(id).source(jsonMap);
		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		return response.status() == RestStatus.OK;
	}

	/**
	 * 插入数据
	 * @param index
	 * @param source
	 */
	public boolean insert(String index, Map<String,Object> source) throws IOException {
		if(!indices.isExists(index)){
			LOGGER.debug("index not found");
			return false;
		}
		Map<String, Object> jsonMap = new HashMap<>();
		for (Map.Entry<String,Object> entry : source.entrySet()) {
			jsonMap.put(entry.getKey(),entry.getValue());
		}
		IndexRequest request = new IndexRequest(index).source(jsonMap);
		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		return response.status() == RestStatus.OK;
	}

	/**
	 * 异步插入数据(指定ID)
	 * @param index
	 * @param id
	 * @param jsonString
	 */
	public void insertAsync(String index, String id, String jsonString) throws IOException {
		if(!indices.isExists(index)){
			LOGGER.debug("index not found");
			return;
		}
		IndexRequest request = new IndexRequest(index).id(id);
		request.source(jsonString, XContentType.JSON);
		ActionListener listener = new ActionListener<IndexResponse>() {
			@Override
			public void onResponse(IndexResponse indexResponse) {

			}

			@Override
			public void onFailure(Exception e) {

			}
		};
		client.indexAsync(request, RequestOptions.DEFAULT, listener);
	}

	/**
	 * 删除字段
	 * @param index
	 * @param id
	 */
	public boolean delete(String index, String id) throws IOException {
		if(!indices.isExists(index)||!isIdExists(index,id)){
			LOGGER.debug("index or id not found");
			return false;
		}
		DeleteRequest request = new DeleteRequest(index,id);

		DeleteResponse response = client.delete(request,RequestOptions.DEFAULT);
		if(response.status() == RestStatus.OK){
			if (response.getResult() == DocWriteResponse.Result.NOT_FOUND) {
				LOGGER.debug("document not exist");
				return false;
			}
			return true;
		}
		return false;
	}

	public long count(String index) throws IOException {
		CountRequest request = new CountRequest(index);
		try{
			CountResponse response = client.count(request,RequestOptions.DEFAULT);
			return response.getCount();
		}catch (ElasticsearchException e) {
			if (e.status() == RestStatus.NOT_FOUND) {
				LOGGER.error("index or id not found");
			}
		}
		return 0;
	}

	/**
	 * 判断字段id是否存在
	 * @param index
	 * @param id
	 */
	public boolean isIdExists(String index,String id) {
		try {
			GetRequest getRequest = new GetRequest(index,id);
			getRequest.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
			return client.exists(getRequest, RequestOptions.DEFAULT);
		}catch (Exception e){
			return false;
		}
	}

	/**
	 * 获取单个字段
	 * @param index
	 * @param id
	 */
	public String get(String index, String id) throws IOException {
		GetRequest request = new GetRequest(index,id);

		try {
			GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
			if (getResponse.isExists()) {
				return getResponse.getSourceAsString();
			}
		} catch (ElasticsearchException e) {
			if (e.status() == RestStatus.NOT_FOUND) {
				LOGGER.error("index or id not found");
			}
		}
		return null;
	}

	/**
	 * 获取字段值
	 * @param index
	 * @param fields
	 */
	public List<Object> getFieldsValues(String index, String[] fields) throws IOException {
		GetRequest request = new GetRequest(index);
		request.storedFields(fields);
		List<Object> list = new ArrayList<>();
		try {
			GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
			if (getResponse.isExists()) {
				for (int i = 0;i<fields.length; i++){
					list.add(getResponse.getField(fields[i]).getValues());
				}
			}
		} catch (ElasticsearchException e) {
			if (e.status() == RestStatus.NOT_FOUND) {
				LOGGER.error("index or id not found");
			}
		}
		return list;
	}


	/**
	 * 获取多个字段
	 * @param index
	 * @param ids
	 */
	public List<String> multiGet(String index, String[] ids) throws IOException {
		if(!indices.isExists(index)){
			LOGGER.error("index or id not found");
		}
		MultiGetRequest request = new MultiGetRequest();
		for (String id:ids){
			request.add(new MultiGetRequest.Item(index,id));
		}
		MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
		List<String> list = new ArrayList<>();
		for (int i = 0;i<response.getResponses().length;i++){
			MultiGetItemResponse firstItem = response.getResponses()[i];
			assertNull(firstItem.getFailure());
			GetResponse firstGet = firstItem.getResponse();
			if (firstGet.isExists()) {
				String sourceAsString = firstGet.getSourceAsString();
				list.add(sourceAsString);
			}
		}
		return list;
	}

	/**
	 * 更新字段
	 * @param index
	 * @param id
	 * @param jsonMap
	 */
	public boolean update(String index, String id, Map<String,Object> jsonMap) throws IOException {
		if(!isIdExists(index,id)){
			LOGGER.error("index or id not found");
			return false;
		}
		UpdateRequest request = new UpdateRequest(index, id).doc(jsonMap);
		/*request.retryOnConflict(3);
		request.fetchSource(true);*/
		UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
		return response.status() == RestStatus.OK;
	}

	public List<IndexRequest> generateRequests(String indexName, String[] source){
		IndexRequest indexRequest = new IndexRequest(indexName);
		List<IndexRequest> requests = new ArrayList<>();
		for (String s : source){
			indexRequest.source(s, XContentType.JSON);
			requests.add(indexRequest);
		}
		return requests;
	}

	/**
	 * 批量插入数据
	 * @param index
	 * @param list
	 */
	public long batchAscendingId(String index,List<Map<String,Object>> list) throws IOException {
		if(!indices.isExists(index)){
			indices.create(index);
		}
		BulkRequest bulkRequest = new BulkRequest();
		List<IndexRequest> requests = new ArrayList<>();
		for (int i = 0;i<list.size();i++){
			IndexRequest indexRequest = new IndexRequest(index);
			//UpdateRequest request = new UpdateRequest().doc();
			indexRequest.source(list.get(i), XContentType.JSON);
			requests.add(indexRequest);
			if(requests.size()%50000 == 0){
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
		BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
		if(responses.status() == RestStatus.OK){
			return Objects.requireNonNull(requests).size();

		}else {
			LOGGER.error("BulkResponse status is" + responses.status());
		}
		return 0;
	}

	/**
	 * 批量插入数据(随机ID)
	 * @param index
	 * @param source
	 */
	public long batchInsert(String index, String[] source) throws IOException {
		if(!indices.isExists(index)){
			indices.create(index);
		}
		BulkRequest bulkRequest = new BulkRequest();
		List<IndexRequest> requests = generateRequests(index,source);
		if(requests!=null&&requests.size()>0){
			for (IndexRequest indexRequest : requests) {
				bulkRequest.add(indexRequest);
			}
		}
		BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
		if(responses.status() == RestStatus.OK){
			return Objects.requireNonNull(requests).size();

		}else {
			LOGGER.error("BulkResponse status is" + responses.status());
		}
		return 0;
	}

	/**
	 * 批量插入数据(指定ID)
	 * @param index
	 * @param source
	 */
	public long batchAscendingId(String index, String[] source) throws IOException {
		if(!indices.isExists(index)){
			indices.create(index);
		}
		BulkRequest bulkRequest = new BulkRequest();
		List<IndexRequest> requests = new ArrayList<>();
		IndexRequest indexRequest = new IndexRequest(index);
		for (int i = 0;i<source.length;i++){
			indexRequest.source(source[i], XContentType.JSON).id(String.valueOf(i));
			requests.add(indexRequest);
		}
		if(requests!=null&&requests.size()>0){
			for (IndexRequest request : requests) {
				bulkRequest.add(request);
			}
		}
		BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
		if(responses.status() == RestStatus.OK){
			return Objects.requireNonNull(requests).size();

		}else {
			LOGGER.error("BulkResponse status is" + responses.status());
		}
		return 0;
	}
}

