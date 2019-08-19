package com.learn.common.elastic.common;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author dshuyou
 * @create 2019/6/14
 *
 */
@Configuration
public class ElasticSearchClient {


	@Value("${elasticsearch.ip}")
	String[] ipAddress;

	//private static final String MASTER = "120.79.149.109";
	//private static final String SLAVE1 = "192.168.138.132";
	private static final int ADDRESS_LENGTH = 2;
	private static final String HTTP_SCHEME = "http";


	@Bean
	public RestClientBuilder restClientBuilder() {
		HttpHost[] hosts = Arrays.stream(ipAddress)
				.map(this::makeHttpHost)
				.filter(Objects::nonNull)
				.toArray(HttpHost[]::new);
		return RestClient.builder(hosts);
	}

	@Bean(name = "client")
	public RestHighLevelClient highLevelClient(@Autowired RestClientBuilder restClientBuilder) {
		return new RestHighLevelClient(restClientBuilder);
	}

	public static void close(@Autowired RestHighLevelClient client){
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private HttpHost makeHttpHost(String s) {
		assert StringUtils.isNotEmpty(s);
		String[] address = s.split(":");
		if (address.length == ADDRESS_LENGTH) {
			String ip = address[0];
			int port = Integer.parseInt(address[1]);
			return new HttpHost(ip, port, HTTP_SCHEME);
		} else {
			return null;
		}
	}
}
