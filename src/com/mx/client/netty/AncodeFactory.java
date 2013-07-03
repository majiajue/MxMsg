package com.mx.client.netty;

import java.util.HashMap;
public class AncodeFactory {
	// Private constructor prevents instantiation from other classes
	public final static int MSGID_STATUS_NONE = 0;
	private AncodeFactory() {
	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		public static final AncodeFactory INSTANCE = new AncodeFactory();
	}

	public static AncodeFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public boolean mNetworkAvailiable = false;
	private boolean isAlive = false;
	private final HashMap<String, String> mSms = new HashMap<String, String>();
	public String LastMsgId = null;
	public int LastMsgIdStatus = MSGID_STATUS_NONE;
	public static boolean isLogin = true;

	public void setOnLine() {
		isAlive = true;
		//notifyPangolinServiceEvent(PG_SERVICE_ONLINE);
	}

	public void setOffLine() {
		isAlive = false;
		//notifyPangolinServiceEvent(PG_SERVICE_OFFLINE);
	}
	
	public void setNetworkConnected(boolean status) {
		mNetworkAvailiable = status;
	}
	/**
	 * @return if 0，有网络连接状态 if 1, 处于空中模式中 if 2, wifi或者3g没有开通
	 */
	public boolean isNetworkAvailiable() {
		return mNetworkAvailiable;
	}
}
