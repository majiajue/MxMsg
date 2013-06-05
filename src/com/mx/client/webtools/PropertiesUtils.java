package com.mx.client.webtools;

import javax.security.auth.login.Configuration;

import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.params.HttpParams;

public class PropertiesUtils {

	public String sessionkey = "";
	public String applicationPSK = "";
	private PropertiesUtils() {
	}

	public String getWebHost() {
		// return this.getKeyValue("webhost");
		return "61.4.82.141";
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
