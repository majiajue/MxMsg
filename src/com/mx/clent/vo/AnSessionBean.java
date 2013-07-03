package com.mx.clent.vo;

import java.util.Hashtable;
import com.mx.client.db.DBDataSQL;
import com.mx.client.db.GenDao;

public class AnSessionBean {
	public String PPeerid = "";
	public String PLasttime = "-1";
	public long PRecentmsgId = -1;
	public String PRecentmsg = "";
	public int PRecentmsgTypeId = -1;
	public int PUnread = 0;;
	public String PSessionId = "";
	public String PSalt = "";
	public String PAeskey = "";

	public AnMessageBean mRecentMessageBean = null;

	/**
	 * 
	 */
	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		public static final AnSessionBean INSTANCE = new AnSessionBean();
	}

	public static AnSessionBean getInstance() {
		return SingletonHolder.INSTANCE;
	}
	public AnSessionBean() {

	}

	public AnSessionBean(String Peerid, String Lasttime, long RecentmsgId,
			String Recentmsg, int RecentmsgTypeId, int Unread,
			String SessionId, String Salt, String Aeskey) {
		PPeerid = Peerid;
		PLasttime = Lasttime;
		PRecentmsgId = RecentmsgId;
		PRecentmsg = Recentmsg;
		PRecentmsgTypeId = RecentmsgTypeId;
		PUnread = Unread;
		PSessionId = SessionId;
		PSalt = Salt;
		PAeskey = Aeskey;
		mRecentMessageBean = AnMessageBean.getInstance().getLastMessagesByID(
				PPeerid);
	}

	public void saveSession(String Peerid, String Lasttime, String RecentmsgId,
			String Recentmsg, String RecentmsgTypeId, String Unread,
			String SessionId, String Salt, String Aeskey) {

		String[] column = new String[] { DBDataSQL.COL_SESSION_PEERID,
				DBDataSQL.COL_SESSION_LAST, DBDataSQL.COL_SESSION_RNTID,
				DBDataSQL.COL_SESSION_RNTMSG, DBDataSQL.COL_SESSION_RNTTYPEID,
				DBDataSQL.COL_SESSION_UNREAD, DBDataSQL.COL_SESSION_SESSIONID,
				DBDataSQL.COL_SESSION_SALT, DBDataSQL.COL_SESSION_AESKEY };
		Hashtable<String, Object> table = new Hashtable<String, Object>();
		table.put(DBDataSQL.COL_SESSION_PEERID, Peerid);
		boolean b = GenDao.getInstance().executeUpdate(
				DBDataSQL.TB_SESSION,
				column,
				new Object[] { Peerid, Lasttime, RecentmsgId, Recentmsg,
						RecentmsgTypeId, Unread, SessionId, Salt, Aeskey },
				table);
		if (!b) {
			GenDao.getInstance().executeInsert(
					DBDataSQL.TB_SESSION,
					column,
					new Object[] { Peerid, Lasttime, RecentmsgId, Recentmsg,
							RecentmsgTypeId, Unread, SessionId, Salt, Aeskey });
		}
	}

	public void deleteSession(String Peerid) {
		Hashtable<String, Object> table = new Hashtable<String, Object>();
		table.put(DBDataSQL.COL_SESSION_PEERID, Peerid);
		GenDao.getInstance().executeDelete(DBDataSQL.TB_SESSION, table);
	}

	public static final AnSessionBean NULL = new AnSessionBean();

	public static AnSessionBean CreateByPeerId(String peerid) {
		AnSessionBean node = NULL;
		Hashtable<String, Object> table = new Hashtable<String, Object>();
		table.put(DBDataSQL.COL_SESSION_PEERID, peerid);
		String PPeerid = GenDao.getInstance().getValue(DBDataSQL.TB_SESSION,
				new String[] { DBDataSQL.COL_SESSION_PEERID },
				DBDataSQL.COL_SESSION_PEERID, table);
		String PLasttime = GenDao.getInstance().getValue(DBDataSQL.TB_SESSION,
				new String[] { DBDataSQL.COL_SESSION_LAST },
				DBDataSQL.COL_SESSION_LAST, table);
		String PRecentmsgId = GenDao.getInstance().getValue(
				DBDataSQL.TB_SESSION,
				new String[] { DBDataSQL.COL_SESSION_RNTID },
				DBDataSQL.COL_SESSION_RNTID, table);
		String PRecentmsg = GenDao.getInstance().getValue(DBDataSQL.TB_SESSION,
				new String[] { DBDataSQL.COL_SESSION_RNTMSG },
				DBDataSQL.COL_SESSION_RNTMSG, table);
		String PRecentmsgTypeId = GenDao.getInstance().getValue(
				DBDataSQL.TB_SESSION,
				new String[] { DBDataSQL.COL_SESSION_RNTTYPEID },
				DBDataSQL.COL_SESSION_RNTTYPEID, table);
		String Unread = GenDao.getInstance().getValue(DBDataSQL.TB_SESSION,
				new String[] { DBDataSQL.COL_SESSION_UNREAD },
				DBDataSQL.COL_SESSION_UNREAD, table);
		String PSessionId = GenDao.getInstance().getValue(DBDataSQL.TB_SESSION,
				new String[] { DBDataSQL.COL_SESSION_SESSIONID },
				DBDataSQL.COL_SESSION_SESSIONID, table);
		String PSalt = GenDao.getInstance().getValue(DBDataSQL.TB_SESSION,
				new String[] { DBDataSQL.COL_SESSION_SALT },
				DBDataSQL.COL_SESSION_SALT, table);
		String PAeskey = GenDao.getInstance().getValue(DBDataSQL.TB_SESSION,
				new String[] { DBDataSQL.COL_SESSION_AESKEY },
				DBDataSQL.COL_SESSION_AESKEY, table);
		node = new AnSessionBean(PPeerid, PLasttime,
				Long.parseLong(PRecentmsgId), PRecentmsg,
				Integer.parseInt(PRecentmsgTypeId), Integer.parseInt(Unread),
				PSessionId, PSalt, PAeskey);
		return node;

	}
	
	public boolean isSessionExist(String peerid){
		Hashtable<String, Object> table = new Hashtable<String, Object>();
		table.put(DBDataSQL.COL_SESSION_PEERID , peerid);
		if(GenDao.getInstance().getValue(DBDataSQL.TB_SESSION,
				new String[] { DBDataSQL.COL_SESSION_PEERID },
				DBDataSQL.COL_SESSION_PEERID, table)!=null){
			return true;
		}else{
			return false;	
		}
		
	}
}
