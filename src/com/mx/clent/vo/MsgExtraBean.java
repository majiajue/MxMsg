package com.mx.clent.vo;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Hashtable;
import com.mx.client.db.DBDataSQL;
import com.mx.client.db.GenDao;

public class MsgExtraBean {
	public int _id = -1;
	public String M_typeid = "";
	public boolean M_unread = false;
	public String M_Appendex = "";
	public String PFileid = "";
	public String PFilename = "";
	public String PFileuri = "";
	public String PFull_size ="";
	public String PCompleted_size = "";
	public String PPeerid = "";
	public String PState = "-1";
	public String PFdate = "";
	public byte[] sendblocks = new byte[1];
	public boolean isFirstTransfer = true;
	public String transSpeed = "0";
	public String encryptFilePath = "";
	public String PFilePSK = "";
	public String PFiletype = "";

	public MsgTypeBean mType = null;

	public MsgExtraBean() {
		mType = new MsgTypeBean();
	}
	private static class SingletonHolder {
		public static final MsgExtraBean INSTANCE = new MsgExtraBean();
	}

	public static MsgExtraBean getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public MsgExtraBean(String typeid, boolean unread, String appendex,
			String Fileid, String Filename, String Fileuri, String Full_size,
			String Completed_size, String Peerid, String State, String Fdate,
			boolean isfirstTrans, String transSpeed, String filePSK,
			String fileType, byte[] sendblock) {
		M_typeid = typeid;
		M_unread = unread;
		M_Appendex = appendex;
		Hashtable<String, Object> table = new Hashtable<String, Object>();
		mType = MsgTypeBean.getInstance().Create(table);
		PFileid = Fileid;
		PFilename = Filename;
		PFileuri = Fileuri;
		PFull_size = Full_size;
		PCompleted_size = Completed_size;
		PPeerid = Peerid;
		PState = State;
		PFdate = Fdate;
		sendblocks = sendblock;
		this.isFirstTransfer = isfirstTrans;
		this.transSpeed = transSpeed;
		PFilePSK = filePSK;
		PFiletype = fileType;

	}

	public boolean HandleSave() {
		int unread = M_unread ? DBDataSQL.TRUE : DBDataSQL.FALSE;
		GenDao.getInstance().executeInsert(
				DBDataSQL.TB_MSG_EXTRA,
				new String[] { DBDataSQL.COL_EXTRA_TYPEID,
						DBDataSQL.COL_EXTRA_UNREAD,
						DBDataSQL.COL_EXTRA_APPENDEX,
						DBDataSQL.COL_TRANS_FILEID,
						DBDataSQL.COL_TRANS_FILENAME,
						DBDataSQL.COL_TRANS_FILEURL,
						DBDataSQL.COL_TRANS_FULLSIZE,
						DBDataSQL.COL_TRANS_COMPLETEDSIZE,
						DBDataSQL.COL_TRANS_PEERID, DBDataSQL.COL_TRANS_STATE,
						DBDataSQL.COL_TRANS_FDATE,
						DBDataSQL.COL_TRANS_ENCRYPTFILEPATH,
						DBDataSQL.COL_TRANS_SPEED, DBDataSQL.COL_TRANS_FILEPSK,
						DBDataSQL.COL_TRANS_FILETYPE },
				new Object[] { M_typeid, unread, M_Appendex, PFileid,
						PFilename, PFileuri, PFull_size, PCompleted_size,
						PPeerid, PState, PFdate, encryptFilePath, transSpeed,
						PFilePSK, PFiletype });
		// int i = (int) StorageManager
		// .GetInstance()
		// .getMessageDatabase()
		// .saveMsgExtra(_id, M_typeid, unread, M_Appendex, PFileid, PFilename,
		// PFileuri, PFull_size,
		// PCompleted_size, PPeerid, PState, PFdate, sendblocks,
		// isFirstTransfer, encryptFilePath,
		// transSpeed, PFilePSK, PFiletype);
		// if (i >= 0) {
		// _id = i;
		// }
		return false;
	}
	public static final MsgExtraBean NULL = new MsgExtraBean();
	/**
	 * 获取MsgExtraBeans
	 * @param extraid
	 * @return
	 */
	public MsgExtraBean getExtraBeanById(int extraid) {
		MsgExtraBean bean = MsgExtraBean.NULL;
		Hashtable<String, Object> table = new Hashtable<String, Object>();
		table.put("_id", extraid);
		String typeid=GenDao.getInstance().getValue(DBDataSQL.TB_MSG_EXTRA, new String[]{DBDataSQL.COL_EXTRA_TYPEID}, DBDataSQL.COL_EXTRA_TYPEID, table);
		String unread=GenDao.getInstance().getValue(DBDataSQL.TB_MSG_EXTRA, new String[]{DBDataSQL.COL_EXTRA_UNREAD}, DBDataSQL.COL_EXTRA_UNREAD, table);
		String appendex=GenDao.getInstance().getValue(DBDataSQL.TB_MSG_EXTRA, new String[]{DBDataSQL.COL_EXTRA_APPENDEX}, DBDataSQL.COL_EXTRA_APPENDEX, table);
		String fileid=GenDao.getInstance().getValue(DBDataSQL.TB_MSG_EXTRA, new String[]{DBDataSQL.COL_TRANS_FILEID}, DBDataSQL.COL_TRANS_FILEID, table);
		String filename = GenDao.getInstance().getValue(DBDataSQL.TB_MSG_EXTRA, new String[]{DBDataSQL.COL_TRANS_FILEID}, DBDataSQL.COL_TRANS_FILEID, table);
		String fileuri  =  GenDao.getInstance().getValue(DBDataSQL.TB_MSG_EXTRA, new String[]{DBDataSQL.COL_TRANS_FILENAME}, DBDataSQL.COL_TRANS_FILENAME, table);
		String Peerid = GenDao.getInstance().getValue(DBDataSQL.TB_MSG_EXTRA, new String[]{DBDataSQL.COL_TRANS_PEERID}, DBDataSQL.COL_TRANS_PEERID, table);
		String FullSize =GenDao.getInstance().getValue(DBDataSQL.TB_MSG_EXTRA, new String[]{DBDataSQL.COL_TRANS_FULLSIZE}, DBDataSQL.COL_TRANS_FULLSIZE, table);
		String CompletedSize =GenDao.getInstance().getValue(DBDataSQL.TB_MSG_EXTRA, new String[]{DBDataSQL.COL_TRANS_COMPLETEDSIZE}, DBDataSQL.COL_TRANS_COMPLETEDSIZE, table);
		String State = GenDao.getInstance().getValue(DBDataSQL.TB_MSG_EXTRA, new String[]{DBDataSQL.COL_TRANS_STATE}, DBDataSQL.COL_TRANS_STATE, table);
		String Fdate = GenDao.getInstance().getValue(DBDataSQL.TB_MSG_EXTRA, new String[]{DBDataSQL.COL_TRANS_FDATE}, DBDataSQL.COL_TRANS_FDATE, table);
		Blob transfedindex =GenDao.getInstance().getBlobValue(DBDataSQL.TB_MSG_EXTRA, new String[]{DBDataSQL.COL_TRANS_COMPLETE_INDEXS}, DBDataSQL.COL_TRANS_COMPLETE_INDEXS, table);
		String transSpeed = GenDao.getInstance().getValue(DBDataSQL.TB_MSG_EXTRA, new String[]{DBDataSQL.COL_TRANS_SPEED}, DBDataSQL.COL_TRANS_SPEED, table);
		String FilePSK = GenDao.getInstance().getValue(DBDataSQL.TB_MSG_EXTRA, new String[]{DBDataSQL.COL_TRANS_FILEPSK}, DBDataSQL.COL_TRANS_FILEPSK, table);
		String fileType = GenDao.getInstance().getValue(DBDataSQL.TB_MSG_EXTRA, new String[]{DBDataSQL.COL_TRANS_FILETYPE}, DBDataSQL.COL_TRANS_FILETYPE, table);
		String begin = GenDao.getInstance().getValue(DBDataSQL.TB_MSG_EXTRA, new String[]{DBDataSQL.COL_TRANS_TASK_BEGAN}, DBDataSQL.COL_TRANS_TASK_BEGAN, table);
		boolean istrans = Integer.parseInt(begin) == DBDataSQL.TRUE ? true : false;
		boolean read = Integer.parseInt(unread) == DBDataSQL.TRUE ? true : false;
		try {
			bean = new MsgExtraBean(typeid, read, appendex, fileid, filename, fileuri, FullSize, CompletedSize, Peerid, State, Fdate, istrans, transSpeed,FilePSK, fileType, transfedindex.getBytes(0,new Long(transfedindex.length()).intValue()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}


}
