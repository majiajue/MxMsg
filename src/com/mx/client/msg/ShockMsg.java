package com.mx.client.msg;



public class ShockMsg extends BaseMessage{
	public ShockMsg(){
		this.setMsgType(MessageType.ShockFrameMessage_Type);
	 }
	
	public ShockMsg(String srcMx,String destMx){
		this();
		this.setSrcMx(srcMx);
		this.setDestMx(destMx);
	}
}
