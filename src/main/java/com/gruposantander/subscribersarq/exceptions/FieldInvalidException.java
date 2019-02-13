package com.gruposantander.subscribersarq.exceptions;

public class FieldInvalidException extends Exception {
	public static final String DESCRIPTION = "Invalid Field";

	private static final long serialVersionUID = -1344640670884805385L;

	public FieldInvalidException() {
		super(DESCRIPTION);
	}

	public FieldInvalidException(String detail) {
		super(DESCRIPTION + ". " + detail);
	}

}
