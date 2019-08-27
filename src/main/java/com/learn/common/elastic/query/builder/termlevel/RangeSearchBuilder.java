package com.learn.common.elastic.query.builder.termlevel;

import com.learn.common.elastic.condition.TermLevelCondition;
import com.learn.common.elastic.query.builder.BaseSearchBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * @author dshuyou
 * @create 2019/7/11
 *
 */
public class RangeSearchBuilder extends BaseSearchBuilder {
	private String field;
	private String gte;
	private String lte;

	public RangeSearchBuilder(TermLevelCondition condition) {
		super(condition);
		this.field = condition.getField();
		this.gte = condition.getGte();
		this.lte = condition.getLte();

		if(field == null){
			throw new IllegalArgumentException("Illegal initial field: " + field);
		}
	}

	@Override
	public SearchSourceBuilder builder() {
		RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder(field);
		rangeQueryBuilder.gte(gte);
		rangeQueryBuilder.lte(lte);

		/*BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
		boolBuilder.must(rangeQueryBuilder);*/

		sourceBuilder.query(rangeQueryBuilder);
		return sourceBuilder;
	}
}
