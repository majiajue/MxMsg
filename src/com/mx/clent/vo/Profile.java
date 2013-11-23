package com.mx.clent.vo;

import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Hashtable;
import com.mx.client.db.DBDataSQL;
import com.mx.client.db.DBTools;
import com.mx.client.db.GenDao;
import com.mx.client.webtools.CryptorException;
import com.mx.client.webtools.PubkeyUtils;
import com.mx.client.webtools.SConfig;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class Profile {
	public static String TABLE_WONDERLAND = "wonderland_";
	private final Object[] dbLock = new Object[0];
	public static String COL_KEY = "KEY";
	public static String COL_vALUE = "VALUE";
	private static String KEY_PRIVATE = "privatekey";
	private static String KEY_PUBLIC = "publickey";
	private static String KEY_PUBKEY_UPDATETIME = "pubkeyuptime";
	private static String KEY_MYID = "myid";
	private static String KEY_SESSIONKEY = "sessionkey";
	public AnPeersBean myPeerBean = null;
	private KeyPair rSAKeyPair = null;
	// private gra MyAvatar = null;
	public String MyUserId = "";
	public String keyupdatetime = "";
	public static final String NEW_MAIN_DATA_DRI = "MiXun/";
	public static final String USER_DATA = "users/";
	/**
	 * 
	 * @param context
	 * @param peerid
	 */
	private Profile(String peerid) {

		myPeerBean = new AnPeersBean();
		MyUserId = peerid;
	}

	public Profile(String peerid, String deblockpwd) {
		myPeerBean = new AnPeersBean();
		MyUserId = peerid;
	}

	public static Profile CreateProfile(String userId, String sKey) throws CryptorException, IOException {
		AnPeersBean bean = new AnPeersBean();
		bean.PPeerid = userId;
		bean.PUsername = userId;
		Profile profile = new Profile(userId, "_password");
		profile.rSAKeyPair = SConfig.getInstance().generateRsaKey(); // 产生rsa公钥对
		try {// 保存公钥到服务器
			SConfig.getInstance().updatePublicKeyToServer(profile.rSAKeyPair.getPublic());
		} catch (Exception e) {
			e.printStackTrace();
		}
		profile.myPeerBean = bean;
		profile.keyupdatetime = System.currentTimeMillis() + "";
		profile.myPeerBean.setPublicKey(profile.rSAKeyPair.getPublic());
		profile.HandleSave();
		return profile;
	}

	public boolean HandleSave() {
		// save(KEY_PRIVATE, Base64.encode(PubkeyUtils.encrypt(rSAKeyPair
		// .getPrivate().getEncoded(), whiterabbit)));
		save(KEY_PRIVATE, Base64.encode(rSAKeyPair.getPrivate().getEncoded()));
		save(KEY_PUBLIC, Base64.encode(rSAKeyPair.getPublic().getEncoded()));
		save(KEY_MYID, Base64.encode(MyUserId.getBytes()));
		save(KEY_PUBKEY_UPDATETIME, Base64.encode(keyupdatetime.getBytes()));
		savePeer(MyUserId, MyUserId, "", "", "", "", "", "");

		return true;
	}

	public void SaveKeyValue(String key, String value) {
		save(key, Base64.encode(value.getBytes()));
	}

	/**
	 * 
	 * @param skey
	 */
	public void setSessionkey(String skey) {
		SaveKeyValue(KEY_SESSIONKEY, skey);
	}

	/**
	 * 
	 * @return
	 */
	public String getSession() {
		String skey = com.mx.client.webtools.SConfig.getInstance().getSessionKey();
		if (skey != null) {
			com.mx.client.webtools.SConfig.getInstance().setSessionKey(skey);
		}
		return skey;
	}

	public static boolean isExistProfile(String peerid) {
		String sql = "select * from " + TABLE_WONDERLAND + peerid;
		int resultSet = DBTools.findData(sql);

		try {
			boolean c = resultSet > 0 ? true : false;
			return c;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean save(String key, String value) {

		synchronized (dbLock) {
			Hashtable<String, Object> condition = new Hashtable<String, Object>();
			condition.put(COL_KEY, key);
			Boolean boolean1 = GenDao.getInstance().executeUpdate(TABLE_WONDERLAND + MyUserId,
					new String[] { COL_vALUE }, new Object[] { value }, condition);
			if (boolean1) {
				return true;
			} else {
				Boolean a = GenDao.getInstance().executeInsert(TABLE_WONDERLAND + MyUserId,
						new String[] { COL_KEY, COL_vALUE }, new Object[] { key, value });
				if (a) {
					return true;
				} else {
					return false;
				}

			}
		}
	}

	public void savePeer(String Peerid, String Username, String Remark, String Pinyin, String PhoneNumber,
			String UpdateTime, String Lastcontact, String Public) {
		if (!checkparam(Peerid, Username, Pinyin, PhoneNumber, UpdateTime, Lastcontact, Public)) {
			return;
		}
		synchronized (dbLock) {
			String[] cloumes = new String[] { DBDataSQL.COL_PEER_LASTCONTACT, DBDataSQL.COL_PEER_PEERID,
					DBDataSQL.COL_PEER_PHONE, DBDataSQL.COL_PEER_PINYIN, DBDataSQL.COL_PEER_PUBLIC,
					DBDataSQL.COL_PEER_UPDTAETIME, DBDataSQL.COL_PEER_USERNAME, DBDataSQL.COL_PEER_REMARK };
			Object[] values = new Object[] { Lastcontact,Peerid, PhoneNumber,Pinyin,Public,UpdateTime,Username, Remark };
			Hashtable<String, Object> condition = new Hashtable<String, Object>();
			condition.put(DBDataSQL.COL_PEER_PEERID, Peerid);
			Boolean boolean1 = GenDao.getInstance().executeUpdate(DBDataSQL.TB_PEERS, cloumes, values, condition);
			if (!boolean1) {
				GenDao.getInstance().executeInsert(DBDataSQL.TB_PEERS, cloumes, values);

			}

		}

	}

	public static boolean VerfyProfile(String text, String shapwd) {
		return false;
	}

	/**
	 * 
	 * @param context
	 * @param peerid
	 * @param passwd
	 */
	public static Profile LoadProfile(String peerid) {
		Profile profile = new Profile(peerid);
		try {
			profile.load();
		} catch (Exception e) {
			e.printStackTrace();
			profile = null;
		}
		return profile;
	}

	/**
	 * StorageManager
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws CryptorException
	 * @throws Base64DecodingException
	 */
	private void load() throws NoSuchAlgorithmException, InvalidKeySpecException, CryptorException,
			Base64DecodingException {

		Hashtable<String, Object> hashtable = new Hashtable<String, Object>();

		hashtable.put(COL_KEY, KEY_PUBKEY_UPDATETIME);
		this.keyupdatetime = GenDao.getInstance().getValue(TABLE_WONDERLAND + MyUserId, new String[0], COL_vALUE,
				hashtable);
		hashtable.clear();
		hashtable.put(COL_KEY, KEY_PUBLIC);

		PublicKey publicKey = PubkeyUtils.decodePublic(
				Base64.decode(GenDao.getInstance().getValue(TABLE_WONDERLAND + MyUserId, new String[0], COL_vALUE,
						hashtable)), PubkeyUtils.KEY_TYPE_RSA);
		hashtable.clear();

		hashtable.put(COL_KEY, KEY_PRIVATE);

		String privateKeyString = GenDao.getInstance().getValue(TABLE_WONDERLAND + MyUserId, new String[0], COL_vALUE,
				hashtable);
		byte[] key = Base64.decode(privateKeyString);
		System.out.println("key1:" + key.length);
		System.out.println("privateKeyString:" + privateKeyString);

		PrivateKey privateKey = PubkeyUtils.decodePrivate(
				Base64.decode(GenDao.getInstance().getValue(TABLE_WONDERLAND + MyUserId, new String[0], COL_vALUE,
						hashtable)), PubkeyUtils.KEY_TYPE_RSA);
		this.rSAKeyPair = new KeyPair(publicKey, privateKey);
		this.myPeerBean.PPeerid = MyUserId;
		if ("".equals(this.myPeerBean.PUsername) || this.myPeerBean.PUsername == null) {
			this.myPeerBean.PUsername = MyUserId;
		}
		this.myPeerBean.publicKey = publicKey;
	}

	protected boolean checkparam(Object... params) {
		boolean result = true;
		for (Object name : params)
			if (name == null) {
				result = false;
				return result;
			}
		return result;
	}

	/**
	 * @return
	 */
	public KeyPair getKeyPair() {
		return this.rSAKeyPair;
	}

	/**
	 * @param k
	 */
	public void changeKeyPair(KeyPair k) {
		this.rSAKeyPair = k;
		HandleSave();
	}
	
	/**
	 * 获取用户数据目录，
	 * 
	 * @return
	 */
	public String getUserDataDir() {
		return System.getenv("TEMP")+ "/" + NEW_MAIN_DATA_DRI + USER_DATA + myPeerBean.PPeerid + "/";
	}

	/**
	 * 获取密讯的根目录
	 * 
	 * @return
	 */
	public String getMixunRootDir() {
		return System.getenv("TEMP") + "/" + NEW_MAIN_DATA_DRI;
	}

}
