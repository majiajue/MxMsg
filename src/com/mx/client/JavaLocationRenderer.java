package com.mx.client;

import javax.swing.*;

import java.awt.*;
import java.util.*;


public class JavaLocationRenderer extends DefaultListCellRenderer {
	private Hashtable iconTable = new Hashtable();

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean hasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value,
				index, isSelected, hasFocus);
		System.out.println("value===" + value.getClass());
		if (value instanceof JavaLocation) {
			System.out.println("===");
			JavaLocation location = (JavaLocation) value;
			ImageIcon icon = (ImageIcon) iconTable.get(value);
			if (icon == null) {
				// System.out.println(location.getFlagFile());
				icon = new ImageIcon(this.getClass().getResource(
						"/com/mx/client/headImage/head_boy_01_32.jpg"));
				iconTable.put(value, icon);
			}
			label.setIcon(icon);
			
			if(index%2==0){
				label.setBackground(new Color(220, 220, 221));
			}else{
				label.setBackground(new Color(238, 238, 238));
			}
		} else {

			label.setIcon(null);
		}
		label.setText((char)32+" "+(char)32+value.toString());
		System.out.println((char)32+value.toString());
		return (label);
	}
}
