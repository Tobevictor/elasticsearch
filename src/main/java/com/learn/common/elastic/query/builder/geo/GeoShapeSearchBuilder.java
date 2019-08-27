package com.learn.common.elastic.query.builder.geo;

import com.learn.common.elastic.condition.GeoCondition;
import com.learn.common.elastic.query.builder.BaseSearchBuilder;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
import org.elasticsearch.index.query.GeoShapeQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Date 2019/7/31 13:24
 * @Created by dshuyou
 */
public class GeoShapeSearchBuilder extends BaseSearchBuilder {
	private String field;
	private String shapeId;

	public GeoShapeSearchBuilder(GeoCondition condition) {
		super(condition);
		this.field = condition.getField();
		this.shapeId = condition.getShapeId();
	}

	@Override
	public SearchSourceBuilder builder() {
		GeoShapeQueryBuilder qb = QueryBuilders.geoShapeQuery(field, shapeId);
		sourceBuilder.query(qb);
		ScoreSortBuilder sort = SortBuilders.scoreSort();
		sourceBuilder.sort(sort);
		return sourceBuilder;
	}
}
