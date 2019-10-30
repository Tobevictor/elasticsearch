package com.learn.mapper;

import com.learn.mbg.model.Resourcedirectory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ResourceDirectoryMapperTest {

    @Autowired
    private ResourceDirectoryMapper resourceDirectoryMapper;
    @Test
    public void findAll() {
        String table = "resourcedirectory";
        List<Resourcedirectory> list = resourceDirectoryMapper.findAll(table);
        for (Resourcedirectory map : list){
            System.out.println(map);
        }
    }
}