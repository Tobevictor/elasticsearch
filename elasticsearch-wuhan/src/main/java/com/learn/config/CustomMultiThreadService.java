package com.learn.config;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author dshuyou
 * @date 2019/10/17 23:26
 */
@Service
public class CustomMultiThreadService {
	private Logger logger = Logger.getLogger(this.getClass());

	@Async
	public void executeAysncTask1(Integer i){
		logger.info("CustomMultiThreadingService ==> executeAysncTask1 method: 执行异步任务 " + i);
	}

	@Async
	public void executeAsyncTask2(Integer i){
		logger.info("CustomMultiThreadingService ==> executeAsyncTask2 method: 执行异步任务 " + i);
	}
}
