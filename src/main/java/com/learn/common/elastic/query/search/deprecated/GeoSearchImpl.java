package com.learn.common.elastic.query.search.deprecated;

import com.learn.common.elastic.query.builder.geo.GeoSearchBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dshuyou
 * @create 2019/7/11
 *
 */
public class GeoSearchImpl implements GeoSearch {
	private static final Logger logger = LoggerFactory.getLogger(GeoSearchImpl.class);
	private RestHighLevelClient client;

	public GeoSearchImpl(RestHighLevelClient client){
		this.client = client;
	}

	/**
	 * 查找位于多边形中的位置
	 * @param index 索引
	 * @param type  类型
	 * @param field 索引字段
	 * @param points  构成多边形的点
	 *
	 */
	@Override
	public List<String> searchGeoPolygon(String type, String field, List<GeoPoint> points,
										 String index) {
		SearchSourceBuilder builder = null;
		try {
			builder = GeoSearchBuilder.builtPolygonQuery(field, points);
		} catch (Exception e) {
			logger.error("error!", e);
		}
		return search(type, builder,index);
	}

	/**
	 * 查询矩形范围内的数据
	 * @param index index
	 * @param type  type
	 * @param field 索引字段
	 * @param bottomRight 右下坐标
	 * @param topLeft 左上坐标
	 *
	 */
	@Override
	public List<String> searchGeoBoundingBox(String type, String field, GeoPoint topLeft,
											 GeoPoint bottomRight, String index) {
		SearchSourceBuilder builder = null;
		try {
			builder = GeoSearchBuilder.builtBoundingBoxQuery(field,topLeft,bottomRight);
		} catch (Exception e) {
			logger.error("error!", e);
		}
		return search(type, builder,index);
	}

	/**
	 * 查询距离中心点指定的范围内的位置
	 * @param index index
	 * @param type type
	 * @param field 索引字段
	 * @param distance 距离
	 * @param point 中心点
	 *
	 */
	@Override
	public List<String> searchGeoDistance(String type, String field, String distance,
										  GeoPoint point, String index) {
		SearchSourceBuilder builder = null;
		try {
			builder = GeoSearchBuilder.builtDistanceQuery(field,distance,point);
		} catch (Exception e) {
			logger.error("error!", e);
		}
		return search(type, builder,index);
	}

	private List<String> search(String type, SearchSourceBuilder builder, String index) {
		try {
			SearchRequest srb = new SearchRequest(index);
			if (type != null && type.length() != 0){
				srb.searchType(type);
			}
			srb.source(builder);
			SearchResponse searchResponse = client.search(srb, RequestOptions.DEFAULT);
			SearchHit[] hits = searchResponse.getHits().getHits();
			List<String> list = new ArrayList<>();

			for (SearchHit searchHit : hits) {
				list.add(searchHit.getSourceAsString());
			}
			return list;
		} catch (Exception e) {
			logger.error("error!", e);
		}
		return null;
	}
}
