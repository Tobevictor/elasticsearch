package com.learn.service;

import com.learn.model.Student;

import java.util.List;

/**
 * @author dshuyou
 * @date 2019/11/1 9:50
 */
public interface RabbitMqService<T> {

    void dataTransport(List<T> message);
}
