package com.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy=true)
public class ElasticsearchWuhanApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchWuhanApplication.class, args);
	}

}
