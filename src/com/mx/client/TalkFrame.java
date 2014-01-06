package com.mx.client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.text.AbstractDocument.Content;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StringContent;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleConstants.CharacterConstants;
import javax.swing.text.StyleConstants.FontConstants;
import javax.swing.text.StyledDocument;
import org.h2.table.Table;

import com.mx.clent.vo.AnMessageBean;
import com.mx.clent.vo.MsgUser;
import com.mx.client.db.DBDataSQL;
import com.mx.client.db.GenDao;
import com.mx.client.msg.ShockMsg;
import com.mx.client.webtools.ConnectionUtils;
import com.mx.client.webtools.Cryptor;
import com.mx.client.webtools.CryptorException;
import com.mx.client.webtools.RSAEncryptor;
import com.mx.client.webtools.SConfig;
import com.sun.awt.AWTUtilities;
import com.sun.xml.internal.ws.resources.SenderMessages;

public class TalkFrame extends JFrame implements Runnable {

	public com.mx.clent.vo.MsgUser friend; // 聊天的对方的QQ
	public static com.mx.clent.vo.MsgUser owerUser;
	private TalkFrame qqTalkFrame;
	private boolean isFileSndOrGeting = false;
	public static boolean isViedoing = false;
	private Timer timer = new Timer();
	Point origin = new Point();// 获取当前鼠标的位置
	private JLabel lblNewLabel_2;// 窗口最大化
	private JLabel label;// 关闭窗口
	private JLabel label_2;// 窗口最小化
	private JLabel label_7;
	private Cryptor mCryptor;
	private MessageLocationCollection collection = null;
	private JTextPane textPane;// 显示聊天窗口
	private FontAttrib userInfoFontAttrib = new FontAttrib();// 显示字体设置
	private FontAttrib textFontAttrib = new FontAttrib();// 发送字体设置
	private StyledDocument doc = null; // 非常重要插入文字样式就靠它了
	private JTextPane textPane_1;// 发送消息
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private JList sampleJList;
	private JLabel label_1;
	private List<PicInfo> myPicInfo = new LinkedList<PicInfo>();
	private List<PicInfo> receivdPicInfo = new LinkedList<PicInfo>();
	private StyledDocument docMsg = null;
	private FontAndText myFont = null;
	/* 表情框 */
	private PicsJWindow picWindow;
	int pos1, pos2;

	/** Creates new form QQTalkFrame */
	public TalkFrame(MsgUser friend) {
		this.friend = friend;
		initComponents();
		qqTalkFrame = this;
	}

	public void changeTile(String info) {
		this.setTitle(info);
	}

	private void initComponents() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 776, 532);
		setUndecorated(true);
		// getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		getContentPane().setLayout(null);
		picWindow = new PicsJWindow(this);
		JPanel p = new JPanel();
		p.setBorder(new EmptyBorder(5, 5, 6, 5));
		// p.setBackground(Color.blue);
		p.setLayout(null);
		p.setOpaque(true);
		// setContentPane(p);
		JPanel jPanel = new JPanel();
		jPanel.setBounds(0, 0, 200, 58);
		jPanel.setLayout(null);
		jPanel.setOpaque(true);
		jPanel.setBackground(new Color(83, 171, 204));
		add(jPanel);
		JTextField jTextField = new JTextField(150);

		jTextField.setText("查找");
		jTextField.setBounds(30, 15, 150, 30);
		jTextField.setHorizontalAlignment(SwingConstants.CENTER);
		jPanel.add(jTextField);
		this.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				TalkFrame.this.picWindow.dispose();

			}

			@Override
			public void componentMoved(ComponentEvent e) {
				TalkFrame.this.picWindow.dispose();
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				TalkFrame.this.picWindow.dispose();
				timer.cancel();
				dispose();
			}

		});
		collection = new MessageLocationCollection(GenDao.getInstance()
				.getMessageArrayValue(
						SConfig.getInstance().getProfile().myPeerBean.PPeerid));
		MessageModel listModel = new MessageModel(collection);
		sampleJList = new JList(listModel);
		sampleJList.setCellRenderer(new MessageRender());
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
				if (e.getClickCount() == 2 && e.getButton() != 3) {
					String peerid = ((MessageCollection) sampleJList
							.getSelectedValue()).getM_peerid();
					String group = ((MessageCollection) sampleJList
							.getSelectedValue()).getIsGroup();
					
					friend = new MsgUser();
					friend.setUserID(peerid);
					friend.setUserName(peerid);
					friend.setGroup(group);
					if (group.equals("1")) {
						String g = ((MessageCollection) sampleJList
								.getSelectedValue()).getGroup_user();
						Hashtable<String, Object> condition = new Hashtable<String, Object>();
						condition.put(DBDataSQL.COL_PROOM_ROOMNAME, peerid);
						friend.setUserName(g);
						String key = GenDao.getInstance().getValue(
								DBDataSQL.TB_ROOMS,
								new String[] { DBDataSQL.COL_PROOM_AESKEY },
								DBDataSQL.COL_PROOM_AESKEY, condition);
						System.out.println("key-->" + key);
						try {
							mCryptor = new Cryptor(key);
						} catch (CryptorException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
					label_7.setText(friend.getUserID());
					label_7.setFont(new Font("微软雅黑", Font.BOLD, 14));
					textPane.setText("");
				}
			}
		});
		Font displayFont = new Font("Serif", Font.BOLD, 18);
		sampleJList.setFont(displayFont);
		sampleJList.setBounds(0, 57, 200, 475);
		add(sampleJList);
		JPanel talkPanel = new JPanel();
		talkPanel.setOpaque(true);
		talkPanel.setBounds(200, 0, 576, 542);
		talkPanel.setBackground(Color.WHITE);
		add(talkPanel);

		JPanel panel = new JPanel();
		panel.setOpaque(true);
		panel.setBackground(new Color(83, 171, 204));
		textPane = new JTextPane();
		textPane.setEditable(false);
		// textPane.setText("\u6D4B\u8BD5");
		textPane.setAutoscrolls(true);
		userInfoFontAttrib.setSize(14);
		userInfoFontAttrib.setColor(new Color(0, 0, 255));
		userInfoFontAttrib.setName("微软雅黑");
		doc = textPane.getStyledDocument();
		JPanel panel_1 = new JPanel();

		textPane_1 = new JTextPane();// 发送消息
		textPane_1.setText("\u804A\u59292");
		textFontAttrib.setSize(22);
		textFontAttrib.setColor(new Color(255, 0, 0));
		textFontAttrib.setName("微软雅黑");
		textFontAttrib.setStyle(1);
		textPane_1.setCharacterAttributes(textFontAttrib.getAttrSet(), true);
		docMsg = textPane_1.getStyledDocument();
		jScrollPane1 = new JScrollPane();
		jScrollPane2 = new JScrollPane();
		jScrollPane1.setViewportView(textPane);
		jScrollPane2.setViewportView(textPane_1);
		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(true);
		panel_2.setBackground(Color.WHITE);
		JButton button = new JButton("\u5173\u95ED");
		button.setOpaque(true);
		button.setBackground(Color.WHITE);
		button.addMouseListener(new MouseListener() {

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
				dispose();
				timer.cancel();
				// timer.cancel();
			}
		});
		JButton button_1 = new JButton("\u53D1\u9001");
		button_1.addMouseListener(new MouseListener() {

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

				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						String text = "";
						try {
							text = buildPicInfo();
						} catch (BadLocationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// TODO Auto-generated method stub
						HashMap<String, String> map = sendMessage(text);

						showMsg(SConfig.getInstance().getProfile().myPeerBean.PPeerid,
								text);

					}
				});
			}
		});
		button_1.setOpaque(true);
		button_1.setBackground(Color.WHITE);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2
				.setHorizontalGroup(gl_panel_2.createParallelGroup(
						Alignment.CENTER).addGroup(
						Alignment.CENTER,
						gl_panel_2.createSequentialGroup().addGap(420)
								.addComponent(button).addGap(5)
								.addComponent(button_1)));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(
				Alignment.CENTER).addGroup(
				gl_panel_2
						.createSequentialGroup()
						.addGroup(
								gl_panel_2
										.createParallelGroup(Alignment.CENTER)

										.addComponent(button)
										.addComponent(button_1))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		panel_2.setLayout(gl_panel_2);

		GroupLayout gl_talkPanel = new GroupLayout(talkPanel);
		gl_talkPanel.setHorizontalGroup(gl_talkPanel
				.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_1, Alignment.TRAILING,
						GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
				.addComponent(panel_2, Alignment.TRAILING,
						GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
				.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 576,
						Short.MAX_VALUE)
				.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 576,
						Short.MAX_VALUE)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 576,
						Short.MAX_VALUE));
		gl_talkPanel.setVerticalGroup(gl_talkPanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_talkPanel
						.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 60,
								GroupLayout.PREFERRED_SIZE)

						.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE,
								296, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 30,
								GroupLayout.PREFERRED_SIZE)

						.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE,
								163, Short.MAX_VALUE)

						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 20,
								GroupLayout.PREFERRED_SIZE).addContainerGap()));

		label_1 = new JLabel("");// 表情
		label_1.setIcon(new ImageIcon(TalkFrame.class
				.getResource("/com/mx/client/image/img.png")));
		label_1.addMouseListener(new MouseListener() {

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
				// picWindow.setVisible(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				picWindow.setVisible(true);
			}
		});
		JLabel label_3 = new JLabel("");// 剪切
		label_3.setIcon(new ImageIcon(TalkFrame.class
				.getResource("/com/mx/client/image/cut.png")));
		JLabel label_4 = new JLabel("");// 震动
		label_4.setIcon(new ImageIcon(TalkFrame.class
				.getResource("/com/mx/client/image/shake.png")));
		JLabel label_5 = new JLabel("");// 图片
		label_5.setIcon(new ImageIcon(TalkFrame.class
				.getResource("/com/mx/client/image/img.png")));
		JLabel label_6 = new JLabel("");// 图片
		label_6.setIcon(new ImageIcon(TalkFrame.class
				.getResource("/com/mx/client/image/log.png")));
		panel_1.setOpaque(true);
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

		panel_1.setBackground(Color.WHITE);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 20,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(label_3)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 30,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(label_5)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(label_6)
						.addContainerGap(504, Short.MAX_VALUE)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createParallelGroup(Alignment.CENTER)
						.addComponent(label_1, GroupLayout.DEFAULT_SIZE, 20,
								Short.MAX_VALUE)
						.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 20,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 20,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 20,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(label_6, GroupLayout.PREFERRED_SIZE, 20,
								GroupLayout.PREFERRED_SIZE)));
		panel_1.setLayout(gl_panel_1);

		JLabel lblNewLabel_1 = new JLabel("");// 头像
		ImageIcon icon = new ImageIcon(this.getClass().getResource(
				"/com/mx/client/headImage/portrait.png"));
		Image image = icon.getImage().getScaledInstance(icon.getIconWidth(),
				icon.getIconHeight(), Image.SCALE_SMOOTH);
		icon.setImage(image);

		int borderWidth = 1;
		int spaceAroundIcon = 0;

		Color borderColor = Color.WHITE;

		BufferedImage bi = new BufferedImage(icon.getIconWidth()
				+ (2 * borderWidth + 2 * spaceAroundIcon), icon.getIconHeight()
				+ (2 * borderWidth + 2 * spaceAroundIcon),
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = bi.createGraphics();
		g.setColor(borderColor);
		g.drawImage(icon.getImage(), borderWidth + spaceAroundIcon, borderWidth
				+ spaceAroundIcon, null);

		BasicStroke stroke = new BasicStroke(5); // 5 pixels wide (thickness of
													// the border)
		g.setStroke(stroke);

		g.drawRect(0, 0, bi.getWidth() - 1, bi.getHeight() - 1);
		g.dispose();
		// label = new JLabel(new ImageIcon(bi), JLabel.LEFT);
		label_7 = new JLabel("\u7528\u6237\u540D");
		lblNewLabel_1.setIcon(new ImageIcon(bi));
		// lblNewLabel_1.setIcon(null);
		lblNewLabel_2 = new JLabel("");// 最大化窗口
		lblNewLabel_2.setIcon(new ImageIcon(TalkFrame.class
				.getResource("/com/mx/client/image/max_01.png")));
		lblNewLabel_2.addMouseListener(new MouseListener() {

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
				lblNewLabel_2.setIcon(new ImageIcon(TalkFrame.class
						.getResource("/com/mx/client/image/max_01.png")));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		lblNewLabel_2.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				lblNewLabel_2.setIcon(new ImageIcon(TalkFrame.class
						.getResource("/com/mx/client/image/max_02.png")));
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		label = new JLabel("");// 关闭窗口
		label.setIcon(new ImageIcon(TalkFrame.class
				.getResource("/com/mx/client/image/close_01.png")));
		label.addMouseListener(new MouseListener() {

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
				label.setIcon(new ImageIcon(TalkFrame.class
						.getResource("/com/mx/client/image/close_01.png")));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				// timer.cancel();
			}
		});
		label.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				label.setIcon(new ImageIcon(TalkFrame.class
						.getResource("/com/mx/client/image/close_02.png")));

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(TalkFrame.class
				.getResource("/com/mx/client/image/small_01.png")));
		label_2.addMouseListener(new MouseListener() {

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
				label_2.setIcon(new ImageIcon(TalkFrame.class
						.getResource("/com/mx/client/image/small_01.png")));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				// timer.cancel();
			}
		});
		label_2.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				label_2.setIcon(new ImageIcon(TalkFrame.class
						.getResource("/com/mx/client/image/small_02.png")));
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addGap(33)
						.addComponent(lblNewLabel_1,
								GroupLayout.PREFERRED_SIZE, 50,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(label_7).addGap(368)
						.addComponent(label_2)

						.addComponent(lblNewLabel_2)

						.addComponent(label)));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING)
												.addGroup(
														Alignment.TRAILING,
														gl_panel.createSequentialGroup()
																.addGap(5)
																.addComponent(
																		lblNewLabel_1,
																		GroupLayout.DEFAULT_SIZE,
																		48,
																		Short.MAX_VALUE))
												.addGroup(
														Alignment.TRAILING,
														gl_panel.createSequentialGroup()
																.addGap(5)
																.addComponent(
																		label_7,
																		GroupLayout.DEFAULT_SIZE,
																		48,
																		Short.MAX_VALUE))
												.addGroup(
														gl_panel.createParallelGroup(
																Alignment.LEADING)
																.addComponent(
																		label)
																.addComponent(
																		lblNewLabel_2)
																.addComponent(
																		label_2)))
								.addContainerGap()));
		panel.setLayout(gl_panel);
		talkPanel.setLayout(gl_talkPanel);
		// p.doLayout();
		// Shape shape = null;
		// shape = new RoundRectangle2D.Double(0, 0, getWidth(),
		// getHeight(), 10D, 10D);
		// AWTUtilities.setWindowShape(this, shape);

		// JList list = new JList();

		// getContentPane().add(list, BorderLayout.WEST);
		// getContentPane().setLayout(new BorderLayout(0, 0));
		// contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel = new JLabel("\u5BC6\u8BAF");
		// lblNewLabel.setForeground(new Color(0, 51, 255));
		lblNewLabel.setBackground(new Color(75, 136, 191));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		this.setAlwaysOnTop(true);
		// TODO Auto-generated method stub

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
		// changeTile("与 " + friend.getUserName() + "  交谈中...");
		// intSubstance();
		// Image icon=ResourcesManagement.getImage("icon/message.gif");
		// this.setIconImage(icon);
		// glassBox1 = new GlassBox();
		// jPanel7 = new javax.swing.JPanel();
		// jPanel9 = new javax.swing.JPanel();
		// jScrollPane1 = new javax.swing.JScrollPane();
		// personWord_TextPane = new javax.swing.JTextPane();
		// headIamge_jLabel = new javax.swing.JLabel();
		// jToolBar1 = new javax.swing.JToolBar();
		// font_jButton = new javax.swing.JButton();
		// face_jButton = new javax.swing.JButton();
		// shock_Button = new javax.swing.JButton();
		// sendImage_jButton = new javax.swing.JButton();
		// fenMusic_jLabel = new javax.swing.JLabel();
		// talkRecord_Button = new javax.swing.JButton();
		// jPanel10 = new javax.swing.JPanel();
		// jScrollPane3 = new javax.swing.JScrollPane();
		// sendMsg_jTextPane = new javax.swing.JTextPane();
		// jPanel11 = new javax.swing.JPanel();
		// jScrollPane2 = new javax.swing.JScrollPane();
		// showMsg_jTextPane = new javax.swing.JTextPane();
		// exit_Button = new javax.swing.JButton();
		// send_Button = new javax.swing.JButton();
		// setKey_Button = new javax.swing.JButton();
		// jTabbedPane2 = new SnapTipTabbedPane();
		// jPanel6 = new javax.swing.JPanel();
		// friendXiuXiu_jLabel = new javax.swing.JLabel();
		// jLabel4 = new javax.swing.JLabel();
		// jPanel12 = new javax.swing.JPanel();
		// jScrollPane4 = new javax.swing.JScrollPane();
		// talkRecord_jTextPane = new javax.swing.JTextPane();
		// jPanel5 = new javax.swing.JPanel();
		// jTabbedPane1 = new SnapTipTabbedPane();
		// jPanel1 = new javax.swing.JPanel();
		// snedFile_Button = new javax.swing.JButton();
		// voice_Button = new javax.swing.JButton();
		// video_Button = new javax.swing.JButton();
		// moblie_jButton = new javax.swing.JButton();
		// jPanel2 = new javax.swing.JPanel();
		// qqMusic_Button = new javax.swing.JButton();
		// qqSpace_jButton = new javax.swing.JButton();
		// qqGame_jButton = new javax.swing.JButton();
		// jPanel3 = new javax.swing.JPanel();
		// remoteHlep_Button = new javax.swing.JButton();
		// findShare_jButton = new javax.swing.JButton();
		// jPanel4 = new javax.swing.JPanel();
		// intoBlacklist_Button = new javax.swing.JButton();
		// deleteFriend_Button = new javax.swing.JButton();
		// layoutSet_Button = new javax.swing.JButton();
		//
		// //jPanel7.setBackground(new java.awt.Color(51, 204, 255));
		// //jPanel9.setBackground(new java.awt.Color(51, 204, 255));
		// personWord_TextPane.setBackground(new java.awt.Color(51, 204, 255));
		// personWord_TextPane.setBorder(javax.swing.BorderFactory
		// .createEmptyBorder(1, 1, 1, 1));
		//
		// personWord_TextPane.setText(friend.getPersonWord());
		//
		// personWord_TextPane
		// .setSelectionColor(new java.awt.Color(102, 204, 255));
		// jScrollPane1.setViewportView(personWord_TextPane);
		//
		// String headNum = (String) friend.getAttribute("headImage");
		// // ImageIcon
		// //
		// headIamge=ResourcesManagement.getImageIcon("head/"+headNum+".gif",43,43);
		// // headIamge_jLabel.setIcon(headIamge);
		//
		// // javax.swing.GroupLayout jPanel9Layout = new
		// javax.swing.GroupLayout(
		// // jPanel9);
		// // jPanel9.setLayout(jPanel9Layout);
		// // jPanel9Layout
		// // .setHorizontalGroup(jPanel9Layout
		// // .createParallelGroup(
		// // javax.swing.GroupLayout.Alignment.LEADING)
		// // .addGroup(
		// // jPanel9Layout
		// // .createSequentialGroup()
		// // .addComponent(headIamge_jLabel)
		// // .addPreferredGap(
		// // javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		// // .addComponent(
		// // jScrollPane1,
		// // javax.swing.GroupLayout.DEFAULT_SIZE,
		// // 393, Short.MAX_VALUE)));
		// // jPanel9Layout.setVerticalGroup(jPanel9Layout
		// // .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		// // .addComponent(jScrollPane1,
		// // javax.swing.GroupLayout.DEFAULT_SIZE, 43,
		// // Short.MAX_VALUE)
		// // .addComponent(headIamge_jLabel,
		// // javax.swing.GroupLayout.DEFAULT_SIZE, 43,
		// // Short.MAX_VALUE));
		//
		// jToolBar1.setBackground(new java.awt.Color(51, 204, 255));
		// jToolBar1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1,
		// 1, 1));
		// font_jButton.setBackground(new java.awt.Color(51, 204, 255));
		//
		// // Image
		// fontIamge=ResourcesManagement.getImage("icon/font.gif",16,16);
		// // font_jButton.setIcon(new javax.swing.ImageIcon(fontIamge));
		// font_jButton.addMouseListener(new java.awt.event.MouseAdapter() {
		// public void mouseClicked(java.awt.event.MouseEvent evt) {
		// font_jButtonMouseClicked(evt);
		// }
		// });
		//
		// jToolBar1.add(font_jButton);
		//
		// face_jButton.setBackground(new java.awt.Color(51, 204, 255));
		// // Image
		// // face_Iamge=ResourcesManagement.getImage("icon/smiley.gif",16,16);
		// // face_jButton.setIcon(new javax.swing.ImageIcon(face_Iamge));
		// face_jButton.addMouseListener(new java.awt.event.MouseAdapter() {
		// public void mouseClicked(java.awt.event.MouseEvent evt) {
		// face_jButtonMouseClicked(evt);
		// }
		// });
		// jToolBar1.add(face_jButton);
		// shock_Button.setBackground(new java.awt.Color(51, 204, 255));
		// // Image
		// //
		// shock_Iamge=ResourcesManagement.getImage("icon/zhengdong.jpg",16,16);
		// // shock_Button.setIcon(new javax.swing.ImageIcon(shock_Iamge));
		// shock_Button.addMouseListener(new java.awt.event.MouseAdapter() {
		// public void mouseClicked(java.awt.event.MouseEvent evt) {
		// shock_ButtonMouseClicked(evt);
		// }
		// });
		//
		// jToolBar1.add(shock_Button);
		//
		// sendImage_jButton.setBackground(new java.awt.Color(51, 204, 255));
		// // Image
		// //
		// sends_Iamge=ResourcesManagement.getImage("icon/import_24.gif",16,16);
		// // sendImage_jButton.setIcon(new javax.swing.ImageIcon(sends_Iamge));
		// sendImage_jButton.addMouseListener(new java.awt.event.MouseAdapter()
		// {
		// public void mouseClicked(java.awt.event.MouseEvent evt) {
		// sendImage_jButtonMouseClicked(evt);
		// }
		// });
		//
		// jToolBar1.add(sendImage_jButton);
		// // Image
		// //
		// fenMusic_Iamge=ResourcesManagement.getImage("icon/mymultimedia.gif",16,16);
		// // fenMusic_jLabel.setIcon(new
		// javax.swing.ImageIcon(fenMusic_Iamge));
		// jToolBar1.add(fenMusic_jLabel);
		//
		// talkRecord_Button.setBackground(new java.awt.Color(51, 204, 255));
		// talkRecord_Button.setText("\u804a\u5929\u8bb0\u5f55");
		// talkRecord_Button.addMouseListener(new java.awt.event.MouseAdapter()
		// {
		// public void mouseClicked(java.awt.event.MouseEvent evt) {
		// talkRecord_ButtonMouseClicked(evt);
		// }
		// });
		//
		// jToolBar1.add(talkRecord_Button);
		// showMsg_jTextPane.setEditable(false);
		// jScrollPane3.setViewportView(sendMsg_jTextPane);
		//
		// javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(
		// jPanel10);
		// jPanel10.setLayout(jPanel10Layout);
		// jPanel10Layout.setHorizontalGroup(jPanel10Layout.createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING).addComponent(
		// jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 437,
		// Short.MAX_VALUE));
		// jPanel10Layout.setVerticalGroup(jPanel10Layout.createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING).addComponent(
		// jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 84,
		// Short.MAX_VALUE));
		//
		// jScrollPane2.setViewportView(showMsg_jTextPane);
		//
		// javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(
		// jPanel11);
		// jPanel11.setLayout(jPanel11Layout);
		// jPanel11Layout.setHorizontalGroup(jPanel11Layout.createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING).addComponent(
		// jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 437,
		// Short.MAX_VALUE));
		// jPanel11Layout.setVerticalGroup(jPanel11Layout.createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING).addComponent(
		// jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 290,
		// Short.MAX_VALUE));
		//
		// exit_Button.setText("\u5173\u95ed");
		// exit_Button.addMouseListener(new java.awt.event.MouseAdapter() {
		// public void mouseClicked(java.awt.event.MouseEvent evt) {
		// exit_ButtonMouseClicked(evt);
		// }
		// });
		//
		// send_Button.setText("\u53d1\u9001");
		// send_Button.addMouseListener(new java.awt.event.MouseAdapter() {
		// public void mouseClicked(java.awt.event.MouseEvent evt) {
		// send_ButtonMouseClicked(evt);
		// }
		// });
		//
		// setKey_Button.setText("1");
		//
		// javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(
		// jPanel7);
		// jPanel7.setLayout(jPanel7Layout);
		// jPanel7Layout
		// .setHorizontalGroup(jPanel7Layout
		// .createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING)
		// .addGroup(
		// javax.swing.GroupLayout.Alignment.TRAILING,
		// jPanel7Layout
		// .createSequentialGroup()
		// .addContainerGap(296, Short.MAX_VALUE)
		// .addComponent(exit_Button)
		// .addPreferredGap(
		// javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		// .addComponent(send_Button)
		// .addPreferredGap(
		// javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		// .addComponent(
		// setKey_Button,
		// javax.swing.GroupLayout.PREFERRED_SIZE,
		// 15,
		// javax.swing.GroupLayout.PREFERRED_SIZE))
		// .addComponent(jPanel11,
		// javax.swing.GroupLayout.Alignment.TRAILING,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// Short.MAX_VALUE)
		// .addComponent(jPanel10,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// Short.MAX_VALUE)
		// .addComponent(jPanel9,
		// javax.swing.GroupLayout.Alignment.TRAILING,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// Short.MAX_VALUE)
		// .addGroup(
		// jPanel7Layout
		// .createSequentialGroup()
		// .addComponent(
		// jToolBar1,
		// javax.swing.GroupLayout.PREFERRED_SIZE,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// javax.swing.GroupLayout.PREFERRED_SIZE)
		// .addContainerGap(260, Short.MAX_VALUE)));
		// jPanel7Layout
		// .setVerticalGroup(jPanel7Layout
		// .createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING)
		// .addGroup(
		// jPanel7Layout
		// .createSequentialGroup()
		// .addComponent(
		// jPanel9,
		// javax.swing.GroupLayout.PREFERRED_SIZE,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// javax.swing.GroupLayout.PREFERRED_SIZE)
		// .addPreferredGap(
		// javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		// .addComponent(
		// jPanel11,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// Short.MAX_VALUE)
		// .addPreferredGap(
		// javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		// .addComponent(
		// jToolBar1,
		// javax.swing.GroupLayout.PREFERRED_SIZE,
		// 25,
		// javax.swing.GroupLayout.PREFERRED_SIZE)
		// .addPreferredGap(
		// javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		// .addComponent(
		// jPanel10,
		// javax.swing.GroupLayout.PREFERRED_SIZE,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// javax.swing.GroupLayout.PREFERRED_SIZE)
		// .addPreferredGap(
		// javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		// .addGroup(
		// jPanel7Layout
		// .createParallelGroup(
		// javax.swing.GroupLayout.Alignment.BASELINE)
		// .addComponent(
		// setKey_Button)
		// .addComponent(
		// send_Button)
		// .addComponent(
		// exit_Button))));
		//
		// jPanel6.setBackground(new java.awt.Color(51, 204, 255));
		// // ImageIcon
		// // friendXiuXiuIcon=ResourcesManagement.getImageIcon("xiuxiu/4.gif");
		// // friendXiuXiu_jLabel.setIcon(friendXiuXiuIcon);
		//
		// // ImageIcon
		// // myXiuXiuIcon=ResourcesManagement.getImageIcon("xiuxiu/5.GIF");
		// // jLabel4.setIcon(myXiuXiuIcon);
		//
		// javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(
		// jPanel6);
		// jPanel6.setLayout(jPanel6Layout);
		// jPanel6Layout.setHorizontalGroup(jPanel6Layout
		// .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		// .addComponent(jLabel4,
		// javax.swing.GroupLayout.Alignment.TRAILING,
		// javax.swing.GroupLayout.PREFERRED_SIZE, 150,
		// Short.MAX_VALUE)
		// .addComponent(friendXiuXiu_jLabel,
		// javax.swing.GroupLayout.DEFAULT_SIZE, 150,
		// Short.MAX_VALUE));
		// jPanel6Layout
		// .setVerticalGroup(jPanel6Layout
		// .createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING)
		// .addGroup(
		// jPanel6Layout
		// .createSequentialGroup()
		// .addComponent(
		// friendXiuXiu_jLabel,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// 227, Short.MAX_VALUE)
		// .addPreferredGap(
		// javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		// .addComponent(
		// jLabel4,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// 227, Short.MAX_VALUE)));
		// // jTabbedPane2.addTab("\u4e2a\u4eba\u79c0\u79c0", jPanel6);
		//
		// jScrollPane4.setViewportView(talkRecord_jTextPane);
		//
		// javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(
		// jPanel12);
		// jPanel12.setLayout(jPanel12Layout);
		// jPanel12Layout.setHorizontalGroup(jPanel12Layout.createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING).addComponent(
		// jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 150,
		// Short.MAX_VALUE));
		// jPanel12Layout.setVerticalGroup(jPanel12Layout.createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING).addComponent(
		// jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING,
		// javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE));
		// //jTabbedPane2.addTab("\u804a\u5929\u8bb0\u5f55", jPanel12);
		//
		// jPanel5.setBackground(new java.awt.Color(51, 204, 255));
		// jTabbedPane1.setBackground(new java.awt.Color(51, 204, 255));
		// jPanel1.setBackground(new java.awt.Color(51, 204, 255));
		// snedFile_Button.setBackground(new java.awt.Color(51, 204, 255));
		// // ImageIcon sendFileIcon=
		// // ResourcesManagement.getImageIcon("icon/export_24.gif");
		// // snedFile_Button.setIcon(sendFileIcon);
		//
		// voice_Button.setBackground(new java.awt.Color(51, 204, 255));
		// // ImageIcon voiceIcon=
		// // ResourcesManagement.getImageIcon("ui/microphone4.png");
		// // voice_Button.setIcon(voiceIcon);
		// voice_Button.addMouseListener(new java.awt.event.MouseAdapter() {
		// public void mouseClicked(java.awt.event.MouseEvent evt) {
		// voice_ButtonMouseClicked(evt);
		// }
		// });
		// javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
		// jPanel1);
		// jPanel1.setLayout(jPanel1Layout);
		// jPanel1Layout
		// .setHorizontalGroup(jPanel1Layout
		// .createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING)
		// .addGroup(
		// jPanel1Layout
		// .createSequentialGroup()
		// .addContainerGap()
		// // .addComponent(
		// // moblie_jButton,
		// // javax.swing.GroupLayout.PREFERRED_SIZE,
		// // 33,
		// // javax.swing.GroupLayout.PREFERRED_SIZE)
		// // .addPreferredGap(
		// // javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		// // .addComponent(
		// // video_Button,
		// // javax.swing.GroupLayout.PREFERRED_SIZE,
		// // 33,
		// // javax.swing.GroupLayout.PREFERRED_SIZE)
		// // .addPreferredGap(
		// // javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		// // .addComponent(
		// // voice_Button,
		// // javax.swing.GroupLayout.PREFERRED_SIZE,
		// // 34,
		// // javax.swing.GroupLayout.PREFERRED_SIZE)
		// // .addPreferredGap(
		// // javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		// // .addComponent(
		// // snedFile_Button,
		// // javax.swing.GroupLayout.PREFERRED_SIZE,
		// // 33,
		// // javax.swing.GroupLayout.PREFERRED_SIZE)
		// .addContainerGap(432, Short.MAX_VALUE)));
		// jPanel1Layout
		// .setVerticalGroup(jPanel1Layout
		// .createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING)
		// .addGroup(
		// jPanel1Layout
		// .createSequentialGroup()
		// .addGroup(
		// jPanel1Layout
		// .createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING)
		// // .addComponent(
		// // moblie_jButton,
		// // javax.swing.GroupLayout.DEFAULT_SIZE,
		// // javax.swing.GroupLayout.DEFAULT_SIZE,
		// // Short.MAX_VALUE)
		// // .addComponent(
		// // video_Button,
		// // javax.swing.GroupLayout.DEFAULT_SIZE,
		// // javax.swing.GroupLayout.DEFAULT_SIZE,
		// // Short.MAX_VALUE)
		// // .addComponent(
		// // voice_Button,
		// // javax.swing.GroupLayout.PREFERRED_SIZE,
		// // 33,
		// // Short.MAX_VALUE)
		// // .addComponent(
		// // snedFile_Button,
		// // javax.swing.GroupLayout.DEFAULT_SIZE,
		// // javax.swing.GroupLayout.DEFAULT_SIZE,
		// // Short.MAX_VALUE)
		// )
		// .addContainerGap()));
		// //jTabbedPane1.addTab("\u804a  \u5929", jPanel1);
		//
		// jPanel2.setBackground(new java.awt.Color(51, 204, 255));
		// qqMusic_Button.setBackground(new java.awt.Color(51, 204, 255));
		// // ImageIcon qqMusicIcon=
		// // ResourcesManagement.getImageIcon("ui/Music.png");
		// // qqMusic_Button.setIcon(qqMusicIcon);
		//
		// qqMusic_Button.addMouseListener(new java.awt.event.MouseAdapter() {
		// public void mouseClicked(java.awt.event.MouseEvent evt) {
		// qqMusic_ButtonMouseClicked(evt);
		// }
		// });
		//
		// qqSpace_jButton.setBackground(new java.awt.Color(51, 204, 255));
		// // ImageIcon qqSpaceIcon=
		// // ResourcesManagement.getImageIcon("ui/Home.png");
		// // qqSpace_jButton.setIcon(qqSpaceIcon);
		// qqSpace_jButton.addMouseListener(new java.awt.event.MouseAdapter() {
		// public void mouseClicked(java.awt.event.MouseEvent evt) {
		// qqSpace_jButtonMouseClicked(evt);
		// }
		// });
		//
		// qqGame_jButton.setBackground(new java.awt.Color(51, 204, 255));
		// // ImageIcon qqGameIcon=
		// // ResourcesManagement.getImageIcon("ui/Games.png");
		// // qqGame_jButton.setIcon(qqGameIcon);
		// qqGame_jButton.addMouseListener(new java.awt.event.MouseAdapter() {
		// public void mouseClicked(java.awt.event.MouseEvent evt) {
		// qqGame_jButtonMouseClicked(evt);
		// }
		// });
		//
		// javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
		// jPanel2);
		// jPanel2.setLayout(jPanel2Layout);
		// jPanel2Layout
		// .setHorizontalGroup(jPanel2Layout
		// .createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING)
		// .addGroup(
		// jPanel2Layout
		// .createSequentialGroup()
		// .addComponent(
		// qqGame_jButton,
		// javax.swing.GroupLayout.PREFERRED_SIZE,
		// 34,
		// javax.swing.GroupLayout.PREFERRED_SIZE)
		// .addPreferredGap(
		// javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		// .addComponent(
		// qqSpace_jButton,
		// javax.swing.GroupLayout.PREFERRED_SIZE,
		// 34,
		// javax.swing.GroupLayout.PREFERRED_SIZE)
		// .addPreferredGap(
		// javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		// .addComponent(
		// qqMusic_Button,
		// javax.swing.GroupLayout.PREFERRED_SIZE,
		// 38,
		// javax.swing.GroupLayout.PREFERRED_SIZE)
		// .addContainerGap(475, Short.MAX_VALUE)));
		// jPanel2Layout
		// .setVerticalGroup(jPanel2Layout
		// .createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING)
		// .addGroup(
		// jPanel2Layout
		// .createSequentialGroup()
		// .addGroup(
		// jPanel2Layout
		// .createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING)
		// .addComponent(
		// qqGame_jButton,
		// javax.swing.GroupLayout.PREFERRED_SIZE,
		// 32,
		// javax.swing.GroupLayout.PREFERRED_SIZE)
		// .addComponent(
		// qqSpace_jButton,
		// javax.swing.GroupLayout.PREFERRED_SIZE,
		// 30,
		// javax.swing.GroupLayout.PREFERRED_SIZE)
		// .addComponent(
		// qqMusic_Button,
		// javax.swing.GroupLayout.PREFERRED_SIZE,
		// 30,
		// javax.swing.GroupLayout.PREFERRED_SIZE))
		// .addContainerGap(
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// Short.MAX_VALUE)));
		// //jTabbedPane1.addTab("\u5a31  \u4e50", jPanel2);
		//
		// jPanel3.setBackground(new java.awt.Color(51, 204, 255));
		// remoteHlep_Button.setBackground(new java.awt.Color(51, 204, 255));
		// remoteHlep_Button.setText("\u8fd0\u7a0b\u534f\u52a9");
		// remoteHlep_Button.addMouseListener(new java.awt.event.MouseAdapter()
		// {
		// public void mouseClicked(java.awt.event.MouseEvent evt) {
		// remoteHlep_ButtonMouseClicked(evt);
		// }
		// });
		//
		// findShare_jButton.setBackground(new java.awt.Color(0, 204, 255));
		// findShare_jButton.setText("\u67e5\u770b\u5171\u4eab");
		//
		// javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(
		// jPanel3);
		// jPanel3.setLayout(jPanel3Layout);
		// jPanel3Layout
		// .setHorizontalGroup(jPanel3Layout
		// .createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING)
		// .addGroup(
		// jPanel3Layout
		// .createSequentialGroup()
		// .addComponent(
		// findShare_jButton,
		// javax.swing.GroupLayout.PREFERRED_SIZE,
		// 81,
		// javax.swing.GroupLayout.PREFERRED_SIZE)
		// .addPreferredGap(
		// javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		// .addComponent(
		// remoteHlep_Button,
		// javax.swing.GroupLayout.PREFERRED_SIZE,
		// 81,
		// javax.swing.GroupLayout.PREFERRED_SIZE)
		// .addContainerGap(425, Short.MAX_VALUE)));
		// jPanel3Layout
		// .setVerticalGroup(jPanel3Layout
		// .createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING)
		// .addGroup(
		// jPanel3Layout
		// .createSequentialGroup()
		// .addGroup(
		// jPanel3Layout
		// .createParallelGroup(
		// javax.swing.GroupLayout.Alignment.BASELINE)
		// .addComponent(
		// findShare_jButton,
		// javax.swing.GroupLayout.PREFERRED_SIZE,
		// 32,
		// javax.swing.GroupLayout.PREFERRED_SIZE)
		// .addComponent(
		// remoteHlep_Button,
		// javax.swing.GroupLayout.PREFERRED_SIZE,
		// 30,
		// javax.swing.GroupLayout.PREFERRED_SIZE))
		// .addContainerGap()));
		// //jTabbedPane1.addTab("\u5e94  \u7528", jPanel3);
		//
		// jPanel4.setBackground(new java.awt.Color(51, 204, 255));
		// intoBlacklist_Button.setBackground(new java.awt.Color(51, 204, 255));
		// intoBlacklist_Button.setText("\u79fb\u5165\u9ed1\u540d\u5355");
		//
		// deleteFriend_Button.setBackground(new java.awt.Color(51, 204, 255));
		// deleteFriend_Button.setText("\u5220\u9664\u597d\u53cb");
		//
		// layoutSet_Button.setBackground(new java.awt.Color(51, 204, 255));
		// layoutSet_Button.setText("\u7248\u9762\u8bbe\u7f6e");
		//
		// javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(
		// jPanel4);
		// jPanel4.setLayout(jPanel4Layout);
		// jPanel4Layout
		// .setHorizontalGroup(jPanel4Layout
		// .createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING)
		// .addGroup(
		// jPanel4Layout
		// .createSequentialGroup()
		// .addComponent(layoutSet_Button)
		// .addPreferredGap(
		// javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		// .addComponent(deleteFriend_Button)
		// .addPreferredGap(
		// javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		// .addComponent(intoBlacklist_Button)
		// .addContainerGap(326, Short.MAX_VALUE)));
		// jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		// jPanel4Layout
		// .createParallelGroup(
		// javax.swing.GroupLayout.Alignment.BASELINE)
		// .addComponent(layoutSet_Button,
		// javax.swing.GroupLayout.DEFAULT_SIZE, 32,
		// Short.MAX_VALUE)
		// .addComponent(deleteFriend_Button,
		// javax.swing.GroupLayout.PREFERRED_SIZE, 30,
		// javax.swing.GroupLayout.PREFERRED_SIZE)
		// .addComponent(intoBlacklist_Button,
		// javax.swing.GroupLayout.PREFERRED_SIZE, 28,
		// javax.swing.GroupLayout.PREFERRED_SIZE)));
		// //jTabbedPane1.addTab("\u5de5  \u5177", jPanel4);
		//
		// jTabbedPane1.getAccessibleContext().setAccessibleName("tabl1");
		//
		// javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(
		// jPanel5);
		// jPanel5.setLayout(jPanel5Layout);
		// jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING).addComponent(
		// jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 598,
		// Short.MAX_VALUE));
		// jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(
		// javax.swing.GroupLayout.Alignment.LEADING).addComponent(
		// jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61,
		// javax.swing.GroupLayout.PREFERRED_SIZE));
		//
		// javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
		// getContentPane());
		// getContentPane().setLayout(layout);
		// layout.setHorizontalGroup(layout
		// .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		// .addGroup(
		// javax.swing.GroupLayout.Alignment.TRAILING,
		// layout.createSequentialGroup().addComponent(jTabbedPane2,
		// javax.swing.GroupLayout.PREFERRED_SIZE,
		// 155,
		// javax.swing.GroupLayout.PREFERRED_SIZE)
		//
		// .addPreferredGap(
		// javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jPanel7,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// Short.MAX_VALUE)
		// )
		// .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE,
		// javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		// layout.setVerticalGroup(layout
		// .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		// .addGroup(
		// javax.swing.GroupLayout.Alignment.TRAILING,
		// layout.createSequentialGroup()
		// //.addGap(69, 69, 69)
		// .addGroup(
		// layout.createParallelGroup(
		// javax.swing.GroupLayout.Alignment.TRAILING)
		// .addComponent(
		// jTabbedPane2,
		// javax.swing.GroupLayout.Alignment.LEADING)
		// .addComponent(
		// jPanel7,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// javax.swing.GroupLayout.DEFAULT_SIZE,
		// Short.MAX_VALUE)
		// ))
		// .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE,
		// 558, Short.MAX_VALUE));
		// addWindowListener(new WindowAdapter() {
		// public void windowClosing(WindowEvent e) {
		// setVisible(false);
		// }
		// });
		//

		//

		// getContentPane().add(glassBox1, java.awt.BorderLayout.CENTER);
		// this.pack();
		new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				// TODO Auto-generated method stub

				timer.schedule(new MessageRecived(), 1000, 1000);
				return null;

			}

			@Override
			protected void done() {
				// TODO Auto-generated method stub

				super.done();
			}

		}.execute();

	}// </editor-fold>

	private void insert(FontAttrib userAttrib) {// 插入文本
		try {
			doc.insertString(doc.getLength(), userAttrib.getText() + "\r\n",
					userAttrib.getAttrSet());
			textPane.setCaretPosition(textPane.getDocument().getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	//
	// // 选择文件还回文件的对象
	// private File slecetFile() {
	// javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
	// fileChooser.showOpenDialog(this);
	// File file = fileChooser.getSelectedFile();
	// return file;
	// }
	//
	// public void setVisible(boolean b) {
	// if (b) {
	// BaseFrame.centerWindow(this);
	// Cache cache = Cache.getInstance();
	// cache.addTalkFrame(friend.getUserID(), this);
	// } else {
	// showMsg_jTextPane.setText("");
	// Cache cache = Cache.getInstance();
	// cache.removeTalkFrame(friend.getUserID());
	// this.dispose();
	// }
	// super.setVisible(b);
	// }
	//
	// private void font_jButtonMouseClicked(java.awt.event.MouseEvent evt) {
	//
	// }
	//
	// private void face_jButtonMouseClicked(java.awt.event.MouseEvent evt) {
	//
	// }
	//
	// /**
	// *
	// * @param 发送震动消息
	// */
	// private void shock_ButtonMouseClicked(java.awt.event.MouseEvent evt) {
	// if (friend.isOnline()) {
	// String srcQQ = owerUser.getUserID();
	// String destQQ = this.friend.getUserID();
	// ShockMsg shockFrameMessage = new ShockMsg(srcQQ, destQQ);
	// // ConnectSession.getInstance().sendTextMessage(shockFrameMessage);
	// // FrameEarthquakeCenter dec = new FrameEarthquakeCenter (this);
	// // dec.startShake();
	// } else {
	// javax.swing.JOptionPane.showMessageDialog(this, "您的好友没在线或者隐身！");
	// }
	//
	// }
	//
	// private void sendImage_jButtonMouseClicked(java.awt.event.MouseEvent evt)
	// {
	// // TODO 将在此处添加您的处理代码：
	// }
	//
	// /**
	// *
	// * @param evt
	// */
	// private void talkRecord_ButtonMouseClicked(java.awt.event.MouseEvent evt)
	// {
	//
	// }
	//
	// private void remoteHlep_ButtonMouseClicked(java.awt.event.MouseEvent evt)
	// {
	// // TODO 将在此处添加您的处理代码：
	// }
	//
	// private void qqMusic_ButtonMouseClicked(java.awt.event.MouseEvent evt) {
	// // TODO 将在此处添加您的处理代码：
	// }
	//
	// private void qqSpace_jButtonMouseClicked(java.awt.event.MouseEvent evt) {
	// // TODO 将在此处添加您的处理代码：
	// }
	//
	// private void qqGame_jButtonMouseClicked(java.awt.event.MouseEvent evt) {
	// // TODO 将在此处添加您的处理代码：
	// }
	//
	// /**
	// * 音频请求按钮
	// *
	// * @param evt
	// */
	// private void voice_ButtonMouseClicked(java.awt.event.MouseEvent evt) {
	// System.out.println("音频请求按钮");
	//
	// }
	//
	// /**
	// * 传送文件的按钮的事件
	// *
	// * @param evt
	// */
	//
	// /**
	// * 关闭按钮的激发事件
	// *
	// * @param evt
	// */
	// private void exit_ButtonMouseClicked(java.awt.event.MouseEvent evt) {
	// this.setVisible(false);
	// timer.cancel();
	// }
	//
	// /**
	// * 发送按钮的激发事件
	// *
	// * @param evt
	// */
	// private void send_ButtonMouseClicked(java.awt.event.MouseEvent evt) {
	// this.sendTalkMessage();
	// }
	//
	public void sendTalkMessage() {
		final String msgStr = textPane_1.getText();
		if (msgStr.length() == 0) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					javax.swing.JOptionPane.showMessageDialog(null,
							"聊天信息不能为空!!..");
				}
			});

		} else {

			new SwingWorker<Void, Void>() {

				@Override
				protected Void doInBackground() {
					// TODO Auto-generated method stub
					try {
						showMsg(SConfig.getInstance().getProfile().myPeerBean.PPeerid,
								msgStr);
						HashMap<String, String> map = new HashMap<String, String>();
						map = sendMessage(msgStr);
						System.out.println(map.toString());
						System.out.println("map===" + map.get("r"));
						if (map.get("r").equals("ok")
								|| map.get("r").equals("inbox")) {
							textPane_1.setText("");
							AnMessageBean
									.getInstance()
									.saveMessage(
											SConfig.getInstance().getProfile().myPeerBean.PPeerid,
											msgStr, "-1", DBDataSQL.OUT, "0",
											map.get("time"), "true", "1");

						} else {

							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									JOptionPane.showMessageDialog(null,
											"消息发送失败，请重发");
								}
							});

						}
					} catch (final Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								JOptionPane.showMessageDialog(null,
										"消息发送失败，请重发" + e.getMessage());
							}
						});
					}
					return null;
				}

			}.execute();

		}
	}

	//
	public void showMsg(String peak, String msg) {
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");// 这里的格式自己按需要写
		Date d = new Date();
		String dataString = sf.format(d);
		userInfoFontAttrib.setText(peak + "  " + dataString);
		// System.out.println("textInfo:" + msg);

		this.insert(userInfoFontAttrib);
		int index = msg.indexOf("/");
		// System.out.println("/");
		System.out.println("index---->" + index);
		pos1 = textPane.getCaretPosition();
		System.out.println("pos1---->" + pos1);
		if (index >= 0 && index < msg.length() - 1) {
			System.out.println("--------------");

			String m = receivedPicInfo(msg);
			System.out.println("---->" + m);
			textFontAttrib.setText(m);
			insert(textFontAttrib);

			// Document doc = textPane.getDocument();
			// textPane.select(doc.getLength(), doc.getLength());
			insertPics(true);
		} else {

			// this.insert(userInfoFontAttrib);
			textFontAttrib.setText(msg);
			this.insert(textFontAttrib);
			Document doc = textPane.getDocument();
			textPane.select(doc.getLength(), doc.getLength());
		}

	}

	public void showRecivedMsg(String peak, String msg, String date)
			throws ParseException {

		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");// 这里的格式自己按需要写
		Date d = new Date();
		d.setTime(Long.parseLong(date));
		String dataString = sf.format(d);
		userInfoFontAttrib.setText(peak + "  " + dataString);
		this.insert(userInfoFontAttrib);
		int index = msg.indexOf("/");
		System.out.println("index---->" + index);
		pos1 = textPane.getCaretPosition();
		System.out.println("pos1---->" + pos1);
		if (index >= 0 && index < msg.length() - 1) {
			String m = receivedPicInfo(msg);
			textFontAttrib.setText(m);
			insert(textFontAttrib);
			insertPics(true);
		} else {
			// this.insert(userInfoFontAttrib);
			textFontAttrib.setText(msg);
			this.insert(textFontAttrib);
			Document doc = textPane.getDocument();
			textPane.select(doc.getLength(), doc.getLength());
		}

	}

	public JLabel getPicLabel() {
		return label_1;
	}

	/**
	 * 插入图片
	 * 
	 * @param icon
	 */
	public void insertSendPic(ImageIcon imgIc) {
		// jpMsg.setCaretPosition(docChat.getLength()); // 设置插入位置
		textPane_1.insertIcon(imgIc); // 插入图片
		System.out.print(imgIc.toString());
		// insert(new FontAttrib()); // 这样做可以换行
	}

	public String receivedPicInfo(String origi) {
		int sum = origi.length();// 接收到的文字
		int i = 0;// 记录文字位置
		int j = 0;// 记录表情
		StringBuffer sb = new StringBuffer("");
		String temp;
		List<String> list = new ArrayList<String>();// 接收到所有表情的文字用于替换
		while (i < sum) {
			if (sum < 4) {
				break;
			}
			if (origi.charAt(i) == '/') {

				if (sum - i >= 4) {
					// System.out.println(sum - i);
					temp = origi.substring(i, i + 4);
					System.out.println("temp==>" + temp);

					Integer position = CustomFace.faceStrToInt.get(temp);
					if (position != null) {

						System.out.println("i2--->" + j);
						PicInfo pic = new PicInfo(j, position + "");
						receivdPicInfo.add(pic);
						list.add(temp);
						i = i + 4;
						j++;
					} else {
						i++;
						j++;
					}
				} else {
					break;
				}
			} else {
				i++;
				j++;
			}

			System.out.println("i--->" + i);
		}
		for (int b = 0; b < list.size(); b++) {
			String a = list.get(b);
			origi = origi.replace(a, "");
		}
		j = 0;// 让j重新计算
		System.out.println(origi);
		return origi;
	}

	/**
	 * 插入图片
	 * 
	 * @param isFriend
	 *            是否为朋友发过来的消息
	 */

	private void insertPics(boolean isFriend) {
		System.out.println(receivdPicInfo.size());
		if (isFriend) {
			if (this.receivdPicInfo.size() <= 0) {
				return;
			} else {
				for (int i = 0; i < receivdPicInfo.size(); i++) {
					// pos1 = textPane.getCaretPosition();
					PicInfo pic = receivdPicInfo.get(i);
					System.out.println("xx-->" + pic.getPos());
					String fileName;
					if (i + 1 == receivdPicInfo.size()) {
						textPane.setCaretPosition(pos1 + pic.getPos() + 1);
					} else {
						textPane.setCaretPosition(pos1 + pic.getPos());
					}
					/* 设置插入位置 */
					fileName = "face/smiley_" + pic.getVal() + ".png";/* 修改图片路径 */
					textPane.insertIcon(new ImageIcon(PicsJWindow.class
							.getResource(fileName))); /* 插入图片 */
					/* jpChat.updateUI(); */
				}
				receivdPicInfo.clear();
			}
		} else {

			if (myPicInfo.size() <= 0) {
				return;
			} else {
				for (int i = 0; i < myPicInfo.size(); i++) {
					PicInfo pic = myPicInfo.get(i);
					textPane.setCaretPosition(pos2 + pic.getPos()); /* 设置插入位置 */
					String fileName;
					fileName = "face/" + pic.getVal() + ".gif";/* 修改图片路径 */
					textPane.insertIcon(new ImageIcon(PicsJWindow.class
							.getResource(fileName))); /* 插入图片 */
					/* jpChat.updateUI(); */
				}
				myPicInfo.clear();
			}
		}
		textPane.setCaretPosition(doc.getLength()); /* 设置滚动到最下边 */
		// insert(new FontAttrib()); /*这样做可以换行*/
	}

	//
	// public void run() {
	// }
	//
	// public boolean isFileSndOrGeting() {
	// return isFileSndOrGeting;
	// }
	//
	// public void setFileSndOrGeting(boolean isFileSndOrGeting) {
	// this.isFileSndOrGeting = isFileSndOrGeting;
	// }
	//
	// public JTabbedPane getJTabbedPane() {
	// return jTabbedPane2;
	// }
	//

	//
	// private javax.swing.JButton deleteFriend_Button;
	// private javax.swing.JButton exit_Button; // 的按钮
	// private javax.swing.JButton face_jButton;
	// private javax.swing.JLabel fenMusic_jLabel;
	// private javax.swing.JButton findShare_jButton;
	// private javax.swing.JButton font_jButton;
	// private javax.swing.JLabel headIamge_jLabel;
	// private javax.swing.JButton intoBlacklist_Button;
	// private javax.swing.JLabel friendXiuXiu_jLabel;
	//
	// private javax.swing.JLabel jLabel4;
	// private javax.swing.JPanel jPanel1;
	// private javax.swing.JPanel jPanel10;
	// private javax.swing.JPanel jPanel11;
	// private javax.swing.JPanel jPanel12;
	// private javax.swing.JPanel jPanel2;
	// private javax.swing.JPanel jPanel3;
	// private javax.swing.JPanel jPanel4;
	// private javax.swing.JPanel jPanel5;
	// private javax.swing.JPanel jPanel6;
	// private javax.swing.JPanel jPanel7;
	// private javax.swing.JPanel jPanel9;
	//
	// private javax.swing.JScrollPane jScrollPane1;
	// private javax.swing.JScrollPane jScrollPane2;
	// private javax.swing.JScrollPane jScrollPane3;
	// private javax.swing.JScrollPane jScrollPane4;
	// private SnapTipTabbedPane jTabbedPane1;
	// private SnapTipTabbedPane jTabbedPane2;
	//
	// private javax.swing.JToolBar jToolBar1;
	// private javax.swing.JButton layoutSet_Button;
	// private javax.swing.JButton moblie_jButton;
	// private javax.swing.JTextPane personWord_TextPane;
	// private javax.swing.JButton qqGame_jButton;
	// private javax.swing.JButton qqMusic_Button;
	// private javax.swing.JButton qqSpace_jButton;
	// private javax.swing.JButton remoteHlep_Button;
	// private javax.swing.JButton sendImage_jButton;
	//
	// private javax.swing.JTextPane sendMsg_jTextPane; // 写入聊天信息的TextArea
	//
	// private javax.swing.JButton send_Button; // 发送信息的按钮
	// private javax.swing.JButton setKey_Button; // 设置快捷键的按钮
	// private javax.swing.JButton shock_Button; // 发送震动的按钮
	//
	// public javax.swing.JTextPane showMsg_jTextPane; // 显示聊天信息的TextArea
	//
	// private javax.swing.JButton snedFile_Button; // 传送文件的按钮
	// private javax.swing.JButton talkRecord_Button;
	//
	// private javax.swing.JTextPane talkRecord_jTextPane;
	//
	// private javax.swing.JButton video_Button; // 视频请求按钮
	// private javax.swing.JButton voice_Button; // 音频请求按钮
	// private GlassBox glassBox1;
	//
	// // 变量声明结束
	//
	// public static MsgUser getOwerUser() {
	// return owerUser;
	// }
	//
	// public static void setOwerUser(MsgUser owerUser) {
	// TalkFrame.owerUser = owerUser;
	// }
	//
	class MessageRecived extends TimerTask {

		@Override
		public void run() {
			collection = new MessageLocationCollection(
					GenDao.getInstance()
							.getMessageArrayValue(
									SConfig.getInstance().getProfile().myPeerBean.PPeerid));
			sampleJList.setListData(collection.getLocations().toArray());// 刷新用户消息列表
			Hashtable<String, Object> condition = new Hashtable<String, Object>();
			List<String> msg = null;
			List<String> msgtime = null;
			if (label_7.getText().equals(friend.getUserID())
					&& friend.getGroup().equals("0")) {
				// TODO Auto-generated method stub

				condition.put(DBDataSQL.COL_MES_PEERID, friend.getUserID());
				condition.put(DBDataSQL.COL_MES_UNREAD, "false");
				msg = GenDao.getInstance().getArrayValue(
						DBDataSQL.TB_MESSAGE.toUpperCase(),
						new String[] { DBDataSQL.COL_MES_MSG.toLowerCase() },
						DBDataSQL.COL_MES_MSG.toUpperCase(), condition);
				msgtime = GenDao.getInstance().getArrayValue(
						DBDataSQL.TB_MESSAGE,
						new String[] { DBDataSQL.COL_MES_MSGTIME },
						DBDataSQL.COL_MES_MSGTIME, condition);
				for (int i = 0; i < msg.size(); i++) {
					String m = msg.get(i);
					String time = msgtime.get(i);
					try {
						showRecivedMsg(friend.getUserID(), m, time);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (msg.size() > 0) {
					GenDao.getInstance().executeUpdate(
							DBDataSQL.TB_MESSAGE.toUpperCase(),
							new String[] { DBDataSQL.COL_MES_UNREAD
									.toUpperCase() }, new Object[] { "true" },
							condition);
				}
			}
			if (label_7.getText().equals(friend.getUserID())
					&& friend.getGroup().equals("1")) {
				condition.put(DBDataSQL.COL_MES_GROUP, friend.getUserID());
				condition.put(DBDataSQL.COL_MES_UNREAD, "false");
				msg = GenDao.getInstance().getArrayValue(
						DBDataSQL.TB_MESSAGE.toUpperCase(),
						new String[] { DBDataSQL.COL_MES_MSG.toLowerCase() },
						DBDataSQL.COL_MES_MSG.toUpperCase(), condition);
				msgtime = GenDao.getInstance().getArrayValue(
						DBDataSQL.TB_MESSAGE,
						new String[] { DBDataSQL.COL_MES_MSGTIME },
						DBDataSQL.COL_MES_MSGTIME, condition);
				for (int i = 0; i < msg.size(); i++) {
					String m = msg.get(i);
					String time = msgtime.get(i);
					try {
						showRecivedMsg(friend.getUserName(), m, time);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (msg.size() > 0) {
					GenDao.getInstance().executeUpdate(
							DBDataSQL.TB_MESSAGE.toUpperCase(),
							new String[] { DBDataSQL.COL_MES_UNREAD
									.toUpperCase() }, new Object[] { "true" },
							condition);
				}
			}

		}
	}

	public HashMap<String, String> sendMessage(String msg) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		if (friend.getGroup().equals("0")) {
			ConnectionUtils.getInstance().getPubkey(friend.getUserID());
			String mMimeSend = "mime:txt:" + msg;
			String mecode = "";
			try {
				mecode = RSAEncryptor.getInstance().encryptBase64Encode(
						mMimeSend.getBytes(), friend.getUserID());
			} catch (CryptorException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("duid", friend.getUserID());
			map.put("msg", mecode);
			// ConnectionUtils.getInstance().postTxtMessage(map);

			hashMap = ConnectionUtils.getInstance().postTxtMessage(map);
			System.out.println("发送信息聊~~~~~~" + hashMap.toString());
		}
		if (friend.getGroup().equals("1")) {
			try {
				String mMimeSend = "mime:txt:"+msg;
				String enc = mCryptor.encryptBase64Encode(msg.getBytes());
				Hashtable<String, Object> condition = new Hashtable<String, Object>();
				condition.put(DBDataSQL.COL_PROOM_ROOMNAME, friend.getUserID());
				String roomId = GenDao.getInstance().getValue(
						DBDataSQL.TB_ROOMS,
						new String[] { DBDataSQL.COL_PROOM_ROOMID },
						DBDataSQL.COL_PROOM_ROOMID, condition);
				HashMap<String, String> map = new HashMap<String, String>();
				map = ConnectionUtils.getInstance().roomSendMsg(roomId, enc,
						DBDataSQL.ROOM_TYPE_NORMAL);
				System.out.println("群聊信息发送中---》" + map.toString());
			} catch (CryptorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return hashMap;

	}

	public static void main(String[] args) {
		TalkFrame frame = new TalkFrame(null);
		frame.setVisible(true);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	/**
	 * 重组发送的表情信息
	 * 
	 * @return 重组后的信息串 格式为 位置|代号+位置|代号+……
	 * @throws BadLocationException
	 */
	private String buildPicInfo() throws BadLocationException {
		StringBuilder sb = new StringBuilder("");

		// 遍历jtextpane找出所有的图片信息封装成指定格式
		for (int i = 0; i < this.textPane_1.getText().length(); i++) {
			if (docMsg.getCharacterElement(i).getName().equals("icon")) {
				Icon icon = StyleConstants.getIcon(textPane_1
						.getStyledDocument().getCharacterElement(i)
						.getAttributes());
				ChatPic cupic = (ChatPic) icon;
				PicInfo picInfo = new PicInfo(i, cupic.getIm() + "");
				sb.append(CustomFace.faceIntToStr.get(i));
			} else if (docMsg.getCharacterElement(i).getName()
					.equals("content")) {
				sb.append(textPane_1.getText(i, 1));// 获取文字用于组装
			}

		}

		System.out.println(sb.toString());
		myPicInfo.clear();
		return sb.toString();
		// return null;
	}

	/**
	 * 获取所需要的文字设置
	 * 
	 * @return FontAttrib
	 * @throws BadLocationException
	 */
	private FontAndText getFontAttrib() throws BadLocationException {
		FontAndText att = new FontAndText();
		att.setText(textPane_1.getText() + "*" + buildPicInfo());// 文本和表情信息
		// att.setName((String) fontName.getSelectedItem());
		// att.setSize(Integer.parseInt((String) fontSize.getSelectedItem()));
		// String temp_color = (String) fontColor.getSelectedItem();
		// if (temp_color.equals("黑色")) {
		// att.setColor(new Color(0, 0, 0));
		// } else if (temp_color.equals("红色")) {
		// att.setColor(new Color(255, 0, 0));
		// } else if (temp_color.equals("蓝色")) {
		// att.setColor(new Color(0, 0, 255));
		// } else if (temp_color.equals("黄色")) {
		// att.setColor(new Color(255, 255, 0));
		// } else if (temp_color.equals("绿色")) {
		// att.setColor(new Color(0, 255, 0));
		// }
		return att;
	}

}
