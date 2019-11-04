package com.learn.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dshuyou
 * @date 2019/11/1 10:52
 */
@Component
@RabbitListener(queues = "rabbit.data.transport")
public class DataReceiver<T> {
    private static Logger logger = LoggerFactory.getLogger(DataReceiver.class);

    @RabbitHandler
    public void handle(List<T> object){
        System.out.println(object.size());
        logger.info("receive delay message :{}",object);
    }
}
