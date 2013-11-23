package com.mx.client;

public class MessageCollection {
    private String avatar;//好友头像加密串
    private String m_peerid;//好友id
    private String tj;//消息统计
    
	public MessageCollection() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MessageCollection(String avatar, String m_peerid, String tj) {
		super();
		this.avatar = avatar;
		this.m_peerid = m_peerid;
		this.tj = tj;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getM_peerid() {
		return m_peerid;
	}
	public void setM_peerid(String m_peerid) {
		this.m_peerid = m_peerid;
	}
	public String getTj() {
		return tj;
	}
	public void setTj(String tj) {
		this.tj = tj;
	}
    
}
