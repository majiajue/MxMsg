package com.mx.client.db;



import java.util.Hashtable;
import java.util.List;

/**
 * IBaseDao �������ݿ���ʽӿ�
 * @author 
 * @version
 * @param <T> ���Ͳ���
 */
public interface IBaseDao<T> {
	
	/**
	 * executeInsert ִ��SQL�����������ķ���
	 * @param tableName Ҫ�������ض����ݿ������
	 * @param columnName Ҫ�������ض����ݿ��������嵥(Pascal������)
	 * @param param SQL�����б�
	 * @return true-�ɹ�/false-ʧ��
	 */
	public boolean executeInsert(
		String tableName, 
		String[] columnName, 
		Object[] param);
	/**
	 * executeDelete ִ��SQLɾ����������ķ���
	 * @param tableName Ҫ�������ض����ݿ������
	 * @param condition SQL�����б�
	 * @return true-�ɹ�/false-ʧ��
	 */
	public boolean executeDelete(
		String tableName, 
		Hashtable<String, Object> condition);
	/**
	 * executeUpdate ִ��SQL�޸���������ķ���
	 * @param tableName Ҫ�������ض����ݿ������
	 * @param columnName Ҫ�������ض����ݿ��������嵥(Pascal������)
	 * @param param SQL�����б�
	 * @param condition SQL�����б�
	 * @return true-�ɹ�/false-ʧ��
	 */
	public boolean executeUpdate(
		String tableName, 
		String[] columnName, 
		Object[] param, 
		Hashtable<String, Object> condition);
	/**
	  * executeSelect ִ��SQL��ѯ��������ķ���(�������ڵ����ѯ)
	  * @param tableName Ҫ�������ض����ݿ������
	  * @param columnName Ҫ�������ض����ݿ��������嵥(Pascal������)
	  * @param condition SQL�����б�
	  * @param classPath �����صķ���pojo������ʱ����·���ִ�(������������+�������磺TestPojo.class.getName())
	  * @return List<T> ����pojo����;
	  */  
	public List<?> executeSelect(
		String tableName, 
		String[] columnName, 
		Hashtable<String, Object> condition,
		String classPath);

}
