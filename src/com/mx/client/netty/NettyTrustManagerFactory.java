package com.mx.client.netty;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;

import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509TrustManager;


public class NettyTrustManagerFactory extends TrustManagerFactorySpi {
	private static final TrustManager DUMMY_TRUST_MANAGER = new X509TrustManager() {
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			//Log.v("netty", "return getAcceptedIssuers");
			return new X509Certificate[0];
		}

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) {
			//Log.v("netty", "UNKNOWN CLIENT CERTIFICATE: " + chain[0].getSubjectDN());
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) {
			//Log.v("netty", "UNKNOWN SERVER CERTIFICATE: " + chain[0].getSubjectDN());
			//Log.v("netty", "UNKNOWN SERVER ALGORITHM: " + chain[0].getPublicKey().getAlgorithm());
			//Log.v("netty", "UNKNOWN SERVER CERT FORMAT: " + chain[0].getPublicKey().getFormat());
		}
	};

	public static TrustManager[] getTrustManagers() {
		return new TrustManager[] { DUMMY_TRUST_MANAGER };
	}

	@Override
	protected TrustManager[] engineGetTrustManagers() {
		return getTrustManagers();
	}

	@Override
	protected void engineInit(KeyStore ks) throws KeyStoreException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void engineInit(ManagerFactoryParameters spec) throws InvalidAlgorithmParameterException {
		// TODO Auto-generated method stub

	}

}
