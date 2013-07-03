package com.mx.clent.vo;

import java.util.Hashtable;
import com.mx.client.db.DBDataSQL;
import com.mx.client.db.GenDao;

public class MsgTypeBean {
	public String typeid ="0";
	public String comment = "";

	public MsgTypeBean() {

	}
	private static class SingletonHolder {
		public static final MsgTypeBean INSTANCE = new MsgTypeBean();
	}

	public static MsgTypeBean getInstance() {
		return SingletonHolder.INSTANCE;
	}
	/**
	 * 定义类型和类型描述
	 * @param typeid
	 * @param comment
	 */
	public MsgTypeBean(String typeid, String comment) {
		this.typeid = typeid;
		this.comment = comment;
	}
	
	public boolean HandleSave() {
		GenDao.getInstance().executeInsert(DBDataSQL.TB_MSGTYPE,new String[]{DBDataSQL.COL_MSGTYPE_COMM,DBDataSQL.COL_MSGTYPE_TYPEID} , new Object[]{comment,typeid});
		return false;
	}

	public boolean handleDelete() {

		return false;
	}

	// 返回一个空的
	public static final MsgTypeBean NULL = new MsgTypeBean();
	/**
	 * 从一个cursor构造一个 Bean对象，cursor 必须是通过AnXXXList，getXXX()方法获得， 数据结构必须对应，
	 * @param c
	 * @return 若cursor的数据库类型与 Bean对象的数据结构不一致，则会导致返回null
	 */
	// 检查参数
		protected boolean checkparam(Object... params) {
			boolean result = true;
			for (Object name : params)
				if (name == null) {
					result = false;
					return result;
				}
			return result;
		}
		
		public static MsgTypeBean Create(Hashtable<String, Object> table) {
			MsgTypeBean node = (MsgTypeBean) NULL;
			try {
				String comm = GenDao.getInstance().getValue(DBDataSQL.TB_MSGTYPE, new String[]{DBDataSQL.COL_MSGTYPE_COMM}, DBDataSQL.COL_MSGTYPE_COMM, table);
				String id = GenDao.getInstance().getValue(DBDataSQL.TB_MSGTYPE, new String[]{DBDataSQL.COL_MSGTYPE_TYPEID}, DBDataSQL.COL_MSGTYPE_TYPEID, table);;
				node = new MsgTypeBean(id, comm);
			} catch (Exception e) {
				//LOG.e("mixun", "类型不匹配，或者有空字段");
				e.printStackTrace();
			} finally {
			}

			return node;
		}
	

}
