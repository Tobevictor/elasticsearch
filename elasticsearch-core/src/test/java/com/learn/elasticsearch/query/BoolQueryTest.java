package com.learn.elasticsearch.query;

import com.learn.elasticsearch.EsClientInit;
import com.learn.elasticsearch.query.condition.BaseCondition;
import com.learn.elasticsearch.query.condition.FullTextCondition;
import com.learn.elasticsearch.query.condition.TermsLevelCondition;
import com.learn.elasticsearch.query.query_enum.FulltextEnum;
import com.learn.elasticsearch.query.query_enum.GeoEnum;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * @Date 2019/8/28 11:10
 * @Created by dshuyou
 */
public class BoolQueryTest {

	private RestHighLevelClient client;
	private GeoQuery geoQuery;
	private String index;
	@Before
	public void setUp(){
		client = EsClientInit.getInstance().getClient();
		index = "comment";
	}

	@Test
	public void executeQuery() {
		List<Integer> list = get();
		for (Integer i :list){
			System.out.println(i);
		}
	}

	private List<Integer> get(){
		List<Integer> list = new ArrayList<>();
		int a = 1;
		if(a == 2){
			list.add(a);
			return list;
		}else {
			return Collections.emptyList();
		}
	}


	@Test
	public void executeBoolQuery() throws IOException {
		BoolQuery boolQuery = new BoolQuery(index,client);
		Map<String, BaseCondition> map = new HashMap<>();
		map.put("matchPhraseQuery",new FullTextCondition("content","他"));
		TermsLevelCondition condition = new TermsLevelCondition();
		condition.setField("content");
		condition.setValues(new String[]{"我","和","你"});
		map.put("termsQuery",condition);
		long start = System.currentTimeMillis();
		List<String> list = boolQuery.executeBoolQuery(map);
		long end = System.currentTimeMillis();
		System.out.println((end-start)/100 + "ms");
		for (String s :list){
			System.out.println(s);
		}
		System.out.println(list.size());
	}
}