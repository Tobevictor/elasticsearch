package com.learn.controller;

import com.learn.common.ServiceResult;
import com.learn.component.HotWord;
import com.learn.elasticsearch.model.SourceEntity;
import com.learn.elasticsearch.query.condition.FullTextCondition;
import com.learn.elasticsearch.query.condition.GeoCondition;
import com.learn.elasticsearch.query.condition.TermsLevelCondition;
import com.learn.mapper.CommentMapper;
import com.learn.service.ElasticsearchService;
import io.swagger.annotations.ApiOperation;
import org.locationtech.jts.geom.Envelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @date 2019/9/16 9:47
 */
@Controller
@RequestMapping("/elastic")
public class ElasticController {
	@Autowired
	private ElasticsearchService elasticsearchService;
	@Autowired
	private CommentMapper commentMapper;

	@ApiOperation("构建默认索引")
	@RequestMapping(value = "/indice/create",method = RequestMethod.PUT)
	@ResponseBody
	public ServiceResult createIndex(@RequestParam String index){
		return elasticsearchService.createIndex(index);
	}

	@ApiOperation("构建自定义索引")
	@RequestMapping(value = "/indice/createbycustom",method = RequestMethod.PUT)
	@ResponseBody
	public ServiceResult createByCustom(@RequestParam String index,
								 @RequestParam String setting,
								 @RequestParam String mapping){
		return elasticsearchService.createIndex(index,setting,mapping);
	}

	@ApiOperation("更新映射")
	@RequestMapping(value = "/indice/putmapping",method = RequestMethod.PUT)
	@ResponseBody
	public ServiceResult putMapping(@RequestParam String index,
								 @RequestParam String mapping){
		return elasticsearchService.putMapping(index,mapping);
	}

	@ApiOperation("更新配置")
	@RequestMapping(value = "/indice/updateset",method = RequestMethod.PUT)
	@ResponseBody
	public ServiceResult updateSet(@RequestParam String index,
								 @RequestParam String setting){
		return elasticsearchService.updateSetting(index,setting);
	}


	@ApiOperation("删除索引")
	@RequestMapping(value = "/indice/delete",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult deleteIndex(@RequestParam String index){
		return elasticsearchService.deleteIndex(index);
	}

	@ApiOperation("批量索引")
	@RequestMapping(value = "/document/bulkindex",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult bulkIndex(@RequestParam String index){
		List<Map<String,Object>> list = commentMapper.findAll2();
		List<SourceEntity> queries = new LinkedList<>();
		for (Map<String, Object> aList : list) {
			SourceEntity sourceEntity = new SourceEntity();
			sourceEntity.setSource(aList);
			sourceEntity.setId(String.valueOf(aList.get("ids")));
			queries.add(sourceEntity);
		}
		return elasticsearchService.bulkIndex(index,queries);
	}

	@ApiOperation("批量更新")
	@RequestMapping(value = "/document/bulkupdate",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult bulkUpdate(@RequestParam String index){
		List<Map<String,Object>> list = commentMapper.findAll();
		List<SourceEntity> queries = new ArrayList<>();
		for (Map<String, Object> aList : list) {
			SourceEntity sourceEntity = new SourceEntity();
			sourceEntity.setSource(aList);
			sourceEntity.setId(String.valueOf(aList.get("ids")));
			queries.add(sourceEntity);
		}
		return elasticsearchService.bulkIndex(index,queries);
	}

	@ApiOperation("全文检索")
	@RequestMapping(value = "/query/matchall",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult matchAll(@RequestParam String index,
								  @RequestParam(defaultValue = "0") int pageNum,
								  @RequestParam(defaultValue = "10") int pageSize){
		String queryType = "matchAllQuery";
		FullTextCondition condition = new FullTextCondition();
		return elasticsearchService.fulltextQuery(index,queryType,condition,pageNum,pageSize);
	}

	@ApiOperation("匹配检索")
	@RequestMapping(value = "/query/match",method = RequestMethod.POST)
	@ResponseBody
	@HotWord
	public ServiceResult matchQuery(@RequestParam String index,
									@RequestBody FullTextCondition condition,
									@RequestParam(defaultValue = "0") int pageNum,
									@RequestParam(defaultValue = "10") int pageSize){
		String queryType = "matchQuery";
		return elasticsearchService.fulltextQuery(index,queryType,condition,pageNum,pageSize);
	}

	@ApiOperation("范围检索")
	@RequestMapping(value = "/query/range",method = RequestMethod.POST)
	@ResponseBody
	@HotWord
	public ServiceResult rangeQuery(@RequestParam String index,
									   @RequestBody TermsLevelCondition condition,
									   @RequestParam(defaultValue = "0") int pageNum,
									   @RequestParam(defaultValue = "10") int pageSize){
		String queryType = "rangeQuery";
		return elasticsearchService.termsQuery(index,queryType,condition,pageNum,pageSize);
	}

	@ApiOperation("地理空间信息检索")
	@RequestMapping(value = "/query/boundingBox",method = RequestMethod.POST)
	@ResponseBody
	public ServiceResult geoQuery(@RequestParam String index,
								  @RequestParam String queryType,
								  @RequestBody Envelope envelope,
								  @RequestParam(defaultValue = "0") int pageNum,
								  @RequestParam(defaultValue = "10") int pageSize){
		GeoCondition condition = new GeoCondition();
		condition.setBox(envelope.getMaxY(),envelope.getMinX(),envelope.getMinY(),envelope.getMaxX());
		return elasticsearchService.geoQuery(index,queryType,condition,pageNum,pageSize);
	}

	@ApiOperation("检索联想词")
	@RequestMapping(value = "/query/extendWord",method = RequestMethod.GET)
	@ResponseBody
	@HotWord
	public ServiceResult extendWord(@RequestParam(required = false,defaultValue = "") String index,
								  @RequestParam String keyword,
								  @RequestParam(defaultValue = "5") int size){

		String[] field = new String[]{"content.content_fullpinyin","content.content_prefixpinyin","content.content_text"};
		return elasticsearchService.extendWord(index,keyword,size,field);
	}

	@ApiOperation("查询mysql")
	@RequestMapping(value = "/query/mysql",method = RequestMethod.GET)
	@ResponseBody
	public ServiceResult selectMysql(@RequestParam int pageNumber,
									@RequestParam(defaultValue = "0") int pageIndex){

		int start = pageIndex * pageNumber;
		int end = start + pageNumber;
		List<Map<String,Object>> list = commentMapper.findAll3(start,end);
		return ServiceResult.success(list);
	}

}
