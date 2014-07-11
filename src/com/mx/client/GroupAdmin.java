package com.mx.client;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.apache.http.client.ClientProtocolException;



import com.mx.client.webtools.ConnectionUtils;
import com.mx.client.webtools.SConfig;


public class GroupAdmin {
	private ArrayList<String> managerList = new ArrayList<String>();
	public JFrame frame;
    private String roomId;
    private JTextPane textPane;
    private String ownerId;
    
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
		Vector<Vector> rowData = getRoomFriendsByNet();
		System.out.println("data===>"+rowData.toString());
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("成员");
		final DefaultTableModel tableModel = new DefaultTableModel(rowData,columnNames){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
		 
		tabbedPane.addTab("成员管理", null, panel_1, null);
	
	
		 final JTable table = new JTable();
	        table.setAutoscrolls(true);
		    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		    table.setModel(tableModel);
		    final JButton addButton = new JButton("添加"); 
		    addButton.addActionListener(new ActionListener(){//添加事件
	            public void actionPerformed(ActionEvent e){
	            	 Vector<String> row = new Vector<String>();
	            	
	                // String []rowValues = {aTextField.getText(),bTextField.getText()};
	                tableModel.addRow(row);  //添加一行
	                int rowCount = table.getRowCount() +1;   //行数加上1
	              
	            }
	        });
		   
		   
		 
			
			tabbedPane.addTab("成员管理", null, panel_1, null);
			panel_1.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_2 = new JPanel();
			panel_1.add(panel_2, BorderLayout.SOUTH);
			
		
			panel_2.add(addButton);
			
			JButton button_del = new JButton("\u5220\u9664");
			panel_2.add(button_del);
			
			JScrollPane scrollPane = new JScrollPane();
			panel_1.add(scrollPane, BorderLayout.CENTER);
			
			scrollPane.setViewportView(table);
	}
	
	private Vector<Vector> getRoomFriendsByNet() {
		Vector<Vector> rowData = new Vector<Vector>();
		
		HashMap<String, String> xmlMap;
		HashMap<String, String> xmlMap2;
		String sFriend;
		String sStatus;
		String sFriendInfo;
	
		ArrayList<String> micList = null;
		String sMic;
		String MicOwner;
		try {
			xmlMap = ConnectionUtils.getInstance().roomGetFriends(getRoomId());
			System.out.println("a---"+xmlMap.toString());
			xmlMap2 = ConnectionUtils.getInstance().getMicInfo(getRoomId());
			System.out.println("b---"+xmlMap.toString());
			MicOwner = ConnectionUtils.getInstance().getMicOwner(getRoomId());
			if (xmlMap != null && !xmlMap.isEmpty()) {
				
				
				if ("room_friends".equals(xmlMap.get("r"))) {

					// 群主ID
					sFriend = xmlMap.get("o0");
					if (sFriend != null) {
					
						
						// modified by lxc at 2014-01-24
						ownerId = sFriend;
					}
					
					//rowData.add(rowadmin);

					// 管理员ID
					int i = 0;
					;
					do {
						sFriend = xmlMap.get("m" + i);
						if (sFriend != null) {
							managerList.add(sFriend);
							
							i++;
						} else {
							break;
						}

					} while (true);
					
					// 群成员ID
					i = 0;
					do {
						 Vector<String> row = new Vector<String>();
						sFriend = xmlMap.get("u" + i);
						sStatus = xmlMap.get("s" + i);
						if (sFriend != null && sStatus != null) {
							//Log.v("wjy", "测试 " + micList.indexOf(sFriend) + "---" + sFriend)	;
							if(micList.contains(sFriend)){
								
								if(sFriend.equals(MicOwner)){
									sMic = "2";
								}else{
									sMic = "1";
								}								
							}else{
								sMic = "0";
							}
							sFriendInfo = sFriend + "|" + sStatus + "|" + sMic;
							//LOG.v("wjy", "sFriendInfo======" + sFriendInfo);
							row.add(sFriendInfo);
							rowData.add(row);
							i++;
						} else {
							break;
						}
					} while (true);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowData;
	}

}
