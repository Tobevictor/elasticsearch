package com.learn.common.elastic.condition;

import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.geo.builders.ShapeBuilder;

import java.util.List;

/**
 * @author dshuyou
 * @create 2019/7/25
 *
 */
public class GeoCondition extends QueryCondition {

	private String field;
	private GeoPoint topLeft;
	private GeoPoint bottomRight;
	private List<GeoPoint> points;
	private String distance;
	private GeoPoint point;
	private ShapeBuilder shape;

	public GeoCondition(String field, GeoPoint topLeft, GeoPoint bottomRight){
		super();
		this.field = field;
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}

	public GeoCondition(int from, int size, String field, GeoPoint topLeft, GeoPoint bottomRight){
		super(from, size);
		this.field = field;
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}

	public GeoCondition(String field, List<GeoPoint> points){
		this.field = field;
		this.points = points;
	}

	public GeoCondition(int from, int size, String field, List<GeoPoint> points){
		super(from, size);
		this.field = field;
		this.points = points;
	}

	public GeoCondition(String field, String distance, GeoPoint point){
		this.field = field;
		this.distance = distance;
		this.point = point;
	}

	public GeoCondition(int from, int size, String field, String distance, GeoPoint point){
		super(from, size);
		this.field = field;
		this.distance = distance;
		this.point = point;
	}

	public GeoCondition(String field, ShapeBuilder shape){
		this.field = field;
		this.shape = shape;
	}

	public GeoCondition(int from, int size, String field, ShapeBuilder shape){
		super(from, size);
		this.field = field;
		this.shape = shape;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public GeoPoint getTopLeft() {
		return topLeft;
	}

	public void setTopLeft(GeoPoint topLeft) {
		this.topLeft = topLeft;
	}

	public GeoPoint getBottomRight() {
		return bottomRight;
	}

	public void setBottomRight(GeoPoint bottomRight) {
		this.bottomRight = bottomRight;
	}

	public List<GeoPoint> getPoints() {
		return points;
	}

	public void setPoints(List<GeoPoint> points) {
		this.points = points;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public GeoPoint getPoint() {
		return point;
	}

	public void setPoint(GeoPoint point) {
		this.point = point;
	}

	public ShapeBuilder getShape() {
		return shape;
	}

	public void setShape(ShapeBuilder shape) {
		this.shape = shape;
	}
}
