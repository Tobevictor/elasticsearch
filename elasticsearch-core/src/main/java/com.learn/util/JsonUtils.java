package com.learn.util;

import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import org.geotools.geojson.geom.GeometryJSON;
import com.vividsolutions.jts.geom.Geometry;

import java.io.*;
/**
 * @Date 2019/8/24 16:48
 * @author dshuyou
 */
public class JsonUtils {

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

	public static String wkt2Geojson(String wkt) throws ParseException, IOException {
		String json = null;
		WKTReader wktReader = new WKTReader();
		Geometry geometry = wktReader.read(wkt);
		return geometry2Geojson(geometry);
	}

	public static String geojson2Wkt(String geojson) throws IOException {
		String wkt = null;
		Geometry geometry = geojson2Geometry(geojson);
		WKTWriter wktWriter = new WKTWriter();
		return wktWriter.write(geometry);
	}

	public static String geometry2Wkt(Geometry geometry){
		WKTWriter wktWriter = new WKTWriter();
		return wktWriter.write(geometry);
	}

	public static Geometry wkt2Geometry(String wkt) throws ParseException {
		WKTReader wktReader = new WKTReader();
		return wktReader.read(wkt);
	}
}
