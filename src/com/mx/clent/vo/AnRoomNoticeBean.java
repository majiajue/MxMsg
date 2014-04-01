package com.mx.clent.vo;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import com.mx.client.db.DBDataSQL;
import com.mx.client.db.GenDao;

public class AnRoomNoticeBean {
	String[] cloumes = new String[] {DBDataSQL.COL_ROOM_NOTICE_UUID, DBDataSQL.COL_ROOM_NOTICE_TIME,
			DBDataSQL.COL_ROOM_NOTICE_STATUS, DBDataSQL.COL_ROOM_NOTICE_ROOMID, DBDataSQL.COL_ROOM_NOTICE_READ,
			DBDataSQL.COL_ROOM_NOTICE_PEERID, DBDataSQL.COL_ROOM_NOTICE_CONTENT};
	public long _id = -1;
	private final Object[] dbLock = new Object[0];
	public String PPeerid = "";
	public String PRoomid = "";
	public String PContent = "";
	public String PUuid = "";
	public String PTime = "";
	public String PStatus = "-1";
	public String PRead = "-1";
	private static class SingletonHolder {
		public static final AnRoomNoticeBean INSTANCE = new AnRoomNoticeBean();
	}

	public static AnRoomNoticeBean getInstance() {
		return SingletonHolder.INSTANCE;
	}
   
	public AnRoomNoticeBean() {
		super();
	}
	 public AnRoomNoticeBean getUserByRoomID(String roomId){
		 AnRoomNoticeBean bean = new AnRoomNoticeBean();
		 AnRoomNoticeBean peers=null;
	    	Hashtable<String, Object> table = new Hashtable<String, Object>();
	    	table.put(DBDataSQL.COL_ROOM_NOTICE_ROOMID,roomId);
	    	if(GenDao.getInstance().getValue(DBDataSQL.TB_ROOM_NOTICE,cloumes, DBDataSQL.COL_ROOM_NOTICE_ROOMID, table)!=null){
	    		try {
					ResultSet rs = GenDao.getInstance().getResult(DBDataSQL.TB_ROOM_NOTICE,cloumes, table);
					bean.PPeerid = rs.getString(DBDataSQL.COL_ROOM_NOTICE_PEERID);
					bean.PContent = rs.getString(DBDataSQL.COL_ROOM_NOTICE_CONTENT);
					bean.PRead = rs.getString(DBDataSQL.COL_ROOM_NOTICE_READ);
					bean.PRoomid = rs.getString(DBDataSQL.COL_ROOM_NOTICE_ROOMID);
					bean.PStatus = rs.getString(DBDataSQL.COL_ROOM_NOTICE_STATUS);
					bean.PTime = rs.getString(DBDataSQL.COL_ROOM_NOTICE_TIME);
					bean.PUuid = rs.getString(DBDataSQL.COL_ROOM_NOTICE_UUID);
					peers = new AnRoomNoticeBean(bean.PPeerid, bean.PRoomid, bean.PContent, bean.PUuid, bean.PTime, bean.PStatus,bean.PRead);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	return peers;
	    }
	public AnRoomNoticeBean( String pPeerid, String pRoomid,
			String pContent, String pUuid, String pTime, String pStatus,
			String pRead) {
		super();
		
		PPeerid = pPeerid;
		PRoomid = pRoomid;
		PContent = pContent;
		PUuid = pUuid;
		PTime = pTime;
		PStatus = pStatus;
		PRead = pRead;
	}
	
	public void saveNotice( String pPeerid, String pRoomid,
			String pContent, String pUuid, String pTime, String pStatus,
			String pRead) {

		long res;
		if (pPeerid==null)
			return;
		if (!checkparam(pPeerid, pRoomid,
				pContent, pUuid, pTime, pStatus,
				pRead)) {
			//LOG.e("db", "null parameter detected!");
			new Exception().printStackTrace();
			return;
		}
		synchronized (dbLock) {
			Hashtable<String, Object> table = new Hashtable<String, Object>();
			table.put(DBDataSQL.COL_ROOM_NOTICE_ROOMID, pRoomid);
			try {				
				boolean a=GenDao.getInstance().executeUpdate(DBDataSQL.TB_ROOM_NOTICE,new String[]{DBDataSQL.COL_ROOM_NOTICE_ROOMID},new Object[]{pRoomid},table);
				if(a){
					
					GenDao.getInstance().executeUpdate(DBDataSQL.TB_ROOM_NOTICE,new String[]{DBDataSQL.COL_ROOM_NOTICE_PEERID,DBDataSQL.COL_ROOM_NOTICE_ROOMID,DBDataSQL.COL_ROOM_NOTICE_CONTENT,DBDataSQL.COL_ROOM_NOTICE_UUID,DBDataSQL.COL_ROOM_NOTICE_TIME,DBDataSQL.COL_ROOM_NOTICE_STATUS,DBDataSQL.COL_ROOM_NOTICE_READ},new Object[]{pPeerid, pRoomid,
							pContent, pUuid, pTime, pStatus,
							pRead},table);
				}else{
					GenDao.getInstance().executeInsert(DBDataSQL.TB_ROOM_NOTICE,new String[]{DBDataSQL.COL_ROOM_NOTICE_PEERID,DBDataSQL.COL_ROOM_NOTICE_ROOMID,DBDataSQL.COL_ROOM_NOTICE_CONTENT,DBDataSQL.COL_ROOM_NOTICE_UUID,DBDataSQL.COL_ROOM_NOTICE_TIME,DBDataSQL.COL_ROOM_NOTICE_STATUS,DBDataSQL.COL_ROOM_NOTICE_READ},new Object[]{pPeerid, pRoomid,
							pContent, pUuid, pTime, pStatus,
							pRead});
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				
			}
		}
	}
	
	protected boolean checkparam(Object... params) {
		boolean result = true;
		for (Object name : params)
			if (name == null) {
				result = false;
				return result;
			}
		return result;
	}
	
}
