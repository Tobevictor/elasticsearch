package com.learn.common.elastic.condition;

import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.geo.GeoShapeType;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
import org.elasticsearch.common.geo.parsers.CoordinateNode;
import org.elasticsearch.common.io.stream.StreamInput;

import java.util.List;

/**
 * @author dshuyou
 * @create 2019/7/25
 *
 */
public class GeoCondition extends QueryCondition {

	private String field;

	private Double leftLongitude;
	private Double leftLatitude;

	private Double rightLongitude;
	private Double rightLatitude;

	private Double longitude;
	private Double latitude;

	private String distance;
	private String shapeId;
	private String shapeType;
	private CoordinateNode coordinates;
	private List<GeoPoint> points;

	public GeoCondition(){
		super();
	}

	public GeoCondition(int from, int size){
		super(from, size);
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Double getLeftLongitude() {
		return leftLongitude;
	}

	public void setLeftLongitude(Double leftLongitude) {
		this.leftLongitude = leftLongitude;
	}

	public Double getLeftLatitude() {
		return leftLatitude;
	}

	public void setLeftLatitude(Double leftLatitude) {
		this.leftLatitude = leftLatitude;
	}

	public Double getRightLongitude() {
		return rightLongitude;
	}

	public void setRightLongitude(Double rightLongitude) {
		this.rightLongitude = rightLongitude;
	}

	public Double getRightLatitude() {
		return rightLatitude;
	}

	public void setRightLatitude(Double rightLatitude) {
		this.rightLatitude = rightLatitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getShapeId() {
		return shapeId;
	}

	public void setShapeId(String shapeId) {
		this.shapeId = shapeId;
	}

	public List<GeoPoint> getPoints() {
		return points;
	}

	public void setPoints(List<GeoPoint> points) {
		this.points = points;
	}

	public String getShapeType() {
		return shapeType;
	}

	public void setShapeType(String shapeType) {
		this.shapeType = shapeType;
	}

	public CoordinateNode getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(CoordinateNode coordinates) {
		this.coordinates = coordinates;
	}
}
