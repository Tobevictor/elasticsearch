package com.learn.common.elastic.query.builder.basic;

import com.learn.common.elastic.condition.TermLevelCondition;
import com.learn.common.elastic.query.builder.BaseSearchBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dshuyou
 * @create 2019/7/11
 *
 */
public class MultiSearchBuilder extends BaseSearchBuilder {
	private String field;
	private String[] terms;

	public MultiSearchBuilder(TermLevelCondition condition) {
		super(condition);
		field = condition.getField();
		terms = condition.getValues();

		if(field == null){
			throw new IllegalArgumentException("Illegal initial field: " + field);
		}
		if(terms == null || terms.length == 0){
			throw new IllegalArgumentException("Illegal initial terms: " + Arrays.toString(terms));
		}
	}

	@Override
	public SearchSourceBuilder builder() {
		return null;
	}

	public List<SearchSourceBuilder> builders(){
		List<SearchSourceBuilder> list = new ArrayList<>();
		for (int i = 0; i < terms.length; i++) {
			sourceBuilder.query(QueryBuilders.matchQuery(field, terms[i]));
			list.add(sourceBuilder);
			sourceBuilder.clearRescorers();
		}
		return list;
	}
}
