package com.learn.service.impl;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @Date 2019/8/21 18:14
 * @Created by dshuyou
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsEarthquakeServiceImplTest {
	@Autowired
	private RestHighLevelClient client;

}