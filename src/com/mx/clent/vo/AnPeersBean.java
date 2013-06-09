package com.mx.clent.vo;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import com.mx.client.webtools.HanziToPinyin;
import com.mx.client.webtools.HanziToPinyin.Token;
import com.mx.client.webtools.PubkeyUtils;

public class AnPeersBean {

	public int _id = -1;
	public String PPeerid = "";
	public String PUsername = "";
	public String PPinyin = "";
	public String Phone = "";
	// public String PAvatarid = "";
	public String PUpdateTime = "";
	public String PLastcontact = "";
	public String PPubKey = "";
	public String PRemark = "";

	public PublicKey publicKey;
	// 判断群成员是否在线的字段，不需存入数据库
	public String isOnline = "1";
	public String Maskid = "-1";

	/**
	 * 构造一个空的peerBean,指导所有字段初始化完成之前，这个peer不能被保存
	 */
	public AnPeersBean() {

	}

	/**
	 * 新建一个用户节点，构造函数将会自动将用户名保存成拼音保存
	 * 
	 * @param Peerid
	 * @param Username
	 * @param PhoneNumber
	 * @param Avatarid
	 * @param UpdateTime
	 * @param Lastcontact
	 * @param Public
	 */
	public AnPeersBean(String Peerid, String Username, String Remark, String PhoneNumber, String UpdateTime,
			String Lastcontact, String Public) {
		//LOG.w("peer", "AnPeersBean:" + Peerid);
		PPeerid = Peerid;
		PUsername = Username;
		PRemark = Remark;
		Phone = PhoneNumber;
		PUpdateTime = UpdateTime;
		PLastcontact = Lastcontact;
		PPubKey = Public.replaceAll("(\\r|\\n)", "");

		PPinyin = getPinYinStr(Username);
		if (!"".equals(PPubKey)||PPubKey!=null) {
			
			byte[] encoded = org.apache.commons.codec.binary.Base64.decodeBase64(PPubKey);
			try {
				publicKey = PubkeyUtils.decodePublic(encoded, "RSA");
			} catch (NoSuchAlgorithmException e) {
				//LOG.w("peer", "public key decode error!:" + PPubKey + "||peerid:" + PPeerid); // e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				//LOG.w("peer", "public key decode error!:" + PPubKey + "||peerid:" + PPeerid); // e.printStackTrace();
			}
		}
	}

	

	

	public String toString() {
		return " || " + PPeerid + " || " + PUsername + "||" + PRemark + "||" + PPinyin + " || " + Phone + " || "
				+ PUpdateTime + " || " + PLastcontact + " || " + PPubKey;
	}

	// 生成拼音
	public String getPinYinStr(String Username) {
		String pinYin = "";
		ArrayList<Token> tokens = new ArrayList<Token>();
		tokens = HanziToPinyin.getInstance().get(tokens, Username);
		if (tokens != null && tokens.isEmpty()) {
			pinYin = Username;
		} else {
			int count = tokens.size();
			String abbreviation = "";
			for (int i = 0; i < count; i++) {
				pinYin += tokens.get(i).target;
				abbreviation += tokens.get(i).target.subSequence(0, 1);
			}
			pinYin += abbreviation;
			pinYin = pinYin.toLowerCase();
		}
		return pinYin;
	}

	/**
	 * 获取该用户的public key
	 * 
	 * @return
	 */
	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	/**
	 * 设置新的公钥
	 * 
	 * @param key
	 */
	public void setPublicKey(PublicKey key) {
		this.publicKey = key;
		if (publicKey != null) {
			PPubKey = org.apache.commons.codec.binary.Base64.encodeBase64String(publicKey.getEncoded());
		}
	}

	/**
	 * 设置公钥，参数是从数据库中取出来的，来源是 Base64.encodetoString(publickey.getencoded());
	 * 
	 * @param encodedKey
	 */
	public void setPublicKey(String encodedKey) {
		PPubKey = encodedKey;
		try {
			byte[] encoded = org.apache.commons.codec.binary.Base64.decodeBase64(PPubKey);
			publicKey = PubkeyUtils.decodePublic(encoded, "RSA");
		} catch (Exception e) {
			//LOG.d("peer", "public key error!");
			e.printStackTrace();
		}
	}

	// 返回一个空的
	public static final AnPeersBean NULL = new AnPeersBean();

	/**
	 * 从一个cursor创建Anpeers对象，
	 * 
	 * @param c
	 *            cursor对象，必须从StorageManager.GetInstance().getAnpeersList().
	 *            getCursor()获得，或者必须是从peerDatabase中获得的peer对应的cursor对象
	 */
//	public static AnPeersBean CreateFromCursor(Cursor c) {
//		AnPeersBean peers = NULL;
//		try {
//			String Peerid, Username, Pinyin, UpdateTime, Lastcontact, Public;
//			String phone;
//			String Remark;
//			Peerid = c.getString(c.getColumnIndex(DataDefine.COL_PEER_PEERID));
//			// 首先从缓存中取一次
//			if (!TextUtils.isEmpty(Peerid)) {
//				peers = AnPeersBeanCache.getInstance().get(Peerid);
//				if (peers == null) {
//					Username = c.getString(c.getColumnIndex(DataDefine.COL_PEER_USERNAME));
//					Remark = c.getString(c.getColumnIndex(DataDefine.COL_PEER_REMARK));
//					Pinyin = c.getString(c.getColumnIndex(DataDefine.COL_PEER_PINYIN));
//					// Avatarid =
//					// c.getString(c.getColumnIndex(DataDefine.COL_PEER_AVATAR));
//					UpdateTime = c.getString(c.getColumnIndex(DataDefine.COL_PEER_UPDTAETIME));
//					Lastcontact = c.getString(c.getColumnIndex(DataDefine.COL_PEER_LASTCONTACT));
//					Public = c.getString(c.getColumnIndex(DataDefine.COL_PEER_PUBLIC));
//					phone = c.getString(c.getColumnIndex(DataDefine.COL_PEER_PHONE));
//					peers = new AnPeersBean(Peerid, Username, Remark, phone, UpdateTime, Lastcontact, Public);
//					peers.PPinyin = Pinyin;
//					peers._id = c.getInt(c.getColumnIndex(DataDefine._id));
//					AnPeersBeanCache.getInstance().put(Peerid, peers);
//				}
//			}
//		} catch (Exception e) {
//			LOG.e("mixun", "类型不匹配，或者有空字段");
//			e.printStackTrace();
//		} finally {
//		}
//		return peers;
//	}

//	public static AnPeersBean CreateSimpleFromCursor(Cursor c) {
//		AnPeersBean peers = NULL;
//		try {
//			String peerid, username, phone;
//			int _id = -1;
//			_id = c.getInt(c.getColumnIndex(DataDefine._id));
//			peerid = c.getString(c.getColumnIndex(DataDefine.COL_PEER_PEERID));
//			username = c.getString(c.getColumnIndex(DataDefine.COL_PEER_USERNAME));
//			phone = c.getString(c.getColumnIndex(DataDefine.COL_PEER_PHONE));
//
//			if (_id != -1) {
//				peers = new AnPeersBean();
//				peers.PPeerid = peerid;
//				peers._id = _id;
//				peers.PUsername = username;
//				peers.Phone = phone;
//			}
//
//		} catch (Exception e) {
//			LOG.e("Debug", "AnPeersBean ---- CreateSimpleFromCursor ---类型不匹配，或者有空字段");
//			e.printStackTrace();
//		} finally {
//		}
//
//		return peers;
//	}

//	public static List<AnPeersBean> createPeerBeansFromCursor(Cursor c) {
//		List<AnPeersBean> beans = new ArrayList<AnPeersBean>();
//		AnPeersBean peer = (AnPeersBean) NULL;
//		do {
//			peer = CreateFromCursor(c);
//			beans.add(peer);
//		} while (c.moveToNext());
//		if (c != null) {
//			c.close();
//		}
//		return beans;
//	}
}
