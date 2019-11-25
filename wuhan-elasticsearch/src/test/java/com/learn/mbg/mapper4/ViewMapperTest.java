package com.learn.mbg.mapper4;

import com.learn.mbg.mapper1.ViewMapper1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViewMapperTest {

    @Autowired
    private ViewMapper1 viewMapper1;
    @Test
    public void tkBjinfo1() {
        String table = "comments";
        String pk = "11";
        Map<String,Object> map =  viewMapper1.findOne(table,pk);
        System.out.println(map);
    }
}