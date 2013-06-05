package com.mx.client.webtools;


public class PassPhrase {
	private PassPhrase() {
	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		public static final PassPhrase INSTANCE = new PassPhrase();
	}

	public static PassPhrase getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * ÃÜÂë×î¶Ì³¤¶È
	 */
	private static final int MIN_LENGTH = 16;

	/**
	 * The random number generator.
	 */
	private static final java.util.Random r = new java.util.Random();

	/*
	 * Set of characters that is valid. Must be printable, memorable, and
	 * "won't break HTML" (i.e., not '<', '>', '&', '=', ...). or break shell
	 * commands (i.e., not '<', '>', '$', '!', ...). I, L and O are good to
	 * leave out, as are numeric zero and one.
	 */
	private static final char[] goodChar = {
			// Comment out next two lines to make upper-case-only, then
			// use String toUpper() on the user's input before validating.
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '@', };

	/* Generate a Password object with a random password. */
	public String getNext() {
		return getNext(MIN_LENGTH);
	}

	/* Generate a Password object with a random password. */
	public String getNext(int length) {
		if (length < 1) {
			throw new IllegalArgumentException("Ridiculous password length " + length);
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(goodChar[r.nextInt(goodChar.length)]);
		}
		return sb.toString();
	}
}
