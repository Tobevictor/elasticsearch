package com.learn.common.elasticsearch.model;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @Date 2019/8/26 16:25
 * @Created by dshuyou
 */
public class IndexQueryTest {

	@Test
	public void getId() {
	}

	@Test
	public void setId() {
	}

	@Test
	public void getObject() {
	}

	@Test
	public void setObject() {
	}

	@Test
	public void getVersion() {
	}

	@Test
	public void setVersion() {
	}

	@Test
	public void getIndexName() {
	}

	@Test
	public void setIndexName() {
	}

	@Test
	public void getSource() {
		Map<String,String> map = new HashMap<>();
		map.put("1","2");
		IndexQuery indexQuery = new IndexQuery();
		indexQuery.setObject(map);
		if(indexQuery.getObject()instanceof String){
			System.out.println("String");
			System.out.println(indexQuery.getObject());
			System.out.println(String.valueOf(indexQuery.getObject()));
		}else if (indexQuery.getObject() instanceof Map) {
			System.out.println("map");
			System.out.println(indexQuery.getObject());
			System.out.println((Map)indexQuery.getObject());
			Map map1 = (Map) indexQuery.getObject();
			System.out.println(map1);
		} else if (indexQuery.getObject() instanceof XContentBuilder) {
			System.out.println("XContentBuilder");
			System.out.println(indexQuery.getObject());
			System.out.println(String.valueOf(indexQuery.getObject()));
		}
	}


}