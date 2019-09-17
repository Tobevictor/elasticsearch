package com.learn.service.impl;
import com.learn.elasticsearch.Document;
import com.learn.elasticsearch.model.SourceEntity;
import com.learn.mapper.CommentMapper;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


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
		String index = "dshuyou";
		long start = System.currentTimeMillis();
		List<Map<String,Object>> list = mapper.findAll();
		List<SourceEntity> queries = new LinkedList<>();
		for (int i = 0;i<list.size();i++){
			SourceEntity sourceEntity = new SourceEntity();
			Map<String, Object> map = list.get(i);
			sourceEntity.setSource(map);
			sourceEntity.setId(String.valueOf(map.get("ids")));
			queries.add(sourceEntity);
		}
		list.clear();
		Document document = new Document(client);

		document.bulkIndex(index,queries);
		long end = System.currentTimeMillis();
		System.out.println((end-start)/1000 + "s");
	}

	@Test
	public void bulkIndex1() throws IOException {
		String index = "dshuyou4";
		long start = System.currentTimeMillis();
		List<Map<String,Object>> list = mapper.findAll();
		Document document = new Document(client);

		document.bulkIndex1(index,list);
		long end = System.currentTimeMillis();
		System.out.println((end-start)/1000 + "s");
	}

	@Test
	public void bulkIndex2() throws IOException {
		String index = "dshuyou3";
		long start = System.currentTimeMillis();
		List<Map<String,Object>> list = mapper.findAll();
		Document document = new Document(client);

		document.bulkIndex1(index,list);
		long end = System.currentTimeMillis();
		System.out.println((end-start)/1000 + "s");
	}
}