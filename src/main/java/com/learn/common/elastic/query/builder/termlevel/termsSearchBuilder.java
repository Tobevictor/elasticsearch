package com.learn.common.elastic.query.builder.termlevel;

import com.learn.common.elastic.condition.TermLevelCondition;
import com.learn.common.elastic.query.builder.BaseSearchBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;


/**
 * @Date 2019/8/12 12:10
 * @Created by dshuyou
 */
public class termsSearchBuilder extends BaseSearchBuilder {

	private String field;
	private String[] terms;

	public termsSearchBuilder(TermLevelCondition condition) {
		super(condition);
		field = condition.getField();
		terms = condition.getValues();

		if(field == null){
			throw new IllegalArgumentException("Illegal initial field: " + field);
		}
		if(terms == null ||terms.length == 0 ){
			throw new IllegalArgumentException("Illegal initial term: " + terms);
		}
	}

	@Override
	public SearchSourceBuilder builder() {
		TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery(field,terms);
		/*BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
		boolBuilder.must(termQueryBuilder);*/

		sourceBuilder.query(termsQueryBuilder);
		return sourceBuilder;
	}
}
