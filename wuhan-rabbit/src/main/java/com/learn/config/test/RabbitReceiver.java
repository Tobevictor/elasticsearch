package com.learn.config.test;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * @author dshuyou
 * @date 2019/10/31 13:39
 */
@Component
@RabbitListener(queues = "my_queue")
public class RabbitReceiver {

    @RabbitHandler
    public void receiver(String content){
        System.out.println("Receiver: " + content);
    }
}
