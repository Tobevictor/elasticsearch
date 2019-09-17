package com.learn.elasticsearch;

import com.learn.elasticsearch.model.SourceEntity;
import org.elasticsearch.action.ActionListener;
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

	private final int COUNT = 30000;
	private RestHighLevelClient client;

	public Document(RestHighLevelClient client){
		this.client = client;
	}

	public Document setClient(RestHighLevelClient client){
		this.client = client;
		return this;
	}

	/**
	 * @param index - 索引名称
	 * @param id - 索引id
	 * @param source - 索引内容
	 * @return - boolean
	 * @throws IOException - IOException
	 */
	public boolean index(String index, String id, Object source) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(source, "source");
		if(id == null){
			return index(index, source);
		}
		IndexRequest request = new IndexRequest(index).id(id);
		setIndexRequest(request,source);

		IndexResponse response = client.index(request,RequestOptions.DEFAULT);
		return response.status() == RestStatus.CREATED;
	}

	/**
	 * @param index - 索引名称
	 * @param source - 索引内容
	 * @return - boolean
	 * @throws IOException - IOException
	 */
	public boolean index(String index,Object source) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(source, "source");
		IndexRequest request = new IndexRequest(index);
		setIndexRequest(request,source);

		IndexResponse response = client.index(request,RequestOptions.DEFAULT);
		return response.status() == RestStatus.CREATED ;
	}

	/**
	 * @param index - 索引名称
	 * @param id - 索引id
	 * @param source - 索引内容
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
	 * @param index - 索引名称
	 * @param id - 索引id
	 * @return - boolean
	 * @throws IOException - IOException
	 */
	public boolean delete(String index, String id) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(id, "id");
		DeleteRequest request = new DeleteRequest(index,id);

		DeleteResponse response = client.delete(request,RequestOptions.DEFAULT);
		return response.status() == RestStatus.OK ;
	}

	/**
	 * @param index - 索引名称
	 * @return - 索引数量
	 * @throws IOException - IOException
	 */
	public long count(String index) throws IOException {
		Objects.requireNonNull(index, "index");
		CountRequest request = new CountRequest(index);

		CountResponse response = client.count(request,RequestOptions.DEFAULT);
		return response.status() == RestStatus.OK ? response.getCount() :0;
	}

	/**
	 * @param index - 索引名称
	 * @param id - 索引id
	 * @return - boolean
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
	 * @param index - 索引名称
	 * @param id - 索引id
	 * @return - 索引内容
	 * @throws IOException - IOException
	 */
	public String get(String index, String id) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(id, "id");
		GetRequest request = new GetRequest(index,id);
		GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
		if (!getResponse.isExists() || getResponse.isSourceEmpty()) {
			return null;
		}
		return getResponse.getSourceAsString();
	}

	/**
	 * @param index - 索引名称
	 * @param fields - 索引字段集合
	 * @return - 索引集合
	 * @throws IOException - IOException
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
	 * @param index - 索引名称
	 * @param ids - 索引id集合
	 * @return - 索引列表
	 * @throws IOException - IOException
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
	 * @param index - 索引名称
	 * @param id - 索引id
	 * @param source - 索引内容
	 * @return - boolean
	 * @throws IOException - IOException
	 */
	public boolean update(String index, String id, Object source) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(id, "id");
		Objects.requireNonNull(source, "source");
		UpdateRequest request = new UpdateRequest(index, id).doc(source);
		setUpdateRequest(request,source);

		UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
		return response.status() == RestStatus.OK;
	}

	/**
	 * @param index - 索引名称
	 * @param source - 索引内容
	 * @return - 索引数量
	 * @throws IOException - IOException
	 */
	public long bulkIndex1(String index,List<Map<String,Object>> source) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(source, "source");

		BulkRequest bulkRequest = new BulkRequest();
		List<IndexRequest> requests = new LinkedList<>();
		int count = 0;
		for (Map<String, Object> aSource : source) {
			long start = System.currentTimeMillis();
			IndexRequest indexRequest = new IndexRequest(index);
			indexRequest.source(aSource, XContentType.JSON);
			requests.add(indexRequest);
			if (requests.size() % COUNT == 0) {
				for (IndexRequest request : requests) {
					bulkRequest.add(request);
				}
				BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
				if (responses.status() == RestStatus.CREATED) {
					count = count + COUNT;
				}
				bulkRequest.requests().clear();
				requests.clear();
				long end = System.currentTimeMillis();
				System.out.println((end - start) / 1000 + "s");
			}
		}
		for (IndexRequest request : requests) {
			bulkRequest.add(request);
		}
		BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
		if(responses.status() == RestStatus.CREATED){
			count = count + requests.size();
		}
		return count;
	}

	/**
	 * @param index - 索引名称
	 * @param queries - 索引内容
	 * @return - 索引数量
	 * @throws IOException - IOException
	 */
	public long bulkIndex(String index, List<SourceEntity> queries) throws IOException {
		BulkRequest bulkRequest = new BulkRequest();
		List<IndexRequest> requests = new LinkedList<>();
		int count = 0;
		for (SourceEntity query : queries) {
			long start = System.currentTimeMillis();
			IndexRequest indexRequest = new IndexRequest(index).id(query.getId());

			Object source = query.getSource();
			setIndexRequest(indexRequest, source);
			requests.add(indexRequest);

			if (requests.size() % COUNT == 0) {
				for (IndexRequest request : requests) {
					bulkRequest.add(request);
				}
				BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
				if (responses.status() == RestStatus.CREATED) {
					count = count + COUNT;
				}
				bulkRequest.requests().clear();
				requests.clear();
				long end = System.currentTimeMillis();
				System.out.println((end - start) / 1000 + "s");
			}
		}
		for (IndexRequest request : requests) {
			bulkRequest.add(request);
		}
		BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
		if(responses.status() == RestStatus.CREATED){
			count = count + requests.size();
		}
		return count;
	}

	/**
	 * @param index - 索引名称
	 * @param queries - 索引更新的内容
	 * @return - 索引数量
	 * @throws IOException - IOException
	 */
	public long bulkUpdate(String index, List<SourceEntity> queries) throws IOException {
		BulkRequest bulkRequest = new BulkRequest();
		List<UpdateRequest> requests = new LinkedList<>();
		int count = 0;
		for (SourceEntity update : queries) {
			UpdateRequest updateRequest = new UpdateRequest(index, update.getId());

			Object source = update.getSource();
			setUpdateRequest(updateRequest, source);
			requests.add(updateRequest);

			if (requests.size() % COUNT == 0) {
				for (UpdateRequest request : requests) {
					bulkRequest.add(request);
				}
				BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
				if (responses.status() == RestStatus.CREATED) {
					count = count + COUNT;
				}
				bulkRequest.requests().clear();
				requests.clear();
			}
		}
		for (UpdateRequest request : requests) {
			bulkRequest.add(request);
		}
		BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
		if(responses.status() == RestStatus.CREATED){
			count = count + requests.size();
		}
		return count;
	}

	/**
	 * @param index - 索引名称
	 * @param queries - 索引删除的内容
	 * @return - 索引数量
	 * @throws IOException - IOException
	 */
	public long bulkDelete(String index, List<SourceEntity> queries) throws IOException {
		BulkRequest bulkRequest = new BulkRequest();
		List<DeleteRequest> requests = new LinkedList<>();
		int count = 0;
		for (SourceEntity delete : queries) {
			DeleteRequest deleteRequest = new DeleteRequest(index).id(delete.getId());
			requests.add(deleteRequest);
			if (requests.size() % COUNT == 0) {
				for (DeleteRequest request : requests) {
					bulkRequest.add(request);
				}
				BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
				if (responses.status() == RestStatus.CREATED) {
					count = count + COUNT;
				}
				bulkRequest.requests().clear();
				requests.clear();
			}
		}
		for (DeleteRequest request : requests) {
			bulkRequest.add(request);
		}
		BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
		if(responses.status() == RestStatus.CREATED){
			count = count + requests.size();
		}
		return count;
	}

	private void setIndexRequest(IndexRequest indexRequest,Object source){
		if(source instanceof String){
			indexRequest.source(String.valueOf(source), XContentType.JSON);
		}else if(source instanceof Map) {
			indexRequest.source((Map) source, XContentType.JSON);
		}else if(source instanceof XContentBuilder){
			indexRequest.source((XContentBuilder)source);
		}
	}

	private void setUpdateRequest(UpdateRequest updateRequest,Object source){
		if (source instanceof String) {
			updateRequest.doc(String.valueOf(source),XContentType.JSON);
		} else if (source instanceof Map) {
			updateRequest.doc((Map) source,XContentType.JSON);
		} else if (source instanceof XContentBuilder) {
			updateRequest.doc(XContentType.JSON,source);
		}
	}
}


