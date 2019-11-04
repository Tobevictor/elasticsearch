package com.learn.config.test;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author dshuyou
 * @date 2019/10/31 13:37
 */
@Component
public class RabbitConfig {

    @Bean
    Queue queue(){
        return new Queue("my_queue",true);
    }
}
