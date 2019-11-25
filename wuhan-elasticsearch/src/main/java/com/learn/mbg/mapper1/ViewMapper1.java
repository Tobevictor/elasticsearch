package com.learn.mbg.mapper1;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @date 2019/11/20 13:15
 */
@Mapper
public interface ViewMapper1 {

    @MapKey("comment_id")
    @Select("SELECT * from ${table}")
    List<Map<String,Map<String,Object>>> comments(@Param(value = "table") String table,@Param(value = "pk") String pk);

    @Select("SELECT * from ${table} where comment_id = #{pk}")
    Map<String,Object> findOne(@Param(value = "table") String table, @Param(value = "pk") String pk);

}
