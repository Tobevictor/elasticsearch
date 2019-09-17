package com.learn.controller;

import com.learn.common.elastic.common.result.ElasticResult;
import com.learn.common.elastic.condition.FullTextCondition;
import com.learn.common.elastic.condition.GeoCondition;
import com.learn.common.elastic.condition.TermLevelCondition;
import com.learn.service.QueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
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
	private RestHighLevelClient client;
	@Autowired
	private QueryService queryService;

	@ApiOperation("simple查询（默认）")
	@RequestMapping(value = "/matchAll/{index}",method = RequestMethod.POST)
	@ResponseBody
	public ElasticResult simpleQuery(@PathVariable String index,
									 @RequestBody FullTextCondition condition){
		return queryService.fulltextQuery(index,condition);
	}

	@ApiOperation("全文查询")
	@RequestMapping(value = "/fulltextQuery/{type}/{index}",method = RequestMethod.POST)
	@ResponseBody
	public ElasticResult fulltextQuery(@PathVariable String type,
									   @PathVariable String index,
									   @RequestBody FullTextCondition condition){
		return queryService.fulltextQuery(type,index,condition);
	}

	@ApiOperation("termlevel查询")
	@RequestMapping(value = "/termlevelQuery/{type}/{index}",method = RequestMethod.POST)
	@ResponseBody
	public ElasticResult termlevelQuery(@PathVariable String type,
										@PathVariable String index,
										@RequestBody TermLevelCondition condition){
		return queryService.termLevelQuery(type,index,condition);
	}

	@ApiOperation("地理查询")
	@RequestMapping(value = "/geoQuery/{type}/{index}",method = RequestMethod.POST)
	@ResponseBody
	public ElasticResult termlevelQuery(@PathVariable String type,
										@PathVariable String index,
										@RequestBody GeoCondition condition){
		return queryService.geoQuery(type,index,condition);
	}


	@ApiOperation("测试查询")
	@RequestMapping(value = "/testQuery/{index}",method = RequestMethod.POST)
	@ResponseBody
	public ElasticResult TestQuery(@PathVariable String index,
								   @RequestBody FullTextCondition condition){
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(QueryBuilders.matchAllQuery()).from(condition.getFrom()).size(condition.getSize());
		SearchRequest request = new SearchRequest(index);
		request.source(builder);
		try {
			List<String> list = new ArrayList<>();
			if(builder == null){
				LOGGER.error("sourceBuilder is null");
				return ElasticResult.failed(RestStatus.CONFLICT.getStatus(),
						"Fulltext Query failed,error:",new ArrayList<>());
			}
			SearchRequest searchRequest = new SearchRequest(index);
			searchRequest.source(builder);

			SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
			SearchHit[] hits = response.getHits().getHits();

			for (SearchHit searchHit : hits) {
				list.add(searchHit.getSourceAsString());
				//System.out.println(searchHit.getSourceAsString());
			}
			return ElasticResult.success(list);
		} catch (IOException e) {
			return ElasticResult.failed(RestStatus.CONFLICT.getStatus(),
					"Fulltext Query failed,error:"+e,new ArrayList<>());
		}
	}

}
