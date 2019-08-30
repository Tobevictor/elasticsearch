package com.learn.util;

import org.geotools.geojson.geom.GeometryJSON;
import com.vividsolutions.jts.geom.Geometry;

import java.io.*;
/**
 * @Date 2019/8/24 16:48
 * @author dshuyou
 */
public class JsonUtils {


	public String evaluate(String geoJson) {
		String ret = null;
		GeometryJSON gjson = new GeometryJSON();
		Reader reader = new StringReader(geoJson);
		try {
			Geometry geometry = gjson.read(reader);
			ret = geometry.toText();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static Geometry geojson2Geometry(String geoJson) throws IOException {
		GeometryJSON json = new GeometryJSON();
		StringReader bf = new StringReader(geoJson);
		return json.read(bf);
	}

	public static String geometry2Geojson(Geometry geometry) throws IOException {
		GeometryJSON json = new GeometryJSON();
		StringWriter writer = new StringWriter();
		json.write(geometry,writer);
		return writer.toString();
	}

	public static void main(String[] args) throws IOException {
		JsonUtils jsonUtils = new JsonUtils();
		String json = "{\n" +
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
		Geometry geometry = geojson2Geometry(json);
		String geojson = geometry2Geojson(geometry);
		System.out.println(geojson);
		System.out.println(geometry.toText());

	}
}
