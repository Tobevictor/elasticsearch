package com.learn.common.elastic.query.builder.fulltext;

import com.learn.common.elastic.condition.FullTextCondition;
import com.learn.common.elastic.query.builder.BaseSearchBuilder;
import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * @Date 2019/8/1 15:29
 * @Created by dshuyou
 */
public class MatchPhrasePrefixSearchBuilder extends BaseSearchBuilder {
	private String field;
	private String term;

	public MatchPhrasePrefixSearchBuilder(FullTextCondition condition) {
		super(condition);
		field = condition.getField();
		term = condition.getValue();

		if(field == null){
			throw new IllegalArgumentException("Illegal initial field: " + field);
		}
		if(term == null ){
			throw new IllegalArgumentException("Illegal initial term: " + term);
		}
	}

	@Override
	public SearchSourceBuilder builder() {

		MatchPhrasePrefixQueryBuilder searchBuilder = new MatchPhrasePrefixQueryBuilder(field, term).analyzer("ik_smart");
		//searchBuilder.maxExpansions(FuzzyQuery.defaultMaxExpansions);
		searchBuilder.maxExpansions();
		sourceBuilder.query(searchBuilder);
		return sourceBuilder;
	}
}
