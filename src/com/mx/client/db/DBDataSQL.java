package com.mx.client.db;

public class DBDataSQL {
	// message database
	  public final static String _id = "_id";
		/**
		 * TABLE message { _id INTEGER PRIMARY KEY AUTOINCREMENT； M_peerid VARCHAR2(255) NOT
		 * NULL; 对方的peerid M_msg VARCHAR2(255) NOT NULL; 消息的内容 M_msg_extra INTEGER NOT
		 * NULL;消息的类型id M_direction VARCHAR2(255) NOT NULL; 消息是收到的还是发出的 M_status INTEGER NOT
		 * NULL ; 消息的发送状态，成功或失败或者等待接收等 M_msgtime VARCHAR2(255) NOT NULL: 消息产生的时间 M_unread
		 * INTEGER NOT NULL; 标记消息是否未读 }
		 */
		public final static String TB_MESSAGE = "tb_message";
		public final static String COL_MES_PEERID = "M_peerid";// 对方的peerid
		public final static String COL_MES_MSG = "M_msg";// 消息的内容
		public final static String COL_MES_MSG_EXTRA = "M_msg_extra";// 消息的额外信息类型id
		public final static String COL_MES_DIRECTION = "M_direction";// 消息是收到的还是发出的
		public final static String COL_MES_STATUS = "M_status";// 消息的发送状态，成功或失败或者等待接收等
		public final static String COL_MES_MSGTIME = "M_msgtime";// 消息产生的时间
		public final static String COL_MES_UNREAD = "M_unread";// 标记消息是否未读
		public final static String COL_MES_MSGTYPE = "M_msgtype";
		
		
      
		public final static String SQL_CREATE_TB_MESSAGE = "CREATE TABLE IF NOT EXISTS " + TB_MESSAGE + " ( " + _id
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_MES_PEERID + " VARCHAR2(255) NOT NULL, " + COL_MES_MSG
				+ " VARCHAR2(255) NOT NULL, " + COL_MES_MSG_EXTRA + " INTEGER NOT NULL, " + COL_MES_DIRECTION + " VARCHAR2(255) NOT NULL, "
				+ COL_MES_STATUS + " INTEGER NOT NULL, " + COL_MES_MSGTIME + " VARCHAR2(255) NOT NULL, " + COL_MES_UNREAD
				+ " INTEGER NOT NULL, " + COL_MES_MSGTYPE + " INTEGER NOT NULL" + ")";
		public final static String SQL_CREATE_MESSAGE_INDEX = "CREATE INDEX IF NOT EXISTS imsg ON " + TB_MESSAGE + " ( "
				+ COL_MES_PEERID + "," + COL_MES_MSG + "," + COL_MES_MSG_EXTRA + "," + COL_MES_DIRECTION + ","
				+ COL_MES_STATUS + "," + COL_MES_MSGTIME + "," + COL_MES_UNREAD + "," + COL_MES_MSGTYPE + ")";
		/**
		 * 记录成功登陆的号码
		 */
		public final static String TB_LOGIN = "tb_login";
		public final static String COL_LOGIN_NUMBER = "M_Number";//自己的ID號
		public final static String COL_LOGIN_STATUS = "M_Status";// 2代表記住密碼和帳號，1代表記住帳號
		public final static String COL_LOGIN_PASSWORD = "M_Password";// 密碼
		public final static String COL_LOGIN_Time = "M_Time";// 密碼
		
		public final static String SQL_CREATE_TB_LOGIN ="CREATE TABLE IF NOT EXISTS " + TB_LOGIN + " ( " + _id
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_LOGIN_NUMBER + " VARCHAR2(255) NOT NULL, " + COL_LOGIN_STATUS
				+ " VARCHAR2(255) NOT NULL, " +COL_LOGIN_PASSWORD+ " VARCHAR2(255) NOT NULL, "+ COL_MES_MSGTIME + " VARCHAR2(255) NOT NULL" + ")";
		
		
		public final static String SQL_CREATE_LOGIN_INDEX = "CREATE INDEX IF NOT EXISTS LOGIN ON " + TB_LOGIN + " ( "
				+ COL_LOGIN_NUMBER + "," + COL_LOGIN_PASSWORD + "," + COL_LOGIN_PASSWORD + "," + COL_LOGIN_Time + ")";


}
