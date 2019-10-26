package com.yahyeet.boardbook.model.util;

/**
 * Custom exception that suggests that an invalid email was supplied
 */
public class EmailFailedException extends Exception {
	public EmailFailedException(String message) {
		super(message);
	}
}
