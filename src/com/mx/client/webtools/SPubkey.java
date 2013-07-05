package com.mx.client.webtools;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.client.ClientProtocolException;

public class SPubkey {
	private static class SingletonHolder {
		public static final SPubkey INSTANCE = new SPubkey();
	}

	public static SPubkey getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public String getPubKey(String uid) throws IOException {
		/**
		 * 返回的xml: <b><r>ok</r><pubkey>...</pubkey></b>
		 */

		String xml = ConnectionUtils.getInstance().getRequest(
				"/getpubkey/" + SConfig.getInstance().getProfile().getSession()
						+ "/" + uid + "/call.xml");
		// LOG.v("web", xml);
		System.out.println("xml====" + xml);
		try {
			return XmlUtil.instance().parseXmltoString(xml, "UTF-8", "pubkey");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String postPubKey(String key) throws Exception {
		/**
		 * 返回的xml: <b><r>ok</r></b>
		 */
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("pubkey", key);
		// LOG.i("pubkey", "self pub key: " + key);
		try {
			String xml = ConnectionUtils.getInstance().postRequest(
					"/pubkey/" + SConfig.getInstance().getSessionKey()
							+ "/call.xml", values);
			System.out.println("公匙==提交" + xml);
			return XmlUtil.instance().parseXmltoString(xml, "UTF-8", "r");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getPubTime(String uid) throws ClientProtocolException,
			IOException {

		try {
			String xml = ConnectionUtils.getInstance().getRequest(
					"/getpubkey/"
							+ SConfig.getInstance().getProfile().getSession()
							+ "/" + uid + "/call.xml");
			System.out.println("xml====="+xml);
			return XmlUtil.instance().parseXmltoString(xml, "UTF-8", "time");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
