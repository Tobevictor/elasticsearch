package com.learn.common.elastic.data;

import com.learn.common.elastic.common.result.CommonResult;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dshuyou
 * @create 2019/7/9
 * 索引工具类
 */
public class Indices {

	private final int SHARDS = 3;
	private final int REPLICAS = 2;
	public RestHighLevelClient client;

	public Indices(RestHighLevelClient client){
		this.client = client;
	}


	/**
	 * 创建默认索引
	 */
	public int create(String index) throws IOException {
		if(isExists(index)){
			return RestStatus.NOT_ACCEPTABLE.getStatus();
		}
		CreateIndexRequest request = new CreateIndexRequest(index);
		request.settings(Settings.builder()
				.put("index.number_of_shards", SHARDS)
				.put("index.number_of_replicas", REPLICAS)
		);
		request.setTimeout(TimeValue.timeValueMinutes(2));
		request.setMasterTimeout(TimeValue.timeValueMinutes(1));
		request.waitForActiveShards(ActiveShardCount.DEFAULT);
		Map<String, Object> message = new HashMap<>();
		message.put("type", "text");
		Map<String, Object> properties = new HashMap<>();
		properties.put("message", message);
		Map<String, Object> mapping = new HashMap<>();
		mapping.put("properties", properties);
		request.mapping(mapping);

		client.indices().create(request, RequestOptions.DEFAULT);
		return RestStatus.OK.getStatus();
	}

	/**
	 * 同步创建索引
	 * @param index
	 * @param jsonString
	 */
	public int create(String index, String jsonString) throws IOException {
		if(isExists(index)){
			return RestStatus.NOT_ACCEPTABLE.getStatus();
		}
		CreateIndexRequest request = new CreateIndexRequest(index);
		request.settings(Settings.builder()
				.put("index.number_of_shards", SHARDS)
				.put("index.number_of_replicas", REPLICAS)
		);
		request.setTimeout(TimeValue.timeValueMinutes(2));
		request.setMasterTimeout(TimeValue.timeValueMinutes(1));
		request.waitForActiveShards(ActiveShardCount.DEFAULT);
		request.mapping(jsonString, XContentType.JSON);

		client.indices().create(request, RequestOptions.DEFAULT);
		return RestStatus.OK.getStatus();
	}

	/**
	 * 同步创建索引
	 * @param index
	 */
	public int createByXcontent(String index,XContentBuilder builder) throws IOException {
		if(isExists(index)){
			return RestStatus.NOT_ACCEPTABLE.getStatus();
		}
		CreateIndexRequest request = new CreateIndexRequest(index);
		request.settings(Settings.builder()
				.put("index.number_of_shards", SHARDS)
				.put("index.number_of_replicas", REPLICAS)
		);
		request.setTimeout(TimeValue.timeValueMinutes(2));
		request.setMasterTimeout(TimeValue.timeValueMinutes(1));
		request.waitForActiveShards(ActiveShardCount.DEFAULT);

		request.mapping(builder);
		client.indices().create(request, RequestOptions.DEFAULT);
		return RestStatus.OK.getStatus();
	}

	/**
	 * 同步创建索引
	 * @param index
	 */
	public int create(String index,Map<String,Object> mapping) throws IOException {
		if(isExists(index)){
			return RestStatus.NOT_ACCEPTABLE.getStatus();
		}
		CreateIndexRequest request = new CreateIndexRequest(index);
		request.settings(Settings.builder()
				.put("index.number_of_shards", SHARDS)
				.put("index.number_of_replicas", REPLICAS)
		);
		request.setTimeout(TimeValue.timeValueMinutes(2));
		request.setMasterTimeout(TimeValue.timeValueMinutes(1));
		request.waitForActiveShards(ActiveShardCount.DEFAULT);
		request.mapping(mapping);

		client.indices().create(request, RequestOptions.DEFAULT);
		return RestStatus.OK.getStatus();
	}

	/**
	 * 异步创建索引
	 * @param index
	 * @param jsonString
	 */
	public int createAsync(String index, String jsonString) throws IOException {
		if(isExists(index)){
			return RestStatus.NOT_ACCEPTABLE.getStatus();
		}
		CreateIndexRequest request = new CreateIndexRequest(index);
		request.settings(Settings.builder()
				.put("index.number_of_shards", SHARDS)
				.put("index.number_of_replicas", REPLICAS)
		);
		request.setTimeout(TimeValue.timeValueMinutes(2));
		request.setMasterTimeout(TimeValue.timeValueMinutes(1));
		request.waitForActiveShards(ActiveShardCount.DEFAULT);
		request.source(jsonString, XContentType.JSON);

		ActionListener<CreateIndexResponse> listener = new ActionListener<CreateIndexResponse>() {
			@Override
			public void onResponse(CreateIndexResponse createIndexResponse) {
			}
			@Override
			public void onFailure(Exception e) {
			}
		};
		client.indices().createAsync(request,RequestOptions.DEFAULT,listener);
		return RestStatus.OK.getStatus();
	}

	/**
	 * 删除索引
	 * @param index
	 */
	public int delete(String index) throws IOException {
		if(!isExists(index)){
			return RestStatus.NOT_FOUND.getStatus();
		}
		DeleteIndexRequest request = new DeleteIndexRequest(index);
		client.indices().delete(request,RequestOptions.DEFAULT);
		return RestStatus.OK.getStatus();

	}

	/**
	 * 判断索引是否存在
	 * @param index
	 */
	public boolean isExists(String... index) {
		try {
			GetIndexRequest request = new GetIndexRequest(index);
			return client.indices().exists(request, RequestOptions.DEFAULT);
		}catch (Exception e){
			return false;
		}
	}

	/**
	 * 更新索引
	 * @param indexs
	 * @param map
	 */
	public int update(Map<String, Object> map, String... indexs) throws IOException {
		if(!isExists(indexs)){
			return RestStatus.NOT_FOUND.getStatus();
		}
		UpdateSettingsRequest requestMultiple =
				new UpdateSettingsRequest(indexs);

		client.indices().putSettings(requestMultiple, RequestOptions.DEFAULT);
		return RestStatus.OK.getStatus();
	}

	/**
	 * 获取索引
	 * @param index
	 */
	public CommonResult get(String... index) throws IOException {
		if(!isExists(index)){
			return CommonResult.failed(RestStatus.NOT_FOUND);
		}
		GetIndexRequest request = new GetIndexRequest(index);
		//request.includeDefaults(true);
		//request.indicesOptions(IndicesOptions.lenientExpandOpen());

		GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
		Map<String,Settings> setting = response.getSettings();
		String[] indices = response.getIndices();
		Map<String, MappingMetaData> mapping = response.getMappings();
		return CommonResult.success(setting+"/"+ Arrays.toString(indices) +"/"+mapping);
	}

	/**
	 * 刷新请求
	 * @param index
	 */
	public int refresh(String... index) throws IOException {
		if(!isExists(index)){
			return RestStatus.NOT_FOUND.getStatus();
		}
		RefreshRequest request = new RefreshRequest(index);
		request.indicesOptions(IndicesOptions.lenientExpandOpen());

		client.indices().refresh(request, RequestOptions.DEFAULT);
		return RestStatus.OK.getStatus();
	}

	/**
	 * 清除缓存
	 * @param field
	 * @param index
	 */
	public int clearCache(String field,String... index) throws IOException {
		if(!isExists(index)){
			return RestStatus.NOT_FOUND.getStatus();
		}
		ClearIndicesCacheRequest request = new ClearIndicesCacheRequest(index);
		request.indicesOptions(IndicesOptions.lenientExpandOpen());
		request.queryCache(true);
		request.fieldDataCache(true);
		request.requestCache(true);
		request.fields(field);

		client.indices().clearCache(request, RequestOptions.DEFAULT);
		return RestStatus.OK.getStatus();

	}

	/**
	 * 刷新索引
	 * @param index
	 */
	public int flush(String... index) throws IOException {
		if(!isExists(index)){
			return RestStatus.NOT_FOUND.getStatus();
		}
		FlushRequest request = new FlushRequest(index);
		request.indicesOptions(IndicesOptions.lenientExpandOpen());
		//request.waitIfOngoing(true);
		//request.force(true);

		client.indices().flush(request, RequestOptions.DEFAULT);
		return RestStatus.OK.getStatus();

	}
}
