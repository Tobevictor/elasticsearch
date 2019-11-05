package com.learn.controller;

import com.learn.common.ServiceResult;
import com.learn.component.HotWord;
import com.learn.elasticsearch.query.condition.BoolCondition;
import com.learn.elasticsearch.query.condition.FullTextCondition;
import com.learn.elasticsearch.query.condition.GeoCondition;
import com.learn.elasticsearch.query.condition.TermsLevelCondition;
import com.learn.service.ElasticsearchService;
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
    public ServiceResult matchAll(@RequestParam String index,
                                  @RequestParam(defaultValue = "0") int pageNum,
                                  @RequestParam(defaultValue = "10") int pageSize){
        String queryType = "matchAllQuery";
        FullTextCondition condition = new FullTextCondition();
        return elasticsearchService.fulltextQuery(index,queryType,condition,pageNum,pageSize);
    }

    @ApiOperation("匹配检索")
    @RequestMapping(value = "/match",method = RequestMethod.POST)
    @ResponseBody
    @HotWord
    public ServiceResult match(@RequestParam String index,
                                    @RequestBody FullTextCondition condition,
                                    @RequestParam(defaultValue = "0") int pageNum,
                                    @RequestParam(defaultValue = "10") int pageSize){
        String queryType = "matchQuery";
        return elasticsearchService.fulltextQuery(index,queryType,condition,pageNum,pageSize);
    }

    @ApiOperation("全文检索")
    @RequestMapping(value = "/fulltext",method = RequestMethod.POST)
    @ResponseBody
    @HotWord
    public ServiceResult fulltextQuery(@RequestParam String index,
                                    @RequestBody FullTextCondition condition,
                                    @RequestParam String queryType,
                                    @RequestParam(defaultValue = "0") int pageNum,
                                    @RequestParam(defaultValue = "10") int pageSize){
        return elasticsearchService.fulltextQuery(index,queryType,condition,pageNum,pageSize);
    }

    @ApiOperation("字段检索")
    @RequestMapping(value = "/terms",method = RequestMethod.POST)
    @ResponseBody
    @HotWord
    public ServiceResult termsQuery(@RequestParam String index,
                                    @RequestBody TermsLevelCondition condition,
                                    @RequestParam String queryType,
                                    @RequestParam(defaultValue = "0") int pageNum,
                                    @RequestParam(defaultValue = "10") int pageSize){
        return elasticsearchService.termsQuery(index,queryType,condition,pageNum,pageSize);
    }

    @ApiOperation("范围检索")
    @RequestMapping(value = "/range",method = RequestMethod.POST)
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
    @RequestMapping(value = "/boundingBox",method = RequestMethod.POST)
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
    @RequestMapping(value = "/extendWord",method = RequestMethod.POST)
    @ResponseBody
    @HotWord
    public ServiceResult extendWord(@RequestParam(required = false,defaultValue = "") String index,
                                    @RequestParam String keyword,
                                    @RequestParam(defaultValue = "5") int size){

        String[] field = new String[]{"title.title_fullpinyin","title.title_prefixpinyin","title.title_text"};
        return elasticsearchService.extendWord(index,keyword,size,field);
    }

    @ApiOperation("多条件查询")
    @RequestMapping(value = "/bool",method = RequestMethod.GET)
    @ResponseBody
    @HotWord
    public ServiceResult boolQuery(@RequestParam String index,
                                   @RequestBody BoolCondition boolCondition,
                                   @RequestParam(defaultValue = "0") int pageNum,
                                   @RequestParam(defaultValue = "10") int pageSize){

        return elasticsearchService.boolQuery(index,boolCondition,pageNum,pageSize);
    }
}
