package com.learn.model;

/**
 * @Date 2019/8/14 17:46
 * @Created by dshuyou
 */
public class Comment {
	private int id;
	private int like;
	private String username;
	private String address;
	private String date;
	private String content;

	private Comment(){

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
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
				", like=" + like +
				", username='" + username + '\'' +
				", address='" + address + '\'' +
				", date='" + date + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}
