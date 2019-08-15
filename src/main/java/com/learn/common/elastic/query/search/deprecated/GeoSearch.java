package com.learn.common.elastic.query.search.deprecated;

import org.elasticsearch.common.geo.GeoPoint;

import java.util.List;

/**
 * @author dshuyou
 * @create 2019/7/17
 *
 */
public interface GeoSearch {

	List<String> searchGeoPolygon(String type, String field, List<GeoPoint> points, String index);

	List<String> searchGeoBoundingBox(String type, String field, GeoPoint topLeft, GeoPoint bottomRight, String index);

	List<String> searchGeoDistance(String type, String field, String distance, GeoPoint point, String index);
}
