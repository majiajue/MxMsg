package com.mx.client.db;


import java.util.Hashtable;



/**
 * SQLCommandBuilder SQL����������������
 * @author 
 * @version 1.0.0.1  2009-3-31
 */
public final class SQLCommandBuilder {

	// ��������������ʵ�� 
	private static SQLCommandBuilder sqlCmdBuilder = null;
	
	/**
	 * ˽�л�Ĭ�Ϲ�����
	 */
	private SQLCommandBuilder() { }
	
	/**
	 * ʹ�þ�̬��������������ʵ��
	 * @return ������ʵ��
	 */
	public static SQLCommandBuilder getInstance() {

		// ȷ��������ʵ��Ψһ
		if (sqlCmdBuilder == null) {
			sqlCmdBuilder = new SQLCommandBuilder();
		}

		// ����������ʵ��
		return sqlCmdBuilder;
		
	}
	
	/**
	 * ����SQL�������ͻ�ȡSQL���ķ���
	 * @param sqlCmdType SQL��������
	 * @param tableName Ҫ�������ض����ݿ������
	 * @param columnName Ҫ�������ض����ݿ��������嵥
	 * @param param SQL�����б�
	 * @param condition SQL�����б�
	 * @return SQL���ķ���
	 */
	public String getSQLCommand(
		SQLCommandType sqlCmdType, 
		String tableName, 
		String[] columnName, 
		Object[] param, 
		Hashtable<String, Object> condition) {
		
		// У���������Ч�ԣ�����Ч���׳��쳣
		try {
			SQLValidateHelper.ValidateSQLAndParam(
				sqlCmdType, 
				tableName, 
				columnName, 
				param, 
				condition);
		} catch (SQLParamException ex) {
			System.err.println("�쳣��Ϣ��������SQL�������Ϸ���\r\n" + ex.getMessage());
		}
		
		// ����ƴ�ӵ�SQL�����ִ�(Unicode�ַ�\u0020��ʾ�ո��)
		StringBuffer sql = new StringBuffer();
		
		// ����SQL�������͹���SQL����
		switch (sqlCmdType) {
			case SELECT:
				// ����Select����(�������ڵ����ѯ)
				sql.append("Select\u0020");
				// ƴ������
				if(columnName.length==0){
					
					sql.append("\u0020*\u0020");
				}else{
					
					for (int i = 0; i < columnName.length; i++) {
						sql.append((i < columnName.length - 1) 
							? (columnName[i] + ",\u0020") 
							: (columnName[i] + "\u0020"));
					}	
				}
				
				// ƴ�ӱ���
				sql.append("From\u0020" + tableName + "");
				// ƴ������
				sql.append(this.buildCondition(condition));
				break;
			case INSERT:
				// ����Insert����
				sql.append("Insert\u0020Into\u0020");
				// ƴ�ӱ���
				sql.append(tableName + "\u0020(");
				// ƴ������
				for (int i = 0; i < columnName.length; i++) {
					sql.append((i < columnName.length - 1) 
						? (columnName[i] + ",\u0020") 
						: (columnName[i] + ")\u0020"));  
				}
				// ƴ��ռλ��
				sql.append("Values\u0020(");
				for (int j = 0; j < param.length; j++) {
					sql.append((j < param.length - 1) ? "?,\u0020" : "?)");
				}
				break;
			case UPDATE:
				// ����Update����
				sql.append("Update\u0020");
				// ƴ�ӱ���
				sql.append(tableName + "\u0020Set\u0020");
				// ƴ������
				for (int i = 0; i < columnName.length; i++) {
					sql.append((i < columnName.length - 1) 
						? (columnName[i] + "\u0020=\u0020?,\u0020") 
						: (columnName[i] + "\u0020=\u0020?"));  
				}
				// ƴ������
				sql.append(this.buildCondition(condition));
				break;
			case DELETE:
				// ����Delete����
				sql.append("Delete\u0020From\u0020");
				// ƴ�ӱ���
				sql.append(tableName + "");
				// ƴ������
				sql.append(this.buildCondition(condition));
				break;
		}
		
		System.err.println("��ǰSQL��䣺\r\n" + sql.toString());
		
		// ���ؽ��
		return sql.toString();
		
	}
	
	/**
	 * ����SQL���������ִ�
	 * @param condition SQL�����б�
	 * @return SQL���������ִ�
	 */
	private String buildCondition(Hashtable<String, Object> condition) {
		
		// ����SQL�������ֵ��ִ�
		StringBuffer sql = new StringBuffer();
		
		// ƴ������
		if (condition != null && condition.size() != 0) {
			sql.append("\u0020Where\u0020");
			Object[] conditionName = condition.keySet().toArray();
			for (int j = 0; j < conditionName.length; j++) {
				sql.append((j < conditionName.length - 1) 
					? (conditionName[j] + "\u0020=\u0020?\u0020And\u0020")
					: (conditionName[j] + "\u0020=\u0020?"));
			}
		}
		
		// ���ؽ��
		return sql.toString();
		
	}
	
	/**
	 * SQLCommandType SQL��������ö��
	 * @author CodingMouse
	 * @version 1.0.0.1  2009-3-31
	 */
	public enum SQLCommandType {
		
		/**
		 * SQL��ѯ��������(�������ڵ����ѯ)
		 */
		SELECT,
		/**
		 * SQL������������
		 */
		INSERT,
		/**
		 * SQL�޸���������
		 */
		UPDATE,
		/**
		 * SQLɾ����������
		 */
		DELETE
		
	}
	
}
