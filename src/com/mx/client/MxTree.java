package com.mx.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.mx.clent.vo.Group;
import com.mx.clent.vo.MsgUser;


public class MxTree extends Box implements MouseListener {
	static ImageIcon collapsedIamge=getImageIcon(MxTree.class.getResource("/com/mx/client/image/collapsed.gif"), 16, 16);
	static ImageIcon expandedIamge=getImageIcon(MxTree.class.getResource("/com/mx/client/image/expanded.gif"), 16, 16);
	private Group dataResoure;
	private JLabel treeInfo;
	private boolean isexp=false;
	private List<MxTreeNode> treeOnlineNodes=new ArrayList<MxTreeNode>();
	Box userBox=Box.createVerticalBox();//
	private List<MxTreeNode> treeOffNodes=new ArrayList<MxTreeNode>();
	public MxTree()  {
		super(BoxLayout.Y_AXIS);
		createUserTree();
		// TODO Auto-generated constructor stub
	}
	private static ImageIcon getImageIcon(URL path, int i, int j) {
		// TODO Auto-generated method stub
		ImageIcon imageIcon = new ImageIcon(path);
		int width=imageIcon.getIconWidth();
		int height=imageIcon.getIconHeight();
		float xbili=(float) ((i*1.0)/width);
		float ybili=(float) ((j*1.0)/height);
		float bili=xbili>ybili?ybili:xbili;
		int newwidth=(int) (width*bili);
		int newheight=(int) (height*bili);
		Image image = Toolkit.getDefaultToolkit().getImage(path).getScaledInstance(newwidth, newheight, Image.SCALE_SMOOTH);
		imageIcon.setImage(image);
		return imageIcon;
	}
	public MxTree(com.mx.clent.vo.Group dataResoure) {
		super(BoxLayout.Y_AXIS);
		this.dataResoure = dataResoure;
		createUserTree();
		
	}
	private void createUserTree() {
		if(dataResoure==null){
			treeInfo=new JLabel("Î´ÃüÃû[0/0]");
			treeInfo.setIcon(collapsedIamge);
			Box box=Box.createHorizontalBox();
			box.add(treeInfo,BorderLayout.WEST);
			box.add(new JLabel("               "));
			this.add(box,BorderLayout.EAST);
		}else{
			initUsers();
			treeInfo=new JLabel("Î´ÃüÃû");
			treeInfo.setIcon(collapsedIamge);
			Box box=Box.createHorizontalBox();
			box.add(treeInfo,BorderLayout.WEST);
			int onlineNum=treeOnlineNodes.size();
			int allNum=treeOffNodes.size()+onlineNum;
			box.add(new JLabel("["+onlineNum+"/"+allNum+"]"));
			this.add(box,BorderLayout.EAST);
			for(int i=0;i<treeOnlineNodes.size();i++){
				userBox.add(treeOnlineNodes.get(i));
				userBox.add(Box.createVerticalStrut(-20));
				userBox.add(Box.createGlue());
				//userBox.doLayout();
				this.add(userBox);
			}
			
		
//			for(int i=0;i<treeOffNodes.size();i++){
//				userBox.add(treeOffNodes.get(i));
//				this.add(userBox);
//			}
		}
		treeInfo.addMouseListener(new LaleMouseAdapter());
	}

	public void initUsers(){
		System.out.println("====>da"+dataResoure.getGrops().size());
		for(int i=0;i<dataResoure.getGrops().size();i++){
		
			MsgUser user=dataResoure.getGrops().get(i);
			MxTreeNode treeNode=new MxTreeNode(user);
			if(user.isOnline()){
				treeOnlineNodes.add(treeNode);
			}else{
				treeOffNodes.add(treeNode);
			}
			
		}
	}
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}
	
	class LaleMouseAdapter extends MouseAdapter{
		
		public void mouseEntered(final MouseEvent e) {
			treeInfo.setForeground(Color.black);
    	}
		
    	public void mouseClicked(final MouseEvent e) {
    		if(e.getClickCount()==1||e.getClickCount()==2){
    			if(isexp){
    				for(int i=0;i<treeOnlineNodes.size();i++){
    					treeOnlineNodes.get(i).setVisible(false);
    		
        			}
//    				for(int i=0;i<treeOffNodes.size();i++){
//    					treeOffNodes.get(i).setVisible(false);
//        			}
    				treeInfo.setIcon(collapsedIamge);
    				isexp=false;
    			}else{
    				for(int i=0;i<treeOnlineNodes.size();i++){
    					treeOnlineNodes.get(i).setVisible(true);
        			}
//    				for(int i=0;i<treeOffNodes.size();i++){
//    					treeOffNodes.get(i).setVisible(true);
//        			}
    				treeInfo.setIcon(expandedIamge);
    				isexp=true;
    			}
    			
    		}
    	}
    	public void mouseExited(final MouseEvent e) {
    		treeInfo.setBackground(Color.white);
    	}
	}

	public List<MxTreeNode> getTreeOnlineNodes() {
		return treeOnlineNodes;
	}

	public void setTreeOnlineNodes(List<MxTreeNode> treeOnlineNodes) {
		this.treeOnlineNodes = treeOnlineNodes;
	}


	public List<MxTreeNode> getTreeOffNodes() {
		return treeOffNodes;
	}

	public void setTreeOffNodes(List<MxTreeNode> treeOffNodes) {
		this.treeOffNodes = treeOffNodes;
	}
}
