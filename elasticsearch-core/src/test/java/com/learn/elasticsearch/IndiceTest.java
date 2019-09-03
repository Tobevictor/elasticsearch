package com.learn.elasticsearch;

import com.vividsolutions.jts.geom.Geometry;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


/**
 * @Date 2019/8/28 11:11
 * @Created by dshuyou
 */
public class IndiceTest {

	private Indice indice;
	private RestHighLevelClient client;
	private String index;
	@Before
	public void init(){
		client = EsClientInit.getInstance().getClient();
		indice = new Indice(client);
		index = "earthquake";
	}

	@Test
	public void create() {
		try {
			indice.create(index);
			System.out.println("create index:" + index +" success");
		} catch (IOException e) {
			System.out.println("create index:" + index +" failed");
		}
	}

	@Test
	public void createIndexWithSettings() {
	}

	@Test
	public void create1() {
	}

	@Test
	public void createMapping() throws IOException {
		Earthquake earthquake = new Earthquake();
		System.out.println(indice.createMapping(earthquake));
	}

	@Test
	public void putMapping() throws IOException {
		Earthquake earthquake = new Earthquake();
		XContentBuilder builder = indice.createMapping(earthquake);
		indice.putMapping(index,builder);
	}

	@Test
	public void createAsync() {
	}

	@Test
	public void getSetting() {
		try {
			String setting = indice.getSetting(index);
			System.out.println(setting);
		} catch (IOException e) {
			System.out.println("get setting failed");
		}

	}

	@Test
	public void getMapping() {
		try {
			System.out.println(indice.getMapping(index));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void deleteIndex() {
		try {
			indice.deleteIndex(index);
			System.out.println("delete index success");
		} catch (IOException e) {
			System.out.println("delete index failed");
		}
	}

	@Test
	public void isExists() {
		boolean result = indice.isExists(index);
		System.out.println(result);
	}

	@Test
	public void get() {

	}

	@Test
	public void refresh() {
	}

	@Test
	public void clearCache() {
	}

	@Test
	public void flush() {
	}

	@Test
	public void addAlias() {
	}

	private static class Earthquake{
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
	private static class Comment{
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
}
