package com.mx.client.webtools;

import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.ClientProtocolException;

import com.mx.clent.vo.Profile;
import com.mx.client.db.GenDao;

public class SConfig {
	public String sessionkey = "";
	public final static String TB_PREFERENCE = "tb_preference";
	private final Object[] dbLock = new Object[0];
	private String m_psk;
	private String ApplicationPSKey = "";
	public String applicationPSK = "";
	private static final String KEY_ALGORITHM = "RSA";
	private static final String DEFAULT_SEED = "2C87BA8BC9A364D8CB0C8AB926039E06";
	public final static String COL_PRE_KEY = "key";
	public final static String COL_PRE_VALUE = "value";
	public Profile profile;

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

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
					"/magic/" + SConfig.getInstance().getSessionKey() + "/call.aspx");

			if (result == null || "".equals(result)) {
				return null;
			}

			if (XmlUtil.instance().parseXmltoString(result, "UTF-8", "r") != null) {
				if (XmlUtil.instance().parseXmltoString(result, "UTF-8", "r").equalsIgnoreCase("ok")) {
					String magic = XmlUtil.instance().parseXmltoString(result, "UTF-8", "magic");
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
			result = ConnectionUtils.getInstance().postRequest("/magic/" + getSessionKey() + "/call.aspx", cv);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// HashMap<String, String> map = SXmlParser.xmlResponse(result);
		if (XmlUtil.instance().parseXmltoString(result, "UTF-8", "r") != null) {
			if (XmlUtil.instance().parseXmltoString(result, "UTF-8", "r").equals("ok")) {
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
		if ("".equals(psk) || psk == null) {
			psk = getMagicContent();
			if ("".equals(psk) || psk == null) {
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
				// Log.v("mixun", sHelpKey);
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
				// LOG.i("wj", "���ܳ���");
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

	public String getHelpKey() {

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
			psk = SConfig.getInstance().getHelpKey();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("psk===");
		String result = null;
		if (psk != null || !"".equals(psk)) {
			try {
				Cryptor c = new Cryptor(psk);
				byte[] data = c.decrypt(Base64.decodeBase64(tmp.getBytes()));
				result = new String(data);
			} catch (CryptorException e) {
				// LOG.e("debug" , "SXmlTool --- decodeContacts error");
				e.printStackTrace();
			}
		}
		return result;
	}

	public KeyPair generateRsaKey() throws CryptorException {
		KeyPair kp = null;
		try {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			SecureRandom secureRandom = new SecureRandom();
			secureRandom.setSeed(DEFAULT_SEED.getBytes());
			keygen.initialize(2048, secureRandom);
			kp = keygen.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			throw new CryptorException(e);
		}
		return kp;
	}

	/**
	 * 
	 * @param ctx
	 * @param uid
	 * @throws Exception
	 */
	public void updatePublicKeyToServer(PublicKey pubkey) throws Exception {
		String encode_key = new String(Base64.encodeBase64(PubkeyUtils.getEncodedPublic(pubkey)));
		String result = SPubkey.getInstance().postPubKey(encode_key);
		System.out.println("updatePublicKeyToServer:" + result);
	}

	public void setApplicationPSK(String psk) {
		save("applicationPSK", psk);
		CryptorManager.getInstance().clearCryptor();
	}

	/**
	 * TODO ���magic
	 * 
	 * @return
	 */
	public boolean getMagic() {
		try {
			HashMap<String, Object> cv = new HashMap<String, Object>();
			String result = ConnectionUtils.getInstance().getRequest("/magic/" + getSessionKey() + "/call.aspx");
			System.out.println("result=====" + result);
			// HashMap<String, String> map = SXmlParser.xmlResponse(result);

			if (result != null && XmlUtil.instance().parseXmltoString(result, "UTF-8", "r") != null) {
				if (XmlUtil.instance().parseXmltoString(result, "UTF-8", "r").equals("ok")) {// ��ȡ�ɹ�
					String magic = XmlUtil.instance().parseXmltoString(result, "UTF-8", "magic");
					if (magic != null) {
						Cryptor crypt = null;
						try {
							crypt = new Cryptor(getHelpKey());
							crypt.decrypt(Base64.decodeBase64(magic.getBytes()));
							setApplicationPSK(magic);
							setMagicSynServer(true);
							return true;
						} catch (CryptorException e) {
							// LOG.e("debug",
							// "SConfig --- �������ϵ�Magic����ȷ����������");
							SConfig.getInstance().initApplicationPSK(getHelpKey());
							if (setMagic(this.applicationPSK)) {
								setApplicationPSK(magic);
								setMagicSynServer(true);
								return true;
							}
						}

						return false;
					}
				}

				if (XmlUtil.instance().parseXmltoString(result, "UTF-8", "r").equals("none")) {// δ����
					if (setMagic(this.applicationPSK))
						return true;
				}
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return false;
	}

	private boolean save(String key, String value) {

		synchronized (dbLock) {
			Hashtable<String, Object> condition = new Hashtable<String, Object>();
			condition.put(COL_PRE_KEY, key);
			Boolean boolean1 = GenDao.getInstance().executeUpdate(TB_PREFERENCE, new String[] { COL_PRE_VALUE },
					new Object[] { value }, condition);
			if (boolean1) {
				return true;
			} else {
				Boolean a = GenDao.getInstance().executeInsert(TB_PREFERENCE,
						new String[] { COL_PRE_KEY, COL_PRE_VALUE }, new Object[] { key, value });
				if (a) {
					return true;
				} else {
					return false;
				}

			}
		}
	}

	public static void main(String[] args) {
		System.out.println(SUtil.toSHAString64("123456", null));
	}
	
	public String getDownLdHost() {
		// return this.getKeyValue("webhost");
		return "61.4.82.141";
		// return "192.168.4.233";
	}

	public String getDownLdPort() {
		// return this.getKeyValue("webport");
		return "80";
	}
	

	public boolean isFolderExists(String strFolder) {
		File file = new File(strFolder);
		if (!file.exists()) {
			if (file.mkdirs()) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
}
