package com.mx.clent.vo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;


import com.mx.client.db.DBDataSQL;
import com.mx.client.db.GenDao;
import com.mx.client.webtools.HanziToPinyin;
import com.mx.client.webtools.HanziToPinyin.Token;

public class AnRoomsBean {
	static String[] columes = new String[] { DBDataSQL.COL_PROOM_ROOMID,
			DBDataSQL.COL_PROOM_OWNER, DBDataSQL.COL_PROOM_ROOMNAME,
			DBDataSQL.COL_PROOM_PINYIN, DBDataSQL.COL_PROOM_UPDTAETIME,
			DBDataSQL.COL_PROOM_RECENTMSG, DBDataSQL.COL_PEER_PUBLIC,
			DBDataSQL.COL_PEER_REMARK, DBDataSQL.COL_PEER_UPDTAETIME,
			DBDataSQL.COL_PROOM_AESKEY, DBDataSQL.COL_PROOM_UNREAD,
			DBDataSQL.COL_PROOM_ROOMTYPE, DBDataSQL.COL_PROOM_ROOMMASKID,
			DBDataSQL.COL_PROOM_ALLOWCHANGE };
	public String RRoomid = "";
	public String ROwnerid = "";
	public String RRoomname = "";
	public String RPinyin = "";
	// public String RAvatarid = "";
	private final Object[] dbLock = new Object[0];
	public String RUpdateTime = "";
	public String PRecentMsg = "";
	public String RAeskey = "";
	public String PUnread = "0";
	public String RRoometype = "-1";
	public String RRoomallowchange = "0";
	public String RRoomMaskid = "";
	public static final AnRoomsBean NULL = new AnRoomsBean();
    
	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		public static final AnRoomsBean INSTANCE = new AnRoomsBean();
	}

	public static AnRoomsBean getInstance() {
		return SingletonHolder.INSTANCE;
	}
	public AnRoomsBean() {

	}

	public AnRoomsBean(String Roomid, String Ownerid, String Roomname,
			String UpdateTime, String Lastcontact, String Aeskey,
			String unread, String Roomtype, String RoomMaskid,
			String Roomallowchange) {
		RRoomid = Roomid;
		ROwnerid = Ownerid;
		RRoomname = Roomname;
		RUpdateTime = UpdateTime;
		PRecentMsg = Lastcontact;
		RAeskey = Aeskey;
		PUnread = unread;
		RRoometype = Roomtype;
		RRoomMaskid = RoomMaskid;
		RRoomallowchange = Roomallowchange;
		ArrayList<Token> tokens = new ArrayList<Token>();
		tokens = HanziToPinyin.getInstance().get(tokens, Roomname);
		if (tokens != null && tokens.isEmpty()) {
			RPinyin = Roomname;
		} else {
			int count = tokens.size();
			for (int i = 0; i < count; i++) {
				RPinyin += tokens.get(i).target;
			}
			RPinyin = RPinyin.toLowerCase();
		}
	}

	protected boolean checkparam(Object... params) {
		boolean result = true;
		for (Object name : params)
			if (name == null) {
				System.out.println("为空的参数名-->"+name);
				result = false;
				return result;
			}
		return result;
	}

	public void savePeer(String Roomid, String Ownerid, String Roomname,
			String RPinyin, String UpdateTime, String Lastcontact,
			String Aeskey, String unread, String Roomtype, String RoomMaskid,
			String Roomallowchange) {

		long res;
		if (Roomid == null)
			return;
		System.out.println(Roomid+Ownerid+Roomname+UpdateTime+Lastcontact
				+Aeskey+unread+Roomtype+RoomMaskid+Roomallowchange);
		if (!checkparam(Roomid, Ownerid, Roomname, UpdateTime, Lastcontact,
				Aeskey, unread, Roomtype, RoomMaskid, Roomallowchange)) {
			// LOG.e("db", "null parameter detected!");
			new Exception().printStackTrace();
			return;
		}
		System.out.println("--------------------kk");
		synchronized (dbLock) {
			Hashtable<String, Object> table = new Hashtable<String, Object>();
            table.put(DBDataSQL.COL_PROOM_ROOMID, Roomid);
			try {
				boolean a = GenDao.getInstance().executeUpdate(
						DBDataSQL.TB_ROOMS,
						new String[] { DBDataSQL.COL_PROOM_ROOMID },
						new Object[] { Roomid }, table);
				if (a) {

					GenDao.getInstance().executeUpdate(
							DBDataSQL.TB_ROOMS,
							new String[] { DBDataSQL.COL_PROOM_ROOMID,
									DBDataSQL.COL_PROOM_OWNER,
									DBDataSQL.COL_PROOM_ROOMNAME,
									DBDataSQL.COL_PROOM_PINYIN,
									DBDataSQL.COL_PROOM_UPDTAETIME,
									DBDataSQL.COL_PROOM_RECENTMSG,
									DBDataSQL.COL_PROOM_AESKEY,
									DBDataSQL.COL_PROOM_UNREAD,
									DBDataSQL.COL_PROOM_ROOMTYPE,
									DBDataSQL.COL_PROOM_ROOMMASKID,
									DBDataSQL.COL_PROOM_ALLOWCHANGE },
							new Object[] { Roomid, Ownerid, Roomname, RPinyin,
									UpdateTime, Lastcontact, Aeskey, unread,
									Roomtype, RoomMaskid, Roomallowchange },
							table);
				} else {
					GenDao.getInstance().executeInsert(
							DBDataSQL.TB_ROOMS,
							new String[] { DBDataSQL.COL_PROOM_ROOMID,
									DBDataSQL.COL_PROOM_OWNER,
									DBDataSQL.COL_PROOM_ROOMNAME,
									DBDataSQL.COL_PROOM_PINYIN,
									DBDataSQL.COL_PROOM_UPDTAETIME,
									DBDataSQL.COL_PROOM_RECENTMSG,
									DBDataSQL.COL_PROOM_AESKEY,
									DBDataSQL.COL_PROOM_UNREAD,
									DBDataSQL.COL_PROOM_ROOMTYPE,
									DBDataSQL.COL_PROOM_ROOMMASKID,
									DBDataSQL.COL_PROOM_ALLOWCHANGE },
							new Object[] { Roomid, Ownerid, Roomname, RPinyin,
									UpdateTime, Lastcontact, Aeskey, unread,
									Roomtype, RoomMaskid, Roomallowchange });

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}
	}

	public static AnRoomsBean CreateByRoomId(String RoomId) throws SQLException {
		AnRoomsBean bean = NULL;
		Hashtable<String, Object> table = new Hashtable<String, Object>();
		table.put(DBDataSQL.COL_PROOM_ROOMID, RoomId);
		if (GenDao.getInstance().getValue(DBDataSQL.TB_ROOMS, columes,
				DBDataSQL.COL_PROOM_ROOMID, table) != null) {
			ResultSet rs = GenDao.getInstance().getResult(DBDataSQL.TB_ROOMS,
					columes, table);
			bean.RRoomid = rs.getString(DBDataSQL.COL_PROOM_ROOMID);
			bean.RRoomname = rs.getString(DBDataSQL.COL_PROOM_ROOMNAME);
			bean.ROwnerid = rs.getString(DBDataSQL.COL_PROOM_OWNER);
			bean.RUpdateTime = rs.getString(DBDataSQL.COL_PROOM_UPDTAETIME);
			bean.PRecentMsg = rs.getString(DBDataSQL.COL_PROOM_RECENTMSG);
			bean.PUnread = rs.getString(DBDataSQL.COL_PROOM_UNREAD);
			bean.RAeskey = rs.getString(DBDataSQL.COL_PROOM_AESKEY);
			bean.RRoomallowchange = rs
					.getString(DBDataSQL.COL_PROOM_ALLOWCHANGE);
			bean.RRoometype = rs.getString(DBDataSQL.COL_PROOM_ROOMTYPE);
			bean.RRoomMaskid = rs.getString(DBDataSQL.COL_PROOM_ROOMMASKID);
			bean = new AnRoomsBean(bean.RRoomid, bean.ROwnerid, bean.RRoomname,
					bean.RUpdateTime, bean.PRecentMsg, bean.RAeskey,
					bean.PUnread, bean.RRoometype, bean.RRoomMaskid,
					bean.RRoomallowchange);
			return bean;
		}

		return NULL;
	}

}
