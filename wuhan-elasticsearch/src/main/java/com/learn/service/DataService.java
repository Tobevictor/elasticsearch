package com.learn.service;

import com.learn.common.ServiceResult;

/**
 * @author dshuyou
 * @date 2019/11/25 14:49
 */
public interface DataService {

    ServiceResult findOne(String table, String primaryKey, String id);
}
