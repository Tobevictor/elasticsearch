package com.learn.model;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Date 2019/8/14 17:46
 * @Created by dshuyou
 */
public class Comment {
	private int id;
	private int liked;
	private String username;
	private String address;
	private String date;
	private String content;

	public Comment(){

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLiked() {
		return liked;
	}

	public void setLiked(int liked) {
		this.liked = liked;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Comment{" +
				"id=" + id +
				", liked=" + liked +
				", username='" + username + '\'' +
				", address='" + address + '\'' +
				", date='" + date + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}
