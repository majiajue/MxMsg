package com.mx.client.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ��ݿ����ӹ�����
 * 
 * @author majiajue
 * 
 */
public class DBTools {
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private static Connection connection = null;
	private static String H2_URL = "jdbc:h2:/data/MX;";// ��ݿ����ӵ�ַ
	private static final String H2_DRIVER = "org.h2.Driver"; // H2 ��
	private static final String H2_USERNAME = "sa";// ��ݿ��û���
	private static final String H2_PASSWORD = "";// ��ݿ�����

	/**
	 * ��ȡH2SQL��ݿ�����
	 * 
	 * @return ��ݿ������
	 */

	public static Connection getH2SQLConnection() {
		try {
			Class.forName(H2_DRIVER);
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Connection conn = null;
		try {
			// �Զ�����������ʵ�����Զ�����DriverManager��ע����
			conn = DriverManager.getConnection(H2_URL, H2_USERNAME, H2_PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}

	/**
	 * ִ��
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public static void excuteSql(String sql) throws SQLException {
		connection = getH2SQLConnection();
		try {
			connection.setAutoCommit(true);
			statement = connection.createStatement();
			// statement.execute(sql);
			int a = statement.executeUpdate(sql);
			System.out.println(a);
			System.out.println("ִ�гɹ�");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connection.rollback();
		} finally {
			if (statement != null) {
				statement.close();
			}

			if (connection != null) {

				connection.close();
			}
		}

	}

	public static int findData(String sql) {
		connection = getH2SQLConnection();
		PreparedStatement ps = null;
		int a = 0;
		try {
			ps = connection.prepareStatement(sql);
			resultSet = ps.executeQuery();
			resultSet.last();
			a = resultSet.getRow();
			System.out.println("===" + a);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return 0;
		} finally {
			if (resultSet != null) {

				try {
					resultSet.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (connection != null) {

				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return a;
	}

	public static String TABLE_WONDERLAND = "wonderland_";

	public static String COL_KEY = "KEY";
	public static String COL_vALUE = "VALUE";

	public static void CreateUserProfile(String MyPeerid) {

		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_WONDERLAND + MyPeerid
				+ " (ID INT PRIMARY KEY AUTO_INCREMENT, " + COL_KEY + " VARCHAR2(255) NOT NULL, " + COL_vALUE
				+ " VARCHAR2(4000) NOT NULL)";
		try {
			excuteSql(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 初始化密讯的数据库结构
	 * 
	 * @param userId
	 * @throws SQLException
	 */
	public static void initDatabase(String userId) throws SQLException {
		DBTools.CreateUserProfile(userId);
		DBTools.excuteSql(DBDataSQL.SQL_CREATE_TB_MESSAGE);
		DBTools.excuteSql(DBDataSQL.SQL_CREATE_MESSAGE_INDEX);
		DBTools.excuteSql(DBDataSQL.SQL_CREATE_TB_LOGIN);
		DBTools.excuteSql(DBDataSQL.SQL_CREATE_LOGIN_INDEX);
		DBTools.excuteSql(DBDataSQL.SQL_CREATE_TB_PEERS);
		DBTools.excuteSql(DBDataSQL.SQL_CREATE_PEERS_INDEX);
		DBTools.excuteSql(DBDataSQL.SQL_CREATE_PREFERENCE);
		DBTools.excuteSql(DBDataSQL.SQL_CREATE_PREFERENCE_INDEX);
	}

}
