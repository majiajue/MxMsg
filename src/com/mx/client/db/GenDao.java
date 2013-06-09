package com.mx.client.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.mx.client.db.SQLCommandBuilder.SQLCommandType;


/**
 * ������ѯ��������ʵ������ݿ����
 * @author majiajue
 *
 */
public class GenDao {
	private Connection conn = null;       // ���ݿ����Ӷ���
	private PreparedStatement ps = null;  // Ԥ�����SQL����ִ�ж���
	private ResultSet rs = null;          // ���������
	private static class SingletonHolder {
		public static final GenDao INSTANCE = new GenDao();
	}

	public static GenDao getInstance() {
		return SingletonHolder.INSTANCE;
	}
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
	 * ��ȡĳһ�ֶ�����¼��ֵ
	 * @param tableName
	 * @param columnName
	 * @param condition
	 * @return
	 */
    public String getValue(String tableName,String[] columnName,String valueColumn,
    		Hashtable<String, Object> condition){
    	this.conn = DBTools.getH2SQLConnection();
		// ��ȡԤ����SQL���ִ�ж��󲢸��ݲ����Զ�����SQL�����ִ�
    	String value="";
		try {
			this.ps = this.conn.prepareStatement(
				SQLCommandBuilder.getInstance().getSQLCommand(
				SQLCommandType.SELECT, 
				tableName, 
				columnName, 
				null, 
				condition));
			if (condition != null && condition.size() > 0) {
				this.ps = SQLParamHelper.JavaParam2SQLParam(condition.values().toArray(), this.ps);
			}

			// ִ��SQL�����������ȡ�صĽ��������
			this.rs = this.ps.executeQuery();
			rs.last();
		    int a = rs.getRow();
		    if(a==1){
		    	value = rs.getString(valueColumn.toUpperCase());
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// �Զ�ӳ��SQL����
    	return value;
    };
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
