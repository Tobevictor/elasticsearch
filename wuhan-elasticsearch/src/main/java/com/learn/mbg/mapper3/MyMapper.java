package com.learn.mbg.mapper3;

import com.learn.model.Resourcedirectory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface MyMapper<T> {
    @Select("select * from ${table}")
    List<Resourcedirectory> findAll1(@Param("table") String table);

    @Select("select * from ${table} where deletetime > #{updatetime} and isdelete = 0")
    List<Resourcedirectory> find(@Param("table") String table, @Param("updatetime") Date updetetime);
}
