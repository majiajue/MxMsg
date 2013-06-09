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
 * BaseDao �������ݿ���ʻ���
 * @author CodingMouse
 * @version 1.0.0.1 2009-3-26
 * @param <T> ���Ͳ���
 */
public class BaseDao<T> implements IBaseDao<T> {

	private Connection conn = null;       // ���ݿ����Ӷ���
	private PreparedStatement ps = null;  // Ԥ�����SQL����ִ�ж���
	private ResultSet rs = null;          // ���������
	
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
		Object[] param) {
		
		int rowCount = 0;  // ����ִ��SQL���������������Ӱ�������
		
		try {
			
			// ��ȡ���ݿ����Ӷ���
			this.conn = DBTools.getH2SQLConnection();
			// ��ȡԤ����SQL���ִ�ж��󲢸��ݲ����Զ�����SQL�����ִ�
			this.ps = this.conn.prepareStatement(
				SQLCommandBuilder.getInstance().getSQLCommand(
					SQLCommandType.INSERT, 
					tableName, 
					columnName, 
					param, 
					null));
			 
			// �Զ�ӳ��SQL����
			if (param != null && param.length > 0) {
				this.ps = SQLParamHelper.JavaParam2SQLParam(param, this.ps);
			}
			 
			// ִ��SQL����������淵�ص���Ӱ������
			rowCount = this.ps.executeUpdate();
			 
		} catch (SQLException ex) {
			
			System.err.println("�쳣��Ϣ��ִ��SQL�������ʱ��������\r\n" + ex.getMessage());
			 
	 	 } finally {
	 		 
	 		 // �ͷ���Դ
	 		 this.releaseResource();
	 		 
	 	 }
		
		return rowCount > 0;
		
	}
	
	/**
	 * executeDelete ִ��SQLɾ����������ķ���
	 * @param tableName Ҫ�������ض����ݿ������
	 * @param condition SQL�����б�
	 * @return true-�ɹ�/false-ʧ��
	 */
	public boolean executeDelete(
		String tableName, 
		Hashtable<String, Object> condition) {
		
		int rowCount = 0; // ����ִ��SQL����������Ӱ�������
		 
		try {
			 
			// ��ȡ���ݿ����Ӷ���
			this.conn = DBTools.getH2SQLConnection();
			// ��ȡԤ����SQL���ִ�ж��󲢸��ݲ����Զ�����SQL�����ִ�
			this.ps = this.conn.prepareStatement(
				SQLCommandBuilder.getInstance().getSQLCommand(
					SQLCommandType.DELETE, 
					tableName, 
					null, 
					null, 
					condition));
			 
			// �Զ�ӳ��SQL����
			if (condition != null && condition.size() > 0) {
				this.ps = SQLParamHelper.JavaParam2SQLParam(condition.values().toArray(), this.ps);
			}
			 
			// ִ��SQL����������淵�ص���Ӱ������
			rowCount = this.ps.executeUpdate();
			 
		} catch (SQLException ex) {
			 
			System.err.println("�쳣��Ϣ��ִ��SQLɾ������ʱ��������\r\n" + ex.getMessage());
			 
	 	} finally {
	 		 
	 		// �ͷ���Դ
	 		this.releaseResource();
	 		 
	 	}
		
		// ���ؽ��
		return rowCount > 0;
		
	}
	
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
		Hashtable<String, Object> condition) {
		
		int rowCount = 0; // ����ִ��SQL����������Ӱ�������
		 
		try {
			 
			// ��ȡ���ݿ����Ӷ���
			this.conn = DBTools.getH2SQLConnection();
			// ��ȡԤ����SQL���ִ�ж��󲢸��ݲ����Զ�����SQL�����ִ�
			this.ps = this.conn.prepareStatement(
				SQLCommandBuilder.getInstance().getSQLCommand(
					SQLCommandType.UPDATE, 
					tableName, 
					columnName, 
					param, 
					condition));
			 
			// �Զ�ӳ��SQL����
			if (param != null && param.length > 0) {
				// �������б���ӵ�SQL�����б���������ò����б�
				if (condition != null && condition.size() > 0) {
					Object[] paramArray = param;
					Object[] conditionArray = condition.values().toArray();
					List<Object> paramList = new Vector<Object>();
					// ���SQL�����б�
					for (Object objParam : paramArray) {
						if (!paramList.add(objParam)) {
							System.err.println("�쳣��Ϣ��δ�ܽ�SQL�����б���ӵ�Update��䣡");
						}
					}
					// ���SQL�����б�
					for (Object objCondition : conditionArray) {
						if (!paramList.add(objCondition)) {
							System.err.println("�쳣��Ϣ��δ�ܽ�SQL�����б���ӵ�Update��䣡");
						}
					}
					param = paramList.toArray();
				}
				this.ps = SQLParamHelper.JavaParam2SQLParam(param, this.ps);
			}

			// ִ��SQL����������淵�ص���Ӱ������
			rowCount = this.ps.executeUpdate();
			 
		} catch (SQLException ex) {
			 
			System.err.println("�쳣��Ϣ��ִ��SQL�޸�����ʱ��������\r\n" + ex.getMessage());
			 
	 	} finally {
	 		 
	 		// �ͷ���Դ
	 		this.releaseResource();
	 		 
	 	}
	 	
		// ���ؽ��
		return rowCount > 0;
		
	 }
	 
	 /**
	  * executeSelect ִ��SQL��ѯ��������ķ���(�������ڵ����ѯ)
	  * @param tableName Ҫ�������ض����ݿ������
	  * @param columnName Ҫ�������ض����ݿ��������嵥(Pascal������)
	  * @param condition SQL�����б�
	  * @param classPath �����صķ���pojo������ʱ����·���ִ�(������������+�������磺TestPojo.class.getName())
	  * @return List<T> ����pojo����;
	  */  
	public List<T> executeSelect(
		String tableName, 
		String[] columnName, 
		Hashtable<String, Object> condition,
		String classPath) {
		
		List<T> pojoSet = null;     // ����pojo����
		 
		try {
			// ��ȡ���ݿ����Ӷ���
			this.conn = DBTools.getH2SQLConnection();
			// ��ȡԤ����SQL���ִ�ж��󲢸��ݲ����Զ�����SQL�����ִ�
			this.ps = this.conn.prepareStatement(
				SQLCommandBuilder.getInstance().getSQLCommand(
				SQLCommandType.SELECT, 
				tableName, 
				columnName, 
				null, 
				condition));
			
			// �Զ�ӳ��SQL����
			if (condition != null && condition.size() > 0) {
				this.ps = SQLParamHelper.JavaParam2SQLParam(condition.values().toArray(), this.ps);
			}

			// ִ��SQL�����������ȡ�صĽ��������
			this.rs = this.ps.executeQuery();

			// ��ȡ���� ResultSet �������е����ͺ�������Ϣ�� ResultSetMetaData Ԫ���ݼ�����
			ResultSetMetaData rsmd = rs.getMetaData();
			// ����Java������Ʋ��Ҷ�Ӧ pojo ��Class
			Class<?> pojoClass = Class.forName(classPath);
			// �����µ��̰߳�ȫ�� pojoSet (pojo����) ʵ��
			pojoSet = new Vector<T>();
			 
			// ����Java������ƶ�ȡȡ�صĽ�������Զ�ӳ��Ϊ��Ӧ�� pojo ʵ��
			while (this.rs.next()) {
				
				// ���ɵ��� pojo ʵ��
				T pojo = SQLParamHelper.SQLParam2JavaParam(rsmd, pojoClass, rs);

				// ���Զ�ӳ����ϵ� pojo ʵ����װ�� pojoSet ���ͼ���
				pojoSet.add(pojo);
			}
			 
		} catch (SQLException ex) {
			System.err.println("�쳣��Ϣ����ȡ���ݿ����Ӷ������\r\n" + ex.getMessage());
		} catch (ClassNotFoundException ex) {
			System.err.println("�쳣��Ϣ���޷��ҵ�ָ���� pojo �࣡\r\n" + ex.getMessage()); 
		} finally {
			// �ͷ���Դ
	 		this.releaseResource();
		}

		// ���� pojoSet ���ͼ���
		return pojoSet;

	}
	
	/**
	 * releaseResource �ͷ��������ݿ���ʶ�����Դ
	 */
	private void releaseResource() {
		 
		if(this.rs != null) {
			try {
				this.rs.close();
			} catch (SQLException ex) {
				System.err.println("�쳣��Ϣ���رս�����������\r\n" + ex.getMessage());
			}
		}
		if(this.ps != null) {
			try {
				this.ps.close();
			} catch (SQLException ex) {
				System.err.println("�쳣��Ϣ���ر�SQL����ִ�ж������\r\n" + ex.getMessage());
			}
		}
		if(this.conn != null) {
			try{	
				if (!this.conn.isClosed()) {
					this.conn.close();
				}
			} catch (SQLException ex){
				System.err.println("�쳣��Ϣ���ر����ݿ����Ӵ���\r\n" + ex.getMessage());
			}
		}
		 
	}
	
}
