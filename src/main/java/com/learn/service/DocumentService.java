package com.learn.service;

import com.learn.common.elastic.common.result.CommonResult;

/**
 * @Date 2019/8/14 17:18
 * @Created by dshuyou
 */
public interface DocumentService {

	CommonResult fromMysql(String index);

	CommonResult fromOracle(String index);

	CommonResult count(String index);

	CommonResult delete(String index,String id);
}
