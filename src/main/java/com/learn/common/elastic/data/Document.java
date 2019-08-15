package com.learn.common.elastic.data;

import com.learn.common.elastic.common.result.CommonResult;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
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

	/**
	 * 插入数据(指定ID)
	 * @param index
	 * @param jsonString
	 */
	public CommonResult insert(String index, String id, String jsonString) throws IOException {
		if(!indices.isExists(index)){
			indices.create(index,jsonString);
		}
		IndexRequest request = new IndexRequest(index).id(id);
		request.source(jsonString, XContentType.JSON);
		client.index(request, RequestOptions.DEFAULT);
		return CommonResult.success("insert success",1);
	}
	/**
	 * 插入数据
	 * @param index
	 * @param jsonString
	 */
	public CommonResult insert(String index, String jsonString) throws IOException {
		if(!indices.isExists(index)){
			indices.create(index);
		}
		IndexRequest request = new IndexRequest(index);
		request.source(jsonString, XContentType.JSON);
		client.index(request, RequestOptions.DEFAULT);
		return CommonResult.success("insert success",1);
	}

	/**
	 * 插入数据(指定ID)
	 * @param index
	 * @param source
	 */
	public CommonResult insert(String index, String id, Map<String,Object> source) throws IOException {
		if(!indices.isExists(index)){
			indices.create(index);
		}
		Map<String, Object> jsonMap = new HashMap<>();
		for (Map.Entry<String,Object> entry : source.entrySet()) {
			jsonMap.put(entry.getKey(),entry.getValue());
		}
		IndexRequest request = new IndexRequest(index).id(id).source(jsonMap);
		client.index(request,RequestOptions.DEFAULT);
		return CommonResult.success("insert success",1);
	}

	/**
	 * 插入数据
	 * @param index
	 * @param source
	 */
	public CommonResult insert(String index, Map<String,Object> source) throws IOException {
		if(!indices.isExists(index)){
			indices.create(index);
		}
		Map<String, Object> jsonMap = new HashMap<>();
		for (Map.Entry<String,Object> entry : source.entrySet()) {
			jsonMap.put(entry.getKey(),entry.getValue());
		}
		IndexRequest request = new IndexRequest(index).source(jsonMap);
		client.index(request,RequestOptions.DEFAULT);
		return CommonResult.success("insert success",1);
	}

	/**
	 * 异步插入数据(指定ID)
	 * @param index
	 * @param jsonString
	 */
	public CommonResult insertAsync(String index, String id, String jsonString) throws IOException {
		if(!indices.isExists(index)){
			indices.create(index);
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
		return CommonResult.success("insert success",1);
	}

	/**
	 * 删除字段
	 * @param index
	 * @param id
	 */
	public CommonResult delete(String index, String id) throws IOException {
		if(!indices.isExists(index)||!isIdExists(index,id)){
			return CommonResult.failed(RestStatus.NOT_FOUND,"index or id not found",0);
		}
		DeleteRequest request = new DeleteRequest(index,id);

		DeleteResponse response = client.delete(request,RequestOptions.DEFAULT);
		if (response.getResult() == DocWriteResponse.Result.NOT_FOUND) {
			return CommonResult.failed(RestStatus.FAILED_DEPENDENCY,"document not exist",0);
		}
		return CommonResult.success("delete success",1);
	}

	public CommonResult count(String index) throws IOException {
		CountRequest request = new CountRequest(index);
		try{
			CountResponse response = client.count(request,RequestOptions.DEFAULT);
			return CommonResult.success("delete success",response.getCount());
		}catch (ElasticsearchException e) {
			if (e.status() == RestStatus.NOT_FOUND) {
				return CommonResult.failed(RestStatus.NOT_FOUND,"index not found",0);
			}
		}
		return CommonResult.failed(RestStatus.NOT_ACCEPTABLE,"count exception",0);
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
	public CommonResult get(String index, String id) throws IOException {
		GetRequest request = new GetRequest(index,id);

		try {
			GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
			if (getResponse.isExists()) {
				String sourceAsString = getResponse.getSourceAsString();
				System.out.println(sourceAsString);
				return CommonResult.success(sourceAsString);
			}
		} catch (ElasticsearchException e) {
			if (e.status() == RestStatus.NOT_FOUND) {
				return CommonResult.failed(RestStatus.NOT_FOUND,"index not found",null);
			}
		}
		return CommonResult.failed(RestStatus.NOT_ACCEPTABLE,"get exception",null);
	}

	public CommonResult getFieldsValues(String index, String[] fields) throws IOException {
		GetRequest request = new GetRequest(index);
		request.storedFields(fields);

		try {
			GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
			List<Object> list = new ArrayList<>();
			if (getResponse.isExists()) {
				for (int i = 0;i<fields.length; i++){
					list.add(getResponse.getField(fields[i]).getValues());
				}
				System.out.println(list);
				return CommonResult.success("get success",list);
			}
		} catch (ElasticsearchException e) {
			if (e.status() == RestStatus.NOT_FOUND) {
				return CommonResult.failed(RestStatus.NOT_FOUND,"index not found",null);
			}
		}
		return CommonResult.failed(RestStatus.NOT_ACCEPTABLE,"get exception",null);
	}


	/**
	 * 获取多个字段
	 * @param index
	 * @param ids
	 */
	public CommonResult multiGet(String index, String[] ids) throws IOException {
		if(!indices.isExists(index)){
			return CommonResult.failed(RestStatus.NOT_FOUND,"index not found",null);
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
			}else {
				return CommonResult.failed(RestStatus.NOT_ACCEPTABLE,"response not exist",list);
			}
		}
		return CommonResult.success(list);
	}

	/**
	 * 更新字段
	 * @param index
	 * @param id
	 * @param jsonMap
	 */
	public CommonResult update(String index, String id, Map<String,Object> jsonMap) throws IOException {
		if(!isIdExists(index,id)){
			return CommonResult.failed(RestStatus.NOT_FOUND,"index not found",0);
		}
		UpdateRequest request = new UpdateRequest(index, id).doc(jsonMap);
		/*request.retryOnConflict(3);
		request.fetchSource(true);*/
		UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
		return CommonResult.success(1);
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

	public CommonResult batchAscendingId(String index,List<Map<String,Object>> list) throws IOException {
		if(!indices.isExists(index)){
			indices.create(index);
		}
		BulkRequest bulkRequest = new BulkRequest();
		List<IndexRequest> requests = new ArrayList<>();
		for (int i = 0;i<list.size();i++){
			IndexRequest indexRequest = new IndexRequest(index);
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
		client.bulk(bulkRequest, RequestOptions.DEFAULT);
		return CommonResult.success("batchInsert success",Objects.requireNonNull(requests).size());
	}

	/**
	 * 批量插入数据(随机ID)
	 * @param index
	 * @param source
	 */
	public CommonResult batchInsert(String index, String[] source) throws IOException {
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
		client.bulk(bulkRequest, RequestOptions.DEFAULT);
		return CommonResult.success("batchInsert success",Objects.requireNonNull(requests).size());
	}

	/**
	 * 批量插入数据(指定ID)
	 * @param index
	 * @param source
	 */
	public CommonResult batchAscendingId(String index, String[] source) throws IOException {
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
		client.bulk(bulkRequest, RequestOptions.DEFAULT);
		return CommonResult.success("batchInsert success",Objects.requireNonNull(requests).size());
	}
}

