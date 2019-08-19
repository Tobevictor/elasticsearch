package com.learn.service.impl;

import com.learn.common.elastic.common.result.ElasticResult;
import com.learn.common.elastic.data.Indices;
import com.learn.service.IndiceService;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

/**
 * @Date 2019/8/14 19:08
 * @Created by dshuyou
 */
@Service
public class IndiceServiceImpl implements IndiceService {
	private static final Logger LOGGER = LoggerFactory.getLogger(IndiceServiceImpl.class);

	@Autowired
	private RestHighLevelClient client;
	private Indices indices;
	@PostConstruct
	public void init() {
		indices = new Indices(client);
	}

	@Override
	public ElasticResult create(String index) {
		ElasticResult result;
		try {
			indices.create(index);
			result = ElasticResult.success("create index success",index);
		} catch (IOException e) {
			LOGGER.error("IOException");
			result = ElasticResult.failed(RestStatus.CONFLICT.getStatus(),"create index failed,error:"+e,index);
		}
		return result;
	}

	@Override
	public ElasticResult create(String index, String jsonString) {
		ElasticResult result;
		try {
			indices.create(index,jsonString);
			result = ElasticResult.success("create index success",index);
		} catch (IOException e) {
			LOGGER.error("IOException");
			result = ElasticResult.failed(RestStatus.CONFLICT.getStatus(),"create index failed,error:"+e,index);
		}
		return result;
	}

	@Override
	public ElasticResult create(String index, Map<String, Object> map) {
		ElasticResult result;
		try {
			indices.create(index,map);
			result = ElasticResult.success("create index success",index);
		} catch (IOException e) {
			LOGGER.error("IOException");
			result = ElasticResult.failed(RestStatus.CONFLICT.getStatus(),"create index failed,error:"+e,index);
		}
		return result;
	}

	@Override
	public ElasticResult delete(String index) {
		ElasticResult result;
		try {
			indices.delete(index);
			result = ElasticResult.success("delete index success",index);
		} catch (IOException e) {
			LOGGER.error("IOException");
			result = ElasticResult.failed(RestStatus.CONFLICT.getStatus(),"create index failed,error:"+e,index);
		}
		return result;
	}

	@Override
	public ElasticResult isExist(String index) {
		boolean result = indices.isExists(index);
		return ElasticResult.success("index isExist",result);
	}
}
