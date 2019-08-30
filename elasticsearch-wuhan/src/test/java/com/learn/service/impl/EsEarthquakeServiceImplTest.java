package com.learn.service.impl;
import com.learn.elasticsearch.Document;
import com.learn.mapper.CommentMapper;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


/**
 * @Date 2019/8/21 18:14
 * @Created by dshuyou
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsEarthquakeServiceImplTest {
	@Autowired
	private RestHighLevelClient client;
	@Autowired
	private CommentMapper mapper;


	@Test
	public void bulkIndex() throws IOException {
		String index = "comment";
		Document document = new Document(client);
		document.bulkIndex(index,mapper.findAll());
	}

}