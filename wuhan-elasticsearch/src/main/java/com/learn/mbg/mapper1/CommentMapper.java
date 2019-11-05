package com.learn.mbg.mapper1;


import com.learn.mbg.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Map;

/**
 * @date 2019/8/21 12:30
 * @author dshuyou
 */
@Mapper
@Scope(ConfigurableListableBeanFactory.SCOPE_SINGLETON)
//@Scope(ConfigurableListableBeanFactory.SCOPE_PROTOTYPE)
public interface CommentMapper {

	@Select("select ids,id,address,date,content,username,liked from comment")
	List<Map<String,Object>> findAll();

	@Select("select ids,id,address,date,content,username,liked from comment")
	List<Comment> findAll1();

	@Select("select ids,id,address,date,content,username,liked from comment where ids < 10000")
	List<Map<String,Object>> findAll2();

	@Select("select ids,id,address,date,content,username,liked from comment where ids > #{start} and ids <= #{end}")
	List<Map<String,Object>> findAll3(@Param("start") int start, @Param("end") int end);
}
