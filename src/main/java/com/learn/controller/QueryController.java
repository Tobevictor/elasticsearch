package com.learn.controller;

import com.learn.common.elastic.common.result.ElasticResult;
import com.learn.common.elastic.condition.FullTextCondition;
import com.learn.common.elastic.condition.QueryCondition;
import com.learn.common.elastic.condition.TermLevelCondition;
import com.learn.common.elastic.query.search.SearchTypeEnum;
import com.learn.service.QueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Date 2019/8/15 15:27
 * @Created by dshuyou
 */
@Api(tags = "QueryController", description = "查询管理")
@Controller
@RequestMapping("/query")
public class QueryController {
	private static Logger LOGGER = LoggerFactory.getLogger(QueryController.class);

	@Autowired
	private QueryService queryService;

	@ApiOperation("simple查询（默认）")
	@RequestMapping(value = "/matchAll/{index}",method = RequestMethod.POST)
	@ResponseBody
	public ElasticResult simpleQuery(@PathVariable String index, @RequestBody FullTextCondition condition){
		return queryService.simpleQuery(index,condition);
	}

	@ApiOperation("全文查询")
	@RequestMapping(value = "/fulltextQuery/{type}/{index}",method = RequestMethod.POST)
	@ResponseBody
	public ElasticResult simpleQuery(@PathVariable String type, @PathVariable String index, @RequestBody FullTextCondition condition){
		return queryService.simpleQuery(type,index,condition);
	}

	@ApiOperation("termlevel查询")
	@RequestMapping(value = "/termlevelQuery/{type}/{index}",method = RequestMethod.POST)
	@ResponseBody
	public ElasticResult termlevelQuery(@PathVariable String type, @PathVariable String index, @RequestBody TermLevelCondition condition){
		return queryService.simpleQuery(type,index,condition);
	}
}
