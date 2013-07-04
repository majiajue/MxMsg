package com.mx.client;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.mx.clent.vo.Profile;
import com.mx.client.db.DBTools;
import com.mx.client.webtools.SConfig;
import com.mx.client.webtools.SLogin;
import com.sun.awt.AWTUtilities;

public class Login extends JFrame {
	static Point origin = new Point();// 获取当前鼠标的位置
	private JPanel contentPane;
	static SystemTray tray = null;// 系统托盘
	static TrayIcon trayIcon = null; // 托盘图标
	public JPasswordField pwd密码;
	private JLabel lblQQ2013;
	public JLabel lbl头像;
	public JCheckBox checkBox记住密码;
	public JCheckBox checkBox自动登录;
	public JLabel lbl登录;
	public JTextField textField用户名;
	public JLabel lbl注册账号;
	public JLabel lbl忘记密码;
	public JLabel lbl最小化;
	public JLabel lbl退出;
	public JLabel lbl多账号;
	public JLabel lbl设置;
	public JComboBox comboBox状态;
	String mSkey;
	String mShapwd;

	/**
	 * @param args
	 */
	/**
	 * 启动登陆框
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// config.addConfiguration(new PropertiesConfiguration(""));
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					final Login frame = new Login();// 启动登陆框
					// AWT.setWindowOpaque(frame, false);//设置窗体完全透明
					AWTUtilities.setWindowOpaque(frame, false);
					frame.setVisible(true);// 可见框架

					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.addMouseListener(new MouseListener() {

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

					frame.addMouseMotionListener(new MouseMotionListener() {

						@Override
						public void mouseMoved(MouseEvent arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mouseDragged(MouseEvent arg0) {
							// TODO Auto-generated method stub
							Point p = frame.getLocation();

							frame.setLocation(p.x + arg0.getX() - origin.x, p.y + arg0.getY() - origin.y);
						}
					});
					frame.addMouseListener(new MouseListener() {

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

					frame.addMouseMotionListener(new MouseMotionListener() {

						@Override
						public void mouseMoved(MouseEvent arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mouseDragged(MouseEvent arg0) {
							// TODO Auto-generated method stub
							Point p = frame.getLocation();

							frame.setLocation(p.x + arg0.getX() - origin.x, p.y + arg0.getY() - origin.y);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public Login() {

		if (SystemTray.isSupported()) // 如果操作系统支持托盘
		{
			this.tray();
		}
		setTitle("MX");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/com/mx/client/image/QQ_64.png")));
		setUndecorated(true);// 设置窗体没有边框
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 267);

		contentPane = new MyPanel("/com/mx/client/image/QQ2011_Login.png");
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setOpaque(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setOpaque(false);
		pwd密码 = new JPasswordField();
		pwd密码.setText("123");
		pwd密码.setEchoChar('●');
		pwd密码.setBounds(104, 163, 154, 26);
		contentPane.add(pwd密码);

		lblQQ2013 = new JLabel("MX");
		lblQQ2013.setForeground(new Color(0, 0, 51));
		lblQQ2013.setFont(new Font("宋体", Font.BOLD, 16));
		lblQQ2013.setBounds(14, 6, 55, 18);
		contentPane.add(lblQQ2013);

		lbl头像 = new JLabel("");
		lbl头像.setIcon(new ImageIcon(Login.class.getResource("/com/mx/client/headImage/head_boy_01_64.jpg")));
		lbl头像.setBounds(18, 127, 64, 64);
		contentPane.add(lbl头像);

		checkBox记住密码 = new JCheckBox("记住密码");
		checkBox记住密码.setBounds(156, 198, 80, 18);
		checkBox记住密码.setOpaque(false);
		checkBox记住密码.setBorder(new EmptyBorder(0, 0, 0, 0));
		checkBox记住密码.setDoubleBuffered(false);
		checkBox记住密码.setRolloverEnabled(false);
		contentPane.add(checkBox记住密码);

		checkBox自动登录 = new JCheckBox("自动登陆");
		checkBox自动登录.setBounds(237, 198, 80, 18);
		checkBox自动登录.setOpaque(false);
		checkBox自动登录.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.add(checkBox自动登录);

		lbl登录 = new JLabel("");
		lbl登录.setIcon(new ImageIcon(Login.class.getResource("/com/mx/client/image/button/button_login_1.png")));
		lbl登录.setBounds(262, 237, 69, 22);
		lbl登录.addMouseListener(new MouseListener() {

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
				try {
					String userId = textField用户名.getText();
					String password = new String(pwd密码.getPassword());
					if (loginProcess(userId, password)) {
						if (Profile.isExistProfile(userId)) {
							System.out.println("Login: 用户(" + userId + ")profile已经存在，载入一下");
							Profile profile = Profile.LoadProfile(userId);
							profile.setSessionkey(mSkey);
							try {
								SConfig.getInstance().updatePublicKeyToServer(profile.getKeyPair().getPublic());
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							SConfig.getInstance().setProfile(profile);
						} else {
							System.out.println("Login: (" + userId + ")第一次生成profile");
							try {
								DBTools.initDatabase(userId); // 初始化密讯数据库
								Profile profile = Profile.CreateProfile(userId, mSkey); // 创建该用户的profile文件
								SConfig.getInstance().setProfile(profile);
								// List<MsgFriendGroup>
								// friendGroup=XmlUtil.instance().parseXmltoString2(friend,"UTF-8","con");
								// MainFrame.setFriends(friendGroup);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}
					MainFrame frame = MainFrame.getInstance();
					frame.setVisible(true);
					setVisible(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(lbl登录);

		textField用户名 = new JTextField();
		textField用户名.setText("\u9A6C\u5316\u817E");
		textField用户名.setBounds(104, 128, 154, 26);
		contentPane.add(textField用户名);
		textField用户名.setColumns(10);

		lbl注册账号 = new JLabel("\u6CE8\u518C\u8D26\u53F7");
		lbl注册账号.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lbl注册账号.setForeground(new Color(0, 51, 255));
		lbl注册账号.setBounds(288, 132, 55, 18);
		contentPane.add(lbl注册账号);
		lbl注册账号.addMouseListener(new MouseListener() {

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
				RegisterFrame registerFrame = new RegisterFrame();
				registerFrame.setVisible(true);
				lbl注册账号.setEnabled(false);
			}
		});

		lbl忘记密码 = new JLabel("\u5FD8\u8BB0\u5BC6\u7801");
		lbl忘记密码.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lbl忘记密码.setForeground(new Color(0, 51, 255));
		lbl忘记密码.setBounds(288, 167, 55, 18);
		contentPane.add(lbl忘记密码);

		lbl最小化 = new JLabel("");
		lbl最小化.setIcon(new ImageIcon(Login.class.getResource("/com/mx/client/image/button/login_minsize_1.png")));
		lbl最小化.setBounds(284, 0, 29, 19);
		contentPane.add(lbl最小化);
		lbl最小化.addMouseListener(new MouseListener() {

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
				try {

					tray.add(trayIcon); // 将托盘图标添加到系统的托盘实例中
					// setVisible(false); // 使窗口不可视
					dispose();
				} catch (AWTException ex) {
					ex.printStackTrace();
				}

			}
		});

		lbl退出 = new JLabel("");
		lbl退出.setIcon(new ImageIcon(Login.class.getResource("/com/mx/client/image/button/login_exit_1.png")));
		lbl退出.setBounds(312, -1, 37, 20);
		contentPane.add(lbl退出);
		lbl退出.addMouseListener(new MouseListener() {

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
				System.exit(0);
			}
		});

		lbl多账号 = new JLabel("");
		lbl多账号.setIcon(new ImageIcon(Login.class.getResource("/com/mx/client/image/button/login_duozhanghao_1.png")));
		lbl多账号.setBounds(14, 237, 69, 21);
		contentPane.add(lbl多账号);

		lbl设置 = new JLabel("");
		lbl设置.setIcon(new ImageIcon(Login.class.getResource("/com/mx/client/image/button/login_setting_1.png")));
		lbl设置.setBounds(93, 237, 69, 21);
		contentPane.add(lbl设置);

		comboBox状态 = new JComboBox();
		comboBox状态.setBounds(104, 195, 40, 24);
		contentPane.add(comboBox状态);
	}

	void tray() {
		tray = SystemTray.getSystemTray(); // 获得本操作系统托盘的实例
		ImageIcon icon = new ImageIcon(Login.class.getResource("/com/mx/client/image/QQ_16.png")); // 将要显示到托盘中的图标
		PopupMenu pop = new PopupMenu(); // 构造一个右键弹出式菜单
		MenuItem show = new MenuItem("打开程序(s)");
		MenuItem exit = new MenuItem("退出程序(x)");
		pop.add(show);
		pop.add(exit);
		// 添加鼠标监听器，当鼠标在托盘图标上双击时，默认显示窗口
		trayIcon = new TrayIcon(icon.getImage(), "MX", pop);
		trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) // 鼠标双击
				{
					tray.remove(trayIcon); // 从系统的托盘实例中移除托盘图标
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

	/**
	 * 
	 * @param userId
	 *            登陆ID
	 * @param password
	 *            登陆密码
	 * @return 登陆成功返回true, 登陆失败返回false, 获得的session key保存在mSkey中
	 * @throws IOException
	 */
	private boolean loginProcess(String userId, String password) throws IOException {
		mSkey = com.mx.client.webtools.SLogin.getInstance().register1();
		SConfig.getInstance().setSessionKey(mSkey);
		// String pwd = new String(password);
		// System.out.println("pwd==" + pwd);
		mShapwd = SConfig.getInstance().setPassword(password);
		String result = SLogin.getInstance().login(userId, mSkey, mShapwd);
		if (result == null || result.equals("Failed") || result.equals("failed")) {
			return false;
		} else {
			return true;
		}
	}
}
