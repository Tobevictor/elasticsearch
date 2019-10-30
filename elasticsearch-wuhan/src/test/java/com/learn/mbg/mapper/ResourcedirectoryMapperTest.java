package com.learn.mbg.mapper;

import com.learn.mapper.ResourceDirectoryMapper;
import com.learn.mbg.model.Resourcedirectory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class ResourcedirectoryMapperTest {
    @Autowired
    private ResourcedirectoryMapper resourcedirectoryMapper;
    @Test
    public void findAll() {
        String table = "resourcedirectory";
        List<Resourcedirectory> list = resourcedirectoryMapper.findAll(table);
        for (Resourcedirectory map : list){
            System.out.println(map);
        }
    }
}