package com.learn.elasticsearch.query;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.learn.elasticsearch.query.condition.BaseCondition;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Date 2019/8/21 10:43
 * @author dshuyou
 */
public abstract class BaseQuery {
	protected String index;
	protected RestHighLevelClient client;
	protected SearchSourceBuilder sourceBuilder;
	protected static int FROM = 0;
	protected static int SIZE = 100;


	protected final int threadNum;
	protected final ThreadPoolExecutor queryWorkersPool;
	protected final LinkedTransferQueue<String> resultQueue;


	public BaseQuery(){
		threadNum = 0;
		queryWorkersPool = null;
		resultQueue = null;
	}

	public BaseQuery(String index, RestHighLevelClient client) {
		this.index = index;
		this.client = client;
		this.sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(FROM).size(SIZE);


		threadNum = Runtime.getRuntime().availableProcessors() + 1;
		ThreadFactory threadFactory = new ThreadFactoryBuilder()
				.setDaemon(true).setNameFormat("internal-pol-%d").build();
		queryWorkersPool =
				(ThreadPoolExecutor) Executors.newFixedThreadPool(threadNum, threadFactory);
		queryWorkersPool.prestartAllCoreThreads();
		resultQueue = new LinkedTransferQueue<>();
	}

	public abstract List<String> executeQuery(BaseCondition baseCondition) throws IOException;

	public abstract List<String> executeBoolQuery(Map<String, BaseCondition> conditions) throws IOException;

}

