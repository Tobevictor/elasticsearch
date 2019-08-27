package com.learn.common.elastic.query.builder.geo;

import com.learn.common.elastic.condition.GeoCondition;
import com.learn.common.elastic.query.builder.BaseSearchBuilder;
import org.apache.lucene.geo.SimpleWKTShapeParser;
import org.elasticsearch.common.geo.GeoShapeType;
import org.elasticsearch.common.geo.builders.PolygonBuilder;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
import org.elasticsearch.common.geo.parsers.CoordinateNode;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.geo.geometry.ShapeType;
import org.elasticsearch.index.query.GeoShapeQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * @Date 2019/7/31 17:55
 * @Created by dshuyou
 */
public class DisjointSearchBuilder extends BaseSearchBuilder {
	private String field;
	private String shapeId;

	public DisjointSearchBuilder(GeoCondition condition) {
		super(condition);
		this.field = condition.getField();
		this.shapeId = condition.getShapeId();

	}

	@Override
	public SearchSourceBuilder builder() {
		GeoShapeQueryBuilder queryBuilder = null;

		/*ShapeBuilder builder = GeoShapeType.valueOf(shapeType).getBuilder
				(coordinates,new DistanceUnit.Distance(0,DistanceUnit.fromString("km")),
				 ShapeBuilder.Orientation.fromString("ccw"),false);*/

		queryBuilder = QueryBuilders.geoDisjointQuery(field, shapeId);

		sourceBuilder.query(queryBuilder);
		ScoreSortBuilder sort = SortBuilders.scoreSort();
		sourceBuilder.sort(sort);
		return sourceBuilder;
	}
}
