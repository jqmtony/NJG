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
 * ���ز�����Facade����
 * 
 * @author ����
 * @email SkyIter@live.com
 * @date 2013-9-27
 * @see
 * @since JDK1.4
 */
public class FdcRptBaseFacadeControllerBean extends AbstractFdcRptBaseFacadeControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.app.FdcRptBaseFacadeControllerBean");

	// /////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////

	// ����
	protected int amountPre = FDCConstants.SCALE_AMOUNT;
	protected int qtyPre = FDCConstants.SCALE_QTY;
	protected int pricePre = FDCConstants.SCALE_PRICE;
	protected int percentPre = FDCConstants.SCALE_PERCENT;
	protected int ratePre = FDCConstants.SCALE_RATE;
	protected int unitAmountPre = FDCConstants.SCALE_UNITAMOUNT;
	protected int unitQtyPre = FDCConstants.SCALE_UNITQTY;

	// ��С��ʾ����
	protected int ltamountPre = FDCConstants.SCALE_LTAMOUNT;
	protected int ltqtyPre = FDCConstants.SCALE_LTQTY;
	protected int ltpricePre = FDCConstants.SCALE_LTPRICE;
	protected int ltpercentPre = FDCConstants.SCALE_LTPERCENT;
	protected int ltratePre = FDCConstants.SCALE_LTRATE;
	protected int ltunitAmountPre = FDCConstants.SCALE_LTUNITAMOUNT;
	protected int ltunitQtyPre = FDCConstants.SCALE_LTUNITQTY;

	// �Ƿ��Ѿ������ʼ������
	protected boolean isDealWithInitParam = false;

	// /////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////

	/**
	 * �����ʼ������
	 * <p>
	 * �ͻ���ͨ��ֻ��ʼ��һ�Σ�������onLoadʱ���س�ʼ�����������Ƿ���˵����Ƿǳ�Ƶ���ģ��������ܿ��ǣ������ӳټ���
	 * 
	 * @param ctx
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void dealWithInitParam(Context ctx) throws BOSException, EASBizException {
		// �ӳټ��س�ʼ������
		if (!isDealWithInitParam) {
			fetchInitParam(ctx);
			isDealWithInitParam = true;
		}
	}

	// ���������д
	protected Map fetchInitParam(Context ctx) throws BOSException, EASBizException {
		// ����ֻ��ȡ���Ų���
		Map param = FdcParamUtil.getDefaultFdcParam(ctx);

		// ʹ�þ��Ȳ���
		if (isUseScaleParam()) {
			// ����
			amountPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC102_AMOUT, amountPre);
			qtyPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC103_QTY, qtyPre);
			pricePre = FdcMapUtil.getIntValue(param, FDCConstants.FDC104_PRICE, pricePre);
			percentPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC102_PERCENT, percentPre);
			ratePre = FdcMapUtil.getIntValue(param, FDCConstants.FDC103_RATE, ratePre);
			unitAmountPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC104_UNITAMOUNT, unitAmountPre);
			unitQtyPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC104_UNITQTY, unitQtyPre);

			// ��ʾ����
			ltamountPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC105_LTAMOUT, ltamountPre);
			ltqtyPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC106_LTQTY, ltqtyPre);
			ltpricePre = FdcMapUtil.getIntValue(param, FDCConstants.FDC107_LTPRICE, ltpricePre);
			ltpercentPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC105_LTPERCENT, ltpercentPre);
			ltratePre = FdcMapUtil.getIntValue(param, FDCConstants.FDC106_LTRATE, ltratePre);
			ltunitAmountPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC107_LTUNITAMOUNT, ltunitAmountPre);
			ltunitQtyPre = FdcMapUtil.getIntValue(param, FDCConstants.FDC107_LTUNITQTY, ltunitQtyPre);
		}

		// �����ȡ��˾������ʹ�� FdcParamUtils.getDefaultFdcParam(ctx,orgunitid);

		return param;
	}

	/**
	 * �Ƿ�ʹ�þ��Ȳ���
	 * <p>
	 * 1��Ŀǰ�ܶ൥���о������ֶ�д��������ֱ�����ó�"true"�������еĵ����Ų��������󣬿����ڻ�����ͳһ���ó�"true"<br>
	 * 2��Ĭ��Ϊ"false"�����������д��<br>
	 * 3��������ֵΪ"true"ʱ����ȷ�����еľ��ȶ����ɲ��������ƣ���Ҫ�ֶ�д��<br>
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

	// Ĭ��ʹ����ʱ���ѯ
	protected boolean useTempTable() {
		return true;
	}

	/**
	 * ��ҳ��ѯ���� �������ѯ��Ҫ������
	 */
	protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException {

		RptParams rpt = new RptParams();

		// ʹ����ʱ���ѯ���ݣ��ֳ����²���ʵ��
		if (useTempTable()) {

			// �������ݵ���ʱ��
			String tempTable = insertDataIntoTempTable(ctx, params);
			if (tempTable == null) {
				return rpt;
			}
			rpt.setObject(FdcRptConstant.RPT_TEMPTABLE, tempTable);

			// ִ��ȡ��SQL������ʱ��ȡ��
			doQuery(ctx, params, rpt);

			// ����ǰ��������ȡ�꣬ɾ����ʱ��
			doSomethingBeforeReturn(ctx, params);
		} else {
			// �����������ʱ���������ش˷������Լ�дsqlʵ��
			// ������useTempTable()������false
			doQuery(ctx, params, rpt);
		}

		return rpt;
	}

	/**
	 * ������ʱ��ǰ�¼�\�������ݵ���ʱ��
	 * 
	 * @param params
	 */
	protected String insertDataIntoTempTable(Context ctx, RptParams params) throws BOSException, EASBizException {
		// ���������
		dealRptParams(params);

		// ������ʱ����\ƴװSQL
		String tmpSql = getInserDataSql(ctx, params);
		if (tmpSql == null) {
			return null;
		} else {
			// ����Sql����
			params.setObject(FdcRptConstant.RPT_INSERDATASQL, tmpSql);
		}
		SqlParams parameters = (SqlParams) params.getObject(FdcRptConstant.RPT_PARAMETERS);

		try {
			// ��ȡ����script ,������?�滻Ϊ��ʵ����
			String tmpSqlTest = FdcSqlUtil.getTestSql(tmpSql, parameters);
			// ����TestSql����
			params.setObject(FdcRptConstant.RPT_INSERDATASQL_TEST, tmpSqlTest);
			// ȡ�ü�����
			String simpleName = FdcClassUtil.getSimpleName(this.getClass());

			// logger.info(
			// "==============================================================="
			// );
			// logger.info(simpleName + ".getInserDataSql():\n " + tmpSqlTest);
			// logger.info(
			// "==============================================================="
			// );

			// ���´����������� begin
			// FileOutputStream out = new
			// FileOutputStream("D:\\tempSql\\tempSql121113.txt");
			// out.write(tmpSqlTest.getBytes());
			// out.close();

			// ���´����������� end
		} catch (Exception e) {
			logger.error(e);
		}

		// �������ݵ���ʱ��
		String tempTable = executeCreateTableAsSelectInto(
				"select * into TEMP_EcRptBaseFacade from (" + tmpSql + ") as tempTable", parameters, ctx)
				.getTempTable();
		params.setObject(FdcRptConstant.RPT_TEMPTABLE, tempTable);

		return tempTable;
	}

	// ִ��ȡ��SQL������ʱ��ȡ��
	// ��д�÷���,���Զ�δ���ʱ��ȡ��
	protected void doQuery(Context ctx, RptParams params, RptParams rpt) throws BOSException, EASBizException {
		RptRowSet rs = executeSqlForData(ctx, params);
		rpt.setObject(FdcRptConstant.RPT_QUERYRESULT, rs);
	}

	/**
	 * ִ��ȡ��SQL������ʱ��ȡ��
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
		// ִ��ȡ��SQL������ʱ��ȡ��
		return executeQuery(sql, parameters, ctx);
	}

	/**
	 * ������ǰ�¼�.�ɼ̳д˷��������ڷ�����ǰ��Ҫ���Ĺ���
	 * 
	 * @param params
	 * @param result
	 *            ���ؽ����
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void doSomethingBeforeReturn(Context ctx, RptParams params) throws BOSException, EASBizException {
		// ������ȡ�꣬ɾ����ʱ��
		String tempTable = (String) params.getObject(FdcRptConstant.RPT_TEMPTABLE);
		dropTempTable(tempTable, ctx);
	}

	protected String getPeriodType(Context ctx, String costCenterId) throws EASBizException, BOSException {
		return null;
	}

	/**
	 * ��ȡ�ڼ䷶Χ
	 */
	protected Map _getPeriodRange(Context ctx, String projectOrgId, String system) throws BOSException, EASBizException {
		return null;
	}

	/**
	 * ��ȡ�ڼ䷶Χ
	 */
	protected Map _getPeriodRange(Context ctx, String projectOrgId, String projectId, String system)
			throws BOSException, EASBizException {
		return null;
	}

	/**
	 * ȡ�ڼ䷶Χ
	 * 
	 * @param type
	 *            0 �����ڼ� 1 ֻ���������ڼ��Ժ� 2 ֻ������ǰ�ڼ���ǰ 3 ֻ���������ڼ��Ժ󣬵�ǰ�ڼ���ǰ
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

		// �������
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
					// �쳣���� 206 PERIOD_ERROR ��ȡ�ڼ䷶Χ����ȷ�����������ڼ䡢��ǰ�ڼ�ͻ���ڼ�������Ƿ���ȷ��
					// throw new RptException(RptException.PERIOD_ERROR);
					continue;
				}

				map.put(new Integer(year), periodRange);
			}

			if (yearRange[0] <= 0 || yearRange[1] <= 0
					|| (startPeriod != null && yearRange[0] < startPeriod.getPeriodYear()) || map.size() <= 0) {
				// �쳣���� 206 PERIOD_ERROR ��ȡ�ڼ䷶Χ����ȷ�����������ڼ䡢��ǰ�ڼ�ͻ���ڼ�������Ƿ���ȷ��
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
	 * ��ȡ��ʱ��Ľṹ������˵�ǹ�����ʱ��ṹ
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

	/** ��ȡȡ��SQL */
	protected String getSelectSql(Context ctx, RptParams params) throws BOSException, EASBizException {
		String tempTable = (String) params.getObject(FdcRptConstant.RPT_TEMPTABLE);
		return "select * from " + tempTable;
	}

	// ��ȡ�ڼ䷶Χ
	protected Map _getPeriodRange(Context ctx, String costCenterId, boolean isProcess) throws BOSException,
			EASBizException {
		return null;
	}

	/**
	 * ��Ŀ����ѯ����ƴ��
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
	 * ƴװ��ѯ����
	 * 
	 * @author forest_liang
	 * @param conditionValues
	 *            ����ֵ
	 * @param sp
	 * @param columnName
	 *            �����ֶ���
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
	 * ƴװ��ѯ����
	 * 
	 * @author forest_liang
	 * @param conditionValues
	 *            ����ֵ
	 * @param sqlParams����list��
	 * @param columnName
	 *            �����ֶ���
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
	 * ���������
	 * 
	 * @param params
	 *            �������
	 */
	protected void dealRptParams(RptParams params) {
		// (1)��ת���������͵ı����������
		// ��Ŀ��֯
		FdcRptUtil.castRptParamsTypeOfArr(params, "projectOrg.ids", String.class);
		// ������Ŀ
		FdcRptUtil.castRptParamsTypeOfArr(params, "project.ids", String.class);
	}

}