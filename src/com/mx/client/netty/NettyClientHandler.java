package com.mx.client.netty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.util.CharsetUtil;

import com.mx.client.db.DBDataSQL;
import com.mx.client.msg.MessagerHandler;
import com.mx.client.msg.TextHandler;
import com.mx.client.webtools.ConnectionUtils;
import com.mx.client.webtools.XmlUtil;
import com.sun.org.apache.bcel.internal.generic.DDIV;


public class NettyClientHandler extends SimpleChannelUpstreamHandler {

	private boolean readingChunks;
	private static final String TAG = "netty";
	private long mTimeOfLastReceiveK = -1;
	HashMap<String, String> mXmlMap;
	HashMap<String, com.mx.client.msg.MessagerHandler> mMessagerHandler;
	MessagerHandler mMessagerHandlerTemp;

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		/*
		 * 当连接中断的时候，考虑采取如下步骤 1、查询当前网络状况，如果网络还是连接状态，则重连 2、
		 */
		NettyStatus.isRettyRunning = false;
		//Log.v("netty", "网络故障发生");

	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		System.out.println("成功来到这了~~~~"+e.getMessage());
		if (!readingChunks) {
			HttpResponse response = (HttpResponse) e.getMessage();					
	
			if (response.isChunked()) {
				readingChunks = true;
				//Log.v("netty", "CHUNKED CONTENT {");
			} else {
				ChannelBuffer content = response.getContent();
				if (content.readable()) {
					//Log.v("netty", "CONTENT {");
					//Log.v("netty", content.toString(CharsetUtil.UTF_8));
					//Log.v("netty", "} END OF CONTENT");
				}
			}
		} else {			
			HttpChunk chunk = (HttpChunk) e.getMessage();
			if (chunk.isLast()) {
				readingChunks = false;
				//Log.v("netty", "} END OF CHUNKED CONTENT");
			} else {
				//Log.v("netty", chunk.getContent().toString(CharsetUtil.UTF_8));
				String content = chunk.getContent().toString(CharsetUtil.UTF_8);
                System.out.println("e.getMessage()"+content);
				//LOG.v(TAG, Thread.currentThread().getId() + ":todo");

				if (content.equals("k\n")) {
					NettyStatus.isConnectAlive = true;
					mTimeOfLastReceiveK = -1;
					return;
				}
				//Log.v("web", "content=" + content);
				mXmlMap = XmlUtil.instance().parseXmltoMap(content,"UTF-8");
				System.out.println("mxmlMap==="+mXmlMap.toString());
				if ((mXmlMap) != null && (mXmlMap.get("r") != null)) {
					mTimeOfLastReceiveK = -1;
					if (mXmlMap.get("r").equals("failed") && mXmlMap.get("w").equals("invalid")) {
						//LOG.v(TAG, "StreamCometClient.ResponseCallback.failed");
						NettyStatus.isConnectAlive = false;
						//MiXunService.isLogin = false;
						CloseNetworkManager();
						//Intent intent = new Intent();
						//Bundle values = new Bundle();
//						values.putString("MiXunId", StorageManager.GetInstance().getUserProfile().MyPeerid);
//						intent.putExtras(values);
//						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//						intent.setAction("org.ancode.MiXun.Login");
//						intent.setClass(AncodeFactory.getInstance().getApplicationContext(), Login.class);
//						AncodeFactory.getInstance().getApplicationContext().startActivity(intent);
					} else {
						//LOG.v(TAG, "StreamCometClient.ResponseCallback.gotmessage");
						NettyStatus.isConnectAlive = true;
						dispatchMessage(mXmlMap);
					}
				} else {
//					MixunProtocol.getInstance().delMessage("-1", new ICallApi() {
//						@Override
//						public void onSuccess(HashMap<String, String> result) {
//						}
//
//						@Override
//						public void onFailure() {
//						}
//					});
				}

			}
		}
	}

	private void CloseNetworkManager() {
		// if (mNetManager != null) {
		// mNetManager.closeConnection();
		// mNetManager = null;
		// }
	}

	public void dispatchMessage(final HashMap<String, String> xmlMap) {
//		String version = xmlMap.get("version");
//		if (version != null && version.equals("SIP/2.0")) {
//			Bundle bundle = new Bundle();
//			for (Map.Entry<String, String> m : xmlMap.entrySet()) {
//				bundle.putString(m.getKey(), m.getValue());
//			}
//			Intent intent = new Intent();
//			intent.putExtras(bundle);
	//	} 
	//else {
			if (xmlMap.get("msgid") != null & !xmlMap.get("msgid").equals("")) {
				//Log.v("netty", "msgid:" + xmlMap.get("msgid"));
				if (xmlMap.get("msgid").equals(AncodeFactory.getInstance().LastMsgId)) {
					switch (AncodeFactory.getInstance().LastMsgIdStatus) {
					case DBDataSQL.MSGID_STATUS_NONE:
						// 丢弃，不可能有这种情况
						//Log.v("netty", "DataDefine.MSGID_STATUS_NONE");
						break;
					case DBDataSQL.MSGID_STATUS_GOT:
						// 继续处理
						//Log.v("netty", "DataDefine.MSGID_STATUS_GOT");
						//根据pluginValues来调用插件管理器查
//						if(pluginValues!=null){
//						PluginMethod pm = new PluginMethod(AncodeFactory.getInstance().getApplicationContext(), pluginValues);
//						pm.doMethod(xmlMap);
//						}else{
						ProcessingMsg(xmlMap);
					//	}
						break;
					case DBDataSQL.MSGID_STATUS_SAVED:
						// 已经保存，但是还没有向服务器发送确认，发送一个确认包
						//Log.v("netty", "DataDefine.MSGID_STATUS_SAVED");
//						MixunProtocol.getInstance().delMessage(xmlMap.get("msgid"), new ICallApi() {
//
//							@Override
//							public void onSuccess(HashMap<String, String> result) {
//								// TODO Auto-generated method stub
//								AncodeFactory.getInstance().LastMsgIdStatus = DataDefine.MSGID_STATUS_ACKED;
//							}
//
//							@Override
//							public void onFailure() {
//								// TODO Auto-generated method stub
//							}
//						});
						break;
					case DBDataSQL.MSGID_STATUS_ACKED:
						//Log.v("netty", "DataDefine.MSGID_STATUS_ACKED");
						// ack已经发过了，难道服务器没收到，重连，或者再发一个呢？
						break;
					}
				} else {
					// 如果msgid不同，不管上个msgid的状态如何，进行一个全新的处理
					//Log.v("netty", "DataDefine.else");
					AncodeFactory.getInstance().LastMsgId = xmlMap.get("msgid");
					AncodeFactory.getInstance().LastMsgIdStatus = DBDataSQL.MSGID_STATUS_GOT;
					ProcessingMsg(xmlMap);
				}
				
				ConnectionUtils.getInstance().delTxtMessage(xmlMap.get("msgid"));
			}
		//}
	}

	// 正常处理消息
	private void ProcessingMsg(final HashMap<String, String> xmlMap) {
		mMessagerHandler = initMessagerHandlerMap(xmlMap);
		mMessagerHandlerTemp = mMessagerHandler.get(xmlMap.get("r"));
		AncodeFactory.getInstance().LastMsgIdStatus = DBDataSQL.MSGID_STATUS_SAVED;
		//Log.v("mixun", "delmessage " + xmlMap.get("msgid"));
//		MixunProtocol.getInstance().delMessage(xmlMap.get("msgid"), new ICallApi() {
//
//			@Override
//			public void onSuccess(HashMap<String, String> result) {
//				// TODO Auto-generated method stub
//				xmlMap.clear();
//				mMessagerHandler.clear();
//				AncodeFactory.getInstance().LastMsgIdStatus = DataDefine.MSGID_STATUS_ACKED;
//			}
//
//			@Override
//			public void onFailure() {
//				// TODO Auto-generated method stub
//			}
//		});
		if (mMessagerHandlerTemp != null) {
			mMessagerHandlerTemp.process();
		}
	}

	private HashMap<String, MessagerHandler> initMessagerHandlerMap(final HashMap<String, String> xmlMap) {
		HashMap<String, MessagerHandler> messagerHandlerMap = new HashMap<String, MessagerHandler>();
		
		messagerHandlerMap.put("sendmsg", new TextHandler(xmlMap));
//		messagerHandlerMap.put("sfs", new Sfs(xmlMap));
//		messagerHandlerMap.put("room_create", new RoomCreate(xmlMap));
//		messagerHandlerMap.put("maskroom_create", new MaskRoomCreate(xmlMap));
//		messagerHandlerMap.put("roommsg", new Roommsg(xmlMap));
//		messagerHandlerMap.put("maskroom_msg", new MaskRoommsg(xmlMap));
//		messagerHandlerMap.put("room_addone", new RoomAddone(xmlMap));
//		messagerHandlerMap.put("maskroom_addone", new MaskRoomAddone(xmlMap));
//		messagerHandlerMap.put("maskroom_role", new MaskRoomChangeRole(xmlMap));
//		messagerHandlerMap.put("roomfs", new Roomfs(xmlMap));
//		messagerHandlerMap.put("room_close", new RoomClose(xmlMap));
//		messagerHandlerMap.put("maskroom_close", new MaskRoomClose(xmlMap));
//		messagerHandlerMap.put("room_kicked", new RoomKicked(xmlMap));
//		messagerHandlerMap.put("maskroom_kick", new MaskRoomKicked(xmlMap));
//		messagerHandlerMap.put("kicked", new Kicked(xmlMap));
//		messagerHandlerMap.put("maskroom_kicked", new MaskKicked(xmlMap));
//		messagerHandlerMap.put("logout_one", new RoomLogout(xmlMap));
//		messagerHandlerMap.put("maskroom_logout_one", new MaskRoomLogout(xmlMap));
		return messagerHandlerMap;
	}
}