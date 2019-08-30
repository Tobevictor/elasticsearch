package com.learn.elasticsearch.query;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Date 2019/8/28 11:10
 * @Created by dshuyou
 */
public class BoolQueryTest {

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
	public void executeBoolQuery() {
	}
}