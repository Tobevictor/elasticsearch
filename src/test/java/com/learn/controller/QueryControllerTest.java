package com.learn.controller;

import com.learn.common.elastic.common.result.ElasticResult;
import com.learn.common.elastic.condition.FullTextCondition;
import com.learn.common.elastic.condition.TermLevelCondition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Date 2019/8/19 12:19
 * @Created by dshuyou
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class QueryControllerTest {

	@Autowired
	private QueryController queryController;

	@Test
	public void simpleQuery() {
		String index = "document";
		FullTextCondition condition = new FullTextCondition();
		condition.setField("username");
		condition.setValue("我");
		condition.setFrom(0);
		condition.setSize(10000);
		ElasticResult result = queryController.simpleQuery(index,condition);
		List<String> list = (List<String>) result.getData();
		for (String s :list){
			System.out.println(s);
		}
	}

	@Test
	public void termQuery() {

		String index = "comment";
		TermLevelCondition condition = new TermLevelCondition();
		condition.setField("username");
		condition.setValue("我");
		condition.setFrom(0);
		condition.setSize(1000);
		ElasticResult result = queryController.termlevelQuery("termSearch",index,condition);
		List<String> list = (List<String>) result.getData();
		for (String s :list){
			System.out.println(s);
		}
	}
}
