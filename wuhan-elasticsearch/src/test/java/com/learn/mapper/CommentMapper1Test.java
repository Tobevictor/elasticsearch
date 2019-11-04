package com.learn.mapper;

import com.learn.mbg.mapper1.CommentMapper1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @Date 2019/9/16 10:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentMapper1Test {

	@Autowired

	private CommentMapper1 commentMapper1;

	@Test
	public void findAll() {
		List<Map<String,Object>> list = commentMapper1.findAll();
		for (Map c : list){
			Iterator it = c.keySet().iterator();
			while (it.hasNext()){
				System.out.println(it.next());
			}
		}
	}
}