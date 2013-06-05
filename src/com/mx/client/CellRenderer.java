package com.mx.client;

import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;

import com.mx.clent.vo.MsgUser;



class CellRenderer extends JLabel implements ListCellRenderer  {
	 MsgUser user;
	CellRenderer() {
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        // JOptionPane.showMessageDialog(null, value);
        String userID = (String) value;
        if (value != null) {
            Cache cache=Cache.getInstance();
            MsgUser user=cache.getfriendMap().get(userID);
            this.user=user;
            String headImage=(String)user.getAttribute("headImage");
            Icon icon =getImageIcon(this.getClass().getResource("/com/mx/client/headImage/"+headImage+".gif").getPath(),32,32);
            setText(user.getUserName()+"("+userID+")");
            setIcon(icon);
        }
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());

        }
        return this;
    }
    
    private static ImageIcon getImageIcon(String path, int i, int j) {
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
}
