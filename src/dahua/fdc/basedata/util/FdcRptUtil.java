/**
 * @(#)FdcRptUtil.java 1.0 2013-9-27
 * Copyright 2013 Kingdee, Inc. All rights reserved
 */

package com.kingdee.eas.fdc.basedata.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.db.TempTablePool;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FdcRptConstant;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.SqlParams;

/**
 * ���ز� ��������
 * 
 * @author ����
 * @email SkyIter@live.com
 * @date 2013-9-27
 * @see
 * @since JDK1.4
 */
public class FdcRptUtil {

	/**
	 * ��
	 */
	public static final String ZERO = "0";

	/**
	 * null
	 */
	public static final String NULL = "null";

	/**
	 * ��
	 */
	public static final String EMPTY = "";

	/**
	 * RptSql·��
	 */
	public static final String PATH_RPT_SQL = "C:/Temp/RptSql/";

	/**
	 * �ɱ�����
	 */
	public static final String COST_GENERA = "CostGenera";

	/**
	 * ʵ�ʳɱ�
	 */
	public static final String REAL_COST = "RealCost";

	/**
	 * KDTable
	 */
	public static final String KDTABLE = "KDTable";

	/**
	 * ��־λ�ַ���
	 */
	public static final String FLAG_STR = "flagStr";

	/**
	 * �����ֶ�_1
	 */
	public static final String ORDER_BY_FIELD_1 = "orderByField_1";

	/**
	 * �����ֶ�_2
	 */
	public static final String ORDER_BY_FIELD_2 = "orderByField_2";

	/**
	 * ���Ƭ��_ģ��
	 */
	public static final String DIVIDE_FRAG_TEMPLATE = "" + " CASE " + " 	WHEN ({1} IS NOT NULL AND {1} != 0) "
			+ " 	THEN ({0} / {1}) " + " 	ELSE 0 " + " END ";

	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	/**
	 * �����ʱ��, �����ı���������ָ��������ʵ�ʴ���/���õı���
	 * 
	 * @param ctx
	 * @param sql
	 *            ��ṹ
	 * @param desc
	 *            ��ժҪ
	 * @return
	 * @throws BOSException
	 */
	public static String createTempTable(Context ctx, String sql, String desc) throws BOSException {
		String tableName = null;
		tableName = desc;
		sql = "create table " + tableName + " " + sql;
		try {
			tableName = TempTablePool.getInstance(ctx).createTempTable(sql);
		} catch (Exception e) {
			throw new BOSException(e);
		}

		return tableName;
	}

	/**
	 * �ͷ���ʱ��
	 * 
	 * @param ctx
	 * @param tableName
	 *            ����
	 * @throws BOSException
	 */
	public static void dropTempTable(Context ctx, String tableName) throws BOSException {

		try {
			TempTablePool.getInstance(ctx).releaseTable(tableName);
		} catch (Exception e) {
			throw new BOSException(e);
		}

	}

	/**
	 * ת���������͵ı����������
	 * 
	 * @param params
	 *            �������
	 * @param key
	 *            ��������
	 * @param toType
	 *            ����Ԫ��Ҫ��ת���ɵ�Class����
	 */
	public static void castRptParamsTypeOfArr(RptParams params, String key, Class toType) {
		Object[] value_oldArrType = (Object[]) params.getObject(key);
		if (null != value_oldArrType) {
			// ȡ������Ԫ�ص� Class ����
			Class cmpType = value_oldArrType.getClass().getComponentType();
			if (!toType.equals(cmpType)) {
				try {
					Object[] value_newArrType = FdcArrayUtil.cast(value_oldArrType, toType);
					params.setObject(key, value_newArrType);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	/**
	 * ��Sqlд�뵽�ļ���
	 * 
	 * @param classz
	 * @param inserDataSql
	 * @param inserDataSql_test
	 * @param sqlParams
	 * @param isAppendTimestamp
	 *            �ļ����Ƿ񸽼�ʱ���
	 * @param subName
	 *            ���±�����
	 */
	public static void writeSqlToFile(Class classz, String inserDataSql, String inserDataSql_test, SqlParams sqlParams,
			boolean isAppendTimestamp, String subName) {
		if (false) {
			System.out.println("FdcRptUtil.writeSqlToFile()�������ã����������²�Ҫд��Sql���ļ��У�");
			return;
		}
		// ��ʱ������ʱ���
		isAppendTimestamp = false;

		String packageName = classz.getName();
		int beginIndex = FDCConstants.EAS_PAKAGE_NAME.length() + 1;
		String filePath = packageName.substring(beginIndex).replaceAll("\\.", "\\/") + "/";
		String fileName_inserDataSql = null;
		String fileName_inserDataSql_test = null;
		String fileName_sqlParams = null;

		// ���±�����
		if (FdcStringUtil.isNotBlank(subName)) {
			subName = "_" + subName;
		} else {
			subName = "";
		}
		if (isAppendTimestamp) {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateStr = dateFormat.format(new Date());
			subName += "_" + dateStr;
		}
		fileName_inserDataSql = "InserDataSql" + subName;
		fileName_inserDataSql_test = "InserDataSql_Test" + subName;
		fileName_sqlParams = "SqlParams" + subName;
		String extName = ".sql";

		String fullFileName_inserDataSql = PATH_RPT_SQL + filePath + fileName_inserDataSql + extName;
		String fullFileName_inserDataSql_test = PATH_RPT_SQL + filePath + fileName_inserDataSql_test + extName;
		String fullFileName_sqlParams = PATH_RPT_SQL + filePath + fileName_sqlParams + extName;

		try {
			// Sql
			FdcFileUtil.writeStringToFile(new File(fullFileName_inserDataSql), inserDataSql);
			// ����Sql
			FdcFileUtil.writeStringToFile(new File(fullFileName_inserDataSql_test), inserDataSql_test);
			// ����
			FdcFileUtil.writeStringToFile(new File(fullFileName_sqlParams), sqlParams.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��Sqlд�뵽�ļ���
	 * 
	 * @param classz
	 * @param inserDataSql
	 * @param sqlParams
	 * @param isAppendTimestamp
	 *            �ļ����Ƿ񸽼�ʱ���
	 * @param subName
	 *            ���±�����
	 */
	public static void writeSqlToFile(Class classz, String inserDataSql, SqlParams sqlParams,
			boolean isAppendTimestamp, String subName) {
		// ��ȡ����script ,������?�滻Ϊ��ʵ����
		String inserDataSql_test = FdcSqlUtil.getTestSql(inserDataSql, sqlParams);

		// ��Sqlд�뵽�ļ���
		FdcRptUtil.writeSqlToFile(classz, inserDataSql, inserDataSql_test, sqlParams, isAppendTimestamp, subName);
	}

	/**
	 * ��Sqlд�뵽�ļ���
	 * 
	 * @param classz
	 * @param inserDataSql
	 * @param params
	 * @param isAppendTimestamp
	 *            �ļ����Ƿ񸽼�ʱ���
	 * @param subName
	 *            ���±�����
	 */
	public static void writeSqlToFile(Class classz, String inserDataSql, Object[] params, boolean isAppendTimestamp,
			String subName) {
		if (FdcArrayUtil.isNotEmpty(params)) {
			SqlParams sqlParams = new SqlParams(params);

			// ��Sqlд�뵽�ļ���
			FdcRptUtil.writeSqlToFile(classz, inserDataSql, sqlParams, isAppendTimestamp, subName);
		}
	}

	/**
	 * ��Sqlд�뵽�ļ���
	 * 
	 * @param classz
	 * @param inserDataSql
	 * @param params
	 * @param isAppendTimestamp
	 *            �ļ����Ƿ񸽼�ʱ���
	 * @param subName
	 *            ���±�����
	 */
	public static void writeSqlToFile(Class classz, String inserDataSql, RptParams params, boolean isAppendTimestamp,
			String subName) {
		// ȡ�ò���SQL
		String inserDataSql_test = (String) params.getObject(FdcRptConstant.RPT_INSERDATASQL_TEST);
		// ȡ��SQL����
		SqlParams sqlParams = (SqlParams) params.getObject(FdcRptConstant.RPT_PARAMETERS);

		// ��Sqlд�뵽�ļ���
		if (null == inserDataSql_test) {
			FdcRptUtil.writeSqlToFile(classz, inserDataSql, sqlParams, isAppendTimestamp, subName);
		} else {
			FdcRptUtil.writeSqlToFile(classz, inserDataSql, inserDataSql_test, sqlParams, isAppendTimestamp, subName);
		}
	}

	/**
	 * ��Sqlд�뵽�ļ���
	 * 
	 * @param classz
	 * @param params
	 * @param isAppendTimestamp
	 *            �ļ����Ƿ񸽼�ʱ���
	 * @param subName
	 *            ���±�����
	 */
	public static void writeSqlToFile(Class classz, RptParams params, boolean isAppendTimestamp, String subName) {
		// ȡ��SQL
		String inserDataSql = (String) params.getObject(FdcRptConstant.RPT_INSERDATASQL);

		// ��Sqlд�뵽�ļ���
		if (null != inserDataSql) {
			FdcRptUtil.writeSqlToFile(classz, inserDataSql, params, isAppendTimestamp, subName);
		}
	}

	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

}
