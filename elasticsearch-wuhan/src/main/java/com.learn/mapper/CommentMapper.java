package com.learn.mapper;

import com.learn.elasticsearch.model.SourceEntity;
import com.learn.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Date 2019/8/21 12:30
 * @Created by dshuyou
 */
@Mapper
public interface CommentMapper {

	@Select("select ids,id,address,date,content,username,liked from comment")
	List<Map<String,Object>> findAll();

	@Select("select ids,id,address,date,content,username,liked from comment")
	List<Comment> findAll1();
}
