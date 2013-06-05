package com.mx.client;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class MyPanel extends JPanel
{
	private Image image=null;
	public MyPanel(String imagePath)
	{
		this.setOpaque(false);
		if(!"".equals(imagePath)){
			image=getIcon(imagePath).getImage();
		}
		
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
	
	public static ImageIcon getIcon(String path)
	{
		try
		{
			//System.out.println(Main.class.getResource(path));
			System.out.println(MyPanel.class.getResource(path));
			ImageIcon icon=new ImageIcon(ImageIO.read(MyPanel.class.getResource(path)));
			
			return icon;
		}
		catch (IOException e)
		{
			System.out.println("Í¼Æ¬£º"+path+"²»´æÔÚ£¡");
			return null;
		}
	}
}
