package com.mx.clent.vo;

import java.io.Serializable;
import java.util.List;

public class MsgFriendGroup extends Group implements Serializable {

private List<MsgUser> userList;    //组的用户列表
	
	public List<MsgUser> getUserList() {
		return userList;
	}
	public void setUserList(List<MsgUser> userList) {
		this.userList = userList;
	}
	public List<MsgUser> getGrops() {
		return userList;
	}
	public String toString(){
		StringBuffer buffer=new StringBuffer();
		buffer.append("\n"+this.getGroupName()+" 群信息如下:\n");
		buffer.append("roupID:\" "+this.getGroupID()+"\"");
		buffer.append("roupOwerID:\" "+this.getGroupOwerID()+"\"");
		buffer.append("roupName:\" "+this.getGroupName()+"\"\n");
		buffer.append("组的用户列表:\n");
		for(MsgUser user: userList){
			buffer.append(user+"\n");
		}
		buffer.append("\n");
		return buffer.toString();
	}

}
