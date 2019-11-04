package com.learn.mapper;

import com.learn.mbg.mapper1.CommentMapper;
import com.learn.mbg.model.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @Date 2019/9/9 21:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentMapperTest {
	@Autowired
	private CommentMapper commentMapper;
	@Test
	public void findAll() {
		List<Map<String,Object>> list =  commentMapper.findAll();
		System.out.println(list.size());
	}

	@Test
	public void findAll1() {
		List<Comment> list = commentMapper.findAll1();
		for(Comment s : list){
			System.out.println(s);
		}
		System.out.println(list.size());

	}
}