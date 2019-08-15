package com.learn.common.elastic.query.builder.geo;

import com.learn.common.elastic.condition.GeoCondition;
import com.learn.common.elastic.query.builder.BaseSearchBuilder;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoPolygonQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;

/**
 * @Date 2019/7/26 16:19
 * @Created by dshuyou
 */
public class PolygonSearchBuilder extends BaseSearchBuilder {
	private String field;
	private List<GeoPoint> points;

	public PolygonSearchBuilder(GeoCondition condition) {
		super(condition);
		this.field = condition.getField();
		this.points = condition.getPoints();
		if (points == null || points.size() <= 0) {
			throw new IllegalArgumentException("bad args of geo points");
		}
	}

	@Override
	public SearchSourceBuilder builder() {

		GeoPolygonQueryBuilder qb = QueryBuilders.geoPolygonQuery(field, points);
		GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort(field,points.get(0))
				.order(SortOrder.ASC)
				.unit(DistanceUnit.KILOMETERS);
		sourceBuilder.sort(sort);
		sourceBuilder.query(qb);
		return sourceBuilder;
	}
}
