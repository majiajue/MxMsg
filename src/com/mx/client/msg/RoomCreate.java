package com.mx.client.msg;

import java.util.HashMap;



import com.mx.clent.vo.AnPeersBean;
import com.mx.clent.vo.AnRoomsBean;
import com.mx.client.webtools.SConfig;

public class RoomCreate implements MessagerHandler {
	private HashMap<String,String> mXmlMap;
	public static long TiemOffset = 0;
	public RoomCreate(HashMap<String, String> xmlMap) {
		mXmlMap = xmlMap;
	}
	@Override
	public boolean process() {
		
			
			boolean NoVoice = false;
			
			if(mXmlMap.get("r").equals("room_create")) {
				
				if(!mXmlMap.get("owner").equals(SConfig.getInstance().getProfile().myPeerBean.PPeerid)) {
					AnPeersBean peer = new AnPeersBean();
					peer.PPeerid = mXmlMap.get("owner");
					peer.PUsername = mXmlMap.get("owner");
					System.out.println(peer.PPeerid+" "+peer.PUsername);
					AnPeersBean.getInstance().savePeer(peer.PPeerid, peer.PUsername, "", "", "", "", "", "");
				}
				
				System.out.println("id-->"+mXmlMap.get("id")+"owner"+mXmlMap.get("owner")+"subject-->"+mXmlMap.get("subject")+"time-->"+mXmlMap.get("time"));
				AnRoomsBean bean = createChatRoom(mXmlMap.get("id"), mXmlMap.get("owner"), mXmlMap.get("subject"), 
						mXmlMap.get("secret"), mXmlMap.get("owner") + "邀请你入群", mXmlMap.get("time"), "300000", "", false);
				if(bean == null) {
					//LOG.d("room", "save to room session db fail");
					return true;
				}
				
//				Intent mTxtmsg = new Intent();
//				mTxtmsg.setAction("org.ancode.group.RoomMsgActivity");
//				mTxtmsg.setClass(AncodeFactory.getInstance().getApplicationContext(), RoomChatActi.class);
//				mTxtmsg.putExtra(RoomChatActi.INTENT_EXTRA_ROOM_ID, mXmlMap.get("id"));
//				mTxtmsg.putExtra(RoomChatActi.INTENT_EXTRA_ROOM_NAME, bean.RRoomname);
//				mTxtmsg.putExtra(RoomChatActi.INTENT_EXTRA_ROOM_OWNER, mXmlMap.get("owner"));
//				mTxtmsg.putExtra(RoomChatActi.INTENT_EXTRA_ROOM_TYPE, DataDefine.ROOM_TYPE_NORMAL);
//				mTxtmsg.putExtra(RoomChatActi.INTENT_EXTRA_ROOM_SECRET, mXmlMap.get("secret"));
				Long time = System.currentTimeMillis();
				if((time - HandlerHelper.lastRingTime)>5000){	
					NoVoice = false;
				}else{
					NoVoice = true;
				}
				
			}
			return true;
			
		}
	
	public AnRoomsBean createChatRoom(String sRoomId, String sOwner, String sRoomName, String sSecret, String showMsg,
			String time, String sRoomtype, String sRoomMaskid, boolean allowchange) {
		time = String.valueOf((Long.valueOf(time) + TiemOffset));
		//LOG.d("debug", "time= " + time);
		// 保存到数据库
		AnRoomsBean roomBean = new AnRoomsBean(sRoomId,sOwner,sRoomName,time,SConfig.getInstance().getProfile().myPeerBean.PPeerid,sSecret,"-1",sRoomtype,sRoomMaskid,String.valueOf(allowchange));
		if (allowchange) {
			roomBean.RRoomallowchange = "1";
		}
		//LOG.v("wjy", "sRoomMaskid======" + sRoomMaskid);
		AnRoomsBean.getInstance().savePeer(roomBean.RRoomid, roomBean.ROwnerid, roomBean.RRoomname, roomBean.RPinyin, roomBean.RUpdateTime, roomBean.PRecentMsg,roomBean.RAeskey, roomBean.PUnread,roomBean.RRoometype, roomBean.RRoomMaskid, roomBean.RRoomallowchange);
		return roomBean;
	}

}
