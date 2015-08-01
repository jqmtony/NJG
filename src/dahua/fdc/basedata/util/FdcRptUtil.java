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
 * 房地产 报表工具类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @date 2013-9-27
 * @see
 * @since JDK1.4
 */
public class FdcRptUtil {

	/**
	 * 零
	 */
	public static final String ZERO = "0";

	/**
	 * null
	 */
	public static final String NULL = "null";

	/**
	 * 空
	 */
	public static final String EMPTY = "";

	/**
	 * RptSql路径
	 */
	public static final String PATH_RPT_SQL = "C:/Temp/RptSql/";

	/**
	 * 成本大类
	 */
	public static final String COST_GENERA = "CostGenera";

	/**
	 * 实际成本
	 */
	public static final String REAL_COST = "RealCost";

	/**
	 * KDTable
	 */
	public static final String KDTABLE = "KDTable";

	/**
	 * 标志位字符串
	 */
	public static final String FLAG_STR = "flagStr";

	/**
	 * 排序字段_1
	 */
	public static final String ORDER_BY_FIELD_1 = "orderByField_1";

	/**
	 * 排序字段_2
	 */
	public static final String ORDER_BY_FIELD_2 = "orderByField_2";

	/**
	 * 相除片段_模板
	 */
	public static final String DIVIDE_FRAG_TEMPLATE = "" + " CASE " + " 	WHEN ({1} IS NOT NULL AND {1} != 0) "
			+ " 	THEN ({0} / {1}) " + " 	ELSE 0 " + " END ";

	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	/**
	 * 获得临时表, 创建的表名可任意指定。返回实际创建/重用的表名
	 * 
	 * @param ctx
	 * @param sql
	 *            表结构
	 * @param desc
	 *            表摘要
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
	 * 释放临时表
	 * 
	 * @param ctx
	 * @param tableName
	 *            表名
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
	 * 转换数组类型的报表参数类型
	 * 
	 * @param params
	 *            报表参数
	 * @param key
	 *            参数名称
	 * @param toType
	 *            数组元素要被转换成的Class类型
	 */
	public static void castRptParamsTypeOfArr(RptParams params, String key, Class toType) {
		Object[] value_oldArrType = (Object[]) params.getObject(key);
		if (null != value_oldArrType) {
			// 取得数组元素的 Class 类型
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
	 * 将Sql写入到文件中
	 * 
	 * @param classz
	 * @param inserDataSql
	 * @param inserDataSql_test
	 * @param sqlParams
	 * @param isAppendTimestamp
	 *            文件名是否附加时间戳
	 * @param subName
	 *            子下标名称
	 */
	public static void writeSqlToFile(Class classz, String inserDataSql, String inserDataSql_test, SqlParams sqlParams,
			boolean isAppendTimestamp, String subName) {
		if (false) {
			System.out.println("FdcRptUtil.writeSqlToFile()：调试用，生产环境下不要写入Sql到文件中！");
			return;
		}
		// 暂时不附加时间戳
		isAppendTimestamp = false;

		String packageName = classz.getName();
		int beginIndex = FDCConstants.EAS_PAKAGE_NAME.length() + 1;
		String filePath = packageName.substring(beginIndex).replaceAll("\\.", "\\/") + "/";
		String fileName_inserDataSql = null;
		String fileName_inserDataSql_test = null;
		String fileName_sqlParams = null;

		// 子下标名称
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
			// 测试Sql
			FdcFileUtil.writeStringToFile(new File(fullFileName_inserDataSql_test), inserDataSql_test);
			// 参数
			FdcFileUtil.writeStringToFile(new File(fullFileName_sqlParams), sqlParams.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将Sql写入到文件中
	 * 
	 * @param classz
	 * @param inserDataSql
	 * @param sqlParams
	 * @param isAppendTimestamp
	 *            文件名是否附加时间戳
	 * @param subName
	 *            子下标名称
	 */
	public static void writeSqlToFile(Class classz, String inserDataSql, SqlParams sqlParams,
			boolean isAppendTimestamp, String subName) {
		// 获取测试script ,将参数?替换为真实参数
		String inserDataSql_test = FdcSqlUtil.getTestSql(inserDataSql, sqlParams);

		// 将Sql写入到文件中
		FdcRptUtil.writeSqlToFile(classz, inserDataSql, inserDataSql_test, sqlParams, isAppendTimestamp, subName);
	}

	/**
	 * 将Sql写入到文件中
	 * 
	 * @param classz
	 * @param inserDataSql
	 * @param params
	 * @param isAppendTimestamp
	 *            文件名是否附加时间戳
	 * @param subName
	 *            子下标名称
	 */
	public static void writeSqlToFile(Class classz, String inserDataSql, Object[] params, boolean isAppendTimestamp,
			String subName) {
		if (FdcArrayUtil.isNotEmpty(params)) {
			SqlParams sqlParams = new SqlParams(params);

			// 将Sql写入到文件中
			FdcRptUtil.writeSqlToFile(classz, inserDataSql, sqlParams, isAppendTimestamp, subName);
		}
	}

	/**
	 * 将Sql写入到文件中
	 * 
	 * @param classz
	 * @param inserDataSql
	 * @param params
	 * @param isAppendTimestamp
	 *            文件名是否附加时间戳
	 * @param subName
	 *            子下标名称
	 */
	public static void writeSqlToFile(Class classz, String inserDataSql, RptParams params, boolean isAppendTimestamp,
			String subName) {
		// 取得测试SQL
		String inserDataSql_test = (String) params.getObject(FdcRptConstant.RPT_INSERDATASQL_TEST);
		// 取得SQL参数
		SqlParams sqlParams = (SqlParams) params.getObject(FdcRptConstant.RPT_PARAMETERS);

		// 将Sql写入到文件中
		if (null == inserDataSql_test) {
			FdcRptUtil.writeSqlToFile(classz, inserDataSql, sqlParams, isAppendTimestamp, subName);
		} else {
			FdcRptUtil.writeSqlToFile(classz, inserDataSql, inserDataSql_test, sqlParams, isAppendTimestamp, subName);
		}
	}

	/**
	 * 将Sql写入到文件中
	 * 
	 * @param classz
	 * @param params
	 * @param isAppendTimestamp
	 *            文件名是否附加时间戳
	 * @param subName
	 *            子下标名称
	 */
	public static void writeSqlToFile(Class classz, RptParams params, boolean isAppendTimestamp, String subName) {
		// 取得SQL
		String inserDataSql = (String) params.getObject(FdcRptConstant.RPT_INSERDATASQL);

		// 将Sql写入到文件中
		if (null != inserDataSql) {
			FdcRptUtil.writeSqlToFile(classz, inserDataSql, params, isAppendTimestamp, subName);
		}
	}

	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

}
