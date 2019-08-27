package com.learn.common.elastic.query.builder.fulltext;

import com.learn.common.elastic.condition.FullTextCondition;
import com.learn.common.elastic.query.builder.BaseSearchBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * @author dshuyou
 * @create 2019/7/11
 *
 */
public class MatchAllSearchBuilder extends BaseSearchBuilder {

	public MatchAllSearchBuilder(FullTextCondition condition) {
		super(condition);
	}

	@Override
	public SearchSourceBuilder builder(){
		sourceBuilder.query(new MatchAllQueryBuilder());
		return sourceBuilder;
	}

}
