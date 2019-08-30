package com.learn.common;

/**
 * @Date 2019/8/19 9:50
 * @Created by dshuyou
 */
public class ServiceResult<T> {
	private int code;
	private String message;
	private T result;

	public ServiceResult(int code) {
		this.code = code;
	}

	public ServiceResult(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public ServiceResult(int code, String message, T result) {
		this.code = code;
		this.message = message;
		this.result = result;
	}

	public int getCode() {
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

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public static <T> ServiceResult<T> success() {
		return new ServiceResult<>(200);
	}

	public static <T> ServiceResult<T> successOf( T result) {
		ServiceResult<T> serviceResult = new ServiceResult<>(200);
		serviceResult.setResult(result);
		return serviceResult;
	}

	public static <T> ServiceResult<T> failedOf( T result) {
		ServiceResult<T> serviceResult = new ServiceResult<>(201);
		serviceResult.setResult(result);
		return serviceResult;
	}

	public static <T> ServiceResult<T> failed() {
		return new ServiceResult<>(201);
	}

	public static <T> ServiceResult<T> notFound() {
		return new ServiceResult<>(404, Message.NOT_FOUND.getValue());
	}

	public enum Message {
		NOT_FOUND("Not Found Resource!"),
		IS_EXIST("IS EXIST Resource!");

		private String value;

		Message(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
