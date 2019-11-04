package com.learn.component;

import com.learn.mapper.StudentMapper;
import com.learn.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @date 2019/8/21 13:06
 * @author dshuyou
 */
@Component
public class OrderTimeOutCancelTask {
	private Logger logger = LoggerFactory.getLogger(OrderTimeOutCancelTask.class);
	private static final long DELAY_TIME = 60 * 1000;
	@Autowired
	private DataSender dataSender;
	@Autowired
	private StudentMapper studentMapper;
	/**
	 * cron表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
	 * 每10分钟扫描一次
	 */
	//@Scheduled(cron = "0 0/1 * ? * ?")
	@Scheduled(cron = "0/10 * * * * ?")
	private void dataTransport() {
		List<Student> list = studentMapper.findAll();
		dataSender.sendMessage(DELAY_TIME,list);
	}
}
