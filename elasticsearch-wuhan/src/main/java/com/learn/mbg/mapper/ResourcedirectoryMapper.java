package com.learn.mbg.mapper;

import com.learn.mbg.model.Resourcedirectory;
import com.learn.mbg.model.ResourcedirectoryExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface ResourcedirectoryMapper {

    List<Resourcedirectory> findAll(@Param("table") String table);

    long countByExample(ResourcedirectoryExample example);

    int deleteByExample(ResourcedirectoryExample example);

    int deleteByPrimaryKey(Short id);

    int insert(Resourcedirectory record);

    int insertSelective(Resourcedirectory record);

    List<Resourcedirectory> selectByExample(ResourcedirectoryExample example);

    Resourcedirectory selectByPrimaryKey(Short id);

    int updateByExampleSelective(@Param("record") Resourcedirectory record, @Param("example") ResourcedirectoryExample example);

    int updateByExample(@Param("record") Resourcedirectory record, @Param("example") ResourcedirectoryExample example);

    int updateByPrimaryKeySelective(Resourcedirectory record);

    int updateByPrimaryKey(Resourcedirectory record);
}