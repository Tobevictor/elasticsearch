package com.learn.common.elastic.data;

import com.learn.common.elastic.common.ElasticSearchClient;
import com.learn.common.elastic.common.result.CommonResult;
import com.learn.common.elastic.util.Comment;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2019/7/30 17:58
 * @Created by dshuyou
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DocumentTest {

	@Autowired
	private RestHighLevelClient client;
	private Document document;

	@Before
	public void init(){
		document = new Document(client);
	}


	@Test
	public void insert() throws IOException {
		String jsonString = "{" +
				"\"user\":\"kimchy\"," +
				"\"postDate\":\"2013-01-30\"," +
				"\"message\":\"trying out Elasticsearch\"," +
				"\"id\":\"2\"," +
				"\"name\":\"mmmmmm\"" +
				"}";
		document.insert("document1",jsonString);
	}

	@Test
	public void insert1() throws IOException {
		Map<String,Object> map = new HashMap<>();
		map.put("id","");
		map.put("username","");
		map.put("address","");
		map.put("data","");
		map.put("like","");
		map.put("content","");
		document.insert("document",map);
	}

	@Test
	public void insert2() {
	}

	@Test
	public void insert3() {
	}

	@Test
	public void insertAsync() {
	}

	@Test
	public void delete() {
	}

	@Test
	public void count() {
	}

	@Test
	public void isIdExists() {
	}

	@Test
	public void get() throws IOException {
		document.get("kibana_sample_data_ecommerce","4D_ACmwBzJUu-ZmXtkmS");
	}

	@Test
	public void getFieldsValues() {
	}

	@Test
	public void multiGet() {
	}

	@Test
	public void update() {
	}

	@Test
	public void generateRequests() {
	}

	@Test
	public void batchInsert() throws IOException {
		System.out.println("***开始导入***");

		List<Map<String,Object>> data = new ArrayList<>();
		List<Map<String, Object>> list = Comment.toJson("D:\\test.csv");
		for (int i = 0;i < 100;i++) {
			data.addAll(list);
		}
		System.out.println(data.size());
		long start = System.currentTimeMillis();
		CommonResult result = document.batchAscendingId("document2",list);
		System.out.println(result.getData());

		long end = System.currentTimeMillis();
		System.out.println("***导入完成***");
		System.out.println("导入时间："+(end-start)+"ms");

	}

	@Test
	public void batchAscendingId() {
	}
}