package com.mx.client;

public class MessageCollection {
    private String avatar;//好友头像加密串
    private String m_peerid;//好友id
    private String tj;//消息统计
    private String isGroup;//0代表不是，1代表是
    private String group_user;
	public String getGroup_user() {
		return group_user;
	}
	public void setGroup_user(String group_user) {
		this.group_user = group_user;
	}
	public String getIsGroup() {
		return isGroup;
	}
	public void setIsGroup(String isGroup) {
		this.isGroup = isGroup;
	}
	public MessageCollection() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MessageCollection(String avatar, String m_peerid, String tj,String isGroup,String group_user) {
		super();
		this.avatar = avatar;
		this.m_peerid = m_peerid;
		this.tj = tj;
		this.isGroup = isGroup;
		this.group_user =group_user;
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
