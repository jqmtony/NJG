/**
 * @(#)FdcSqlUtil.java 1.0 2013-9-9
 * Copyright 2013 Kingdee, Inc. All rights reserved
 */
package com.kingdee.eas.fdc.basedata.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.apache.commons.lang.time.DateFormatUtils;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.FdcRptConstant;
import com.kingdee.eas.fdc.basedata.IFDCSQLFacade;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.SqlParams;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.db.SQLUtils;

/**
 * ���ز� SQL������
 * 
 * @author ����
 * @email SkyIter@live.com
 * @date 2013-9-9
 * @see
 * @since JDK1.4
 */
public class FdcSqlUtil {

	// ��_����_����_����_Map
	private static Map COLUMN_TYPE_CODE_STR_MAP;
	// ��_����_����_����_Map
	private static Map COLUMN_TYPE_STR_CODE_MAP;

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��ȡ����script ,������?�滻Ϊ��ʵ����
	 * 
	 * @param sql
	 *            SQL���
	 * @param params
	 *            ��������
	 * @return
	 */
	public static String getTestSql(String sql, Object[] params) {
		String testSql = null;

		// ��ȡ����script ,������?�滻Ϊ��ʵ����
		FDCSQLBuilder ecBuilder = new FDCSQLBuilder(sql);
		if (FdcArrayUtil.isNotEmpty(params)) {
			ecBuilder.appendParam(params);
		}
		testSql = ecBuilder.getTestSql();

		return testSql;
	}

	/**
	 * ��ȡ����script ,������?�滻Ϊ��ʵ����
	 * 
	 * @param sql
	 *            SQL���
	 * @param sqlParams
	 *            SQL����
	 * @return
	 */
	public static String getTestSql(String sql, SqlParams sqlParams) {
		return getTestSql(sql, sqlParams.getParams());
	}

	/**
	 * ��ȡ����script ,������?�滻Ϊ��ʵ����
	 * 
	 * @param sql
	 *            SQL���
	 * @param rptParams
	 *            �������
	 * @return
	 */
	public static String getTestSql(String sql, RptParams rptParams) {
		SqlParams sqlParams = (SqlParams) rptParams.getObject(FdcRptConstant.RPT_PARAMETERS);
		return getTestSql(sql, sqlParams);
	}

	/**
	 * ���Դ�ӡ����script ,������?�滻Ϊ��ʵ����
	 * 
	 * @param out
	 *            ��ӡ������
	 * @param label
	 *            ��ǩ
	 * @param sql
	 *            SQL���
	 * @param params
	 *            �������
	 * @return
	 */
	public static String debugPrint(PrintStream out, Object label, String sql, Object[] params) {
		String testSql = getTestSql(sql, params);

		// Ĭ��Ϊ����̨���
		out = (PrintStream) FdcObjectUtil.defaultIfNull(out, System.out);
		out.println("===============================================================");
		if (null != label) {
			out.println(label + ":");
		}
		out.println(testSql);
		out.println("===============================================================");

		return testSql;
	}

	/**
	 * ���Դ�ӡ����script ,������?�滻Ϊ��ʵ����
	 * 
	 * @param out
	 *            ��ӡ������
	 * @param label
	 *            ��ǩ
	 * @param sql
	 *            SQL���
	 * @param sqlParams
	 *            SQL����
	 * @return
	 */
	public static String debugPrint(PrintStream out, Object label, String sql, SqlParams sqlParams) {
		return debugPrint(out, label, sql, sqlParams.getParams());
	}

	/**
	 * ���Դ�ӡ����script ,������?�滻Ϊ��ʵ����
	 * 
	 * @param out
	 *            ��ӡ������
	 * @param label
	 *            ��ǩ
	 * @param sql
	 *            SQL���
	 * @param rptParams
	 *            �������
	 * @return
	 */
	public static String debugPrint(PrintStream out, Object label, String sql, RptParams rptParams) {
		SqlParams sqlParams = (SqlParams) rptParams.getObject(FdcRptConstant.RPT_PARAMETERS);
		return debugPrint(out, label, sql, sqlParams);
	}

	/**
	 * ��ѯKSQL����ӳ��
	 * <p>
	 * ����һ��LinkedHashMap������Key��ű�����Value�������Set��������������Сд��
	 * 
	 * @param ctx
	 *            �����ģ��ɿ�
	 * @param dataTableName
	 *            ���ݱ������ɿ�
	 * @param isLikeTab
	 *            �Ƿ�ģ����������
	 * @param colName
	 *            �������ɿ�
	 * @param isLikeCol
	 *            �Ƿ�ģ����������
	 * @param isCheck
	 *            �Ƿ���
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static Map queryKsqlColNameMap(Context ctx, String dataTableName, boolean isLikeTab, String colName,
			boolean isLikeCol, boolean isCheck) throws BOSException, EASBizException {
		Map colNameMap = new LinkedHashMap();

		IFDCSQLFacade fDCSQLFacade = null;
		if (null == ctx) {
			fDCSQLFacade = FDCSQLFacadeFactory.getRemoteInstance();
		} else {
			fDCSQLFacade = FDCSQLFacadeFactory.getLocalInstance(ctx);
		}

		StringBuffer sqlSb = new StringBuffer();
		sqlSb.append(" SELECT KSQL_COL_TABNAME, KSQL_COL_NAME FROM KSQL_USERCOLUMNS WHERE 1 = 1 ");
		List paramList = new ArrayList();
		dataTableName = (null != dataTableName) ? dataTableName.trim().toUpperCase() : dataTableName;
		colName = (null != colName) ? dataTableName.trim().toUpperCase() : colName;

		appendKsqlColWhereFrag(sqlSb, paramList, "KSQL_COL_TABNAME", dataTableName, isLikeTab, "AND");
		appendKsqlColWhereFrag(sqlSb, paramList, "KSQL_COL_NAME", colName, isLikeTab, "AND");

		IRowSet rs = fDCSQLFacade.executeQuery(sqlSb.toString(), paramList);
		int rsSize = rs.size();
		String tempTabName = null;
		String tempColName = null;
		Set tempColNameSet = null;
		if (rsSize > 0) {
			try {
				while (rs.next()) {
					tempTabName = rs.getString("KSQL_COL_TABNAME").toLowerCase();
					tempColNameSet = (Set) colNameMap.get(tempTabName);
					if (null == tempColNameSet) {
						tempColNameSet = new LinkedHashSet();
						colNameMap.put(tempTabName, tempColNameSet);
					}
					tempColName = rs.getString("KSQL_COL_NAME").toLowerCase();
					tempColNameSet.add(tempColName);
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		} else if (isCheck) {
			boolean hasTableName = FdcStringUtil.isNotBlank(dataTableName);
			boolean hasColName = FdcStringUtil.isNotBlank(colName);

			String msg = null;
			if (hasTableName && hasColName) {
				msg = "���ݿ��в�����{0}���� " + dataTableName + "��{1}����" + colName;
			} else if (hasTableName) {
				msg = "���ݿ��в�����{0}���� " + dataTableName;
			} else if (hasColName) {
				msg = "���ݿ��в�����{1}���� " + colName;
			}

			if (FdcStringUtil.isNotBlank(msg)) {
				String tabFrag = "";
				String colFrag = "";
				if (isLikeTab && isLikeCol) {
					tabFrag = "����";
					colFrag = "����";
				} else if (isLikeTab) {
					tabFrag = "����";
				} else if (isLikeCol) {
					colFrag = "����";
				}
				msg = msg.replaceFirst("\\{0\\}", tabFrag);
				msg = msg.replaceFirst("\\{1\\}", colFrag);

				FDCUtils.throwEASBizException(msg);
			}
		}

		return colNameMap;
	}

	/**
	 * ��ѯKSQL����
	 * <p>
	 * ����һ��LinkedHashSet��������Сд��
	 * 
	 * @param ctx
	 *            �����ģ��ɿ�
	 * @param dataTableName
	 *            ���ݱ�������ȷ�������ɿ�
	 * @param isLikeTab
	 *            �Ƿ�ģ����������
	 * @param isCheck
	 *            �Ƿ���
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static Set queryKsqlColNameSet(Context ctx, String dataTableName, boolean isLikeTab, boolean isCheck)
			throws BOSException, EASBizException {
		Set colNameSet = new LinkedHashSet();

		IFDCSQLFacade fDCSQLFacade = null;
		if (null == ctx) {
			fDCSQLFacade = FDCSQLFacadeFactory.getRemoteInstance();
		} else {
			fDCSQLFacade = FDCSQLFacadeFactory.getLocalInstance(ctx);
		}

		StringBuffer sqlSb = null;
		List paramList = new ArrayList();
		IRowSet rs = null;
		int rsSize = 0;
		String tempColName = null;

		sqlSb = new StringBuffer();
		sqlSb.append(" SELECT  KSQL_COL_NAME FROM KSQL_USERCOLUMNS WHERE 1 = 1 ");
		paramList.clear();
		dataTableName = (null != dataTableName) ? dataTableName.trim().toUpperCase() : dataTableName;
		// ����"KSQL��"Where����
		appendKsqlColWhereFrag(sqlSb, paramList, "KSQL_COL_TABNAME", dataTableName, isLikeTab, "AND");

		rs = fDCSQLFacade.executeQuery(sqlSb.toString(), paramList);
		rsSize = rs.size();
		tempColName = null;
		if (rsSize > 0) {
			try {
				while (rs.next()) {
					tempColName = rs.getString("KSQL_COL_NAME").toLowerCase();
					colNameSet.add(tempColName);
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		} else if (isCheck) {
			boolean hasTableName = FdcStringUtil.isNotBlank(dataTableName);

			String msg = null;
			if (hasTableName) {
				msg = "���ݿ��в�����{0}���� " + dataTableName;
			}

			if (FdcStringUtil.isNotBlank(msg)) {
				String tabFrag = "";
				if (isLikeTab) {
					tabFrag = "����";
				}
				msg = msg.replaceFirst("\\{0\\}", tabFrag);
				FDCUtils.throwEASBizException(msg);
			}
		}

		return colNameSet;
	}

	/**
	 * ����"KSQL��"Where����
	 * 
	 * @param sqlSb
	 *            SQL�ַ���
	 * @param paramList
	 *            SQL�����б�
	 * @param columnName
	 *            ����
	 * @param columnValue
	 *            ��ֵ �ɿ�
	 * @param isLike
	 *            �Ƿ�ģ������
	 * @param blockMergeLogic
	 *            ����ϲ��߼�����ͷ�����ɿա�eg: AND, OR
	 */
	public static void appendKsqlColWhereFrag(StringBuffer sqlSb, List paramList, String columnName,
			Object columnValue, boolean isLike, String blockMergeLogic) {
		if (null != columnValue) {
			// �ַ��ͣ�ȥ����β�ո�
			if (columnValue instanceof CharSequence) {
				columnValue = columnValue.toString().trim();
				// ���Ϊ""���򲻲�ѯ
				if (FdcStringUtil.isEmpty((String) columnValue)) {
					return;
				}
			}

			if (FdcStringUtil.isNotBlank(blockMergeLogic)) {
				sqlSb.append("	").append(blockMergeLogic);
			}
			if (isLike) {
				sqlSb.append(" UCASE(" + columnName + ") LIKE %?% ");
			} else {
				sqlSb.append(" UCASE(" + columnName + ") = ? ");
			}

			paramList.add(columnValue);
		}
	}

	/**
	 * ����"����"����
	 * 
	 * @param sp
	 *            SQL����
	 * @param columnName
	 *            ����
	 * @param columnValues
	 *            ��ֵ����
	 * @param blockMergeLogic
	 *            ����ϲ��߼�����ͷ�����ɿա�eg: AND, OR
	 */
	public static String generateInFrag(SqlParams sp, String columnName, Object[] columnValues, String blockMergeLogic) {
		StringBuffer sql = new StringBuffer();

		if (FdcArrayUtil.isNotEmpty(columnValues)) {
			if (FdcStringUtil.isNotBlank(blockMergeLogic)) {
				sql.append("	").append(blockMergeLogic);
			}
			sql.append("	( ");

			Object columnValue = null;
			for (int i = 0, len = columnValues.length; i < len; i++) {
				columnValue = columnValues[i];

				if (null == columnValue) {
					if (i == 0) {
						sql.append(" " + columnName + " IS NULL ");
					} else {
						sql.append(" Or " + columnName + " IS NULL ");
					}
				} else {
					if (i == 0) {
						sql.append(" " + columnName + " = ? ");
					} else {
						sql.append(" Or " + columnName + " = ? ");
					}
				}

				sp.addObject(columnValue);
			}

			sql.append(" ) \r\n ");
		}

		return sql.toString();
	}

	/**
	 * ����"����"Ƭ��
	 * 
	 * @param sql
	 *            SQL
	 * @param sp
	 *            SQL����
	 * @param columnName
	 *            ����
	 * @param columnValues
	 *            ��ֵ����
	 * @param blockMergeLogic
	 *            ����ϲ��߼�����ͷ�����ɿա�eg: AND, OR
	 */
	public static void appendInFrag(StringBuffer sql, SqlParams sp, String columnName, Object[] columnValues,
			String blockMergeLogic) {
		String frag = generateInFrag(sp, columnName, columnValues, blockMergeLogic);

		if (FdcStringUtil.isNotBlank(frag)) {
			sql.append(frag);
		}
	}

	/**
	 * ����"����"Ƭ��
	 * 
	 * @param sql
	 *            SQL
	 * @param sp
	 *            SQL����
	 * @param columnName
	 *            ����
	 * @param columnValues
	 *            ��ֵ����
	 * @param blockMergeLogic
	 *            ����ϲ��߼�����ͷ�����ɿա�eg: AND, OR
	 */
	public static void appendInFrag(StringBuffer sql, SqlParams sp, String columnName, Collection columnValues,
			String blockMergeLogic) {
		if (FdcCollectionUtil.isNotEmpty(columnValues)) {
			String frag = generateInFrag(sp, columnName, columnValues.toArray(), blockMergeLogic);

			if (FdcStringUtil.isNotBlank(frag)) {
				sql.append(frag);
			}
		}
	}

	/**
	 * ����"����"����
	 * 
	 * @param columnName
	 *            ����
	 * @param columnValues
	 *            ��ֵ����
	 * @param blockMergeLogic
	 *            ����ϲ��߼�����ͷ�����ɿա�eg: AND, OR
	 */
	public static String generateInFrag(String columnName, String[] columnValues, String blockMergeLogic) {
		StringBuffer sql = new StringBuffer();

		if (FdcArrayUtil.isNotEmpty(columnValues)) {
			if (FdcStringUtil.isNotBlank(blockMergeLogic)) {
				sql.append("	").append(blockMergeLogic);
			}
			sql.append("	( ");

			Object columnValue = null;
			for (int i = 0, len = columnValues.length; i < len; i++) {
				columnValue = columnValues[i];

				if (null == columnValue) {
					if (i == 0) {
						sql.append(" " + columnName + " IS NULL ");
					} else {
						sql.append(" Or " + columnName + " IS NULL ");
					}
				} else {
					if (i == 0) {
						sql.append(" " + columnName + " = '" + columnValue + "'");
					} else {
						sql.append(" Or " + columnName + " = '" + columnValue + "'");
					}
				}
			}

			sql.append(" ) \r\n ");
		}

		return sql.toString();
	}

	/**
	 * ����"����"Ƭ��
	 * 
	 * @param sql
	 *            SQL
	 * @param columnName
	 *            ����
	 * @param columnValues
	 *            ��ֵ����
	 * @param blockMergeLogic
	 *            ����ϲ��߼�����ͷ�����ɿա�eg: AND, OR
	 */
	public static void appendInFrag(StringBuffer sql, String columnName, String[] columnValues, String blockMergeLogic) {
		String frag = generateInFrag(columnName, columnValues, blockMergeLogic);

		if (FdcStringUtil.isNotBlank(frag)) {
			sql.append(frag);
		}
	}

	/**
	 * ����"����"Ƭ��
	 * 
	 * @param sql
	 *            SQL
	 * @param columnName
	 *            ����
	 * @param columnValues
	 *            ��ֵ����
	 * @param blockMergeLogic
	 *            ����ϲ��߼�����ͷ�����ɿա�eg: AND, OR
	 */
	public static void appendInFrag(StringBuffer sql, String columnName, Collection columnValues, String blockMergeLogic) {
		if (FdcCollectionUtil.isNotEmpty(columnValues)) {
			appendInFrag(sql, columnName, (String[]) columnValues.toArray(new String[0]), blockMergeLogic);
		}
	}

	/**
	 * ���Ӷ�̬AndƬ��
	 * 
	 * @param sql
	 * @param sp
	 * @param columnNameArr
	 * @param columnValueArr
	 * @param blockMergeLogic
	 */
	public static void appendDynaAndFrag(StringBuffer sql, SqlParams sp, String[] columnNameArr,
			Object[] columnValueArr, String blockMergeLogic) {
		String msg = "�������鳤�ȣ�" + columnNameArr.length + "����������ֵ���鳤�ȣ�" + columnValueArr.length;
		Assert.assertEquals(msg, columnNameArr.length, columnValueArr.length);

		Map map = new LinkedHashMap();
		String key = null;
		Object value = null;
		for (int i = 0, len = columnNameArr.length; i < len; i++) {
			key = columnNameArr[i];
			value = columnValueArr[i];

			map.put(key, value);
		}

		// ���Ӷ�̬AndƬ��
		appendDynaAndFrag(sql, sp, map, blockMergeLogic);
	}

	/**
	 * ���Ӷ�̬AndƬ��
	 * 
	 * @param sql
	 * @param sp
	 * @param map
	 * @param blockMergeLogic
	 */
	public static void appendDynaAndFrag(StringBuffer sql, SqlParams sp, Map map, String blockMergeLogic) {
		if (FdcMapUtil.isNotEmpty(map)) {
			Set set = map.keySet();

			if (FdcStringUtil.isNotBlank(blockMergeLogic)) {
				sql.append("	").append(blockMergeLogic).append("	\r\n");
			}
			sql.append("	(	\r\n");

			int i = 0;
			String key = null;
			Object value = null;
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				key = (String) iterator.next();
				// �������Ϊ�գ���ֱ�Ӻ���
				if (FdcStringUtil.isBlank(key)) {
					continue;
				}
				value = map.get(key);

				if (null != value) {
					if (value instanceof String && FdcStringUtil.isBlank(value.toString())) {
						continue;
					} else {
						if (0 != (i++)) {
							sql.append("	AND ");
						}
						sql.append(key).append(" = ?	\r\n");
						sp.addObject(value);
					}
				}
			}

			sql.append("	)	\r\n");
		}
	}

	/**
	 * ����Sql����
	 * 
	 * @param params
	 * @param newSqlParams
	 */
	public static void appendSqlParameters(RptParams params, SqlParams newSqlParams) {
		// ���ز���
		SqlParams oldSqlParams = (SqlParams) params.getObject(FdcRptConstant.RPT_PARAMETERS);
		Object[] oldSqlParamsArr = null;
		if (null != oldSqlParams) {
			oldSqlParamsArr = oldSqlParams.getParams();
		}
		oldSqlParamsArr = (Object[]) FdcObjectUtil.defaultIfNull(oldSqlParamsArr, new Object[0]);

		Object[] appendSqlParamsArr = newSqlParams.getParams();
		if (appendSqlParamsArr.length > 0) {
			List newSqlParamsList = new ArrayList(Arrays.asList(oldSqlParamsArr));
			newSqlParamsList.addAll(Arrays.asList(appendSqlParamsArr));
			newSqlParams.setParams(newSqlParamsList.toArray());

			params.setObject(FdcRptConstant.RPT_PARAMETERS, newSqlParams);
		}
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ��ʼ������Map
	 */
	private static void initTypeMap() {
		if (null == COLUMN_TYPE_CODE_STR_MAP) {
			COLUMN_TYPE_CODE_STR_MAP = new LinkedHashMap();
			COLUMN_TYPE_STR_CODE_MAP = new LinkedHashMap();

			COLUMN_TYPE_CODE_STR_MAP.put(new Integer(3), "decimal");
			COLUMN_TYPE_CODE_STR_MAP.put(new Integer(4), "int");
			COLUMN_TYPE_CODE_STR_MAP.put(new Integer(5), "int");
			COLUMN_TYPE_CODE_STR_MAP.put(new Integer(6), "float");
			COLUMN_TYPE_CODE_STR_MAP.put(new Integer(-6), "int");
			COLUMN_TYPE_CODE_STR_MAP.put(new Integer(7), "double");
			COLUMN_TYPE_CODE_STR_MAP.put(new Integer(-7), "int");
			COLUMN_TYPE_CODE_STR_MAP.put(new Integer(8), "double");
			COLUMN_TYPE_CODE_STR_MAP.put(new Integer(93), "datetime");
			COLUMN_TYPE_CODE_STR_MAP.put(new Integer(-2), "timestamp");
			COLUMN_TYPE_CODE_STR_MAP.put(new Integer(92), "timestamp");
			COLUMN_TYPE_CODE_STR_MAP.put(new Integer(12), "string");
			COLUMN_TYPE_CODE_STR_MAP.put(new Integer(1), "string");
			COLUMN_TYPE_CODE_STR_MAP.put(new Integer(2005), "clob");
			COLUMN_TYPE_CODE_STR_MAP.put(new Integer(2), "int");
			COLUMN_TYPE_CODE_STR_MAP.put(new Integer(91), "date");
			COLUMN_TYPE_CODE_STR_MAP.put(new Integer(-1), "ntext");

			COLUMN_TYPE_STR_CODE_MAP.put("decimal", new Integer(3));
			COLUMN_TYPE_STR_CODE_MAP.put("int", new Integer(4));
			COLUMN_TYPE_STR_CODE_MAP.put("int", new Integer(5));
			COLUMN_TYPE_STR_CODE_MAP.put("float", new Integer(6));
			COLUMN_TYPE_STR_CODE_MAP.put("int", new Integer(-6));
			COLUMN_TYPE_STR_CODE_MAP.put("double", new Integer(7));
			COLUMN_TYPE_STR_CODE_MAP.put("int", new Integer(-7));
			COLUMN_TYPE_STR_CODE_MAP.put("double", new Integer(8));
			COLUMN_TYPE_STR_CODE_MAP.put("datetime", new Integer(93));
			COLUMN_TYPE_STR_CODE_MAP.put("timestamp", new Integer(-2));
			COLUMN_TYPE_STR_CODE_MAP.put("timestamp", new Integer(92));
			COLUMN_TYPE_STR_CODE_MAP.put("string", new Integer(12));
			COLUMN_TYPE_STR_CODE_MAP.put("string", new Integer(1));
			COLUMN_TYPE_STR_CODE_MAP.put("clob", new Integer(2005));
			COLUMN_TYPE_STR_CODE_MAP.put("int", new Integer(2));
			COLUMN_TYPE_STR_CODE_MAP.put("date", new Integer(91));
			COLUMN_TYPE_STR_CODE_MAP.put("ntext", new Integer(-1));
		}
	}

	/**
	 * �������ʹ��룬ȡ�ö�Ӧ�������ַ���
	 * 
	 * @param typeCode
	 *            ���ʹ���
	 * @return
	 */
	public static String getTypeStr(Integer typeCode) {
		String typeStr = null;

		// ��ʼ������Map
		initTypeMap();
		typeStr = (String) COLUMN_TYPE_CODE_STR_MAP.get(typeCode);

		return typeStr;
	}

	/**
	 * ���������ַ�����ȡ�ö�Ӧ�����ʹ���
	 * 
	 * @param typeStr
	 *            �����ַ���
	 * @return
	 */
	public static Integer getTypeCode(String typeStr) {
		Integer typeCode = null;

		// ��ʼ������Map
		initTypeMap();
		typeCode = (Integer) COLUMN_TYPE_STR_CODE_MAP.get(typeStr);

		return typeCode;
	}

	/**
	 * ��������
	 * <p>
	 * Ĭ��"�Ƿ���붺��"
	 * 
	 * @param result
	 * @param restmd
	 * @param sqlSb
	 * @param numberOfColumns
	 * @param k
	 * @return
	 * @throws SQLException
	 */
	public static StringBuffer dealData(ResultSet result, ResultSetMetaData restmd, StringBuffer sqlSb,
			int numberOfColumns, int k) throws SQLException {
		for (int i = k; i <= numberOfColumns; i++) {
			int typeCode = restmd.getColumnType(i);
			String typeStr = getTypeStr(new Integer(typeCode));

			if (null != typeStr) {
				// Ĭ��"���붺��"
				sqlSb = getData(result, sqlSb, i, typeStr, true);
				// break;
			}
		}

		return sqlSb;
	}

	/**
	 * �����ݿ���ȡ��ÿ�е����ݣ����ж��Ƿ�Ϊ��
	 * 
	 * 
	 * @param resultSet
	 * @param sqlSb
	 * @param i
	 * @param typeStr
	 * @param isInsertComma
	 *            �Ƿ���붺��
	 * @return
	 */
	public static StringBuffer getData(ResultSet resultSet, StringBuffer sqlSb, int i, String typeStr,
			boolean isInsertComma) {
		String sql = getData(resultSet, i, typeStr, isInsertComma);

		sqlSb.append(sql);

		return sqlSb;
	}

	/**
	 * �����ݿ���ȡ��ÿ�е����ݣ����ж��Ƿ�Ϊ��
	 * 
	 * @param resultSet
	 * @param i
	 * @param typeStr
	 * @param isInsertComma
	 *            �Ƿ���붺��
	 * @return
	 */
	public static String getData(ResultSet resultSet, int i, String typeStr, boolean isInsertComma) {
		String sql = "";

		try {
			if (isInsertComma && i != 1) {
				sql += " , ";
			}
			if (typeStr == "int") {
				sql += resultSet.getInt(i);
			}
			if (typeStr == "timestamp") {
				if (resultSet.wasNull()) {
					sql += "getdate()";
				} else
					sql += "null";
			}
			if (typeStr == "float") {
				if (resultSet.wasNull()) {
					sql += "null";
				} else
					sql += resultSet.getShort(i);
			}
			if (typeStr == "double") {
				if (resultSet.wasNull()) {
					sql += "null";
				} else
					sql += resultSet.getDouble(i);
			}
			if (typeStr == "decimal") {
				if (resultSet.wasNull()) {
					sql += "null";
				} else
					sql += resultSet.getBigDecimal(i);
			}
			if (typeStr.equals("string") || typeStr.equals("ntext")) {
				String tempStr = resultSet.getString(i);
				if (tempStr == null) {
					sql += "null";
				} else {
					sql += "N'" + getString(parseString(tempStr)) + "'";
				}
			}
			if (typeStr.equals("clob")) {
				Clob tempClob = resultSet.getClob(i);
				if (tempClob == null) {
					sql += "null";
				} else {
					// ȡ�����ַ���
					String tempStr = parseLineString(tempClob);
					sql += "N'" + getString(parseString(tempStr)) + "'";
				}
			}
			if (typeStr == "date") {
				if (resultSet.wasNull()) {
					sql += "null";
				} else
					sql += "'" + resultSet.getString(i) + "'";
			}
			if (typeStr == "datetime") {
				String value = null;
				Date date = null;
				
				try {
					// String[] parsePatterns = { "yyyy-MM-dd'T'HH:mm:ss" };
					// date = DateUtils.parseDate(value, parsePatterns);
					date = resultSet.getTimestamp(i);
					
					if (null != date) {
						value = DateFormatUtils.format(date, "yyyy-MM-dd' 'HH:mm:ss");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (FdcStringUtil.isBlank(value)) {
					value = getString(parseString(resultSet.getString(i)));
				}

				if (FdcStringUtil.isNotBlank(value)) {
					sql += "{ts'" + value + "'}";
				} else {
					sql += "null";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sql;
	}

	/**
	 * ȡ���ַ��������ж��Ƿ�Ϊ��
	 * 
	 * @param sqlSb
	 * @return
	 */
	public static String getString(String sqlSb) {
		if (sqlSb == null) {
			return "null";
		} else {
			return sqlSb;
		}

	}

	/**
	 * �����ַ����е�ת���ַ�
	 * 
	 * @param sqlSb
	 * @return
	 */
	public static String parseString(String sqlSb) {
		if (sqlSb == null)
			return "";
		int len = 0;
		String tmp = "";
		len = sqlSb.length();

		for (int i = 0; i < len; i++) {
			if (String.valueOf(sqlSb.charAt(i)).equals("\'")) {
				tmp = tmp + "\'\'";

			} else {
				tmp = tmp + sqlSb.charAt(i);

			}
		}
		if (tmp.endsWith(".0"))
			tmp = tmp.substring(0, tmp.length() - 2);
		return tmp;
	}

	/**
	 * ȡ�����ַ���
	 * 
	 * @param sqlSb
	 * @return
	 */
	public static String parseLineString(Clob sqlSb) {
		if (sqlSb == null)
			return "";

		StringBuffer sb = new StringBuffer();

		Reader reader = null;
		BufferedReader br = null;
		try {
			reader = sqlSb.getCharacterStream();
			br = new BufferedReader(reader);

			String str;
			str = br.readLine();
			while (null != str) {
				if (FdcStringUtil.isNotBlank(str)) {
					str = str.trim();

					// // SQL�е�ת���ַ�
					// int index = str.indexOf("--");
					// if (0 == index) {
					// str = br.readLine();
					// continue;
					// } else if (0 < index) {
					// str = str.substring(0, index);
					// }

					sb.append(" ");
					sb.append(str);
					sb.append(" ");
				}

				str = br.readLine();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// �ȹر����������ٹر�������
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString().trim();
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ȡ��Ψһ���ֶΣ���Ӧ�������ַ���
	 * 
	 * @param restmd
	 * @param keyField
	 * @return
	 * @throws SQLException
	 */
	public static String getKeyFieldTypeStr(ResultSetMetaData restmd, String keyField) throws SQLException {
		String typeStr = null;

		Integer typeCode = getKeyFieldTypeCode(restmd, keyField);
		if (null != typeCode) {
			typeStr = getTypeStr(typeCode);
		}

		return typeStr;
	}

	/**
	 * ȡ��Ψһ���ֶΣ���Ӧ�����ʹ���
	 * 
	 * @param restmd
	 * @param keyField
	 * @return
	 * @throws SQLException
	 */
	public static Integer getKeyFieldTypeCode(ResultSetMetaData restmd, String keyField) throws SQLException {
		Integer typeCode = null;

		Integer index = getKeyFieldIndex(restmd, keyField);
		if (null != index) {
			typeCode = new Integer(restmd.getColumnType(index.intValue()));
		}

		return typeCode;
	}

	/**
	 * ȡ��Ψһ���ֶΣ���Ӧ��������
	 * 
	 * @param restmd
	 * @param keyField
	 * @return
	 * @throws SQLException
	 */
	public static Integer getKeyFieldIndex(ResultSetMetaData restmd, String keyField) throws SQLException {
		Integer index = null;

		String name = null;

		int numberOfColumns = restmd.getColumnCount();
		int k = 1;
		for (int i = k; i <= numberOfColumns; i++) {
			name = restmd.getColumnName(i);

			if (name.equalsIgnoreCase(keyField)) {
				index = new Integer(i);
				break;
			}
		}

		return index;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ת���ɲ���KSQL���
	 * 
	 * @param tableName
	 *            ���ݱ���
	 * @param result
	 *            �����
	 * @param ifExist
	 *            �Ƿ���д����ж�
	 * @param keyField
	 *            Ψһ���ֶ�
	 * @return
	 */
	public static StringBuffer transferToInsertKsql(String tableName, ResultSet result, boolean ifExist, String keyField) {
		StringBuffer sqlSb = new StringBuffer();

		try {
			ResultSetMetaData restmd = result.getMetaData();
			int numberOfColumns = restmd.getColumnCount();
			int iRowGo = 0;
			int iAllRow = 0;

			if (ifExist) {
				String ifSqlFrag = "if not exists(select 1 from " + tableName + " where " + keyField + " = ";
				int keyIndex = FdcSqlUtil.getKeyFieldIndex(restmd, keyField).intValue();
				int keyTypeCode = restmd.getColumnType(keyIndex);
				String keyTypeStr = FdcSqlUtil.getTypeStr(new Integer(keyTypeCode));

				while (result.next()) {
					iRowGo = iRowGo + 1;
					iAllRow = iAllRow + 1;

					sqlSb.append(ifSqlFrag);
					// ��"���붺��"
					FdcSqlUtil.getData(result, sqlSb, keyIndex, keyTypeStr, false);
					sqlSb.append(" ) ");
					sqlSb.append("insert into " + tableName + " (");
					int k = 1;
					for (int i = k; i <= numberOfColumns; i++) {
						if (i > 1)
							sqlSb.append(" , " + restmd.getColumnName(i));
						else
							sqlSb.append(restmd.getColumnName(i));
					}

					sqlSb.append(")  values( ");

					// ��������
					FdcSqlUtil.dealData(result, restmd, sqlSb, numberOfColumns, k);
					sqlSb.append(");");
					sqlSb.append("\n");
				}
			} else {
				while (result.next()) {
					iRowGo = iRowGo + 1;
					iAllRow = iAllRow + 1;

					sqlSb.append("insert into " + tableName + " (");
					int k = 1;
					for (int i = k; i <= numberOfColumns; i++) {
						if (i > 1)
							sqlSb.append(" , " + restmd.getColumnName(i));
						else
							sqlSb.append(restmd.getColumnName(i));
					}

					sqlSb.append(")  values( ");

					// ��������
					FdcSqlUtil.dealData(result, restmd, sqlSb, numberOfColumns, k);
					sqlSb.append(");");
					sqlSb.append("\n");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			SQLUtils.cleanup(result);
		}

		return sqlSb;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * ������ת���ɴ���KSQL���
	 * 
	 * @param srcFileName
	 *            Դ�ļ�����
	 * @param destFileName
	 *            Ŀ���ļ�����
	 * @return
	 * @author RD_skyiter_wang
	 * @createDate 2014-2-12
	 */
	public static StringBuffer transferToCreateKsql(String srcFileName, String destFileName) {
		StringBuffer sqlSb = new StringBuffer();

		try {
			File srcFile = new File(srcFileName);
			List strList = FdcFileUtil.readLines(srcFile);
			File destFile = new File(destFileName);

			String sqlStrLine = null;
			int tableBeginIndex = 0;
			int tableEndIndex = 0;
			String tableName = null;
			String ifExistSqlTemplate = "IF NOT EXISTS (SELECT 1 FROM KSQL_USERTABLES WHERE KSQL_TABNAME='{0}')";
			String ifExistSql = null;
			int length = "Create Table ".length();
			for (int i = 0, size = strList.size(); i < size; i++) {
				sqlStrLine = (String) strList.get(i);

				tableBeginIndex = sqlStrLine.indexOf("Create Table ");
				if (-1 == tableBeginIndex) {
					continue;
				}
				tableEndIndex = sqlStrLine.indexOf(" ", tableBeginIndex + length);
				if (-1 == tableEndIndex) {
					continue;
				}
				tableName = sqlStrLine.substring(tableBeginIndex + length, tableEndIndex);
				tableName = tableName.trim();

				ifExistSql = ifExistSqlTemplate.replaceFirst("\\{0\\}", tableName);
				sqlStrLine = ifExistSql + " \n" + sqlStrLine + " \n\n";

				sqlSb.append(sqlStrLine);
			}

			String sqlSbStr = sqlSb.toString();
			FdcFileUtil.writeStringToFile(destFile, sqlSbStr);
			System.out.println(sqlSbStr);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return sqlSb;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) {
		String srcFileName = "W:/workspace750_0912_750er/01_ex_sql/export_sql/CreateTable_basedata.sql";
		String destFileName = "W:/workspace750_0912_750er/01_ex_sql/export_sql/CreateTable_basedata_ifexist.sql";
		transferToCreateKsql(srcFileName, destFileName);
	}
}
