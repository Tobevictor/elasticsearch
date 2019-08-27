package com.learn.service;

import com.learn.common.elastic.common.result.ElasticResult;

import java.util.Map;

/**
 * @Date 2019/8/14 19:07
 * @Created by dshuyou
 */
public interface IndiceService {

	ElasticResult create(String index);

	ElasticResult create(String index,String jsonString);

	ElasticResult create(String index, Map<String,Object>map);

	ElasticResult delete(String index);

	ElasticResult isExist(String index);
}
