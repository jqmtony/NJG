package com.kingdee.eas.fdc.aimcost.report.companydynamic.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class CompanyDynamicReportFacadeControllerBean extends AbstractCompanyDynamicReportFacadeControllerBean
{
	private static Logger logger =
		Logger.getLogger("com.kingdee.eas.fdc.aimcost.report.companydynamic.app.CompanyDynamicReportFacadeControllerBean");
	protected RptParams _init(Context ctx, RptParams params)
	throws BOSException, EASBizException {
		RptParams pp = new RptParams();
		return pp;
	}

	private void initColoum(RptTableHeader header,String name,int width,boolean isHide){
		RptTableColumn col= new RptTableColumn(name);
		col.setWidth(width);
		col.setHided(isHide);
		header.addColumn(col);
	}
	protected RptParams _createTempTable(Context ctx, RptParams params)
	throws BOSException, EASBizException {
		RptTableHeader header = new RptTableHeader();

		Integer year = (Integer) params.getObject("year");
		Integer month = (Integer) params.getObject("month");
		String secondMonth = year+"年"+month+"月";
		month = month-1;
		if(month == 0) {
			year = year-1;
			month = 12;
		}
		String firstMonth = year+"年"+month+"月";
		initColoum(header, "projectName", 150, false);
//		initColoum(header, "costAccountName", 150, false);
//		initColoum(header, "costAccountNumber", 150, false);
		initColoum(header, "faimCost", 150, false);
		initColoum(header, "fdynamicCost", 150, false);
		initColoum(header, "fdiffAmount", 150, false);
		initColoum(header, "fdiffRate", 150, false);
		initColoum(header, "saimCost", 150, false);
		initColoum(header, "sdynamicCost", 150, false);
		initColoum(header, "sdiffAmount", 150, false);
		initColoum(header, "sdiffRate", 150, false);

		header.setLabels(new Object[][]{
				{"项目", 
					firstMonth,firstMonth,firstMonth,firstMonth,
					secondMonth,secondMonth,secondMonth,secondMonth
				},
				{"项目",
					"目标成本","动态成本","成本差异","差异率",
					"目标成本","动态成本","成本差异","差异率"}
		}, true);
		params.setObject("header", header);

		return params;
	}
	
	protected RptParams _query(Context ctx, RptParams params)
			throws BOSException, EASBizException {
		Integer year = (Integer) params.getObject("year");
		Integer month = (Integer) params.getObject("month");
		
		int preYear = year;
		int preMonth = month-1;
		if(preMonth == 0) {
			preYear = preYear-1;
			preMonth = 12;
		}
		CompanyOrgUnitInfo company = (CompanyOrgUnitInfo) params.getObject("company");
		String preCompanySql = getCompanyDataSql(preYear, preMonth, company);
		RptRowSet preRowSet = executeQuery(preCompanySql, null, ctx);
		Map preMap = rowSetToMap(preRowSet);
		params.setObject("preMap", preMap);
		
		String curCompanySql = getCompanyDataSql(year, month, company);
		RptRowSet curRowSet = executeQuery(curCompanySql, null, ctx);
		Map curMap = rowSetToMap(curRowSet);
		params.setObject("curMap", curMap);
		
		return params;
	}
	 private Map rowSetToMap(RptRowSet rowSet) {
	    	TreeMap map = new TreeMap();
	    	
	    	while(rowSet.next()) {
	    		Map detailMap = new HashMap();
	    		String project = rowSet.getString("project");
	    		BigDecimal aimCost = rowSet.getBigDecimal("aimCost");
	    		BigDecimal dynamicCost = rowSet.getBigDecimal("dynamicCost");
	    		BigDecimal diffAmount = rowSet.getBigDecimal("diffAmount");
	    		BigDecimal diffRate = rowSet.getBigDecimal("diffRate");
	    		
	    		detailMap.put("project", project);
	    		detailMap.put("aimCost", aimCost);
	    		detailMap.put("dynamicCost", dynamicCost);
	    		detailMap.put("diffAmount", diffAmount);
	    		detailMap.put("diffRate", diffRate);
	    		
	    		map.put(project, detailMap);
	    	}
	    	
	    	return map;
	    }
	private String getCompanyDataSql(int year, int month, CompanyOrgUnitInfo company) {
		StringBuilder sql = new StringBuilder("");
		sql.append(" select a.project, sum(case when a.aimCost is null then 0 else a.aimCost end) aimCost,");
		sql.append(" sum(case when a.dynamicCost is null then 0 else a.dynamicCost end) dynamicCost,");
		sql.append(" sum(case when a.diffAmount is null then 0 else a.diffAmount end) diffAmount,");
		sql.append(" case when sum(a.aimCost)=0 then 0 else round(sum(a.diffAmount)/sum(a.aimCost), 3) end diffRate");
		sql.append(" from (");
		sql.append(" select curProject.fname_l2 project,total.CFCostAccount costAccName, total.CFCostAccountNumber costAccNum,");
		sql.append(" round(total.CFAimCost, 3) aimCost, round(total.CFSumBC, 3) dynamicCost,");
		sql.append(" round(total.CFDiffAmount,3) diffAmount, round(total.CFDiffRate,3) diffRate");
		sql.append(" from CT_AIM_ProjectDCET total");
		sql.append(" left join CT_AIM_ProjectDynamicCost cost on cost.fid=total.FParentID");
		sql.append(" left join t_fdc_curproject curProject on curProject.fid=cost.CFCurProjectID");
		sql.append(" where 1=1");
		sql.append(" and cost.CFIsLatest='1'");
		sql.append(" and  cost.fyear='"+year+"'");
		sql.append(" and cost.fmonth='"+month+"'");
		sql.append(" and curProject.FFullOrgUnit='"+company.getId()+"'");
		sql.append(" order by total.fseq");
		sql.append(" ) a group by  a.project");
		
		return sql.toString();
	}
}