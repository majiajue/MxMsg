package com.mx.client.webtools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.mx.client.webtools.Cryptor.DecryptProgressListener;
import com.mx.client.webtools.Cryptor.EncryptProgressListener;


public class FileCryptor implements DecryptProgressListener, EncryptProgressListener {

	// public final static String FileCryptorCacheDirectory = SConfig.getUserDataDir() + "cache" + File.separator;
	public final static String FileCryptorCacheDirectory = "/MiXun/Pangolin/cache/";

	public final static String FileCryptorDecryptDirectory = SConfig.getInstance().getProfile().getUserDataDir()+ File.separator;
	private final File mFile;
	private final String mOutputFile;
	private final long mLength;

	public FileCryptor(File file, String outFile) throws SecurityException {
		mFile = file;
		mOutputFile = outFile;
		File directory = new File(FileCryptorCacheDirectory);
		if (directory.exists() == false) {
			if (directory.mkdirs() == false)
				throw new SecurityException("Could not create crypt cache dir.");
		}
		mLength = file.length();
		if (mLength <= 0)
			throw new SecurityException("Could not handle zero length file.");
	}

	public File encrypt(String password) {
		File res = null;
		InputStream fis = null;
		OutputStream fos = null;
		try {
			Cryptor enc = new Cryptor(password);
			enc.setEncryptProgressListener(this);
			File cacheFile;
			if (mOutputFile == null)
				cacheFile = new File(FileCryptorCacheDirectory + mFile.getName());
			else
				cacheFile = new File(mOutputFile);
			if (cacheFile.exists() == false)
				// LOG.d("Cryptor", "Could not delete crypt cache file before real encrypt work.");
				cacheFile.createNewFile();
			fis = new FileInputStream(mFile);
			fos = new FileOutputStream(cacheFile);
			enc.encrypt(fis, fos);
			res = cacheFile;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return res;
	}

	public File decrypt(String password) {
		File res = null;
		InputStream fis = null;
		OutputStream fos = null;
		try {
			Cryptor dec = new Cryptor(password);
			dec.setDecryptProgressListener(this);
			File cacheFile;
			if (mOutputFile == null)
				cacheFile = new File(FileCryptorDecryptDirectory + mFile.getName());
			else
				cacheFile = new File(mOutputFile);
			if (cacheFile.delete() == false)
				System.out.print("Could not delete crypt cache file before real work.");
			fis = new FileInputStream(mFile);
			fos = new FileOutputStream(cacheFile);
			dec.decrypt(fis, fos);
			res = cacheFile;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
				}
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
				}
		}

		return res;
	}

	public byte[] decryptContent(String password) {
		byte[] res = null;
		InputStream fis = null;
		ByteArrayOutputStream fos = null;
		try {
			Cryptor dec = new Cryptor(password);
			dec.setDecryptProgressListener(this);

			fis = new FileInputStream(mFile);
			fos = new ByteArrayOutputStream();
			dec.decrypt(fis, fos);
			res = fos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
				}
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
				}
		}

		return res;
	}

	@Override
	public void OnEncryptUpdateProgress(long progress) {
		if (mFileEncryptProgressListener != null) {
			mFileEncryptProgressListener.OnFileEncryptUpdateProgress((int) (100 * progress / mLength), mLength,
					progress);
		}

	}

	@Override
	public void OnEncryptFinished() {
		if (mFileEncryptProgressListener != null) {
			mFileEncryptProgressListener.OnFileEncryptFinished();
		}

	}

	@Override
	public void OnDecryptUpdateProgress(long progress) {
		if (mFileDecryptProgressListener != null) {
			mFileDecryptProgressListener.OnFileDecryptUpdateProgress((int) (100 * progress / mLength), mLength,
					progress);
		}

	}

	@Override
	public void OnDecryptFinished() {
		if (mFileDecryptProgressListener != null) {
			mFileDecryptProgressListener.OnFileDecryptFinished();
		}

	}

	private FileEncryptProgressListener mFileEncryptProgressListener;

	public void setFileEncryptProgressListener(FileEncryptProgressListener listener) {
		mFileEncryptProgressListener = listener;
	}

	/**
	 * 加密进度接口，用来触发加密数据进度的接口。
	 * 
	 * @author mada
	 * 
	 */
	public interface FileEncryptProgressListener {
		/**
		 * 当加密数据有更新时候更新进度
		 * 
		 * @param progress
		 *            当前进度
		 * @param total
		 *            文件总长度
		 * @param handled
		 *            已加密的字节长度
		 */
		public void OnFileEncryptUpdateProgress(int progress, long total, long handled);

		/**
		 * 所有数据加密完成
		 */
		public void OnFileEncryptFinished();
	}

	private FileDecryptProgressListener mFileDecryptProgressListener;

	public void setFileDecryptProgressListener(FileDecryptProgressListener listener) {
		mFileDecryptProgressListener = listener;
	}

	/**
	 * 文件解密进度接口，用来触发解密文件数据进度的接口。
	 * 
	 * @author mada
	 * 
	 */
	public interface FileDecryptProgressListener {
		/**
		 * 当解密数据有更新时候更新进度
		 * 
		 * @param progress
		 *            当前进度
		 * @param total
		 *            文件总长度
		 * @param handled
		 *            已解密的字节长度
		 */
		public void OnFileDecryptUpdateProgress(int progress, long total, long handled);

		/**
		 * 所有数据解密完成
		 */
		public void OnFileDecryptFinished();
	}

}
