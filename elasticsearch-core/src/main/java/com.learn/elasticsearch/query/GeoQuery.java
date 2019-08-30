package com.learn.elasticsearch.query;

import com.learn.elasticsearch.query.condition.BaseCondition;
import com.learn.elasticsearch.query.condition.GeoCondition;
import com.learn.elasticsearch.query.query_enum.GeoEnum;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.geo.builders.*;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.geo.geometry.ShapeType;
import org.elasticsearch.index.query.GeoPolygonQueryBuilder;
import org.elasticsearch.index.query.GeoShapeQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Date 2019/8/21 10:05
 * @author dshuyou
 */
public class GeoQuery extends BaseQuery{
	private GeoEnum queryType;

	public GeoQuery(String index, RestHighLevelClient client, GeoEnum type){
		super(index, client);
		this.queryType = type;
	}

	@Override
	public List<String> executeQuery(BaseCondition baseCondition) throws IOException {
		if(baseCondition.getFrom() != 0){
			sourceBuilder.from(baseCondition.getFrom());
		}
		if(baseCondition.getSize() != 0){
			sourceBuilder.size(baseCondition.getSize());
		}
		sourceBuilder.query(queryBuilder(baseCondition));
		if(sourceBuilder == null){
			return Collections.emptyList();
		}
		SearchRequest searchRequest = new SearchRequest(index);
		searchRequest.source(sourceBuilder);

		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHit[] hits = response.getHits().getHits();

		List<String> list = new ArrayList<>();
		for (SearchHit searchHit : hits) {
			list.add(searchHit.getSourceAsString());
			System.out.println(searchHit.getSourceAsString());
		}
		return list;
	}

	@Override
	public List<String> executeBoolQuery(Map<String, BaseCondition> conditions) {
		return null;
	}

	public QueryBuilder queryBuilder(BaseCondition baseCondition) throws IOException {
		if (!(baseCondition instanceof GeoCondition)) {
			throw new IllegalArgumentException("geoquery need geoquery condition");
		}

		GeoCondition condition = (GeoCondition) baseCondition;
		QueryBuilder queryBuilder;
		switch (queryType){
			case boundingBoxQuery:
				queryBuilder = boundingBoxBuilder(condition);
				break;
			case disjointQuery:
				queryBuilder = disjoinBuilder(condition);
				break;
			case distanceQuery:
				queryBuilder = distanceBuilder(condition);
				break;
			case geoShapeQuery:
				queryBuilder = geoShapeBuilder(condition);
				break;
			case intersectionQuery:
				queryBuilder = intersectionBuilder(condition);
				break;
			case polygonQuery:
				queryBuilder = polygonBuilder(condition);
				break;
			case withinQuery:
				queryBuilder = withinBuilder(condition);
				break;
			default:
				throw new IllegalArgumentException("not supported FullText search type");
		}
		return queryBuilder;
	}

	private QueryBuilder boundingBoxBuilder(GeoCondition condition){
		GeoPoint topLeft = condition.getTopLeft();
		GeoPoint bottomRight = condition.getBottomRight();

		return QueryBuilders.geoBoundingBoxQuery(condition.getField())
				.setCorners(topLeft, bottomRight);
	}

	private QueryBuilder disjoinBuilder(GeoCondition condition) throws IOException {
		ShapeBuilder shapeBuilder = getShapeBuilder(condition);

		return QueryBuilders.geoDisjointQuery(condition.getField(), shapeBuilder);
	}

	private QueryBuilder distanceBuilder(GeoCondition condition) {
		QueryBuilder queryBuilder = QueryBuilders.geoDistanceQuery(condition.getField())
				.point(condition.getPoint())
				.distance(condition.getDistance(), DistanceUnit.KILOMETERS);
		GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort(condition.getField(),condition.getPoint())
				.order(SortOrder.ASC)
				.unit(DistanceUnit.KILOMETERS);
		sourceBuilder.sort(sort);
		return queryBuilder;
	}

	private QueryBuilder geoShapeBuilder(GeoCondition condition) throws IOException {
		ShapeBuilder shapeBuilder = getShapeBuilder(condition);

		GeoShapeQueryBuilder queryBuilder = QueryBuilders.geoShapeQuery(condition.getField(), shapeBuilder);
		ScoreSortBuilder sort = SortBuilders.scoreSort();
		sourceBuilder.sort(sort);
		return queryBuilder;
	}

	private QueryBuilder intersectionBuilder(GeoCondition condition) throws IOException {
		ShapeBuilder shapeBuilder = getShapeBuilder(condition);

		GeoShapeQueryBuilder queryBuilder = QueryBuilders.geoIntersectionQuery(condition.getField(),shapeBuilder);
		ScoreSortBuilder sort = SortBuilders.scoreSort();
		sourceBuilder.sort(sort);
		return queryBuilder;
	}

	private QueryBuilder polygonBuilder(GeoCondition condition) {
		GeoPolygonQueryBuilder queryBuilder = QueryBuilders.geoPolygonQuery(condition.getField(),condition.getPoints());
		ScoreSortBuilder sort = SortBuilders.scoreSort();
		sourceBuilder.sort(sort);
		return queryBuilder;
	}

	private QueryBuilder withinBuilder(GeoCondition condition) throws IOException {
		ShapeBuilder shapeBuilder = getShapeBuilder(condition);

		GeoShapeQueryBuilder queryBuilder = QueryBuilders.geoWithinQuery(condition.getField(),shapeBuilder);
		GeoDistanceSortBuilder sort = SortBuilders.geoDistanceSort(condition.getField(),condition.getPoints().get(0))
				.order(SortOrder.ASC)
				.unit(DistanceUnit.KILOMETERS);
		sourceBuilder.sort(sort);
		sourceBuilder.query(queryBuilder);
		return queryBuilder;
	}


	private ShapeBuilder getShapeBuilder(GeoCondition condition){
		ShapeBuilder shapeBuilder;
		ShapeType shapeType = ShapeType.valueOf(condition.getShapeType());
		switch (shapeType){
			case POINT:
				shapeBuilder = new PointBuilder().coordinate(condition.getCoordinate());
				break;
			case ENVELOPE:
				shapeBuilder = new EnvelopeBuilder(condition.getTlCoordinate(),condition.getBrCoordinate());
				break;
			case CIRCLE:
				shapeBuilder = new CircleBuilder().center(condition.getCoordinate()).radius(condition.getDistance());
				break;
			case POLYGON:
				shapeBuilder = new PolygonBuilder(new CoordinatesBuilder().coordinates(condition.getCoordinates()));
				break;
			case LINEARRING:
				shapeBuilder = new LineStringBuilder(condition.getCoordinates());
				break;
			case MULTIPOINT:
				shapeBuilder = new MultiPointBuilder().coordinates(condition.getCoordinates());
				break;
			/*case GEOMETRYCOLLECTION:
				shapeBuilder = new GeometryCollectionBuilder().getShapeAt(condition.getShapeNumber());
				break;*/
			default:
				throw new IllegalArgumentException("not supported shape type");
		}
		return shapeBuilder;
	}
}
