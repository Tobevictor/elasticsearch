package com.learn.elasticsearch.query;

import com.learn.elasticsearch.EsClientInit;
import com.learn.elasticsearch.query.condition.FullTextCondition;
import com.learn.elasticsearch.query.query_enum.FulltextEnum;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;


/**
 * @Date 2019/8/28 11:10
 * @Created by dshuyou
 */
public class FulltextQueryTest {

	private RestHighLevelClient client;
	private FulltextQuery fulltextQuery;
	@Before
	public void setUp(){
		client = EsClientInit.getInstance().getClient();
		String index = "comment";
		fulltextQuery = new FulltextQuery(index,client, FulltextEnum.matchPhraseQuery);
	}

	@Test
	public void executeQuery() throws IOException {

		long start = System.currentTimeMillis();
		/*for (int i = 0;i<100;i++) {
			query();
		}*/
		query();
		long end = System.currentTimeMillis();
		System.out.println((end-start)/100 + "ms");
		//System.out.println((end-start) + "ms");

	}

	public void query() throws IOException {
		FullTextCondition condition = new FullTextCondition();
		condition.setField("content");
		condition.setValue("他");
		condition.setFrom(0);
		condition.setSize(1000);
		List<String> list = fulltextQuery.executeQuery(condition);
		for (String s :list){
			System.out.println(s);
		}
	}

	@Test
	public void executeBoolQuery() {
	}

	@Test
	public void queryBuilder() {
	}
}