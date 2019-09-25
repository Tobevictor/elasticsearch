package com.learn.common;

import org.springframework.http.HttpStatus;

import java.util.Objects;

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
		return new ServiceResult<>(HttpStatus.OK.value());
	}

	public static <T> ServiceResult success(T result) {
		return new ServiceResult(HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),result);
	}

	public static <T> ServiceResult successOf( String message, T result) {
		return new ServiceResult(HttpStatus.OK.value(),message,result);
	}

	public static <T> ServiceResult notFound() {
		return new ServiceResult(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
	}

	public static <T> ServiceResult isNull() {
		return new ServiceResult(HttpStatus.NO_CONTENT.value(),HttpStatus.NO_CONTENT.getReasonPhrase());
	}

	public static <T> ServiceResult isExist() {
		return new ServiceResult(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
	}

	public static <T> ServiceResult internalServerError() {
		return new ServiceResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof ServiceResult)) {
			return false;
		}
		ServiceResult<?> that = (ServiceResult<?>) object;
		return getCode() == that.getCode() &&
				Objects.equals(getMessage(), that.getMessage()) &&
				Objects.equals(getResult(), that.getResult());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCode(), getMessage());
	}

	public static void main(String[] args){
		ServiceResult serviceResult = ServiceResult.isExist();
		ServiceResult serviceResult1 = ServiceResult.isExist();
		System.out.println(serviceResult.hashCode());
		System.out.println(serviceResult1.hashCode());
		System.out.println(serviceResult == serviceResult1);
		System.out.println(serviceResult.equals(serviceResult1));
		String s = new String("123");
		String ss = new String("123");
		System.out.println(s.equals(ss));
		System.out.println(s == ss);
		System.out.println(s.hashCode());
		System.out.println(ss.hashCode());
	}
}
