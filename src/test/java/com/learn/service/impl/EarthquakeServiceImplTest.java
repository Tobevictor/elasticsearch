package com.learn.service.impl;

import com.learn.mapper.CommentMapper;
import com.learn.model.EarthquakeTemplate;
import com.learn.service.EarthquakeService;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @Date 2019/8/20 13:39
 * @Created by dshuyou
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EarthquakeServiceImplTest {

	@Autowired
	private RestHighLevelClient client;
	@Autowired
	private EarthquakeService earthquakeService;
	@Autowired
	private CommentMapper commentMapper;

	@Test
	public void create() {
		String index = "earthquake";
		earthquakeService.create(index);

	}

	@Test
	public void update() {
		String jsonString = "{\n" +
				"  \"properties\": {\n" +
				"    \"message\": {\n" +
				"      \"type\": \"text\"\n" +
				"    }\n" +
				"  }\n" +
				"}";
		String index = "earthquake";
		earthquakeService.update(index,jsonString);

	}

	@Test
	public void querybyTime(){
		Map<String,Object> template = commentMapper.findbytime();
		Iterator<Map.Entry<String, Object>> it = template.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
		}

		System.out.println(template.toString());
	}

	@Test
	public void fromMysql() {
	}

	@Test
	public void fromMysqlAsc() {
	}

	@Test
	public void query() {
	}

	@Test
	public void suggest() {
	}

	@Test
	public void aggregateDistrictEarthquake() {
	}

	@Test
	public void mapAggregate() {
	}

	@Test
	public void mapQuery() {
	}

	@Test
	public void mapQuery1() {
	}
}