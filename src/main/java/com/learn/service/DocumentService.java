package com.learn.service;

/**
 * @Date 2019/8/14 17:18
 * @Created by dshuyou
 */
public interface DocumentService {

	void fromMysql(String index);

	void fromOracle(String index);

	int count(String index);

	boolean delete(String index);
}
