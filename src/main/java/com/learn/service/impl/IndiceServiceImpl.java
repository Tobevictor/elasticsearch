package com.learn.service.impl;

import com.learn.common.elastic.data.Indices;
import com.learn.service.IndiceService;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
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

	@Autowired
	private RestHighLevelClient client;
	private Indices indices;
	private int restStaus;
	@PostConstruct
	public void init() {
		indices = new Indices(client);
	}

	@Override
	public int create(String index) {
		try {
			restStaus = indices.create(index);
		} catch (IOException e) {
			restStaus = RestStatus.EXPECTATION_FAILED.getStatus();
		}
		return restStaus;
	}

	@Override
	public int create(String index, String jsonString) {
		try {
			restStaus = indices.create(index,jsonString);
		} catch (IOException e) {
			restStaus = RestStatus.EXPECTATION_FAILED.getStatus();
		}
		return restStaus;
	}

	@Override
	public int create(String index, Map<String, Object> map) {
		try {
			restStaus = indices.create(index,map);
		} catch (IOException e) {
			restStaus = RestStatus.EXPECTATION_FAILED.getStatus();
		}
		return restStaus;
	}

	@Override
	public int delete(String index) {
		try {
			restStaus = indices.delete(index);
		} catch (IOException e) {
			restStaus = RestStatus.EXPECTATION_FAILED.getStatus();
		}
		return restStaus;
	}

	@Override
	public boolean isExist(String index) {
		return indices.isExists(index);
	}
}
