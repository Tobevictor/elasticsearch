package com.learn.mbg.mapper3;

import com.learn.mbg.mapper2.ResourceMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceMapperTest {

    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private SqlSessionTemplate datasourceTemplate2;

    @Test
    public void get(){
        System.out.println(datasourceTemplate2.getMapper(ResourceMapper.class).findAll());
    }
    @Test
    public void findAll() {
        List<Map<String,Object>> list = resourceMapper.findAll();
        for (Map c : list){
            Iterator it = c.keySet().iterator();
            while (it.hasNext()){
                System.out.println(it.next());
            }
        }
    }
}