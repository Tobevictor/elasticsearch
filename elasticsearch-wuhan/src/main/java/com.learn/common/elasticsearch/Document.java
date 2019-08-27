package com.learn.common.elasticsearch;

import com.learn.common.elasticsearch.model.IndexQuery;
import com.learn.common.elasticsearch.model.UpdateQuery;
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
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;


import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertNull;

/**
 * @Date 2019/8/21 10:03
 * @Created by dshuyou
 */

public class Document {

	private RestHighLevelClient client;

	public Document(RestHighLevelClient client){
		this.client = client;
	}

    /**
	 * 插入数据(指定ID)
	 * @param index
	 * @param id
	 * @param source
	 */
	public Object index(String index, String id, Object source) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(source, "source");
		if(id.equals(null)){
			return index(index,source);
		}
		IndexRequest request = new IndexRequest(index).id(id);
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

	public Object index(String index,Object source) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(source, "source");
		IndexRequest request = new IndexRequest(index);
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


    /**
	 * 异步插入数据(指定ID)
	 * @param index
	 * @param id
	 * @param source
	 */
	public void insertAsync(String index, String id, String source) {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(id, "id");
		Objects.requireNonNull(source, "source");
		IndexRequest request = new IndexRequest(index).id(id);
		request.source(source, XContentType.JSON);
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
	public String delete(String index, String id) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(id, "id");
		DeleteRequest request = new DeleteRequest(index,id);

		DeleteResponse response = client.delete(request,RequestOptions.DEFAULT);
		if (response.getResult() == DocWriteResponse.Result.NOT_FOUND) {
			return null;
		}
		return id;
	}

	public long count(String index) throws IOException {
		Objects.requireNonNull(index, "index");
		CountRequest request = new CountRequest(index);
		try{
			CountResponse response = client.count(request,RequestOptions.DEFAULT);
			return response.getCount();
		}catch (ElasticsearchException e) {
			if (e.status() == RestStatus.NOT_FOUND) {
				return 0;
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
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(id, "id");
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
	public Map<String, Object> get(String index, String id) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(id, "id");
		GetRequest request = new GetRequest(index,id);
		GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
		if (getResponse.isExists()) {
			return getResponse.getSourceAsMap();
		}
		return null;
	}

    /**
	 * 获取字段值
	 * @param index
	 * @param fields
	 */
	public List<Object> getFieldsValues(String index, String[] fields) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(fields, "fields");
		GetRequest request = new GetRequest(index);
		request.storedFields(fields);
		List<Object> list = new ArrayList<>();
		GetResponse getResponse = null;
		getResponse = client.get(request, RequestOptions.DEFAULT);
		if (Objects.requireNonNull(getResponse).isExists()) {
			for (int i = 0;i<fields.length; i++){
				list.add(getResponse.getField(fields[i]).getValues());
			}
		}
		return list;
	}



    /**
	 * 获取多个字段
	 * @param index
	 * @param ids
	 */
	public List<Map<String,Object>> multiGet(String index, String[] ids) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(ids, "ids");
		MultiGetRequest request = new MultiGetRequest();
		for (String id:ids){
			request.add(new MultiGetRequest.Item(index,id));
		}
		MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
		List<Map<String, Object>> list = new LinkedList<>();
		for (int i = 0;i<response.getResponses().length;i++){
			MultiGetItemResponse firstItem = response.getResponses()[i];
			assertNull(firstItem.getFailure());
			GetResponse getResponse = firstItem.getResponse();
			if (getResponse.isExists()) {
				Map<String,Object> sourceAsMap = getResponse.getSourceAsMap();
				list.add(sourceAsMap);
			}
		}
		return list;
	}


    /**
	 * 更新字段
	 * @param index
	 * @param id
	 * @param source
	 */
	public boolean update(String index, String id, Map<String,Object> source) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(id, "id");
		Objects.requireNonNull(source, "source");
		UpdateRequest request = new UpdateRequest(index, id).doc(source);

		UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
		return response.status() == RestStatus.OK;
	}

	public List<IndexRequest> generateRequests(String index, String[] source){
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(source, "source");
		IndexRequest indexRequest = new IndexRequest(index);
		List<IndexRequest> requests = new LinkedList<>();
		for (String s : source){
			indexRequest.source(s, XContentType.JSON);
			requests.add(indexRequest);
		}
		return requests;
	}

    /**
	 * 批量插入数据
	 * @param index
	 * @param source
	 */
	public long bulkIndex(String index,List<Map<String,Object>> source) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(source, "source");
		BulkRequest bulkRequest = new BulkRequest();
		List<IndexRequest> requests = new LinkedList<>();
		for (int i = 0;i<source.size();i++){
			IndexRequest indexRequest = new IndexRequest(index);
			//UpdateRequest request = new UpdateRequest().doc();
			indexRequest.source(source.get(i), XContentType.JSON);
			requests.add(indexRequest);
			if(requests.size()%50000 == 0){
				for (IndexRequest request : requests) {
					bulkRequest.add(request);
				}
				client.bulk(bulkRequest, RequestOptions.DEFAULT);
				bulkRequest = new BulkRequest();
				requests.clear();
			}
		}
		for (IndexRequest request : requests) {
			bulkRequest.add(request);
		}
		client.bulk(bulkRequest, RequestOptions.DEFAULT);
		return Objects.requireNonNull(requests).size();
	}

	public long bulkIndex(List<IndexQuery> queries) throws IOException {
		BulkRequest bulkRequest = new BulkRequest();
		List<IndexRequest> requests = new LinkedList<>();
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
				requests.clear();
			}
		}
		for (IndexRequest request : requests) {
			bulkRequest.add(request);
		}
		client.bulk(bulkRequest, RequestOptions.DEFAULT);
		return Objects.requireNonNull(requests).size();
	}

	public long bulkUpdate(List<UpdateQuery> queries) throws IOException {
		BulkRequest bulkRequest = new BulkRequest();
		List<UpdateRequest> requests = new LinkedList<>();
		for(UpdateQuery updateQuery:queries) {
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

    /**
	 * 批量插入数据(随机ID)
	 * @param index
	 * @param source
	 */
	public long batchInsert(String index, String[] source) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(source, "source");
		BulkRequest bulkRequest = new BulkRequest();
		List<IndexRequest> requests = generateRequests(index,source);
		if(requests!=null&&requests.size()>0){
			for (IndexRequest indexRequest : requests) {
				bulkRequest.add(indexRequest);
			}
		}
		client.bulk(bulkRequest, RequestOptions.DEFAULT);
		return Objects.requireNonNull(requests).size();
	}


    /**
	 * 批量插入数据(指定ID)
	 * @param index
	 * @param source
	 */
	public long batchAscendingId(String index, String[] source) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(source, "source");
		BulkRequest bulkRequest = new BulkRequest();
		List<IndexRequest> requests = new LinkedList<>();
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
		return Objects.requireNonNull(requests).size();
	}
}


