package com.mx.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mx.clent.vo.MsgFriendGroup;
import com.mx.clent.vo.MsgTeam;
import com.mx.clent.vo.MsgUser;


public class Cache {
private static Cache instance;
	
	private List<MsgFriendGroup> friendGroups;
	private List<MsgTeam> teams;
	private boolean isChange=false;
	private Map<String ,MsgUser> friendMap=new HashMap<String ,MsgUser>();
	//private Map<String,TalkFrame> talkFrameMap=new HashMap<String ,EIMTalkFrame>();
	
	public static void setInstance(Cache instance) {
		Cache.instance = instance;
	}

	public static Cache getInstance() {
		if(instance==null){
			instance=new Cache();
		}
		return instance;
	}
	
	private Cache(){
		 friendGroups=new ArrayList<MsgFriendGroup>();
		 teams=new ArrayList<MsgTeam>();
	}
	
	public Map<String ,MsgUser> getfriendMap(){
		if(!isChange){
			isChange=true;
			for(MsgFriendGroup group:friendGroups){
				for(MsgUser user:group.getUserList()){
					friendMap.put(user.getUserID(), user);
				}
			}
		}
		return friendMap;
	}
	
//	public TalkFrame getTalkFrame(String userId){
//		TalkFrame frame=talkFrameMap.get(userId);
//		if(null==frame){
//			MsgUser user=this.getUserByID(userId);
//			frame=new TalkFrame(user);
//			this.addTalkFrame(userId, frame);
//		}
//		return frame;
//	}
//	
//	public void addTalkFrame(String userId,TalkFrame frame){
//		this.talkFrameMap.put(userId, frame);
//	}
//	
//	public void removeTalkFrame(String userId){
//		this.talkFrameMap.remove(userId);
//	}
	
	public MsgUser getUserByID(String id){
		return this.getfriendMap().get(id);
	}
	
	public List<MsgFriendGroup> getFriendGroups() {
		return friendGroups;
	}

	public void setFriendGroups(List<MsgFriendGroup> friendGroups) {
		this.friendGroups = friendGroups;
	}

	public List<MsgTeam> getTeams() {
		return teams;
	}

	public void setTeams(List<MsgTeam> teams) {
		this.teams = teams;
	}


//	public Map<String, TalkFrame> getTalkFrameMap() {
//		return talkFrameMap;
//	}
//
//	public void setTalkFrameMap(Map<String, TalkFrame> talkFrameMap) {
//		this.talkFrameMap = talkFrameMap;
//	}
}
