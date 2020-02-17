package com.kuang.jwt.exception;

/**
 * 未找到对应实体.
 *
 */
public class EntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1;

	public EntityNotFoundException() {
		super();
	}

	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityNotFoundException(String message) {
		super(message);
	}

	public EntityNotFoundException(Throwable cause) {
		super(cause);
	}
}
