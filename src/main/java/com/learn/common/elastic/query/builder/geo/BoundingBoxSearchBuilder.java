package com.learn.common.elastic.query.builder.geo;

import com.learn.common.elastic.condition.GeoCondition;
import com.learn.common.elastic.query.builder.BaseSearchBuilder;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.GeoBoundingBoxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;

/**
 * @Date 2019/7/26 16:43
 * @Created by dshuyou
 */
public class BoundingBoxSearchBuilder extends BaseSearchBuilder {
	private String field;
	private GeoPoint topLeft;
	private GeoPoint bottomRight;

	public BoundingBoxSearchBuilder(GeoCondition condition){
		super(condition);
		this.field = condition.getField();
		this.topLeft = new GeoPoint(condition.getLeftLatitude(),condition.getLeftLongitude());
		this.bottomRight = new GeoPoint(condition.getRightLatitude(),condition.getRightLongitude());

		/*if (condition.getLeftLatitude() < condition.getRightLatitude() ||
				condition.getLeftLongitude() > condition.getRightLongitude()) {
			throw new IllegalArgumentException("bad args of geo points");
		}*/
	}

	@Override
	public SearchSourceBuilder builder() {
		GeoBoundingBoxQueryBuilder qb = QueryBuilders.geoBoundingBoxQuery(field)
				.setCorners(topLeft, bottomRight);
		sourceBuilder.query(qb);
		ScoreSortBuilder sort = SortBuilders.scoreSort();
		sourceBuilder.sort(sort);
		return sourceBuilder;
	}
}
