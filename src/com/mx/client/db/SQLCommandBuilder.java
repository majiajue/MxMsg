package com.mx.client.db;


import java.util.Hashtable;



/**
 * SQLCommandBuilder SQL命令生成器工具类
 * @author 
 * @version 1.0.0.1  2009-3-31
 */
public final class SQLCommandBuilder {

	// 保存生成器自身实例 
	private static SQLCommandBuilder sqlCmdBuilder = null;
	
	/**
	 * 私有化默认构造器
	 */
	private SQLCommandBuilder() { }
	
	/**
	 * 使用静态工厂创建类自身实例
	 * @return 类自身实例
	 */
	public static SQLCommandBuilder getInstance() {

		// 确保类自身实例唯一
		if (sqlCmdBuilder == null) {
			sqlCmdBuilder = new SQLCommandBuilder();
		}

		// 返回类自身实例
		return sqlCmdBuilder;
		
	}
	
	/**
	 * 根据SQL命令类型获取SQL语句的方法
	 * @param sqlCmdType SQL命令类型
	 * @param tableName 要操作的特定数据库表名称
	 * @param columnName 要操作的特定数据库列名称清单
	 * @param param SQL参数列表
	 * @param condition SQL条件列表
	 * @return SQL语句的方法
	 */
	public String getSQLCommand(
		SQLCommandType sqlCmdType, 
		String tableName, 
		String[] columnName, 
		Object[] param, 
		Hashtable<String, Object> condition) {
		
		// 校验参数的有效性，若无效则抛出异常
		try {
			SQLValidateHelper.ValidateSQLAndParam(
				sqlCmdType, 
				tableName, 
				columnName, 
				param, 
				condition);
		} catch (SQLParamException ex) {
			System.err.println("异常信息：给定的SQL参数不合法！\r\n" + ex.getMessage());
		}
		
		// 保存拼接的SQL命令字串(Unicode字符\u0020表示空格符)
		StringBuffer sql = new StringBuffer();
		
		// 根据SQL命令类型构建SQL命令
		switch (sqlCmdType) {
			case SELECT:
				// 生成Select命令(仅适用于单表查询)
				sql.append("Select\u0020");
				// 拼接列名
				if(columnName.length==0){
					
					sql.append("\u0020*\u0020");
				}else{
					
					for (int i = 0; i < columnName.length; i++) {
						sql.append((i < columnName.length - 1) 
							? (columnName[i] + ",\u0020") 
							: (columnName[i] + "\u0020"));
					}	
				}
				
				// 拼接表名
				sql.append("From\u0020" + tableName + "");
				// 拼接条件
				sql.append(this.buildCondition(condition));
				//拼接排序
				
				break;
			case INSERT:
				// 生成Insert命令
				sql.append("Insert\u0020Into\u0020");
				// 拼接表名
				sql.append(tableName + "\u0020(");
				// 拼接列名
				for (int i = 0; i < columnName.length; i++) {
					sql.append((i < columnName.length - 1) 
						? (columnName[i] + ",\u0020") 
						: (columnName[i] + ")\u0020"));  
				}
				// 拼接占位符
				sql.append("Values\u0020(");
				for (int j = 0; j < param.length; j++) {
					sql.append((j < param.length - 1) ? "?,\u0020" : "?)");
				}
				break;
			case UPDATE:
				// 生成Update命令
				sql.append("Update\u0020");
				// 拼接表名
				sql.append(tableName + "\u0020Set\u0020");
				// 拼接列名
				for (int i = 0; i < columnName.length; i++) {
					sql.append((i < columnName.length - 1) 
						? (columnName[i] + "\u0020=\u0020?,\u0020") 
						: (columnName[i] + "\u0020=\u0020?"));  
				}
				// 拼接条件
				sql.append(this.buildCondition(condition));
				break;
			case DELETE:
				// 生成Delete命令
				sql.append("Delete\u0020From\u0020");
				// 拼接表名
				sql.append(tableName + "");
				// 拼接条件
				sql.append(this.buildCondition(condition));
				break;
		}
		
		System.err.println("当前SQL语句：\r\n" + sql.toString());
		
		// 返回结果
		return sql.toString();
		
	}
	
	/**
	 * 
	 * @param sqlCmdType
	 * @param tableName
	 * @param columnName
	 * @param param
	 * @param condition
	 * @param orderBySql排序sql
	 * @return
	 */
	public String getOrderBySQLCommand(
			SQLCommandType sqlCmdType, 
			String tableName, 
			String[] columnName, 
			Object[] param, 
			Hashtable<String, Object> condition,
			String orderBySql
			) {
			
			// 校验参数的有效性，若无效则抛出异常
			try {
				SQLValidateHelper.ValidateSQLAndParam(
					sqlCmdType, 
					tableName, 
					columnName, 
					param, 
					condition);
			} catch (SQLParamException ex) {
				System.err.println("异常信息：给定的SQL参数不合法！\r\n" + ex.getMessage());
			}
			
			// 保存拼接的SQL命令字串(Unicode字符\u0020表示空格符)
			StringBuffer sql = new StringBuffer();
			
			// 根据SQL命令类型构建SQL命令
			switch (sqlCmdType) {
				case SELECT:
					// 生成Select命令(仅适用于单表查询)
					sql.append("Select\u0020");
					// 拼接列名
					if(columnName.length==0){
						
						sql.append("\u0020*\u0020");
					}else{
						
						for (int i = 0; i < columnName.length; i++) {
							sql.append((i < columnName.length - 1) 
								? (columnName[i] + ",\u0020") 
								: (columnName[i] + "\u0020"));
						}	
					}
					
					// 拼接表名
					sql.append("From\u0020" + tableName + "");
					// 拼接条件
					sql.append(this.buildCondition(condition));
					//拼接排序
					sql.append(this.buildOrderByCondition(orderBySql));
					break;
			}
			
			System.err.println("当前SQL语句：\r\n" + sql.toString());
			
			// 返回结果
			return sql.toString();
			
		}
	/**
	 * 构建SQL命令查询排序条件字串
	 * @param condition SQL条件列表
	 * @return SQL命令条件字串
	 */
	private String buildOrderByCondition(String orderby) {
		
		// 保存SQL条件部分的字串
		StringBuffer sql = new StringBuffer();
		
		// 拼接条件
		sql.append("\u0020"+orderby+"\u0020");
		
		// 返回结果
		return sql.toString();
		
	}
	
	private String buildCondition(Hashtable<String, Object> condition) {
		
		// 保存SQL条件部分的字串
		StringBuffer sql = new StringBuffer();
		
		// 拼接条件
		if (condition != null && condition.size() != 0) {
			sql.append("\u0020Where\u0020");
			Object[] conditionName = condition.keySet().toArray();
			for (int j = 0; j < conditionName.length; j++) {
				sql.append((j < conditionName.length - 1) 
					? (conditionName[j] + "\u0020=\u0020?\u0020And\u0020")
					: (conditionName[j] + "\u0020=\u0020?"));
			}
		}
		
		// 返回结果
		return sql.toString();
		
	}
	
	
	/**
	 * SQLCommandType SQL命令类型枚举
	 * @author CodingMouse
	 * @version 1.0.0.1  2009-3-31
	 */
	public enum SQLCommandType {
		
		/**
		 * SQL查询数据命令(仅适用于单表查询)
		 */
		SELECT,
		/**
		 * SQL插入数据命令
		 */
		INSERT,
		/**
		 * SQL修改数据命令
		 */
		UPDATE,
		/**
		 * SQL删除数据命令
		 */
		DELETE
		
	}
	
}
