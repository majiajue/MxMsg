package com.mx.client.db;

public class DBDataSQL {
	// message database
	  public final static String _id = "ID";
		/**
		 * TABLE message { _id INT PRIMARY KEY AUTOINCREMENT； M_peerid VARCHAR2(255) NOT
		 * NULL; 对方的peerid M_msg VARCHAR2(255) NOT NULL; 消息的内容 M_msg_extra INT NOT
		 * NULL;消息的类型id M_direction VARCHAR2(255) NOT NULL; 消息是收到的还是发出的 M_status INT NOT
		 * NULL ; 消息的发送状态，成功或失败或者等待接收等 M_msgtime VARCHAR2(255) NOT NULL: 消息产生的时间 M_unread
		 * INT NOT NULL; 标记消息是否未读 }
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
				+ " INT PRIMARY KEY AUTO_INCREMENT, " + COL_MES_PEERID + " VARCHAR2(255) NOT NULL, " + COL_MES_MSG
				+ " VARCHAR2(255) NOT NULL, " + COL_MES_MSG_EXTRA + " INT NOT NULL, " + COL_MES_DIRECTION + " VARCHAR2(255) NOT NULL, "
				+ COL_MES_STATUS + " INT NOT NULL, " + COL_MES_MSGTIME + " VARCHAR2(255) NOT NULL, " + COL_MES_UNREAD
				+ " INT NOT NULL, " + COL_MES_MSGTYPE + " INT NOT NULL" + ")";
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
				+ " INT PRIMARY KEY AUTO_INCREMENT, " + COL_LOGIN_NUMBER + " VARCHAR2(255) NOT NULL, " + COL_LOGIN_STATUS
				+ " VARCHAR2(255) NOT NULL, " +COL_LOGIN_PASSWORD+ " VARCHAR2(255) NOT NULL, "+ COL_LOGIN_Time + " VARCHAR2(255) NOT NULL" + ")";
		
		
		public final static String SQL_CREATE_LOGIN_INDEX = "CREATE INDEX IF NOT EXISTS LOGIN ON " + TB_LOGIN + " ( "
				+ COL_LOGIN_NUMBER + "," + COL_LOGIN_PASSWORD + "," + COL_LOGIN_PASSWORD + "," + COL_LOGIN_Time + ")";
		/**
		 * TABLE peer { _id INTEGER NOT NULL; Peerid TEXT PRIMARY KEY NOT NULL;
		 * 用户的用户id，是主键且唯一 Username TEXT; 用户的名称 Pinyin TEXT NOT NULL; 用户名的全称信息，用于检索
		 * PHONE TEXT NOT NULL; 系统联系人的id Avatarid TEXT; 用户的头像 UpdateTime TEXT;
		 * 上次更新的时间 Lastcontact TEXT:上次联系的时间 Public TEXT NOT NULL;该用户的公钥 }
		 */
		public final static String TB_PEERS = "peers";
		public final static String COL_PEER_PEERID = "Peerid";
		public final static String COL_PEER_USERNAME = "Username";
		public final static String COL_PEER_REMARK = "Remark";
		public final static String COL_PEER_PINYIN = "Pinyin";
		public final static String COL_PEER_PHONE = "phone";
		public final static String COL_PEER_AVATAR = "Avatarid";
		public final static String COL_PEER_UPDTAETIME = "UpdateTime";
		public final static String COL_PEER_LASTCONTACT = "Lastcontact";
		public final static String COL_PEER_PUBLIC = "Public";
		public final static String COL_PEER_FROMPEERID="FriendId";

		public final static String SQL_CREATE_TB_PEERS = "CREATE TABLE IF NOT EXISTS " + TB_PEERS + " (" + _id
				+ "  INT PRIMARY KEY AUTO_INCREMENT, " + COL_PEER_PEERID + " VARCHAR2(4000) NOT NULL, " + COL_PEER_USERNAME
				+ " VARCHAR2(4000), " + COL_PEER_REMARK + " VARCHAR2(4000), " + COL_PEER_PINYIN + " VARCHAR2(4000) ,  " + COL_PEER_PHONE
				+ " VARCHAR2(4000) , " + COL_PEER_AVATAR + " VARCHAR2(4000), " + COL_PEER_UPDTAETIME + " VARCHAR2(4000), " + COL_PEER_LASTCONTACT
				+ " VARCHAR2(4000) , " + COL_PEER_PUBLIC + " VARCHAR2(4000) ," +COL_PEER_FROMPEERID + " VARCHAR2(4000)" +")";
		public final static String SQL_CREATE_PEERS_INDEX = "CREATE INDEX IF NOT EXISTS imsg ON " + TB_PEERS + " ("
				+ COL_PEER_PEERID + "," + COL_PEER_USERNAME + "," + COL_PEER_REMARK + "," + COL_PEER_PINYIN + ","
				+ COL_PEER_PHONE + "," + COL_PEER_AVATAR + "," + COL_PEER_UPDTAETIME + "," + COL_PEER_LASTCONTACT + ","
				+ COL_PEER_PUBLIC + ")";
		// preference database
		/**
		 * Table preference{ _id INTEGER PRIMARY KEY AUTOINCREMENT;
		 * sqlite的helper默认主键 Key TEXT NOT NULL PRIMARY KEY; 配置选项的名称 Value TEXT NOT
		 * NULL; 配置选项的值 }
		 */
		public final static String TB_PREFERENCE = "tb_preference";
		public final static String COL_PRE_KEY = "key";
		public final static String COL_PRE_VALUE = "value";
		public final static String SQL_CREATE_PREFERENCE = "CREATE TABLE IF NOT EXISTS " + TB_PREFERENCE + " ( " + _id
				+ " INT PRIMARY KEY AUTO_INCREMENT, " + COL_PRE_KEY + " VARCHAR2(4000) NOT NULL ," + COL_PRE_VALUE
				+ " VARCHAR2(4000) NOT NULL , UNIQUE( " + COL_PRE_KEY + "," + COL_PRE_VALUE + " ))";
		public final static String SQL_CREATE_PREFERENCE_INDEX = "CREATE INDEX IF NOT EXISTS  imsg ON " + TB_PREFERENCE
				+ " (" + COL_PRE_KEY + "," + COL_PRE_VALUE + ")";



}
