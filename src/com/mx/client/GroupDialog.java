package com.mx.client;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

import com.mx.clent.vo.AnRoomsBean;
import com.mx.client.webtools.ConnectionUtils;
import com.mx.client.webtools.SConfig;

/**
 * 群创建
 * @author mayong
 *
 */

public class GroupDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GroupDialog dialog = new GroupDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GroupDialog() {
		setBounds(100, 100, 365, 197);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setToolTipText("\u8BF7\u8F93\u5165\u7FA4\u540D\u79F0");
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		textField = new JTextField();
		textField.setToolTipText("\u8BF7\u8F93\u5165\u7FA4\u540D\u79F0");
		textField.setBounds(115, 36, 162, 26);
		contentPanel.add(textField);
		textField.setColumns(10);
		
			JLabel label = new JLabel("\u8BF7\u8F93\u5165\u7FA4\u540D\u79F0");
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setVerticalAlignment(SwingConstants.BOTTOM);
			label.setBounds(16, 31, 95, 26);
			contentPanel.add(label);
		
		
			final JCheckBox checkBox = new JCheckBox("\u5EFA\u7ACB\u533F\u540D\u7FA4");
			checkBox.setBounds(115, 71, 103, 23);
			contentPanel.add(checkBox);
		
		
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
				JButton okButton = new JButton("\u786E\u5B9A");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			
			
				JButton cancelButton = new JButton("\u53D6\u6D88");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			
		String roomName ="";
		
		okButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				String roomName="";
				if("".equals(textField.getText().trim())||textField.getText()==null){
					roomName="密聊聊天室";
				}else{
					roomName = textField.getText();
				}
				if(checkBox.isSelected()){
				    Form form = new Form(roomName);
				    form.setVisible(true);
				    setVisible(false);
				    
				}else{
				
					String roomtype ="300000";
					try {
						String [] room= ConnectionUtils.getInstance().getCreateRoomResultFromServer3(roomName);
						System.out.println("room--->"+room);
						createChatRoom(room[0], SConfig.getInstance().getProfile().myPeerBean.PPeerid, roomName, room[1], "我创建群", String.valueOf(new Date().getTime()), roomtype, "", new Boolean(false));
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
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
