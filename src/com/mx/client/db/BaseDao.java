package com.mx.client.db;

import java.util.Hashtable;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.mx.client.db.SQLCommandBuilder.SQLCommandType;



/**
 * BaseDao 泛型数据库访问基类
 * @author CodingMouse
 * @version 1.0.0.1 2009-3-26
 * @param <T> 类型参数
 */
public class BaseDao<T> implements IBaseDao<T> {

	private Connection conn = null;       // 数据库连接对象
	private PreparedStatement ps = null;  // 预编译的SQL命令执行对象
	private ResultSet rs = null;          // 结果集对象
	
	/**
	 * executeInsert 执行SQL添加数据命令的方法
	 * @param tableName 要操作的特定数据库表名称
	 * @param columnName 要操作的特定数据库列名称清单(Pascal命名法)
	 * @param param SQL参数列表
	 * @return true-成功/false-失败
	 */
	public boolean executeInsert(
		String tableName, 
		String[] columnName, 
		Object[] param) {
		
		int rowCount = 0;  // 保存执行SQL插入数据命令后受影响的行数
		
		try {
			
			// 获取数据库连接对象
			this.conn = DBTools.getH2SQLConnection();
			// 获取预编译SQL语句执行对象并根据参数自动构造SQL命令字串
			this.ps = this.conn.prepareStatement(
				SQLCommandBuilder.getInstance().getSQLCommand(
					SQLCommandType.INSERT, 
					tableName, 
					columnName, 
					param, 
					null));
			 
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
	 * @param tableName 要操作的特定数据库表名称
	 * @param condition SQL条件列表
	 * @return true-成功/false-失败
	 */
	public boolean executeDelete(
		String tableName, 
		Hashtable<String, Object> condition) {
		
		int rowCount = 0; // 保存执行SQL更新命令受影响的行数
		 
		try {
			 
			// 获取数据库连接对象
			this.conn = DBTools.getH2SQLConnection();
			// 获取预编译SQL语句执行对象并根据参数自动构造SQL命令字串
			this.ps = this.conn.prepareStatement(
				SQLCommandBuilder.getInstance().getSQLCommand(
					SQLCommandType.DELETE, 
					tableName, 
					null, 
					null, 
					condition));
			 
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
	 * @param tableName 要操作的特定数据库表名称
	 * @param columnName 要操作的特定数据库列名称清单(Pascal命名法)
	 * @param param SQL参数列表
	 * @param condition SQL条件列表
	 * @return true-成功/false-失败
	 */
	public boolean executeUpdate(
		String tableName, 
		String[] columnName, 
		Object[] param, 
		Hashtable<String, Object> condition) {
		
		int rowCount = 0; // 保存执行SQL更新命令受影响的行数
		 
		try {
			 
			// 获取数据库连接对象
			this.conn = DBTools.getH2SQLConnection();
			// 获取预编译SQL语句执行对象并根据参数自动构造SQL命令字串
			this.ps = this.conn.prepareStatement(
				SQLCommandBuilder.getInstance().getSQLCommand(
					SQLCommandType.UPDATE, 
					tableName, 
					columnName, 
					param, 
					condition));
			 
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
	  * executeSelect 执行SQL查询数据命令的方法(仅适用于单表查询)
	  * @param tableName 要操作的特定数据库表名称
	  * @param columnName 要操作的特定数据库列名称清单(Pascal命名法)
	  * @param condition SQL条件列表
	  * @param classPath 待返回的泛型pojo类运行时类名路径字串(包含完整包名+类名，如：TestPojo.class.getName())
	  * @return List<T> 泛型pojo集合;
	  */  
	public List<T> executeSelect(
		String tableName, 
		String[] columnName, 
		Hashtable<String, Object> condition,
		String classPath) {
		
		List<T> pojoSet = null;     // 泛型pojo集合
		 
		try {
			// 获取数据库连接对象
			this.conn = DBTools.getH2SQLConnection();
			// 获取预编译SQL语句执行对象并根据参数自动构造SQL命令字串
			this.ps = this.conn.prepareStatement(
				SQLCommandBuilder.getInstance().getSQLCommand(
				SQLCommandType.SELECT, 
				tableName, 
				columnName, 
				null, 
				condition));
			
			// 自动映射SQL参数
			if (condition != null && condition.size() > 0) {
				this.ps = SQLParamHelper.JavaParam2SQLParam(condition.values().toArray(), this.ps);
			}

			// 执行SQL更新命令并保存取回的结果集对象
			this.rs = this.ps.executeQuery();

			// 获取关于 ResultSet 对象中列的类型和属性信息的 ResultSetMetaData 元数据集对象
			ResultSetMetaData rsmd = rs.getMetaData();
			// 利用Java反射机制查找对应 pojo 的Class
			Class<?> pojoClass = Class.forName(classPath);
			// 创建新的线程安全的 pojoSet (pojo集合) 实例
			pojoSet = new Vector<T>();
			 
			// 利用Java反射机制读取取回的结果集并自动映射为对应的 pojo 实例
			while (this.rs.next()) {
				
				// 生成单个 pojo 实例
				T pojo = SQLParamHelper.SQLParam2JavaParam(rsmd, pojoClass, rs);

				// 将自动映射完毕的 pojo 实例封装到 pojoSet 泛型集合
				pojoSet.add(pojo);
			}
			 
		} catch (SQLException ex) {
			System.err.println("异常信息：获取数据库连接对象错误！\r\n" + ex.getMessage());
		} catch (ClassNotFoundException ex) {
			System.err.println("异常信息：无法找到指定的 pojo 类！\r\n" + ex.getMessage()); 
		} finally {
			// 释放资源
	 		this.releaseResource();
		}

		// 返回 pojoSet 泛型集合
		return pojoSet;

	}
	
	/**
	 * releaseResource 释放所有数据库访问对象资源
	 */
	private void releaseResource() {
		 
		if(this.rs != null) {
			try {
				this.rs.close();
			} catch (SQLException ex) {
				System.err.println("异常信息：关闭结果集对象错误！\r\n" + ex.getMessage());
			}
		}
		if(this.ps != null) {
			try {
				this.ps.close();
			} catch (SQLException ex) {
				System.err.println("异常信息：关闭SQL命令执行对象错误！\r\n" + ex.getMessage());
			}
		}
		if(this.conn != null) {
			try{	
				if (!this.conn.isClosed()) {
					this.conn.close();
				}
			} catch (SQLException ex){
				System.err.println("异常信息：关闭数据库连接错误！\r\n" + ex.getMessage());
			}
		}
		 
	}
	
}
