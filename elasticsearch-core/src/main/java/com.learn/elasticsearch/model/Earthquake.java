package com.learn.elasticsearch.model;

import com.google.common.collect.Lists;
import com.vividsolutions.jts.geom.Geometry;

import java.util.List;

/**
 * @Date 2019/8/20 10:38
 * @Created by dshuyou
 */
public class Earthquake {

	private Long id;
	private String time;
	private double latitude;
	private double longitude;
	private double depth;
	private double mag;
	private Geometry point;

	public Earthquake(){
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getDepth() {
		return depth;
	}

	public void setDepth(double depth) {
		this.depth = depth;
	}

	public double getMag() {
		return mag;
	}

	public void setMag(double mag) {
		this.mag = mag;
	}

	public List<String> getAnalyzedField(){
		return Lists.newArrayList("time");
	}

	@Override
	public String toString() {
		return "Earthquake{" +
				"id=" + id +
				", time='" + time + '\'' +
				", latitude=" + latitude +
				", longitude=" + longitude +
				", depth=" + depth +
				", mag=" + mag +
				'}';
	}

}
