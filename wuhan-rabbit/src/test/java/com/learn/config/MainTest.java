package com.learn.config;


import com.learn.config.test.RabbitProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MainTest {
    @Autowired
    private RabbitProducer rabbitProducer;
    @Test
    public void test(){
        for (int i = 0;i < 100;i++){
            rabbitProducer.send();
        }
    }

}