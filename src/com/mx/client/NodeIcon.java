package com.mx.client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;



public class NodeIcon extends JPanel {
	 private int isOnline=BufferedImage.TYPE_BYTE_GRAY;
	 private Image icon;
	 private String iconPath="1.gif";
	 public NodeIcon(String iconNum,boolean isOnline){
		 this.iconPath=iconNum+".gif";
		 if(true==isOnline){
			 this.isOnline=BufferedImage.TYPE_INT_BGR;
			 }else{
			 this.isOnline=BufferedImage.TYPE_BYTE_GRAY;
         }
		this.add(new JLabel("1111"));
	 }
	 
	 public void paint(Graphics g) {
		    icon =Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/com/mx/client/headImage")).getScaledInstance(32,32, Image.SCALE_SMOOTH);
		    BufferedImage bufferedImage =new BufferedImage(32,32, isOnline);
		    Graphics gi =bufferedImage.getGraphics();
		    gi.drawImage(icon, 0, 0, null);
		    gi.dispose();
		    Graphics2D g2d =(Graphics2D) g;
		    g2d.drawImage(bufferedImage, null, 0, 0);
		  }
	 
	  /**
	   * 
	   * @param isOnline
	   */
	  public void changeIconState(boolean isOnline){
		  if(isOnline){
				 this.isOnline=BufferedImage.TYPE_INT_BGR;//在线
				 }else{
				 this.isOnline=BufferedImage.TYPE_BYTE_GRAY;//非在线
	         }
		  this.paint(this.getGraphics());
		  this.repaint();
	  }
}
