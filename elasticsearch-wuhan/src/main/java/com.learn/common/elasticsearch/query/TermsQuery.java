package com.learn.common.elasticsearch.query;

import com.learn.common.elasticsearch.query.condition.BaseCondition;
import com.learn.common.elasticsearch.query.condition.TermsLevelCondition;
import com.learn.common.elasticsearch.query.query_enum.QueryTypeEnum;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Date 2019/8/21 10:05
 * @Created by dshuyou
 */
public class TermsQuery extends BaseQuery{
	private QueryTypeEnum queryType;

	public TermsQuery(String index, RestHighLevelClient client, QueryTypeEnum type){
		super(index, client);
		this.queryType = type;
	}

	@Override
	public List<String> executeQuery(BaseCondition baseCondition) throws IOException {
		if(baseCondition.getFrom() != 0){
			sourceBuilder.from(baseCondition.getFrom());
		}
		if(baseCondition.getSize() != 0){
			sourceBuilder.size(baseCondition.getSize());
		}
		sourceBuilder.query(queryBuilder(baseCondition));
		if(sourceBuilder == null){
			return null;
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

	@Override
	public List<String> executeBoolQuery(Map<QueryTypeEnum, BaseCondition> conditions) throws IOException {
		return null;
	}

	public QueryBuilder queryBuilder(BaseCondition baseCondition){
		if (!(baseCondition instanceof TermsLevelCondition)) {
			throw new IllegalArgumentException("term level query need term level query condition");
		}

		TermsLevelCondition condition = (TermsLevelCondition) baseCondition;
		QueryBuilder queryBuilder;
		switch (queryType){
			case prefixQuery:
				queryBuilder = prefixBuilder(condition);
				break;
			case rangeQuery:
				queryBuilder = rangeBuilder(condition);
				break;
			case fuzzyQuery:
				queryBuilder = fuzzyBuilder(condition);
				break;
			case termQuery:
				queryBuilder = termBuilder(condition);
				break;
			case termsQuery:
				queryBuilder = termsBuilder(condition);
				break;
			case idsQuery:
				queryBuilder = idsBuilder(condition);
				break;
			default:
				throw new IllegalArgumentException("not supported term level search type");
		}
		return queryBuilder;
	}

	private QueryBuilder fuzzyBuilder(TermsLevelCondition condition){
		String field = condition.getField();
		String value = condition.getValue();

		return new FuzzyQueryBuilder(field, value).maxExpansions(10);
	}

	private QueryBuilder prefixBuilder(TermsLevelCondition condition){
		String field = condition.getField();
		String value = condition.getValue();

		return new PrefixQueryBuilder(field, value);
	}

	private QueryBuilder rangeBuilder(TermsLevelCondition condition){
		String field = condition.getField();
		String gte = condition.getGte();
		String lte = condition.getLte();
		RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder(field);
		rangeQueryBuilder.gte(gte);
		rangeQueryBuilder.lte(lte);

		return rangeQueryBuilder;
	}

	private QueryBuilder termBuilder(TermsLevelCondition condition){
		String field = condition.getField();
		String value = condition.getValue();

		return new TermQueryBuilder(field,value);
	}

	private QueryBuilder termsBuilder(TermsLevelCondition condition){
		String field = condition.getField();
		String[] values = condition.getValues();

		return new TermsQueryBuilder(field,values);
	}

	private QueryBuilder idsBuilder(TermsLevelCondition condition){
		String[] ids = condition.getIds();

		return new IdsQueryBuilder().addIds(ids);
	}
}
