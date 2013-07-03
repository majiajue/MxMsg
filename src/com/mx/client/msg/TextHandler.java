package com.mx.client.msg;

import java.util.HashMap;

import com.mx.clent.vo.AnMessageBean;
import com.mx.client.db.DBDataSQL;
import com.mx.client.netty.NettyStatus;
import com.mx.client.webtools.CryptorException;
import com.mx.client.webtools.Msg;
import com.mx.client.webtools.RSAEncryptor;
import com.mx.client.webtools.SConfig;




public class TextHandler implements MessagerHandler {

	HashMap<String, String> xmlMap = null;

	public TextHandler(final HashMap<String, String> xmlMap) {
		this.xmlMap = xmlMap;
	}
	/**
	 * 文本消息处理程序
	 * 
	 * @param xmlMap
	 */
	public boolean process() {
		// 在消息数据库中增加一条记录
		boolean NoVoice = false;
		AnMessageBean messageBean = new AnMessageBean();
		messageBean.PDirection = DBDataSQL.IN;
		messageBean.PMsgtime = String.valueOf((Long.valueOf(xmlMap.get("time")) + NettyStatus.TiemOffset));
		messageBean.PPeerid = xmlMap.get("from");
		messageBean.PUnread = false;
		int mMsgType = 0;
		try {
			String sData = decodeChatUser(xmlMap.get("msg"));
			messageBean.PMsg = Msg.getContentOfMsg(sData);
			mMsgType = Msg.getTypeOfMsg(sData);
			switch (mMsgType) {
			case Msg.TYPE_IMAGE:
//				String[] shit = messageBean.PMsg.split("\\:");
//				messageBean.PMsgType = DBDataSQL.MSG_PICTURE;
//				String mId = shit[0];
//				messageBean = (AnMessageBean) AnMessageBean.getInstance().saveMessage(messageBean.PPeerid, messageBean.PMsg, msg_extra, messageBean.PDirection, status, messageBean.PMsgtime,String.valueOf(messageBean.PUnread),String.valueOf(mMsgType));
//				if (mId != null) {
//					if (SCache.getInstance().getFileTransferIdentify(mId) != null) {
//						SCache.getInstance().removeFileTransferIdentify(mId);
//					}
//					FileTransferIdentify taskIdentify = new FileTransferIdentify();
//					taskIdentify.MessageID = messageBean._id;
//					taskIdentify.FileId = mId;
//					SCache.getInstance().putFileTransferIdentify(mId, taskIdentify);
//				}
				break;
			case Msg.TYPE_VOICE:
//				String[] temp = messageBean.PMsg.split("\\|");
//				String voiceFileName = temp[2];
//				messageBean.PMsgType = DataDefine.MSG_VOICE;
//				messageBean = (AnMessageBean) StorageManager.GetInstance().getMessageList().Save(messageBean);
//				if (voiceFileName != null) {
//					if (SCache.getInstance().getFileTransferIdentify(voiceFileName) != null) {
//						SCache.getInstance().removeFileTransferIdentify(voiceFileName);
//					}
//					FileTransferIdentify taskIdentify = new FileTransferIdentify();
//					taskIdentify.MessageID = messageBean._id;
//					taskIdentify.FileId = voiceFileName;
//					SCache.getInstance().putFileTransferIdentify(voiceFileName, taskIdentify);
//					// 是否需要数据库支持
//				}
				break;
			case Msg.TYPE_TXT:
				AnMessageBean.getInstance().saveMessage(messageBean.PPeerid, messageBean.PMsg, "-1", messageBean.PDirection, "0", messageBean.PMsgtime,String.valueOf(messageBean.PUnread),String.valueOf(mMsgType));
				//Log.v("netty", "TiemOffset=" + NettyStatus.TiemOffset);
				if (NettyStatus.TiemOffset == 0) {
					AnMessageBean messageBeanError = new AnMessageBean();
					messageBeanError.PDirection = DBDataSQL.IN;
					if (xmlMap.get("time") != null) {
						messageBeanError.PMsgtime = String
								.valueOf((Long.valueOf(xmlMap.get("time")) + NettyStatus.TiemOffset));
					} else {
						messageBeanError.PMsgtime = String.valueOf(System.currentTimeMillis());
					}
					messageBeanError.PPeerid = xmlMap.get("from");
					messageBeanError.PUnread = false;
					if (xmlMap.get("time") != null) {
						messageBeanError.PMsgtime = String.valueOf((Long.valueOf(xmlMap.get("time"))
								+ NettyStatus.TiemOffset + 1000));
					} else {
						messageBeanError.PMsgtime = String.valueOf(System.currentTimeMillis());
					}
					messageBeanError.PPeerid = xmlMap.get("from");
					messageBeanError.PUnread = false;
					messageBeanError.PMsg = "系统提示：和服务器的时间差获取失败了，重新连接试试吧";
					messageBeanError.PStatusId = DBDataSQL.STATUS_CRYPTERROR;
					AnMessageBean.getInstance().saveMessage(messageBeanError.PPeerid,messageBeanError.PMsg, "-1", messageBeanError.PDirection, "0", messageBeanError.PMsgtime,String.valueOf(messageBeanError.PUnread),String.valueOf(mMsgType));
				}
				break;

			}
			Long time = System.currentTimeMillis();

			if ((time - HandlerHelper.lastRingTime) > 5000) {
				NoVoice = false;
			} else {
				NoVoice = true;
			}
		 //LOG.v(TAG, time - HandlerHelper.lastRingTime + "      " + NoVoice);
		} catch (CryptorException e) {
			messageBean.PMsg = "系统提示：可能由于密钥的更换，导致对方发给您的历史消息无法解析，已丢弃，您需要请对方重发！";
			messageBean.PStatusId = DBDataSQL.STATUS_CRYPTERROR;
			AnMessageBean.getInstance().saveMessage(messageBean.PPeerid, messageBean.PMsg, "-1", messageBean.PDirection, String.valueOf(messageBean.PStatusId), messageBean.PMsgtime,String.valueOf(messageBean.PUnread),String.valueOf(mMsgType));

		} catch (Exception e) {
			messageBean.PMsg = "系统提示：对方发给您的历史消息无法解析，已丢弃，您需要请对方重发！";
			messageBean.PStatusId = DBDataSQL.STATUS_CRYPTERROR;
			AnMessageBean.getInstance().saveMessage(messageBean.PPeerid, messageBean.PMsg, "-1", messageBean.PDirection, String.valueOf(messageBean.PStatusId), messageBean.PMsgtime,String.valueOf(messageBean.PUnread),String.valueOf(mMsgType));
			e.printStackTrace();
		}

		//mTxtmsg.putExtra("peerid", messageBean.PPeerid);
		//mTxtmsg.setAction("org.ancode.MiXun.MsgActivity"); // 通过notification的点击,必须进入会话界面

		//if (!HandlerHelper.isPangolinRunningTask()) {
			//SUtil.getInstance().notification(AncodeFactory.getInstance().getApplicationContext(), "密聊消息",
					//messageBean.PPeerid, mTxtmsg, NoVoice);

		//} else {
		//	if (!messageBean.PPeerid.equals(ChatWindowActivity.mCurrentPeersBean.PPeerid)) {
			//	SUtil.getInstance().notification(AncodeFactory.getInstance().getApplicationContext(), "密聊消息",
		//				messageBean.PPeerid, mTxtmsg, NoVoice);
		//	}

		//}
		return true;

	}

	private String decodeChatUser(String sMsg) throws CryptorException {
		byte[] data;
		String sData;
		data = RSAEncryptor.getInstance().decryptBase64String(sMsg,
				SConfig.getInstance().getProfile().myPeerBean.PPeerid);
		sData = new String(data);
		System.out.println("sData"+sData);
		String result = Msg.getContentOfMsg(sData);
		System.out.println("解析结果==="+result);
		if (result == null) {
			throw new CryptorException("文本解析错误");
		}
		return sData;
	}
	
}
