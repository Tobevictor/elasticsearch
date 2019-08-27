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
 * @Date 2019/7/31 17:56
 * @Created by dshuyou
 */
public class WithinSearchBuilder extends BaseSearchBuilder {
	private String field;
	private String shapeId;

	public WithinSearchBuilder(GeoCondition condition) {
		super(condition);
		this.field = condition.getField();
		this.shapeId = condition.getShapeId();

		/*if (field == null || shape == null) {
			throw new IllegalArgumentException("bad args of geo points");
		}*/
	}

	public WithinSearchBuilder(String field, String shapeId) {
		super();
		this.field = field;
		this.shapeId = shapeId;
	}

	@Override
	public SearchSourceBuilder builder(){
		GeoShapeQueryBuilder queryBuilder = QueryBuilders.geoWithinQuery(field,shapeId);
		sourceBuilder.query(queryBuilder);
		ScoreSortBuilder sort = SortBuilders.scoreSort();
		sourceBuilder.sort(sort);
		return sourceBuilder;
	}

}
