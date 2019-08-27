package com.learn.common.elastic.query.builder.fulltext;

import com.learn.common.elastic.condition.FullTextCondition;
import com.learn.common.elastic.query.builder.BaseSearchBuilder;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * @author dshuyou
 * @create 2019/7/11
 *
 */
public class MatchSearchBuilder extends BaseSearchBuilder {
	private String field;
	private String term;

	public MatchSearchBuilder(FullTextCondition condition) {
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

		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(field, term)
				.fuzziness(Fuzziness.AUTO)
				.prefixLength(3)
				.maxExpansions(10).analyzer("ik_smart");
		sourceBuilder.query(matchQueryBuilder);
		return sourceBuilder;
	}
}
