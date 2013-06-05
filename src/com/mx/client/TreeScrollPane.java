package com.mx.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.mx.clent.vo.Group;
import com.mx.clent.vo.MsgFriendGroup;
import com.mx.clent.vo.MsgUser;

public class TreeScrollPane extends JScrollPane{
	private JPanel panel;
	private Box box;
	private List<MsgFriendGroup> groupList=new ArrayList<MsgFriendGroup>();
	
	public TreeScrollPane(){
		initTrees();
	}
	
	public TreeScrollPane(List<MsgFriendGroup> groupList){
		this.groupList=groupList;
		initTrees();
	}
	
	
	public void initTrees(){
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.setBackground(Color.white);
		panel = new JPanel();
		panel.setBackground(Color.white);
		box=Box.createVerticalBox();
		for(Group group:groupList){
			MxTree tree=new MxTree(group);
			box.add(tree);
		}
		panel.setLayout(new BorderLayout());
		panel.add(box,BorderLayout.WEST);//.WEST)
		this.setViewportView(panel);
		
	}
	
	public void changUserSate(String userId,boolean isOnline){
		for(MsgFriendGroup group:groupList){
			for(MsgUser user:group.getUserList()){
				if(userId.equals(user.getUserID())){
					user.setOnline(isOnline);
					break;
				}
				
			}
		}
		box.removeAll();
		for(Group group:groupList){
			MxTree tree=new MxTree(group);
			box.add(tree);
		}
	}
}
