package com.learn;

import com.learn.config.datasource.FirstDataSourceConfig;
import com.learn.config.datasource.SecondDataSourceConfig;
import com.learn.config.datasource.ThirdDataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(exposeProxy=true)
@Import({
		FirstDataSourceConfig.class,
		SecondDataSourceConfig.class,
		ThirdDataSourceConfig.class
})
public class ElasticsearchWuhanApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchWuhanApplication.class, args);
	}

}
