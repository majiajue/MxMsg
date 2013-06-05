package com.mx.clent.vo;

import java.io.Serializable;
import java.util.List;

public abstract class Group implements Serializable {
	private String groupID;          //Ⱥ��ID
	private String groupOwerID;      //Ⱥ��ID
	private String groupName;        //Ⱥ������
	
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public String getGroupOwerID() {
		return groupOwerID;
	}
	public void setGroupOwerID(String groupOwerID) {
		this.groupOwerID = groupOwerID;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public abstract List<MsgUser> getGrops();
}
