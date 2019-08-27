package com.learn.common.elasticsearch;

import com.learn.model.Earthquake;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * @Date 2019/8/25 15:15
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
		index = "my_index";
	}

	@Test
	public void create() {
		try {
			indice.create(index);
			System.out.println("create success");
		} catch (IOException e) {
			System.out.println("create failed");
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
		indice.createMapping(index,earthquake);
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

		Map mapping = null;
		try {
			mapping = indice.getMapping(index);
			Iterator<Map.Entry> it = mapping.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = it.next();
				System.out.println(entry.getKey()+"   "+entry.getValue());
			}
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