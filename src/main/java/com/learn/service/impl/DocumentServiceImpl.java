package com.learn.service.impl;


import com.learn.common.elastic.common.result.CommonResult;
import com.learn.common.elastic.data.Document;
import com.learn.mapper.CommentMapper;
import com.learn.service.DocumentService;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

/**
 * @Date 2019/8/14 17:25
 * @Created by dshuyou
 */
@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private RestHighLevelClient client;

	private Document document;

	@PostConstruct
	public void init() {
		document = new Document(client);
	}

	@Override
	public CommonResult fromMysql(String index) {
		List<Map<String,Object>> list = commentMapper.getAll();
		CommonResult result;
		try {
			result = document.batchAscendingId(index,list);
			System.out.println(result.getData());
		} catch (IOException e) {
			result = CommonResult.failed(RestStatus.EXPECTATION_FAILED,"IOException",0);
		}
		return result;
	}

	@Override
	public CommonResult fromOracle(String index) {
    	return null;
	}

	@Override
	public CommonResult count(String index) {
		CommonResult result;
		try {
			result = document.count(index);
		} catch (IOException e) {
			result = CommonResult.failed(RestStatus.EXPECTATION_FAILED,"IOException",0);
		}
		return result;
	}

	@Override
	public CommonResult delete(String index,String id) {
		CommonResult result;
		try {
			result = document.delete(index,id);
		} catch (IOException e) {
			result = CommonResult.failed(RestStatus.EXPECTATION_FAILED,"IOException",0);
		}
		return result;
	}

}
