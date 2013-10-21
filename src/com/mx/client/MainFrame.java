package com.mx.client;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sun.applet.Main;

import com.mx.clent.vo.MsgFriendGroup;
import com.mx.clent.vo.MsgUser;
import com.mx.client.db.DBDataSQL;
import com.mx.client.db.GenDao;
import com.mx.client.netty.NettyClient;
import com.mx.client.webtools.ConnectionUtils;
import com.mx.client.webtools.CryptorException;
import com.mx.client.webtools.RSAEncryptor;
import com.mx.client.webtools.SConfig;
import com.sun.awt.AWTUtilities;

public class MainFrame extends BaseFrame {
	List<JavaLocation> defaultLocations = new ArrayList<JavaLocation>();
	static Point origin = new Point();
	private JLabel small = null;
	static SystemTray mainTray = null;// 系统托盘
	static TrayIcon maintrayIcon = null; // 托盘图标
	private JLabel big = null;
	private JLabel close = null;
	private static List<MsgFriendGroup> friends = new ArrayList<MsgFriendGroup>();
	private static MsgUser ower = new MsgUser();

	private static MainFrame instance;

	public static MainFrame getInstance() {
		if (instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}

	private MainFrame() {
		try {

			intSubstance();
			initComponents();
			String s = "test";

			try {
				System.out.println("RSA s:" + s);
				String m1 = RSAEncryptor.getInstance().myEncryptBase64Encode(
						s.getBytes());
				System.out.println("RSA m1:" + m1);
				String m2 = new String(RSAEncryptor.getInstance()
						.myDecryptBase64String(m1));
				System.out.println("RSA m2:" + m2.trim());
			} catch (CryptorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public void changTryIcon(){
	// EIMTrayIcon qqTrayIcon=EIMTrayIcon.getInStance();
	// qqTrayIcon.setFrame(this);
	// qqTrayIcon.setTrayIconState(EIMClientConfig.OnlineTryIcon_Type);
	// qqTrayIcon.showIcon(EIMClientConfig.OnlineTryIcon_Type);
	// }

	private void initComponents() {
		try {

			if (SystemTray.isSupported()) // 如果操作系统支持托盘
			{
				this.tray();
			}
			mainTray.add(maintrayIcon);

			final JPopupMenu popupMenu = new JPopupMenu();
			JMenuItem menuItem = new JMenuItem("删除该好友");
			menuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String peerid = ((JavaLocation) sampleJList
							.getSelectedValue()).getPeerId();
					Hashtable<String, Object> table = new Hashtable<String, Object>();
					table.put(DBDataSQL.COL_PEER_PEERID, table);
					GenDao.getInstance().executeDelete(DBDataSQL.TB_PEERS,
							table);
					defaultLocations.remove(sampleJList.getSelectedIndex());
					sampleJList.setListData(defaultLocations.toArray());
				}
			});
			popupMenu.add(menuItem);
			this.setUndecorated(true);
			this.setSize(349, 550);
			this.setAlwaysOnTop(true);
			setMinimumSize(new java.awt.Dimension(349, 400));
			// Shape shape = new RoundRectangle2D.Double(0, 0, this.getWidth(),
			// this.getHeight(), 10.0D, 10.0D);
			// AWTUtilities.setWindowShape(this, shape);
			int w = 5;
			int h = 5;
			int _width = this.getWidth();
			int _height = this.getHeight();
			GeneralPath path = new GeneralPath();
			path.append(new Ellipse2D.Float(w, h, -w, -h), true);
			path.append(new Line2D.Float(0, h, 0, _height - h), true);
			path.append(new Ellipse2D.Float(w, _height - h, -w, h), true);
			path.append(new Line2D.Float(w, _height, _width - w, _height), true);
			path.append(new Ellipse2D.Float(_width, _height, -w, -h), true);
			path.append(new Line2D.Float(_width, _height - h, _width, h), true);
			path.append(new Ellipse2D.Float(_width - w, h, w, -h), true);
			path.append(new Line2D.Float(_width - w, 0, w, 0), true);
			AWTUtilities.setWindowShape(this, path);

			jPanel1 = new javax.swing.JPanel();

			findField = new AutoCompletionField();
			// Cache cache=Cache.getInstance();
			// findField.setFilter(new
			// DefaultCompletionFilter(cache.getfriendMap()));

			headImage = new javax.swing.JLabel();
			stateComboBox = new javax.swing.JComboBox();
			personWord = new javax.swing.JLabel();
			// jToolBar1 = new javax.swing.JToolBar();

			compnayHome = new javax.swing.JButton();
			// System.out.println(this.getClass().getResource("/com/csu/client/resourse/ui/compnayHome.gif").toString());
			// Icon
			// compnayhomIcon=ResourcesManagement.getImageIcon(this.getClass().getResource("/com/csu/client/resourse/ui/compnayHome.gif").toString(),16,16);
			// compnayHome.setIcon(compnayhomIcon);
			System.out.println("2");
			compnayEmail = new javax.swing.JButton();
			// Icon
			// compnayEmailIcon=ResourcesManagement.getImageIcon(this.getClass().getResource("/com/csu/client/resourse/ui/Explorer.png").toString(),16,16);
			// compnayEmail.setIcon(compnayEmailIcon);

			compnayOA = new javax.swing.JButton();

			// Icon
			// oaIcon=ResourcesManagement.getImageIcon(this.getClass().getResource("/com/csu/client/resourse/ui/oa.gif").toString(),16,16);
			// compnayOA.setIcon(oaIcon);

			news = new javax.swing.JButton();
			// Icon
			// newsIcon=ResourcesManagement.getImageIcon(this.getClass().getResource("/com/csu/client/resourse/ui/news.gif").toString(),16,16);
			// news.setIcon(newsIcon);

			jPanel2 = new javax.swing.JPanel();
			jToolBar2 = new javax.swing.JToolBar();
			iEButton = new javax.swing.JButton();
			// Icon
			// iEIcon=ResourcesManagement.getImageIcon(this.getClass().getResource("/com/csu/client/resourse/ui/Explorer_16.png").toString(),16,16);
			// iEButton.setIcon(iEIcon);
			personHomeButton = new javax.swing.JButton();
			// Icon
			// homIcon=ResourcesManagement.getImageIcon(this.getClass().getResource("/com/csu/client/resourse/ui/Home.png").toString(),16,16);
			// personHomeButton.setIcon(homIcon);

			mySetButton = new javax.swing.JButton();
			// Icon
			// mySetIcon=ResourcesManagement.getImageIcon("ui/set.gif",16,16);
			// mySetButton.setIcon(mySetIcon);
			sysSetButton = new RButton(RButton.RIGHT_ROUND_RECT);
			jToolBar3 = new javax.swing.JToolBar();
			infoButton = new javax.swing.JButton();
			findButton = new javax.swing.JButton();
			addButton = new javax.swing.JButton();
			// Icon
			// infoIcon=ResourcesManagement.getImageIcon(this.getClass().getResource("/com/csu/client/resourse/ui/sysIfo.gif").toString());
			// infoButton.setIcon(infoIcon);
			// Icon
			// findIcon=ResourcesManagement.getImageIcon(this.getClass().getResource("/com/csu/client/resourse/ui/search.gif").toString());
			// findButton.setIcon(findIcon);

			mainTabbedPanel = new SnapTipTabbedPane();
			mainTabbedPanel.setUI(new TabbedPaneUI());
			//mainTabbedPanel.setFont(new Font("宋体", Font.PLAIN, 16));
			friendPanel = new javax.swing.JPanel();
			// friendTree=new JListCustomModel();
			teamPanel = new javax.swing.JPanel();
			zuijinPanel = new javax.swing.JPanel();
			// GenDao.getInstance().getArrayValue(, columnName, valueColumn,
			// condition)(, columnName, valueColumn, condition)
			JavaLocationCollection collection = new JavaLocationCollection(
					GenDao.getInstance()
							.getArrayValue(
									SConfig.getInstance().getProfile().myPeerBean.PPeerid));
			JavaLocationListModel listModel = new JavaLocationListModel(
					collection);
			sampleJList = new JList(listModel);
			sampleJList.setBackground(new Color(204,204,204));
			sampleJList.setCellRenderer(new JavaLocationRenderer());
			Font displayFont = new Font("Serif", Font.BOLD, 18);
			sampleJList.setFont(displayFont);
			sampleJList.addListSelectionListener(new ValueReporter());
			sampleJList.add(popupMenu);
			sampleJList.addMouseListener(new MouseListener() {

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
					if (e.getClickCount() == 2) {

						String peerid = ((JavaLocation) sampleJList
								.getSelectedValue()).getPeerId();
						final MsgUser user = new MsgUser();
						user.setUserID(peerid);
						user.setUserName(peerid);
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {

								TalkFrame frame = new TalkFrame(user);
								frame.setVisible(true); // 这个就是程序界面初始化
							}
						});

						// System.out.println("peerid===" + peerid);

						// String peerid = ((JavaLocation)
						// sampleJList.getSelectedValue()).getPeerId();
						// System.out.println("peerid===" + peerid);
						// ConnectionUtils.getInstance().getPubkey(peerid);
						// String mMimeSend = "mime:txt:" + "helloworld!";
						// String mecode = "";
						// try {
						// mecode =
						// RSAEncryptor.getInstance().encryptBase64Encode(mMimeSend.getBytes(),
						// peerid);
						// } catch (CryptorException e1) {
						// // TODO Auto-generated catch block
						// e1.printStackTrace();
						// }
						// Map<String, Object> map = new HashMap<String,
						// Object>();
						// map.put("duid", peerid);
						// map.put("msg", mecode);
						// ConnectionUtils.getInstance().postTxtMessage(map);
						// System.out.println();

					}
					if (e.getButton() == 3
							&& sampleJList.getSelectedIndex() >= 0) {

						popupMenu.show(sampleJList, e.getX(), e.getY());

					}
				}
			});
			// friendPanel.add(sampleJList);
			// jToolBar1.setEnabled(false);
			jToolBar2.setEnabled(false);
			jToolBar3.setEnabled(false);

			headImage.setBackground(new java.awt.Color(255, 255, 255));
			headImage.setForeground(new java.awt.Color(255, 255, 102));
			headImage
					.setIcon(new ImageIcon(
							MainFrame.class
									.getResource("/com/mx/client/headImage/portrait_60x60.png")));
			String headnum = "1";
			// Icon
			// headIcon=ResourcesManagement.getImageIcon(this.getClass().getResource("/com/csu/client/resourse/head/"+headnum+".gif").toString());

			// headImage.setIcon(headIcon);
			// headImage.setMinimumSize(new java.awt.Dimension(42, 15));

			stateComboBox.setModel(new javax.swing.DefaultComboBoxModel(
					new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
			small = new JLabel("");
			small.setIcon(new ImageIcon(MainFrame.class
					.getResource("/com/mx/client/image/button/button01_up.png")));
			// big = new JLabel("");
			// big.setIcon(new ImageIcon(MainFrame.class
			// .getResource("/com/mx/client/image/button/full.png")));
			close = new JLabel("");
			close.setIcon(new ImageIcon(MainFrame.class
					.getResource("/com/mx/client/image/button/button02_up.png")));
			small.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseEntered(e);
					// small.setIcon(new ImageIcon(MainFrame.class
					// .getResource("/com/mx/client/image/button/btn_min_2.png")));
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mousePressed(e);

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseExited(e);
					small.setIcon(new ImageIcon(
							MainFrame.class
									.getResource("/com/mx/client/image/button/button01_up.png")));
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);

					small.setIcon(new ImageIcon(
							MainFrame.class
									.getResource("/com/mx/client/image/button/button01_down.png")));
					// try {
					// mainTray.add(maintrayIcon);
					// } catch (AWTException e1) {
					// // TODO Auto-generated catch block
					// e1.printStackTrace();
					// } // 将托盘图标添加到系统的托盘实例中
					// // setVisible(false); // 使窗口不可视
					// //dispose();
					setVisible(false);
					dispose();
				}

				// @Override
				// public void mouseMoved(MouseEvent e) {
				// // TODO Auto-generated method stub
				// small.setIcon(new ImageIcon(
				// MainFrame.class
				// .getResource("/com/mx/client/image/button/button01_down.png")));
				// super.mouseMoved(e);
				//
				// }
			});

			close.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseEntered(e);
					// small.setIcon(new ImageIcon(MainFrame.class
					// .getResource("/com/mx/client/image/button/btn_min_2.png")));
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mousePressed(e);

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseExited(e);
					close.setIcon(new ImageIcon(
							MainFrame.class
									.getResource("/com/mx/client/image/button/button02_up.png")));
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);
					close.setIcon(new ImageIcon(
							MainFrame.class
									.getResource("/com/mx/client/image/button/button02_down.png")));
					System.exit(0);
					// dispose();
				}

			});

			// small.setBounds(600, 0, 60, 60);
			personWord.setText(ower.getPersonWord());
			JLabel label = new JLabel("");
			label.setIcon(new ImageIcon(MainFrame.class
					.getResource("/com/mx/client/image/logo.png")));
			// compnayHome.setText("��˾��ҳ");
			// jToolBar1.add(compnayHome);

			// compnayEmail.setText("��˾����");
			// jToolBar1.add(compnayEmail);

			// compnayOA.setText("��˾OA");
			// jToolBar1.add(compnayOA);

			// news.setText("��˾����");
			// jToolBar1.add(news);
			jPanel1.setBackground(new Color(222, 222, 222));
			GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
			jPanel1Layout
					.setHorizontalGroup(jPanel1Layout
							.createParallelGroup(Alignment.TRAILING)
							.addGroup(
									jPanel1Layout
											.createSequentialGroup()
											.addGroup(
													jPanel1Layout
															.createParallelGroup(
																	Alignment.TRAILING)
															.addGroup(
																	Alignment.LEADING,
																	jPanel1Layout
																			.createSequentialGroup()
																			.addGap(30)
																			.addComponent(
																					headImage))
															.addGroup(
																	Alignment.LEADING,
																	jPanel1Layout
																			.createSequentialGroup()
																			.addGroup(
																					jPanel1Layout
																							.createParallelGroup(
																									Alignment.LEADING)
																							.addGroup(
																									jPanel1Layout
																											.createSequentialGroup()
																											.addComponent(
																													label)
																											.addPreferredGap(
																													ComponentPlacement.UNRELATED,
																													340,
																													Short.MAX_VALUE)

																											.addComponent(
																													small)
																											// .addPreferredGap(ComponentPlacement.RELATED)
																											// .addComponent(big)
																											.addComponent(
																													close)))))));
			jPanel1Layout
					.setVerticalGroup(jPanel1Layout
							.createParallelGroup(Alignment.LEADING)
							.addGroup(
									jPanel1Layout
											.createSequentialGroup()

											.addPreferredGap(
													ComponentPlacement.RELATED)
											.addGroup(
													jPanel1Layout
															.createSequentialGroup()
															.addGroup(
																	jPanel1Layout
																			.createParallelGroup(
																					Alignment.BASELINE)
																			.addComponent(
																					small)
																			// .addComponent(big)
																			.addComponent(
																					close)
																			.addComponent(
																					label)
																			.addGroup(
																					jPanel1Layout
																							.createSequentialGroup()
																							.addGap(25)
																							.addComponent(
																									headImage)))
															.addContainerGap(
																	204,
																	Short.MAX_VALUE))));
			jPanel1.setLayout(jPanel1Layout);
			// javax.swing.GroupLayout jPanel1Layout = new
			// javax.swing.GroupLayout(
			// jPanel1);
			// jPanel1.setLayout(jPanel1Layout);
			// jPanel1Layout
			// .setHorizontalGroup(jPanel1Layout
			// .createParallelGroup(
			// javax.swing.GroupLayout.Alignment.LEADING)
			// .addGroup(
			// Alignment.TRAILING,
			// jPanel1Layout
			// .createSequentialGroup()
			// .addContainerGap(310,
			// Short.MAX_VALUE)
			// .addComponent(small,
			// GroupLayout.PREFERRED_SIZE,
			// GroupLayout.DEFAULT_SIZE,
			// GroupLayout.PREFERRED_SIZE)
			// .addPreferredGap(
			// ComponentPlacement.RELATED)
			// .addComponent(big)
			// .addContainerGap())
			// .addGroup(
			// jPanel1Layout
			// .createSequentialGroup()
			// .addContainerGap()
			// .addComponent(
			// headImage,
			// javax.swing.GroupLayout.PREFERRED_SIZE,
			// javax.swing.GroupLayout.DEFAULT_SIZE,
			// javax.swing.GroupLayout.PREFERRED_SIZE)
			// .addPreferredGap(
			// javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			//
			// .addGroup(
			// jPanel1Layout
			// .createParallelGroup(
			// javax.swing.GroupLayout.Alignment.LEADING)
			// .addComponent(
			// personWord,
			// javax.swing.GroupLayout.DEFAULT_SIZE,
			// 179,
			// Short.MAX_VALUE))
			// .addContainerGap())
			// // .addComponent(jToolBar1,
			// // javax.swing.GroupLayout.DEFAULT_SIZE, 200,
			// // Short.MAX_VALUE)
			// .addComponent(findField,
			// javax.swing.GroupLayout.DEFAULT_SIZE, 200,
			// Short.MAX_VALUE));
			// jPanel1Layout
			// .setVerticalGroup(jPanel1Layout
			// .createParallelGroup(
			// javax.swing.GroupLayout.Alignment.LEADING).addGroup(
			// jPanel1Layout
			// .createParallelGroup(
			// Alignment.BASELINE)
			// .addComponent(small)
			// .addComponent(big))
			// .addGroup(
			// javax.swing.GroupLayout.Alignment.TRAILING,
			// jPanel1Layout
			// .createSequentialGroup()
			// .addContainerGap()
			// .addGroup(
			// jPanel1Layout
			// .createParallelGroup(
			// javax.swing.GroupLayout.Alignment.LEADING,
			// false)
			// .addComponent(
			// headImage,
			// javax.swing.GroupLayout.PREFERRED_SIZE,
			// javax.swing.GroupLayout.DEFAULT_SIZE,
			// javax.swing.GroupLayout.PREFERRED_SIZE)
			//
			// )
			// .addPreferredGap(
			// javax.swing.LayoutStyle.ComponentPlacement.RELATED,
			// javax.swing.GroupLayout.DEFAULT_SIZE,
			// Short.MAX_VALUE)
			//
			// // .addComponent(jToolBar1,
			// // javax.swing.GroupLayout.PREFERRED_SIZE,
			// // 25,
			// // javax.swing.GroupLayout.PREFERRED_SIZE)
			//
			// .addComponent(
			// findField,
			// javax.swing.GroupLayout.PREFERRED_SIZE,
			// 29,
			// javax.swing.GroupLayout.PREFERRED_SIZE)));

			// jToolBar2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("\u5feb\u6377")));
			// iEButton.setText("\u6d4f\u89c8\u5668");
			// jToolBar2.add(iEButton);

			// personHomeButton.setText("\u4e2a\u4eba\u4e3b\u9875");
			// jToolBar2.add(personHomeButton);

			// mySetButton.setText("\u6211\u7684\u8bbe\u7f6e");
			// jToolBar2.add(mySetButton);

			sysSetButton.setText("jButton5");

			infoButton.setText("\u4fe1\u606f");
			jToolBar3.add(infoButton);

			findButton.setText("\u67e5\u627e");
			jToolBar3.add(findButton);
			addButton.setText("\u6dfb\u52a0");
			addButton.addMouseListener(new MouseListener() {

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
					String inputValue = JOptionPane.showInputDialog(null,
							"请输入好友的密讯号", "添加好友", JOptionPane.PLAIN_MESSAGE);
					if (inputValue != null) {
						defaultLocations.add(new JavaLocation(inputValue,
								"None", "head_boy_01_32.jpg"));
						GenDao.getInstance()
								.executeInsert(
										DBDataSQL.TB_PEERS,
										new String[] {
												DBDataSQL.COL_PEER_PEERID,
												DBDataSQL.COL_PEER_USERNAME,
												DBDataSQL.COL_PEER_FROMPEERID },
										new Object[] {
												inputValue,
												inputValue,
												SConfig.getInstance()
														.getProfile().myPeerBean.PPeerid });
						sampleJList.setListData(defaultLocations.toArray());
					} else {

						JOptionPane.showInternalMessageDialog(null,
								"亲，请输入好友的密讯号!");
					}

					// sampleJList.updateUI();
				}
			});
			jToolBar3.add(addButton);
			jToolBar3.setVisible(false);
			// javax.swing.GroupLayout jPanel2Layout = new
			// javax.swing.GroupLayout(
			// jPanel2);
			// jPanel2.setLayout(jPanel2Layout);
			// jPanel2Layout
			// .setHorizontalGroup(jPanel2Layout.createParallelGroup(
			// javax.swing.GroupLayout.Alignment.LEADING)
			// // .addComponent(jToolBar2,
			// // javax.swing.GroupLayout.DEFAULT_SIZE, 245,
			// // Short.MAX_VALUE)
			// .addGroup(
			// jPanel2Layout
			// .createSequentialGroup()
			// .addComponent(
			// sysSetButton,
			// javax.swing.GroupLayout.PREFERRED_SIZE,
			// 70,
			// javax.swing.GroupLayout.PREFERRED_SIZE)
			// .addPreferredGap(
			// javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			// .addComponent(
			// jToolBar3,
			// javax.swing.GroupLayout.DEFAULT_SIZE,
			// 169, Short.MAX_VALUE)));
			// jPanel2Layout
			// .setVerticalGroup(jPanel2Layout
			// .createParallelGroup(
			// javax.swing.GroupLayout.Alignment.LEADING)
			// .addGroup(
			// jPanel2Layout
			// .createSequentialGroup()
			// // .addComponent(jToolBar2,
			// // javax.swing.GroupLayout.PREFERRED_SIZE,
			// // 48,
			// // javax.swing.GroupLayout.PREFERRED_SIZE)
			// .addPreferredGap(
			// javax.swing.LayoutStyle.ComponentPlacement.RELATED)
			// .addGroup(
			// jPanel2Layout
			// .createParallelGroup(
			// javax.swing.GroupLayout.Alignment.TRAILING)
			// .addComponent(
			// jToolBar3,
			// javax.swing.GroupLayout.DEFAULT_SIZE,
			// 31,
			// Short.MAX_VALUE)
			// .addComponent(
			// sysSetButton,
			// javax.swing.GroupLayout.PREFERRED_SIZE,
			// 31,
			// javax.swing.GroupLayout.PREFERRED_SIZE))));

			javax.swing.GroupLayout friendPanelLayout = new javax.swing.GroupLayout(
					friendPanel);
			friendPanel.setLayout(friendPanelLayout);
			friendPanelLayout.setHorizontalGroup(friendPanelLayout
					.createParallelGroup(
							javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(sampleJList,
							javax.swing.GroupLayout.DEFAULT_SIZE, 240,
							Short.MAX_VALUE));
			friendPanelLayout.setVerticalGroup(friendPanelLayout
					.createParallelGroup(
							javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(sampleJList,
							javax.swing.GroupLayout.DEFAULT_SIZE, 260,
							Short.MAX_VALUE));

			// mainTabbedPanel.addTab("\u597d\u53cb\u5217\u8868", friendPanel);

			mainTabbedPanel
					.addTab("",
							new ImageIcon(
									MainFrame.class
											.getResource("/com/mx/client/image/friend_down.png")),
							friendPanel, "");
			mainTabbedPanel.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					SnapTipTabbedPane tabbedPane = (SnapTipTabbedPane) e
							.getSource();
					int selectedIndex = tabbedPane.getSelectedIndex();

					if (selectedIndex == 0) {
						tabbedPane.setIconAt(
								0,
								new ImageIcon(
										MainFrame.class
												.getResource("/com/mx/client/image/friend_down.png")));
						tabbedPane.setIconAt(
								1,
								new ImageIcon(
										MainFrame.class
												.getResource("/com/mx/client/image/group_up.png")));
						tabbedPane.setIconAt(
								2,
								new ImageIcon(
										MainFrame.class
												.getResource("/com/mx/client/image/add_up.png")));
					}
					if (selectedIndex == 1) {
						tabbedPane.setIconAt(
								0,
								new ImageIcon(
										MainFrame.class
												.getResource("/com/mx/client/image/friend_up.png")));
						tabbedPane.setIconAt(
								1,
								new ImageIcon(
										MainFrame.class
												.getResource("/com/mx/client/image/group_down.png")));
						tabbedPane.setIconAt(
								2,
								new ImageIcon(
										MainFrame.class
												.getResource("/com/mx/client/image/add_up.png")));
					}

					if (selectedIndex == 2) {
						tabbedPane.setIconAt(
								0,
								new ImageIcon(
										MainFrame.class
												.getResource("/com/mx/client/image/friend_up.png")));
						tabbedPane.setIconAt(
								1,
								new ImageIcon(
										MainFrame.class
												.getResource("/com/mx/client/image/group_up.png")));
						tabbedPane.setIconAt(
								2,
								new ImageIcon(
										MainFrame.class
												.getResource("/com/mx/client/image/add_down.png")));
					}
				}

			});
			javax.swing.GroupLayout teamPanelLayout = new javax.swing.GroupLayout(
					teamPanel);
			teamPanel.setLayout(teamPanelLayout);
			teamPanelLayout.setHorizontalGroup(teamPanelLayout
					.createParallelGroup(
							javax.swing.GroupLayout.Alignment.LEADING).addGap(
							0, 240, Short.MAX_VALUE));
			teamPanelLayout.setVerticalGroup(teamPanelLayout
					.createParallelGroup(
							javax.swing.GroupLayout.Alignment.LEADING).addGap(
							0, 260, Short.MAX_VALUE));
			mainTabbedPanel.addTab(
					"",
					new ImageIcon(MainFrame.class
							.getResource("/com/mx/client/image/group_up.png")),
					teamPanel, "");
			// mainTabbedPanel.addTab("", new ImageIcon(MainFrame.class
			// .getResource("/com/mx/client/image/tab14.png")), teamPanel, "");

			javax.swing.GroupLayout zuijinPanelLayout = new javax.swing.GroupLayout(
					zuijinPanel);
			zuijinPanel.setLayout(zuijinPanelLayout);
			zuijinPanelLayout.setHorizontalGroup(zuijinPanelLayout
					.createParallelGroup(
							javax.swing.GroupLayout.Alignment.LEADING).addGap(
							0, 240, Short.MAX_VALUE));
			zuijinPanelLayout.setVerticalGroup(zuijinPanelLayout
					.createParallelGroup(
							javax.swing.GroupLayout.Alignment.LEADING).addGap(
							0, 260, Short.MAX_VALUE));
			mainTabbedPanel.addTab(
					"",
					new ImageIcon(MainFrame.class
							.getResource("/com/mx/client/image/add_up.png")),
					zuijinPanel, "");
			// mainTabbedPanel.addTab("", new ImageIcon(MainFrame.class
			// .getResource("/com/mx/client/image/tab15.png")), zuijinPanel,
			// "");

			javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
					getContentPane());
			getContentPane().setLayout(layout);
			layout.setHorizontalGroup(layout
					.createParallelGroup(
							javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(jPanel1,
							javax.swing.GroupLayout.DEFAULT_SIZE,
							javax.swing.GroupLayout.DEFAULT_SIZE,
							Short.MAX_VALUE)
					// .addComponent(jPanel2,
					// javax.swing.GroupLayout.DEFAULT_SIZE,
					// javax.swing.GroupLayout.DEFAULT_SIZE,
					// Short.MAX_VALUE)
					.addComponent(mainTabbedPanel,
							javax.swing.GroupLayout.DEFAULT_SIZE, 245,
							Short.MAX_VALUE));
			layout.setVerticalGroup(layout
					.createParallelGroup(
							javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(
							layout.createSequentialGroup()
									.addComponent(
											jPanel1,
											javax.swing.GroupLayout.PREFERRED_SIZE,
											118,
											javax.swing.GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
											javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
											mainTabbedPanel,
											javax.swing.GroupLayout.DEFAULT_SIZE,
											289, Short.MAX_VALUE)
									.addPreferredGap(
											javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					// .addComponent(
					// jPanel2,
					// javax.swing.GroupLayout.PREFERRED_SIZE,
					// javax.swing.GroupLayout.DEFAULT_SIZE,
					// javax.swing.GroupLayout.PREFERRED_SIZE)
					));
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					setVisible(false);
				}
			});

			// this.setMainColor(new java.awt.Color(238, 240, 244));
			this.setAlwaysOnTop(true);
			addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					origin.x = arg0.getX();
					origin.y = arg0.getY();
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

				}
			});

			addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseMoved(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseDragged(MouseEvent arg0) {
					// TODO Auto-generated method stub
					Point p = getLocation();

					setLocation(p.x + arg0.getX() - origin.x, p.y + arg0.getY()
							- origin.y);
				}
			});
			NettyClient client = null;
			try {
				client = new NettyClient("https://www.han2011.com/"
						+ "/getmessage/"
						+ SConfig.getInstance().getProfile().getSession()
						+ "/call.xml");
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Thread thread = new Thread(client);
			thread.setDaemon(true);
			thread.start();
			System.out.println("线程启动");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public void initMainTree(List<MsgFriendGroup> groupList){
	// TreeScrollPane treeScrollPane=new TreeScrollPane(groupList);
	// friendTree=treeScrollPane;
	// this.repaint();
	// }
	public void setMainColor(Color bg) {
		//mainTabbedPanel.setBackground(bg);
		friendPanel.setBackground(bg);
		teamPanel.setBackground(bg);
		zuijinPanel.setBackground(bg);
		jPanel1.setBackground(bg);
		jPanel2.setBackground(bg);
		// jToolBar1.setBackground(bg);
		// jToolBar2.setBackground(bg);
		jToolBar3.setBackground(bg);
		// jToolBar1.setForeground(bg);
		// jToolBar2.setForeground(bg);
		jToolBar3.setForeground(bg);

	}

	public static List<MsgFriendGroup> getFriends() {
		return friends;
	}

	public static void setFriends(List<MsgFriendGroup> friends) {
		MainFrame.friends = friends;
	}

	public static MsgUser getOwer() {
		return ower;
	}

	public static void setOwer(MsgUser ower) {
		MainFrame.ower = ower;
	}

	// public TreeScrollPane getFriendTree() {
	// return friendTree;
	// }
	//
	// public void setFriendTree(TreeScrollPane friendTree) {
	// this.friendTree = friendTree;
	// }

	// �������� - �������޸�
	private javax.swing.JButton compnayEmail;
	private javax.swing.JButton compnayHome;
	private javax.swing.JButton compnayOA;
	private javax.swing.JButton findButton;
	private AutoCompletionField findField;
	private javax.swing.JPanel friendPanel;
	// private JListCustomModel friendTree;
	private javax.swing.JLabel headImage;
	private javax.swing.JButton iEButton;
	private javax.swing.JButton infoButton;
	private javax.swing.JButton addButton;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	// private javax.swing.JToolBar jToolBar1;
	private javax.swing.JToolBar jToolBar2;
	private javax.swing.JToolBar jToolBar3;
	private JList sampleJList;
	private SnapTipTabbedPane mainTabbedPanel;
	private javax.swing.JButton mySetButton;
	private javax.swing.JButton news;
	private javax.swing.JButton personHomeButton;
	private javax.swing.JLabel personWord;
	private javax.swing.JComboBox stateComboBox;
	private RButton sysSetButton;
	private javax.swing.JPanel teamPanel;
	private javax.swing.JPanel zuijinPanel;

	// ������������
	public static void main(String[] args) {
		try {
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					// TODO Auto-generated method stub
					final MainFrame frame = new MainFrame();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setUndecorated(true);
					frame.addMouseListener(new MouseListener() {

						public void mouseReleased(MouseEvent arg0) {
							// TODO Auto-generated method stub

						}

						public void mousePressed(MouseEvent arg0) {
							// TODO Auto-generated method stub
							origin.x = arg0.getX();
							origin.y = arg0.getY();
						}

						public void mouseExited(MouseEvent arg0) {
							// TODO Auto-generated method stub

						}

						public void mouseEntered(MouseEvent arg0) {
							// TODO Auto-generated method stub

						}

						public void mouseClicked(MouseEvent arg0) {
							// TODO Auto-generated method stub

						}
					});

					frame.addMouseMotionListener(new MouseMotionListener() {

						public void mouseMoved(MouseEvent arg0) {
							// TODO Auto-generated method stub

						}

						public void mouseDragged(MouseEvent arg0) {
							// TODO Auto-generated method stub
							Point p = frame.getLocation();

							frame.setLocation(p.x + arg0.getX() - origin.x, p.y
									+ arg0.getY() - origin.y);
						}
					});
					frame.setVisible(true);

				}
			});

			// System.out.println(EIMMainFrame.class.getResource("/com/csu/client/resourse/ui/compnayHome.gif").toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class ValueReporter implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			if (!e.getValueIsAdjusting())
				System.out.println(((JavaLocation) sampleJList
						.getSelectedValue()).getPeerId());

		}
	}

	void tray() {
		mainTray = SystemTray.getSystemTray(); // 获得本操作系统托盘的实例
		ImageIcon icon = new ImageIcon(
				Login.class
						.getResource("/com/mx/client/image/ic_launcher_20x20.png")); // 将要显示到托盘中的图标
		PopupMenu pop = new PopupMenu(); // 构造一个右键弹出式菜单
		MenuItem show = new MenuItem("打开程序(s)");
		MenuItem exit = new MenuItem("退出程序(x)");
		pop.add(show);
		pop.add(exit);
		// 添加鼠标监听器，当鼠标在托盘图标上双击时，默认显示窗口
		maintrayIcon = new TrayIcon(icon.getImage(), "MX", pop);
		maintrayIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) // 鼠标双击
				{
					mainTray.remove(maintrayIcon); // 从系统的托盘实例中移除托盘图标
					setExtendedState(JFrame.NORMAL);
					setVisible(true); // 显示窗口
					toFront();
				}
			}
		});
		show.addActionListener(new ActionListener() // 点击“显示窗口”菜单后将窗口显示出来
		{
			public void actionPerformed(ActionEvent e) {
				// tray.remove(trayIcon); // 从系统的托盘实例中移除托盘图标
				setExtendedState(JFrame.NORMAL);
				setVisible(true); // 显示窗口
				toFront();
			}
		});
		exit.addActionListener(new ActionListener() // 点击“退出演示”菜单后退出程序
		{
			public void actionPerformed(ActionEvent e) {
				System.exit(0); // 退出程序
			}
		});
	}

};
