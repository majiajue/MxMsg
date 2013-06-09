package com.mx.client.db;



import java.util.Hashtable;
import java.util.List;

/**
 * IBaseDao 泛型数据库访问接口
 * @author 
 * @version
 * @param <T> 类型参数
 */
public interface IBaseDao<T> {
	
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
		Object[] param);
	/**
	 * executeDelete 执行SQL删除数据命令的方法
	 * @param tableName 要操作的特定数据库表名称
	 * @param condition SQL条件列表
	 * @return true-成功/false-失败
	 */
	public boolean executeDelete(
		String tableName, 
		Hashtable<String, Object> condition);
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
		Hashtable<String, Object> condition);
	/**
	  * executeSelect 执行SQL查询数据命令的方法(仅适用于单表查询)
	  * @param tableName 要操作的特定数据库表名称
	  * @param columnName 要操作的特定数据库列名称清单(Pascal命名法)
	  * @param condition SQL条件列表
	  * @param classPath 待返回的泛型pojo类运行时类名路径字串(包含完整包名+类名，如：TestPojo.class.getName())
	  * @return List<T> 泛型pojo集合;
	  */  
	public List<?> executeSelect(
		String tableName, 
		String[] columnName, 
		Hashtable<String, Object> condition,
		String classPath);

}
