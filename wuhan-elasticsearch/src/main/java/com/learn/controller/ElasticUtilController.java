package com.learn.controller;

import com.alibaba.fastjson.JSONObject;
import com.learn.common.ServiceResult;
import com.learn.elasticsearch.model.SourceEntity;
import com.learn.mbg.mapper4.ViewMapper;
import com.learn.model.IndexSource;
import com.learn.service.ElasticsearchService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin
public class ElasticUtilController {
	private static final Logger logger = LoggerFactory.getLogger(ElasticUtilController.class);
	@Autowired
	private ElasticsearchService elasticsearchService;
	@Resource
	private ViewMapper viewMapper;

	@ApiOperation("构建默认索引")
	@RequestMapping(value = "/create",method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query")
	public ServiceResult createIndex(@RequestParam String index){
		return elasticsearchService.createIndex(index);
	}

	@ApiOperation("构建自定义索引")
	@RequestMapping(value = "/createbycustom",method = RequestMethod.POST)
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query"),
						@ApiImplicitParam(name = "setting",value = "配置",required = true,dataType = "String",paramType = "query"),
						@ApiImplicitParam(name = "mapping",value = "映射",required = true,dataType = "String",paramType = "query")})
	public ServiceResult createByCustom(@RequestParam String index,
								 @RequestParam String setting,
								 @RequestParam String mapping){
		return elasticsearchService.createIndex(index,setting,mapping);
	}

	@ApiOperation("更新映射")
	@RequestMapping(value = "/putmapping",method = RequestMethod.PUT)
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query"),
						@ApiImplicitParam(name = "mapping",value = "映射",required = true,dataType = "String",paramType = "query")})
	public ServiceResult putMapping(@RequestParam String index,
								 @RequestParam String mapping){
		return elasticsearchService.putMapping(index,mapping);
	}

	@ApiOperation("更新配置")
	@RequestMapping(value = "/updateset",method = RequestMethod.PUT)
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query"),
						@ApiImplicitParam(name = "setting",value = "配置",required = true,dataType = "String",paramType = "query")})
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
	@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query")
	public ServiceResult getSettings(@RequestParam String index){
		return elasticsearchService.getSetting(index);
	}

	@ApiOperation("查看索引映射")
	@RequestMapping(value = "/mapping",method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query")
	public ServiceResult getMapping(@RequestParam String index){
		return elasticsearchService.getMapping(index);
	}

	@ApiOperation("删除索引")
	@RequestMapping(value = "/delete",method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query")
	public ServiceResult deleteIndex(@RequestParam String index){
		return elasticsearchService.deleteIndex(index);
	}

	@ApiOperation("全量索引")
	@RequestMapping(value = "/bulkindex",method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query"),
						@ApiImplicitParam(name = "table",value = "元数据表名称",required = true,dataType = "String",paramType = "query"),
						@ApiImplicitParam(name = "idColumn",value = "主键",required = true,dataType = "String",paramType = "query")})
	public ServiceResult bulkIndex(@RequestParam String index,
								   @RequestParam String table,
								   @RequestParam String idColumn){
		List<Map<String, Object>> list = viewMapper.select(table);
		List<SourceEntity> bulk = new ArrayList<>();
		for (Map<String, Object> r : list){
			SourceEntity sourceEntity = new SourceEntity();
			sourceEntity.setSource(JSONObject.toJSONString(r));
			sourceEntity.setId(String.valueOf(r.get(idColumn)));
			bulk.add(sourceEntity);
		}
		return elasticsearchService.bulkIndex(index,bulk);
	}

	@ApiOperation("全量索引1")
	@RequestMapping(value = "/bulkindex1",method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query"),
						@ApiImplicitParam(name = "table",value = "元数据表名称",required = true,dataType = "String",paramType = "query")})
	public ServiceResult bulkIndex1(@RequestParam String index,
								   @RequestParam String table){
		List<IndexSource> list = viewMapper.selectIndex(table);
		List<SourceEntity> bulk = new ArrayList<>();
		for (IndexSource r : list){
			SourceEntity sourceEntity = new SourceEntity();
			sourceEntity.setSource(JSONObject.toJSONString(r));
			sourceEntity.setId(r.getId());
			bulk.add(sourceEntity);
		}
		return elasticsearchService.bulkIndex(index,bulk);
	}

	@ApiOperation("全量异步索引")
	@RequestMapping(value = "/asycindex",method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "index",value = "索引名称",required = true,dataType = "String",paramType = "query"),
						@ApiImplicitParam(name = "table",value = "元数据表名称",required = true,dataType = "String",paramType = "query"),
						@ApiImplicitParam(name = "idColumn",value = "主键",required = true,dataType = "String",paramType = "query")})
	public ServiceResult asycIndex(@RequestParam String index,
								   @RequestParam String table,
								   @RequestParam String idColumn){
		List<Map<String, Object>> list = viewMapper.select(table);
		List<SourceEntity> bulk = new ArrayList<>();
		for (Map<String, Object> r : list){
			SourceEntity sourceEntity = new SourceEntity();
			sourceEntity.setSource(JSONObject.toJSONString(r));
			sourceEntity.setId(String.valueOf(r.get(idColumn)));
			bulk.add(sourceEntity);
		}
		return elasticsearchService.asycBulkIndex(index,bulk);
	}

	/*@ApiOperation("采矿")
	@RequestMapping(value = "/caikuang",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult caikuang(@RequestParam String index,
								   @RequestParam String table,
								   @RequestParam String idColumn){
		List<Map<String, Object>> list;
		if("hdfw".equals(table)){
			list = viewMapper.ckHdfw();
		}else if("sqdj".equals(table)){
			list = viewMapper.ckSqdj();
		}else {
			list = viewMapper.ckBjinfo();
		}
		if(list == null || list.isEmpty()){
			return ServiceResult.isNull();
		}
		List<SourceEntity> bulk = new ArrayList<>();
		for (Map<String, Object> r : list){
			SourceEntity sourceEntity = new SourceEntity();
			sourceEntity.setSource(JSONObject.toJSONString(r));
			sourceEntity.setId(String.valueOf(r.get(idColumn)));
			bulk.add(sourceEntity);
		}
		return elasticsearchService.bulkIndex(index,bulk);
	}

	@ApiOperation("探矿")
	@RequestMapping(value = "/tankuang",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult tankuang(@RequestParam String index,
							  @RequestParam String table,
							  @RequestParam String idColumn){
		List<Map<String, Object>> list = null;
		if("hdfw".equals(table)){
			list = viewMapper.tkBjinfo();
		}
		if(list == null || list.isEmpty()){
			return ServiceResult.isNull();
		}
		List<SourceEntity> bulk = new ArrayList<>();
		for (Map<String, Object> r : list){
			SourceEntity sourceEntity = new SourceEntity();
			sourceEntity.setSource(JSONObject.toJSONString(r));
			sourceEntity.setId(String.valueOf(r.get(idColumn)));
			bulk.add(sourceEntity);
		}
		return elasticsearchService.bulkIndex(index,bulk);
	}

	@ApiOperation("批量更新")
	@RequestMapping(value = "/bulkupdate",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult bulkUpdate(@RequestParam String index,
									@RequestParam String table,
									@RequestParam String idColumn,
									@RequestParam String updateTime){
		List<Map<String, Object>> list = viewMapper.updateGw(table,updateTime);
		if(list == null || list.isEmpty()){
			return ServiceResult.isNull();
		}
		List<SourceEntity> bulk = new ArrayList<>();
		for (Map<String, Object> r : list){
			SourceEntity sourceEntity = new SourceEntity();
			sourceEntity.setSource(JSONObject.toJSONString(r));
			sourceEntity.setId(String.valueOf(r.get(idColumn)));
			bulk.add(sourceEntity);
		}
		return elasticsearchService.bulkUpdate(index,bulk);
	}*/
}
