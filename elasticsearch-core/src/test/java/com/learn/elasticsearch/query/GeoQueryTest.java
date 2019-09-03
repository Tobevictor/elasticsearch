package com.learn.elasticsearch.query;

import com.learn.elasticsearch.EsClientInit;
import com.learn.elasticsearch.query.condition.GeoCondition;
import com.learn.elasticsearch.query.query_enum.GeoEnum;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2019/8/28 11:11
 * @Created by dshuyou
 */
public class GeoQueryTest {

	private RestHighLevelClient client;
	private GeoQuery geoQuery;
	@Before
	public void setUp(){
		client = EsClientInit.getInstance().getClient();
		String index = "earthquake";
		GeoEnum queryType = GeoEnum.geoShapeQuery;
		geoQuery = new GeoQuery(index,client,queryType);
	}

	@Test
	public void executeQuery() throws IOException {
		GeoCondition condition = new GeoCondition();
		condition.setShapeType("ENVELOPE");
		condition.setField("point");
		double leftLatitude = 80.0;
		double leftLongitude = -180.0;
		double rightLatitude = -80.0;
		double rightLongitude = 180.0;
		condition.getBox(leftLatitude,leftLongitude,rightLatitude,rightLongitude);
		/*Coordinate coordinate = new Coordinate(leftLongitude,leftLatitude);
		Coordinate coordinate1 = new Coordinate(rightLongitude,rightLatitude);
		List<Coordinate> list = new ArrayList<>();
		list.add(coordinate);
		list.add(coordinate1);
		condition.setCoordinates(list);*/
		condition.getBoxfromCoord(leftLatitude,leftLongitude,rightLatitude,rightLongitude);
		System.out.println(condition.getTlCoordinate().toString());
		System.out.println(condition.getBrCoordinate().toString());

		List<String> list = geoQuery.executeQuery(condition);
		System.out.println(list.size());

	}

	@Test
	public void boundingBoxBuilder() throws IOException {
		GeoCondition condition = new GeoCondition();
		condition.setShapeType("envelop");
		condition.setField("shape");
		double leftLatitude = 80.0;
		double leftLongitude = -150.0;
		double rightLatitude = -80.0;
		double rightLongitude = 150.0;
		condition.getBox(leftLatitude,leftLongitude,rightLatitude,rightLongitude);

		QueryBuilder builder = geoQuery.queryBuilder(condition);
		System.out.println(builder.queryName());

	}


	@Test
	public void executeBoolQuery() {
	}

	@Test
	public void queryBuilder() {
	}
}