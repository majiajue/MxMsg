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
import java.security.KeyPair;
import java.security.PublicKey;

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
import org.apache.commons.codec.binary.Base64;
import com.mx.clent.vo.AnPeersBean;
import com.mx.clent.vo.Profile;
import com.mx.client.db.DBDataSQL;
import com.mx.client.db.DBTools;
import com.mx.client.webtools.ConnectionUtils;
import com.mx.client.webtools.SConfig;
import com.mx.client.webtools.SLogin;
import com.sun.awt.AWTUtilities;

public class Login extends JFrame {
	static Point origin = new Point();// ��ȡ��ǰ����λ��
	private JPanel contentPane;
	static SystemTray tray = null;// ϵͳ����
	static TrayIcon trayIcon = null; // ����ͼ��
	public JPasswordField pwd����;
	private JLabel lblQQ2013;
	public JLabel lblͷ��;
	public JCheckBox checkBox��ס����;
	public JCheckBox checkBox�Զ���¼;
	public JLabel lbl��¼;
	public JTextField textField�û���;
	public JLabel lblע���˺�;
	public JLabel lbl��������;
	public JLabel lbl��С��;
	public JLabel lbl�˳�;
	public JLabel lbl���˺�;
	public JLabel lbl����;
	public JComboBox comboBox״̬;
	String Skey;
	String shapwd;

	/**
	 * @param args
	 */
	/**
	 * ������½��
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// config.addConfiguration(new PropertiesConfiguration(""));
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					final Login frame = new Login();// ������½��
					// AWT.setWindowOpaque(frame, false);//���ô�����ȫ͸��
					AWTUtilities.setWindowOpaque(frame, false);
					frame.setVisible(true);// �ɼ����

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

							frame.setLocation(p.x + arg0.getX() - origin.x, p.y
									+ arg0.getY() - origin.y);
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

							frame.setLocation(p.x + arg0.getX() - origin.x, p.y
									+ arg0.getY() - origin.y);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public Login() {
		if (SystemTray.isSupported()) // �������ϵͳ֧������
		{
			this.tray();
		}
		setTitle("MX");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Login.class.getResource("/com/mx/client/image/QQ_64.png")));
		setUndecorated(true);// ���ô���û�б߿�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 267);

		contentPane = new MyPanel("/com/mx/client/image/QQ2011_Login.png");
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setOpaque(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setOpaque(false);
		pwd���� = new JPasswordField();
		pwd����.setText("123");
		pwd����.setEchoChar('��');
		pwd����.setBounds(104, 163, 154, 26);
		contentPane.add(pwd����);

		lblQQ2013 = new JLabel("MX");
		lblQQ2013.setForeground(new Color(0, 0, 51));
		lblQQ2013.setFont(new Font("����", Font.BOLD, 16));
		lblQQ2013.setBounds(14, 6, 55, 18);
		contentPane.add(lblQQ2013);

		lblͷ�� = new JLabel("");
		lblͷ��.setIcon(new ImageIcon(Login.class
				.getResource("/com/mx/client/headImage/head_boy_01_64.jpg")));
		lblͷ��.setBounds(18, 127, 64, 64);
		contentPane.add(lblͷ��);

		checkBox��ס���� = new JCheckBox("��ס����");
		checkBox��ס����.setBounds(156, 198, 80, 18);
		checkBox��ס����.setOpaque(false);
		checkBox��ס����.setBorder(new EmptyBorder(0, 0, 0, 0));
		checkBox��ס����.setDoubleBuffered(false);
		checkBox��ס����.setRolloverEnabled(false);
		contentPane.add(checkBox��ס����);

		checkBox�Զ���¼ = new JCheckBox("�Զ���½");
		checkBox�Զ���¼.setBounds(237, 198, 80, 18);
		checkBox�Զ���¼.setOpaque(false);
		checkBox�Զ���¼.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.add(checkBox�Զ���¼);

		lbl��¼ = new JLabel("");
		lbl��¼.setIcon(new ImageIcon(Login.class
				.getResource("/com/mx/client/image/button/button_login_1.png")));
		lbl��¼.setBounds(262, 237, 69, 22);
		lbl��¼.addMouseListener(new MouseListener() {

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
				String pwd = "";
				try {
					Skey = com.mx.client.webtools.SLogin.getInstance()
							.register1();
					SConfig.getInstance().setSessionKey(Skey);
					char[] p = pwd����.getPassword();
					pwd = new String(p);
					System.out.println("pwd==" + pwd);
					shapwd = SConfig.getInstance().setPassword(pwd);
					System.out.println("1====" + shapwd);
					String result = SLogin.getInstance().login(
							textField�û���.getText(), Skey, shapwd);

					// String friend
					// =ConnectionUtils.getInstance().getContacts();
					// System.out.println(SConfig.getInstance().decodeContacts(friend));
					// friend = SConfig.getInstance().decodeContacts(friend);
					System.out.println("�Ƿ���ڳԼ�¼"
							+ Profile.isExistProfile(textField�û���.getText()));

					if (result == null || result.equals("Failed")
							|| result.equals("failed")) {

						System.out.println("��½ʧ��");
					} else {

						if (!SConfig.getInstance().getMagic()) {
							// publishProgress("�û���¼ʧ��...");
							System.out.println("��½ʧ��");
							// SConfig.getInstance().setRegisted(false);
							// return false;
						}
						if (Profile.isExistProfile(textField�û���.getText())) {
							boolean re = Profile.checkloginpwd(
									textField�û���.getText(), pwd);
							if (re) {

								Profile p1 = Profile.LoadProfile(textField�û���
										.getText());
								p1.setSessionkey(Skey);
								KeyPair kp = null;
								kp = p1.getKeyPair();
								PublicKey key = kp.getPublic();
								String encode_key = new String(
										Base64.encodeBase64(com.mx.client.webtools.PubkeyUtils
												.getEncodedPublic(key)));

								try {
									com.mx.client.webtools.SPubkey
											.getInstance().postPubKey(
													encode_key);
								} catch (Exception exception) {
									// TODO Auto-generated catch block
									exception.printStackTrace();
								}
								System.out.println("p1.PUsername1:"
										+ p1.myPeerBean.PUsername);
								SConfig.getInstance().setProfile(p1);
//								String friend = ConnectionUtils.getInstance()
//										.getContacts();
//								System.out.println(SConfig.getInstance()
//										.decodeContacts(friend));
//								friend = SConfig.getInstance().decodeContacts(
//										friend);
								MainFrame frame = MainFrame.getInstance();
								frame.setVisible(true);
								setVisible(false);
							} else {

								System.out.println(" error password!");
							}

						} else {

							KeyPair kp = null;
							String uptime = "";
							try {
								kp = SConfig.getInstance()
										.updatePublicKeyToServer(
												textField�û���.getText());
								uptime = System.currentTimeMillis() + "";
								DBTools.CreateUserProfile(textField�û���
										.getText());
								DBTools.excuteSql(DBDataSQL.SQL_CREATE_TB_MESSAGE);
								DBTools.excuteSql(DBDataSQL.SQL_CREATE_MESSAGE_INDEX);
								DBTools.excuteSql(DBDataSQL.SQL_CREATE_TB_LOGIN);
								DBTools.excuteSql(DBDataSQL.SQL_CREATE_LOGIN_INDEX);
								DBTools.excuteSql(DBDataSQL.SQL_CREATE_TB_PEERS);
								DBTools.excuteSql(DBDataSQL.SQL_CREATE_PEERS_INDEX);
								DBTools.excuteSql(DBDataSQL.SQL_CREATE_PREFERENCE);
								DBTools.excuteSql(DBDataSQL.SQL_CREATE_PREFERENCE_INDEX);
								AnPeersBean bean = new AnPeersBean();
								bean.PPeerid = textField�û���.getText();
								bean.PUsername = textField�û���.getText();
								Profile profile = new Profile(textField�û���
										.getText(), pwd);
								profile = profile.CreateProfile(bean, pwd, kp,
										Skey, uptime);
								SConfig.getInstance().setProfile(profile);
								// List<MsgFriendGroup>
								// friendGroup=XmlUtil.instance().parseXmltoString2(friend,"UTF-8","con");
								// MainFrame.setFriends(friendGroup);
								MainFrame frame = MainFrame.getInstance();
								frame.setVisible(true);
								setVisible(false);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							// System.out.println("friend==="+friend);
						}
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(lbl��¼);

		textField�û��� = new JTextField();
		textField�û���.setText("\u9A6C\u5316\u817E");
		textField�û���.setBounds(104, 128, 154, 26);
		contentPane.add(textField�û���);
		textField�û���.setColumns(10);

		lblע���˺� = new JLabel("\u6CE8\u518C\u8D26\u53F7");
		lblע���˺�.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblע���˺�.setForeground(new Color(0, 51, 255));
		lblע���˺�.setBounds(288, 132, 55, 18);
		contentPane.add(lblע���˺�);
		lblע���˺�.addMouseListener(new MouseListener() {

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
				lblע���˺�.setEnabled(false);
			}
		});

		lbl�������� = new JLabel("\u5FD8\u8BB0\u5BC6\u7801");
		lbl��������.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lbl��������.setForeground(new Color(0, 51, 255));
		lbl��������.setBounds(288, 167, 55, 18);
		contentPane.add(lbl��������);

		lbl��С�� = new JLabel("");
		lbl��С��.setIcon(new ImageIcon(Login.class
				.getResource("/com/mx/client/image/button/login_minsize_1.png")));
		lbl��С��.setBounds(284, 0, 29, 19);
		contentPane.add(lbl��С��);
		lbl��С��.addMouseListener(new MouseListener() {

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

					tray.add(trayIcon); // ������ͼ����ӵ�ϵͳ������ʵ����
					// setVisible(false); // ʹ���ڲ�����
					dispose();
				} catch (AWTException ex) {
					ex.printStackTrace();
				}

			}
		});

		lbl�˳� = new JLabel("");
		lbl�˳�.setIcon(new ImageIcon(Login.class
				.getResource("/com/mx/client/image/button/login_exit_1.png")));
		lbl�˳�.setBounds(312, -1, 37, 20);
		contentPane.add(lbl�˳�);
		lbl�˳�.addMouseListener(new MouseListener() {

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

		lbl���˺� = new JLabel("");
		lbl���˺�.setIcon(new ImageIcon(
				Login.class
						.getResource("/com/mx/client/image/button/login_duozhanghao_1.png")));
		lbl���˺�.setBounds(14, 237, 69, 21);
		contentPane.add(lbl���˺�);

		lbl���� = new JLabel("");
		lbl����.setIcon(new ImageIcon(Login.class
				.getResource("/com/mx/client/image/button/login_setting_1.png")));
		lbl����.setBounds(93, 237, 69, 21);
		contentPane.add(lbl����);

		comboBox״̬ = new JComboBox();
		comboBox״̬.setBounds(104, 195, 40, 24);
		contentPane.add(comboBox״̬);
	}

	void tray() {
		tray = SystemTray.getSystemTray(); // ��ñ�����ϵͳ���̵�ʵ��
		ImageIcon icon = new ImageIcon(
				Login.class.getResource("/com/mx/client/image/QQ_16.png")); // ��Ҫ��ʾ�������е�ͼ��
		PopupMenu pop = new PopupMenu(); // ����һ���Ҽ�����ʽ�˵�
		MenuItem show = new MenuItem("�򿪳���(s)");
		MenuItem exit = new MenuItem("�˳�����(x)");
		pop.add(show);
		pop.add(exit);
		// ������������������������ͼ����˫��ʱ��Ĭ����ʾ����
		trayIcon = new TrayIcon(icon.getImage(), "MX", pop);
		trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) // ���˫��
				{
					tray.remove(trayIcon); // ��ϵͳ������ʵ�����Ƴ�����ͼ��
					setExtendedState(JFrame.NORMAL);
					setVisible(true); // ��ʾ����
					toFront();
				}
			}
		});
		show.addActionListener(new ActionListener() // �������ʾ���ڡ��˵��󽫴�����ʾ����
		{
			public void actionPerformed(ActionEvent e) {
				// tray.remove(trayIcon); // ��ϵͳ������ʵ�����Ƴ�����ͼ��
				setExtendedState(JFrame.NORMAL);
				setVisible(true); // ��ʾ����
				toFront();
			}
		});
		exit.addActionListener(new ActionListener() // ������˳���ʾ���˵����˳�����
		{
			public void actionPerformed(ActionEvent e) {
				System.exit(0); // �˳�����
			}
		});
	}

}
