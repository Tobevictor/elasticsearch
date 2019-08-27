package com.learn.common.elasticsearch.query;

import com.learn.common.elasticsearch.query.condition.BaseCondition;
import com.learn.common.elasticsearch.query.condition.GeoCondition;
import com.learn.common.elasticsearch.query.query_enum.QueryTypeEnum;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.security.auth.login.Configuration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;

import static org.junit.Assert.*;

/**
 * @Date 2019/8/25 15:17
 * @Created by dshuyou
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GeoQueryTest {

	@Autowired
	private RestHighLevelClient client;

	private GeoQuery geoQuery;
	@Before
	public void setUp() throws Exception {
		String index = "my_index1";
		QueryTypeEnum queryType = QueryTypeEnum.geoShapeQuery;
		geoQuery = new GeoQuery(index,client,queryType);
	}

	@Test
	public void executeQuery() throws IOException {
		GeoCondition condition = new GeoCondition();
		condition.setShapeType("ENVELOPE");
		condition.setField("shape");
		double leftLatitude = 80.0;
		double leftLongitude = -150.0;
		double rightLatitude = -80.0;
		double rightLongitude = 150.0;
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

		geoQuery.executeQuery(condition);

	}

	@Test
	public void boundingBoxBuilder() {
		GeoCondition condition = new GeoCondition();
		condition.setShapeType("envelop");
		condition.setField("shape");
		double leftLatitude = 80.0;
		double leftLongitude = -150.0;
		double rightLatitude = -80.0;
		double rightLongitude = 150.0;
		condition.getBox(leftLatitude,leftLongitude,rightLatitude,rightLongitude);

		QueryBuilder builder = geoQuery.boundingBoxBuilder(condition);
		System.out.println(builder.queryName());

	}

	@Test
	public void disjoinBuilder() {
	}

	@Test
	public void distanceBuilder() {
	}

	@Test
	public void geoShapeBuilder() {
	}

	@Test
	public void intersectionBuilder() {
	}

	@Test
	public void polygonBuilder() {
	}

	@Test
	public void withinBuilder() {
	}

	@Test
	public void getShapeBuilder() {
	}
}