package com.learn.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Date 2019/8/14 17:51
 * @Created by dshuyou
 */
@Mapper
public interface CommentMapper {

	@Select("select * from comment")
	List<Map<String,Object>> getAll();

	/*@Select("select * from comment")
	List<Comment> getAll();*/
}
