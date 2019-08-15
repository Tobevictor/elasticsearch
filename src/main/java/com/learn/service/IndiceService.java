package com.learn.service;

import java.util.Map;

/**
 * @Date 2019/8/14 19:07
 * @Created by dshuyou
 */
public interface IndiceService {

	int create(String index);

	int create(String index,String jsonString);

	int create(String index, Map<String,Object>map);

	int delete(String index);

	boolean isExist(String index);
}
