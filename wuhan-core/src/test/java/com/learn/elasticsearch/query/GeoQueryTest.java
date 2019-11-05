package com.learn.elasticsearch.query;

import com.learn.elasticsearch.EsClientInit;
import com.learn.elasticsearch.query.condition.GeoCondition;
import com.learn.elasticsearch.query.model.DataContent;
import com.learn.elasticsearch.query.query_enum.GeoEnum;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.junit.Before;
import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;

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
		String index = "dshuyou1";
		//GeoEnum queryType = GeoEnum.geoShapeQuery;
		GeoEnum queryType = GeoEnum.intersectionQuery;
		geoQuery = new GeoQuery(index,client,queryType);
	}

	@Test
	public void distance() throws IOException {
		GeoCondition condition = new GeoCondition();
		condition.setField("address");
		condition.setPoint(50,30);
		condition.setDistance(String.valueOf(10000));
		DataContent dataContent = geoQuery.executeQuery(condition);
		System.out.println(dataContent);
	}

	@Test
	public void disjoin() throws IOException {
		GeoCondition condition = new GeoCondition();
		condition.setField("point");
		condition.setShapeType("POLYGON");
		List<Coordinate> list = new ArrayList<>();
		list.add(new Coordinate(-20,20));
		list.add(new Coordinate(20,20));
		list.add(new Coordinate(20,-20));
		list.add(new Coordinate(-20,-20));
		list.add(new Coordinate(-20,20));
		condition.setCoordinates(list);
		DataContent dataContent = geoQuery.executeQuery(condition);
		System.out.println(dataContent);
	}

	@Test
	public void polygon1() throws IOException {
		GeoCondition condition = new GeoCondition();
		condition.setField("address");

		List<GeoPoint> list = new ArrayList<>();
		list.add(new GeoPoint(-80,180));
		list.add(new GeoPoint(80,180));
		list.add(new GeoPoint(80,-180));
		list.add(new GeoPoint(-80,-180));
		list.add(new GeoPoint(-80,180));
		condition.setPoints(list);
		DataContent dataContent = geoQuery.executeQuery(condition);
		System.out.println(dataContent);
	}

	@Test
	public void whthin() throws IOException {
		GeoCondition condition = new GeoCondition();
		condition.setField("point");
		condition.setShapeType("POLYGON");
		List<Coordinate> list = new ArrayList<>();
		list.add(new Coordinate(-180,80));
		list.add(new Coordinate(180,80));
		list.add(new Coordinate(180,-80));
		list.add(new Coordinate(-180,-80));
		list.add(new Coordinate(-180,80));
		condition.setCoordinates(list);
		DataContent dataContent = geoQuery.executeQuery(condition);
		System.out.println(dataContent);
	}

	@Test
	public void Intersection() throws IOException {
		GeoCondition condition = new GeoCondition();
		condition.setField("point");
		condition.setShapeType("POLYGON");
		List<Coordinate> list = new ArrayList<>();
		list.add(new Coordinate(-180,80));
		list.add(new Coordinate(180,80));
		list.add(new Coordinate(180,-80));
		list.add(new Coordinate(-180,-80));
		list.add(new Coordinate(-180,80));
		condition.setCoordinates(list);
		DataContent dataContent = geoQuery.executeQuery(condition);
		System.out.println(dataContent);
	}

	@Test
	public void boundingbox() throws IOException {
		GeoCondition condition = new GeoCondition();
		condition.setField("address");
		condition.setBox(80,-180,-80,180);
		DataContent dataContent = geoQuery.executeQuery(condition);
		System.out.println(dataContent);
	}

	@Test
	public void envelop() throws IOException {
		GeoCondition condition = new GeoCondition();
		condition.setShapeType("ENVELOPE");
		condition.setField("point");
		double leftLatitude = 80.0;
		double leftLongitude = -180.0;
		double rightLatitude = -80.0;
		double rightLongitude = 180.0;

		condition.setBoxfromCoord(leftLatitude,leftLongitude,rightLatitude,rightLongitude);
		System.out.println(condition.getTlCoordinate().toString());
		System.out.println(condition.getBrCoordinate().toString());

		DataContent dataContent = geoQuery.executeQuery(condition);
		System.out.println(dataContent);
	}

	@Test
	public void polygon() throws IOException {
		GeoCondition condition = new GeoCondition();
		condition.setField("point");
		condition.setShapeType("POLYGON");
		List<Coordinate> list = new ArrayList<>();
		list.add(new Coordinate(-180,80));
		list.add(new Coordinate(180,80));
		list.add(new Coordinate(180,-80));
		list.add(new Coordinate(-180,-80));
		list.add(new Coordinate(-180,80));
		condition.setCoordinates(list);
		DataContent dataContent = geoQuery.executeQuery(condition);
		System.out.println(dataContent);
	}

	@Test
	public void point() throws IOException {
		GeoCondition condition = new GeoCondition();
		condition.setField("point");
		condition.setShapeType("POINT");

		condition.setCoordinate(80,-180);
		DataContent dataContent = geoQuery.executeQuery(condition);
		System.out.println(dataContent);
	}

	@Test
	@Deprecated
	public void circle() throws IOException {
		GeoCondition condition = new GeoCondition();
		condition.setField("point");
		condition.setShapeType("CIRCLE");

		condition.setCoordinate(80,-180);
		condition.setDistance(String.valueOf(1000000));
		DataContent dataContent = geoQuery.executeQuery(condition);
		System.out.println(dataContent);
	}
	@Test
	public void linestring() throws IOException {
		GeoCondition condition = new GeoCondition();
		condition.setField("point");
		condition.setShapeType("LINEARRING");
		List<Coordinate> list = new ArrayList<>();
		list.add(new Coordinate(-180,80));
		list.add(new Coordinate(180,80));
		list.add(new Coordinate(180,-80));
		list.add(new Coordinate(-180,-80));

		condition.setCoordinates(list);
		DataContent dataContent = geoQuery.executeQuery(condition);
		System.out.println(dataContent);
	}

	@Test
	@Deprecated
	public void multipoint() throws IOException {
		GeoCondition condition = new GeoCondition();
		condition.setField("point");
		condition.setShapeType("MULTIPOINT");
		List<Coordinate> list = new ArrayList<>();
		list.add(new Coordinate(-180,80));
		list.add(new Coordinate(180,80));
		list.add(new Coordinate(180,-80));
		list.add(new Coordinate(-180,-80));

		condition.setCoordinates(list);
		DataContent dataContent = geoQuery.executeQuery(condition);
		System.out.println(dataContent);
	}

}