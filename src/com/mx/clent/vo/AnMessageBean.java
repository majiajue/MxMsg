package com.mx.clent.vo;

import java.util.HashMap;
import java.util.Hashtable;

import org.h2.util.DbDriverActivator;
import com.mx.client.db.DBDataSQL;
import com.mx.client.db.GenDao;

public class AnMessageBean {
	public long _id = -1;
	protected final Object[] dbLock = new Object[0];
	public String PPeerid = "";
	public String PMsg = "";
	public int PMmsgExtraId = -1;
	public String PDirection = "";
	public int PStatusId = 0;
	public String PMsgtime = "";
	public Boolean PUnread = false;
	public AnPeersBean PPeersBean = null;
	public MsgExtraBean PExtra = null;
	public MsgStatusBean PStatu = null;
	public int PMsgType = DBDataSQL.MSG_TEXT;

	// 初始化一些参数
	private void initparams() {
		PStatu = new MsgStatusBean();
		PPeersBean = new AnPeersBean();
		PExtra = new MsgExtraBean();
	}

	public AnMessageBean() {
		initparams();
	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		public static final AnMessageBean INSTANCE = new AnMessageBean();
	}

	public static AnMessageBean getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public AnMessageBean(String peerid, String msg, int msg_extra,
			String direction, int status, String msgtime, Boolean unread,
			int msgType) {
		PPeerid = peerid;
		PMsg = msg;
		PMmsgExtraId = msg_extra;
		PDirection = direction;
		PStatusId = status;
		PMsgtime = msgtime;
		PUnread = unread;

		PMsgType = msgType;
		PPeersBean = AnPeersBean.getInstance().getUserByPeerID(peerid);
		if (PPeersBean == null) {
			// LOG.d("chat", "用户不存在，自动添加到数据库" + peerid);
			PPeersBean = new AnPeersBean();
			PPeersBean.PPeerid = PPeerid;
			PPeersBean.PUsername = PPeerid;
			AnPeersBean.getInstance().savePeer(PPeersBean.PPeerid,
					PPeersBean.PUsername, "", "", "", "", "", "");
		}
		if (PMmsgExtraId >= 0) {
			PExtra = MsgExtraBean.getInstance().getExtraBeanById(msg_extra);
			// PSendBean =
			// StorageManager.GetInstance().getFileSendList().getSendBeanByTaskId(msg_extra);
		} else {
			PExtra = new MsgExtraBean();
		}
		PStatu = new MsgStatusBean();
		// if (PExtra == null) {
		// PExtra = new MsgExtraBean(DBDataSQL.MSG_TEXT, false, "", msgtime,
		// msgtime, msgtime, status, status,
		// msgtime, status, msgtime, unread, status);
		// }
	}

	// 调用notepad提供的方法的参数
	public HashMap<String, String> getHashMap(String title) {
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put(DBDataSQL.COL_RECORD_ALARM, "not use here");
		hashmap.put(DBDataSQL.COL_RECORD_CATALOGID, "1");
		hashmap.put(DBDataSQL.COL_RECORD_CONTENT, PMsg);
		hashmap.put(DBDataSQL.COL_RECORD_RELATEDID, "not use here");
		hashmap.put(DBDataSQL.COL_RECORD_TIME, System.currentTimeMillis() + "");
		hashmap.put(DBDataSQL.COL_RECORD_TITLE, title);
		switch (Integer.valueOf(PMsgType)) {
		case DBDataSQL.MSG_TEXT:
			hashmap.put(DBDataSQL.COL_RECORD_TYPE, DBDataSQL.FILE_TEXT + "");
			break;
		case DBDataSQL.MSG_FILE:
			hashmap.put(DBDataSQL.COL_RECORD_TYPE, DBDataSQL.FILE_NORMAL + "");
			hashmap.put(DBDataSQL.COL_RECORD_FILEPATH, PExtra.PFilename);
			hashmap.put(DBDataSQL.COL_RECORD_FILEPSK, PExtra.PFilePSK);
			hashmap.put(DBDataSQL.COL_RECORD_FILESIZE, PExtra.PFull_size + "");
			break;
		case DBDataSQL.MSG_PICTURE:
			hashmap.put(DBDataSQL.COL_RECORD_TYPE, DBDataSQL.FILE_PIC + "");
			hashmap.put(DBDataSQL.COL_RECORD_FILEPATH, PExtra.PFilename);
			hashmap.put(DBDataSQL.COL_RECORD_FILEPSK, PExtra.PFilePSK);
			hashmap.put(DBDataSQL.COL_RECORD_FILESIZE, PExtra.PFull_size + "");
			break;
		case DBDataSQL.MSG_VOICE:
			hashmap.put(DBDataSQL.COL_RECORD_TYPE, DBDataSQL.FILE_VOICE + "");
			hashmap.put(DBDataSQL.COL_RECORD_FILEPATH, PExtra.PFilename);
			hashmap.put(DBDataSQL.COL_RECORD_FILEPSK, PExtra.PFilePSK);
			hashmap.put(DBDataSQL.COL_RECORD_FILESIZE, PExtra.PFull_size + "");
			break;

		}

		return hashmap;
	}

	public boolean HandleSave() {
		// 连同消息和消息附加信息一起保存在数据库中
		int unread = PUnread ? DBDataSQL.TRUE : DBDataSQL.FALSE;
		if ((PExtra != null)) {
			PExtra.HandleSave();
			PMmsgExtraId = PExtra._id;
		}

		this._id = saveMessage(PPeerid, PMsg, String.valueOf(PMmsgExtraId),
				PDirection, String.valueOf(PStatusId), PMsgtime,
				String.valueOf(unread), String.valueOf(PMsgType));
		// LOG.v("wjy", "PMsgType========" + PMsgType);
		// LOG.v("wjy", "PDirection==========" + PDirection);
		// 保存消息的同时保存session信息
		AnSessionBean bean;
		if (AnSessionBean.getInstance().isSessionExist(PPeerid)) {
			bean = AnSessionBean.getInstance().CreateByPeerId(PPeerid);
		} else {
			bean = new AnSessionBean();
		}
		bean.PUnread += 1;
		bean.PLasttime = PMsgtime;
		bean.PRecentmsgId = (int) _id;
		// bean.PRecentmsg = PMsg;
		switch (PMsgType) {
		case DBDataSQL.MSG_TEXT:
			bean.PRecentmsg = PMsg;
			break;
		case DBDataSQL.MSG_PICTURE:
			if (PDirection.equals("out")) {
				// LOG.v("wjy", "这是你发送的");
				bean.PRecentmsg = "[你发送了一张图片]";
			} else if (PDirection.equals("in")) {
				bean.PRecentmsg = "[你收到了一张图片]";
			} else {
				bean.PRecentmsg = "";
			}
			break;
		case DBDataSQL.MSG_VOICE:
			if (PDirection.equalsIgnoreCase("in")) {
				bean.PRecentmsg = "[你收到了一条语音消息]";
			} else if (PDirection.equalsIgnoreCase("out")) {
				bean.PRecentmsg = "[你发送了一条语音消息]";
			} else {
				bean.PRecentmsg = "";
			}
			break;
		case DBDataSQL.MSG_FILE:
			if (PDirection.equalsIgnoreCase("in")) {
				bean.PRecentmsg = "[你收到了一个文件]";
			} else if (PDirection.equalsIgnoreCase("out")) {
				bean.PRecentmsg = "[你发送了一个文件]";
			} else {
				bean.PRecentmsg = "";
			}
			break;
		}

		bean.PPeerid = PPeerid;
		// LOG.d("db", "毛毛球在这而" + bean.PUnread);
		AnSessionBean.getInstance().saveSession(bean.PPeerid, bean.PLasttime,
				String.valueOf(bean.PRecentmsgId), bean.PRecentmsg,
				String.valueOf(bean.PRecentmsgTypeId),
				String.valueOf(bean.PUnread), bean.PSessionId, bean.PSalt,
				bean.PAeskey);

		// 然后试图保存联系人信息 若不存在则保存联系人
		if (!AnPeersBean.getInstance().isPeerExist(PPeerid)) {
		//	LOG.d("chat", "用户不存在，自动添加到数据库" + PPeerid);
			PPeersBean = new AnPeersBean();
			PPeersBean.PPeerid = PPeerid;
			PPeersBean.PUsername = PPeerid;
			AnPeersBean.getInstance().savePeer(PPeerid,PPeerid, "", "", "", "", "", "");
			//BackupContacts backup = new BackupContacts();
			//backup.start();
		}
		return true;
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

	public AnMessageBean getLastMessagesByID(final String peerid) {
		AnMessageBean bean = null;
		Hashtable<String, Object> table = new Hashtable<String, Object>();
		table.put(DBDataSQL.COL_MES_PEERID, peerid);
		bean.PDirection = GenDao.getInstance().getOrderByValue(
				DBDataSQL.TB_MESSAGE,
				new String[] { DBDataSQL.COL_MES_DIRECTION },
				DBDataSQL.COL_MES_DIRECTION, table,
				" ORDER BY " + DBDataSQL.COL_MES_MSGTIME + " desc");
		bean.PMmsgExtraId = Integer.parseInt(GenDao.getInstance()
				.getOrderByValue(DBDataSQL.TB_MESSAGE,
						new String[] { DBDataSQL.COL_MES_MSG_EXTRA },
						DBDataSQL.COL_MES_MSG_EXTRA, table,
						" ORDER BY " + DBDataSQL.COL_MES_MSGTIME + " desc"));
		bean.PMsg = GenDao.getInstance().getOrderByValue(DBDataSQL.TB_MESSAGE,
				new String[] { DBDataSQL.COL_MES_MSG }, DBDataSQL.COL_MES_MSG,
				table, " ORDER BY " + DBDataSQL.COL_MES_MSGTIME + " desc");
		bean.PMsgtime = GenDao.getInstance().getOrderByValue(
				DBDataSQL.TB_MESSAGE,
				new String[] { DBDataSQL.COL_MES_MSGTIME },
				DBDataSQL.COL_MES_MSGTIME, table,
				" ORDER BY " + DBDataSQL.COL_MES_MSGTIME + " desc");
		bean.PPeerid = GenDao.getInstance().getOrderByValue(
				DBDataSQL.TB_MESSAGE,
				new String[] { DBDataSQL.COL_MES_PEERID },
				DBDataSQL.COL_MES_PEERID, table,
				" ORDER BY " + DBDataSQL.COL_MES_MSGTIME + " desc");
		bean.PMsgType = Integer.parseInt(GenDao.getInstance().getOrderByValue(
				DBDataSQL.TB_MESSAGE,
				new String[] { DBDataSQL.COL_MES_MSGTYPE },
				DBDataSQL.COL_MES_MSGTYPE, table,
				" ORDER BY " + DBDataSQL.COL_MES_MSGTIME + " desc"));
		bean.PStatusId = Integer.parseInt(GenDao.getInstance().getOrderByValue(
				DBDataSQL.TB_MESSAGE,
				new String[] { DBDataSQL.COL_MES_STATUS },
				DBDataSQL.COL_MES_STATUS, table,
				" ORDER BY " + DBDataSQL.COL_MES_MSGTIME + " desc"));
		return bean;
	}

	public int saveMessage(String peerid, String msg, String msg_extra,
			String direction, String status, String msgtime, String unread,
			String msgType) {
		String[] columns = new String[] { DBDataSQL.COL_MES_PEERID,
				DBDataSQL.COL_MES_DIRECTION, DBDataSQL.COL_MES_MSG,
				DBDataSQL.COL_MES_MSG_EXTRA, DBDataSQL.COL_MES_MSGTIME,
				DBDataSQL.COL_MES_MSGTYPE, DBDataSQL.COL_MES_STATUS,
				DBDataSQL.COL_MES_UNREAD };

		Hashtable<String, Object> table = new Hashtable<String, Object>();
		table.put(DBDataSQL.COL_MES_PEERID, peerid);
//		boolean b = GenDao.getInstance().executeUpdate(
//				DBDataSQL.TB_MESSAGE,
//				columns,
//				new Object[] { peerid, direction, msg, msg_extra, msgtime,
//						msgType, status, unread }, null);
		
		int a = GenDao.getInstance().executeInsertRId(
				DBDataSQL.TB_MESSAGE,
				columns,
				new Object[] { peerid, direction, msg, msg_extra, msgtime,
						msgType, status, unread });
	   return a;

	}
}
