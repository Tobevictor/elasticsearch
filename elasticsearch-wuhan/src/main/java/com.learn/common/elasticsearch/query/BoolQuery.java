package com.learn.common.elasticsearch.query;

import com.learn.common.elasticsearch.query.condition.BaseCondition;
import com.learn.common.elasticsearch.query.condition.FullTextCondition;
import com.learn.common.elasticsearch.query.condition.GeoCondition;
import com.learn.common.elasticsearch.query.condition.TermsLevelCondition;
import com.learn.common.elasticsearch.query.query_enum.QueryTypeEnum;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Date 2019/8/22 16:50
 * @Created by dshuyou
 */
public class BoolQuery extends BaseQuery{

	public BoolQuery(String index,RestHighLevelClient client){
		super(index,client);
	}

	@Override
	public List<String> executeQuery(BaseCondition baseCondition) throws IOException {
		return null;
	}

	@Override
	public List<String> executeBoolQuery(Map<QueryTypeEnum,BaseCondition> conditions) throws IOException {
		QueryBuilder queryBuilder = null;
		List<QueryBuilder> list = new ArrayList<>();
		for (Map.Entry<QueryTypeEnum, BaseCondition> entry : conditions.entrySet()) {
			QueryTypeEnum type = entry.getKey();
			if (entry.getValue() instanceof FullTextCondition) {
				queryBuilder = new FulltextQuery(index, client, type).queryBuilder(entry.getValue());
			} else if (entry.getValue() instanceof TermsLevelCondition) {
				queryBuilder = new TermsQuery(index, client, type).queryBuilder(entry.getValue());
			} else if (entry.getValue() instanceof GeoCondition) {
				queryBuilder = new GeoQuery(index, client, type).queryBuilder(entry.getValue());
			}
			if (queryBuilder != null) {
				list.add(queryBuilder);
			}
		}
		/*sourceBuilder.from(0);
		sourceBuilder.size(10);*/
		sourceBuilder.query(boolBuilder(list));
		if(sourceBuilder == null){
			return null;
		}
		SearchRequest searchRequest = new SearchRequest(index);
		searchRequest.source(sourceBuilder);

		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHit[] hits = response.getHits().getHits();

		List<String> result = new ArrayList<>();
		for (SearchHit searchHit : hits) {
			result.add(searchHit.getSourceAsString());
		}
		return result;
	}

	private QueryBuilder boolBuilder(List<QueryBuilder> queryBuilders){
		BoolQueryBuilder builder = new BoolQueryBuilder();
		for (QueryBuilder q : queryBuilders) {
			builder.must();
		}
		return builder;
	}
}
