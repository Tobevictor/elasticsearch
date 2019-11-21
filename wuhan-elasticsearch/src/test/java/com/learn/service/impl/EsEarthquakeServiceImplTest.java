package com.learn.service.impl;
import com.learn.elasticsearch.Document;
import com.learn.elasticsearch.model.SourceEntity;
import com.learn.mbg.mapper1.CommentMapper;
import com.learn.service.ElasticsearchService;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
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
	@Autowired
	private ElasticsearchService elasticsearchService;

	@Test
	public void bulkIndex() throws InterruptedException {
		String index = "dshuyou2";
		long start = System.currentTimeMillis();
		long findStart = System.currentTimeMillis();
		List<Map<String,Object>> list = mapper.findAll();
		long findEnd = System.currentTimeMillis();
		System.out.println(findEnd-findStart + "ms");
		List<SourceEntity> queries = new ArrayList<>();
		for (Map<String,Object> map : list){
			SourceEntity sourceEntity = new SourceEntity();
			sourceEntity.setSource(map);
			sourceEntity.setId(String.valueOf(map.get("ids")));
			queries.add(sourceEntity);
		}
		list.clear();
		Document document = new Document(client);

		document.asycBulkIndex(index,queries);
		long end = System.currentTimeMillis();
		System.out.println((end-start)/1000 + "s");
	}

	@Test
	public void extendWord(){
		String[] field = new String[]{"content.content_fullpinyin", "content.content_prefixpinyin", "content.content_text"};
		String keyword = "像我";
		int size = 10;
		String index = "asxdasd";
		List<String> list = null;
		list = (List<String>) elasticsearchService.extendWord(index ,keyword, size, field).getResult();
		if(list == null){return;}
		System.out.println(list.size());
		for (String s : list) {
			System.out.println(s);
		}
	}
}