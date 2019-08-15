package com.learn.common.elastic.query.search;

import com.learn.common.elastic.condition.FullTextCondition;
import com.learn.common.elastic.condition.QueryCondition;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Date 2019/7/30 17:06
 * @Created by dshuyou
 */
public class BasicSearchTest {

	@Autowired
	private RestHighLevelClient client;

	@Test
	public void executeQuery() throws Throwable {

		SearchTypeEnum typeEnum = SearchTypeEnum.matchPhrasePrefixSearch;

		QueryCondition condition = new FullTextCondition(0,500);
		((FullTextCondition) condition).setField("content");
		//((TermLevelCondition) condition).setValue("评论时间");
		//((TermLevelCondition) condition).setGte("1");
		//((TermLevelCondition) condition).setLte("3");
		String[] s = new String[]{"真","的","我"};
		String ss = "我爱你 ";
		String[] ids = new String[]{"x1F4g2wB8djZHRb7DRaH","B1F4g2wB8djZHRb7DReH"};
		((FullTextCondition) condition).setValue(ss);
		//((TermLevelCondition) condition).setIds(ids);
		SimpleSearch search = new SimpleSearch("document",client,typeEnum);
		search.executeQuery(condition);

	}
}