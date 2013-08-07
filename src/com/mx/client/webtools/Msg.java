package com.mx.client.webtools;


import java.util.HashMap;


public class Msg {
	public static final int TYPE_ERROR = 0;
	public static final int TYPE_TXT = 1;
	public static final int TYPE_IMAGE = 2;
	public static final int TYPE_VOICE = 3;
	public static final int TYPE_FILE = 4;
	public static final int TYPE_FILERESULT = 5;
	public static final int TYPE_VOICE_CALL = 6;
	public static final int TYPE_LOCATION = 7;
	private static final HashMap<String, Integer> mTypeMaps = new HashMap<String, Integer>();


	static {
		mTypeMaps.put("txt", TYPE_TXT);
		mTypeMaps.put("jpg", TYPE_IMAGE);
		mTypeMaps.put("amr", TYPE_VOICE);
		mTypeMaps.put("fil", TYPE_FILE);
		mTypeMaps.put("frs", TYPE_FILERESULT);
		mTypeMaps.put("voc", TYPE_VOICE_CALL);
		mTypeMaps.put("loc", TYPE_LOCATION);
	}


	public static String packMsg(int iType, String sMsg) {
		switch (iType) {
		case TYPE_TXT:
			return "mime:txt:" + sMsg;
		case TYPE_IMAGE:
			return "mime:jpg:" + sMsg;
		case TYPE_VOICE:
			return "mime:amr:" + sMsg;
		case TYPE_FILE:
			return "mime:fil:" + sMsg;
		case TYPE_FILERESULT:
			return "mime:frs:" + sMsg;
		case TYPE_VOICE_CALL:
			return "mime:voc:" + sMsg;
		case TYPE_LOCATION:
			return "mime:loc:" + sMsg;
		default:
			return null;
		}
	}


	public static String packTxtMsg(String sMsg) {
		return packMsg(TYPE_TXT, sMsg);
	}


	public static String packJpgMsg(String sJpg) {
		return packMsg(TYPE_IMAGE, sJpg);
	}


	public static String packAmrMsg(String sAmr) {
		return packMsg(TYPE_VOICE, sAmr);
	}


	public static String packFileMsg(String sFile) {
		return packMsg(TYPE_FILE, sFile);
	}


	public static String packVoiceMsg(String sVoc) {
		return packMsg(TYPE_VOICE_CALL, sVoc);
	}


	public static String packLocationMsg(String sLoc) {
		return packMsg(TYPE_LOCATION, sLoc);
	}


	/*
	 * 判定是否是一个合法的消息
	 */
	public static boolean isMsg(String sMsg) {
		return sMsg.startsWith("mime:");
	}


	/*
	 * 获得消息的类型 getTypeOfMsg("mime:txt:abcdefgsdfsdfsdfsd");
	 */
	public static int getTypeOfMsg(String sMsg) {
		if (isMsg(sMsg)) {
			String type = sMsg.substring(5, 8);
			Integer value = mTypeMaps.get(type);
			if (value != null)
				return value;
			else
				return TYPE_ERROR;
		}
		return TYPE_ERROR;
	}


	/*
	 * 获得消息的内容
	 */
	public static String getContentOfMsg(String sMsg) {
		if (isMsg(sMsg)) {
			return sMsg.substring(9);
		}
		return null;
	}
}
