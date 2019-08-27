package com.learn.common.elastic.query.builder.fulltext;

import com.learn.common.elastic.condition.FullTextCondition;
import com.learn.common.elastic.query.builder.BaseSearchBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * @author dshuyou
 * @create 2019/7/16
 *
 */
public class MatchPhraseSearchBuilder extends BaseSearchBuilder {
	private String field;
	private String term;

	public MatchPhraseSearchBuilder(FullTextCondition condition) {
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

		MatchPhraseQueryBuilder searchBuilder = new MatchPhraseQueryBuilder(field, term).analyzer("ik_smart");
		sourceBuilder.query(searchBuilder);
		return sourceBuilder;
	}
}

