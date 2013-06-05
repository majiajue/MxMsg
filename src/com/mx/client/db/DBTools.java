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
			conn = DriverManager.getConnection(H2_URL, H2_USERNAME,
					H2_PASSWORD);
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
			statement = connection.createStatement();
			statement.execute(sql);
			connection.commit();
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
   public static ResultSet findData(String sql){
	   connection = getH2SQLConnection();
	   PreparedStatement ps =null;
	   try {
		ps = connection.prepareStatement(sql);
		resultSet = ps.executeQuery();
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{
		if(resultSet!=null)
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}
		}
		
		if(connection!=null){
			
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	   return resultSet;
   }

}
