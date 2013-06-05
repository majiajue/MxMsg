package com.mx.client.webtools;

import java.util.HashMap;
import java.util.Map;


public class CryptorManager {
	// Private constructor prevents instantiation from other classes
	private CryptorManager() {
	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		public static final CryptorManager INSTANCE = new CryptorManager();
	}

	public static CryptorManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private final Map<Long, Cryptor> mCryptorMaps = new HashMap<Long, Cryptor>();

	public Cryptor getCryptor() throws Exception {
		long id = Thread.currentThread().getId();
		Cryptor c = mCryptorMaps.get(id);
		if (c == null) {
			String password = SConfig.getInstance().getApplicationPSK();
			c = new Cryptor(password);
			mCryptorMaps.put(id, c);
		}

		return c;
	}

	public void clearCryptor() {
		mCryptorMaps.clear();
	}
}
