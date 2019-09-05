package com.learn.elasticsearch;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import static org.elasticsearch.client.Requests.refreshRequest;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @Date 2019/8/21 10:02
 * @author dshuyou
 */
public class Indice {
	private final int SHARDS = 3;
	private final int REPLICAS = 2;
	private final int TIMEOUT = 2;
	private final int MASTER_TIMEOUT = 1;
	private final int REFLUSH_INTERVAL = 30;

	private RestHighLevelClient client;

	public Indice(RestHighLevelClient client){
		this.client = client;
	}

    /**
	 * 创建默认索引
	 * @param index
	 */
	public boolean create(String index) throws IOException {
		Objects.requireNonNull(index, "index");
		CreateIndexRequest request = new CreateIndexRequest(index);
		request.settings(Settings.builder()
				.put("index.number_of_shards", SHARDS)
				.put("index.number_of_replicas", REPLICAS)
				.put("index.refresh_interval",TimeValue.timeValueSeconds(REFLUSH_INTERVAL))
		);
		request.setTimeout(TimeValue.timeValueMinutes(TIMEOUT));
		request.setMasterTimeout(TimeValue.timeValueMinutes(MASTER_TIMEOUT));
		request.waitForActiveShards(ActiveShardCount.DEFAULT);
		return client.indices().create(request, RequestOptions.DEFAULT).isAcknowledged();
	}

	/***
	 * 创建索引(自定义settings)
	 * @param index
	 * @param settings
	 */
	public boolean create(String index, Object settings) throws IOException {
		return createWithAnalysis(index,settings,createAnalysis());
	}

	private boolean createWithAnalysis(String index, Object settings, Object analysis) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(settings, "settings");
		CreateIndexRequest request = new CreateIndexRequest(index);

		putSetting(request,settings);
		if (analysis instanceof String) {
			request.source(String.valueOf(analysis), XContentType.JSON);
		}  else if (analysis instanceof Map) {
			request.source((Map) analysis);
		} else if (analysis instanceof XContentBuilder) {
			request.source((XContentBuilder) analysis);
		}

		return client.indices().create(request, RequestOptions.DEFAULT).isAcknowledged();
	}

	/**
	 * 同步创建索引(自定义setting,mapping)
	 * @param index
	 * @param mapping
	 */
	public boolean create(String index, Object settings, Object mapping) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(mapping, "mapping");
		CreateIndexRequest request = new CreateIndexRequest(index);

		putSetting(request,settings);
		if (mapping instanceof String) {
			request.source(String.valueOf(mapping), XContentType.JSON);
		}  else if (mapping instanceof Map) {
			request.source((Map) mapping);
		} else if (mapping instanceof XContentBuilder) {
			request.source((XContentBuilder) mapping);
		}
		CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
		return response.isAcknowledged();
	}

	/**
	 * 更新索引
	 * @param index
	 * @param mapping
	 */
	public boolean putMapping(String index, Object mapping) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(mapping, "mapping");
		PutMappingRequest request = new PutMappingRequest(index);

		if (mapping instanceof String) {
			request.source(String.valueOf(mapping), XContentType.JSON);
		} else if (mapping instanceof Map) {
			request.source((Map) mapping);
		} else if (mapping instanceof XContentBuilder) {
			request.source((XContentBuilder) mapping);
		}
		return client.indices().putMapping(request,RequestOptions.DEFAULT).isAcknowledged();
	}

    /**
	 * 异步创建索引
	 * @param index
	 * @param mapping
	 */
	public void createAsync(String index, String mapping) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(mapping, "mapping");
		CreateIndexRequest request = new CreateIndexRequest(index);
		request.settings(Settings.builder()
				.put("index.number_of_shards", SHARDS)
				.put("index.number_of_replicas", REPLICAS)
				.put("index.refresh_interval",TimeValue.timeValueSeconds(REFLUSH_INTERVAL))
		);
		request.setTimeout(TimeValue.timeValueMinutes(TIMEOUT));
		request.setMasterTimeout(TimeValue.timeValueMinutes(MASTER_TIMEOUT));
		request.waitForActiveShards(ActiveShardCount.DEFAULT);
		request.source(mapping, XContentType.JSON);

		ActionListener<CreateIndexResponse> listener = new ActionListener<CreateIndexResponse>() {
			@Override
			public void onResponse(CreateIndexResponse createIndexResponse) {
			}
			@Override
			public void onFailure(Exception e) {
			}
		};
		client.indices().createAsync(request,RequestOptions.DEFAULT,listener);
	}

	/**
	 * 获取settings
	 * @param index
	 */
	public String getSetting(String index) throws IOException {
		Objects.requireNonNull(index, "index");
		GetSettingsRequest request = new GetSettingsRequest().indices(index);
		return client.indices().getSettings(request,RequestOptions.DEFAULT).toString();
	}

	/**
	 * 获取mapping
	 * @param index
	 */
	public Map<String,Object> getMapping(String... index) throws IOException {
		Objects.requireNonNull(index, "index");
		GetMappingsRequest request = new GetMappingsRequest().indices(index);

		Map<String,MappingMetaData> map =  client.indices().getMapping(request, RequestOptions.DEFAULT).mappings();
		if(map != null){
			Iterator<Map.Entry<String, MappingMetaData>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, MappingMetaData> entry = it.next();
				return entry.getValue().getSourceAsMap();
			}
		}
		return Collections.emptyMap();
	}

    /**
	 * 删除索引
	 * @param index
	 */
	public boolean deleteIndex(String... index) throws IOException {
		Objects.requireNonNull(index, "index");
		if (isExists(index)) {
			return client.indices().delete(new DeleteIndexRequest(index),RequestOptions.DEFAULT).isAcknowledged();
		}
		return false;
	}

    /**
	 * 判断索引是否存在
	 * @param index
	 */
	public boolean isExists(String... index) {
		Objects.requireNonNull(index, "index");
		try {
			GetIndexRequest request = new GetIndexRequest(index);
			return client.indices().exists(request, RequestOptions.DEFAULT);
		}catch (IOException e){
			return false;
		}
	}

    /**
	 * 获取索引
	 * @param index
	 */
	public Object get(String... index) throws IOException {
		Objects.requireNonNull(index, "index");
		GetIndexRequest request = new GetIndexRequest(index);

		GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
		Map<String,Settings> setting = response.getSettings();
		String[] indices = response.getIndices();
		Map<String, MappingMetaData> mapping = response.getMappings();
		return setting+"/"+ Arrays.toString(indices) +"/"+mapping;
	}

    /**
	 * 刷新请求
	 * @param index
	 */
	public void refresh(String... index) throws IOException {
		Objects.requireNonNull(index, "index");
		client.indices().refresh(refreshRequest(index),RequestOptions.DEFAULT);
	}

    /**
	 * 清除缓存
	 * @param field
	 * @param index
	 */
	public void clearCache(String field,String... index) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(field, "field");
		ClearIndicesCacheRequest request = new ClearIndicesCacheRequest(index);
		request.indicesOptions(IndicesOptions.lenientExpandOpen());
		request.queryCache(true);
		request.fieldDataCache(true);
		request.requestCache(true);
		request.fields(field);

		client.indices().clearCache(request, RequestOptions.DEFAULT);

	}

    /**
	 * 刷新索引
	 * @param index
	 */
	public void flush(String... index) throws IOException {
		Objects.requireNonNull(index, "index");
		FlushRequest request = new FlushRequest(index);
		request.indicesOptions(IndicesOptions.lenientExpandOpen());

		client.indices().flush(request, RequestOptions.DEFAULT);
	}

	/**
	 * 添加索引别名
	 * @param index
	 * @param aliasname
	 */
	public Boolean addAlias(String index,String aliasname) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(aliasname, "aliasname");
		IndicesAliasesRequest.AliasActions aliasActions = IndicesAliasesRequest.AliasActions.add()
				.alias(aliasname)
				.index(index);
		return client.indices().updateAliases(new IndicesAliasesRequest().addAliasAction(aliasActions),
				RequestOptions.DEFAULT).isAcknowledged();

	}

	/**
	 * 生成setting
	 */
	public Settings.Builder createSetting(int shards,int replicas,int reflushInterval){
		return Settings.builder()
				.put("index.number_of_shards", shards)
				.put("index.number_of_replicas", replicas)
				.put("index.refresh_interval", TimeValue.timeValueSeconds(reflushInterval));
	}

	private XContentBuilder createAnalysis() throws IOException {
		XContentBuilder builder = XContentFactory.jsonBuilder();
		builder.startObject().startObject("settings")
				.startObject("analysis")
				.startObject("analyzer")
				.startObject("pinyin_analyzer").field("tokenizer","ik_smart")
				.field("filter","my_pinyin")
				.endObject().endObject().startObject("filter")
				.startObject("my_pinyin")
				.field( "type","pinyin")
				.field( "keep_first_letter",true)
				.field( "keep_separate_first_letter" ,true)
				.field( "keep_full_pinyin",true)
				.field("keep_original" ,false)
				.field("limit_first_letter_length" ,16)
				.field( "lowercase",true)
				.endObject().endObject().endObject().endObject().endObject();
		return builder;
	}

	/**
	 * 生成mapping
	 * @param object
	 */
	public XContentBuilder createMapping(Object object) {
		Objects.requireNonNull(object, "object");
		List<Field> fieldList = getFields(object);
		XContentBuilder mapping = null;
		try {
			mapping = jsonBuilder().startObject().startObject("properties");
			for (Field field : fieldList) {
				if (Modifier.isStatic(field.getModifiers())){
					continue;
				}
				String name = field.getName();
				if ("string".equals(field.getType().getSimpleName().toLowerCase())) {
					mapping.startObject(name)
							.field("type", getElasticSearchMappingType(field.getType().getSimpleName().toLowerCase()))
							.field("analyzer", "ik_smart")

							//.field("search_analyzer", "ik_smart")
							.endObject();
				} else {
					mapping.startObject(name)
							.field("type", getElasticSearchMappingType(field.getType().getSimpleName().toLowerCase()))
							.endObject();
				}
			}
			mapping.endObject().endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapping;
	}

	/**
	 * 获取对象所有自定义的属性
	 * @param object
	 */
	private List<Field> getFields(Object object) {
		List<Field> fieldList = new ArrayList<>();
		Class objClass = object.getClass();
		while (null != objClass) {
			fieldList.addAll(Arrays.asList(objClass.getDeclaredFields()));
			objClass = objClass.getSuperclass();
		}
		return fieldList;
	}

	/**
	 * java类型与ElasticSearch类型映射
	 * @param varType
	 */
	private String getElasticSearchMappingType(String varType) {
		String es;
		switch (varType) {
			case "date":
				es = "date";
				break;
			case "double":
				es = "double";
				break;
			case "long":
				es = "long";
				break;
			case "int":
				es = "integer";
				break;
			case "geometry"	:
				es = "geo_shape";
				break;
			default:
				es = "text";
				break;
		}
		return es;
	}

	private void putSetting(CreateIndexRequest request, Object settings) {
		if (settings instanceof String) {
			request.settings(String.valueOf(settings), XContentType.JSON);
		}  else if (settings instanceof Map) {
			request.settings((Map) settings);
		} else if(settings instanceof Settings.Builder){
			request.settings((Settings.Builder)settings);
		}else if (settings instanceof XContentBuilder) {
			request.settings((XContentBuilder) settings);
		}
		request.setTimeout(TimeValue.timeValueMinutes(TIMEOUT));
		request.setMasterTimeout(TimeValue.timeValueMinutes(MASTER_TIMEOUT));
		request.waitForActiveShards(ActiveShardCount.DEFAULT);
	}
}

