package com.imaginnovate.exception;

/**
 * @author saiganesh
 *
 */
public class AppException extends RuntimeException{
	private static final long serialVersionUID = -6856350924008410922L;

	public AppException() {
		super("Unable to process the record...");
	}

	public AppException(String errorMessage) {
		super(errorMessage);
	}

}
