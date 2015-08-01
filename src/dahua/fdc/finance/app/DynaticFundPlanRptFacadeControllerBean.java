package com.kingdee.eas.fdc.finance.app;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FdcRptConstant;
import com.kingdee.eas.fdc.basedata.util.FdcRptUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.SqlParams;

/**
 * ��̬�ʽ�ƻ�Facade
 * 
 * @author ����
 * @email SkyIter@live.com
 * @date 2013-9-29
 * @see
 * @since JDK1.4
 */
public class DynaticFundPlanRptFacadeControllerBean extends AbstractDynaticFundPlanRptFacadeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.finance.app.DynaticFundPlanRptFacadeControllerBean");

	// Ĭ��ʹ����ʱ���ѯ
	protected boolean useTempTable() {
		return false;
	}

	// ִ��ȡ��SQL������ʱ��ȡ��
	// ��д�÷���,���Զ�δ���ʱ��ȡ��
	protected void doQuery(Context ctx, RptParams params, RptParams rpt) throws BOSException, EASBizException {
		// ����
		RptRowSet result_inCome = executeSql_inCome(ctx, params);
		// ֧��
		RptRowSet result_payOut = executeSql_payOut(ctx, params);

		Map map = new LinkedHashMap();
		map.put("result.inCome", result_inCome);
		map.put("result.payOut", result_payOut);

		rpt.setObject(FdcRptConstant.RPT_QUERYRESULT, map);
	}

	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	/**
	 * ����
	 * 
	 * @param params
	 * @return List
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected RptRowSet executeSql_inCome(Context ctx, RptParams params) throws BOSException, EASBizException {

		String sql = getSelectSql_inCome(ctx, params);
		// ȡ��SQL����
		SqlParams sp = (SqlParams) params.getObject(FdcRptConstant.RPT_PARAMETERS);

		// ִ��ȡ��SQL
		return executeQuery(sql, sp, ctx);
	}

	/**
	 * ֧��
	 * 
	 * @param params
	 * @return List
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected RptRowSet executeSql_payOut(Context ctx, RptParams params) throws BOSException, EASBizException {
		String sql = getSelectSql_payOut(ctx, params);
		// ȡ��SQL����
		SqlParams sp = (SqlParams) params.getObject(FdcRptConstant.RPT_PARAMETERS);

		// ִ��ȡ��SQL
		return executeQuery(sql, sp, ctx);
	}

	/**
	 * ������ȡ�ò�ѯSQL_����
	 * 
	 * @author ����
	 * @email SkyIter@live.com
	 * @date 2013-10-2
	 * @since JDK1.4
	 */
	protected String getSelectSql_inCome(Context ctx, RptParams params) throws BOSException, EASBizException {
		String selectSql = null;

		StringBuffer sqlSb = new StringBuffer();

		sqlSb.append("	SELECT fdcSelP.FPlanYear planYear,	\r\n");
		sqlSb.append("	       SUM(ISNULL(tfdcSelPE.FMonth1Amount, 0)) month1Amount,	\r\n");
		sqlSb.append("	       SUM(ISNULL(tfdcSelPE.FMonth2Amount, 0)) month2Amount,	\r\n");
		sqlSb.append("	       SUM(ISNULL(tfdcSelPE.FMonth3Amount, 0)) month3Amount,	\r\n");
		sqlSb.append("	       SUM(ISNULL(tfdcSelPE.FMonth4Amount, 0)) month4Amount,	\r\n");
		sqlSb.append("	       SUM(ISNULL(tfdcSelPE.FMonth5Amount, 0)) month5Amount,	\r\n");
		sqlSb.append("	       SUM(ISNULL(tfdcSelPE.FMonth6Amount, 0)) month6Amount,	\r\n");
		sqlSb.append("	       SUM(ISNULL(tfdcSelPE.FMonth7Amount, 0)) month7Amount,	\r\n");
		sqlSb.append("	       SUM(ISNULL(tfdcSelPE.FMonth8Amount, 0)) month8Amount,	\r\n");
		sqlSb.append("	       SUM(ISNULL(tfdcSelPE.FMonth9Amount, 0)) month9Amount,	\r\n");
		sqlSb.append("	       SUM(ISNULL(tfdcSelPE.FMonth10Amount, 0)) month10Amount,	\r\n");
		sqlSb.append("	       SUM(ISNULL(tfdcSelPE.FMonth11Amount, 0)) month11Amount,	\r\n");
		sqlSb.append("	       SUM(ISNULL(tfdcSelPE.FMonth12Amount, 0)) month12Amount	\r\n");
		sqlSb.append("	  FROM T_SHE_SellPlanEntry tfdcSelPE	\r\n");
		sqlSb.append("	 INNER JOIN T_SHE_SellPlan fdcSelP	\r\n");
		sqlSb.append("	    ON fdcSelP.FID = tfdcSelPE.FSellPlanID	\r\n");
		sqlSb.append("	 INNER JOIN T_SHE_SellProject tfdcSelPro	\r\n");
		sqlSb.append("	    ON tfdcSelPro.FID = fdcSelP.FProjectID	\r\n");
		sqlSb.append("	 INNER JOIN T_FDC_CurProject tfdcCurP	\r\n");
		sqlSb.append("	    ON tfdcCurP.FID = tfdcSelPro.FProjectID	\r\n");
		sqlSb.append("	 WHERE tfdcCurP.FID = ?	\r\n");
		sqlSb.append("	   AND fdcSelP.FPlanYear >= ?	\r\n");
		sqlSb.append("	   AND fdcSelP.FPlanYear <= ?	\r\n");
		// �Ѿ����(��Ӧ��com.kingdee.eas.fdc.sales.BillStateEnum)
		sqlSb.append("	   AND fdcSelP.FBILLSTATE = '4'	\r\n");
		// �ؿ��(��Ӧ��com.kingdee.eas.fdc.sales.PlanDimensionEnum.BACKMONEYAMOUNT)
		sqlSb.append("	   AND tfdcSelPE.FPlanDimension = 4	\r\n");
		sqlSb.append("	 GROUP BY fdcSelP.FPlanYear	\r\n");
		sqlSb.append("	 ORDER BY fdcSelP.FPlanYear	\r\n");

		selectSql = sqlSb.toString();

		// //////////////////////////////////////////////////////////////////

		String projectId = (String) params.getString("curProject.id");
		String startYear = (String) params.getString("startYear");
		String endYear = (String) params.getString("endYear");
		SqlParams parameters = new SqlParams();
		parameters.addString(projectId);
		parameters.addString(startYear);
		parameters.addString(endYear);

		params.setObject(FdcRptConstant.RPT_PARAMETERS, parameters);

		// //////////////////////////////////////////////////////////////////

		// ��Sqlд�뵽�ļ���
		FdcRptUtil.writeSqlToFile(this.getClass(), selectSql, params, true, "inCome");

		return selectSql;
	}

	/**
	 * ������ȡ�ò�ѯSQL_֧��
	 * 
	 * @author ����
	 * @email SkyIter@live.com
	 * @date 2013-10-2
	 * @since JDK1.4
	 */
	protected String getSelectSql_payOut(Context ctx, RptParams params) throws BOSException, EASBizException {
		String selectSql = null;

		StringBuffer sqlSb = new StringBuffer();

		sqlSb.append("	SELECT tfdcProDFPPDE.FPayMonth payMonth, 	\r\n");
		sqlSb.append("	       SUM(ISNULL(tfdcProDFPPDE.FPayAmount, 0)) payAmount	\r\n");
		sqlSb.append("	  FROM T_FNC_ProjectDynaticFundPPDE tfdcProDFPPDE	\r\n");
		sqlSb.append("	 INNER JOIN T_FNC_ProjectDynaticFPPE tfdcProDFPPE	\r\n");
		sqlSb.append("	    ON tfdcProDFPPE.FID = tfdcProDFPPDE.FParent1ID	\r\n");
		sqlSb.append("	 INNER JOIN T_FNC_ProjectDynaticFPP tfdcProDFPP	\r\n");
		sqlSb.append("	    ON tfdcProDFPP.FID = tfdcProDFPPE.FParentID	\r\n");
		sqlSb.append("	 WHERE tfdcProDFPP.FCurProjectID = ?	\r\n");
		// ���հ汾
		sqlSb.append("	   AND tfdcProDFPP.FIsLastVersion = 1	\r\n");
		sqlSb.append("	   AND tfdcProDFPPDE.FPayMonth >= ?	\r\n");
		sqlSb.append("	   AND tfdcProDFPPDE.FPayMonth <= ?	\r\n");
		sqlSb.append("	 GROUP BY tfdcProDFPPDE.FPayMonth	\r\n");
		sqlSb.append("	 ORDER BY tfdcProDFPPDE.FPayMonth	\r\n");

		selectSql = sqlSb.toString();

		// //////////////////////////////////////////////////////////////////

		String projectId = (String) params.getString("curProject.id");
		String startYearMonth = (String) params.getString("startYearMonth");
		String endYearMonth = (String) params.getString("endYearMonth");
		SqlParams parameters = new SqlParams();
		parameters.addString(projectId);
		parameters.addInt(Integer.parseInt(startYearMonth));
		parameters.addInt(Integer.parseInt(endYearMonth));

		params.setObject(FdcRptConstant.RPT_PARAMETERS, parameters);

		// //////////////////////////////////////////////////////////////////

		// ��Sqlд�뵽�ļ���
		FdcRptUtil.writeSqlToFile(this.getClass(), selectSql, params, true, "payOut");

		return selectSql;
	}

}