package com.mx.client.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.Driver;

/**
 * 数据库连接工具类
 * 
 * @author majiajue
 * 
 */
public class DBTools {
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	private static Connection connection = null;
	private static String H2_URL = "jdbc:h2:/data/MX;";// 数据库连接地址
	private static final String H2_DRIVER = "org.h2.Driver"; // H2 驱动
	private static final String H2_USERNAME = "sa";// 数据库用户名
	private static final String H2_PASSWORD = "";// 数据库密码

	/**
	 * 获取H2SQL数据库连接
	 * 
	 * @return 数据库的连接
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
			// 自动创建驱动程序的实例且自动调用DriverManager来注册它
			conn = DriverManager
					.getConnection(H2_URL, H2_USERNAME, H2_PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}

	/**
	 * 执行
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public static void excuteSql(String sql) throws SQLException {
		connection = getH2SQLConnection();
		try {
			connection.setAutoCommit(true);
			statement = connection.createStatement();
			//statement.execute(sql);
			int a=statement.executeUpdate(sql);
			System.out.println(a);
			System.out.println("执行成功");
			
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
		int a=0;
		try {
			ps = connection.prepareStatement(sql);
			resultSet = ps.executeQuery();
			resultSet.last();
			a= resultSet.getRow();
            System.out.println("==="+a);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return 0;
		} finally {
			if(resultSet!=null){
				
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

		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_WONDERLAND
				+ MyPeerid + " (ID INT PRIMARY KEY AUTO_INCREMENT, "
				+ COL_KEY + " VARCHAR2(255) NOT NULL, " + COL_vALUE
				+ " VARCHAR2(4000) NOT NULL)";
		try {
			excuteSql(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
