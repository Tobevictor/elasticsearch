package com.learn.common.elastic.query.search;
import com.learn.common.elastic.condition.QueryCondition;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Date 2019/7/25 11:05
 * @Created by dshuyou
 */
public abstract class QueryOptions {

	protected String indice;
	protected RestHighLevelClient client;
	protected SearchSourceBuilder sourceBuilder;


	protected final int threadNum;
	protected final ThreadPoolExecutor queryWorkersPool;
	protected final LinkedTransferQueue<String> resultQueue;


	public QueryOptions(){
		threadNum = 0;
		queryWorkersPool = null;
		resultQueue = null;
	}

	public QueryOptions(String indice, RestHighLevelClient client) {
		this.indice = indice;
		this.client = client;
		this.sourceBuilder = new SearchSourceBuilder();

		threadNum = Runtime.getRuntime().availableProcessors() + 1;
		ThreadFactory threadFactory = new ThreadFactoryBuilder()
				.setDaemon(true).setNameFormat("internal-pol-%d").build();
		queryWorkersPool =
				(ThreadPoolExecutor) Executors.newFixedThreadPool(threadNum, threadFactory);
		queryWorkersPool.prestartAllCoreThreads();
		resultQueue = new LinkedTransferQueue<>();
	}

	public abstract List<String> executeQuery(QueryCondition queryCondition) throws Throwable;


}
