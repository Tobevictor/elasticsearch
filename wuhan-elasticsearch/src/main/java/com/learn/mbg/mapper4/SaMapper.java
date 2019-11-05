package com.learn.mbg.mapper4;

import com.learn.model.SA.VIEW_MAP_model;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @date 2019/11/4 18:37
 */
@Mapper
public interface SaMapper {

    @Select("select BjNO,BSNUM,项目名称,申请人,矿山名称 from ${table}")
    List<VIEW_MAP_model> findAllFromSA(@Param("table") String table);

    @Select("select * from ${table}")
    List<Map<String,Object>> findAll(@Param("table") String table);
}
