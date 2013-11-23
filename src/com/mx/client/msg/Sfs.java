package com.mx.client.msg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.mx.clent.vo.MsgExtraBean;
import com.mx.client.cache.SCache;
import com.mx.client.db.DataDefine;
import com.mx.client.webtools.AncFile;
import com.mx.client.webtools.CryptorException;
import com.mx.client.webtools.FileTransferIdentify;
import com.mx.client.webtools.RSAEncryptor;
import com.mx.client.webtools.SConfig;

public class Sfs implements MessagerHandler {
	HashMap<String, String> xmlMap = null;
	private static final int HttpBufferSize = 4096;
	private final byte[] mBuffer = new byte[HttpBufferSize];
	// private final static HashMap<String, MsgExtraBean> mFileRecvQue = new
	// HashMap<String, MsgExtraBean>();
	// private final Map<String, FileTask> mFileTaskMaps = new HashMap<String,
	// FileTask>();
	// private static FileTaskManager mFileTaskManager = new FileTaskManager();
	// Intent mTxtmsg;
	// Intent cloudIntent;
	private String mLastModified;

	public String getLastModified() {
		return mLastModified;
	}

	@Override
	public boolean process() {
		// TODO Auto-generated method stub
		fsHandle(xmlMap);
		return true;
	}

	public Sfs(HashMap<String, String> xmlMap) {
		this.xmlMap = xmlMap;
	}

	private void fsHandle(HashMap<String, String> xmlMap) {
		String sendtype = xmlMap.get("sendtype");
		if (sendtype != null) {
			String peerid = xmlMap.get("from");
			// AnCloudFileBean cloudbean=new AnCloudFileBean();
			String a1 = xmlMap.get("key");
			String a2 = new String(Base64.decodeBase64(a1.getBytes()));
			String a3 = decodeAeskey(a2);
			// cloudbean.decryptkey=a3;
			// cloudbean.cometime=String.valueOf(AncodeFactory.getInstance().now());
			String filename = xmlMap.get("name");
			boolean result = org.apache.commons.codec.binary.Base64
					.isArrayByteBase64(filename.getBytes());
			if (result) {
				filename = new String(Base64.decodeBase64(filename.getBytes()));
			}
			// cloudbean.filename=filename;
			// cloudbean.filelength=Long.valueOf(xmlMap.get("size"));
			// cloudbean.filetype=xmlMap.get("type");
			// cloudbean.fileid=xmlMap.get("tid");
			// AnCloudFileStatusBean statusbean=new AnCloudFileStatusBean();
			// statusbean.linkid=cloudbean.fileid;
			// statusbean.isread=false;
			// statusbean.owner=peerid;
			// StorageManager.GetInstance().getCloudStorageList().Save(cloudbean);
			// StorageManager.GetInstance().getCloudStatusList().Save(statusbean);

			// mTxtmsg.putExtra("peerid", peerid);
			// SUtil.getInstance().notification(AncodeFactory.getInstance().getApplicationContext(),
			// "云空间收到文件",
			// "来自"+peerid, mTxtmsg,false);
			return;
		}
		boolean NoVoice = false;
		boolean isBase64 = false;
		String url = xmlMap.get("pp") + "://" + xmlMap.get("u") + "/";
		System.out.println("url------?" + url);
		// LOG.v("web", "get sendfile from server. " + xmlMap.get("from") +
		// " url: " + url);
		String protocol = "http://" + SConfig.getInstance().getDownLdHost()
				+ ":" + SConfig.getInstance().getDownLdPort() + "/";

		String filename = xmlMap.get("n");
		String PeerNumber = xmlMap.get("from");
		String FileTaskId = xmlMap.get("tid");
		String FileType = xmlMap.get("t");
		String FilePSK = xmlMap.get("p");
		String FileSize = xmlMap.get("l");
		// 测试
		String[] temp = FileType.split("\\.");
		String mId = null;
		if (temp.length >= 2)
			mId = temp[1];

		try {
			isBase64 = Base64.isArrayByteBase64(filename.getBytes());
		} catch (Exception e) {
		}
		if (!"".equals(filename) && !"".equals(FileTaskId) && isBase64) {
			try {

				filename = new String(Base64.decodeBase64(filename.getBytes()));
				System.out.println("filename------?" + filename);

			} catch (Exception e) {
				// LOG.w("pgService",
				// "Might older version protocol by encoding filename.");
			}
			try {
				if (Base64.isArrayByteBase64(FilePSK.getBytes()))
					FilePSK = new String(
							Base64.decodeBase64(FilePSK.getBytes()));
			} catch (Exception e) {
				// LOG.w("pgService", "Might older version protocol.");
			}
			if (FileType != null && FileType.startsWith("image")) {
				// 组装task直接进行下载
				// FileReceiveTask fileRecvTask = new FileReceiveTask(false);
				// fileRecvTask.setPreSharedKey(FilePSK);

				String imageFilePath = "E:\\" + "/ImageFiles/";
				String savepath = imageFilePath + filename;
				// 更新数据库中文件保存的位置
				FileTransferIdentify taskIdentify = SCache.getInstance()
						.getFileTransferIdentify(mId);
				long _id = 0;
				if (taskIdentify != null) {
					_id = taskIdentify.MessageID;
				}
				MsgExtraBean taskBean = new MsgExtraBean();
				taskBean.PFilename = savepath;
				taskBean.PFileid = FileTaskId;
				taskBean.PCompleted_size = 0l + "";
				taskBean.PFileuri = url;
				taskBean.PPeerid = PeerNumber;
				taskBean.PFilePSK = FilePSK;
				taskBean.PFull_size = FileSize;
				taskBean.PFiletype = "image";
				taskBean.PState = DataDefine.STATUS_PICTURE_RECV_READY + "";
				// PFdate 是数据库没用的字段，现在暂时用来保存图片文件解密后的路径
				taskBean.PFdate = imageFilePath;
				// taskBean = (MsgExtraBean)
				// StorageManager.GetInstance().getMsgExtrasList().Save(taskBean);

				// StorageManager.GetInstance().getMessageList()
				// .updateMessageByMid(PeerNumber, _id + "", taskBean._id + "");

				// fileRecvTask.setFileSavePath(imageFilePath);
				// fileRecvTask.setFileId(FileTaskId);
				// fileRecvTask.setFileType("image");
				// mFileTaskManager.startFileReceiveTask(fileRecvTask, false);
				try {
					System.out.println("uri---->" + protocol + "newrfs/"
							+ SConfig.getInstance().getSessionKey() + "/"
							+ FileTaskId + "/call.xml");
					URL uri = new URL(protocol + "newrfs/"
							+ SConfig.getInstance().getSessionKey() + "/"
							+ FileTaskId + "/call.xml");

					HttpURLConnection con = null;
					long handled = 0;
					long length = 0;
					long length_all = 0;
					con = (HttpURLConnection) uri.openConnection();
					con.setDoInput(true);

					con.setDoOutput(false);
					con.setUseCaches(false);
					con.setRequestMethod("GET");
					con.setRequestProperty("Connection", "Close");
					con.setRequestProperty("Charset", "UTF-8");
					long completed = Long.valueOf(xmlMap.get("l"));
					// con.setRequestProperty("Range", "bytes= " + (completed) +
					// " -");
					con.setConnectTimeout(15 * 1000);
					con.setReadTimeout(3 * 60 * 1000);
					con.connect();
					for (int i = 0;; i++) {
						String headerName = con.getHeaderFieldKey(i);
						String headerValue = con.getHeaderField(i);

						if (headerName == null && headerValue == null) {
							// No more headers
							break;
						}
						if (headerName == null)
							continue;

						if (headerName.equalsIgnoreCase("Last-modified")) {
							if (mLastModified == null)
								mLastModified = headerValue;
							else
								mLastModified += headerValue;
						} else if (headerName.equalsIgnoreCase("Etag")) {
							if (mLastModified == null)
								mLastModified = headerValue;
							else
								mLastModified += headerValue;
						}
					}
					 java.io.File file = new java.io.File("E:\\"+filename);
					 RandomAccessFile randomAccessFile = new
					 RandomAccessFile(file,
					 "rwd");
					 randomAccessFile.seek(completed);
					 InputStream is = con.getInputStream();
					 int nRead = -1;
					 while ((nRead = is.read(mBuffer)) != -1) {
					 if (length == 0) {
					 length = con.getContentLength() + completed;
					 if (length <= 0)
					 break;
					 }
					 randomAccessFile.write(mBuffer, 0, nRead);
					 completed += nRead;
					 // 设置范围，格式为Range：bytes x-y;
					 }
//					DataInputStream in = new DataInputStream(
//							con.getInputStream());
//
//					DataOutputStream out = new DataOutputStream(
//							new FileOutputStream("E:\\" + filename));
//
//					byte[] buffer = new byte[4096];
//
//					int count = 0;
//
//					while ((count = in.read(buffer)) > 0) {
//
//						out.write(buffer, 0, count);
//
//					}
					AncFile.getInstance().decrypt(file,
							(String)filename.subSequence(0, taskBean.PFilename.lastIndexOf('/')));
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private String decodeAeskey(String sMsg) {
		byte[] data = null;
		String sData;
		try {
			data = RSAEncryptor.getInstance().decryptBase64String(sMsg,
					SConfig.getInstance().getProfile().myPeerBean.PPeerid);
		} catch (CryptorException e) {
			e.printStackTrace();
		}
		sData = new String(data);
		return sData;
	}

}
