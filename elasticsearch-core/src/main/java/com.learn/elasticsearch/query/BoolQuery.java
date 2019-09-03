package com.learn.elasticsearch.query;

import com.learn.elasticsearch.query.condition.BaseCondition;
import com.learn.elasticsearch.query.condition.FullTextCondition;
import com.learn.elasticsearch.query.condition.GeoCondition;
import com.learn.elasticsearch.query.condition.TermsLevelCondition;
import com.learn.elasticsearch.query.query_enum.FulltextEnum;
import com.learn.elasticsearch.query.query_enum.GeoEnum;
import com.learn.elasticsearch.query.query_enum.TermsEnum;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.util.*;

/**
 * @Date 2019/8/22 16:50
 * @author dshuyou
 */
public class BoolQuery extends BaseQuery{

	public BoolQuery(String index,RestHighLevelClient client){
		super(index,client);
	}

	@Override
	public List<String> executeQuery(BaseCondition baseCondition) {
		return null;
	}

	@Override
	public List<String> executeBoolQuery(Map<String, BaseCondition> conditions) throws IOException {
		QueryBuilder queryBuilder = null;
		List<QueryBuilder> list = new ArrayList<>();
		for (Map.Entry<String, BaseCondition> entry : conditions.entrySet()) {
			String type = entry.getKey();
			if (entry.getValue() instanceof FullTextCondition) {
				queryBuilder = new FulltextQuery(index, client, FulltextEnum.valueOf(type))
						.queryBuilder(entry.getValue());
			} else if (entry.getValue() instanceof TermsLevelCondition) {
				queryBuilder = new TermsQuery(index, client, TermsEnum.valueOf(type))
						.queryBuilder(entry.getValue());
			} else if (entry.getValue() instanceof GeoCondition) {
				queryBuilder = new GeoQuery(index, client, GeoEnum.valueOf(type))
						.queryBuilder(entry.getValue());
			}
			if (queryBuilder != null) {
				list.add(queryBuilder);
			}
		}
		/*sourceBuilder.from(0);
		sourceBuilder.size(100);*/
		sourceBuilder.query(boolBuilder(list));
		if(sourceBuilder == null){
			return Collections.emptyList();
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
			builder.must(q);
		}
		return builder;
	}

	/*public static void main(String[] args){
		QueryBuilder queryBuilder = new MatchPhraseQueryBuilder("content","我");
		QueryBuilder queryBuilder1 = new MatchPhraseQueryBuilder("content","和");
		QueryBuilder queryBuilder2 = new MatchPhraseQueryBuilder("content","你");
		BoolQueryBuilder builder = new BoolQueryBuilder();
		builder.must(queryBuilder).must(queryBuilder1).mustNot(queryBuilder2).boost(0.2f);
		System.out.println(builder);
	}*/
}
