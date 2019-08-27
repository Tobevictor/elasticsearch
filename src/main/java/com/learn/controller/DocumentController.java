package com.learn.controller;

import com.learn.common.elastic.common.result.ElasticResult;
import com.learn.service.DocumentService;
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
@Api(tags = "DocumentController", description = "文档管理")
@Controller
@RequestMapping("/document")
public class DocumentController {
	private static Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);

	@Autowired
	private DocumentService documentService;

	@ApiOperation("从MYSQL批量导入数据(全量)")
	@RequestMapping(value = "/fromMysql",method = RequestMethod.POST)
	@ResponseBody
	public ElasticResult fromMysql(@RequestParam String index){
		return documentService.fromMysql(index);
	}

	@ApiOperation("从MYSQL批量导入数据(增量)")
	@RequestMapping(value = "/fromMysqlAsc",method = RequestMethod.POST)
	@ResponseBody
	public ElasticResult fromMysqlAsc(@RequestParam String index){
		return documentService.fromMysqlAsc(index);
	}

	@ApiOperation("获取文档数量")
	@RequestMapping(value = "/count",method = RequestMethod.GET)
	@ResponseBody
	public ElasticResult count(@RequestParam String index){
		return documentService.count(index);
	}

	@ApiOperation("根据id删除文档")
	@RequestMapping(value = "/delete",method = RequestMethod.GET)
	@ResponseBody
	public ElasticResult delete(@RequestParam String index,
								@RequestParam String id){
		return documentService.delete(index,id);
	}
}
