package com.learn.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.common.elastic.common.result.ElasticResult;
import com.learn.common.elastic.common.result.ServiceMultiResult;
import com.learn.common.elastic.data.Document;
import com.learn.mapper.CommentMapper;
import com.learn.model.EarthquakeBucketDTO;
import com.learn.model.EarthquakeTemplate;
import com.learn.model.MapSearch;
import com.learn.model.RentSearch;
import com.learn.service.EarthquakeService;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Date 2019/8/20 12:42
 * @Created by dshuyou
 */
@Service
public class EarthquakeServiceImpl implements EarthquakeService {
	private static Logger LOGGER = LoggerFactory.getLogger(EarthquakeServiceImpl.class);

	private static final String INDEX_NAME = "earthquake";
	private final int SHARDS = 3;
	private final int REPLICAS = 2;

	@Autowired
	private RestHighLevelClient client;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private CommentMapper commentMapper;

	@Override
	public boolean create(String index) {
		if(isExists(index)){
			LOGGER.debug(index + " index is arleady exist");
			return false;
		}
		try {
			CreateIndexRequest request = new CreateIndexRequest(index);
			request.settings(Settings.builder()
					.put("index.number_of_shards", SHARDS)
					.put("index.number_of_replicas", REPLICAS)
			);
			request.setTimeout(TimeValue.timeValueMinutes(2));
			request.setMasterTimeout(TimeValue.timeValueMinutes(1));
			request.waitForActiveShards(ActiveShardCount.DEFAULT);

			client.indices().create(request, RequestOptions.DEFAULT);
			LOGGER.debug("Create index with " + index);
			return true;
		} catch (IOException e) {
			LOGGER.error("Error to create index " + index,e);
			return false;
		}
	}

	@Override
	public boolean update(String index, String jsonString) {
		if(!isExists(index)){
			LOGGER.debug(index + " index is not exist");
			return false;
		}
		PutMappingRequest request = new PutMappingRequest(index);

		try {
			request.source(jsonString,XContentType.JSON);
			AcknowledgedResponse response = client.indices().putMapping(request, RequestOptions.DEFAULT);
			LOGGER.debug("Update index with " + index );
			if(response.isAcknowledged()){
				return true;
			}
		} catch (IOException e) {
			LOGGER.error("Error to update index " + index , e);
		}
		return false;
	}

	@Override
	public boolean delete(String index) {
		if(!isExists(index)){
			LOGGER.debug(index + " index is not exist");
			return false;
		}
		DeleteIndexRequest request = new DeleteIndexRequest(index);
		try {
			AcknowledgedResponse response = client.indices().delete(request,RequestOptions.DEFAULT);
			if(response.isAcknowledged()){
				LOGGER.debug("Update index with " + index );
				return true;
			}
		} catch (IOException e) {
			LOGGER.error("Error to update index " + index , e);
		}
		return false;
	}

	private boolean isExists(String... index) {
		try {
			GetIndexRequest request = new GetIndexRequest(index);
			return client.indices().exists(request, RequestOptions.DEFAULT);
		}catch (IOException e){
			LOGGER.error("Error to update index " + index , e);
			return false;
		}
	}

	@Override
	public ElasticResult fromMysql(String index) {
		List<Map<String,Object>> list = commentMapper.findAll();
		if(list.isEmpty()){
			return ElasticResult.failed("MetaData is null or query failed",0);
		}
		Document document = new Document(client);
		try {
			long count = document.batchAscendingId(index,list);
			LOGGER.debug("batch import data success" );
			return ElasticResult.success("batch import success",count);
		} catch (IOException e) {
			LOGGER.error("Error to batch import data " , e);
			return ElasticResult.failed(RestStatus.CONFLICT.getStatus(),"batch import failed,error:"+e,0);
		}
	}

	@Override
	public ElasticResult fromMysqlAsc(String index) {
		return null;
	}

	@Override
	public ElasticResult<Long> query(RentSearch rentSearch) {
		return null;
	}

	@Override
	public ElasticResult<List<String>> suggest(String prefix) {
		return null;
	}

	@Override
	public ElasticResult<Long> aggregateDistrictEarthquake(String cityEnName, String regionEnName, String district) {
		return null;
	}

	@Override
	public ServiceMultiResult<EarthquakeBucketDTO> mapAggregate(String cityEnName) {
		return null;
	}

	@Override
	public ServiceMultiResult<Long> mapQuery(String cityEnName, String orderBy, String orderDirection, int start, int size) {
		return null;
	}

	@Override
	public ServiceMultiResult<Long> mapQuery(MapSearch mapSearch) {
		return null;
	}
}
