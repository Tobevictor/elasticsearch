package com.learn.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.learn.model.Earthquake;
import com.vividsolutions.jts.io.WKTReader;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.geotools.geojson.geom.GeometryJSON;
import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.validator.constraints.EAN;

import javax.management.ObjectName;
import java.io.*;
import java.util.*;

/**
 * @Date 2019/8/24 16:48
 * @Created by dshuyou
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
