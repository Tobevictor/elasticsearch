package com.learn.model;

/**
 * @Date 2019/8/20 10:47
 * @Created by dshuyou
 */
public class EarthquakeSuggest {
	private String input;
	private int weight = 10; // 默认权重

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}