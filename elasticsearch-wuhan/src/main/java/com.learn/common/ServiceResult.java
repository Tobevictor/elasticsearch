package com.learn.common;

/**
 * @Date 2019/8/19 9:50
 * @Created by dshuyou
 */
public class ServiceResult<T> {
	private int code;
	private String message;
	private T result;

	public ServiceResult(){

	}

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
		return new ServiceResult<>(Message.OK.code);
	}

	public static <T> ServiceResult success(T result) {
		return new ServiceResult(Message.OK.code,Message.OK.value,result);
	}

	public static <T> ServiceResult successOf( String message, T result) {
		return new ServiceResult(Message.OK.code,message,result);
	}

	public static <T> ServiceResult notFound() {
		return new ServiceResult(Message.NOT_FOUND.code, Message.NOT_FOUND.getValue());
	}

	public static <T> ServiceResult isNull() {
		return new ServiceResult(Message.NOT_FOUND.code, Message.NOT_FOUND.getValue());
	}

	public static <T> ServiceResult isExist() {
		return new ServiceResult(Message.IS_EXIST.code, Message.IS_EXIST.getValue());
	}

	public static <T> ServiceResult notExist() {
		return new ServiceResult(Message.NOT_EXIST.code, Message.NOT_EXIST.getValue());
	}

	public static <T> ServiceResult ioException() {
		return new ServiceResult(Message.IOException.code, Message.IOException.value);
	}

	public enum Message {
		OK(200,"success"),
		ERROR(400, "error"),
		UNAUTHORIZED(401, "unauthorized"),
		FORBIDDEN(403,"forbidden"),
		NOT_FOUND(404,"Not Found Resource"),
		NULL(405,"NULL Resource"),
		IS_EXIST(406,"IS EXIST Resource"),
		NOT_EXIST(407,"NOT EXIST Resource"),
		IOException(408,"IOException");

		private int code;
		private String value;

		Message(int code,String value) {
			this.code = code;
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
