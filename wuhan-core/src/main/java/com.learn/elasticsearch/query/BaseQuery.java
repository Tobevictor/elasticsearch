package com.learn.elasticsearch.query;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.learn.elasticsearch.query.condition.BaseCondition;
import com.learn.elasticsearch.query.model.DataContent;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * This class is the abstract class for query action.
 * @date 2019/8/21 10:43
 * @author dshuyou
 * @see BoolQuery
 * @see FulltextQuery
 * @see GeoQuery
 * @see TermsQuery
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

	public abstract DataContent executeQuery(BaseCondition baseCondition) throws IOException;

	public abstract QueryBuilder queryBuilder(BaseCondition baseCondition) throws IOException;

	DataContent returnList(SearchSourceBuilder sourceBuilder, BaseCondition baseCondition) throws IOException {
		if(baseCondition.getFrom() != FROM){
			sourceBuilder.from(baseCondition.getFrom());
		}
		if(baseCondition.getSize() != SIZE){
			sourceBuilder.size(baseCondition.getSize());
		}
		//if(hasSort){sourceBuilder.sort("_score", SortOrder.DESC);}
		String sortField = baseCondition.getSortField();
		if(sortField == null||sortField.isEmpty()){
			sourceBuilder.sort(SortBuilders.scoreSort());
		}else {
			sourceBuilder.sort(sortField, SortOrder.DESC);
		}
		//String.split("",),前端传参形式（a,b,c,d）
		SearchRequest searchRequest = new SearchRequest(index.split(","));
		searchRequest.source(sourceBuilder);

		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		long count = response.getHits().getTotalHits().value;
		SearchHit[] hits = response.getHits().getHits();

		List<String> list = new ArrayList<>();
		for (SearchHit searchHit : hits) {
			list.add(searchHit.getSourceAsString());
		}
		return new DataContent(list,count);
	}

}

