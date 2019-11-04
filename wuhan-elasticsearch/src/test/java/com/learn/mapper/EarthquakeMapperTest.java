package com.learn.mapper;

import com.learn.elasticsearch.Document;
import com.learn.elasticsearch.EsClientInit;;
import com.learn.mbg.mapper1.EarthquakeMapper;
import com.learn.mbg.model.Earthquake;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Date 2019/8/29 10:56
 * @Created by dshuyou
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EarthquakeMapperTest {

	@Autowired
	private EarthquakeMapper earthquakeMapper;

	@Test
	public void findOne() throws IOException {
		RestHighLevelClient client = EsClientInit.getInstance().getClient();
		Document document = new Document(client);
		Earthquake earthquake =  earthquakeMapper.findOne1();
		System.out.println(earthquake.toString());
		String geometry = earthquake.getPoint();
		System.out.println(geometry);
		document.index("earthquake",earthquake.getPoint());

	}

	@Test
	public void putOne() throws IOException {
		RestHighLevelClient client = EsClientInit.getInstance().getClient();
		Document document = new Document(client);
		Map<String,Object> map = earthquakeMapper.findOne();
		System.out.println(map.get("point"));


		String json = "{\"type\": \"Point\", \"coordinates\": [-118.7435, 37.4333333]}";
		String js = "{\"point\":"+json+"}";
		map.put("point",json);
		document.index("earthquake","4",map);
	}

	@Test
	public void findEarthquake(){
		List<Map<String,Object>> list =  earthquakeMapper.findAll();
		for (Map<String,Object> map : list){
			System.out.println(map);
			String point = (String) map.get("point");
			System.out.println(point);

		}
	}
}