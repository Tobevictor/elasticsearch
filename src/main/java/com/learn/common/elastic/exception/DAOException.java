package com.learn.common.elastic.exception;

/**
 * @author dshuyou
 * @create 2019/7/25
 *
 */
public class DAOException extends Exception {
	private static final long serialVersionUID = 5766658431948407458L;

	public DAOException() {
		super();
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}
}
