package com.mx.client;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.AbstractListModel;

import com.mx.client.webtools.ConnectionUtils;
import com.mx.client.webtools.SConfig;


public class GroupAdmin {
    
	public JFrame frame;
    private String roomId;
    private JTextPane textPane;
    
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GroupAdmin window = new GroupAdmin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GroupAdmin() {
		initialize();
	}
    
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 434, 261);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("群公告", null, panel, null);
		panel.setLayout(null);
		
	    textPane = new JTextPane();
		textPane.setBounds(59, 35, 277, 126);
		panel.add(textPane);
		
		JLabel label = new JLabel("\u7FA4\u516C\u544A\u8BBE\u7F6E");
		label.setBounds(171, 10, 86, 15);
		panel.add(label);
		
		JButton button = new JButton("\u53D1\u5E03");
		button.setBounds(86, 183, 93, 23);
		button.addMouseListener(new MouseListener() {
			
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
				Map<String, String> map =ConnectionUtils.getInstance().addNotice(getRoomId(), SConfig.getInstance().profile.myPeerBean.PPeerid, textPane.getText());
				System.out.println("--->"+map.toString());
			}
		});
		panel.add(button);
		
		JButton button_1 = new JButton("\u5220\u9664");
		button_1.setBounds(226, 183, 93, 23);
		panel.add(button_1);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("成员管理", null, panel_1, null);
		panel_1.setLayout(null);
		
		JList list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBounds(92, 10, 226, 188);
		panel_1.add(list);
		
		JButton button_2 = new JButton("\u6DFB\u52A0\u6210\u5458");
		button_2.setBounds(102, 208, 93, 23);
		panel_1.add(button_2);
		
		JButton button_3 = new JButton("\u5220\u9664\u6210\u5458");
		button_3.setBounds(225, 208, 93, 23);
		panel_1.add(button_3);
	}
}
