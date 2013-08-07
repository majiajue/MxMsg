package com.mx.client;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;

import com.mx.clent.vo.Profile;
import com.mx.client.db.DBTools;
import com.mx.client.webtools.PropertiesUtils;
import com.mx.client.webtools.SConfig;
import com.mx.client.webtools.SLogin;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 注册面板填写基本信息
 * 
 * @author majiajue
 * 
 */
public class RegisterFrame extends JFrame {

	public JPanel contentPane;
	public JTextField txtName;
	public JPasswordField pwd;
	public JTextField pwdRe;
	public JButton btnRegister;
	private String uid = "";

	/**
	 * Create the frame.
	 */
	public RegisterFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				beforeClose();
			}
		});

		setIconImage(Toolkit.getDefaultToolkit().getImage(
				RegisterFrame.class
						.getResource("/com/mx/client/image/QQ_64.png")));
		setTitle("\u6CE8\u518C");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(300, 300);
		contentPane = new MyPanel("/com/mx/client/image/registerBGimg2.jpg");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbUserName = new JLabel("\u7528\u6237\u540D");
		lbUserName.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lbUserName.setBounds(20, 20, 54, 24);
		contentPane.add(lbUserName);

		final JLabel lb = new JLabel("\u83b7\u53d6\u5bc6\u8baf\u53f7\u4e2d....");
		lb.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lb.setBounds(200, 20, 100, 24);
		contentPane.add(lb);

		JLabel lbPwd = new JLabel("\u5BC6\u7801");
		lbPwd.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lbPwd.setBounds(30, 50, 54, 24);
		lbPwd.setToolTipText("请输入你的密码(必填向)");
		contentPane.add(lbPwd);

		JLabel lbPwdre = new JLabel("\u624B\u673A\u53F7");
		lbPwdre.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lbPwdre.setToolTipText("请输入你的手机号");
		lbPwdre.setBounds(20, 70, 100, 45);
		contentPane.add(lbPwdre);

		btnRegister = new JButton("\u6CE8\u518C");
		btnRegister.setForeground(new Color(255, 255, 255));
		btnRegister.setBackground(new Color(0, 100, 0));
		btnRegister.setFont(new Font("微软雅黑", Font.BOLD, 14));
		btnRegister.setBounds(20, 110, 80, 25);
		btnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				register();
			}
		});
		contentPane.add(btnRegister);
		JButton btnCancel = new JButton("\u53D6\u6D88");
		btnCancel.setForeground(new Color(255, 250, 250));
		btnCancel.setBackground(new Color(106, 90, 205));
		btnCancel.setFont(new Font("微软雅黑", Font.BOLD, 14));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancle();
			}
		});
		btnCancel.setBounds(120, 110, 80, 25);
		contentPane.add(btnCancel);
		txtName = new JTextField();
		// txtName.setText(uid);
		txtName.setEditable(false);
		txtName.setBounds(90, 20, 100, 25);
		contentPane.add(txtName);
		txtName.setColumns(10);

		pwd = new JPasswordField();
		pwd.setBounds(90, 50, 100, 25);
		contentPane.add(pwd);

		pwdRe = new JTextField();
		pwdRe.setBounds(90, 80, 100, 25);
		pwdRe.setToolTipText("请输入你的手机号");
		pwdRe.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				pwdRe.setText(pwdRe.getText().replaceAll("^{11}[0-9]", ""));
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		contentPane.add(pwdRe);

		/**
		 * 后台线程获取注册的id号
		 */
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				// TODO Auto-generated method stub
				SLogin login = SLogin.getInstance();

				try {
					String mSkey = login.register1();
					SConfig.getInstance().setSessionKey(mSkey);
					uid = login.getNewUid();
					txtName.setText(uid);
					lb.setVisible(false);
					System.out.println("====" + uid);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return null;
			}

		};
		worker.execute();
	}

	public void register() {
		String password = "";
		String phone = pwdRe.getText();
		if (pwd.getPassword() == null || pwd.getPassword().length == 0) {
			JOptionPane.showMessageDialog(null, "请你输入密码");
		} else {

			password = new String(pwd.getPassword());
		}

		String nickname = txtName.getText();

		try {

			if (loginProcess(nickname, password, phone)) {

				System.out.println("Login: (" + nickname + ")第一次生成profile");
				try {
					DBTools.initDatabase(nickname); // 初始化密讯数据库
					Profile profile = Profile.CreateProfile(nickname, SConfig
							.getInstance().getSessionKey()); // 创建该用户的profile文件
					SConfig.getInstance().setProfile(profile);
					MainFrame frame = MainFrame.getInstance();
					setVisible(false);
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "注册失败请重试");
			e.printStackTrace();
		}

	}

	/**
	 * 在窗体关闭之前需要做的事
	 */
	public void beforeClose() {
	}

	/**
	 * 取消按钮事件
	 */
	public void cancle() {
	}

	public static void main(String[] args) {
		RegisterFrame frame = new RegisterFrame();
		frame.setVisible(true);
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
	private boolean loginProcess(String userId, String password, String phone)
			throws Exception {

		// String pwd = new String(password);
		// System.out.println("pwd==" + pwd);
		String result = null;

		result = SLogin.getInstance().register3(password, userId, phone);

		if (result == null || result.equals("Failed")
				|| result.equals("failed")) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean validateMoblie(String phone) {
		int l = phone.length();
		boolean rs = false;
		switch (l) {
		case 7:
			if (matchingText("^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4}$", phone)) {
				rs = true;
			}
			break;
		case 11:
			if (matchingText("^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4,8}$", phone)) {
				rs = true;
			}
			break;
		default:
			rs = false;
			break;
		}
		return rs;
	}

	private static boolean matchingText(String expression, String text) {
		Pattern p = Pattern.compile(expression); // 正则表达式
		Matcher m = p.matcher(text); // 操作的字符串
		boolean b = m.matches();
		return b;
	}

}
