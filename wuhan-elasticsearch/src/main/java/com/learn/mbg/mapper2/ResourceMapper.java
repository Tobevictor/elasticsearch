package com.learn.mbg.mapper2;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @date 2019/8/21 12:30
 * @author dshuyou
 */
@Mapper
public interface ResourceMapper {

	@Select("select * from ums_member")
	List<Map<String,Object>> findAll();
}
