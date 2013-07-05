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
 * SQLParamHelper SQL����������������
 * @author CodingMouse
 * @version 1.0.0.1 2009-3-26
 */
public final class SQLParamHelper {

	/**
	  * �Զ���Java�������Ͳ���ӳ��ת��ΪSQL����
	  * @param param Java�������Ͳ�������
	  * @param ps SQL����ִ�ж���
	  * @return �����Զ�ƥ����Ӻ�SQL������SQl����ִ�ж���
	  */
	 public static PreparedStatement JavaParam2SQLParam(Object[] param, PreparedStatement ps) {
		
		 // ʹ��Class.isInstance()�����ж�ָ���� Object 
		 // �Ƿ���� Class ����ʾ�Ķ���ֵ���ݡ�
		 // �˷����� Java ���� instanceof ������Ķ�̬��Ч������
		 // �����ǰSQL����������������Java����������SQL����������˹�ӳ��
		 if (param != null && param.length > 0) {
			 try {
			 	 for (int i = 0; i < param.length; i++) {
					 // ����i��������Java��������ͨ�����Ӧ�İ�װ�����˹�ӳ�䲢�趨SQL�������
					 if (Boolean.class.isInstance(param[i])) {                  // ӳ��boolean����
						 ps.setBoolean(i + 1, Boolean.parseBoolean(param[i].toString()));
					 } else if (Byte.class.isInstance(param[i])) {              // ӳ��byte����
						 ps.setByte(i + 1, Byte.parseByte(param[i].toString()));
					 } else if (byte[].class.isInstance(param[i])) {
						 ps.setBytes(i + 1, (param[i].toString()).getBytes());  // ӳ��byte[]����
					 } else if (Character.class.isInstance(param[i])
						 || String.class.isInstance(param[i])) {                // ӳ��char��String����
						 ps.setString(i + 1, String.valueOf(param[i]));
					 } else if (Short.class.isInstance(param[i])) {             // ӳ��short����
						 ps.setShort(i + 1, Short.parseShort(param[i].toString()));
				 	 } else if (Integer.class.isInstance(param[i])) {           // ӳ��int����
						 ps.setInt(i + 1, Integer.parseInt(param[i].toString()));
				 	 } else if (Long.class.isInstance(param[i])) {              // ӳ��long����
				 		 ps.setLong(i + 1, Long.parseLong(param[i].toString()));
					 } else if (Float.class.isInstance(param[i])) {             // ӳ��float����
						 ps.setFloat(i + 1, Float.parseFloat(param[i].toString()));
					 } else if (Double.class.isInstance(param[i])) {            // ӳ��double����
						 ps.setDouble(i + 1, Double.parseDouble(param[i].toString()));
					 } else if (BigDecimal.class.isInstance(param[i])) {        // ӳ��BigDecimal����
						 ps.setBigDecimal(i + 1, new BigDecimal(param[i].toString()));
					 } else if (Date.class.isInstance(param[i])) {              // ӳ��Date����
			 			 ps.setDate(i + 1, Date.valueOf(param[i].toString()));
					 } else if (Time.class.isInstance(param[i])) {              // ӳ��Time����
			 			 ps.setTime(i + 1, Time.valueOf(param[i].toString()));
					 } else if (Timestamp.class.isInstance(param[i])) {         // ӳ��Timestamp����
			 			 ps.setTimestamp(i + 1, Timestamp.valueOf(param[i].toString()));
					 } else {
						 throw new Exception("Java�����а�����֧�ֵ��Զ�ӳ���������ͣ�" + param[i].getClass().getName());
					 }
			 	 }
			 } catch(SQLException ex) {
		 		 System.err.println("�쳣��Ϣ���ض����ݿ���ʼ�������ش���\r\n" + ex.getMessage());
		 	 } catch (Exception ex) {
		 		System.err.println("�쳣��Ϣ������������⣡\r\n" + ex.getMessage());
			 }
			 
		 }
		 
		 // ���ؽ��
		 return ps;
		 
	 }
	
	 /**
	  * �Զ���SQL�������Ͳ���ӳ��ת��ΪJava��������
	  * @param <T> �������Ͳ���
	  * @param rsmd ���� ResultSet �������е����ͺ�������Ϣ�� ResultSetMetaData ����
	  * @param pojoClass ����Java������Ʋ��Ҷ�Ӧ pojo ��Class
	  * @param rs ���������
	  * @return ���� pojo ʵ��
	  */
	 @SuppressWarnings("unchecked")  // ���öԴ˷��������Ͱ�ȫ���
	public static <T> T SQLParam2JavaParam(ResultSetMetaData rsmd, Class<?> pojoClass, ResultSet rs) {
		  
		 // ���巺�� pojo ʵ��
		 T pojo = null;
		 
		 try {
			 
			 // ���ɵ������� pojo ʵ��
			 pojo = (T)pojoClass.newInstance();
			 
			 // �������ݼ���ÿһ�У�ͨ����ͬ���ص�Pascal������������Ҳ�ִ�ж�Ӧ 
			 // pojo ��ĸ�ֵ(getter)������ʵ�ֽ������pojo���ͼ��ϵ��Զ�ӳ��
			 for (int i = 1; i <= rsmd.getColumnCount(); i++) {
	
				 // ȡ�õ�i������
				 String setMethodName = rsmd.getColumnName(i);
				 // ͨ�������������i������,ȡ�� pojo �ж�Ӧ�ֶε�ȡֵ(setter)������
				 setMethodName = "set"
					+ setMethodName.substring(0, 1).toUpperCase()
					+ setMethodName.substring(1);
				 // ȡ�õ�i�е���������
				 int dbType = rsmd.getColumnType(i);
				 // ��ǰ���䷽��
				 Method method = null;
				 // ��Ӧ��i�е�SQL���������˹�ӳ�䵽��Ӧ��Java�������ͣ�
				 // ������ִ�и��е��� pojo �ж�Ӧ���Ե� setter ������ɸ�ֵ
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
				 } else if (dbType == Types.DATE) {       // �̳��� java.util.Date ��
					 method = pojoClass.getMethod(setMethodName, Date.class);
					 method.invoke(pojo, rs.getDate(i));
				 } else if (dbType == Types.TIME) {       // �̳��� java.util.Date ��
					 method = pojoClass.getMethod(setMethodName, Time.class);
					 method.invoke(pojo, rs.getTime(i));
				 } else if (dbType == Types.TIMESTAMP) {  // �̳��� java.util.Date ��
					 method = pojoClass.getMethod(setMethodName, Timestamp.class);
					 method.invoke(pojo, rs.getTimestamp(i));
				 } else if (dbType == Types.BINARY
						 || dbType == Types.VARBINARY
						 || dbType == Types.LONGVARBINARY
						 || dbType == Types.BLOB) {
					 method = pojoClass.getMethod(setMethodName, byte[].class);
					 method.invoke(pojo, rs.getBytes(i));
				 } else {
					 throw new Exception("���ݿ��а�����֧�ֵ��Զ�ӳ���������ͣ�" + dbType);
				 }
			 }
		 } catch (InstantiationException ex) {
			 System.err.println("�쳣��Ϣ����������ָ����������޷��� Class ���е� newInstance ����ʵ������\r\n" + ex.getMessage());
		 } catch (NoSuchMethodException ex) {
			 System.err.println("�쳣��Ϣ�����������޷��ҵ�ĳһ�ض��ķ�����\r\n" + ex.getMessage());
		 } catch (IllegalAccessException ex) {
			 System.err.println("�쳣��Ϣ���������󣬶������޷����ʣ��޷������Եش���һ��ʵ����\r\n" + ex.getMessage());
		 } catch (InvocationTargetException ex) {
			 System.err.println("�쳣��Ϣ�����������ɵ��÷������췽�����׳��쳣�ľ��������쳣��\r\n" + ex.getMessage());
		 } catch (SecurityException ex) {
			 System.err.println("�쳣��Ϣ���������󣬰�ȫ��������⵽��ȫ�ַ���\r\n" + ex.getMessage());
		 } catch (IllegalArgumentException ex) {
			 System.err.println("�쳣��Ϣ�����������򷽷�������һ�����Ϸ�����ȷ�Ĳ�����\r\n" + ex.getMessage());
		 } catch (SQLException ex) {
			 System.err.println("�쳣��Ϣ���������󣬻�ȡ���ݿ����Ӷ������\r\n" + ex.getMessage());
		 } catch (Exception ex) {
		 	 System.err.println("�쳣��Ϣ������������⣡\r\n" + ex.getMessage());
		 }
			 
		 // ���ؽ��
		 return pojo;
	 }
	 
		public static <T> T SQLParam2JavaParam(String[] classColumn, Class<?> pojoClass, ResultSet rs) {
			  
			 // ���巺�� pojo ʵ��
			 T pojo = null;
			 
			 try {
				 
				 // ���ɵ������� pojo ʵ��
				 pojo = (T)pojoClass.newInstance();
				 
				 // �������ݼ���ÿһ�У�ͨ����ͬ���ص�Pascal������������Ҳ�ִ�ж�Ӧ 
				 // pojo ��ĸ�ֵ(getter)������ʵ�ֽ������pojo���ͼ��ϵ��Զ�ӳ��
				 for (int i = 1; i <= classColumn.length; i++) {
		
					 // ȡ�õ�i������
					 String setMethodName = classColumn[i];
					 // ͨ�������������i������,ȡ�� pojo �ж�Ӧ�ֶε�ȡֵ(setter)������
					 setMethodName = "set"
						+ setMethodName.substring(0, 1).toUpperCase()
						+ setMethodName.substring(1);
					 // ȡ�õ�i�е���������
					
					 // ��ǰ���䷽��
					 Method method = null;
					 // ��Ӧ��i�е�SQL���������˹�ӳ�䵽��Ӧ��Java�������ͣ�
					 // ������ִ�и��е��� pojo �ж�Ӧ���Ե� setter ������ɸ�ֵ
					
						 method = pojoClass.getMethod(setMethodName, String.class);
						 method.invoke(pojo, rs.getString(i));
					 
				 }
			 } catch (InstantiationException ex) {
				 System.err.println("�쳣��Ϣ����������ָ����������޷��� Class ���е� newInstance ����ʵ������\r\n" + ex.getMessage());
			 } catch (NoSuchMethodException ex) {
				 System.err.println("�쳣��Ϣ�����������޷��ҵ�ĳһ�ض��ķ�����\r\n" + ex.getMessage());
			 } catch (IllegalAccessException ex) {
				 System.err.println("�쳣��Ϣ���������󣬶������޷����ʣ��޷������Եش���һ��ʵ����\r\n" + ex.getMessage());
			 } catch (InvocationTargetException ex) {
				 System.err.println("�쳣��Ϣ�����������ɵ��÷������췽�����׳��쳣�ľ��������쳣��\r\n" + ex.getMessage());
			 } catch (SecurityException ex) {
				 System.err.println("�쳣��Ϣ���������󣬰�ȫ��������⵽��ȫ�ַ���\r\n" + ex.getMessage());
			 } catch (IllegalArgumentException ex) {
				 System.err.println("�쳣��Ϣ�����������򷽷�������һ�����Ϸ�����ȷ�Ĳ�����\r\n" + ex.getMessage());
			 } catch (SQLException ex) {
				 System.err.println("�쳣��Ϣ���������󣬻�ȡ���ݿ����Ӷ������\r\n" + ex.getMessage());
			 } catch (Exception ex) {
			 	 System.err.println("�쳣��Ϣ������������⣡\r\n" + ex.getMessage());
			 }
				 
			 // ���ؽ��
			 return pojo;
		 }
		 
	 
}