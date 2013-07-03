package com.mx.client.netty;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;



public class NettySslContextFactory {
	private static SSLContext CLIENT_CONTEXT = null;

	public static SSLContext getClientContext() {
		try {
			CLIENT_CONTEXT = SSLContext.getInstance("TLS");
			//Log.v("netty", "SESSION CACHESIZE:" + CLIENT_CONTEXT.getClientSessionContext().getSessionCacheSize());
			//Log.v("netty", "SESSION TIEMOUTTIME:" + CLIENT_CONTEXT.getClientSessionContext().getSessionTimeout());
			CLIENT_CONTEXT.getClientSessionContext().setSessionCacheSize(5);
			CLIENT_CONTEXT.init(null, NettyTrustManagerFactory.getTrustManagers(), null);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return CLIENT_CONTEXT;
	}
}
