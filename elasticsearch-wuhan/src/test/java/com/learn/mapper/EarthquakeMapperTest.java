package com.learn.mapper;

import com.alibaba.fastjson.JSON;
import com.learn.elasticsearch.Document;
import com.learn.elasticsearch.EsClientInit;
import com.learn.model.Earthquake;
import com.learn.util.JsonUtils;
import com.vividsolutions.jts.geom.Geometry;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.geotools.geojson.GeoJSON;
import org.geotools.geojson.geom.GeometryJSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
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
	@Autowired
	private CommentMapper commentMapper;

	@Test
	public void findComment(){
		List<Map<String,Object>> list =  commentMapper.findAll();
		for (Map<String,Object> map : list){
			Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
			while (it.hasNext()){
				Map.Entry<String, Object> entry = it.next();
				System.out.println(entry.getKey() + "" + entry.getValue());
			}
		}
	}

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

	@Test
	public void findAll() throws IOException {
		RestHighLevelClient client = EsClientInit.getInstance().getClient();
		List<Map<String,Object>> list =  earthquakeMapper.findAll();

		Document document = new Document(client);
		System.out.println(client.ping(RequestOptions.DEFAULT));
		try {
			long count = document.bulkIndexAsc("earthquake",list);
			System.out.println("共计导入" + count + "条数据");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}