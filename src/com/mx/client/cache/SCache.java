package com.mx.client.cache;

import java.awt.Image;
import java.util.HashMap;
import java.util.Iterator;

public class SCache {
	private SCache() {
		mChatImageCache = new ImageCache(100, 15, 2);
		mChatImageCache.enableDiskCache();
	}

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

	private final ImageCache mChatImageCache;
	private final HashMap<String, FileTransferIdentify> mFileTransferMap = new HashMap<String, FileTransferIdentify>();
	private final HashMap<String, FileTransferIdentify> mReceiveImageMap = new HashMap<String, FileTransferIdentify>();

	public Image getChatImage(String uid, long msg_id) {
		String key = uid + "_" + msg_id;
		return mChatImageCache.getBitmap(key);
	}

	public void putChatImage(String uid, long msg_id, byte[] data) {
		mChatImageCache.put(uid + "_" + msg_id, data);
	}

	public void removeChatImage(String uid, long msg_id) {
		mChatImageCache.remove(uid + "_" + msg_id);
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

	private final HashMap<Long, Image> mMMSImageCache = new HashMap<Long, Image>();

	public void putMMSImage(long id, Image Image) {
		mMMSImageCache.put(id, Image);
	}

	public Image getMMSImage(long id) {
		return mMMSImageCache.get(id);
	}

	public void clearMMSImageCache() {
		mMMSImageCache.clear();
	}
}
