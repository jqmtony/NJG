package com.kingdee.eas.fdc.basedata.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FdcRptConstant;
import com.kingdee.eas.fdc.basedata.util.FdcClassUtil;
import com.kingdee.eas.fdc.basedata.util.FdcMapUtil;
import com.kingdee.eas.fdc.basedata.util.FdcParamUtil;
import com.kingdee.eas.fdc.basedata.util.FdcRptUtil;
import com.kingdee.eas.fdc.basedata.util.FdcSqlUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.SqlParams;

/**
 * 房地产报表Facade基类
 * 
 * @author 王正
 * @email SkyIter@live.com
 * @date 2013-9-27
 * @see
 * @since JDK1.4
 */
public class FdcRptBaseFacadeControllerBean extends AbstractFdcRptBaseFacadeControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.app.FdcRptBaseFacadeControllerBean");

	// /////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////

	// 精度
	protected int amountPre = FDCConstants.SCALE_AMOUNT;
	protected int qtyPre = FDCConstants.SCALE_QTY;
	protected int pricePre = FDCConstants.SCALE_PRICE;
	protected int percentPre = FDCConstants.SCALE_PERCENT;
	protected int ratePre = FDCConstants.SCALE_RATE;
	protected int unitAmountPre = FDCConstants.SCALE_UNITAMOUNT;
	protected int unitQtyPre = FDCConstants.SCALE_UNITQTY;

	// 最小显示精度
	protected int ltamountPre = FDCConstants.SCALE_LTAMOUNT;
	protected int ltqtyPre = FDCConstants.SCALE_LTQTY;
	protected int ltpricePre = FDCConstants.SCALE_LTPRICE;
	protected int ltpercentPre = FDCConstants.SCALE_LTPERCENT;
	protected int ltratePre = FDCConstants.SCALE_LTRATE;
	protected int ltunitAmountPre = FDCConstants.SCALE_LTUNITAMOUNT;
	protected int ltunitQtyPre = FDCConstants.SCALE_LTUNITQTY;

	// 是否已经处理初始化参数
	protected boolean isDealWithInitParam = false;

	// /////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////

	/**
	 * 处理初始化参数
	 * <p>
	 * 客户端通常只初始化一次，可以在onLoad时加载初始化参数。但是服务端调用是非常频繁的，处于性能考虑，建议延迟加载
	 * 
	 * @param ctx
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void dealWithInitParam(Context ctx) throws BOSException, EASBizException {
		// 延迟加载初始化参数
		if (!isDealWithInitParam) {
			fetchInitParam(ctx);
			isDealWithInitParam = true;
		}
	}

	// 子类可以重写
	protected Map fetchInitParam(Context ctx) throws BOSException, EASBizException {
		// 这里只获取集团参数
		Map param = FdcParamUtil.getDefaultFdcParam(ctx);

		// 使用精度参数
		if (isUseScaleParam()) {
			// 精度
			amountPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC102_AMOUT, amountPre);
			qtyPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC103_QTY, qtyPre);
			pricePre = FdcMapUtil.getIntValue(param, FDCConstants.FDC104_PRICE, pricePre);
			percentPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC102_PERCENT, percentPre);
			ratePre = FdcMapUtil.getIntValue(param, FDCConstants.FDC103_RATE, ratePre);
			unitAmountPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC104_UNITAMOUNT, unitAmountPre);
			unitQtyPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC104_UNITQTY, unitQtyPre);

			// 显示精度
			ltamountPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC105_LTAMOUT, ltamountPre);
			ltqtyPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC106_LTQTY, ltqtyPre);
			ltpricePre = FdcMapUtil.getIntValue(param, FDCConstants.FDC107_LTPRICE, ltpricePre);
			ltpercentPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC105_LTPERCENT, ltpercentPre);
			ltratePre = FdcMapUtil.getIntValue(param, FDCConstants.FDC106_LTRATE, ltratePre);
			ltunitAmountPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC107_LTUNITAMOUNT, ltunitAmountPre);
			ltunitQtyPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC107_LTUNITQTY, ltunitQtyPre);
		}

		// 如果获取公司参数，使用 FdcParamUtils.getDefaultFdcParam(ctx,orgunitid);

		return param;
	}

	/**
	 * 是否使用精度参数
	 * <p>
	 * 1、目前很多单据中精度是手动写死，不能直接设置成"true"。当所有的单据排查完毕无误后，可以在基类中统一设置成"true"<br>
	 * 2、默认为"false"，子类可以重写。<br>
	 * 3、当返回值为"true"时，请确保所有的精度都是由参数来控制，不要手动写死<br>
	 * 
	 * @return
	 */
	protected boolean isUseScaleParam() {
		return FDCConstants.IS_USE_SCALE_PARAM;
	}

	// //////////////////////////////////////////////////////////////////////////
	// /
	// //////////////////////////////////////////////////////////////////////////
	// /

	// 默认使用临时表查询
	protected boolean useTempTable() {
		return true;
	}

	/**
	 * 分页查询数据 在这里查询必要的数据
	 */
	protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException {

		RptParams rpt = new RptParams();

		// 使用临时表查询数据，分成以下步骤实现
		if (useTempTable()) {

			// 插入数据到临时表
			String tempTable = insertDataIntoTempTable(ctx, params);
			if (tempTable == null) {
				return rpt;
			}
			rpt.setObject(FdcRptConstant.RPT_TEMPTABLE, tempTable);

			// 执行取数SQL，从临时表取数
			doQuery(ctx, params, rpt);

			// 返回前，若数据取完，删除临时表
			doSomethingBeforeReturn(ctx, params);
		} else {
			// 如果不是用临时表，必须重载此方法，自己写sql实现
			// 请重载useTempTable()，返回false
			doQuery(ctx, params, rpt);
		}

		return rpt;
	}

	/**
	 * 插入临时表前事件\插入数据到临时表
	 * 
	 * @param params
	 */
	protected String insertDataIntoTempTable(Context ctx, RptParams params) throws BOSException, EASBizException {
		// 处理报表参数
		dealRptParams(params);

		// 设置临时表名\拼装SQL
		String tmpSql = getInserDataSql(ctx, params);
		if (tmpSql == null) {
			return null;
		} else {
			// 设置Sql常量
			params.setObject(FdcRptConstant.RPT_INSERDATASQL, tmpSql);
		}
		SqlParams parameters = (SqlParams) params.getObject(FdcRptConstant.RPT_PARAMETERS);

		try {
			// 获取测试script ,将参数?替换为真实参数
			String tmpSqlTest = FdcSqlUtil.getTestSql(tmpSql, parameters);
			// 设置TestSql常量
			params.setObject(FdcRptConstant.RPT_INSERDATASQL_TEST, tmpSqlTest);
			// 取得简单类名
			String simpleName = FdcClassUtil.getSimpleName(this.getClass());

			// logger.info(
			// "==============================================================="
			// );
			// logger.info(simpleName + ".getInserDataSql():\n " + tmpSqlTest);
			// logger.info(
			// "==============================================================="
			// );

			// 以下代码做调试用 begin
			// FileOutputStream out = new
			// FileOutputStream("D:\\tempSql\\tempSql121113.txt");
			// out.write(tmpSqlTest.getBytes());
			// out.close();

			// 以下代码做调试用 end
		} catch (Exception e) {
			logger.error(e);
		}

		// 插入数据到临时表
		String tempTable = executeCreateTableAsSelectInto(
				"select * into TEMP_EcRptBaseFacade from (" + tmpSql + ") as tempTable", parameters, ctx)
				.getTempTable();
		params.setObject(FdcRptConstant.RPT_TEMPTABLE, tempTable);

		return tempTable;
	}

	// 执行取数SQL，从临时表取数
	// 重写该方法,可以多次从临时表取数
	protected void doQuery(Context ctx, RptParams params, RptParams rpt) throws BOSException, EASBizException {
		RptRowSet rs = executeSqlForData(ctx, params);
		rpt.setObject(FdcRptConstant.RPT_QUERYRESULT, rs);
	}

	/**
	 * 执行取数SQL，从临时表取数
	 * 
	 * @param params
	 * @return List
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected RptRowSet executeSqlForData(Context ctx, RptParams params) throws BOSException, EASBizException {
		String sql = getSelectSql(ctx, params);

		SqlParams parameters = null;// (SqlParams)params.getObject(FdcRptUtil.
		// RPT_PARAMETERS);
		// 执行取数SQL，从临时表取数
		return executeQuery(sql, parameters, ctx);
	}

	/**
	 * 返回以前事件.可继承此方法，用于返回以前需要做的工作
	 * 
	 * @param params
	 * @param result
	 *            返回结果集
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void doSomethingBeforeReturn(Context ctx, RptParams params) throws BOSException, EASBizException {
		// 若数据取完，删除临时表
		String tempTable = (String) params.getObject(FdcRptConstant.RPT_TEMPTABLE);
		dropTempTable(tempTable, ctx);
	}

	protected String getPeriodType(Context ctx, String costCenterId) throws EASBizException, BOSException {
		return null;
	}

	/**
	 * 获取期间范围
	 */
	protected Map _getPeriodRange(Context ctx, String projectOrgId, String system) throws BOSException, EASBizException {
		return null;
	}

	/**
	 * 获取期间范围
	 */
	protected Map _getPeriodRange(Context ctx, String projectOrgId, String projectId, String system)
			throws BOSException, EASBizException {
		return null;
	}

	/**
	 * 取期间范围
	 * 
	 * @param type
	 *            0 所有期间 1 只包括启用期间以后 2 只包括当前期间以前 3 只包括启用期间以后，当前期间以前
	 */
	protected Map getPeriodRangeNormal(Context ctx, int type, PeriodInfo startPeriod, PeriodInfo currentPeriod,
			String periodType) throws BOSException, EASBizException {
		HashMap map = new HashMap();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TP.FPeriodYear, \r\n");
		sql.append("	Min(TP.FPeriodNumber) MinNumber, \r\n");
		sql.append("	Max(TP.FPeriodNumber) MaxNumber \r\n");
		sql.append("FROM T_BD_Period TP \r\n");
		sql.append("WHERE TP.FTypeID = ? \r\n");

		if ((type == 1 || type == 3) && startPeriod != null) {
			// sql.append(" AND TP.FPeriodYear * 100 + TP.FPeriodNumber >= ?
			// \r\n");
			sql.append(" AND (TP.FPeriodYear>? or (TP.FPeriodYear=?  and TP.FPeriodNumber >= ?)) \r\n");
		}
		if ((type == 2 || type == 3) && currentPeriod != null) {
			// sql.append(" AND TP.FPeriodYear * 100 + TP.FPeriodNumber <= ?
			// \r\n");
			sql.append(" AND (TP.FPeriodYear<? or(TP.FPeriodYear=? and TP.FPeriodNumber <= ?)) \r\n");
		}
		sql.append("GROUP BY TP.FPeriodYear \r\n");
		sql.append("ORDER BY TP.FPeriodYear \r\n");

		// 加入参数
		SqlParams parameter = new SqlParams();
		{
			parameter.addString(periodType);

			if ((type == 1 || type == 3) && startPeriod != null) {
				// parameter.addValue(startPeriod.getPeriodYear() * 100+
				// startPeriod.getPeriodNumber());
				parameter.addInt(startPeriod.getPeriodYear());
				parameter.addInt(startPeriod.getPeriodYear());
				parameter.addInt(startPeriod.getPeriodNumber());
			}
			if ((type == 2 || type == 3) && currentPeriod != null) {
				// parameter.addValue(currentPeriod.getPeriodYear() * 100+
				// currentPeriod.getPeriodNumber());
				parameter.addInt(currentPeriod.getPeriodYear());
				parameter.addInt(currentPeriod.getPeriodYear());
				parameter.addInt(currentPeriod.getPeriodNumber());

			}
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = this.getConnection(ctx);

			stmt = (PreparedStatement) con.prepareStatement(parameter.getSqlWithParams(sql.toString()));
			stmt.setFetchSize(100);

			rs = stmt.executeQuery();

			int[] yearRange = new int[] { -1, -1 };

			// map.put(ReportInitInfo.PERIOD_RANGE_YEAR_RANGE,yearRange);

			while (rs.next()) {
				int year = rs.getInt("FPeriodYear");
				yearRange[0] = (yearRange[0] == -1) ? year : yearRange[0];
				yearRange[1] = year;

				int[] periodRange = new int[2];
				periodRange[0] = rs.getInt("MinNumber");
				periodRange[1] = rs.getInt("MaxNumber");
				if (periodRange[0] <= 0 || periodRange[1] <= 0) {
					// 异常类型 206 PERIOD_ERROR 获取期间范围不正确，请检查启用期间、当前期间和会计期间的设置是否正确！
					// throw new RptException(RptException.PERIOD_ERROR);
					continue;
				}

				map.put(new Integer(year), periodRange);
			}

			if (yearRange[0] <= 0 || yearRange[1] <= 0
					|| (startPeriod != null && yearRange[0] < startPeriod.getPeriodYear()) || map.size() <= 0) {
				// 异常类型 206 PERIOD_ERROR 获取期间范围不正确，请检查启用期间、当前期间和会计期间的设置是否正确！
				// throw new RptException(RptException.PERIOD_ERROR);
			}

			return map;
		} catch (SQLException sqle) {
			sqle.setNextException(new SQLException("The query sql is:" + sql.toString()));
			throw new SQLDataException(sqle);
		} finally {
			close(con, stmt, rs);
		}
	}

	/**
	 * 获取临时表的结构，或者说是构造临时表结构
	 * 
	 * @param params
	 * @return
	 * @throws BOSException
	 *             ,EASBizException
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected String getInserDataSql(Context ctx, RptParams params) throws BOSException, EASBizException {
		return null;
	}

	/** 获取取数SQL */
	protected String getSelectSql(Context ctx, RptParams params) throws BOSException, EASBizException {
		String tempTable = (String) params.getObject(FdcRptConstant.RPT_TEMPTABLE);
		return "select * from " + tempTable;
	}

	// 获取期间范围
	protected Map _getPeriodRange(Context ctx, String costCenterId, boolean isProcess) throws BOSException,
			EASBizException {
		return null;
	}

	/**
	 * 项目部查询条件拼接
	 * 
	 * @param orgIds
	 * @param sp
	 * @param columnName
	 */
	protected String createOrgCondition(String[] orgIds, SqlParams sp, String columnName) {
		StringBuffer sql = new StringBuffer();
		if (orgIds != null && orgIds.length > 0) {
			sql.append("          AND ( ");
			for (int i = 0; i < orgIds.length; i++) {
				String centerId = orgIds[i] != null ? orgIds[i] : "";
				if (i == 0) {
					sql.append(" (" + columnName + " = ? )");
				} else {
					sql.append(" Or (" + columnName + " = ?  ) \r\n");
				}
				sp.addString(centerId);
			}
			sql.append(" ) \n ");

		}
		return sql.toString();
	}

	/**
	 * 拼装查询条件
	 * 
	 * @author forest_liang
	 * @param conditionValues
	 *            条件值
	 * @param sp
	 * @param columnName
	 *            数据字段名
	 * @return
	 */
	protected String createSqlCondition(String[] conditionValues, SqlParams sp, String columnName) {
		StringBuffer sql = new StringBuffer();
		if (conditionValues != null && conditionValues.length > 0) {
			sql.append("          AND ( ");
			for (int i = 0; i < conditionValues.length; i++) {
				if (conditionValues[i] == null)
					continue;
				String centerId = conditionValues[i];
				if (i == 0) {
					sql.append(" (" + columnName + " = ? )");
				} else {
					sql.append(" Or (" + columnName + " = ?  )");
				}
				sp.addString(centerId);
			}
			sql.append(" ) \n ");
		}
		return sql.toString();
	}

	protected String createSqlCondition(Object[] conditionValues, SqlParams sp, String columnName) {
		StringBuffer sql = new StringBuffer();
		if (conditionValues != null && conditionValues.length > 0) {
			sql.append("          AND ( ");
			for (int i = 0; i < conditionValues.length; i++) {
				if (conditionValues[i] == null)
					continue;
				String centerId = conditionValues[i].toString();
				if (i == 0) {
					sql.append(" (" + columnName + " = ? )");
				} else {
					sql.append(" Or (" + columnName + " = ?  ) \r\n");
				}
				sp.addString(centerId);
			}
			sql.append(" ) \n ");
		}
		return sql.toString();
	}

	/**
	 * 拼装查询条件
	 * 
	 * @author forest_liang
	 * @param conditionValues
	 *            条件值
	 * @param sqlParams放入list中
	 * @param columnName
	 *            数据字段名
	 * @return
	 */
	protected String createSqlCondition(String[] conditionIds, List sqlParams, String columnName) {
		StringBuffer sql = new StringBuffer();
		if (conditionIds != null && conditionIds.length > 0) {
			sql.append("          AND ( ");
			for (int i = 0; i < conditionIds.length; i++) {
				String centerId = conditionIds[i] != null ? conditionIds[i] : "";
				if (i == 0) {
					sql.append(" (" + columnName + " = ? )");
				} else {
					sql.append(" Or (" + columnName + " = ?  )");
				}
				sqlParams.add(centerId);
			}
			sql.append(" ) \n ");
		}
		return sql.toString();
	}

	/**
	 * 处理报表参数
	 * 
	 * @param params
	 *            报表参数
	 */
	protected void dealRptParams(RptParams params) {
		// (1)、转换数组类型的报表参数类型
		// 项目组织
		FdcRptUtil.castRptParamsTypeOfArr(params, "projectOrg.ids", String.class);
		// 工程项目
		FdcRptUtil.castRptParamsTypeOfArr(params, "project.ids", String.class);
	}

}