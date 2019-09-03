package com.learn.util;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @Date 2019/8/28 11:12
 * @author dshuyou
 */
public class JsonUtilsTest {
	private String json;
	@Before
	public void init(){
		json = "{\n" +
				"                \"type\": \"LineString\",\n" +
				"                \"coordinates\": [\n" +
				"                    [\n" +
				"                        120.6584555,\n" +
				"                        30.45144\n" +
				"                    ],\n" +
				"                    [\n" +
				"                        120.1654515,\n" +
				"                        30.54848\n" +
				"                    ]\n" +
				"                ]\n" +
				"            }";
	}

	@Test
	public void geojson2Geometry() throws IOException {
		Geometry geometry = JsonUtils.geojson2Geometry(json);
		System.out.println(geometry);
	}

	@Test
	public void geometry2Geojson() throws IOException {
		Geometry geometry = JsonUtils.geojson2Geometry(json);
		String geojson = JsonUtils.geometry2Geojson(geometry);
		System.out.println(geojson);
	}

	@Test
	public void geometry2Wkt() throws IOException {
		Geometry geometry = JsonUtils.geojson2Geometry(json);
		String wkt = JsonUtils.geometry2Wkt(geometry);
		System.out.println(wkt);
	}

	@Test
	public void wkt2Geometry() throws ParseException, IOException {
		Geometry geometry = JsonUtils.geojson2Geometry(json);
		String wkt = JsonUtils.geometry2Wkt(geometry);
		System.out.println(JsonUtils.wkt2Geometry(wkt));
	}

	@Test
	public void wkt2Geojson() throws IOException, ParseException {
		Geometry geometry = JsonUtils.geojson2Geometry(json);
		String wkt = JsonUtils.geometry2Wkt(geometry);
		String geojson = JsonUtils.wkt2Geojson(wkt);
		System.out.println(geojson);
	}

	@Test
	public void geojson2Wkt() throws IOException {
		Geometry geometry = JsonUtils.geojson2Geometry(json);
		String geojson = JsonUtils.geometry2Geojson(geometry);
		System.out.println(JsonUtils.geojson2Wkt(geojson));
	}
}