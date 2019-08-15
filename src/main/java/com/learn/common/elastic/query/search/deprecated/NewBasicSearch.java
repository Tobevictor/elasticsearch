package com.learn.common.elastic.query.search.deprecated;

import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dshuyou
 * @create 2019/7/16
 *
 */
public class NewBasicSearch<T> implements Search{

	private RestHighLevelClient client;

	public NewBasicSearch(RestHighLevelClient client){
		this.client = client;
	}

	private List<String> search(SearchSourceBuilder searchBuilder, String... indices) {
		SearchRequest searchRequest = new SearchRequest(indices);
		searchRequest.source(searchBuilder);

		SearchResponse response = null;
		try {
			response = client.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		SearchHit[] hits = response.getHits().getHits();
		List<String> list = new ArrayList<>();

		for (SearchHit searchHit : hits) {
			list.add(searchHit.getSourceAsString());
		}
		return list;
	}

	@Override
	public List<String> matchSearch(String field, String term, String indice) {
		MatchQueryBuilder builder = QueryBuilders.matchQuery(field,term)
				.fuzziness(Fuzziness.AUTO)
				.prefixLength(3)
				.maxExpansions(10);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(builder);


		return search(sourceBuilder,indice);
	}

	@Override
	public List<String> matchAllSearch() {
		MatchAllQueryBuilder builder = QueryBuilders.matchAllQuery();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(builder);

		return search(sourceBuilder);
	}

	@Override
	public List<String> fuzzySearch(String field, String term, int size,String indice) {
		FuzzyQueryBuilder builder = QueryBuilders.fuzzyQuery(field,term);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

		sourceBuilder.from(0);
		sourceBuilder.size(size);
		sourceBuilder.query(builder);

		return search(sourceBuilder);
	}

	@Override
	public List<String> idsSearch(String[] ids,String indice) {

		IdsQueryBuilder builder = QueryBuilders.idsQuery();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(builder.addIds(ids));

		return search(sourceBuilder,indice);
	}

	public List<String> idsSearch(String[] ids,String terms,String field,String subTerms,String subField) {

		IdsQueryBuilder builder = QueryBuilders.idsQuery();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		TermsAggregationBuilder aggregation = AggregationBuilders.terms(terms)
				.field(field);
		aggregation.subAggregation(AggregationBuilders.avg(subTerms)
				.field(subField));
		sourceBuilder.aggregation(aggregation);
		sourceBuilder.query(builder.addIds(ids));

		return search(sourceBuilder);
	}

	@Override
	public List<String> multiSearch(String field, String[] terms, String indices) {
		List<SearchSourceBuilder> builders = new ArrayList<>();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

		for (String term : terms) {
			sourceBuilder.query(QueryBuilders.matchQuery(field, term));
			builders.add(sourceBuilder);
			sourceBuilder.clearRescorers();
		}

		return multiSearch(builders,indices);
	}

	@Override
	public List<String> rangeSearch(String field, String gte, String lte, int size, String indice) {
		RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(field);
		rangeQueryBuilder.gte(gte);
		rangeQueryBuilder.lte(lte);

		BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
		boolBuilder.must(rangeQueryBuilder);

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(0);
		sourceBuilder.size(size);
		sourceBuilder.query(boolBuilder);

		return search(sourceBuilder,indice);
	}

	@Override
	public List<String> prefixSearch(String field, String prefix,String indice) {
		PrefixQueryBuilder queryBuilder = QueryBuilders.prefixQuery(field,prefix);
		BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
		boolBuilder.must(queryBuilder);

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(boolBuilder);

		return search(sourceBuilder,indice);
	}

	@Override
	public List<String> termSearch(String field, String term, String indice) {
		TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(field,term);
		BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
		boolBuilder.must(termQueryBuilder);

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(0);
		sourceBuilder.query(boolBuilder);

		return search(sourceBuilder,indice);
	}

	@Override
	public List<String> termsSearch(String field, String[] terms, String indice) {
		TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery(field,terms);
		BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
		boolBuilder.must(termsQueryBuilder);

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(0);
		sourceBuilder.query(boolBuilder);

		return search(sourceBuilder,indice);
	}

	private List<String> multiSearch(List<SearchSourceBuilder> searchBuilders,String indice) {
		MultiSearchRequest request = new MultiSearchRequest();
		for (SearchSourceBuilder searchBuilder : searchBuilders) {
			SearchRequest searchRequest = new SearchRequest(indice);
			searchRequest.source(searchBuilder);
			request.add(searchRequest);
		}
		List<String> list = new ArrayList<>();
		MultiSearchResponse multiResponse;
		try {
			multiResponse = client.msearch(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		for (MultiSearchResponse.Item response:multiResponse){
			SearchHit[] searchHits = response.getResponse().getHits().getHits();
			for (SearchHit searchHit : searchHits) {
				list.add(searchHit.getSourceAsString());
			}
		}
		return list;
	}

	public List<String> searchScroll(SearchSourceBuilder searchBuilder,String indice) {
		final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
		SearchRequest searchRequest = new SearchRequest(indice);
		searchRequest.scroll(scroll);
		searchRequest.source(searchBuilder);

		SearchResponse searchResponse = null;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		List<String> list = new ArrayList<>();

		while (searchHits != null && searchHits.length > 0) {

			SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
			scrollRequest.scroll(scroll);
			try {
				searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			scrollId = searchResponse.getScrollId();
			searchHits = searchResponse.getHits().getHits();

			for (SearchHit searchHit : searchHits) {
				list.add(searchHit.getSourceAsString());
			}
		}

		ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
		clearScrollRequest.addScrollId(scrollId);
		try {
			client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public long count(SearchSourceBuilder searchBuilder,String indice) {
		CountRequest countRequest = new CountRequest(indice);
		countRequest.source(searchBuilder);

		CountResponse countResponse = null;
		try {
			countResponse = client.count(countRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return countResponse.getCount();
	}

}
