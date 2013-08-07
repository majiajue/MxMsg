package com.mx.client.webtools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.ClientProtocolException;
public class SLogin {
	// Private constructor prevents instantiation from other classes
	private SLogin() {
	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		public static final SLogin INSTANCE = new SLogin();
	}

	public static SLogin getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private String mSKey;

	public String register1() throws IOException {
		String mSkeyXml = ConnectionUtils.getInstance().getRequest(
				"/register1/call.xml");

		try {
			mSKey = XmlUtil.instance().parseXmltoString(mSkeyXml, "UTF-8",
					"skey");
			System.out.println("msKey======>"+mSkeyXml);
			PropertiesUtils.getInstance().setSessionKey(mSKey);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mSKey;
	}

	public String register2() throws ClientProtocolException, IOException  {
		String uid = ConnectionUtils.getInstance().getRequest(
				"/register2/" + this.mSKey + "/call.xml");
		try {
			System.out.println("uid==========>"+XmlUtil.instance().parseXmltoString(uid, "UTF-8", "uid"));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return XmlUtil.instance().parseXmltoString(uid, "UTF-8", "uid");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // ���UID
		return null;
	}

	public String getNewUid() throws IOException {
		return register2();
	}
	
	
	public String login(String uid, String skey, String pwd) throws IOException {
		return register4(uid, skey, pwd);
	}
	/**
	 * 注册
	 * @param pwd
	 * @param nickname
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public String register3(String pwd, String nickname, String phone) throws Exception {
		/*
		 * 返回的xml: <b><skey>skey</skey><r>ok</r></b>
		 */
		String skey = SConfig.getInstance().getSessionKey();
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("password", pwd);
		if (nickname != null)
			values.put("nickname", nickname);
		if (phone != null)
			values.put("tel", phone);
		String result = ConnectionUtils.getInstance().postRequestwithVersion("/register3/" + skey + "/call.aspx", values);
		return XmlUtil.instance().parseXmltoString(result, "UTF-8","r");
		
	}
	
    public String register4(String uid, String skey, String pwd) throws ClientProtocolException, IOException {
		try {
			pwd = new String(Base64.encodeBase64(SUtil.Hmac(skey, pwd)));
			System.out.println("pwd==="+pwd);
			//LOG.v("web", "最终传递的数据为： "+"sessionkey: "+skey+ "  uid:  " + uid + "  pwd:" + pwd);
			Map<String, Object> cv = new HashMap<String, Object>();
			cv.put("pwd", pwd);
			String result = ConnectionUtils.getInstance().postRequestwithVersion("/login/" + skey + "/" + uid + "/call.xml", cv);
			//LOG.v("web", Thread.currentThread().getId() + ":register4:" + result);
			return XmlUtil.instance().parseXmltoString(result, "UTF-8","r");
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
	}

}
