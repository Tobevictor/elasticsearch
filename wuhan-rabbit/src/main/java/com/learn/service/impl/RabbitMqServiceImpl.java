package com.learn.service.impl;

import com.learn.component.DataSender;
import com.learn.model.Student;
import com.learn.service.RabbitMqService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author dshuyou
 * @date 2019/11/1 9:52
 */
public class RabbitMqServiceImpl implements RabbitMqService {

    @Autowired
    private DataSender dataSender;
    final long delayTime = 5 * 60;

    @Override
    public void dataTransport(List object) {
        dataSender.sendMessage(delayTime,object);
    }
}
