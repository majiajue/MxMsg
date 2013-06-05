package com.mx.client.webtools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.client.ClientProtocolException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;





public class SConfig {
	public String sessionkey = "";
	private String m_psk;// �����ڴ��е���Կ
	private String ApplicationPSKey="";
   
	public String getApplicationPSKey() {
		return ApplicationPSKey;
	}

	private SConfig() {

	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		public static final SConfig INSTANCE = new SConfig();
	}

	public static SConfig getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public void setSessionKey(String skey) {

		this.sessionkey = skey;
	}

	public String getSessionKey() {
		return this.sessionkey;

	}

	/**
	 * ��������
	 * 
	 * @param password
	 * @return
	 */
	public String setPassword(String password) {
		// setHostinPwd(password);
		setHelpKey(password);
		String str = SUtil.toSHAString64(password, null);
		return str;
	}


	public String getMagicContent() throws Exception {
		try {
			String result = ConnectionUtils.getInstance().getRequest(
					"/magic/" + SConfig.getInstance().getSessionKey()
							+ "/call.aspx");

			if (result == null || "".equals(result)) {
				return null;
			}

			if (XmlUtil.instance().parseXmltoString(result, "UTF-8", "r") != null) {
				if (XmlUtil.instance().parseXmltoString(result, "UTF-8", "r")
						.equalsIgnoreCase("ok")) {
					String magic = XmlUtil.instance().parseXmltoString(result,
							"UTF-8", "magic");
					;
					return magic;
				} else {
					return null;
				}
			} else {
				return null;
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setApplicationPSKey(String psk) {
		this.ApplicationPSKey = psk;
		CryptorManager.getInstance().clearCryptor();
	}

	public String initApplicationPSK(String str) {

		String keyPass = PassPhrase.getInstance().getNext();
		byte[] encodeKeyPass;
		Cryptor crypt;
		try {
			setHelpKey(str);
			crypt = new Cryptor(str);
			encodeKeyPass = crypt.encrypt(keyPass.getBytes());
			str = new String(Base64.encodeBase64(encodeKeyPass));
			setApplicationPSKey(str);
			setMagic(str);
			// ResetTiemer();
			return keyPass;
		} catch (CryptorException e) {
			System.out.println("SConfig --- ��ʼ��AES�������");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return keyPass;
	}

	public boolean setMagic(String magic) throws Exception {
		Map<String, Object> cv = new HashMap<String, Object>();
		cv.put("magic", magic);
		String result = null;
		try {
			result = ConnectionUtils.getInstance().postRequest(
					"/magic/" + getSessionKey() + "/call.aspx", cv);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// HashMap<String, String> map = SXmlParser.xmlResponse(result);
		if (XmlUtil.instance().parseXmltoString(result, "UTF-8", "r") != null) {
			if (XmlUtil.instance().parseXmltoString(result, "UTF-8", "r")
					.equals("ok")) {
				setMagicSynServer(true);
				// LOG.i("debug", "SConfig --- setMagic success");
				return true;
			}
		}
		setMagicSynServer(false);
		// LOG.i("debug", "SConfig --- setMagic failed");
		return false;
	}

	private boolean MAGIC_SYN_SERVER = false;

	public void setMagicSynServer(boolean bool) {
		this.MAGIC_SYN_SERVER = bool;
	}

	public boolean isMagicSynServer() {
		return MAGIC_SYN_SERVER;
	}
	public String getApplicationPSK() throws Exception {
		String psk = getApplicationPSKey();
		Cryptor crypt;
		if ("".equals(psk)||psk==null) {
			psk = getMagicContent();
			if ("".equals(psk)||psk==null) {
				String pwd = getHelpKey();
				psk = initApplicationPSK(pwd);
			} else {
				byte[] data = null;
				crypt = new Cryptor(getHelpKey());
				data = crypt.decrypt(Base64.decodeBase64(psk.getBytes()));
				psk = new String(data);
			}
		} else {
			try {
				String sHelpKey = getHelpKey();
				//Log.v("mixun", sHelpKey);
				crypt = new Cryptor(getHelpKey());
			} catch (CryptorException e) {
				e.printStackTrace();
				throw new CryptorException("Could not get encrypt instance for encoding password.");
			}
			byte[] decodeKeyPass;
			try {
				decodeKeyPass = crypt.decrypt(Base64.decodeBase64(psk.getBytes()));
				psk = new String(decodeKeyPass);
			} catch (CryptorException e) {
				//LOG.i("wj", "���ܳ���");
				throw new CryptorException("Could not decrypt the encoded password. Might mismatched key.");
			}
		}
		return psk;
	}
	
	/**
	 * ��װ��key
	 * 
	 * @throws CryptorException
	 */
	public void setHelpKey(String psk) {
		 m_psk = psk;
	}
	
	public String getHelpKey(){
		
		return m_psk;
	}
	private String holdMonkey(String str) {
		if (str == null)
			return null;
		byte[] temp = str.getBytes();
		byte[] temp1 = str.getBytes();
		for (int i = 0; i < temp.length; i++) {
			temp[i] = temp1[temp.length - 1 - i];
		}
		return new String(Base64.decodeBase64(temp));
	}
	
	public static String decodeContacts(String tmp) {
		String psk = null;
		try {
			psk = SConfig.getInstance().getApplicationPSK();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = null;
		if(psk!=null||!"".equals(psk)) {
			try {
				Cryptor c = new Cryptor(psk);
				byte[] data = c.decrypt(Base64.decodeBase64(tmp.getBytes()));
				result = new String(data);
			} catch (CryptorException e) {
				//LOG.e("debug" , "SXmlTool --- decodeContacts error");
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(SUtil.toSHAString64("123456", null));
	}
}
