package com.learn.common.elastic.query.builder.termlevel;

import com.learn.common.elastic.condition.TermLevelCondition;
import com.learn.common.elastic.query.builder.BaseSearchBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Arrays;


/**
 * @author dshuyou
 * @create 2019/7/11
 *
 */
public class IdsSearchBuilder extends BaseSearchBuilder {
	private String[] ids;
	public IdsSearchBuilder(TermLevelCondition condition) {
		super(condition);
		this.ids = condition.getIds();

		if(ids == null || ids.length == 0){
			throw new IllegalArgumentException("Illegal initial ids: " + Arrays.toString(ids));
		}
	}

	@Override
	public SearchSourceBuilder builder() {
		IdsQueryBuilder idsQueryBuilder = new IdsQueryBuilder();
		sourceBuilder.query(idsQueryBuilder.addIds(ids));
		return sourceBuilder;
	}

}
