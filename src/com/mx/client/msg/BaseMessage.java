package com.mx.client.msg;

public class BaseMessage {
	private int msgType;        //��Ϣ����
	private String srcMx;       //��Ϣ�ķ�����
	private String destMx;      //��Ϣ�Ľ�����
	
	
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
		return "��Ϣ����:"+msgType+"  ������:"+srcMx+"  ������:"+destMx;
	}
}
