package com.learn.common.elastic.common.result;

import org.elasticsearch.rest.RestStatus;

/**
 * @author dshuyou
 * @create 2019/7/9
 *
 */
public class CommonResult<T> {
	private int code;
	private String message;
	private T data;

	public CommonResult(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public CommonResult() {
	}

	/**
	 * 成功返回结果
	 *
	 * @param data 获取的数据
	 */
	public static <T> CommonResult<T> success(T data) {
		return new CommonResult<T>(RestStatus.OK.getStatus(),null, data);
	}

	/**
	 * 成功返回结果
	 *
	 * @param data 获取的数据
	 * @param  message 提示信息
	 */
	public static <T> CommonResult<T> success( String message,T data) {
		return new CommonResult<T>(RestStatus.OK.getStatus(), message, data);
	}

	/**
	 * 失败返回结果
	 */
	public static <T> CommonResult<T> failed(RestStatus status,T data){
		return new CommonResult<T>(status.getStatus(),null,data);
	}

	/**
	 * 失败返回结果
	 */
	public static <T> CommonResult<T> failed(RestStatus status) {
		return new CommonResult<T>(status.getStatus(), null,null);
	}

	/**
	 * 失败返回结果
	 */
	public static <T> CommonResult<T> failed(RestStatus status,String message,T data) {
		return new CommonResult<T>(status.getStatus(),message,data);
	}
//
//	/**
//	 * 参数验证失败返回结果
//	 */
//	public static <T> CommonResult<T> validateFailed() {
//		return failed(ResultCode.VALIDATE_FAILED);
//	}
//
//	/**
//	 * 参数验证失败返回结果
//	 * @param message 提示信息
//	 */
//	public static <T> CommonResult<T> validateFailed(String message) {
//		return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
//	}
//
//	/**
//	 * 未登录返回结果
//	 */
//	public static <T> CommonResult<T> unauthorized(T data) {
//		return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
//	}
//
//	/**
//	 * 未授权返回结果
//	 */
//	public static <T> CommonResult<T> forbidden(T data) {
//		return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
//	}


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

