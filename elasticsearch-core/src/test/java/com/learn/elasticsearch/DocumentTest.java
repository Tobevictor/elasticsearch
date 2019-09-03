package com.learn.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2019/8/28 11:11
 * @Created by dshuyou
 */
public class DocumentTest {

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
	public void index() throws IOException {
		Document document = new Document(client);
		Map<String, Object> map = new HashMap<>();
		map.put("point","POINT(-118.1850357 34.1645203)");
		Object o = document.index(index,map);
		System.out.println(o);
	}

	@Test
	public void index1() throws IOException {
		String json = "{\"mag\":3.69," +
				"\"depth\":6.0," +
				"\"latitude\":37.4333333," +
				"\"id\":2," +
				"\"time\":\"1970-01-01T19:49:24.730Z\"," +
				" \"point\":{\"type\": \"Point\", \"coordinates\": [-118.7435, 37.4333333]},"+
				"\"longitude\":-118.7435}";

		Document document = new Document(client);
		Object object = document.index(index,json);
		System.out.println(object);
	}

	@Test
	public void insertAsync() {
	}

	@Test
	public void delete() throws IOException {
		Document document = new Document(client);
		boolean result = document.delete(index,"s5zQ3GwBiAEaKMA44SCd");
		System.out.println(result);
	}

	@Test
	public void count() {
	}

	@Test
	public void isIdExists() {
	}

	@Test
	public void get() {
	}

	@Test
	public void getFieldsValues() {
	}

	@Test
	public void multiGet() {
	}

	@Test
	public void update() {
	}

	@Test
	public void generateRequests() {
	}

	@Test
	public void bulkIndex() {


	}

	@Test
	public void bulkIndex1() {
	}

	@Test
	public void bulkUpdate() {
	}

	@Test
	public void batchInsert() {
	}

	@Test
	public void batchAscendingId() {
	}
}