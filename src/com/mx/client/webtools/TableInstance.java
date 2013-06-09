package com.mx.client.webtools;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class TableInstance {
	private String user = "sa";
	private String pass = "";

	private String driverName = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:h2:/data/MX;";
	private String sql = "SELECT * FROM INFORMATION_SCHEMA.TABLES";
	 private String sql_1 = "select VIEW_NAME from all_views where owner='CONTRACT'";

	// private String address = "./src/com/spr/pro/pojo";
	private String address = "E:\\majiajue\\eclipse\\workspace\\MxMsg\\src\\com\\mx\\clent\\vo";
	private Connection conn;
	private Statement stmt;
	private Statement stmt2;
	private ResultSet rs;
	private ResultSet rs2;
	private ResultSet rs3;
	private ResultSetMetaData rsmd;
	private DatabaseMetaData dbmd;

	private FileOutputStream fos;
	private OutputStreamWriter osw;
	private BufferedWriter bw;

	private StringBuffer sbuf = new StringBuffer();
	private StringBuffer sbuf2 = new StringBuffer();
	private StringBuffer sbuf3 = new StringBuffer();
	private StringBuffer sbuf4 = new StringBuffer();
	private String primaryKeys;
	private String columnName;
	private String dateTypeValue;

	public TableInstance() throws IOException {
		// TODO Auto-generated constructor stub
		String sqlV = "";
		String TableName = "";
		try {
			connect(sql);

			while (rs.next()) {

				TableName = rs.getString("TABLE_NAME");

				if (TableName.equals("V_CONTRACT_INFO")) {

					sqlV = "SELECT * FROM " + TableName;

					stmt2 = conn.createStatement();
					rs2 = stmt2.executeQuery(sqlV);

					writeData(TableName, getColumenName(rs2, TableName));

					rs2.close();
					stmt2.close();

				}
			}

			//
			// destroy();

			// connect(sql_1);
			//
			// while(rs.next()){
			//
			// TableName = rs.getString("VIEW_NAME");
			// //濡傛灉琛ㄥ悕涓篢_寮�鐨勮緭鍑�
			// //
			// if(TableName.substring(0,2).equals("NS")||TableName.indexOf("JBPM")>-1){
			// // continue;
			// // }
			// if(TableName.equals("V_USERMRESOURCEGROUP")){
			// sqlV = "SELECT * FROM " + TableName;
			//
			// stmt2 = conn.createStatement();
			// rs2 = stmt2.executeQuery(sqlV);
			//
			// writeData(TableName,getColumenName(rs2,TableName));
			//
			// rs2.close();
			// stmt2.close();
			// }
			// }

			destroy();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			new TableInstance();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// String name = "12";
		// System.out.println(name.matches("12|13|14"));
	}

	/**
	 * 杩炴帴鏁版嵁搴�
	 * 
	 * @throws SQLException
	 */
	private void connect(String sql) throws SQLException {
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, pass);
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql); // 鏌ヨ涓嬬‘瀹氱粨鏋滈泦鏄偅涓〃鐨�

		} catch (Exception e) {
			e.printStackTrace();
			rs.close();
			stmt.close();
			conn.close();
		}
	}

	/**
	 * 鍏抽棴鏁版嵁搴�
	 * 
	 * @throws SQLException
	 */
	private void destroy() throws SQLException {
		rs.close();
		stmt.close();
		conn.close();
	}

	/**
	 * 鍐欏叆鏂囦欢
	 * 
	 * @param message
	 * @throws IOException
	 */
	private void writeData(String className, String message) throws IOException {
		fos = new FileOutputStream(address + "/" + className + ".java");
		osw = new OutputStreamWriter(fos);
		bw = new BufferedWriter(osw);
		bw.write(message);
		bw.flush();
		bw.close();
		osw.close();
		fos.close();
	}

	/**
	 * 鑾峰彇鎵�湁鍒楀悕
	 * 
	 * @return
	 * @throws SQLException
	 */
	private String getColumenName(ResultSet rsFile, String TableName)
			throws SQLException {
		/*
		 * 寰楀埌琛ㄧ殑鎵�湁鍒楀悕浠ュ瓧绗︿覆鏁扮粍鐨勫舰寮忚繑鍥�
		 */
		dbmd = conn.getMetaData();
		rsmd = rsFile.getMetaData();
		rs3 = dbmd.getPrimaryKeys(null, null, TableName);

		String dateTypeValue = null;

		if (rs3.next()) {
			primaryKeys = "";
			primaryKeys = rs3.getString(4);
		}
		// 涓婚敭涓虹┖鏃讹紝浠ラ槻绌烘寚閽堝紓甯�
		if (primaryKeys == null)
			primaryKeys = "";
		rs3.close();

		sbuf2.delete(0, sbuf2.length());
		sbuf3.delete(0, sbuf3.length());
		sbuf4.delete(0, sbuf4.length());

		sbuf2.append("package com.spr.pro.pojo;\n\n");

		sbuf2.append("import java.io.Serializable;\n");
		sbuf2.append("import java.util.Date;\n\n");

		sbuf2.append("import com.spr.support.JdbcAnnotation;\n");
		sbuf2.append("import com.spr.support.JdbcBeanSupport;\n\n");

		sbuf2.append("public class " + TableName
				+ " extends JdbcBeanSupport implements Serializable{\n");
		sbuf2.append("	private static final long serialVersionUID = "
				+ getRandom() + "L;\n");

		try {
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				columnName = rsmd.getColumnName(i);
				dateTypeValue = dateType(rsmd.getColumnType(i));
				// 濡傛灉涓烘棩鏈熺被鍨嬶紝杩樻槸杞负String绫诲瀷鏉ュ鐞�
				// if(dateTypeValue.equals("Date")){
				// dateTypeValue = "String";
				// };

				if (primaryKeys.equals(columnName)) {
					sbuf2.append("	@JdbcAnnotation(field=\"primaryKey\")\n");
					sbuf2.append("	private " + dateTypeValue + " "
							+ columnName.toLowerCase() + " = null;\n");
					sbuf4.append(fileModel(dateType(rsmd.getColumnType(i)),
							columnName));
					continue;
				}

				sbuf3.append("	private " + dateTypeValue + " "
						+ columnName.toLowerCase() + " = null;\n");
				sbuf4.append(fileModel(dateType(rsmd.getColumnType(i)),
						columnName));
				/*
				 * System.out.println(rsmd.getColumnName(i) + "------" +
				 * dateType(rsmd.getColumnType(i)) + "------" +
				 * rsmd.getColumnTypeName(i) + "------" +
				 * rsmd.getColumnType(i));
				 */
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		sbuf2.append(sbuf3.toString());
		sbuf2.append(sbuf4.toString());
		sbuf2.append("\n}");

		System.out.println(sbuf2.toString());
		return sbuf2.toString();
	}

	/**
	 * 杩斿洖鍒ゆ柇鐨勬暟鎹被鍨�
	 * 
	 * @return
	 */
	private String dateType(int rsmd) {
		dateTypeValue = null;
		switch (rsmd) {
		case Types.BLOB:
			dateTypeValue = "Blob";
			break;
		case Types.FLOAT:
			dateTypeValue = "Float";
			break;
		case Types.TIMESTAMP:
			dateTypeValue = "Date";
			break;
		case Types.INTEGER:
			dateTypeValue = "Long";
			break;
		case Types.CHAR:
			dateTypeValue = "String";
			break;
		case Types.DATE:
			dateTypeValue = "Date";
			break;
		case Types.VARCHAR:
			dateTypeValue = "String";
			break;
		case Types.NUMERIC:
			dateTypeValue = "Long";
			break;
		case Types.CLOB:
			dateTypeValue = "Clob";
			break;
		}
		return dateTypeValue;
	}

	public String fileModel(String type, String name) {
		String tName = name.toLowerCase();
		String tName2 = null;
		String pName = name.substring(0, 1).toUpperCase()
				+ name.substring(1).toLowerCase();

		// if(type.equals("Date")){
		// type = "String";
		// tName2 = "JdbcDataHandle.parseDate("+tName+")";
		// }else{
		// tName2 = tName;
		// }
		//
		tName2 = tName;

		sbuf.delete(0, sbuf.length());
		sbuf.append("\n\n	public " + type + " get" + pName + "() {\n");
		sbuf.append("		return " + tName + ";\n");
		sbuf.append("	}");

		sbuf.append("\n\n	public void set" + pName + "(" + type + " " + tName
				+ ") {\n");
		sbuf.append("		this." + tName + " = " + tName2 + ";\n");
		sbuf.append("	}");
		return sbuf.toString();
	}

	public long getRandom() {
		double a = Math.random() * 999999999;
		a = Math.ceil(a);
		return new Double(a).longValue();
	}
}
