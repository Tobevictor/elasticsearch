package com.learn.controller;

import com.learn.common.ServiceResult;
import com.learn.component.HotWord;
import com.learn.elasticsearch.query.condition.BoolCondition;
import com.learn.elasticsearch.query.condition.FullTextCondition;
import com.learn.elasticsearch.query.condition.GeoCondition;
import com.learn.elasticsearch.query.condition.TermsLevelCondition;
import com.learn.service.ElasticsearchService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.locationtech.jts.geom.Envelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/elastic/search")
@CrossOrigin
public class SearchController {
    @Autowired
    private ElasticsearchService elasticsearchService;

    @ApiOperation("全部检索")
    @RequestMapping(value = "/matchall",method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name="index",value = "索引",required = true),
            @ApiImplicitParam(name="pn",value = "搜索页码",required = true),
            @ApiImplicitParam(name="rn",value = "每页搜索结果数",required = true)
    })
    public ServiceResult matchAll(@RequestParam String index,
                                  @RequestParam(defaultValue = "0") int pn,
                                  @RequestParam(defaultValue = "10") int rn){
        String queryType = "matchAllQuery";
        FullTextCondition condition = new FullTextCondition();
        return elasticsearchService.fulltextQuery(index,queryType,condition,pn,rn);
    }

    @ApiOperation("匹配检索")
    @RequestMapping(value = "/match",method = RequestMethod.POST)
    @ResponseBody
    @HotWord
    public ServiceResult match(@RequestParam String index,
                                    @RequestBody FullTextCondition condition,
                                    @RequestParam(defaultValue = "0") int pn,
                                    @RequestParam(defaultValue = "10") int rn){
        String queryType = "matchQuery";
        return elasticsearchService.fulltextQuery(index,queryType,condition,pn,rn);
    }

    @ApiOperation("全文检索")
    @RequestMapping(value = "/fulltext",method = RequestMethod.POST)
    @ResponseBody
    @HotWord
    public ServiceResult fulltextQuery(@RequestParam String index,
                                    @RequestBody FullTextCondition condition,
                                    @RequestParam String queryType,
                                    @RequestParam(defaultValue = "0") int pn,
                                    @RequestParam(defaultValue = "10") int rn){
        return elasticsearchService.fulltextQuery(index,queryType,condition,pn,rn);
    }

    @ApiOperation("字段检索")
    @RequestMapping(value = "/terms",method = RequestMethod.POST)
    @ResponseBody
    @HotWord
    public ServiceResult termsQuery(@RequestParam String index,
                                    @RequestBody TermsLevelCondition condition,
                                    @RequestParam String queryType,
                                    @RequestParam(defaultValue = "0") int pn,
                                    @RequestParam(defaultValue = "10") int rn){
        return elasticsearchService.termsQuery(index,queryType,condition,pn,rn);
    }

    @ApiOperation("范围检索")
    @RequestMapping(value = "/range",method = RequestMethod.POST)
    @ResponseBody
    @HotWord
    public ServiceResult rangeQuery(@RequestParam String index,
                                    @RequestBody TermsLevelCondition condition,
                                    @RequestParam(defaultValue = "0") int pn,
                                    @RequestParam(defaultValue = "10") int rn){
        String queryType = "rangeQuery";
        return elasticsearchService.termsQuery(index,queryType,condition,pn,rn);
    }

    @ApiOperation("地理空间信息检索")
    @RequestMapping(value = "/boundingBox",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult geoQuery(@RequestParam String index,
                                  @RequestParam String queryType,
                                  @RequestBody Envelope envelope,
                                  @RequestParam(defaultValue = "0") int pn,
                                  @RequestParam(defaultValue = "10") int rn){
        GeoCondition condition = new GeoCondition();
        condition.setBox(envelope.getMaxY(),envelope.getMinX(),envelope.getMinY(),envelope.getMaxX());
        return elasticsearchService.geoQuery(index,queryType,condition,pn,rn);
    }

    @ApiOperation("检索联想词")
    @RequestMapping(value = "/extendWord",method = RequestMethod.POST)
    @ResponseBody
    @HotWord
    @ApiImplicitParams({
            @ApiImplicitParam(name = "index", value = "索引", required = false),
            @ApiImplicitParam(name = "wd", value = "关键词", required = true),
            @ApiImplicitParam(name="rn",value = "搜索结果数",required = true),
    })
    public ServiceResult extendWord(@RequestParam(required = false,defaultValue = "") String index,
                                    @RequestParam String keyword,
                                    @RequestParam(defaultValue = "5") int rn){

        String[] field = new String[]{"title.title_fullpinyin","title.title_prefixpinyin","title.title_text"};
        return elasticsearchService.extendWord(index,keyword,rn,field);
    }

    @ApiOperation("多条件查询")
    @RequestMapping(value = "/bool",method = RequestMethod.POST)
    @ResponseBody
    @HotWord
    public ServiceResult boolQuery(@RequestParam String index,
                                   @RequestBody BoolCondition boolCondition,
                                   @RequestParam(defaultValue = "0") int pn,
                                   @RequestParam(defaultValue = "10") int rn){

        return elasticsearchService.boolQuery(index,boolCondition,pn,rn);
    }

    @ApiOperation("主页输入框查询")
    @RequestMapping(value = "/home",method = RequestMethod.POST)
    @ResponseBody
    @HotWord
    public ServiceResult queryString(@RequestParam String index,
                                   @RequestBody FullTextCondition condition,
                                   @RequestParam(defaultValue = "0") int pn,
                                   @RequestParam(defaultValue = "10") int rn){

        return elasticsearchService.queryString(index,condition,pn,rn);
    }
}
