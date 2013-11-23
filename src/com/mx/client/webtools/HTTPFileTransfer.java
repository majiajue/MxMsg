package com.mx.client.webtools;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;

import com.mx.clent.vo.MsgExtraBean;



public class HTTPFileTransfer {
	private String TAG="HTTPFileTransfer";
	private static final int HttpBufferSize = 4096;
	private final SSLContext mSSLContext;
	private final TrustManager[] mTrustAllCerts;
	private final byte[] mBuffer = new byte[HttpBufferSize];
	private MsgExtraBean mSendBean;
	private long finishSize;
	private long mLastUpdateLength;
	protected long mLastUpdateTime;
//	ArrayList<ProgressbarData> progressbarDatas = new ArrayList<ProgressbarData>();
	long lastCompletedSize = 0;
	long lastTime = 0;
	long recvFileSpeed = 0;
	// 云存储暂用变量
	//设置一个boolean变量，使速度产生变化
	boolean changeSpeed=true;
	
	byte[] mFileindex;
	boolean isCloudFileTrans = false;
	File cloudCacheFile;
	String CloudFileId;
	//AnCloudFileBean Cloudbean;
	boolean ispause = false;
	// 表示是否下载完毕
	boolean isdownloadcomplete = false;

	public HTTPFileTransfer(MsgExtraBean mSendBean2, byte[] indexfile)
			throws NoSuchAlgorithmException, KeyManagementException {
		// 建立信任管理器，允许所有的无效证书
		mTrustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] certs,
					String authType) {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] certs,
					String authType) {
			}
		} };
		this.mSendBean = mSendBean2;
		if (this.mSendBean != null && !"".equals(mSendBean.M_Appendex)) {
			this.finishSize = Long.valueOf(mSendBean2.M_Appendex);
		} else
			this.finishSize = 0;
		if (indexfile != null) {
			this.mFileindex = indexfile;
		}
	
		mSSLContext = SSLContext.getInstance("TLS");
		mSSLContext.init(null, mTrustAllCerts, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(mSSLContext
				.getSocketFactory());
	}

	public HTTPFileTransfer() throws NoSuchAlgorithmException,
			KeyManagementException {
		// 建立信任管理器，允许所有的无效证书
		mTrustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] certs,
					String authType) {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] certs,
					String authType) {
			}
		} };
		mSSLContext = SSLContext.getInstance("TLS");
		mSSLContext.init(null, mTrustAllCerts, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(mSSLContext
				.getSocketFactory());
	}

	// 用于云文件传输
//	public HTTPFileTransfer(byte[] indexfile, File cloudfile, String fileid,
//			AnCloudFileBean cloudbean) throws NoSuchAlgorithmException,
//			KeyManagementException {
//		// 建立信任管理器，允许所有的无效证书
//		mTrustAllCerts = new TrustManager[] { new X509TrustManager() {
//			@Override
//			public X509Certificate[] getAcceptedIssuers() {
//				return null;
//			}
//
//			@Override
//			public void checkClientTrusted(X509Certificate[] certs,
//					String authType) {
//			}
//
//			@Override
//			public void checkServerTrusted(X509Certificate[] certs,
//					String authType) {
//			}
//		} };
//		if (indexfile != null) {
//			this.mFileindex = indexfile;
//		}
//		
//		this.isCloudFileTrans = true;
//		this.cloudCacheFile = cloudfile;
//		this.CloudFileId = fileid;
//		//this.Cloudbean = cloudbean;
//		this.finishSize = cloudbean.statusbean.transsize;
//		mSSLContext = SSLContext.getInstance("TLS");
//		mSSLContext.init(null, mTrustAllCerts, new SecureRandom());
//		HttpsURLConnection.setDefaultSSLSocketFactory(mSSLContext
//				.getSocketFactory());
//	}

//	private String convertStreamToString(InputStream is) {
//		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//		StringBuilder stringBuilder = new StringBuilder();
//
//		String line = null;
//		try {
//			while ((line = reader.readLine()) != null) {
//				stringBuilder.append(line + "\n");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				is.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return stringBuilder.toString();
//	}
//
//	private String mLastModified;
//
//	public String getLastModified() {
//		return mLastModified;
//	}
//
//	public String[] uploadFile() throws IOException {
//		return this.uploadFile(false, null);
//	}
//
////	// 根据index的值进行传输文件
////	public String[] uploadFile(boolean isroom, String roomid)
////			throws IOException {
////		refreshThread sd = new refreshThread();
////		Thread th = new Thread(sd);
////		File file;
////		if (isCloudFileTrans) {
////			file = cloudCacheFile;
////		} else {
////			file = new File(mSendBean.PFileuri);
////		}
////		String[] results = null;
////		try {
////			if (!isCloudFileTrans)
////				th.start();
////			int i, j;
////			for (i = 0; i < file.length() / (1024 * 800 * 8); i++) {
////				for (j = 0; j < 8; j++) {
////					if ((mFileindex[i] & (0x01 << j)) == 0) {
////						int indexNumber = i * 8 + j;
////						if (isCloudFileTrans) {
////							if (!ispause) {
////								CloudFileupload(indexNumber);
////							} else {
////								return null;
////							}
////						} else {
////							upload(indexNumber, isroom, roomid, sd);
////						}
////					}
////
////				}
////			}
////			for (int k = 0; k < (file.length() / (1024 * 800)) % 8 + 1; k++) {
////				int a = (int) (file.length() / (1024 * 800 * 8));
////				if ((mFileindex[a] & (0x01 << k)) == 0) {
////					int indexNumber = a * 8 + k;
////					if (isCloudFileTrans) {
////						if (!ispause) {
////							CloudFileupload(indexNumber);
////						} else {
////							return null;
////						}
////					} else {
////						upload(indexNumber, isroom, roomid, sd);
////					}
////				}
////			}
////			// 传输完成后向服务器确定是否传输完整
////			if (isroom) {
////				results = SSendFile.getInstance().postIsCompleteToServer(
////						mSendBean.PFileid, roomid, isroom);
////			} else {
////				// 根据是否是云存储文件修改
////				if (isCloudFileTrans) {
////					results = SSendFile.getInstance()
////							.cloudPostIsCompleteToServer(CloudFileId);
////
////				} else {
////					results = SSendFile.getInstance().postIsCompleteToServer(
////							mSendBean.PFileid, mSendBean.PPeerid, isroom);
////				}
////			}
////
////		} catch (Exception e) {
////			e.printStackTrace();
////		} finally {
////			synchronized (sd) {
////				// sd.stop();
////				// sd.notify();
////			}
////
////		}
////
////		return results;
////
////	}
//
//	public boolean UploadFileToServer() {
//		return this.UploadFileToServer(false, null);
//	}
//
////	// 对文件上传失败进行控制
////	public boolean UploadFileToServer(boolean isroom, String roomid) {
////		boolean result = false;
////		String[] results = null;
////		try {
////
////			results = uploadFile(isroom, roomid);
////			if (results != null) {
////				if (results[0] != null && results[0].equals("ok")) {
////					result = true;
////				} else if (results[1] != null) {
////
////					char[] data = results[1].toCharArray();
////					int len = data.length;
////					byte[] bt = new byte[len];
////					for (int i = 0; i < len; i++) {
////						bt[i] = Byte.decode("0x" + data[i]);
////					}
////					if (!isCloudFileTrans)
////						mSendBean.sendblocks = bt;
////					results = uploadFile(isroom, roomid);
////
////					if (results[0] != null && results[0].equals("ok")) {
////						result = true;
////					} else if (results[1] != null) {
////
////						char[] data2 = results[1].toCharArray();
////						int len1 = data2.length;
////						byte[] bt1 = new byte[len1];
////						for (int i = 0; i < len1; i++) {
////							bt1[i] = Byte.decode("0x" + data2[i]);
////						}
////						if (!isCloudFileTrans)
////							mSendBean.sendblocks = bt1;
////						results = uploadFile(isroom, roomid);
////
////						if (results[0] != null && results[0] == "OK") {
////							result = true;
////						}
////					}
////				}
////
////			} else {	
////				return false;
////
////			}
////		} catch (Exception e) {
////			if (!isCloudFileTrans) {
////				StorageManager
////						.GetInstance()
////						.getMsgExtrasList()
////						.setFileRecvStatus(DataDefine.STATUS_FILE_SEND_FAILED,
////								mSendBean.PFileid);
////			} else {
////				StorageManager
////						.GetInstance()
////						.getCloudStatusList()
////						.setCloudFileStatus(
////								DataDefine.STATUS_CLOUD_FILE_PAUSE_UP,
////								Cloudbean.fileid);
////			}
////			result=false;
////		}
////		return result;
////
////	}
//
//	public void upload(int indexNumber, refreshThread sd) throws IOException {
//		this.upload(indexNumber, false, null, sd);
//	}
//
//	// 根据文件索引进行文件上传
////	private void upload(int indexNumber, boolean isRoom, String roomid,
////			refreshThread sd) throws IOException {
////		// 构造HttpPut实例
////		long start = System.currentTimeMillis();
////	
////		File file = new File(mSendBean.PFileuri);
////		HttpClient wclient = null;
////		wclient = SCore.getInstance().initPgHttpClient();
////		HttpPut put;
////		if (!isRoom) {
////			put = new HttpPut("https://" + SConfig.getInstance().getWebHost() + ":"
////					+ SConfig.getInstance().getWebPort() + "/"
////					+ "upfile/"
////					+ StorageManager.GetInstance().getUserProfile()
////							.getSession() + "/" + mSendBean.PPeerid + "/"
////					+ mSendBean.PFileid + "/" + indexNumber + "/call.xml");
////			LOG.v("web", "https://" + SConfig.getInstance().getWebHost() + ":" + SConfig.getInstance().getWebPort()
////					+ "/" + "upfile/"
////					+ StorageManager.GetInstance().getUserProfile()
////							.getSession() + "/" + mSendBean.PPeerid + "/"
////					+ mSendBean.PFileid + "/" + indexNumber + "/call.xml");
////		} else {
////			put = new HttpPut("https://" + SConfig.getInstance().getWebHost() + ":"
////					+ SConfig.getInstance().getWebPort() + "/"
////					+ "upfile/"
////					+ StorageManager.GetInstance().getUserProfile()
////							.getSession() + "/" + roomid + "/"
////					+ mSendBean.PFileid + "/" + indexNumber + "/call.xml");
////			LOG.v("web", "https://" + SConfig.getInstance().getWebHost() + ":" + SConfig.getInstance().getWebPort()
////					+ "/"
////					+ "upfile/"
////					+ StorageManager.GetInstance().getUserProfile()
////							.getSession() + "/" + roomid + "/"
////					+ mSendBean.PFileid + "/" + indexNumber + "/call.xml");
////		}
////		RandomAccessFile fileOutStream = new RandomAccessFile(file, "r");
////		int position = indexNumber * 1024 * 800;
////		fileOutStream.seek(position);
////		// 提前算出buffer的大小
////		byte[] buffer = null;
////		if (indexNumber == file.length() / (1024 * 800)) {
////			int a = (int) (file.length() % (1024 * 800));
////			buffer = new byte[a];
////		} else {
////			buffer = new byte[1024 * 800];
////		}
////		int length = fileOutStream.read(buffer);
////		ByteArrayEntity entity = new ByteArrayEntity(buffer);
////		put.setEntity(entity);
////		HttpResponse response = null;
////		try {
////			response = wclient.execute(put);
////			HttpEntity resEntity = response.getEntity();
////			// 对返回结果进行处理
////			if (resEntity != null) {
////				InputStream inputStream = null;
////				try {
////					inputStream = resEntity.getContent();
////				} catch (IllegalStateException e) {
////					e.printStackTrace();
////				}
////				String result = convertStreamToString(inputStream);
////				LOG.v("web", result);
////				result = SXmlParser.xmlResponseTag(result, "status");
////				if (TextUtils.equals(result, "ok")) {
////					// 把index中相应的位置一
////					int a = indexNumber / 8;
////					int b = indexNumber % 8;
////					mSendBean.sendblocks[a] = (byte) (mSendBean.sendblocks[a] | ((0x01) << b));
////
////					finishSize += length;
////
////					long end = System.currentTimeMillis();
////				
////					long time = end - start;
////					LOG.v(TAG,"length:"+length+" time: "+time);
////					ProgressbarData progressbarData = new ProgressbarData(time,
////							finishSize, length, mSendBean.PFull_size);
////					progressbarDatas.add(progressbarData);
////					synchronized (sd) {
////						sd.notify();
////					}
////
////				}
////			}
////		} catch (Exception e) {
////			e.printStackTrace();
////			// 设置失败状态
////			// 保存断点状态，把sendblocks,和finishsize保存起来
////			ContentValues values = new ContentValues();
////			values.put(DataDefine.COL_TRANS_COMPLETE_INDEXS,
////					mSendBean.sendblocks);
////			values.put(DataDefine.COL_EXTRA_APPENDEX,
////					String.valueOf(finishSize));
////			StorageManager
////					.GetInstance()
////					.getMsgExtrasList()
////					.update(values, DataDefine.COL_TRANS_FILEID + "=?",
////							new String[] { mSendBean.PFileid });
////			StorageManager
////					.GetInstance()
////					.getMsgExtrasList()
////					.setFileRecvStatus(DataDefine.STATUS_FILE_SEND_FAILED,
////							mSendBean.PFileid);
////
////		} finally {
////			if (wclient != null) {
////				wclient.getConnectionManager().shutdown();
////			}
////		}
////
////	}
//
////	/*
////	 * 根据文件索引进行文件上传 修改传输协议
////	 */
////	private void CloudFileupload(int cloudIndexNumber) throws IOException {
////		// 构造HttpPut实例
////
////		HttpClient wclient = null;
////		wclient = SCore.getInstance().initPgHttpClient();
////		HttpPut put;
////		put = new HttpPut("https://" + SConfig.getInstance().getWebHost() + ":" + SConfig.getInstance().getWebPort()
////				+ "/" + "upfile/"
////				+ StorageManager.GetInstance().getUserProfile().getSession()
////				+ "/" + StorageManager.GetInstance().getUserProfile().MyPeerid
////				+ "/" + CloudFileId + "/" + cloudIndexNumber + "/call.xml");
////		LOG.v("web", "https://" + SConfig.getInstance().getWebHost() + ":" + SConfig.getInstance().getWebPort() + "/"
////				+ "upfile/" + StorageManager.GetInstance().getUserProfile().getSession()
////				+ "/" + StorageManager.GetInstance().getUserProfile().MyPeerid
////				+ "/" + CloudFileId + "/" + cloudIndexNumber + "/call.xml");
////		RandomAccessFile fileOutStream = new RandomAccessFile(cloudCacheFile,
////				"r");
////		int position = cloudIndexNumber * 1024 * 800;
////		fileOutStream.seek(position);
////		// 提前算出buffer的大小
////		byte[] buffer = null;
////		if (cloudIndexNumber == cloudCacheFile.length() / (1024 * 800)) {
////			int a = (int) (cloudCacheFile.length() % (1024 * 800));
////			buffer = new byte[a];
////		} else {
////			buffer = new byte[1024 * 800];
////		}
////		int length = fileOutStream.read(buffer);
////		ByteArrayEntity entity = new ByteArrayEntity(buffer);
////		put.setEntity(entity);
////		HttpResponse response = null;
////		try {
////			response = wclient.execute(put);
////			HttpEntity resEntity = response.getEntity();
////			// 对返回结果进行处理
////			if (resEntity != null) {
////				InputStream inputStream = null;
////				try {
////					inputStream = resEntity.getContent();
////				} catch (IllegalStateException e) {
////					e.printStackTrace();
////				}
////				String result = convertStreamToString(inputStream);
////				LOG.v("web", result);
////				result = SXmlParser.xmlResponseTag(result, "status");
////				if (TextUtils.equals(result, "ok")) {
////					// 把index中相应的位置一
////					int a = cloudIndexNumber / 8;
////					int b = cloudIndexNumber % 8;
////					mFileindex[a] = (byte) (mFileindex[a] | ((0x01) << b));
////					cloudIndexNumber++;
////
////					finishSize += length;
////
////					long percentage = (long) (((float) finishSize / Cloudbean.filelength) * 100);
////
////					ContentValues values = new ContentValues();
////					values.put(DataDefine.COL_CLOUD_PERCENTAGE, percentage);
////					values.put(DataDefine.COL_CLOUD_SENDED_INDEXS, mFileindex);
////					values.put(DataDefine.COL_CLOUD_TRANSSIZE, finishSize);
////					StorageManager
////							.GetInstance()
////							.getCloudStatusList()
////							.update(values, DataDefine.COL_CLOUD_LINKID + "=?",
////									new String[] { Cloudbean.fileid });
////				}
////			}
////		} catch (Exception e) {
////			e.printStackTrace();
////		} finally {
////			if (wclient != null) {
////				wclient.getConnectionManager().shutdown();
////			}
////		}
////
////	}
//
//	public boolean downloadFile(File file, String RoomId, String url,
//			String fileId, String filetype, String filesavepath) {
//		URL uri = null;
//		String protocol = "";
//		mLastModified = null;
//		try {
//			if (url == null || url.equals("null://null/")) {
//				protocol = "http://"+SConfig.getInstance().getDownLdHost()+":"+SConfig.getInstance().getDownLdPort()+"/";
//				if (!TextUtils.isEmpty(RoomId))
//					uri = new URL(protocol
//							+ "roomrfs/"
//							+ StorageManager.GetInstance().getUserProfile()
//									.getSession() + "/" + RoomId + "/" + fileId
//							+ "/call.xml");
//				else
//					uri = new URL(protocol
//							+ "newrfs/"
//							+ StorageManager.GetInstance().getUserProfile()
//									.getSession() + "/" + fileId + "/call.xml");
//			} else {
//				if (!TextUtils.isEmpty(RoomId))
//					uri = new URL(protocol
//							+ "roomrfs/"
//							+ StorageManager.GetInstance().getUserProfile()
//									.getSession() + "/" + RoomId + "/" + fileId
//							+ "/call.xml");
//				else
//					uri = new URL(protocol
//							+ "rfs/"
//							+ StorageManager.GetInstance().getUserProfile()
//									.getSession() + "/" + fileId + "/call.xml");
//			}
//
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//			return false;
//		}
//		return downloadFile(file, uri, fileId, filetype, filesavepath);
//	}
//
//	public boolean downloadFile(File file, URL uri, String fileId,
//			String filetype, String filesavepath) {
//		boolean res = false;
//
//		HttpURLConnection con = null;
//		long handled = 0;
//		long length = 0;
//		long length_all = 0;
//		mLastModified = null;
//
//		try {
//			//LOG.v("web", "url:" + uri.toString());
//			con = (HttpURLConnection) uri.openConnection();
//			con.setDoInput(true);
//
//			con.setDoOutput(false);
//			con.setUseCaches(false);
//			con.setRequestMethod("GET");
//			con.setRequestProperty("Connection", "Close");
//			con.setRequestProperty("Charset", "UTF-8");
//			// 设置范围，格式为Range：bytes x-y;
//
//			MsgExtraBean taskBean = StorageManager.GetInstance()
//					.getMsgExtrasList().getExtraBeanByFildId(fileId);
//			long completed;
//			if (taskBean._id == -1 || TextUtils.isEmpty(taskBean.M_Appendex)) {
//				completed = 0;
//			} else {
//
//				completed = Long.valueOf(taskBean.M_Appendex);
//			}
//
//			con.setRequestProperty("Range", "bytes= " + (completed) + " -");
//			con.setConnectTimeout(15 * 1000);
//			con.setReadTimeout(3 * 60 * 1000);
//			con.connect();
//
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//
//			int errCode = con.getResponseCode();
//			if (errCode % 206 == 0 || errCode % 200 == 0) {
//				/* Do some HTTP header work */
//				// List all the response headers from the server.
//				// Note: The first call to getHeaderFieldKey() will implicit
//				// send
//				// the HTTP request to the server.
//				for (int i = 0;; i++) {
//					String headerName = con.getHeaderFieldKey(i);
//					String headerValue = con.getHeaderField(i);
//
//					if (headerName == null && headerValue == null) {
//						// No more headers
//						break;
//					}
//					if (headerName == null)
//						continue;
//
//					if (headerName.equalsIgnoreCase("Last-modified")) {
//						if (mLastModified == null)
//							mLastModified = headerValue;
//						else
//							mLastModified += headerValue;
//					} else if (headerName.equalsIgnoreCase("Etag")) {
//						if (mLastModified == null)
//							mLastModified = headerValue;
//						else
//							mLastModified += headerValue;
//					}
//				}
//				// 文件的初始化
//
//				RandomAccessFile randomAccessFile = new RandomAccessFile(file,
//						"rwd");
//				randomAccessFile.seek(completed);
//				InputStream is = con.getInputStream();
//				int nRead = -1;
//				while ((nRead = is.read(mBuffer)) != -1) {
//					if (length == 0) {
//						length = con.getContentLength() + completed;
//						if (length <= 0)
//							break;
//					}
//					randomAccessFile.write(mBuffer, 0, nRead);
//					completed += nRead;
//					taskBean.PCompleted_size = (long) (((float) completed / taskBean.PFull_size) * 100);
//					long currentTime = System.currentTimeMillis();
//					if (taskBean.PCompleted_size - lastCompletedSize > 4) {
//						// 增加speed的计算
//						long timeInterval = currentTime - lastTime;
//						lastTime = currentTime;
//						recvFileSpeed = (long) ((100 * 1000 * nRead)
//								/ timeInterval / 1024.0);
//						ContentValues values = new ContentValues();
//						values.put(DataDefine.COL_TRANS_COMPLETEDSIZE,
//								taskBean.PCompleted_size);
//						values.put(DataDefine.COL_TRANS_SPEED, recvFileSpeed);
//						values.put(DataDefine.COL_EXTRA_APPENDEX,
//								String.valueOf(completed));
//						StorageManager
//								.GetInstance()
//								.getMsgExtrasList()
//								.update(values,
//										DataDefine.COL_TRANS_FILEID + "=?",
//										new String[] { fileId });
//						lastCompletedSize = taskBean.PCompleted_size;
//					}
//				}
//				try {
//					randomAccessFile.close();
//					is.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				} finally {
//					// 把文件解密
//					StorageManager
//							.GetInstance()
//							.getMsgExtrasList()
//							.setFileRecvStatus(
//									DataDefine.STATUS_FILERECV_DECRYPT, fileId);
//					boolean result = false;
//					if (filetype.equals("file")) {
//						result = AncFile.getInstance().decrypt(file,
//								FileReceiveTask.FileReceiveDirectory);
//					} else {
//						result = AncFile.getInstance().decrypt(file,
//								filesavepath);
//					}
//
//					if (result) {
//						StorageManager
//								.GetInstance()
//								.getMsgExtrasList()
//								.setFileRecvStatus(
//										DataDefine.STATUS_FILE_RECV_SUCESS,
//										fileId);
//					}
//				}
//				res = true;
//			} else {
//				// 文件下载失败
//			}
//			con.disconnect();
//		} catch (Exception e) {
//			StorageManager
//					.GetInstance()
//					.getMsgExtrasList()
//					.setFileRecvStatus(DataDefine.STATUS_FILE_RECV_FAILED,
//							fileId);
//			e.printStackTrace();
//
//		}
//		return res;
//	}
//
//	public void setIspause(boolean pause) {
//		this.ispause = pause;
//	}
//
//	/*
//	 * file:下载到那个文件里 fileId:云文件下载的唯一标志符 filesavepath:文件下载完后解密的位置
//	 */
//	@SuppressWarnings("finally")
//	public boolean cloudFileDownload(File file, String fileId) {
//
//		URL uri = null;
//		String protocol;
//		protocol = "http://" + SConfig.getInstance().getDownLdHost() + ":" + SConfig.getInstance().getDownLdPort()
//				+ "/";
//		try {
//			uri = new URL(protocol
//					+ "cloud_getoldfile/"
//					+ StorageManager.GetInstance().getUserProfile()
//							.getSession() + "/" + fileId);
//		} catch (MalformedURLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		LOG.v("web", "url:" + uri.toString());
//
//		HttpURLConnection con = null;
//		long handled = 0;
//		long length = 0;
//		long length_all = 0;
//		mLastModified = null;
//		AnCloudFileBean cloudfilebean = null;
//		try {
//			con = (HttpURLConnection) uri.openConnection();
//			con.setDoInput(true);
//			con.setDoOutput(false);
//			con.setUseCaches(false);
//			con.setRequestMethod("GET");
//			con.setRequestProperty("Connection", "Close");
//			con.setRequestProperty("Charset", "UTF-8");
//			// 根据fileId从数据库中找到bean
//
//			cloudfilebean = StorageManager.GetInstance().getCloudStorageList()
//					.getCloudFileBeanByFildId(fileId);
//			long completedsize = 0;
//			if (cloudfilebean.statusbean.transsize != 0) {
//				completedsize = cloudfilebean.statusbean.transsize;
//			}
//			// 设置范围，格式为Range：bytes x-y;
//			con.setRequestProperty("Range", "bytes= " + (completedsize) + " -");
//			con.setConnectTimeout(15 * 1000);
//			con.setReadTimeout(3 * 60 * 1000);
//			con.connect();
//
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//			int errCode = con.getResponseCode();
//			LOG.v("web", "errCode" + errCode);
//			if (errCode % 206 == 0 ) {
//				/* Do some HTTP header work */
//				// List all the response headers from the server.
//				// Note: The first call to getHeaderFieldKey() will implicit
//				// send
//				// the HTTP request to the server.
//				for (int i = 0;; i++) {
//					String headerName = con.getHeaderFieldKey(i);
//					String headerValue = con.getHeaderField(i);
//
//					if (headerName == null && headerValue == null) {
//						// No more headers
//						break;
//					}
//					if (headerName == null)
//						continue;
//
//					if (headerName.equalsIgnoreCase("Last-modified")) {
//						if (mLastModified == null)
//							mLastModified = headerValue;
//						else
//							mLastModified += headerValue;
//					} else if (headerName.equalsIgnoreCase("Etag")) {
//						if (mLastModified == null)
//							mLastModified = headerValue;
//						else
//							mLastModified += headerValue;
//					}
//				}
//				// 文件的初始化
//
//				RandomAccessFile randomAccessFile = new RandomAccessFile(file,
//						"rwd");
//				randomAccessFile.seek(0);
//				InputStream is = con.getInputStream();
//				int nRead = -1;
//				int time = 0;
//				while ((nRead = is.read(mBuffer)) != -1) {
//					// 开始循环之前先检查一下状态
//					if (!ispause) {
//						time++;
//						randomAccessFile.write(mBuffer, 0, nRead);
//						completedsize += nRead;
//						if(completedsize==cloudfilebean.filelength){
//							long percentage = (long) (((float) completedsize / cloudfilebean.filelength) * 100);
//							ContentValues values = new ContentValues();
//							values.put(DataDefine.COL_CLOUD_TRANSSIZE,
//									completedsize);
//							values.put(DataDefine.COL_CLOUD_PERCENTAGE,
//									percentage);
//							StorageManager
//									.GetInstance()
//									.getCloudStatusList()
//									.update(values,
//											DataDefine.COL_CLOUD_LINKID + "=?",
//											new String[] { fileId });
//							isdownloadcomplete=true;
//						}
//						// 降低更新数据库的频率
//						if (time == 10) {
//							long percentage = (long) (((float) completedsize / cloudfilebean.filelength) * 100);
//							ContentValues values = new ContentValues();
//							values.put(DataDefine.COL_CLOUD_TRANSSIZE,
//									completedsize);
//							values.put(DataDefine.COL_CLOUD_PERCENTAGE,
//									percentage);
//							StorageManager
//									.GetInstance()
//									.getCloudStatusList()
//									.update(values,
//											DataDefine.COL_CLOUD_LINKID + "=?",
//											new String[] { fileId });
//							time = 0;
//						}
//
//					} else  {
//						// 设置状态为暂停
//						StorageManager
//								.GetInstance()
//								.getCloudStatusList()
//								.setCloudFileStatus(
//										DataDefine.STATUS_CLOUD_FILE_PAUSE,
//										fileId);
//						isdownloadcomplete = false;
//						randomAccessFile.close();
//						is.close();
//						break;
//					}
//				}
//			
//				randomAccessFile.close();
//				is.close();
//
//			}else{
//				isdownloadcomplete = false;
//			}// end errcode==200
//		} catch (Exception e) {
//		
//			// 网络断掉后会进入该异常处理程序
//			e.printStackTrace();
//			// 设置状态为暂停
//			StorageManager
//					.GetInstance()
//					.getCloudStatusList()
//					.setCloudFileStatus(DataDefine.STATUS_CLOUD_FILE_PAUSE,
//							fileId);
//			isdownloadcomplete = false;
//		} finally {
//			con.disconnect();			
//			boolean result = false;
//			if (isdownloadcomplete && cloudfilebean != null) {
//				result = AncFile.getInstance().decrypt(file,
//						FileReceiveTask.CloudFileDownloadDir,
//						cloudfilebean.filename,cloudfilebean.decryptkey);
//				if(result){
//					file.delete();
//				}
//			}
//			return result;
//		}
//	}
//
//	private HttpFileDownloadProgressListener mHttpFileDownloadProgressListener;
//
//	public void setHttpFileDownloadProgressListener(
//			HttpFileDownloadProgressListener listener) {
//		mHttpFileDownloadProgressListener = listener;
//	}

//	public interface HttpFileDownloadProgressListener {
//		public void OnDownloadBegin();
//
//		public void OnDownloadProgress(int progress, long total, long handled);
//
//		public void OnDownloadSuccess();
//
//		public void OnDownloadFailed();
//	}
//
//	public boolean downloadFile(File file, URL uri) {
//		boolean res = false;
//		HttpURLConnection con = null;
//		long handled = 0;
//		long length = 0;
//		mLastModified = null;
//
//		try {
//			con = (HttpURLConnection) uri.openConnection();
//			con.setDoInput(true);
//			con.setUseCaches(false);
//			con.setRequestMethod("GET");
//			con.setRequestProperty("Connection", "Close");
//			con.setRequestProperty("Charset", "UTF-8");
//			con.setConnectTimeout(15 * 1000);
//			con.setReadTimeout(3 * 60 * 1000);
//			con.connect();
//
//			if (file.exists()) {
//				if (file.delete() == false)
//					LOG.d("web", "Could not delete the old download file.");
//			}
//			if (mHttpFileDownloadProgressListener != null) {
//				mHttpFileDownloadProgressListener.OnDownloadBegin();
//			}
//
//			int errCode = con.getResponseCode();
//			if (errCode % 200 == 0) {
//				/* Do some HTTP header work */
//				// List all the response headers from the server.
//				// Note: The first call to getHeaderFieldKey() will implicit
//				// send
//				// the HTTP request to the server.
//				for (int i = 0;; i++) {
//					String headerName = con.getHeaderFieldKey(i);
//					String headerValue = con.getHeaderField(i);
//
//					if (headerName == null && headerValue == null) {
//						// No more headers
//						break;
//					}
//					if (headerName == null)
//						continue;
//
//					if (headerName.equalsIgnoreCase("Last-modified")) {
//						if (mLastModified == null)
//							mLastModified = headerValue;
//						else
//							mLastModified += headerValue;
//					} else if (headerName.equalsIgnoreCase("Etag")) {
//						if (mLastModified == null)
//							mLastModified = headerValue;
//						else
//							mLastModified += headerValue;
//					}
//				}
//
//				FileOutputStream fStream = new FileOutputStream(file);
//				InputStream is = con.getInputStream();
//				int nRead = -1;
//				while ((nRead = is.read(mBuffer)) != -1) {
//					if (length == 0) {
//						length = con.getContentLength();
//						if (length <= 0)
//							break;
//					}
//					fStream.write(mBuffer, 0, nRead);
//					handled += nRead;
//					if (mHttpFileDownloadProgressListener != null) {
//						mHttpFileDownloadProgressListener
//								.OnDownloadProgress(
//										(int) (handled * 100 / length), length,
//										handled);
//					}
//				}
//				LOG.System.out("Received: file " + file.toString()
//						+ " with last-mod " + mLastModified);
//				try {
//					fStream.close();
//					is.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				} finally {
//					if (mHttpFileDownloadProgressListener != null) {
//						if (length <= 0)
//							mHttpFileDownloadProgressListener
//									.OnDownloadFailed();
//						else
//							mHttpFileDownloadProgressListener
//									.OnDownloadSuccess();
//					}
//				}
//				res = true;
//			} else {
//				LOG.w("web", "File server response err: " + errCode
//						+ " for url " + uri.toString());
//				if (mHttpFileDownloadProgressListener != null) {
//					mHttpFileDownloadProgressListener.OnDownloadFailed();
//				}
//			}
//			con.disconnect();
//		} catch (Exception e) {
//			e.printStackTrace();
//			if (mHttpFileDownloadProgressListener != null) {
//				mHttpFileDownloadProgressListener.OnDownloadFailed();
//			}
//		}
//		return res;
//	}
//
//	class refreshThread implements Runnable {
//		boolean isStop = false;
//		boolean hasdata = true;
//
//		public void stop() {
//			isStop = true;
//		}
//
//		@Override
//		public void run() {
//			while (!isStop) {
//				try {
//					synchronized (this) {
//						wait();
//
//						while (hasdata) {
//							ProgressbarData Data = null;
//							if (!progressbarDatas.isEmpty()) {
//								Data = progressbarDatas.remove(0);
//								if (Data == null)
//									break;
//							} else {
//								break;
//							}
//
//							long rate = 0;
//							for (int i = 0; i < 15; i++) {
//								this.wait(Data.time / 10);																			
//								double speed=(double)(Data.length*1000)/(double)(Data.time*1024);	
//								if(changeSpeed){
//									speed=speed+0.001;
//									changeSpeed=false;
//								}else{
//									speed=speed-0.001;
//									changeSpeed=true;
//								}
//								String finalSpeed=(String) String.valueOf(speed).subSequence(0,String.valueOf(speed).indexOf(".")+4 );
//								LOG.v(TAG, "finalspeed:"+finalSpeed);
//																														
//								ContentValues values = new ContentValues();
//								long complete = (long) (Data.finishsize
//										- Data.length + (Data.length / 15.0)
//										* (i + 2));
//								rate = (long) (((float) complete / (float) Data.fullsize) * 100);
//
//								values.put(DataDefine.COL_TRANS_COMPLETEDSIZE,
//										rate);
//								values.put(DataDefine.COL_TRANS_SPEED, finalSpeed);
//								StorageManager
//										.GetInstance()
//										.getMsgExtrasList()
//										.update(values,
//												DataDefine.COL_TRANS_FILEID
//														+ "=?",
//												new String[] { mSendBean.PFileid });
//								i++;
//							}
//							if (rate >= 98) {
//								isStop = true;
//								StorageManager
//										.GetInstance()
//										.getMsgExtrasList()
//										.setFileRecvStatus(
//												DataDefine.STATUS_FILE_SEND_SUCCES,
//												mSendBean.PFileid);
//							}
//
//						}
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			}
//		}
//	}
//
//	// 存放更新进度条需要的数据
//	class ProgressbarData {
//
//		public long fullsize;
//		public long time;
//		public int length;
//		public long finishsize;
//
//		ProgressbarData(long time, long finishSize, int length, long fullSize) {
//
//			this.time = time;
//			this.finishsize = finishSize;
//			this.length = length;
//			this.fullsize = fullSize;
//
//		}
//	}

}
