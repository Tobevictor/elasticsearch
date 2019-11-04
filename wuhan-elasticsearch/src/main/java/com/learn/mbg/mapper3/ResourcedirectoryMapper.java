package com.learn.mbg.mapper3;

import com.learn.model.Resourcedirectory;
import com.learn.model.ResourcedirectoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ResourcedirectoryMapper {
    List<Resourcedirectory> findAll();

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