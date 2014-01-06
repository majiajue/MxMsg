package com.mx.client;

public class GroupLocation {
	private String GroupName, OwnName, flagFile;
    
	public GroupLocation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GroupLocation(String groupName, String ownName, String flagFile) {
		super();
		GroupName = groupName;
		OwnName = ownName;
		this.flagFile = flagFile;
	}

	public String getGroupName() {
		return GroupName;
	}

	public void setGroupName(String groupName) {
		GroupName = groupName;
	}

	public String getOwnName() {
		return OwnName;
	}

	public void setOwnName(String ownName) {
		OwnName = ownName;
	}

	public String getFlagFile() {
		return flagFile;
	}

	public void setFlagFile(String flagFile) {
		this.flagFile = flagFile;
	}
	
	public String toString() {
		return  GroupName + System.getProperty("line.separator");
	}
} 
