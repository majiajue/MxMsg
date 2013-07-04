package com.mx.client.db;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.mx.client.db.SQLCommandBuilder.SQLCommandType;

/**
 * @author majiajue
 * 
 */
public class GenDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	private static class SingletonHolder {
		public static final GenDao INSTANCE = new GenDao();
	}

	public static GenDao getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * executeInsert 执行SQL添加数据命令的方法
	 * 
	 * @param tableName
	 *            要操作的特定数据库表名称
	 * @param columnName
	 *            要操作的特定数据库列名称清单(Pascal命名法)
	 * @param param
	 *            SQL参数列表
	 * @return true-成功/false-失败
	 */
	public boolean executeInsert(String tableName, String[] columnName, Object[] param) {

		int rowCount = 0; // 保存执行SQL插入数据命令后受影响的行数

		try {

			// 获取数据库连接对象
			this.conn = DBTools.getH2SQLConnection();
			// 获取预编译SQL语句执行对象并根据参数自动构造SQL命令字串
			this.ps = this.conn.prepareStatement(SQLCommandBuilder.getInstance().getSQLCommand(SQLCommandType.INSERT,
					tableName, columnName, param, null));

			// 自动映射SQL参数
			if (param != null && param.length > 0) {
				this.ps = SQLParamHelper.JavaParam2SQLParam(param, this.ps);
			}

			// 执行SQL更新命令并保存返回的受影响行数
			rowCount = this.ps.executeUpdate();

		} catch (SQLException ex) {

			System.err.println("异常信息：执行SQL添加命令时发生错误！\r\n" + ex.getMessage());

		} finally {

			// 释放资源
			this.releaseResource();

		}

		return rowCount > 0;

	}

	/**
	 * executeDelete 执行SQL删除数据命令的方法
	 * 
	 * @param tableName
	 *            要操作的特定数据库表名称
	 * @param condition
	 *            SQL条件列表
	 * @return true-成功/false-失败
	 */
	public boolean executeDelete(String tableName, Hashtable<String, Object> condition) {

		int rowCount = 0; // 保存执行SQL更新命令受影响的行数

		try {

			// 获取数据库连接对象
			this.conn = DBTools.getH2SQLConnection();
			// 获取预编译SQL语句执行对象并根据参数自动构造SQL命令字串
			this.ps = this.conn.prepareStatement(SQLCommandBuilder.getInstance().getSQLCommand(SQLCommandType.DELETE,
					tableName, null, null, condition));

			// 自动映射SQL参数
			if (condition != null && condition.size() > 0) {
				this.ps = SQLParamHelper.JavaParam2SQLParam(condition.values().toArray(), this.ps);
			}

			// 执行SQL更新命令并保存返回的受影响行数
			rowCount = this.ps.executeUpdate();

		} catch (SQLException ex) {

			System.err.println("异常信息：执行SQL删除命令时发生错误！\r\n" + ex.getMessage());

		} finally {

			// 释放资源
			this.releaseResource();

		}

		// 返回结果
		return rowCount > 0;

	}

	/**
	 * executeUpdate 执行SQL修改数据命令的方法
	 * 
	 * @param tableName
	 *            要操作的特定数据库表名称
	 * @param columnName
	 *            要操作的特定数据库列名称清单(Pascal命名法)
	 * @param param
	 *            SQL参数列表
	 * @param condition
	 *            SQL条件列表
	 * @return true-成功/false-失败
	 */
	public boolean executeUpdate(String tableName, String[] columnName, Object[] param,
			Hashtable<String, Object> condition) {

		int rowCount = 0; // 保存执行SQL更新命令受影响的行数

		try {

			// 获取数据库连接对象
			this.conn = DBTools.getH2SQLConnection();
			// 获取预编译SQL语句执行对象并根据参数自动构造SQL命令字串
			this.ps = this.conn.prepareStatement(SQLCommandBuilder.getInstance().getSQLCommand(SQLCommandType.UPDATE,
					tableName, columnName, param, condition));

			// 自动映射SQL参数
			if (param != null && param.length > 0) {
				// 将条件列表添加到SQL参数列表后整体设置参数列表
				if (condition != null && condition.size() > 0) {
					Object[] paramArray = param;
					Object[] conditionArray = condition.values().toArray();
					List<Object> paramList = new Vector<Object>();
					// 添加SQL参数列表
					for (Object objParam : paramArray) {
						if (!paramList.add(objParam)) {
							System.err.println("异常信息：未能将SQL参数列表添加到Update语句！");
						}
					}
					// 添加SQL条件列表
					for (Object objCondition : conditionArray) {
						if (!paramList.add(objCondition)) {
							System.err.println("异常信息：未能将SQL条件列表添加到Update语句！");
						}
					}
					param = paramList.toArray();
				}
				this.ps = SQLParamHelper.JavaParam2SQLParam(param, this.ps);
			}

			// 执行SQL更新命令并保存返回的受影响行数
			rowCount = this.ps.executeUpdate();

		} catch (SQLException ex) {

			System.err.println("异常信息：执行SQL修改命令时发生错误！\r\n" + ex.getMessage());

		} finally {

			// 释放资源
			this.releaseResource();

		}

		// 返回结果
		return rowCount > 0;

	}

	/**
	 * 获取某一字段条记录的值
	 * 
	 * @param tableName
	 * @param columnName
	 * @param condition
	 * @return
	 */
	public String getValue(String tableName, String[] columnName, String valueColumn,
			Hashtable<String, Object> condition) {
		this.conn = DBTools.getH2SQLConnection();
		// 获取预编译SQL语句执行对象并根据参数自动构造SQL命令字串
		String value = "";
		try {
			this.ps = this.conn.prepareStatement(SQLCommandBuilder.getInstance().getSQLCommand(SQLCommandType.SELECT,
					tableName, columnName, null, condition));
			if (condition != null && condition.size() > 0) {
				this.ps = SQLParamHelper.JavaParam2SQLParam(condition.values().toArray(), this.ps);
			}

			// 执行SQL更新命令并保存取回的结果集对象
			this.rs = this.ps.executeQuery();
			rs.last();
			int a = rs.getRow();
			if (a == 1) {
				value = rs.getString(valueColumn.toUpperCase());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 自动映射SQL参数
		return value;
	};

	/**
	 * 获取某一字段条记录的值
	 * 
	 * @param tableName
	 * @param columnName
	 * @param condition
	 * @return
	 */
	public String getOrderByValue(String tableName, String[] columnName, String valueColumn,
			Hashtable<String, Object> condition, String orderBySQL) {
		this.conn = DBTools.getH2SQLConnection();
		// 获取预编译SQL语句执行对象并根据参数自动构造SQL命令字串
		String value = "";
		try {
			this.ps = this.conn.prepareStatement(SQLCommandBuilder.getInstance().getOrderBySQLCommand(
					SQLCommandType.SELECT, tableName, columnName, null, condition, orderBySQL));
			if (condition != null && condition.size() > 0) {
				this.ps = SQLParamHelper.JavaParam2SQLParam(condition.values().toArray(), this.ps);
			}

			// 执行SQL更新命令并保存取回的结果集对象
			this.rs = this.ps.executeQuery();
			rs.last();
			int a = rs.getRow();
			if (a == 1) {
				value = rs.getString(valueColumn.toUpperCase());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 自动映射SQL参数
		return value;
	};

	/**
	 * 获取blob字段
	 * 
	 * @param tableName
	 * @param columnName
	 * @param condition
	 * @return
	 */
	public Blob getBlobValue(String tableName, String[] columnName, String valueColumn,
			Hashtable<String, Object> condition) {
		this.conn = DBTools.getH2SQLConnection();
		// 获取预编译SQL语句执行对象并根据参数自动构造SQL命令字串
		Blob blob = null;
		try {
			this.ps = this.conn.prepareStatement(SQLCommandBuilder.getInstance().getSQLCommand(SQLCommandType.SELECT,
					tableName, columnName, null, condition));
			if (condition != null && condition.size() > 0) {
				this.ps = SQLParamHelper.JavaParam2SQLParam(condition.values().toArray(), this.ps);
			}

			// 执行SQL更新命令并保存取回的结果集对象
			this.rs = this.ps.executeQuery();
			rs.last();
			int a = rs.getRow();
			if (a == 1) {
				blob = rs.getBlob(valueColumn.toUpperCase());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 自动映射SQL参数
		return blob;
	};

	/**
	 * releaseResource 释放所有数据库访问对象资源
	 */
	private void releaseResource() {

		if (this.rs != null) {
			try {
				this.rs.close();
			} catch (SQLException ex) {
				System.err.println("异常信息：关闭结果集对象错误！\r\n" + ex.getMessage());
			}
		}
		if (this.ps != null) {
			try {
				this.ps.close();
			} catch (SQLException ex) {
				System.err.println("异常信息：关闭SQL命令执行对象错误！\r\n" + ex.getMessage());
			}
		}
		if (this.conn != null) {
			try {
				if (!this.conn.isClosed()) {
					this.conn.close();
				}
			} catch (SQLException ex) {
				System.err.println("异常信息：关闭数据库连接错误！\r\n" + ex.getMessage());
			}
		}

	}

	/**
	 * executeInsert 执行SQL添加数据命令的方法并且返回主键
	 * 
	 * @param tableName
	 *            要操作的特定数据库表名称
	 * @param columnName
	 *            要操作的特定数据库列名称清单(Pascal命名法)
	 * @param param
	 *            SQL参数列表
	 * @return true-成功/false-失败
	 */
	public int executeInsertRId(String tableName, String[] columnName, Object[] param) {

		int rowId = 0; // 保存执行SQL插入数据命令后受影响的行数

		try {

			// 获取数据库连接对象
			this.conn = DBTools.getH2SQLConnection();
			// 获取预编译SQL语句执行对象并根据参数自动构造SQL命令字串
			this.ps = this.conn.prepareStatement(
					SQLCommandBuilder.getInstance().getSQLCommand(SQLCommandType.INSERT, tableName, columnName, param,
							null), Statement.RETURN_GENERATED_KEYS);

			// 自动映射SQL参数
			if (param != null && param.length > 0) {
				this.ps = SQLParamHelper.JavaParam2SQLParam(param, this.ps);
			}

			// 执行SQL更新命令并保存返回的受影响行数
			this.ps.executeUpdate();

			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {

				rowId = rs.getInt(1);

			}

		} catch (SQLException ex) {

			System.err.println("异常信息：执行SQL添加命令时发生错误！\r\n" + ex.getMessage());

		} finally {

			// 释放资源
			this.releaseResource();

		}

		return rowId;

	}

}
