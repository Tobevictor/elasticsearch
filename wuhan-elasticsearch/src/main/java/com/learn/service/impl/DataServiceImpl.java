package com.learn.service.impl;

import com.learn.common.ServiceResult;
import com.learn.mbg.mapper4.ViewMapper;
import com.learn.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    public ServiceResult findOne(String table, String primaryKey, String id) {
        Map<String,Object> params = new HashMap<>();
        params.put("table",table);
        params.put("pk",primaryKey);
        params.put("id",id);
        Map<String,Object> res = viewMapper.selectWithId(params);
        if(res.isEmpty()){
            return ServiceResult.isNull();
        }else {
            return ServiceResult.success(res);
        }
    }
}
