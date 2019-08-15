package com.learn.service;

import java.util.Map;

/**
 * @Date 2019/8/14 19:07
 * @Created by dshuyou
 */
public interface IndiceService {

	Boolean create(String index);

	Boolean create(String index,String jsonString);

	Boolean create(String index, Map<String,Object>map);

	Boolean delete(String index);

	Boolean isExist(String index);
}
