package com.kes.ip.exception;

public class ProductServiceBusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductServiceBusinessException(String message) {
		super(message);
	}
}
