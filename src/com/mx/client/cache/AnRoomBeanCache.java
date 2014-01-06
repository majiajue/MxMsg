package com.mx.client.cache;

import com.mx.clent.vo.AnRoomsBean;



public class AnRoomBeanCache extends SizedHashMap<String, AnRoomsBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8347548936877008599L;
	private static int cachesize = 16;
	private static AnRoomBeanCache INSTANCE;

	private AnRoomBeanCache() {
		super("AnRoomBeanCache");
	
	}

	public static AnRoomBeanCache getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AnRoomBeanCache();
			INSTANCE.setSize(cachesize);
		}
		return INSTANCE;
	}


	public void onDataChanged() {
		if (INSTANCE != null) {
			INSTANCE.clear();
		}
	}
}
