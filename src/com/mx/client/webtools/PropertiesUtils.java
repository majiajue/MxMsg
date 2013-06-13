package com.mx.client.webtools;


public class PropertiesUtils {

	public String sessionkey = "";
	public String applicationPSK = "";

	private PropertiesUtils() {
	}

	public String getWebHost() {
		// return this.getKeyValue("webhost");
		// return "61.4.82.141";
		return "www.han2011.com";
		// return "192.168.4.233";
	}

	public String getWebPort() {
		// return this.getKeyValue("webport");
		return "443";
	}

	private static class SingletonHolder {
		public static final PropertiesUtils INSTANCE = new PropertiesUtils();
	}

	public static PropertiesUtils getInstance() {

		return SingletonHolder.INSTANCE;
	}

	public void setSessionKey(String skey) {

		this.sessionkey = skey;
	}

	public String getSessionKey() {
		return this.sessionkey;

	}
}
