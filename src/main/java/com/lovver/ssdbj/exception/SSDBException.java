package com.lovver.ssdbj.exception;

public class SSDBException extends Exception {
	public SSDBException() {
		super();
	}

	public SSDBException(String message, Throwable cause) {
		super(message, cause);
	}

	public SSDBException(String message) {
		super(message);
	}

	public SSDBException(Throwable cause) {
		super(cause);
	}
}
