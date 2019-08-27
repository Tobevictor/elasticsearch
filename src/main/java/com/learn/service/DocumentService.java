package com.learn.service;

import com.learn.common.elastic.common.result.ElasticResult;

/**
 * @Date 2019/8/14 17:18
 * @Created by dshuyou
 */
public interface DocumentService {

	ElasticResult fromMysql(String index);

	ElasticResult fromMysqlAsc(String index);

	ElasticResult fromOracle(String index);

	ElasticResult count(String index);

	ElasticResult delete(String index, String id);
}
