package com.mx.client.db;

public class DataDefine {
	public final static int TRUE = 1;
	public final static int FALSE = 0;

	public final static String IN = "in";
	public final static String OUT = "out";

	// 几种文件类型
	public final static int MSG_TEXT = 100000;
	public final static int MSG_FILE = MSG_TEXT + 1;
	public final static int MSG_VOICE = MSG_TEXT + 2;
	public final static int MSG_CALL = MSG_TEXT + 3;
	public final static int MSG_PICTURE = MSG_TEXT + 4;

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

}
