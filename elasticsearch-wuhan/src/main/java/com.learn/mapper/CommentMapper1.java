package com.learn.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @Date 2019/9/16 10:43
 */
@Mapper
public interface CommentMapper1 {

	List<Map<String,Object>> findAll();
}
