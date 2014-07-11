package com.mx.client;

public class TempFriend {
	private String roomid;
	private String m_group;

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getM_group() {
		return m_group;
	}

	public void setM_group(String m_group) {
		this.m_group = m_group;
	}

	public TempFriend(String roomid, String m_group) {
		super();
		this.roomid = roomid;
		this.m_group = m_group;
	}

}
