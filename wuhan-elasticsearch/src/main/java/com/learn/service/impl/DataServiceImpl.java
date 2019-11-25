package com.learn.service.impl;

import com.learn.common.ServiceResult;
import com.learn.mbg.mapper4.ViewMapper;
import com.learn.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author dshuyou
 * @date 2019/11/25 14:50
 */
@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private ViewMapper viewMapper;

    @Override
    public ServiceResult findOne(String table, String primaryKey) {
        Map<String,Object> map = viewMapper.findOne(table,primaryKey);
        if(map.isEmpty()){
            return ServiceResult.isNull();
        }else {
            return ServiceResult.success(map);
        }
    }
}
