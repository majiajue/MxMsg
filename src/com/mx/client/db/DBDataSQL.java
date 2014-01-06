package com.mx.client.db;

public class DBDataSQL {
	// message database
	   public final static int TRUE = 1;
	   public final static int FALSE = 0;
	   public final static String _id = "ID";	
	   public final static String IN = "in";
		public final static String OUT = "out";
	   // 几种文件类型
		public final static int MSG_TEXT = 100000;
		public final static int MSG_FILE = MSG_TEXT + 1;
		public final static int MSG_VOICE = MSG_TEXT + 2;
		public final static int MSG_CALL = MSG_TEXT + 3;
		public final static int MSG_PICTURE = MSG_TEXT + 4;
	   
		/**
		 * TABLE message { _id INT PRIMARY KEY AUTOINCREMENT�� M_peerid VARCHAR2(255) NOT
		 * NULL; �Է���peerid M_msg VARCHAR2(255) NOT NULL; ��Ϣ������ M_msg_extra INT NOT
		 * NULL;��Ϣ������id M_direction VARCHAR2(255) NOT NULL; ��Ϣ���յ��Ļ��Ƿ����� M_status INT NOT
		 * NULL ; ��Ϣ�ķ���״̬���ɹ���ʧ�ܻ��ߵȴ���յ� M_msgtime VARCHAR2(255) NOT NULL: ��Ϣ�����ʱ�� M_unread
		 * INT NOT NULL; �����Ϣ�Ƿ�δ�� }
		 */
		public final static String TB_MESSAGE = "tb_message";
		public final static String COL_MES_PEERID = "M_peerid";// �Է���peerid
		public final static String COL_MES_MSG = "M_msg";// ��Ϣ������
		public final static String COL_MES_MSG_EXTRA = "M_msg_extra";// ��Ϣ�Ķ�����Ϣ����id
		public final static String COL_MES_DIRECTION = "M_direction";// ��Ϣ���յ��Ļ��Ƿ�����
		public final static String COL_MES_STATUS = "M_status";// ��Ϣ�ķ���״̬���ɹ���ʧ�ܻ��ߵȴ���յ�
		public final static String COL_MES_MSGTIME = "M_msgtime";// ��Ϣ�����ʱ��
		public final static String COL_MES_UNREAD = "M_unread";// �����Ϣ�Ƿ�δ��
		public final static String COL_MES_MSGTYPE = "M_msgtype";
		public final static String COL_MES_GROUP = "M_Group";
		
      
		public final static String SQL_CREATE_TB_MESSAGE = "CREATE TABLE IF NOT EXISTS " + TB_MESSAGE + " ( " + _id
				+ " INT PRIMARY KEY AUTO_INCREMENT, " + COL_MES_PEERID + " VARCHAR2(255) NOT NULL, " + COL_MES_MSG
				+ " VARCHAR2(255) NOT NULL, " + COL_MES_MSG_EXTRA + " VARCHAR2(255) NOT NULL, " + COL_MES_DIRECTION + " VARCHAR2(255) NOT NULL, "
				+ COL_MES_STATUS + " VARCHAR2(255) NOT NULL, " + COL_MES_MSGTIME + " VARCHAR2(255) NOT NULL, " + COL_MES_UNREAD
				+ " VARCHAR2(255) NOT NULL, " + COL_MES_MSGTYPE + " VARCHAR2(255) NOT NULL, " +COL_MES_GROUP + " VARCHAR2(255)"+ ")";
		public final static String SQL_CREATE_MESSAGE_INDEX = "CREATE INDEX IF NOT EXISTS imsg ON " + TB_MESSAGE + " ( "
				+ COL_MES_PEERID + "," + COL_MES_MSG + "," + COL_MES_MSG_EXTRA + "," + COL_MES_DIRECTION + ","
				+ COL_MES_STATUS + "," + COL_MES_MSGTIME + "," + COL_MES_UNREAD + "," + COL_MES_MSGTYPE +"," + COL_MES_GROUP + ")";
		/**
		 * ��¼�ɹ���½�ĺ���
		 */
		public final static String TB_LOGIN = "tb_login";
		public final static String COL_LOGIN_NUMBER = "M_Number";//�Լ���ID̖
		public final static String COL_LOGIN_STATUS = "M_Status";// 2���ӛס�ܴa�͎�̖��1���ӛס��̖
		public final static String COL_LOGIN_PASSWORD = "M_Password";// �ܴa
		public final static String COL_LOGIN_Time = "M_Time";// �ܴa
		
		public final static String SQL_CREATE_TB_LOGIN ="CREATE TABLE IF NOT EXISTS " + TB_LOGIN + " ( " + _id
				+ " INT PRIMARY KEY AUTO_INCREMENT, " + COL_LOGIN_NUMBER + " VARCHAR2(255) NOT NULL, " + COL_LOGIN_STATUS
				+ " VARCHAR2(255) NOT NULL, " +COL_LOGIN_PASSWORD+ " VARCHAR2(255) NOT NULL, "+ COL_LOGIN_Time + " VARCHAR2(255) NOT NULL" + ")";
		
		
		public final static String SQL_CREATE_LOGIN_INDEX = "CREATE INDEX IF NOT EXISTS LOGIN ON " + TB_LOGIN + " ( "
				+ COL_LOGIN_NUMBER + "," + COL_LOGIN_PASSWORD + "," + COL_LOGIN_PASSWORD + "," + COL_LOGIN_Time + ")";
		/**
		 * TABLE peer { _id INT NOT NULL; Peerid TEXT PRIMARY KEY NOT NULL;
		 * �û����û�id����������Ψһ Username TEXT; �û������ Pinyin TEXT NOT NULL; �û����ȫ����Ϣ�����ڼ���
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
		 * Table preference{ _id INT PRIMARY KEY AUTOINCREMENT;
		 * sqlite��helperĬ������ Key TEXT NOT NULL PRIMARY KEY; ����ѡ������ Value TEXT NOT
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

		/**
		 * TBLE msg_type{ 消息类型的定义表 _id INT PRIMARY KEY Typeid INT NOT NULL
		 * 消息类型代码 Comment TEXT NOT NULL 消息类型的描述 }
		 */
		public final static String TB_MSGTYPE = "tb_msg_type"; // 消息类型的定义表
		public final static String COL_MSGTYPE_TYPEID = "typeid";// 消息类型代码
		public final static String COL_MSGTYPE_COMM = "comment";// 消息类型的描述
		public final static String SQL_CREATE_TB_MSGTYPE = "CREATE TABLE IF NOT EXISTS " + TB_MSGTYPE + " ( " + _id
				+ "  INT PRIMARY KEY AUTO_INCREMENT, " + COL_MSGTYPE_TYPEID + " VARCHAR2(4000) NOT NULL," + COL_MSGTYPE_COMM
				+ " VARCHAR2(4000) NOT NULL" + ")";
		public final static String SQL_CREATE_MSGTYPE_INDEX = "CREATE INDEX IF NOT EXISTS imsg ON " + TB_MSGTYPE + " ("
				+ COL_MSGTYPE_TYPEID + "," + COL_MSGTYPE_COMM + ")";
		/**
		 * 　TABLE msg_status { 消息的状态定义，发送成功，发送中，离线待收等等 　　　　_id INT PRIMARY KEY
		 * 　　　　StatusId INT 状态的定义代码 　　　　StatusComment TEXT 状态的描述 　　　　}
		 */
		public final static String TB_MSGSTATUS = "tb_msg_status";// KEY
		public final static String COL_MSGSTATUS_STATUSID = "statusId";// 状态的定义代码
		public final static String COL_MSGSTATUS_COMM = "statusComm";// 状态的描述
		public final static String SQL_CREATE_TB_MSGSTATUS = "CREATE TABLE IF NOT EXISTS " + TB_MSGSTATUS + " ( " + _id
				+ "  INT PRIMARY KEY AUTO_INCREMENT, " + COL_MSGSTATUS_STATUSID + " VARCHAR2(4000) NOT NULL, "
				+ COL_MSGSTATUS_COMM + " VARCHAR2(4000) NOT NULL " + ")";
		public final static String SQL_CREATE_MSGSTATUS_INDEX = "CREATE INDEX IF NOT EXISTS imsg ON " + TB_MSGSTATUS
				+ " (" + COL_MSGSTATUS_STATUSID + "," + COL_MSGSTATUS_COMM + ")";

		/**
		 * TABLE msg_extra { 消息的额外信息，主要用于文件消息、图片消息、语音消息等等 _id INT PRIMARY KEY
		 * M_Typeid INT NOT NULL;消息类型 M_unread INT NOT NULL; 标记消息是否未读
		 * M_Appendex TEXT 该类型消息的附加内容 文件路径等等附加信息 }　
		 */
		public final static String TB_MSG_EXTRA = "tb_msg_extra";//
		public final static String COL_EXTRA_TYPEID = "M_typeid";//
		public final static String COL_EXTRA_UNREAD = "M_unread";//
		public final static String COL_EXTRA_APPENDEX = "M_Appendex";//
		public final static String COL_TRANS_FILEID = "Fileid";
		public final static String COL_TRANS_FILENAME = "Filename";
		public final static String COL_TRANS_FILEURL = "Fileuri";
		public final static String COL_TRANS_FULLSIZE = "Full_size";
		public final static String COL_TRANS_COMPLETEDSIZE = "Completed_size";
		public final static String COL_TRANS_PEERID = "Peerid";
		public final static String COL_TRANS_STATE = "State";
		public final static String COL_TRANS_FDATE = "Fdate";
		public final static String COL_TRANS_COMPLETE_INDEXS = "sended_indexs";
		public final static String COL_TRANS_TASK_BEGAN = "istaskbegan";
		public final static String COL_TRANS_SPEED = "trans_speed";// 文件传输的速度
		public final static String COL_TRANS_ENCRYPTFILEPATH = "encryptfilepath";// 加密文件的保存位置
		public final static String COL_TRANS_FILEPSK = "file_psk";
		public final static String COL_TRANS_FILETYPE = "Filetype";

		public final static String SQL_CREATE_TB_MSG_EXTRA = "CREATE TABLE IF NOT EXISTS " + TB_MSG_EXTRA + " ( " + _id
				+ "  INT PRIMARY KEY AUTO_INCREMENT, " + COL_EXTRA_TYPEID + " VARCHAR2(4000) NOT NULL, " + COL_EXTRA_UNREAD
				+ " VARCHAR2(4000) NOT NULL, " + COL_EXTRA_APPENDEX + " VARCHAR2(4000) NOT NULL, " + COL_TRANS_FILEID + " VARCHAR2(4000) NOT NULL, "
				+ COL_TRANS_FILENAME + " TEXT NOT NULL, " + COL_TRANS_FILEURL + " VARCHAR2(4000) NOT NULL, " + COL_TRANS_FULLSIZE
				+ " VARCHAR2(4000) NOT NULL, " + COL_TRANS_COMPLETEDSIZE + " VARCHAR2(4000) NOT NULL, " + COL_TRANS_PEERID
				+ " VARCHAR2(4000) NOT NULL, " + COL_TRANS_STATE + " VARCHAR2(4000) NOT NULL, " + COL_TRANS_FDATE + " VARCHAR2(4000) NOT NULL, "
				+ COL_TRANS_COMPLETE_INDEXS + " BLOB NOT NULL, " + COL_TRANS_TASK_BEGAN + " VARCHAR2(4000) NOT NULL, "
				+ COL_TRANS_SPEED + " VARCHAR2(4000) NOT NULL," + COL_TRANS_ENCRYPTFILEPATH + " VARCHAR2(4000) NOT NULL,"
				+ COL_TRANS_FILEPSK + " VARCHAR2(4000) NOT NULL, " + COL_TRANS_FILETYPE + " VARCHAR2(4000)" + ")";
		public final static String SQL_CREATE_MSG_EXTRA_INDEX = "CREATE INDEX IF NOT EXISTS IEXTRA ON " + TB_MSG_EXTRA
				+ " (" + COL_EXTRA_TYPEID + "," + COL_EXTRA_UNREAD + "," + COL_EXTRA_APPENDEX + ")";

		//记事本mixunRecord的数据库字段
		// 记录表相关定义
		public static final String COL_RECORD_ID = "_id";
		// 根据type的不同，content里的内容分别是文本，关联的文件路径
		public static final String COL_RECORD_CONTENT = "f_content";
		// 是个boolean值，表示是否有关联的文件
		public static final String COL_RECORD_RELATEDID = "f_relatedid";//integer 0:无关联文件；1：有关联文件 默认为0；
		public static final String COL_RECORD_TIME = "f_time";//创建时间
		public static final String COL_RECORD_CATALOGID = "f_catalogid";// references (t_category._id) 创建主外键约束,级联删除
		public static final String COL_RECORD_TITLE = "f_title";
		public static final String COL_RECORD_ALARM="f_alarm";//闹钟时间
		// 文件的全路径
		public static final String COL_FILE_PATH = "f_filepath";
		// 文件的类型(图片，普通文件，语音)
		public static final String COL_FILE_TYPE = "f_type";
		// 如果是图片文件，就存放文件的缩略图，语音文件就存放时长，普通文件的话，文件的大小
		public static final String COL_FILE_ATTRIBUTE = "f_attribute";
		public static final String COL_FILE_KEY="f_key"; //text 加密文件的aeskey
		// 只用于mixun跟notepad的数据类型转换
		public static final String COL_RECORD_TYPE = "type";
		public static final String COL_RECORD_FILEPATH = "filepath";
		public static final String COL_RECORD_FILESIZE = "filesize";
		public static final String COL_RECORD_FILEPSK = "filepsk";
		// 文件的类型定义
		public static final int FILE_NORMAL = 100000;
		public static final int FILE_PIC = FILE_NORMAL + 1;
		public static final int FILE_VOICE = FILE_NORMAL + 2;
		public static final int FILE_TEXT = FILE_NORMAL + 3;
		
		/**
		 * Table seesion{ 和联系人的session 　　　　_id INTEGER PRIMARY KEY AUTOINCREMENT;
		 * 　　　　Peerid TEXT PRIMARY KEY NOT NULL; 最近联系的人的信息id 　　　　Lasttime text NOT
		 * NULL; 最近联系的时间 　　　　RecentmsgId INTEGER; 最近消息的id，指向msg表 　　　　Recentmsg TEXT
		 * ;最近一条消息的内容 　　　　RecentmsgTypeId INTEGER; 最近一条消息的类型 　　　　Unread INTEGER ;
		 * 未读消息的数量 　　　　SessionId TEXT NOT NULL; 会话的id 　　　　Salt TEXT NOT NULL;
		 * 会话的过程中用的salt值，是一个随机字符串 　　　　Aeskey TEXT NOT NULL;与该用户的会话主aes密钥 　　　　}
		 */
		public final static String TB_SESSION = "tb_session";//
		public final static String COL_SESSION_PEERID = "Peerid";// TEXT PRIMARY KEY
																	// NOT NULL;
																	// 最近联系的人的信息id
		public final static String COL_SESSION_LAST = "Lasttime";// 　 text NOT NULL;
																	// 最近联系的时
		public final static String COL_SESSION_RNTID = "RecentmsgId";// 　 INTEGER;
																		// 最近消息的id，指向msg表
		public final static String COL_SESSION_RNTMSG = "Recentmsg";// 　 TEXT
																	// ;最近一条消息的内容
		public final static String COL_SESSION_RNTTYPEID = "RecentmsgTypeId";// 　
																				// INTEGER;
																				// 最近一条消息的类型
		public final static String COL_SESSION_UNREAD = "Unread";// 　 INTEGER ;
																	// 未读消息的数量
		public final static String COL_SESSION_SESSIONID = "SessionId";// 　 TEXT NOT
																		// NULL;
																		// 会话的id
		public final static String COL_SESSION_SALT = "Salt";// 　 TEXT NOT NULL;
																// 会话的过程中用的salt值，是一个随机字符串
		public final static String COL_SESSION_AESKEY = "Aeskey";// 　 TEXT NOT
																	// NULL;与该用户的会话主aes密钥
		public final static String SQL_CREATE_TB_SESSION = "CREATE TABLE IF NOT EXISTS " + TB_SESSION + " ( " + _id
				+ "  INT PRIMARY KEY AUTO_INCREMENT, " + COL_SESSION_PEERID + " VARCHAR2(4000) NOT NULL, " + COL_SESSION_LAST
				+ " VARCHAR2(4000) NOT NULL, " + COL_SESSION_RNTID + " VARCHAR2(4000), " + COL_SESSION_RNTMSG + " VARCHAR2(4000), "
				+ COL_SESSION_RNTTYPEID + " VARCHAR2(4000), " + COL_SESSION_UNREAD + " VARCHAR2(4000), " + COL_SESSION_SESSIONID
				+ " VARCHAR2(4000) NOT NULL, " + COL_SESSION_SALT + " VARCHAR2(4000) NOT NULL, " + COL_SESSION_AESKEY + " VARCHAR2(4000) NOT NULL"
				+ " ) ";
		public final static String SQL_CREATE_SESSION_INDEX = "CREATE INDEX IF NOT EXISTS isession ON " + TB_SESSION
				+ " ( " + COL_SESSION_PEERID + "," + COL_SESSION_LAST + "," + COL_SESSION_RNTID + "," + COL_SESSION_RNTMSG
				+ "," + COL_SESSION_RNTTYPEID + "," + COL_SESSION_UNREAD + "," + COL_SESSION_SESSIONID + ","
				+ COL_SESSION_SALT + "," + COL_SESSION_AESKEY + " )";
		// 几种消息发送的状态
		public final static int STATUS_READY = 200000;
		public final static int STATUS_FAILED = STATUS_READY + 1;
		public final static int STATUS_INBOX = STATUS_READY + 2;
		public final static int STATUS_SUCCESS = STATUS_READY + 3;

		public final static int STATUS_FILE_RECV_READY = STATUS_READY + 4;
		public final static int STATUS_FILETRANSFERING = STATUS_READY + 5;
		public final static int STATUS_FILETRANFAILED = STATUS_READY + 6;
		public final static int STATUS_FILE_RECV_SUCESS = STATUS_READY + 7;
		public final static int STATUS_FILERECV_DECRYPT = STATUS_READY + 8;
		public final static int STATUS_FILE_SEND_READY = STATUS_READY + 9;
		public final static int STATUS_FILE_SEND_ENCRYPT = STATUS_READY + 10;
		public final static int STATUS_FILE_SEND_SUCCES = STATUS_READY + 11;
		public final static int STATUS_FILE_SEND_FAILED = STATUS_READY + 12;
		public final static int STATUS_FILE_RECV_FAILED = STATUS_READY + 13;
		public final static int STATUS_FILE_TRANS_FAILED = STATUS_READY + 14;
		public final static int STATUS_PICTURE_RECV_READY = STATUS_READY + 15;
		// 云文件传送的状态
		public final static int STATUS_CLOUD_FILE_START = STATUS_READY + 16;
		public final static int STATUS_CLOUD_FILE_SUCESS = STATUS_READY + 17;
		public final static int STATUS_CLOUD_FILE_PAUSE = STATUS_READY + 18;
		public final static int STATUS_CLOUD_FILE_START_UP = STATUS_READY + 19;
		public final static int STATUS_CLOUD_FILE_PAUSE_UP = STATUS_READY + 20;
		public final static int STATUS_CLOUD_FILE_SUCESS_UP = STATUS_READY + 21;
		
		// 群聊时重发
		public final static int STATUS_RESEND = STATUS_READY + 22;
		// 接受信息解析出错时的状态
		public final static int STATUS_CRYPTERROR = STATUS_READY + 23;
		// 群类型
		public final static int ROOM_TYPE_NORMAL = 300000;
		public final static int ROOM_TYPE_MASK = ROOM_TYPE_NORMAL + 1;
		public final static int ROOM_TYPE_MASK_ADVANCED = ROOM_TYPE_NORMAL + 2;
		
		public final static int MSGID_STATUS_NONE = 0;
		public final static int MSGID_STATUS_GOT = 1;
		public final static int MSGID_STATUS_SAVED = 2;
		public final static int MSGID_STATUS_ACKED = 3;
		
		/**
		 * TABLE room { Roomid TEXT PRIMARY KEY NOT NULL; 聊天室的id，是主键且唯一 Roomname
		 * TEXT NOT NULL; 聊天室的名称 Pinyin TEXT NOT NULL; 聊天室的全称信息，用于检索 Avatarid TEXT
		 * NOT NULL; 聊天室的头像 UpdateTime TEXT; 上次更新的时间 COL_PROOM_RECENTMSG
		 * TEXT:上次联系的信息内容 COL_PROOM_UNREAD 未读消息的数量 Aeskey TEXT NOT NULL;房间的主aes密钥 }
		 */
		public final static String TB_ROOMS = "room";
		public final static String COL_PROOM_ROOMID = "Roomid";
		public final static String COL_PROOM_OWNER = "ownerid";
		public final static String COL_PROOM_ROOMNAME = "Roomname";
		public final static String COL_PROOM_PINYIN = "Pinyin";
		public final static String COL_PROOM_AVATAR = "Avatarid";
		public final static String COL_PROOM_UPDTAETIME = "UpdateTime";
		public final static String COL_PROOM_RECENTMSG = "recentmsg";
		public final static String COL_PROOM_UNREAD = "Unread";// 　 INTEGER ;
																// 未读消息的数量
		public final static String COL_PROOM_AESKEY = "Aeskey";
		public final static String COL_PROOM_ROOMTYPE = "Roomtype";
		public final static String COL_PROOM_ROOMMASKID = "RoomMaskid";
		public final static String COL_PROOM_MASKTHEME = "Masktheme";
		public final static String COL_PROOM_ALLOWCHANGE = "Allowchange";
		public final static String SQL_CREATE_TB_ROOMS = "CREATE TABLE IF NOT EXISTS " + TB_ROOMS + "(" + _id
				+ "  INT PRIMARY KEY AUTO_INCREMENT, " + COL_PROOM_ROOMID + " VARCHAR2(4000) NOT NULL, " + COL_PROOM_OWNER
				+ " VARCHAR2(4000) NOT NULL, " + COL_PROOM_ROOMNAME + " VARCHAR2(4000) NOT NULL, " + COL_PROOM_PINYIN + " VARCHAR2(4000) NOT NULL,  "
				+ COL_PROOM_AVATAR + " VARCHAR2(4000), " + COL_PROOM_UPDTAETIME + " VARCHAR2(4000), " + COL_PROOM_RECENTMSG + " VARCHAR2(4000) , "
				+ COL_PROOM_AESKEY + " VARCHAR2(4000) NOT NULL," + COL_PROOM_UNREAD + " VARCHAR2(4000), " + COL_PROOM_ROOMMASKID + " VARCHAR2(4000), "
				+ COL_PROOM_MASKTHEME + " VARCHAR2(4000) NOT NULL DEFAULT -1, " + COL_PROOM_ALLOWCHANGE
				+ " VARCHAR2(4000) NOT NULL DEFAULT 0, " + COL_PROOM_ROOMTYPE + " VARCHAR2(4000) NOT NULL DEFAULT -1 " + ")";
		public final static String SQL_CREATE_ROOMS_INDEX = "CREATE INDEX IF NOT EXISTS irooms ON " + TB_ROOMS + " ("
				+ COL_PROOM_ROOMID + "," + COL_PROOM_OWNER + " ," + COL_PROOM_ROOMNAME + "," + COL_PROOM_PINYIN + ","
				+ COL_PROOM_AVATAR + "," + COL_PROOM_UPDTAETIME + "," + COL_PROOM_RECENTMSG + "," + COL_PROOM_AESKEY + ","
				+ COL_PROOM_ROOMMASKID + "," + COL_PROOM_MASKTHEME + "," + COL_PROOM_ALLOWCHANGE + "," + COL_PROOM_ROOMTYPE
				+ ")";

		
}
