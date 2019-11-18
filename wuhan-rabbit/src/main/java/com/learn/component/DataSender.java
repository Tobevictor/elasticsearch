package com.learn.component;

import com.learn.dto.QueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dshuyou
 * @date 2019/11/1 10:45
 */
@Component
public class DataSender<T> {
    public Logger logger = LoggerFactory.getLogger(DataSender.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(final long delayTime, List<T> object){
        rabbitTemplate.convertAndSend(QueueEnum.QUEUE_TTL_DATA_TRANSPORT.getExchange(),
                QueueEnum.QUEUE_TTL_DATA_TRANSPORT.getRouteKey(),object,message ->{
                            message.getMessageProperties().setExpiration(String.valueOf(delayTime));
                            return message; });
        logger.info("send delay message: " + object);
    }
}
