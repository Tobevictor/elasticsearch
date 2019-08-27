package com.learn.common.elastic.data;

import com.learn.common.elastic.common.result.ElasticResult;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2019/8/1 15:53
 * @Created by dshuyou
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class IndicesTest {
	@Autowired
	private RestHighLevelClient client;
	private Indices indices;
	@Before
	public void init(){
		indices = new Indices(client);
	}

	@Test
	public void create() throws IOException {
		Map<String, Object> message = new HashMap<>();
		message.put("type", "text");
		Map<String, Object> properties = new HashMap<>();
		properties.put("message", message);
		Map<String, Object> mapping = new HashMap<>();
		mapping.put("properties", properties);
		indices.create("document3",mapping);
	}

	@Test
	public void createByXcontent() throws IOException {
		XContentBuilder builder = XContentFactory.jsonBuilder();
		builder.startObject();
		{
			builder.startObject("properties");
			{
				builder.startObject("message");
				{
					builder.field("type", "text");
				}
				builder.endObject();
			}
			builder.endObject();
		}
		builder.endObject();
		indices.createByXcontent("test1",builder);
	}

	@Test
	public void create1() throws IOException {
		String jsonString = "{\n" +
				"  \"properties\": {\n" +
				"    \"message\": {\n" +
				"      \"type\": \"text\"\n" +
				"    }\n" +
				"  }\n" +
				"}";
		indices.create("document",jsonString);
	}

	@Test
	public void createAsync() {
	}

	@Test
	public void delete() throws IOException {
		indices.delete("test1");
	}

	@Test
	public void isExists() {
		System.out.println(	indices.isExists("fenci"));
	}

	@Test
	public void update() {
	}

	@Test
	public void get() throws IOException {
		Object result = indices.get("fenci");
		System.out.println(result);
	}

	@Test
	public void refresh() {
	}

	@Test
	public void clearCache() {
	}

	@Test
	public void flush() {
	}
}