package com.learn.common.elastic.query.builder.geo;

import com.learn.common.elastic.condition.GeoCondition;
import com.learn.common.elastic.query.builder.BaseSearchBuilder;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

/**
 * @Date 2019/7/26 16:46
 * @Created by dshuyou
 */
public class DistanceSearchBuilder extends BaseSearchBuilder {
	private String field;
	private String distance;
	private GeoPoint geoPoint;

	public DistanceSearchBuilder(GeoCondition condition) {
		super(condition);
		this.field = condition.getField();
		this.distance = condition.getDistance();
		this.geoPoint = new GeoPoint(condition.getLatitude(),condition.getRightLatitude());
	}

	@Override
	public SearchSourceBuilder builder() {
		QueryBuilder qb = QueryBuilders.geoDistanceQuery(field)
				.point(geoPoint)
				.distance(distance, DistanceUnit.KILOMETERS);
		sourceBuilder.query(qb);
		GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort(field,geoPoint)
				.order(SortOrder.ASC)
				.unit(DistanceUnit.KILOMETERS);
		sourceBuilder.sort(sort);
		return sourceBuilder;
	}
}
