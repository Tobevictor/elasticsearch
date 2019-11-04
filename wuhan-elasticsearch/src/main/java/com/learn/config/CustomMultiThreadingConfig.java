package com.learn.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * @author dshuyou
 * @date 2019/10/17 23:08
 */
@Configuration
@EnableAsync//利用@EnableAsync注解开启异步任务支持
public class CustomMultiThreadingConfig implements AsyncConfigurer {
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,
				10,
				1000,
				TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(10),
				new ThreadPoolTaskExecutor(),
				new ThreadPoolExecutor.AbortPolicy());
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();//SynchronousQueue
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);//LinkedBlockQueue
		ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);//delayBlockQueue
		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();//LinkedBlockQueue
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(25);
		taskExecutor.initialize();
		return taskExecutor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return AsyncConfigurer.super.getAsyncUncaughtExceptionHandler();
	}
}
