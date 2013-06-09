package com.mx.client.db;



import java.util.Hashtable;

import com.mx.client.db.SQLCommandBuilder.SQLCommandType;



/**
 * SQLValidateHelper SQL��֤������������
 * @author CodingMouse
 * @version 1.0.0.1  2009-3-30
 */
public final class SQLValidateHelper {

	/**
	 * ��֤SQL�����ַ�����������б�ķ���
	 * @param sqlCmdType SQL��������
	 * @param tableName Ҫ�������ض����ݿ������
	 * @param columnName Ҫ�������ض����ݿ��������嵥
	 * @param param SQL�����б�
	 * @param condition SQL�����б�
	 * @return true-��֤ͨ��/false-��֤ʧ��
	 * @throws SQLParamException SQL�����쳣
	 */
	public static void ValidateSQLAndParam(
		SQLCommandType sqlCmdType, 
		String tableName, 
		String[] columnName, 
		Object[] param, 
		Hashtable<String, Object> condition) throws SQLParamException {
		
		// ��Ϣ��ʾ��׺
		String notNullMsg = "��ֵ�Ϳ��ִ�ֵ��";
		String resetParam = "�����¸�����ȷ��Բ�����";
		String entryTypeMsg = "��ȷ����ʹ�õ�SQL���������Ƿ���ȷ��";
		
		// ���SQL��������Ϊ��
		if (sqlCmdType == null) {
			// ��֤ʧ��
			throw new SQLParamException("SQL�������Ͳ�������Ϊ�գ�");
		}
		// ���Ҫ�������ض����ݿ������Ϊ��
		if (tableName == null
			|| tableName.equals("")) {
			// ��֤ʧ��
			throw new SQLParamException("Ҫ�������ض����ݿ�����Ʋ���Ϊ" + notNullMsg);
		}
		// ����ض����ݿ��������嵥���Ƿ����null�Ϳ��ִ�ֵ
		if (columnName != null) {
			for (int i = 0; i < columnName.length; i++) {
				// �Ƴ���������ʹ������ӵķ�(��)����(��[���͡�]��)
				columnName[i] = columnName[i].replace('[', '\u0020').trim().replace(']', '\u0020').trim();
				if (columnName[i] == null 
					|| columnName[i].equals("")) {
					// ��֤ʧ��
					throw new SQLParamException("Ҫ�������ض����ݿ��������嵥�в��ܰ���" + notNullMsg);
				}
			}
		}
		// ���SQL�����б����Ƿ����null�Ϳ��ִ�ֵ
		if (param != null) {
			for (int i = 0; i < param.length; i++) {
				if (param[i] == null 
					|| param[i].equals("")) {
					// ��֤ʧ��
					throw new SQLParamException("SQL�����б��в��ܰ���" + notNullMsg);
				}
			}
		}
		// ��������Ĳ������
		switch (sqlCmdType) {
			case SELECT:
				// ���������SQL�����б�
				if (param != null && param.length > 0) {
					// ��֤ʧ��
					throw new SQLParamException("Select���������ָ��SQL�����б�" + entryTypeMsg);
				}
				break;
			case INSERT:
				// ������������������һ��
				if (columnName.length != param.length) {
					// ��֤ʧ��
					throw new SQLParamException("Insert����и�����������SQL����������ƥ�䣬" + resetParam);
				}
				// ���������SQL�����б�
				if (condition != null && condition.size() > 0) {
					// ��֤ʧ��
					throw new SQLParamException("Insert���������ָ��SQL�����б�" + entryTypeMsg);
				}
				break;
			case UPDATE:
				// ������������������һ��
				if (columnName.length != param.length) {
					// ��֤ʧ��
					throw new SQLParamException("Update����и�����������SQL����������ƥ�䣬" + resetParam);
				}
				break;
			case DELETE:
				// ���������Ҫ�������ض����ݿ��������嵥
				if (columnName != null && columnName.length > 0) {
					// ��֤ʧ��
					throw new SQLParamException("Delete���������ָ��Ҫ�������ض����ݿ��������嵥��" + entryTypeMsg);
				}
				// ���������SQL�����б�
				if (param != null && param.length > 0) {
					// ��֤ʧ��
					throw new SQLParamException("Delete���������ָ��SQL�����б�" + entryTypeMsg);
				}
				break;
		}
		
	}
	
}
