package com.mx.client.db;

public class DBDataSQL {
	// message database
	  public final static String _id = "ID";
		/**
		 * TABLE message { _id INT PRIMARY KEY AUTOINCREMENT�� M_peerid VARCHAR2(255) NOT
		 * NULL; �Է���peerid M_msg VARCHAR2(255) NOT NULL; ��Ϣ������ M_msg_extra INT NOT
		 * NULL;��Ϣ������id M_direction VARCHAR2(255) NOT NULL; ��Ϣ���յ��Ļ��Ƿ����� M_status INT NOT
		 * NULL ; ��Ϣ�ķ���״̬���ɹ���ʧ�ܻ��ߵȴ����յ� M_msgtime VARCHAR2(255) NOT NULL: ��Ϣ������ʱ�� M_unread
		 * INT NOT NULL; �����Ϣ�Ƿ�δ�� }
		 */
		public final static String TB_MESSAGE = "tb_message";
		public final static String COL_MES_PEERID = "M_peerid";// �Է���peerid
		public final static String COL_MES_MSG = "M_msg";// ��Ϣ������
		public final static String COL_MES_MSG_EXTRA = "M_msg_extra";// ��Ϣ�Ķ�����Ϣ����id
		public final static String COL_MES_DIRECTION = "M_direction";// ��Ϣ���յ��Ļ��Ƿ�����
		public final static String COL_MES_STATUS = "M_status";// ��Ϣ�ķ���״̬���ɹ���ʧ�ܻ��ߵȴ����յ�
		public final static String COL_MES_MSGTIME = "M_msgtime";// ��Ϣ������ʱ��
		public final static String COL_MES_UNREAD = "M_unread";// �����Ϣ�Ƿ�δ��
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
		 * ��¼�ɹ���½�ĺ���
		 */
		public final static String TB_LOGIN = "tb_login";
		public final static String COL_LOGIN_NUMBER = "M_Number";//�Լ���ID̖
		public final static String COL_LOGIN_STATUS = "M_Status";// 2����ӛס�ܴa�͎�̖��1����ӛס��̖
		public final static String COL_LOGIN_PASSWORD = "M_Password";// �ܴa
		public final static String COL_LOGIN_Time = "M_Time";// �ܴa
		
		public final static String SQL_CREATE_TB_LOGIN ="CREATE TABLE IF NOT EXISTS " + TB_LOGIN + " ( " + _id
				+ " INT PRIMARY KEY AUTO_INCREMENT, " + COL_LOGIN_NUMBER + " VARCHAR2(255) NOT NULL, " + COL_LOGIN_STATUS
				+ " VARCHAR2(255) NOT NULL, " +COL_LOGIN_PASSWORD+ " VARCHAR2(255) NOT NULL, "+ COL_LOGIN_Time + " VARCHAR2(255) NOT NULL" + ")";
		
		
		public final static String SQL_CREATE_LOGIN_INDEX = "CREATE INDEX IF NOT EXISTS LOGIN ON " + TB_LOGIN + " ( "
				+ COL_LOGIN_NUMBER + "," + COL_LOGIN_PASSWORD + "," + COL_LOGIN_PASSWORD + "," + COL_LOGIN_Time + ")";
		/**
		 * TABLE peer { _id INTEGER NOT NULL; Peerid TEXT PRIMARY KEY NOT NULL;
		 * �û����û�id����������Ψһ Username TEXT; �û������� Pinyin TEXT NOT NULL; �û�����ȫ����Ϣ�����ڼ���
		 * PHONE TEXT NOT NULL; ϵͳ��ϵ�˵�id Avatarid TEXT; �û���ͷ�� UpdateTime TEXT;
		 * �ϴθ��µ�ʱ�� Lastcontact TEXT:�ϴ���ϵ��ʱ�� Public TEXT NOT NULL;���û��Ĺ�Կ }
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
		 * sqlite��helperĬ������ Key TEXT NOT NULL PRIMARY KEY; ����ѡ������� Value TEXT NOT
		 * NULL; ����ѡ���ֵ }
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
