package com.mx.client.webtools;

public class CryptorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3822472763475733413L;

	public CryptorException(Exception e) {
		super(e);
	}

	public CryptorException(String s) {
		super(s);
	}

}
