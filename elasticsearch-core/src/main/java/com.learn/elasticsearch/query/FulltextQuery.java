package com.learn.elasticsearch.query;

import com.learn.elasticsearch.query.condition.BaseCondition;
import com.learn.elasticsearch.query.condition.FullTextCondition;
import com.learn.elasticsearch.query.query_enum.FulltextEnum;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Date 2019/8/21 10:04
 * @author dshuyou
 */
public class FulltextQuery extends BaseQuery{
	private FulltextEnum queryType = FulltextEnum.matchAllQuery;

	public FulltextQuery(String index, RestHighLevelClient client){
		super(index,client);
	}

	public FulltextQuery(String index, RestHighLevelClient client, FulltextEnum type){
		super(index, client);
		if(type != null){
			queryType = type;
		}
	}

	@Override
	public List<String> executeQuery(BaseCondition baseCondition) throws IOException {
		if(baseCondition.getFrom() != FROM){
			sourceBuilder.from(baseCondition.getFrom());
		}
		if(baseCondition.getSize() != SIZE){
			sourceBuilder.size(baseCondition.getSize());
		}
		sourceBuilder.query(queryBuilder(baseCondition));
		if(sourceBuilder == null){
			return Collections.emptyList();
		}
		SearchRequest searchRequest = new SearchRequest(index);
		searchRequest.source(sourceBuilder);

		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHit[] hits = response.getHits().getHits();

		List<String> list = new ArrayList<>();
		for (SearchHit searchHit : hits) {
			list.add(searchHit.getSourceAsString());
		}
		return list;
	}

	public QueryBuilder queryBuilder(BaseCondition baseCondition){
		if (!(baseCondition instanceof FullTextCondition)) {
			throw new IllegalArgumentException("fulltext query need fulltext query condition");
		}

		FullTextCondition condition = (FullTextCondition) baseCondition;
		QueryBuilder queryBuilder;
		switch (queryType){
			case matchAllQuery :
				queryBuilder = matchAllBuilder();
				break;
			case matchPhraseQuery:
				queryBuilder = matchPhraseBuilder(condition);
				break;
			case matchPhrasePrefixQuery:
				queryBuilder = matchPhrasePrefixBuilder(condition);
				break;
			default:
				throw new IllegalArgumentException("not supported FullText search type");
		}
		return queryBuilder;
	}

	private QueryBuilder matchAllBuilder(){
		return new MatchAllQueryBuilder();
	}

	private QueryBuilder matchPhraseBuilder(FullTextCondition condition){
		String field = condition.getField();
		String value = condition.getValue();

		return new MatchPhraseQueryBuilder(field,value).analyzer("ik_smart");
	}

	private QueryBuilder matchPhrasePrefixBuilder(FullTextCondition condition){
		String field = condition.getField();
		String value = condition.getValue();
		MatchPhrasePrefixQueryBuilder builder = new MatchPhrasePrefixQueryBuilder(field,value).analyzer("ik_smart");
		builder.maxExpansions(10);

		return builder;
	}
}
