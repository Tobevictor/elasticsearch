package com.learn.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.learn.elasticsearch.model.IndexEntity;
import org.elasticsearch.action.ActionListener;
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

/**
 * @Date 2019/8/21 10:03
 * @author dshuyou
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
		if(id == null){
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
		return response.status() == RestStatus.CREATED ? source :null;
	}

	public Object index(String index,Object source) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(source, "source");
		IndexRequest request = new IndexRequest(index);
		if (source instanceof String) {
			request.source(String.valueOf(source),XContentType.JSON);
		} else if (source instanceof Map) {
			request.source((Map) source,XContentType.JSON);
		} else if (source instanceof XContentBuilder) {
			request.source(XContentType.JSON,source);
		}
		IndexResponse response = client.index(request,RequestOptions.DEFAULT);
		return response.status() == RestStatus.CREATED? source :null;

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
	public boolean delete(String index, String id) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(id, "id");
		DeleteRequest request = new DeleteRequest(index,id);

		DeleteResponse response = client.delete(request,RequestOptions.DEFAULT);
		return response.status() == RestStatus.OK ;
	}

	public long count(String index) throws IOException {
		Objects.requireNonNull(index, "index");
		CountRequest request = new CountRequest(index);

		CountResponse response = client.count(request,RequestOptions.DEFAULT);
		return response.status() == RestStatus.OK ? response.getCount() :0;
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
		if (!getResponse.isExists() || getResponse.isSourceEmpty()) {
			return Collections.emptyMap();
		}
		return getResponse.getSourceAsMap();
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

		GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
		if (Objects.requireNonNull(getResponse).isExists() && !getResponse.isSourceEmpty()) {
			for (String field : fields) {
				list.add(getResponse.getField(field).getValues());
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

	private List<IndexRequest> generateRequests(String index, String[] source){
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

	public long bulkIndexAsc(String index,List<Map<String,Object>> source) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(source, "source");

		BulkRequest bulkRequest = new BulkRequest();
		List<IndexRequest> requests = new LinkedList<>();
		for (int i = 0;i<source.size();i++){
			IndexRequest indexRequest = new IndexRequest(index).id(String.valueOf(source.get(i).get("id")));
			indexRequest.source(JSON.toJSONString(source.get(i)), XContentType.JSON);
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

	public long bulkIndex(List<IndexEntity> queries) throws IOException {
		BulkRequest bulkRequest = new BulkRequest();
		List<IndexRequest> requests = new LinkedList<>();
		for(IndexEntity index:queries) {
			IndexRequest indexRequest = new IndexRequest(index.getIndexName());
			indexRequest.source(index.getSource(), XContentType.JSON);
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

	public long bulkUpdate(List<IndexEntity> queries) throws IOException {
		BulkRequest bulkRequest = new BulkRequest();
		List<UpdateRequest> requests = new LinkedList<>();
		for(IndexEntity update:queries) {
			UpdateRequest updateRequest = new UpdateRequest(update.getIndexName(),update.getId());
			updateRequest.doc().source(update.getSource(), XContentType.JSON);
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

	public long bulkDelete(List<IndexEntity> queries) throws IOException {
		BulkRequest bulkRequest = new BulkRequest();
		List<DeleteRequest> requests = new LinkedList<>();
		for(IndexEntity delete:queries) {
			DeleteRequest deleteRequest = new DeleteRequest(delete.getIndexName()).id(delete.getId());
			requests.add(deleteRequest);
			if(requests.size()%50000 == 0){
				for (DeleteRequest request : requests) {
					bulkRequest.add(request);
				}
				client.bulk(bulkRequest, RequestOptions.DEFAULT);
				bulkRequest.getRefreshPolicy();
				requests.clear();
			}
		}
		for (DeleteRequest request : requests) {
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
	public long bulkIndex(String index, String[] source) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(source, "source");
		BulkRequest bulkRequest = new BulkRequest();
		List<IndexRequest> requests = generateRequests(index,source);
		if(requests!=null && requests.size()>0){
			for (IndexRequest indexRequest : requests) {
				bulkRequest.add(indexRequest);
			}
		}
		client.bulk(bulkRequest, RequestOptions.DEFAULT);
		return Objects.requireNonNull(requests).size();
	}
}


