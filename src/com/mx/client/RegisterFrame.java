package com.mx.client;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;

import com.mx.client.webtools.PropertiesUtils;
import com.mx.client.webtools.SLogin;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 * 注册面板填写基本信息
 * @author majiajue
 *
 */
public class RegisterFrame extends JFrame {

	public JPanel contentPane;
	public JTextField txtName;
	public JPasswordField pwd;
	public JTextField pwdRe;
	public JButton btnRegister;
	/**
	 * Create the frame.
	 */
	public RegisterFrame()
	{
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) 
			{
				beforeClose();
			}
		});
		SLogin login = SLogin.getInstance();
		String uid ="";
		try {
			String mSkey = login.register1();
			PropertiesUtils.getInstance().setSessionKey(mSkey);
			uid = login.getNewUid();
			System.out.println("===="+uid);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setIconImage(Toolkit.getDefaultToolkit().getImage(RegisterFrame.class.getResource("/com/mx/client/image/QQ_64.png")));
		setTitle("QQ2013\u6CE8\u518C");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(592, 533);
		contentPane = new MyPanel("/com/mx/client/image/registerBGimg2.jpg");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbUserName = new JLabel("\u7528\u6237\u540D");
		lbUserName.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lbUserName.setBounds(38, 45, 54, 24);
		contentPane.add(lbUserName);
		
		JLabel lbPwd = new JLabel("\u5BC6\u7801");
		lbPwd.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lbPwd.setBounds(38, 96, 54, 30);
		lbPwd.setToolTipText("请输入你的密码(必填向)");
		contentPane.add(lbPwd);
		
		JLabel lbPwdre = new JLabel("请输入你的手机号:");
		lbPwdre.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lbPwdre.setToolTipText("请输入你的手机号");
		lbPwdre.setBounds(22, 138, 79, 45);
		contentPane.add(lbPwdre);
		
//		JLabel lbEmail = new JLabel("E-mail");
//		lbEmail.setFont(new Font("微软雅黑", Font.PLAIN, 18));
//		lbEmail.setBounds(33, 234, 59, 28);
//		contentPane.add(lbEmail);
		
//		JLabel lbBirthday = new JLabel("\u751F\u65E5");
//		lbBirthday.setFont(new Font("微软雅黑", Font.PLAIN, 18));
//		lbBirthday.setBounds(38, 285, 46, 24);
//		contentPane.add(lbBirthday);
		
//		JLabel lbSignature = new JLabel("\u4E2A\u6027\u7B7E\u540D");
//		lbSignature.setFont(new Font("微软雅黑", Font.PLAIN, 18));
//		lbSignature.setBounds(22, 332, 79, 28);
//		contentPane.add(lbSignature);
//		
		btnRegister = new JButton("\u514D\u8D39\u6CE8\u518C");
		btnRegister.setForeground(new Color(255, 255, 255));
		btnRegister.setBackground(new Color(0, 100, 0));
		btnRegister.setFont(new Font("微软雅黑", Font.BOLD, 28));
		btnRegister.setBounds(57, 431, 161, 60);
		contentPane.add(btnRegister);
		JButton btnCancel = new JButton("\u53D6\u6D88");
		btnCancel.setForeground(new Color(255, 250, 250));
		btnCancel.setBackground(new Color(106, 90, 205));
		btnCancel.setFont(new Font("微软雅黑", Font.BOLD, 28));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				cancle();
			}
		});
		btnCancel.setBounds(248, 431, 100, 60);
		contentPane.add(btnCancel);
		txtName = new JTextField();
		txtName.setText(uid);
		txtName.setEditable(false);
		txtName.setBounds(111, 40, 217, 35);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		pwd = new JPasswordField();
		pwd.setBounds(111, 92, 217, 35);
		contentPane.add(pwd);
		
		pwdRe = new JTextField();
		pwdRe.setBounds(113, 138, 215, 35);
		pwdRe.setToolTipText("请输入你的手机号");
		contentPane.add(pwdRe);
		
//		txtEmail = new JTextField();
//		txtEmail.setColumns(10);
//		txtEmail.setBounds(111, 231, 217, 35);
//		contentPane.add(txtEmail);
//		
//		comGender = new JComboBox();
//		comGender.setModel(new DefaultComboBoxModel(new String[] {"男", "女"}));
//		comGender.setBounds(111, 185, 59, 35);
//		contentPane.add(comGender);
//		
//		JLabel lbGender = new JLabel("\u6027\u522B");
//		lbGender.setFont(new Font("微软雅黑", Font.PLAIN, 18));
//		lbGender.setBounds(38, 190, 46, 24);
//		contentPane.add(lbGender);
		
//		txtbirthday = new JTextField();
//		txtbirthday.setBounds(111, 281, 217, 35);
//		contentPane.add(txtbirthday);
//		txtbirthday.setColumns(10);
		
//		comboBoxHeadImage = new JComboBox();
//		comboBoxHeadImage.setBounds(375, 193, 138, 117);
//		contentPane.add(comboBoxHeadImage);
//		
//		JLabel lblHead = new JLabel("\u5934\u50CF\u9009\u62E9\uFF1A");
//		lblHead.setFont(new Font("微软雅黑", Font.PLAIN, 18));
//		lblHead.setBounds(375, 139, 100, 32);
//		contentPane.add(lblHead);
	}
	
	/**
	 * 在窗体关闭之前需要做的事
	 */
	public void beforeClose()
	{}
	/**
	 * 取消按钮事件
	 */
	public void cancle()
	{}
}
