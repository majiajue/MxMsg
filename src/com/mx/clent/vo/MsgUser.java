package com.mx.clent.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MsgUser implements Serializable {
	private Map<String,Object> attributes=new HashMap<String,Object>();
	private String userID;            //用户的id号
	private String passWord;          //用户密码
	
	private String userName;       //用户的用户名
	private String personWord;     //用户的个性签名
	private boolean isOnline=false;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPersonWord() {
		return personWord;
	}
	public void setPersonWord(String personWord) {
		this.personWord = personWord;
	}
	
	public String toString(){
		StringBuffer buffer=new StringBuffer();
		buffer.append("userID:\" "+userID+"\"");
		buffer.append("passWord:\" "+passWord+"\"");
		buffer.append("userName:\" "+userName+"\"");
		buffer.append("personWord:\" "+personWord+"\"");
		return buffer.toString();
	}
	public Object getAttribute(String key) {
		return attributes.get(key);
	}
	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
}
