package com.learn.elasticsearch.query.condition;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author dshuyou
 * @Date 2019/9/3 21:42
 */
public class BaseConditionTest {

	@Test
	public void getFrom() {
	}

	@Test
	public void setFrom() {
	}

	@Test
	public void getSize() {
	}

	@Test
	public void setSize() {
	}

	@Test
	public void textEquals(){
		FullTextCondition fullTextCondition = new FullTextCondition();
		GeoCondition geoCondition = new GeoCondition();
		TermsLevelCondition termsLevelCondition = new TermsLevelCondition();
		System.out.println(fullTextCondition instanceof BaseCondition);

		System.out.println(BaseCondition.class.getClass().equals(fullTextCondition.getClass()));
		System.out.println(fullTextCondition.getClass().equals(fullTextCondition.getClass()));

	}
}