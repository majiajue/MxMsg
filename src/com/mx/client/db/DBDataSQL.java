package com.mx.client.db;

public class DBDataSQL {
	// message database
	  public final static String _id = "_id";
		/**
		 * TABLE message { _id INTEGER PRIMARY KEY AUTOINCREMENT�� M_peerid VARCHAR2(255) NOT
		 * NULL; �Է���peerid M_msg VARCHAR2(255) NOT NULL; ��Ϣ������ M_msg_extra INTEGER NOT
		 * NULL;��Ϣ������id M_direction VARCHAR2(255) NOT NULL; ��Ϣ���յ��Ļ��Ƿ����� M_status INTEGER NOT
		 * NULL ; ��Ϣ�ķ���״̬���ɹ���ʧ�ܻ��ߵȴ����յ� M_msgtime VARCHAR2(255) NOT NULL: ��Ϣ������ʱ�� M_unread
		 * INTEGER NOT NULL; �����Ϣ�Ƿ�δ�� }
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
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_MES_PEERID + " VARCHAR2(255) NOT NULL, " + COL_MES_MSG
				+ " VARCHAR2(255) NOT NULL, " + COL_MES_MSG_EXTRA + " INTEGER NOT NULL, " + COL_MES_DIRECTION + " VARCHAR2(255) NOT NULL, "
				+ COL_MES_STATUS + " INTEGER NOT NULL, " + COL_MES_MSGTIME + " VARCHAR2(255) NOT NULL, " + COL_MES_UNREAD
				+ " INTEGER NOT NULL, " + COL_MES_MSGTYPE + " INTEGER NOT NULL" + ")";
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
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_LOGIN_NUMBER + " VARCHAR2(255) NOT NULL, " + COL_LOGIN_STATUS
				+ " VARCHAR2(255) NOT NULL, " +COL_LOGIN_PASSWORD+ " VARCHAR2(255) NOT NULL, "+ COL_MES_MSGTIME + " VARCHAR2(255) NOT NULL" + ")";
		
		
		public final static String SQL_CREATE_LOGIN_INDEX = "CREATE INDEX IF NOT EXISTS LOGIN ON " + TB_LOGIN + " ( "
				+ COL_LOGIN_NUMBER + "," + COL_LOGIN_PASSWORD + "," + COL_LOGIN_PASSWORD + "," + COL_LOGIN_Time + ")";


}
