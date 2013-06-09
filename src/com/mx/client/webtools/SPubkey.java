package com.mx.client.webtools;

import java.util.HashMap;


public class SPubkey {
	private static class SingletonHolder {
		public static final SPubkey INSTANCE = new SPubkey();
	}

	public static SPubkey getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public String postPubKey(String key) throws Exception {
		/**
		 * 返回的xml: <b><r>ok</r></b>
		 */
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("pubkey", key);
		//LOG.i("pubkey", "self pub key: " + key);
		try {
			String xml = ConnectionUtils.getInstance().postRequest("/pubkey/" + SConfig.getInstance().getSessionKey() + "/call.xml",
					values);
			System.out.println("公匙==提交"+xml);
			return XmlUtil.instance().parseXmltoString(xml, "UTF-8","r");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
