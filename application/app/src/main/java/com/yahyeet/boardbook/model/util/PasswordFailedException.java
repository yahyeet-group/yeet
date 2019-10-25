package com.yahyeet.boardbook.model.util;

/**
 * Custom exception that suggests that an incorrect password was supplied
 */
public class PasswordFailedException extends Exception {
	public PasswordFailedException(String message) {
		super(message);
	}
}
