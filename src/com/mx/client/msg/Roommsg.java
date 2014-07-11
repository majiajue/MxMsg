package com.mx.client.msg;

import java.io.IOException;
import java.rmi.ConnectIOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.NoSuchPaddingException;

import org.apache.http.client.ClientProtocolException;
import com.mx.clent.vo.AnMessageBean;
import com.mx.clent.vo.AnRoomsBean;
import com.mx.client.cache.SCache;
import com.mx.client.db.DBDataSQL;
import com.mx.client.db.DataDefine;
import com.mx.client.db.GenDao;
import com.mx.client.netty.NettyStatus;
import com.mx.client.webtools.ConnectionUtils;
import com.mx.client.webtools.Cryptor;
import com.mx.client.webtools.CryptorException;
import com.mx.client.webtools.Msg;
import com.mx.client.webtools.RSAEncryptor;
import com.mx.client.webtools.SConfig;

public class Roommsg implements MessagerHandler {
	private HashMap<String, String> mXmlMap;

	public Roommsg(HashMap<String, String> xmlMap) {
		mXmlMap = xmlMap;
	}

	private void updateToRoomSession(String roomid, String subject,
			String owner, String secret) {
		if (!"".equals(roomid) && !"".equals(secret)) {
			AnRoomsBean bean = new AnRoomsBean();
			bean.RRoomid = roomid;
			bean.RAeskey = secret;
			bean.RRoomname = subject;
			bean.ROwnerid = owner;
			bean.RRoometype = "300000";
			java.util.Hashtable<String, Object> table = new java.util.Hashtable<String, Object>();
			table.put(DBDataSQL.COL_PROOM_ROOMID, roomid);
			String a =GenDao.getInstance().getValue(DBDataSQL.TB_ROOMS,
					new String[] { DBDataSQL.COL_PROOM_ROOMID },
					DBDataSQL.COL_PROOM_ROOMID, table);
			if (a != null&&!"".equals(a)) {
				GenDao.getInstance().executeUpdate(DBDataSQL.TB_ROOMS,
						new String[] { DBDataSQL.COL_PROOM_AESKEY },
						new Object[] { bean.RAeskey }, table);
			} else {
				bean = new AnRoomsBean(roomid, owner, subject, "", SConfig.getInstance().getProfile().myPeerBean.PPeerid, secret,
						"-1", "300000", "", "0");
				AnRoomsBean.getInstance().savePeer(roomid, owner, subject,
						bean.RPinyin, "", SConfig.getInstance().getProfile().myPeerBean.PPeerid, secret, "-1", "300000", "", "0");
			}
		}
	}

	@Override
	public boolean process() {
		// TODO Auto-generated method stub
		try {
			boolean NoVoice = false;
			String roomid = mXmlMap.get("id");
			String duid = mXmlMap.get("from");
			java.util.Hashtable<String, Object> table = new java.util.Hashtable<String, Object>();
			table.put(DBDataSQL.COL_PROOM_ROOMID, roomid);
			ResultSet rs = GenDao.getInstance()
					.getResult(
							DBDataSQL.TB_ROOMS,
							new String[] { DBDataSQL.COL_PROOM_ROOMNAME,
									DBDataSQL.COL_PROOM_AESKEY,
									DBDataSQL.COL_PROOM_OWNER }, table);
			String aeskey = "";
			String ownerId = "";
			String name = "";
			if(rs!=null){
				
				try {
					aeskey = rs.getString(DBDataSQL.COL_PROOM_AESKEY.toUpperCase());
					ownerId = rs.getString(DBDataSQL.COL_PROOM_OWNER.toUpperCase());
					name = rs.getString(DBDataSQL.COL_PROOM_ROOMNAME.toUpperCase());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if ("".equals(aeskey) || "".equals(ownerId)||aeskey==null||ownerId==null) {
				Map<String, String> roomInfo = null;
				try {
					roomInfo = ConnectionUtils.getInstance().getRoomInfo(roomid);
					if (roomInfo != null && roomInfo.get("r").equals("room_info")) {
						String subject = roomInfo.get("subject");
						String owner = roomInfo.get("owner");
						String secret = roomInfo.get("secret");
						updateToRoomSession(roomid, subject, owner, secret);
						aeskey = secret;
						ownerId = owner;
						name = subject;
					}
				} catch (Exception e) {
					//errorProcessing();
					e.printStackTrace();
				}
//			if (TextUtils.isEmpty(aeskey)) {
//				// 提醒用户有消息解析不出来
//				errorProcessing();
//			}
				
			}
					String encMsg = mXmlMap.get("msg");
					String msgWithType = null;
					try {
						Cryptor cryptor = new Cryptor(aeskey);
						msgWithType = new String(
								cryptor.decryptBase64String(encMsg));
					} catch (CryptorException e) {
						// 提醒用户有消息解析不出来
						// errorProcessing();
					}
				
					int msgType = Msg.getTypeOfMsg(msgWithType);
					String pureMsg = Msg.getContentOfMsg(msgWithType);
					AnMessageBean messageBean = new AnMessageBean();
					messageBean.PDirection = DataDefine.IN;
					messageBean.PMsgtime = String.valueOf((Long.valueOf(mXmlMap
							.get("time")) + NettyStatus.TiemOffset));
					messageBean.PPeerid = mXmlMap.get("from");
					messageBean.PUnread = false;
					messageBean.PMsg = pureMsg;
			       
					switch (msgType) {
					case Msg.TYPE_IMAGE:
						String[] shit = messageBean.PMsg.split("\\:");
						messageBean.PMsgType = DBDataSQL.MSG_PICTURE;
						String mId = shit[0];
						System.out.println("shit----->" + shit[1]);
						// Image image =
						// Toolkit.getDefaultToolkit().createImage(shit[1].getBytes());
						// ImgErToFileUtil.saveImage(shit[1],"E:\\test.bmp","BMP");

						// System.out.println(image.getHeight(null));
						// BMPFile bmpFile = new BMPFile();
						// bmpFile.saveBitmap("E:\\yourImageName.bmp", image,
						// image.getWidth(null), image.getHeight(null));
						// AnMessageBean.getInstance().saveMessage(messageBean.PPeerid,
						// messageBean.PMsg, "-1", messageBean.PDirection, "0",
						// messageBean.PMsgtime,
						// String.valueOf(messageBean.PUnread),
						// String.valueOf(mMsgType));
						if (mId != null) {
							if (SCache.getInstance().getFileTransferIdentify(mId) != null) {
								SCache.getInstance()
										.removeFileTransferIdentify(mId);
							}
							// FileTransferIdentify taskIdentify = new
							// FileTransferIdentify();
							// taskIdentify.MessageID = messageBean._id;
							// taskIdentify.FileId = mId;
							// SCache.getInstance().putFileTransferIdentify(mId,
							// taskIdentify);
							com.mx.client.webtools.FileTransferIdentify taskIdentify = new com.mx.client.webtools.FileTransferIdentify();
							taskIdentify.MessageID = messageBean._id;
							taskIdentify.FileId = mId;
						}
						break;
					case Msg.TYPE_VOICE:
						// String[] temp = messageBean.PMsg.split("\\|");
						// String voiceFileName = temp[2];
						// messageBean.PMsgType = DataDefine.MSG_VOICE;
						// messageBean = (AnMessageBean)
						// StorageManager.GetInstance().getMessageList().Save(messageBean);
						// if (voiceFileName != null) {
						// if
						// (SCache.getInstance().getFileTransferIdentify(voiceFileName)
						// != null) {
						// SCache.getInstance().removeFileTransferIdentify(voiceFileName);
						// }
						// FileTransferIdentify taskIdentify = new
						// FileTransferIdentify();
						// taskIdentify.MessageID = messageBean._id;
						// taskIdentify.FileId = voiceFileName;
						// SCache.getInstance().putFileTransferIdentify(voiceFileName,
						// taskIdentify);
						// // 是否需要数据库支持
						// }
						break;
					case Msg.TYPE_TXT:
						AnMessageBean.getInstance().saveMessage(
								messageBean.PPeerid, messageBean.PMsg, "-1",
								messageBean.PDirection, "0", messageBean.PMsgtime,
								String.valueOf(messageBean.PUnread),
								String.valueOf(msgType),name,roomid);
						
						// Log.v("netty", "TiemOffset=" + NettyStatus.TiemOffset);
						if (NettyStatus.TiemOffset == 0) {
							AnMessageBean messageBeanError = new AnMessageBean();
							messageBeanError.PDirection = DBDataSQL.IN;
							if (mXmlMap.get("time") != null) {
								messageBeanError.PMsgtime = String
										.valueOf((Long.valueOf(mXmlMap.get("time")) + NettyStatus.TiemOffset));
							} else {
								messageBeanError.PMsgtime = String.valueOf(System
										.currentTimeMillis());
							}
							messageBeanError.PPeerid = mXmlMap.get("from");
							messageBeanError.PUnread = false;
							if (mXmlMap.get("time") != null) {
								messageBeanError.PMsgtime = String.valueOf((Long
										.valueOf(mXmlMap.get("time"))
										+ NettyStatus.TiemOffset + 1000));
							} else {
								messageBeanError.PMsgtime = String.valueOf(System
										.currentTimeMillis());
							}
							messageBeanError.PPeerid = mXmlMap.get("from");
							messageBeanError.PUnread = false;
							messageBeanError.PMsg = "系统提示：和服务器的时间差获取失败了，重新连接试试吧";
							messageBeanError.PStatusId = DBDataSQL.STATUS_CRYPTERROR;
							AnMessageBean.getInstance().saveMessage(
									messageBeanError.PPeerid,
									messageBeanError.PMsg, "-1",
									messageBeanError.PDirection, "0",
									messageBeanError.PMsgtime,
									String.valueOf(messageBeanError.PUnread),
									String.valueOf(msgType));
						}
						break;

					}
					Long time = System.currentTimeMillis();
					SConfig.getInstance().getSub().notify_("来自于群信息"+messageBean.PMsg);
					if ((time - HandlerHelper.lastRingTime) > 5000) {
						NoVoice = false;
					} else {
						NoVoice = true;
					}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return true;
	}

	private String decodeChatUser(String sMsg) throws CryptorException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException {
		byte[] data;
		String sData;

		// data = RSAEncryptor.getInstance().decryptBase64String(sMsg,
		// SConfig.getInstance().getProfile().myPeerBean.PPeerid);
		data = RSAEncryptor.getInstance().myDecryptBase64String(sMsg);
		sData = new String(data);
		System.out.println("sData:" + sData.trim());
		String result = Msg.getContentOfMsg(sData.trim());
		if (result == null) {
			throw new CryptorException("文本解析错误");
		}
		return sData.trim();
	}

}
