package com.learn.common.elastic.query.search;

import com.learn.common.elastic.condition.FullTextCondition;
import com.learn.common.elastic.condition.TermLevelCondition;
import com.learn.common.elastic.condition.QueryCondition;
import com.learn.common.elastic.query.builder.fulltext.MatchAllSearchBuilder;
import com.learn.common.elastic.query.builder.fulltext.MatchPhrasePrefixSearchBuilder;
import com.learn.common.elastic.query.builder.fulltext.MatchPhraseSearchBuilder;
import com.learn.common.elastic.query.builder.fulltext.MatchSearchBuilder;
import com.learn.common.elastic.query.builder.termlevel.*;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dshuyou
 * @create 2019/7/24
 *
 */
public class simpleSearch extends QueryOptions {

	private SearchTypeEnum searchType = SearchTypeEnum.matchAllSearch;

	/**
	 * Only use for simple query.
	 * Default is matchAllSearch.
	 * */
	public simpleSearch(String indice, RestHighLevelClient client){
		super(indice, client);
	}

	public simpleSearch(String indice, RestHighLevelClient client, SearchTypeEnum type){
		super(indice, client);
		if(searchType != null){
			searchType = type;
		}
	}

	@Override
	public List<String> executeQuery(QueryCondition querycondition) throws IOException {
		if(searchType.equals(SearchTypeEnum.matchAllSearch)||
				searchType.equals(SearchTypeEnum.matchPhraseSearch)||
				searchType.equals(SearchTypeEnum.matchSearch)||
				searchType.equals(SearchTypeEnum.matchPhrasePrefixSearch)
		){
			sourceBuilder = fullText(querycondition,searchType);
		}

		else if (searchType.equals(SearchTypeEnum.fuzzySearch)||
				searchType.equals(SearchTypeEnum.idsSearch)||
				searchType.equals(SearchTypeEnum.prefixSearch)||
				searchType.equals(SearchTypeEnum.termSearch)||
				searchType.equals(SearchTypeEnum.rangeSearch)
		){
			sourceBuilder = termLevel(querycondition,searchType);
		}

		SearchRequest searchRequest = new SearchRequest(indice);
		searchRequest.source(sourceBuilder);

		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHit[] hits = response.getHits().getHits();
		List<String> list = new ArrayList<>();

		for (SearchHit searchHit : hits) {
			list.add(searchHit.getSourceAsString());
			System.out.println(searchHit.getSourceAsString());
		}
		return list;
	}

	private SearchSourceBuilder termLevel(QueryCondition querycondition,SearchTypeEnum searchType){
		if (!(querycondition instanceof TermLevelCondition)) {
			throw new IllegalArgumentException("term level query need term level query condition");
		}

		TermLevelCondition condition = (TermLevelCondition) querycondition;

		switch (searchType){
			case prefixSearch:
				sourceBuilder = new PrefixSearchBuilder(condition).builder();
				break;
			case rangeSearch:
				sourceBuilder = new RangeSearchBuilder(condition).builder();
				break;
			case fuzzySearch:
				sourceBuilder = new FuzzySearchBuilder(condition).builder();
				break;
			case termSearch:
				sourceBuilder = new TermSearchBuilder(condition).builder();
				break;
			case idsSearch:
				sourceBuilder = new IdsSearchBuilder(condition).builder();
				break;

			default:
				throw new IllegalArgumentException("not supported term level search type");
		}
		return sourceBuilder;
	}

	public SearchSourceBuilder fullText(QueryCondition querycondition,SearchTypeEnum searchType){
		if (!(querycondition instanceof FullTextCondition)) {
			throw new IllegalArgumentException("FullText query need FullText query condition");
		}

		FullTextCondition condition = (FullTextCondition) querycondition;
		switch (searchType){
			case matchAllSearch:
				sourceBuilder = new MatchAllSearchBuilder(condition).builder();
				break;
			case matchSearch:
				sourceBuilder = new MatchSearchBuilder(condition).builder();
				break;
			case matchPhraseSearch:
				sourceBuilder = new MatchPhraseSearchBuilder(condition).builder();
				break;
			case matchPhrasePrefixSearch:
				sourceBuilder = new MatchPhrasePrefixSearchBuilder(condition).builder();
				break;
			default:
				throw new IllegalArgumentException("not supported FullText search type");
		}
		return sourceBuilder;
	}
}