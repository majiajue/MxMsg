package com.mx.client.webtools;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;



import org.apache.commons.codec.binary.Base64;


public class AncFile {
	private String mPassword = null;
	Cryptor mEnc = null;

	private AncFile() {
	}

	private static class SingletonHolder {
		public static final AncFile INSTANCE = new AncFile();
	}

	public static AncFile getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private String getPassword() throws Exception {
		// if (mPassword == null) {
			mPassword = SConfig.getInstance().getApplicationPSK();
		// }
			System.out.println("SConfig.getInstance().getApplicationPSK()--->"+mPassword);
		return mPassword;
	}

	private Cryptor getCryptor() throws Exception {
		// if (mEnc == null) {
			mEnc = new Cryptor(getPassword());
		// }
		return mEnc;
	}

	/**
	 * 
	 * @param saveToDir
	 *            保存到那个目录
	 * @param plainFilename
	 *            要生成的文件名：test.jpg
	 * @param src
	 *            要加密的文件
	 * @param mode
	 *            是否使用公私钥不对称加密
	 * @param pubkeyMode
	 * @return 加密过的文件
	 * @throws CryptorException
	 * @throws IOException
	 * @throws SecurityException
	 */
	public File encrypt(String saveToDir, String plainFilename, File src, boolean mode, String aeskey)
			throws CryptorException, IOException, SecurityException {
		File out = null;
		if (src != null && src.canRead()) {
			SUtil.getInstance().RealizeDirectory(saveToDir);
			String f = String.format("%s.%s", plainFilename, PassPhrase.getInstance().getNext(4));
			byte[] ef = null;
			if (mode)
				try {
					ef = getCryptor().encrypt(f.getBytes());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else {
				Cryptor enc = new Cryptor(aeskey);
				ef = enc.encrypt(f.getBytes());
			}
			String secretFilename = Hash.getInstance().convertToHex(ef);
			String saveTo = String.format("%s/%s.anc", saveToDir, secretFilename);
			FileCryptor fc = new FileCryptor(src, saveTo);
			out = fc.encrypt(f);
		}
		return out;
	}

public boolean decrypt(File src, String saveToDir){
	return decrypt( src,  saveToDir,null);
}
	public boolean decrypt(File src, String saveToDir,String filename) {
		File out = null;

		try {
			if (src != null && src.canRead()) {
				int pos = src.getName().lastIndexOf('.');
				if (pos == -1)
					return false;
				if(SConfig.getInstance().isFolderExists(saveToDir))
				{
				String secret = src.getName().substring(0, pos);
				byte[] bf = Convert.hexToBytes(secret);				
				String f = new String(getCryptor().decrypt(bf));	
				try{
					if (Base64.isArrayByteBase64(f.getBytes()))
						f = new String(Base64.decodeBase64(f.getBytes()));
				}catch(Exception e){
					e.printStackTrace();
				}
								
				pos = f.lastIndexOf('.');
				if (pos == -1)
					return false;
				FileCryptor fc=null;
				if(filename==null){
			fc = new FileCryptor(src, saveToDir + File.separator + f.substring(0, pos));
				}else{
					fc = new FileCryptor(src, saveToDir + File.separator + filename);
				}
				out = fc.decrypt(f);
				}
			}else{
				//LOG.v(CloudFileCursorAdapter.TAG,"文件不能读取");
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (CryptorException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;

	}
	
	public boolean decrypt(File src, String saveToDir,String filename,String key) {
		File out = null;
		try {
			if (src != null && src.canRead()) {			
				FileCryptor fc=null;		
					fc = new FileCryptor(src, saveToDir + File.separator + filename);		
				out = fc.decrypt(key);
				}else{
					return false;
				}
		
		} catch (SecurityException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		

	}

	public String getSecuretFilename(String password) throws Exception {
		byte[] ef = getCryptor().encrypt(password.getBytes());
		String secretFilename = Hash.getInstance().convertToHex(ef); // 对文件名进行加密
		return String.format("%s.anc", secretFilename); // 确定文件保存路径全称
	}

	/**
	 * 
	 * @param secretFilename
	 *            f2a0e1b9dd5f3e8cad56dc174222eb8832cf05c62d8cb29432e27dd6cc427b21
	 *            .anc"
	 * @return test.jpg.2345
	 * @throws Exception 
	 */
	public String getPlainFilename(String secretFilename) throws Exception {
		if (!secretFilename.endsWith(".anc")) {
			return null;
		}
		File file = new File(secretFilename);
		String filename = file.getName();
		String secret = filename.substring(0, filename.lastIndexOf('.'));
		byte[] bf = Convert.hexToBytes(secret);
		String plainFilename = new String(getCryptor().decrypt(bf));
		return plainFilename;
	}

	public String getPlainAESFilename(String secretFilename, String AESKey) throws CryptorException {
		if (!secretFilename.endsWith(".anc") || "".equals(AESKey)||null==AESKey) {
			return null;
		}
		File file = new File(secretFilename);
		String filename = file.getName();
		String secret = filename.substring(0, filename.lastIndexOf('.'));
		byte[] bf = Convert.hexToBytes(secret);
		Cryptor dec = new Cryptor(AESKey);
		String plainFilename = new String(dec.decrypt(bf));
		return plainFilename;
	}

	public byte[] getByteContent(String secretFilename) throws Exception {
		File file = new File(secretFilename);
		if (!file.exists()) {
			return null;
		}
		String plainFilename = getPlainFilename(secretFilename);
		Cryptor encFile = new Cryptor(plainFilename);
		FileInputStream input = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		input.read(buffer);
		input.close();
		return encFile.decrypt(buffer);
	}



	public byte[] decryptContent(String secretFilename) throws Exception {
		byte[] out = null;
		File src = new File(secretFilename);
		if (!src.exists()) {
			// return out;
			String p = System.getenv("TEMP") + "/pangolin";
			String d = System.getenv("TEMP") + "/MiXun/Pangolin";
			secretFilename = secretFilename.replace(p, d);
			src = new File(secretFilename);
			if (!src.exists()) {
				return out;
			}
		}

		try {
			if (src.canRead()) {
				int pos = src.getName().lastIndexOf('.');
				if (pos == -1)
					return out;

				String secret = src.getName().substring(0, pos);
				byte[] bf = Convert.hexToBytes(secret);
				String f = new String(getCryptor().decrypt(bf));

				try {
					if (Base64.isArrayByteBase64(f.getBytes()))
						f = new String(Base64.decodeBase64(f.getBytes()));
				} catch (Exception e) {

				}

				pos = f.lastIndexOf('.');
				if (pos == -1)
					return out;

				FileCryptor fc = new FileCryptor(src, null);
				out = fc.decryptContent(f);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (CryptorException e) {
			e.printStackTrace();
		}

		return out;

	}
}
