package com.learn.common.elastic.query.builder.termlevel;

import com.learn.common.elastic.condition.TermLevelCondition;
import com.learn.common.elastic.query.builder.BaseSearchBuilder;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * @author dshuyou
 * @create 2019/7/16
 *
 */
public class PrefixSearchBuilder extends BaseSearchBuilder {
	private String field;
	private String term;

	public PrefixSearchBuilder(TermLevelCondition condition){
		super(condition);
		field = condition.getField();
		term = condition.getValue();

		if(field == null){
			throw new IllegalArgumentException("Illegal initial field: " + field);
		}
		if(term == null){
			throw new IllegalArgumentException("Illegal initial term: " + term);
		}
	}

	@Override
	public SearchSourceBuilder builder() {
		PrefixQueryBuilder queryBuilder = QueryBuilders.prefixQuery(field, term);
		/*BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
		boolBuilder.must(queryBuilder);*/

		sourceBuilder.query(queryBuilder);

		return sourceBuilder;
	}
}
