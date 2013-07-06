package com.mx.client.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 锟斤拷菘锟斤拷锟斤拷庸锟斤拷锟斤拷锟�
 * 
 * @author majiajue
 * 
 */
public class DBTools {
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private static Connection connection = null;
	private static String H2_URL = "jdbc:h2:/data/MX;";// 锟斤拷菘锟斤拷锟斤拷拥锟街�
	private static final String H2_DRIVER = "org.h2.Driver"; // H2 锟斤拷
	private static final String H2_USERNAME = "sa";// 锟斤拷菘锟斤拷没锟斤拷锟�
	private static final String H2_PASSWORD = "";// 锟斤拷菘锟斤拷锟斤拷锟�

	/**
	 * 锟斤拷取H2SQL锟斤拷菘锟斤拷锟斤拷锟�
	 * 
	 * @return 锟斤拷菘锟斤拷锟斤拷锟斤拷
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
			// 锟皆讹拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷实锟斤拷锟斤拷锟皆讹拷锟斤拷锟斤拷DriverManager锟斤拷注锟斤拷锟斤拷
			conn = DriverManager.getConnection(H2_URL, H2_USERNAME, H2_PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}

	/**
	 * 执锟斤拷
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
			System.out.println("执锟叫成癸拷");

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
	 * 鍒濆鍖栧瘑璁殑鏁版嵁搴撶粨鏋�
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
