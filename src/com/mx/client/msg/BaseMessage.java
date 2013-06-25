package com.mx.client.msg;

public class BaseMessage {
	private int msgType;        //消息类型
	private String srcMx;       //消息的发送者
	private String destMx;      //消息的接受者
	
	
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getSrcMx() {
		return srcMx;
	}
	public void setSrcMx(String srcMx) {
		this.srcMx = srcMx;
	}
	public String getDestMx() {
		return destMx;
	}
	public void setDestMx(String destMx) {
		this.destMx = destMx;
	}
	public String toString(){
		return "消息类型:"+msgType+"  发送者:"+srcMx+"  接受者:"+destMx;
	}
}
