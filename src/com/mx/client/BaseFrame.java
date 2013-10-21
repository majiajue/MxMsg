package com.mx.client;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;


public class BaseFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private  String owerID;                //�����QQ��������
	//private  String icon=this.getClass().getResource("/com/csu/client/resourse/ui/icon.png").toString();
	private  String baseTitle="密讯";   //����

	public BaseFrame(){
		super();
		this.setTitle(baseTitle);
		//System.out.println(icon);
		//Image image = ResourcesManagement.getImage(icon, 32, 32);this.getClass().getResource("/com/csu/client/resourse/ui/icon.png")
		//Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/com/csu/client/resourse/ui/icon.png").toString());
		
		//this.setIconImage(image.getScaledInstance(16, 16, Image.SCALE_SMOOTH));
	}
	
	public static void intSubstance(){
	 	JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel(new SubstanceOfficeBlue2007LookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(BaseFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
	public static void centerWindow(Container window) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = window.getSize().width;
		int h = window.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		window.setLocation(x, y);
	}
	
	public static void leftWindow(Container window) {
		// get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		// determine the new location of the window
		int w = window.getSize().width;
		int h = window.getSize().height;
		
		int x = (dim.width - w);
		int y = (dim.height - h) / 2;

		// move the window
		window.setLocation(x, y);
	}

	public String getOwerID() {
		return owerID;
	}

	public void setOwerID(String owerID) {
		this.owerID = owerID;
	}

	public String getBaseTitle() {
		return baseTitle;
	}

	public void setBaseTitle(String baseTitle) {
		this.baseTitle = baseTitle;
	}
}
