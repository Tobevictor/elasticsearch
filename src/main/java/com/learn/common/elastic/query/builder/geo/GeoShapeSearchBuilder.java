package com.learn.common.elastic.query.builder.geo;

import com.learn.common.elastic.condition.GeoCondition;
import com.learn.common.elastic.query.builder.BaseSearchBuilder;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
import org.elasticsearch.index.query.GeoShapeQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;

import java.io.IOException;

/**
 * @Date 2019/7/31 13:24
 * @Created by dshuyou
 */
public class GeoShapeSearchBuilder extends BaseSearchBuilder {
	private String field;
	private ShapeBuilder shape;

	public GeoShapeSearchBuilder(GeoCondition condition) {
		super(condition);
		this.field = condition.getField();
		this.shape = condition.getShape();
		if (shape == null) {
			throw new IllegalArgumentException("bad args of geo shap");
		}
	}

	@Override
	public SearchSourceBuilder builder() throws IOException {
		GeoShapeQueryBuilder qb = QueryBuilders.geoShapeQuery(field, shape);
		sourceBuilder.query(qb);
		ScoreSortBuilder sort = SortBuilders.scoreSort();
		sourceBuilder.sort(sort);
		return sourceBuilder;
	}
}
