package com.learn.common.result;

import org.elasticsearch.rest.RestStatus;

/**
 * @author dshuyou
 * @create 2019/7/9
 *
 */
public class ElasticResult<T> {
	private int code;
	private String message;
	private T data;

	public ElasticResult(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public ElasticResult() {
	}

	/**
	 * 成功返回结果
	 *
	 * @param data 获取的数据
	 */
	public static <T> ElasticResult<T> success(T data) {
		return new ElasticResult<T>(RestStatus.OK.getStatus(),null, data);
	}

	/**
	 * 成功返回结果
	 *
	 * @param data 获取的数据
	 * @param  message 提示信息
	 */
	public static <T> ElasticResult<T> success(String message, T data) {
		return new ElasticResult<T>(RestStatus.OK.getStatus(), message, data);
	}

	/**
	 * 失败返回结果
	 */
	public static <T> ElasticResult<T> failed(int code, String message, T data) {
		return new ElasticResult<T>(code,message,data);
	}

	/**
	 * 失败返回结果
	 */
	public static <T> ElasticResult<T> failed(String message, T data) {
		return new ElasticResult<T>(RestStatus.BAD_REQUEST.getStatus(),message,data);
	}

	public long getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}

