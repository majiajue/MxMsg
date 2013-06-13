package com.mx.clent.vo;

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
import com.mx.client.webtools.SUtil;
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
	private static String KEY_PEERID = "peerid";
	private static String KEY_SESSIONKEY = "sessionkey";
	private static String KEY_RABBITWATCH = "rabbitwatch";
	private static String KEY_WHITERABBIT = "whiterabbit";
	private static String KEY_DEBLOCK_PWD = "deblockpassphrase";
	private static String KEY_HATTER = "hatter";
	private static String KEY_ALICE = "alice";
	public AnPeersBean myPeerBean = null;
	private KeyPair rSAKeyPair = null;
	// private gra MyAvatar = null;
	public static String MyPeerid = "";
	public String keyupdatetime = "";
	private String whiterabbit = ""; // primary AES key (whriterabbit =
										// hmac(hatter+rabbitwatch))
	private String loginpwd = "";// 密讯的登录密码，生成方法与whiterabbit一样
	private static String rabbbitsWatch = ""; // the random salt to store AES
												// key//
	// this is peerid
	private String alice = "";// (alice = hmac(whriterabbit+password));
	private String hatter = "";// hatter = hmac(password);
	private String cheshireCat = ""; // sessionkey

	/**
	 * 用户查看当前配置是否存在
	 * 
	 * @param context
	 * @param peerid
	 */
	private Profile(String peerid) {

		myPeerBean = new AnPeersBean();
		this.MyPeerid = peerid;
		this.rabbbitsWatch = peerid;
	}

	public Profile(String peerid, String deblockpwd) {
		myPeerBean = new AnPeersBean();
		this.MyPeerid = peerid;
		this.rabbbitsWatch = peerid;
		this.hatter = SUtil.toSHAString64(deblockpwd, null);
		this.whiterabbit = SUtil.toSHAString64(hatter + rabbbitsWatch, null);
		this.alice = SUtil.toSHAString64(whiterabbit + deblockpwd, null);
	}

	public static Profile CreateProfile(AnPeersBean peer, String password,
			KeyPair keyPair, String sessionkey, String keyUpdateTime) {
		Profile profile = new Profile(peer.PPeerid, password);
		profile.cheshireCat = sessionkey;
		profile.myPeerBean = peer;
		profile.rSAKeyPair = keyPair;
		if (!"".equals(keyUpdateTime) || keyUpdateTime != null) {
			profile.keyupdatetime = keyUpdateTime;
		}
		profile.myPeerBean.setPublicKey(profile.rSAKeyPair.getPublic());
		profile.HandleSave();
		return profile;
	}

	public boolean HandleSave() {
		try {
			System.out.println("私匙长度"
					+ PubkeyUtils.encrypt(rSAKeyPair.getPrivate().getEncoded(),
							whiterabbit).length);
			// save(KEY_PRIVATE, Base64.encode(PubkeyUtils.encrypt(rSAKeyPair
			// .getPrivate().getEncoded(), whiterabbit)));
			String savePrivateString = Base64.encode(rSAKeyPair.getPrivate()
					.getEncoded());
			System.out.println("savePrivateKey:" + savePrivateString);
			save(KEY_PRIVATE, savePrivateString);
			save(KEY_PUBLIC, Base64.encode(rSAKeyPair.getPublic().getEncoded()));
			save(KEY_RABBITWATCH, Base64.encode(rabbbitsWatch.getBytes()));
			save(KEY_PEERID, Base64.encode(MyPeerid.getBytes()));
			save(KEY_WHITERABBIT, Base64.encode(whiterabbit.getBytes()));
			save(KEY_PUBKEY_UPDATETIME, Base64.encode(keyupdatetime.getBytes()));
			SaveKeyValue(KEY_SESSIONKEY, cheshireCat);
			SaveKeyValue(KEY_ALICE, alice);
			SaveKeyValue(KEY_HATTER, hatter);
			savePeer(MyPeerid, MyPeerid, "", "", "", "", "", "");
		} catch (CryptorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public void SaveKeyValue(String key, String value) {
		save(key, Base64.encode(value.getBytes()));
	}
    
	/**
	 * 保存sessionkey
	 * 
	 * @param skey
	 */
	public void setSessionkey(String skey) {
		this.cheshireCat = skey;
		SaveKeyValue(KEY_SESSIONKEY, skey);
	}
	
	/**
	 * 获取用户的session
	 * 
	 * @return
	 */
	public String getSession() {
		String skey = com.mx.client.webtools.SConfig.getInstance().getSessionKey();
		if (skey!=null) {// 这里把sessionkey保存到内存中
			skey = cheshireCat;
			com.mx.client.webtools.SConfig.getInstance().setSessionKey(skey);
		}
		return skey;
	}
	
	public static boolean checkloginpwd(String peerid, String password) {
		Profile profile = new Profile(peerid);
		boolean result = false;
		String h, w;
		try {
			h = SUtil.toSHAString64(password, null);
			w = SUtil.toSHAString64(h + rabbbitsWatch, null);
			Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
			hashtable.put(COL_KEY, KEY_WHITERABBIT);
			String t1 = GenDao.getInstance().getValue(
					TABLE_WONDERLAND + MyPeerid, new String[0], COL_vALUE,
					hashtable);
			t1 = new String(
					org.apache.commons.codec.binary.Base64.decodeBase64(t1
							.getBytes()));
			System.out.println("t1" + t1);
			result = t1.equals(w);
		} catch (Exception e) {
			result = false;
		}

		return result;
	}

	/**
	 * 验证密码是否正确
	 * 
	 * @param context
	 * @param peerid
	 * @param passwd
	 * @param 检查用户密码是否正确
	 * @return
	 */
	public static boolean CheckPassWord(String peerid, String passwd) {
		boolean result = false;
		Profile profile = new Profile(peerid, passwd);
		try {
			if (profile.checkloginpwd(peerid, passwd)) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public static boolean isExistProfile(String peerid) {
		String sql = "select * from " + TABLE_WONDERLAND + peerid;
		int resultSet = DBTools.findData(sql);

		try {
			boolean c = resultSet > 0 ? true : false;
			return c;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private boolean save(String key, String value) {

		synchronized (dbLock) {
			Hashtable<String, Object> condition = new Hashtable<String, Object>();
			condition.put(COL_KEY, key);
			Boolean boolean1 = GenDao.getInstance().executeUpdate(
					TABLE_WONDERLAND + MyPeerid, new String[] { COL_vALUE },
					new Object[] { value }, condition);
			if (boolean1) {
				return true;
			} else {
				Boolean a = GenDao.getInstance().executeInsert(
						TABLE_WONDERLAND + MyPeerid,
						new String[] { COL_KEY, COL_vALUE },
						new Object[] { key, value });
				if (a) {
					return true;
				} else {
					return false;
				}

			}
		}
	}

	public void savePeer(String Peerid, String Username, String Remark,
			String Pinyin, String PhoneNumber, String UpdateTime,
			String Lastcontact, String Public) {
		if (!checkparam(Peerid, Username, Pinyin, PhoneNumber, UpdateTime,
				Lastcontact, Public)) {
			return;
		}
		synchronized (dbLock) {
			String[] cloumes = new String[] { DBDataSQL.COL_PEER_LASTCONTACT,
					DBDataSQL.COL_PEER_PEERID, DBDataSQL.COL_PEER_PHONE,
					DBDataSQL.COL_PEER_PINYIN, DBDataSQL.COL_PEER_PUBLIC,
					DBDataSQL.COL_PEER_UPDTAETIME, DBDataSQL.COL_PEER_USERNAME,
					DBDataSQL.COL_PEER_REMARK };
			Object[] values = new Object[] { Peerid, Username, Pinyin,
					PhoneNumber, UpdateTime, Lastcontact, Public };
			Hashtable<String, Object> condition = new Hashtable<String, Object>();
			condition.put(DBDataSQL.COL_PEER_PEERID, Peerid);
			Boolean boolean1 = GenDao.getInstance().executeUpdate(
					DBDataSQL.TB_PEERS, cloumes, values, condition);
			if (!boolean1) {
				GenDao.getInstance().executeInsert(DBDataSQL.TB_PEERS, cloumes,
						values);

			}

		}

	}

	public static boolean VerfyProfile(String text, String shapwd) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 从本地数据库中载入用户信息，通过账户和密码来分辨多账户支持
	 * 
	 * @param context
	 * @param peerid
	 * @param passwd
	 * @return 如果用户名和密码不正确，则返回null
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
	 * 正式载入所有的配置文件，初始化StorageManager
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws CryptorException
	 * @throws Base64DecodingException
	 */
	private void load() throws NoSuchAlgorithmException,
			InvalidKeySpecException, CryptorException, Base64DecodingException {

		Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
		hashtable.put(COL_KEY, KEY_ALICE);
		this.alice = GenDao.getInstance().getValue(TABLE_WONDERLAND + MyPeerid,
				new String[0], COL_vALUE, hashtable);
		hashtable.clear();
		hashtable.put(COL_KEY, KEY_WHITERABBIT);
		this.whiterabbit = GenDao.getInstance().getValue(
				TABLE_WONDERLAND + MyPeerid, new String[0], COL_vALUE,
				hashtable);
		System.out.println("whiterabbit" + whiterabbit);
		hashtable.clear();
		hashtable.put(COL_KEY, KEY_HATTER);
		this.hatter = GenDao.getInstance().getValue(
				TABLE_WONDERLAND + MyPeerid, new String[0], COL_vALUE,
				hashtable);
		// StorageManager.setStorageKey(this.alice);
		hashtable.clear();

		hashtable.put(COL_KEY, KEY_RABBITWATCH);
		this.rabbbitsWatch = GenDao.getInstance().getValue(
				TABLE_WONDERLAND + MyPeerid, new String[0], COL_vALUE,
				hashtable);
		hashtable.clear();
		hashtable.put(COL_KEY, KEY_SESSIONKEY);
		this.cheshireCat = GenDao.getInstance().getValue(
				TABLE_WONDERLAND + MyPeerid, new String[0], COL_vALUE,
				hashtable);
		hashtable.clear();

		hashtable.put(COL_KEY, KEY_PUBKEY_UPDATETIME);
		this.keyupdatetime = GenDao.getInstance().getValue(
				TABLE_WONDERLAND + MyPeerid, new String[0], COL_vALUE,
				hashtable);
		hashtable.clear();
		hashtable.put(COL_KEY, KEY_PUBLIC);

		PublicKey publicKey = PubkeyUtils.decodePublic(
				Base64.decode(GenDao.getInstance().getValue(
						TABLE_WONDERLAND + MyPeerid, new String[0], COL_vALUE,
						hashtable)), PubkeyUtils.KEY_TYPE_RSA);

		hashtable.clear();

		hashtable.put(COL_KEY, KEY_PRIVATE);

		String privateKeyString = GenDao.getInstance().getValue(
				TABLE_WONDERLAND + MyPeerid, new String[0], COL_vALUE,
				hashtable);
		byte[] key = Base64.decode(privateKeyString);
		System.out.println("key1:" + key.length);
		System.out.println("privateKeyString:" + privateKeyString);

		PrivateKey privateKey = PubkeyUtils.decodePrivate(
				Base64.decode(GenDao.getInstance().getValue(
						TABLE_WONDERLAND + MyPeerid, new String[0], COL_vALUE,
						hashtable)), PubkeyUtils.KEY_TYPE_RSA);
		this.rSAKeyPair = new KeyPair(publicKey, privateKey);
		this.myPeerBean.PPeerid = MyPeerid;
		if ("".equals(this.myPeerBean.PUsername)
				|| this.myPeerBean.PUsername == null) {
			this.myPeerBean.PUsername = MyPeerid;
		}
		this.myPeerBean.publicKey = publicKey;
	}

	// 检查参数
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
	 * 获取用户的公私钥对
	 * 
	 * @return
	 */
	public KeyPair getKeyPair() {
		return this.rSAKeyPair;
	}

	/**
	 * 更新自己的密钥对
	 * 
	 * @param k
	 */
	public void changeKeyPair(KeyPair k) {
		this.rSAKeyPair = k;
		HandleSave();
	}

}
