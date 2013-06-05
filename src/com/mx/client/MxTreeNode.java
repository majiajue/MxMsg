package com.mx.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.mx.clent.vo.MsgUser;


public class MxTreeNode extends JPanel implements MouseListener {
	private MsgUser user;
	public MxTreeNode(MsgUser user2) {
		this.addMouseListener(this);
		this.user = user2;
		createNode(user2);
	}
	private void createNode(MsgUser user2) {
		// TODO Auto-generated method stub
		Box box=Box.createHorizontalBox();
		String headImage=(String)user2.getAttribute("headImage");
		NodeIcon nodeIcon=new NodeIcon(headImage,user2.isOnline());
		box.add(nodeIcon);
		Box verticalBox=Box.createVerticalBox();
		JLabel personWordLale=new JLabel();
		personWordLale.setText(user2.getUserName());
		JLabel kongjianLale=new JLabel();
		kongjianLale.setText("ø’º‰»’÷æ.......");
		verticalBox.add(personWordLale);
		verticalBox.add(kongjianLale);
		box.add(verticalBox);
		box.add(Box.createVerticalStrut(-120));
		this.add(box);
		//this.setBackground(Color.white);
		this.setForeground(Color.BLACK);
		//this.setSize(40, 40);
		this.setVisible(true);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
