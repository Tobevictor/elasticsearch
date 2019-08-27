package com.learn.service;

import com.learn.common.elastic.common.result.ElasticResult;
import com.learn.common.elastic.common.result.ServiceMultiResult;
import com.learn.model.EarthquakeBucketDTO;
import com.learn.model.EarthquakeTemplate;
import com.learn.model.MapSearch;
import com.learn.model.RentSearch;

import java.util.List;

/**
 * @Date 2019/8/20 10:48
 * @Created by dshuyou
 */
public interface EarthquakeService {

	boolean create(String index);


	boolean update(String index, String jsonString);


	boolean delete(String index);


	ElasticResult fromMysql(String index);


	ElasticResult fromMysqlAsc(String index);


	ElasticResult<Long> query(RentSearch rentSearch);


	ElasticResult<List<String>> suggest(String prefix);


	ElasticResult<Long> aggregateDistrictEarthquake(String cityEnName, String regionEnName, String district);


	ServiceMultiResult<EarthquakeBucketDTO> mapAggregate(String cityEnName);


	ServiceMultiResult<Long> mapQuery(String cityEnName, String orderBy,
									  String orderDirection, int start, int size);

	ServiceMultiResult<Long> mapQuery(MapSearch mapSearch);
}
