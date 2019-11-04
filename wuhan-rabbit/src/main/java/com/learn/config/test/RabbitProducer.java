package com.learn.config.test;

import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dshuyou
 * @date 2019/10/31 13:39
 */
@Component
public class RabbitProducer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send(){
        String content = "my message: " + System.currentTimeMillis();
        System.out.println("Producer: " + content);
        rabbitTemplate.convertAndSend("my_queue",content);
        /*rabbitTemplate.convertAndSend("", trade.getTradeCode(), message ->{
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            message.getMessageProperties().setDelay(30 * (60*1000));   // 毫秒为单位，指定此消息的延时时长
            return message;*/
    }

}
