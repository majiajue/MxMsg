package com.mx.client.webtools;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.ClientProtocolException;

import com.mx.clent.vo.AnPeersBean;
import com.mx.clent.vo.Profile;

/**
 * �������еĹ�˽Կ
 * 
 * @author tweety
 * 
 */
public class KeyManager {
	public boolean ifUpdatePubkeyPair = false;

	// Private constructor prevents instantiation from other classes
	private KeyManager() {
		// mRSAKeyMap = new HashMap<String, KeyPair>();
		// mEncryptKeyMap = new HashMap<String, PublicKey>();
	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		public static final KeyManager INSTANCE = new KeyManager();
	}

	public static KeyManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	// private final Map<String, KeyPair> mRSAKeyMap;
	// private final Map<String, PublicKey> mEncryptKeyMap;
	private static final String KEY_ALGORITHM = "RSA";
	private static final String DEFAULT_SEED = "2C87BA8BC9A364D8CB0C8AB926039E06";

	/**
	 * ��ȡ������˽Կ
	 * 
	 * @param ctx
	 * @param name
	 *            ��Կ�Ե����
	 * @param withPrivateKey
	 *            �Ƿ�������˽Կ����
	 * @return ���ڽ�����ݵ�˽Կ
	 */
	public PrivateKey getDecryptKey(String name) throws CryptorException {
		KeyPair kp = SConfig.getInstance().getProfile().getKeyPair();
		// KeyPair kp = mRSAKeyMap.get(name);
		if (kp == null) {
			System.out.println("user " + name + " kp is null");
			kp = initRSAKeyPair(name, DEFAULT_SEED);
		} else {
			System.out.println("user " + name + " kp isn't null");
			System.out.println("private length:" + kp.getPrivate().getEncoded().length);
			System.out.println("public length:" + kp.getPublic().getEncoded().length);
		}

		return kp == null ? null : kp.getPrivate();
	}

	public KeyPair updateSelfEncryptKey(String peerid) throws CryptorException {
		KeyPair kp = null;

		try {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			SecureRandom secureRandom = new SecureRandom();
			secureRandom.setSeed(DEFAULT_SEED.getBytes());
			keygen.initialize(2048, secureRandom);

			kp = keygen.genKeyPair();
			saveKey(peerid, kp);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptorException(e);
		}
		return kp;
	}

	public PublicKey updateSelfEncryptKey() throws CryptorException {
		String name = SConfig.getInstance().getProfile().myPeerBean.PPeerid;
		KeyPair kp = null;
		try {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			String updateTime = null;
			try {
				updateTime = SPubkey.getInstance().getPubTime(name);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// ��ʼ����������
			SecureRandom secureRandom = new SecureRandom();
			secureRandom.setSeed(DEFAULT_SEED.getBytes());
			keygen.initialize(2048, secureRandom);

			kp = keygen.genKeyPair();
			saveKey(name, kp);

			AnPeersBean.getInstance().savePeerKey(name, kp.getPublic(), updateTime);
			// LOG.System.out("Generate the new Private/Publick KeyPair successful.");
		} catch (NoSuchAlgorithmException e) {
			throw new CryptorException(e);
		}
		return kp.getPublic();
	}

	private KeyPair initRSAKeyPair(String name, String seed) throws CryptorException {
		com.mx.clent.vo.Profile profile = SConfig.getInstance().getProfile();
		KeyPair kp = null;

		try {
			kp = profile.getKeyPair();
		} catch (Exception e) {
			throw new CryptorException("Invalid key specification is encountered.");
		}

		try {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			SecureRandom secureRandom = new SecureRandom();
			secureRandom.setSeed(seed.getBytes());
			keygen.initialize(2048, secureRandom);

			kp = keygen.genKeyPair();
			saveKey(name, kp);

			// LOG.System.out("Generate the new Private/Publick KeyPair successful.");
		} catch (NoSuchAlgorithmException e) {
			throw new CryptorException(e);
		}
		return kp;
	}

	public void setIfUpdatePubkeyPair(boolean ifUpdatePubkeyPair) {
		this.ifUpdatePubkeyPair = ifUpdatePubkeyPair;
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @throws CryptorException
	 */
	public PublicKey getEncryptKey(String name) throws CryptorException {
		System.out.println("RSA:" + "getEncryptKey:" + name);
		PublicKey key = getPeerEncryptKey(name);
		return key;
	}

	private PublicKey getPeerEncryptKey(String name) throws CryptorException {
		AnPeersBean peer = null;
		PublicKey pkey = null;

		System.out.println("RSA:" + "getPeerEncryptKey:" + name);
		peer = AnPeersBean.getInstance().getUserByPeerID(name);
		if (peer.publicKey != null) {
			pkey = peer.publicKey;
		} else {
			String pubkey_str;
			String pubkey_time;
			byte[] decoded;
			try {
				pubkey_str = SPubkey.getInstance().getPubKey(name);
				pubkey_time = SPubkey.getInstance().getPubTime(name);
				decoded = Base64.decodeBase64(pubkey_str.getBytes());
				pkey = PubkeyUtils.decodePublic(decoded, "RSA");
				AnPeersBean.getInstance().savePeerKey(name, pkey, pubkey_time);
				System.out.println("RSA:2");
			} catch (NoSuchAlgorithmException e) {
				System.out.println("RSA:3");
				throw new CryptorException(e);
			} catch (InvalidKeySpecException e) {
				try {
					System.out.println("RSA:4");
					pubkey_str = SPubkey.getInstance().getPubKey(name);
					pubkey_time = SPubkey.getInstance().getPubTime(name);
					decoded = Base64.decodeBase64(pubkey_str.getBytes());
					byte[] mod = new byte[128];
					System.arraycopy(decoded, 29, mod, 0, 128);
					byte[] ed = new byte[3];
					System.arraycopy(decoded, 159, ed, 0, 3);
					BigInteger m = new BigInteger(mod);
					BigInteger ev = new BigInteger(ed);
					RSAPublicKeySpec pubkeyspec = new RSAPublicKeySpec(m, ev);
					pkey = PubkeyUtils.decodePublicKey(pubkeyspec, "RSA");
					AnPeersBean.getInstance().savePeerKey(name, pkey, pubkey_time);
					System.out.println("RSA:5");
					return pkey;
				} catch (Exception exxx) {
					System.out.println("RSA:6");
					throw new CryptorException(exxx);
				}
			} catch (IOException e) {
				System.out.println("RSA:7");
				e.printStackTrace();
			}
		}
		System.out.println("RSA:8");
		return pkey;
	}

	public void updatePublicKeyToServer() throws CryptorException, IOException {
		PublicKey key = null;
		if (ifUpdatePubkeyPair)
			key = KeyManager.getInstance().updateSelfEncryptKey();
		String encode_key = new String(Base64.encodeBase64(PubkeyUtils.getEncodedPublic(key)));
		try {
			System.out.println("update private key:"
					+ Base64.encodeBase64String(KeyManager.getInstance().getDecryptKey("3").getEncoded()));
			System.out.println("update public key:"
					+ Base64.encodeBase64String(KeyManager.getInstance().getEncryptKey("3").getEncoded()));
			SPubkey.getInstance().postPubKey(encode_key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveKey(String name, KeyPair kp) {
		Profile profile = SConfig.getInstance().getProfile();
		if (profile == null) {
			return;
		}
		try {
			profile.changeKeyPair(kp);
		} catch (Exception e) {
			// LOG.e("wjy", "save profile error");
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ�ϴ���Կ��ʱ��
	 * 
	 * @param peerid
	 * @return
	 */
	public String getUpdatePubkeyTime(String peerid) {
		String updateTime = null;
		try {
			updateTime = SPubkey.getInstance().getPubTime(peerid);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateTime;
	}
}
