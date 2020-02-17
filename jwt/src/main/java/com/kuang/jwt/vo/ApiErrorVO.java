package com.kuang.jwt.vo;


import java.util.Arrays;
import java.util.List;

public class ApiErrorVO {
	/**
	 * HTTP Status Code.
	 */
	private int status;

	/**
	 * Error Message.
	 */
	private String message;

	/**
	 * Detailed Error Info.
	 */
	private List<String> errors;


	public ApiErrorVO() {
		super();
	}

	public ApiErrorVO(final int status, final String message, final List<String> errors) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

	public ApiErrorVO(final int status, final String message, final String error) {
		super();
		this.status = status;
		this.message = message;
		errors = Arrays.asList(error);
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(final int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(final List<String> errors) {
		this.errors = errors;
	}

	public void setError(final String error) {
		errors = Arrays.asList(error);
	}
}
