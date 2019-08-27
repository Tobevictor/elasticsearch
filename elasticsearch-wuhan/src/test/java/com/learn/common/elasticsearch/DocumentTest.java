package com.learn.common.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Date 2019/8/25 15:14
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
		index = "beijing";
	}
	@Test
	public void index() {

	}

	@Test
	public void index1() {
	}

	@Test
	public void insertAsync() {
	}

	@Test
	public void delete() {
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