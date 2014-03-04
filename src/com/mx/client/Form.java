package com.mx.client;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import com.mx.clent.vo.AnRoomsBean;
import com.mx.client.webtools.ConnectionUtils;
import com.mx.client.webtools.SConfig;

/**
 * 创建匿名群
 * @author mayong
 *
 */
public class Form extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */


	/**
	 * Create the dialog.
	 */
	public Form(final String roomName) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel label = new JLabel("\u65F6\u6548");
		label.setBounds(60, 48, 54, 15);
		contentPanel.add(label);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1\u5C0F\u65F6", "1\u5929", "1\u661F\u671F", "1\u5E74"}));
		comboBox.setBounds(95, 45, 128, 21);
		contentPanel.add(comboBox);
		
		JLabel label_1 = new JLabel("\u4E3B\u9898");
		label_1.setBounds(60, 73, 54, 15);
		contentPanel.add(label_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"\u6C34\u6D52108\u5C06"}));
		comboBox_1.setBounds(95, 73, 128, 21);
		contentPanel.add(comboBox_1);
		
		final JCheckBox chckbxNewCheckBox = new JCheckBox("  \u5141\u8BB8\u7FA4\u6210\u5458\u66F4\u6362\u89D2\u8272");
		chckbxNewCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxNewCheckBox.setBounds(52, 94, 170, 23);
		contentPanel.add(chckbxNewCheckBox);
		
		final JCheckBox checkBox = new JCheckBox("  \u9AD8\u7EA7\u533F\u540D\u7FA4");
		checkBox.setHorizontalAlignment(SwingConstants.CENTER);
		checkBox.setBounds(21, 119, 185, 23);
		contentPanel.add(checkBox);
		
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
				JButton okButton = new JButton("\u786E\u5B9A");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			    okButton.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						String room = roomName;
						int a = comboBox.getSelectedIndex();
						int validity = 0;
						String level;
						String allow = null;
						switch (a) {
						case 0:
							validity = 60 * 60;
							break;
						case 1:
							validity = 60 * 60 * 24;
							break;
						case 2:
							validity = 60 * 60 * 24 * 7;
							break;
						case 3:
							validity = 60 * 60 * 24 * 365;
							break;
						}
						if (chckbxNewCheckBox.isSelected()) {
							level = "advanced";
						} else {
							level = "notanvanced";
						}
						if (checkBox.isSelected()) {
							allow = "allow";
						} else {
							allow = "notallow";
						}
						String roomValidity = String.valueOf(validity);
						try {
							String [] r = ConnectionUtils.getInstance().getCreateMaskRoomResultFromServer(roomName,"mask",roomValidity, new Boolean(level), new Boolean(allow));
							createChatRoom(r[0], SConfig.getInstance().getProfile().myPeerBean.PPeerid, roomName, r[1], "我创建群", String.valueOf(new Date().getTime()), "mask", r[2], new Boolean(allow));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}); 
			  
				JButton cancelButton = new JButton("\u53D6\u6D88");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
	            
		}
	
	public AnRoomsBean createChatRoom(String sRoomId, String sOwner, String sRoomName, String sSecret, String showMsg,
			String time, String sRoomtype, String sRoomMaskid, boolean allowchange) {
		time = String.valueOf(new Date().getTime());
		//LOG.d("debug", "time= " + time);
		// 保存到数据库
		AnRoomsBean roomBean = new AnRoomsBean(sRoomId,sOwner,sRoomName,time,SConfig.getInstance().getProfile().myPeerBean.PPeerid,sSecret,"-1",sRoomtype,sRoomMaskid,String.valueOf(allowchange));
		if (allowchange) {
			roomBean.RRoomallowchange = "1";
		}
		//LOG.v("wjy", "sRoomMaskid======" + sRoomMaskid);
		AnRoomsBean.getInstance().savePeer(roomBean.RRoomid, roomBean.ROwnerid, roomBean.RRoomname, roomBean.RPinyin, roomBean.RUpdateTime, roomBean.PRecentMsg,roomBean.RAeskey, roomBean.PUnread,roomBean.RRoometype, roomBean.RRoomMaskid, roomBean.RRoomallowchange);
		return roomBean;
	}
	
}
