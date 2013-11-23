package com.mx.client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class MessageRender extends DefaultListCellRenderer {
	private Hashtable iconTable = new Hashtable();

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean hasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value,
				index, isSelected, hasFocus);
		//JLabel label = new JLabel();
		label.setFont(new Font("微软雅黑",0,14));
	    //JLabel jLabel2 = new JLabel();
		setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));//
		if (value instanceof MessageCollection) {
		
			MessageCollection location = (MessageCollection) value;
			ImageIcon icon = (ImageIcon) iconTable.get(value);
			if (location.getAvatar() == null||location.getAvatar().equals("null")||location.getAvatar().equals("")) {
				// System.out.println(location.getFlagFile());
				icon = new ImageIcon(this.getClass().getResource(
						"/com/mx/client/headImage/head_boy_01_32.jpg"));
				iconTable.put(value, icon);
			}
			
			Image image = icon.getImage().getScaledInstance(icon.getIconWidth(),icon.getIconHeight(), Image.SCALE_SMOOTH);            
			icon.setImage(image);   

			int borderWidth = 1;
			int spaceAroundIcon = 0;
			
			Color borderColor = Color.WHITE;

			BufferedImage bi = new BufferedImage(icon.getIconWidth() + (2 * borderWidth + 2 * spaceAroundIcon),icon.getIconHeight() + (2 * borderWidth + 2 * spaceAroundIcon), BufferedImage.TYPE_INT_ARGB);

			Graphics2D g = bi.createGraphics();
			g.setColor(borderColor);
			g.drawImage(icon.getImage(), borderWidth + spaceAroundIcon, borderWidth + spaceAroundIcon, null);

			BasicStroke stroke = new BasicStroke(5); //5 pixels wide (thickness of the border)
			g.setStroke(stroke);

			g.drawRect(0, 0, bi.getWidth() - 1, bi.getHeight() - 1);
			g.dispose();
			//label = new JLabel(new ImageIcon(bi), JLabel.LEFT);
			label.setIcon(new ImageIcon(bi));
			label.setText((char)32+" "+(char)32+location.getM_peerid()+(char)32+" "+(char)32+" "+(char)32+" "+(char)32+"("+location.getTj()+")");
			//p.add(label);
			//jLabel2.setText(location.getTj());
			//p.add(jLabel2);
			if(index%2==0){
				label.setOpaque(true);
				label.setBackground(new Color(238, 238, 239));
			}else{
				label.setOpaque(true);
				label.setBackground(new Color(219, 220, 220));
			}
		} else {

			//p.setIcon(null);
		}
		
		//System.out.println((char)32+value.toString());
		return label;
	}
}
