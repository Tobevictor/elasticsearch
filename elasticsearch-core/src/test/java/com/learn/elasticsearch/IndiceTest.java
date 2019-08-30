package com.learn.elasticsearch;

import com.learn.elasticsearch.model.Earthquake;
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
		XContentBuilder builder = indice.createMapping(earthquake);
		indice.putMapping(index,builder);
	}

	@Test
	public void putMapping() {
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
}
