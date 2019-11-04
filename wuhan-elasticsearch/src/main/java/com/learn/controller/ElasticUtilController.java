package com.learn.controller;

import com.alibaba.fastjson.JSONObject;
import com.learn.common.ServiceResult;
import com.learn.elasticsearch.model.SourceEntity;

import com.learn.mbg.mapper3.ResourcedirectoryMapper;
import com.learn.model.Resourcedirectory;
import com.learn.service.ElasticsearchService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author dshuyou
 * @date 2019/9/16 9:47
 */
@Controller
@RequestMapping("/elastic/indice")
public class ElasticUtilController {
	@Resource
	private ElasticsearchService elasticsearchService;
	@Resource
	private ResourcedirectoryMapper resourcedirectoryMapper;

	@ApiOperation("构建默认索引")
	@RequestMapping(value = "/create",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult createIndex(@RequestParam String index){
		return elasticsearchService.createIndex(index);
	}

	@ApiOperation("构建自定义索引")
	@RequestMapping(value = "/createbycustom",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult createByCustom(@RequestParam String index,
								 @RequestParam String setting,
								 @RequestParam String mapping){
		return elasticsearchService.createIndex(index,setting,mapping);
	}

	@ApiOperation("更新映射")
	@RequestMapping(value = "/putmapping",method = RequestMethod.PUT)
	@ResponseBody
	public ServiceResult putMapping(@RequestParam String index,
								 @RequestParam String mapping){
		return elasticsearchService.putMapping(index,mapping);
	}

	@ApiOperation("更新配置")
	@RequestMapping(value = "/updateset",method = RequestMethod.PUT)
	@ResponseBody
	public ServiceResult updateSet(@RequestParam String index,
								 @RequestParam String setting){
		return elasticsearchService.updateSetting(index,setting);
	}

	@ApiOperation("索引列表")
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult getAllindex(){
		return elasticsearchService.getAllIndex();
	}

	@ApiOperation("查看索引配置")
	@RequestMapping(value = "/settings",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult getSettings(@RequestParam String index){
		return elasticsearchService.getSetting(index);
	}

	@ApiOperation("查看索引映射")
	@RequestMapping(value = "/mapping",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult getMapping(@RequestParam String index){
		return elasticsearchService.getMapping(index);
	}

	@ApiOperation("删除索引")
	@RequestMapping(value = "/delete",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult deleteIndex(@RequestParam String index){
		return elasticsearchService.deleteIndex(index);
	}

	@ApiOperation("全量索引")
	@RequestMapping(value = "/bulkindex",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult bulkIndex(@RequestParam String index,
								   @RequestParam String table){
		List<Resourcedirectory> list = resourcedirectoryMapper.findAll();
		List<SourceEntity> queries = new LinkedList<>();
		for (Resourcedirectory r : list){
			SourceEntity sourceEntity = new SourceEntity();
			sourceEntity.setSource(JSONObject.toJSONString(r));
			sourceEntity.setId(String.valueOf(r.getId()));
			queries.add(sourceEntity);
		}
		return elasticsearchService.bulkIndex(index,queries);
	}

	@ApiOperation("批量更新")
	@RequestMapping(value = "/bulkupdate",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult bulkUpdate(@RequestParam String index,
									@RequestParam String table,
									@RequestParam Date updateTime){
		List<Resourcedirectory> list = resourcedirectoryMapper.findAll();
		List<SourceEntity> queries = new LinkedList<>();
		for (Resourcedirectory r : list){
			SourceEntity sourceEntity = new SourceEntity();
			sourceEntity.setSource(JSONObject.toJSONString(r));
			sourceEntity.setId(String.valueOf(r.getId()));
			queries.add(sourceEntity);
		}
		return elasticsearchService.bulkUpdate(index,queries);
	}
}
