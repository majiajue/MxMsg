package com.mx.client.cache;

import java.util.HashMap;
import java.util.Iterator;

import com.mx.client.webtools.FileTransferIdentify;






public class SCache {
	private final HashMap<String, FileTransferIdentify> mFileTransferMap = new HashMap<String, FileTransferIdentify>();
	private final HashMap<String, FileTransferIdentify> mReceiveImageMap = new HashMap<String, FileTransferIdentify>();
	
	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		public static final SCache INSTANCE = new SCache();
	}

	public static SCache getInstance() {
		
		return SingletonHolder.INSTANCE;
	}
	public synchronized FileTransferIdentify getFileTransferIdentify(String id) {
		return mFileTransferMap.get(id);
	}

	public synchronized FileTransferIdentify putFileTransferIdentify(String id, FileTransferIdentify fi) {
		return mFileTransferMap.put(id, fi);
	}

	public synchronized FileTransferIdentify removeFileTransferIdentify(String id) {
		return mFileTransferMap.remove(id);
	}

	public synchronized FileTransferIdentify getReceiveImageMap(String id) {
		Iterator<String> iterator = mReceiveImageMap.keySet().iterator();
		while(iterator.hasNext()) {
			//LOG.e("debug" , "-----------------\r\n" +  mReceiveImageMap.get(iterator.next()));
		}
		return mReceiveImageMap.get(id);
	}

	public synchronized FileTransferIdentify putReceiveImageMap(String id, FileTransferIdentify fi) {
		return mReceiveImageMap.put(id, fi);
	}
}
