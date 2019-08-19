package com.learn.service.impl;


import com.learn.common.elastic.common.result.ElasticResult;
import com.learn.common.elastic.data.Document;
import com.learn.mapper.CommentMapper;
import com.learn.service.DocumentService;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentServiceImpl.class);

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
	public ElasticResult fromMysql(String index) {
		List<Map<String,Object>> list = commentMapper.getAll();
		ElasticResult result;
		try {
			long count = document.batchAscendingId(index,list);
			result = ElasticResult.success("batch intert success",count);
		} catch (IOException e) {
			LOGGER.error("IOException");
			result = ElasticResult.failed(RestStatus.CONFLICT.getStatus(),"batch intert failed,error:"+e,0);
		}
		return result;
	}

	@Override
	public ElasticResult fromOracle(String index) {
    	return null;
	}

	@Override
	public ElasticResult count(String index) {
		ElasticResult result = null;
		try {
			long count = document.count(index);
			result = ElasticResult.success("count success",count);
		} catch (IOException e) {
			LOGGER.error("IOException");
			result = ElasticResult.failed(RestStatus.CONFLICT.getStatus(),"counting failed,error:"+e,0);
		}
		return result;
	}

	@Override
	public ElasticResult delete(String index, String id) {
		ElasticResult result = null;
		try {
			document.delete(index,id);
			result = ElasticResult.success("delete success",id);
		} catch (IOException e) {
			LOGGER.error("IOException");
			result = ElasticResult.failed(RestStatus.CONFLICT.getStatus(),"delete failed,error:"+e,id);
		}
		return result;
	}

}
