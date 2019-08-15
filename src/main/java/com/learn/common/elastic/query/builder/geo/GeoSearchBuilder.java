package com.learn.common.elastic.query.builder.geo;

import com.learn.common.elastic.query.builder.BaseSearchBuilder;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoBoundingBoxQueryBuilder;
import org.elasticsearch.index.query.GeoPolygonQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;

/**
 * @author dshuyou
 * @create 2019/7/11
 *
 */
@Deprecated
public class GeoSearchBuilder extends BaseSearchBuilder {

	public static SearchSourceBuilder builtPolygonQuery(String field, List<GeoPoint> points) throws Exception {
		if (points == null || points.size() <= 0) {
			throw new Exception("bad args of geo points");
		}
		SearchSourceBuilder srb = new SearchSourceBuilder();
		GeoPolygonQueryBuilder qb = QueryBuilders.geoPolygonQuery(field, points);
		srb.query(qb);
		return srb;
	}

	/**
	 * 针对geo_point类型
	 * 获取在指定矩形框内的数据
	 * @param field 字段
	 * @param topLeft 矩形左上边界
	 * @param bottomRight 矩形右下边界
	 * @return
	 * @throws Exception
	 */
	public static SearchSourceBuilder builtBoundingBoxQuery(String field, GeoPoint topLeft, GeoPoint bottomRight) throws Exception {
		if (topLeft == null || bottomRight == null) {
			throw new Exception("bad args of geo points");
		}
		SearchSourceBuilder srb = new SearchSourceBuilder();
		GeoBoundingBoxQueryBuilder qb = QueryBuilders.geoBoundingBoxQuery(field)
				.setCorners(topLeft, bottomRight);
		srb.query(qb);
		return srb;
	}

	/**
	 * 针对geo_point类型
	 * 查找在给定的中心点确定范围内的数据
	 * @param field
	 * @param distance
	 * @param point
	 * @return
	 * @throws Exception
	 */
	public static SearchSourceBuilder builtDistanceQuery( String field,String distance, GeoPoint point) throws Exception {
		if (point == null) {
			throw new Exception("bad args of geo points");
		}
		SearchSourceBuilder srb = new SearchSourceBuilder();
		QueryBuilder qb = QueryBuilders.geoDistanceQuery(field)
				.point(point)
				.distance(distance, DistanceUnit.KILOMETERS);
		srb.query(qb);
		GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort(field,point)
				.order(SortOrder.ASC)
				.unit(DistanceUnit.KILOMETERS);
		srb.sort(sort);
		return srb;
	}

	@Override
	public SearchSourceBuilder builder() {
		return null;
	}

}

