package com.mx.client.db;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;

/**
 * SQLParamHelper SQL参数辅助器工具类
 * @author CodingMouse
 * @version 1.0.0.1 2009-3-26
 */
public final class SQLParamHelper {

	/**
	  * 自动将Java数据类型参数映射转换为SQL参数
	  * @param param Java数据类型参数数组
	  * @param ps SQL命令执行对象
	  * @return 返回自动匹配添加好SQL参数的SQl命令执行对象
	  */
	 public static PreparedStatement JavaParam2SQLParam(Object[] param, PreparedStatement ps) {
		
		 // 使用Class.isInstance()方法判定指定的 Object 
		 // 是否与此 Class 所表示的对象赋值兼容。
		 // 此方法是 Java 语言 instanceof 运算符的动态等效方法。
		 // 如果当前SQL命令包含参数则进行Java数据类型与SQL命令参数的人工映射
		 if (param != null && param.length > 0) {
			 try {
			 	 for (int i = 0; i < param.length; i++) {
					 // 将第i个参数的Java数据类型通过其对应的包装器类人工映射并设定SQL命令参数
					 if (Boolean.class.isInstance(param[i])) {                  // 映射boolean类型
						 ps.setBoolean(i + 1, Boolean.parseBoolean(param[i].toString()));
					 } else if (Byte.class.isInstance(param[i])) {              // 映射byte类型
						 ps.setByte(i + 1, Byte.parseByte(param[i].toString()));
					 } else if (byte[].class.isInstance(param[i])) {
						 ps.setBytes(i + 1, (param[i].toString()).getBytes());  // 映射byte[]类型
					 } else if (Character.class.isInstance(param[i])
						 || String.class.isInstance(param[i])) {                // 映射char和String类型
						 ps.setString(i + 1, String.valueOf(param[i]));
					 } else if (Short.class.isInstance(param[i])) {             // 映射short类型
						 ps.setShort(i + 1, Short.parseShort(param[i].toString()));
				 	 } else if (Integer.class.isInstance(param[i])) {           // 映射int类型
						 ps.setInt(i + 1, Integer.parseInt(param[i].toString()));
				 	 } else if (Long.class.isInstance(param[i])) {              // 映射long类型
				 		 ps.setLong(i + 1, Long.parseLong(param[i].toString()));
					 } else if (Float.class.isInstance(param[i])) {             // 映射float类型
						 ps.setFloat(i + 1, Float.parseFloat(param[i].toString()));
					 } else if (Double.class.isInstance(param[i])) {            // 映射double类型
						 ps.setDouble(i + 1, Double.parseDouble(param[i].toString()));
					 } else if (BigDecimal.class.isInstance(param[i])) {        // 映射BigDecimal类型
						 ps.setBigDecimal(i + 1, new BigDecimal(param[i].toString()));
					 } else if (Date.class.isInstance(param[i])) {              // 映射Date类型
			 			 ps.setDate(i + 1, Date.valueOf(param[i].toString()));
					 } else if (Time.class.isInstance(param[i])) {              // 映射Time类型
			 			 ps.setTime(i + 1, Time.valueOf(param[i].toString()));
					 } else if (Timestamp.class.isInstance(param[i])) {         // 映射Timestamp类型
			 			 ps.setTimestamp(i + 1, Timestamp.valueOf(param[i].toString()));
					 } else {
						 throw new Exception("Java程序中包含不支持的自动映射数据类型：" + param[i].getClass().getName());
					 }
			 	 }
			 } catch(SQLException ex) {
		 		 System.err.println("异常信息：特定数据库访问及其他相关错误！\r\n" + ex.getMessage());
		 	 } catch (Exception ex) {
		 		System.err.println("异常信息：程序兼容问题！\r\n" + ex.getMessage());
			 }
			 
		 }
		 
		 // 返回结果
		 return ps;
		 
	 }
	
	 /**
	  * 自动将SQL数据类型参数映射转换为Java数据类型
	  * @param <T> 泛型类型参数
	  * @param rsmd 关于 ResultSet 对象中列的类型和属性信息的 ResultSetMetaData 对象
	  * @param pojoClass 利用Java反射机制查找对应 pojo 的Class
	  * @param rs 结果集对象
	  * @return 泛型 pojo 实例
	  */
	 @SuppressWarnings("unchecked")  // 禁用对此方法的类型安全检查
	public static <T> T SQLParam2JavaParam(ResultSetMetaData rsmd, Class<?> pojoClass, ResultSet rs) {
		  
		 // 定义泛型 pojo 实例
		 T pojo = null;
		 
		 try {
			 
			 // 生成单个泛型 pojo 实例
			 pojo = (T)pojoClass.newInstance();
			 
			 // 遍历数据集的每一列，通过共同遵守的Pascal命名规则反射查找并执行对应 
			 // pojo 类的赋值(getter)方法以实现结果集到pojo泛型集合的自动映射
			 for (int i = 1; i <= rsmd.getColumnCount(); i++) {
	
				 // 取得第i列列名
				 String setMethodName = rsmd.getColumnName(i);
				 // 通过命名规则处理第i列列名,取得 pojo 中对应字段的取值(setter)方法名
				 setMethodName = "set"
					+ setMethodName.substring(0, 1).toUpperCase()
					+ setMethodName.substring(1);
				 // 取得第i列的数据类型
				 int dbType = rsmd.getColumnType(i);
				 // 当前反射方法
				 Method method = null;
				 // 对应第i列的SQL数据类型人工映射到对应的Java数据类型，
				 // 并反射执行该列的在 pojo 中对应属性的 setter 方法完成赋值
				 if (dbType == Types.TINYINT) {
					 method = pojoClass.getMethod(setMethodName, byte.class);
					 method.invoke(pojo, rs.getByte(i));
				 } else if (dbType == Types.SMALLINT) {
					 method = pojoClass.getMethod(setMethodName, short.class);
					 method.invoke(pojo, rs.getShort(i));
				 } else if (dbType == Types.INTEGER) {
					 method = pojoClass.getMethod(setMethodName, int.class);
					 method.invoke(pojo, rs.getInt(i));
				 } else if (dbType == Types.BIGINT) {
					 method = pojoClass.getMethod(setMethodName, long.class);
					 method.invoke(pojo, rs.getLong(i));
				 } else if (dbType == Types.FLOAT
						 || dbType == Types.REAL) {
					 method = pojoClass.getMethod(setMethodName, float.class);
					 method.invoke(pojo, rs.getFloat(i));
				 } else if (dbType == Types.DOUBLE) {
					 method = pojoClass.getMethod(setMethodName, double.class);
					 method.invoke(pojo, rs.getDouble(i));
				 } else if (dbType == Types.DECIMAL
						 || dbType == Types.NUMERIC) {
					 method = pojoClass.getMethod(setMethodName, BigDecimal.class);
					 method.invoke(pojo, rs.getBigDecimal(i));
				 } else if (dbType == Types.BIT) {
					 method = pojoClass.getMethod(setMethodName, boolean.class);
					 method.invoke(pojo, rs.getBoolean(i));
				 } else if (dbType == Types.CHAR
						 || dbType == Types.VARCHAR
						 || dbType == Types.LONGVARCHAR
						 || dbType == Types.CLOB) {
					 method = pojoClass.getMethod(setMethodName, String.class);
					 method.invoke(pojo, rs.getString(i));
				 } else if (dbType == Types.DATE) {       // 继承于 java.util.Date 类
					 method = pojoClass.getMethod(setMethodName, Date.class);
					 method.invoke(pojo, rs.getDate(i));
				 } else if (dbType == Types.TIME) {       // 继承于 java.util.Date 类
					 method = pojoClass.getMethod(setMethodName, Time.class);
					 method.invoke(pojo, rs.getTime(i));
				 } else if (dbType == Types.TIMESTAMP) {  // 继承于 java.util.Date 类
					 method = pojoClass.getMethod(setMethodName, Timestamp.class);
					 method.invoke(pojo, rs.getTimestamp(i));
				 } else if (dbType == Types.BINARY
						 || dbType == Types.VARBINARY
						 || dbType == Types.LONGVARBINARY
						 || dbType == Types.BLOB) {
					 method = pojoClass.getMethod(setMethodName, byte[].class);
					 method.invoke(pojo, rs.getBytes(i));
				 } else {
					 throw new Exception("数据库中包含不支持的自动映射数据类型：" + dbType);
				 }
			 }
		 } catch (InstantiationException ex) {
			 System.err.println("异常信息：参数错误，指定的类对象无法被 Class 类中的 newInstance 方法实例化！\r\n" + ex.getMessage());
		 } catch (NoSuchMethodException ex) {
			 System.err.println("异常信息：参数错误，无法找到某一特定的方法！\r\n" + ex.getMessage());
		 } catch (IllegalAccessException ex) {
			 System.err.println("异常信息：参数错误，对象定义无法访问，无法反射性地创建一个实例！\r\n" + ex.getMessage());
		 } catch (InvocationTargetException ex) {
			 System.err.println("异常信息：参数错误，由调用方法或构造方法所抛出异常的经过检查的异常！\r\n" + ex.getMessage());
		 } catch (SecurityException ex) {
			 System.err.println("异常信息：参数错误，安全管理器检测到安全侵犯！\r\n" + ex.getMessage());
		 } catch (IllegalArgumentException ex) {
			 System.err.println("异常信息：参数错误，向方法传递了一个不合法或不正确的参数！\r\n" + ex.getMessage());
		 } catch (SQLException ex) {
			 System.err.println("异常信息：参数错误，获取数据库连接对象错误！\r\n" + ex.getMessage());
		 } catch (Exception ex) {
		 	 System.err.println("异常信息：程序兼容问题！\r\n" + ex.getMessage());
		 }
			 
		 // 返回结果
		 return pojo;
	 }
	 
		public static <T> T SQLParam2JavaParam(String[] classColumn, Class<?> pojoClass, ResultSet rs) {
			  
			 // 定义泛型 pojo 实例
			 T pojo = null;
			 
			 try {
				 
				 // 生成单个泛型 pojo 实例
				 pojo = (T)pojoClass.newInstance();
				 
				 // 遍历数据集的每一列，通过共同遵守的Pascal命名规则反射查找并执行对应 
				 // pojo 类的赋值(getter)方法以实现结果集到pojo泛型集合的自动映射
				 for (int i = 1; i <= classColumn.length; i++) {
		
					 // 取得第i列列名
					 String setMethodName = classColumn[i];
					 // 通过命名规则处理第i列列名,取得 pojo 中对应字段的取值(setter)方法名
					 setMethodName = "set"
						+ setMethodName.substring(0, 1).toUpperCase()
						+ setMethodName.substring(1);
					 // 取得第i列的数据类型
					
					 // 当前反射方法
					 Method method = null;
					 // 对应第i列的SQL数据类型人工映射到对应的Java数据类型，
					 // 并反射执行该列的在 pojo 中对应属性的 setter 方法完成赋值
					
						 method = pojoClass.getMethod(setMethodName, String.class);
						 method.invoke(pojo, rs.getString(i));
					 
				 }
			 } catch (InstantiationException ex) {
				 System.err.println("异常信息：参数错误，指定的类对象无法被 Class 类中的 newInstance 方法实例化！\r\n" + ex.getMessage());
			 } catch (NoSuchMethodException ex) {
				 System.err.println("异常信息：参数错误，无法找到某一特定的方法！\r\n" + ex.getMessage());
			 } catch (IllegalAccessException ex) {
				 System.err.println("异常信息：参数错误，对象定义无法访问，无法反射性地创建一个实例！\r\n" + ex.getMessage());
			 } catch (InvocationTargetException ex) {
				 System.err.println("异常信息：参数错误，由调用方法或构造方法所抛出异常的经过检查的异常！\r\n" + ex.getMessage());
			 } catch (SecurityException ex) {
				 System.err.println("异常信息：参数错误，安全管理器检测到安全侵犯！\r\n" + ex.getMessage());
			 } catch (IllegalArgumentException ex) {
				 System.err.println("异常信息：参数错误，向方法传递了一个不合法或不正确的参数！\r\n" + ex.getMessage());
			 } catch (SQLException ex) {
				 System.err.println("异常信息：参数错误，获取数据库连接对象错误！\r\n" + ex.getMessage());
			 } catch (Exception ex) {
			 	 System.err.println("异常信息：程序兼容问题！\r\n" + ex.getMessage());
			 }
				 
			 // 返回结果
			 return pojo;
		 }
		 
	 
}