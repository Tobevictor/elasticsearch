package com.learn.elasticsearch;

import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
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
	public static final int SHARDS = 3;
	public static final int INIT_REPLICAS = 0;
	public static final int REPLICAS = 2;
	public static final int TIMEOUT = 2;
	public static final int MASTER_TIMEOUT = 1;
	public static final int INIT_REFLUSH_INTERVAL = -1;
	public static final int REFLUSH_INTERVAL = 30;

	private RestHighLevelClient client;

	public Indice(RestHighLevelClient client){
		this.client = client;
	}

	public Indice(){}

	public Indice setClient(RestHighLevelClient client){
		this.client = client;
		return this;
	}


	/**
	 * @param index -索引
	 * @return -boolean
	 * @throws IOException -io异常
	 */
	public boolean create(String index) throws IOException {
		Objects.requireNonNull(index, "index");
		CreateIndexRequest request = new CreateIndexRequest(index);

		request.settings(Settings.builder()
				.put("index.number_of_shards", SHARDS)
				.put("index.number_of_replicas", INIT_REPLICAS)
				.put("index.refresh_interval", TimeValue.timeValueSeconds(INIT_REFLUSH_INTERVAL)));
		request.setTimeout(TimeValue.timeValueMinutes(TIMEOUT));
		request.setMasterTimeout(TimeValue.timeValueMinutes(MASTER_TIMEOUT));
		request.waitForActiveShards(ActiveShardCount.DEFAULT);
		request.source(createAnalysis());
		return client.indices().create(request, RequestOptions.DEFAULT).isAcknowledged();
	}

	/***
	 * @param index -索引
	 * @param settings - 设置
	 * @return -boolean
	 * @throws IOException -io异常
	 */
	public boolean create(String index, Object settings) throws IOException {
		CreateIndexRequest request = new CreateIndexRequest(index);

		if (settings instanceof String) {
			request.source(String.valueOf(settings), XContentType.JSON);
		} else if (settings instanceof Map) {
			request.source((Map) settings);
		} else if (settings instanceof XContentBuilder) {
			request.source((XContentBuilder) settings);
		}

		return client.indices().create(request, RequestOptions.DEFAULT).isAcknowledged();
	}

	/**
	 * @param index - 索引
	 * @param mapping - 映射
	 * @return -boolean
	 * @throws IOException - io异常
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
	 * @param index -索引
	 * @return -boolean
	 * @throws IOException -io异常
	 */
	public boolean updateSetting(String index) throws IOException {
		UpdateSettingsRequest request = new UpdateSettingsRequest(index);
		request.settings(Settings.builder()
				.put("index.number_of_replicas",REPLICAS)
				.put("index.refresh_interval",REFLUSH_INTERVAL)
		);
		return client.indices().putSettings(request, RequestOptions.DEFAULT).isAcknowledged();
	}

	public boolean updateSetting(String index,int replicas,int reflush) throws IOException {
		UpdateSettingsRequest request = new UpdateSettingsRequest(index);
		request.settings(Settings.builder()
				.put("index.number_of_replicas",replicas)
				.put("index.refresh_interval",reflush)
		);
		return client.indices().putSettings(request, RequestOptions.DEFAULT).isAcknowledged();
	}

	public String getSetting(String index) throws IOException {
		Objects.requireNonNull(index, "index");
		GetSettingsRequest request = new GetSettingsRequest().indices(index);
		return client.indices().getSettings(request,RequestOptions.DEFAULT).toString();
	}

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
	 * @param index - 索引
	 * @return - boolean
	 * @throws IOException - io异常
	 */
	public boolean deleteIndex(String... index) throws IOException {
		Objects.requireNonNull(index, "index");
		if (isExists(index)) {
			return client.indices().delete(new DeleteIndexRequest(index),RequestOptions.DEFAULT).isAcknowledged();
		}
		return false;
	}

	/**
	 * @param index - 索引
	 * @return - boolean
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


	public Object get(String... index) throws IOException {
		Objects.requireNonNull(index, "index");
		GetIndexRequest request = new GetIndexRequest(index);

		GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
		Map<String,Settings> setting = response.getSettings();
		String[] indices = response.getIndices();
		Map<String, MappingMetaData> mapping = response.getMappings();
		return setting+"/"+ Arrays.toString(indices) +"/"+mapping;
	}

	public void refresh(String... index) throws IOException {
		Objects.requireNonNull(index, "index");
		client.indices().refresh(refreshRequest(index),RequestOptions.DEFAULT);
	}

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

	public void flush(String... index) throws IOException {
		Objects.requireNonNull(index, "index");
		FlushRequest request = new FlushRequest(index);
		request.indicesOptions(IndicesOptions.lenientExpandOpen());

		client.indices().flush(request, RequestOptions.DEFAULT);
	}

	/**
	 * @param index - 索引
	 * @param aliasname - 别名
	 * @return -boolean
	 * @throws IOException - io异常
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

	public XContentBuilder createAnalysis() throws IOException {
		XContentBuilder builder = XContentFactory.jsonBuilder();
		builder.startObject().startObject("settings")
				.startObject("analysis")
				.startObject("analyzer")
				.startObject("pinyin_analyzer").field("tokenizer","ik_smart")
				.field("filter","my_pinyin")
				.endObject()
				.startObject("onlyOne_analyzer").field( "tokenizer","onlyOne_pinyin")
				.endObject().endObject().startObject("tokenizer").startObject("onlyOne_pinyin")
				.field( "type","pinyin")
				.field("keep_separate_first_letter","true")
				.field("keep_full_pinyin","false")
				.endObject().endObject()
				.startObject("filter")
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
							.field("analyzer", "pinyin_analyzer")
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
	 * @param object -具体的对象
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
	 * @param varType -数据类型
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

	public boolean putIndexTemplate(String index, String source) throws IOException {
		PutIndexTemplateRequest request = new PutIndexTemplateRequest(index);
		request.source(source,XContentType.JSON);
		return client.indices().putTemplate(request,RequestOptions.DEFAULT).isAcknowledged();
	}
}

