package com.learn.controller;

import com.learn.common.ServiceResult;
import com.learn.component.HotWord;
import com.learn.service.DataService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author dshuyou
 * @date 2019/11/25 14:57
 */
@Controller
@RequestMapping("/data")
@CrossOrigin
public class DataSourceController {
    @Autowired
    private DataService dataService;

    @ApiOperation("点击查询")
    @RequestMapping(value = "/selectone",method = RequestMethod.GET)
    @ResponseBody
    @HotWord
    public ServiceResult findOne(@RequestParam String table,
                                 @RequestParam String pk,
                                 @RequestParam String id){

        return dataService.findOne(table,pk,id);
    }
}
