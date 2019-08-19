package com.learn.controller;

import com.learn.common.elastic.common.result.ElasticResult;
import com.learn.service.IndiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Date 2019/8/15 15:27
 * @Created by dshuyou
 */
@Api(tags = "IndiceController", description = "索引管理")
@Controller
@RequestMapping("/indice")
public class IndiceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndiceController.class);

	@Autowired
	private IndiceService indiceService;


	@ApiOperation("构建默认索引")
	@RequestMapping(value = "/create",method = RequestMethod.POST)
	@ResponseBody
	public ElasticResult create(@RequestParam String index){
		return indiceService.create(index);
	}

	@ApiOperation("删除索引")
	@RequestMapping(value = "/delete",method = RequestMethod.GET)
	@ResponseBody
	public ElasticResult delete(@RequestParam String index){
		return indiceService.delete(index);
	}

	@ApiOperation("索引是否存在")
	@RequestMapping(value = "/isexist",method = RequestMethod.GET)
	@ResponseBody
	public ElasticResult isExist(@RequestParam String index){
		return indiceService.isExist(index);
	}
}
