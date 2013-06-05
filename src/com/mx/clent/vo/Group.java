package com.mx.clent.vo;

import java.io.Serializable;
import java.util.List;

public abstract class Group implements Serializable {
	private String groupID;          //群的ID
	private String groupOwerID;      //群主ID
	private String groupName;        //群的名字
	
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
