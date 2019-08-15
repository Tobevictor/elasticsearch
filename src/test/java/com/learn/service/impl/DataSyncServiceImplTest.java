package com.learn.service.impl;

import com.learn.common.elastic.common.ElasticSearchClient;
import com.learn.common.elastic.data.Document;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Date 2019/8/14 18:20
 * @Created by dshuyou
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DataSyncServiceImplTest {
	@Autowired
	private DocumentServiceImpl dataSyncService;
	@Autowired
	private RestHighLevelClient client;

	private Document document;

	@Before
	public void before(){
		document = new Document(client);
	}

	@Test
	public void fromMysql() {
		dataSyncService.fromMysql("comment");
	}

	@Test
	public void fromOracle() {
	}
}