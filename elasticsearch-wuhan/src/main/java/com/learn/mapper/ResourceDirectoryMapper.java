package com.learn.mapper;

import com.learn.mbg.model.Resourcedirectory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface ResourceDirectoryMapper {

    @Select("select id,title,type,pid,sindex,ceatetime,isdelete,deletetime,code from ${table}")
    List<Resourcedirectory> findAll(@Param("table") String table);

    @Select("select * from ${table} where deletetime > #{updatetime} and isdelete = 0 ")
    List<Resourcedirectory> find(@Param("table") String table, @Param("updatetime") Date updateTime);
}
