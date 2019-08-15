package com.learn.service.impl;


import com.learn.common.elastic.common.ElasticSearchClient;
import com.learn.common.elastic.common.result.CommonResult;
import com.learn.common.elastic.data.Document;
import com.learn.mapper.CommentMapper;
import com.learn.service.DocumentService;
import org.elasticsearch.client.RestHighLevelClient;
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
	public void fromMysql(String index) {
		List<Map<String,Object>> result = commentMapper.getAll();
		try {
			CommonResult result1 = document.batchAscendingId(index,result);
			System.out.println(result1.getData());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void fromOracle(String index) {

	}

	@Override
	public int count(String index) {
		return 0;
	}

	@Override
	public boolean delete(String index) {
		return false;
	}

}
