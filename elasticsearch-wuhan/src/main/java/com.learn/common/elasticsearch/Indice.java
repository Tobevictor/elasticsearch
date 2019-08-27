package com.learn.common.elasticsearch;

import com.learn.model.Earthquake;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;

import java.lang.reflect.Field;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.*;

import static org.elasticsearch.client.Requests.refreshRequest;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @Date 2019/8/21 10:02
 * @Created by dshuyou
 */
public class Indice {
	private final int SHARDS = 3;
	private final int REPLICAS = 2;
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
		);
		request.setTimeout(TimeValue.timeValueMinutes(2));
		request.setMasterTimeout(TimeValue.timeValueMinutes(1));
		request.waitForActiveShards(ActiveShardCount.DEFAULT);
		return client.indices().create(request, RequestOptions.DEFAULT).isAcknowledged();
	}

	/***
	 * 创建索引(自定义settings)
	 * @param index
	 * @param settings
	 */
	public boolean createIndexWithSettings(String index, Object settings) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(settings, "settings");
		CreateIndexRequest request = new CreateIndexRequest(index);
		if (settings instanceof String) {
			request.settings(String.valueOf(settings), Requests.INDEX_CONTENT_TYPE);
		} else if (settings instanceof Map) {
			request.settings((Map) settings);
		} else if (settings instanceof XContentBuilder) {
			request.settings((XContentBuilder) settings);
		}
		return client.indices().create(request, RequestOptions.DEFAULT).isAcknowledged();
	}

	/**
	 * 同步创建索引(自定义mapping)
	 * @param index
	 * @param mapping
	 */
	public boolean create(String index, Object mapping) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(mapping, "mapping");
		CreateIndexRequest request = new CreateIndexRequest(index);
		request.settings(Settings.builder()
				.put("index.number_of_shards", SHARDS)
				.put("index.number_of_replicas", REPLICAS)
		);
		request.setTimeout(TimeValue.timeValueMinutes(2));
		request.setMasterTimeout(TimeValue.timeValueMinutes(1));
		request.waitForActiveShards(ActiveShardCount.DEFAULT);
		if (mapping instanceof String) {
			request.mapping(String.valueOf(mapping), XContentType.JSON);
		} else if (mapping instanceof Map) {
			request.mapping((Map) mapping);
		} else if (mapping instanceof XContentBuilder) {
			request.mapping((XContentBuilder) mapping);
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
		);
		request.setTimeout(TimeValue.timeValueMinutes(2));
		request.setMasterTimeout(TimeValue.timeValueMinutes(1));
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
	public Map getMapping(String... index) throws IOException {
		Objects.requireNonNull(index, "index");
		GetMappingsRequest request = new GetMappingsRequest().indices(index);

		return client.indices().getMapping(request, RequestOptions.DEFAULT).mappings();
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
		//request.includeDefaults(true);
		//request.indicesOptions(IndicesOptions.lenientExpandOpen());

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
		//request.waitIfOngoing(true);
		//request.force(true);

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
		IndicesAliasesRequest.AliasActions aliasActions = IndicesAliasesRequest.AliasActions.add().alias(aliasname).index(index);

		return client.indices().updateAliases(new IndicesAliasesRequest().addAliasAction(aliasActions),RequestOptions.DEFAULT).isAcknowledged();

	}


	/**
	 * 构建映射
	 * @param index
	 * @param obj
	 */
	public boolean createMapping(String index,Object obj) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(obj, "obj");
		List<String> analyzedField;
		if(obj instanceof Earthquake){
			analyzedField = ((Earthquake) obj).getAnalyzedField();
		}else {
			return false;
		}
		PutMappingRequest request = new PutMappingRequest(index).source(setMapping(obj,analyzedField));
		return client.indices().putMapping(request,RequestOptions.DEFAULT).isAcknowledged();
	}

	/**
	 * 构建映射
	 * @param index
	 * @param obj
	 * @param analyzedField
	 *
	 */
	public boolean createMapping(String index,Object obj,List<String> analyzedField) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(obj, "obj");
		if(analyzedField == null || analyzedField.size() == 0){
			return createMapping(index,obj);
		}
		PutMappingRequest request = new PutMappingRequest(index).source(setMapping(obj,analyzedField));
		return client.indices().putMapping(request,RequestOptions.DEFAULT).isAcknowledged();
	}

	/**
	 * 设置对象的ElasticSearch的Mapping
	 * @param obj
	 * @param analyzedField
	 * @return
	 */
	private XContentBuilder setMapping(Object obj,List<String> analyzedField) {
		Objects.requireNonNull(obj, "obj");
		List<Field> fieldList = getFields(obj);
		XContentBuilder mapping = null;
		try {
			mapping = jsonBuilder().startObject().startObject("properties");
			for (Field field : fieldList) {
				//修饰符是static的字段不处理
				if (Modifier.isStatic(field.getModifiers())){
					continue;
				}
				String name = field.getName();
				if (analyzedField.contains(name)) {
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
		System.out.println(mapping.toString());
		return mapping;
	}

	/**
	 * 获取对象所有自定义的属性
	 * @param obj
	 */
	private List<Field> getFields(Object obj) {
		List<Field> fieldList = new ArrayList<>();
		Class objClass = obj.getClass();
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

}

