package com.kingdee.eas.fdc.aimcost.report.groupDynamic.app;

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

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class GroupDynamicReportFacadeControllerBean extends AbstractGroupDynamicReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.report.groupDynamic.app.GroupDynamicReportFacadeControllerBean");
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
		initColoum(header, "companyName", 150, false);
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
				{"公司", 
					firstMonth,firstMonth,firstMonth,firstMonth,
					secondMonth,secondMonth,secondMonth,secondMonth
				},
				{"公司",
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
		String curGroupSql = getGroupDataSql(year, month);
		RptRowSet curGroupRowSet = executeQuery(curGroupSql, null, ctx);
		Map curGroupMap = rowSetToMap(curGroupRowSet);
		params.setObject("curMap", curGroupMap);
		
		String preGroupSql = getGroupDataSql(preYear, preMonth);
		RptRowSet preGroupRowSet = executeQuery(preGroupSql, null, ctx);
		Map preGroupMap = rowSetToMap(preGroupRowSet);
		params.setObject("preMap", preGroupMap);
		
		return params;
	}
	private Map rowSetToMap(RptRowSet rowSet) {
    	TreeMap map = new TreeMap();
    	
    	while(rowSet.next()) {
    		Map detailMap = new HashMap();
    		String project = rowSet.getString("company");
    		BigDecimal aimCost = rowSet.getBigDecimal("aimCost");
    		BigDecimal dynamicCost = rowSet.getBigDecimal("dynamicCost");
    		BigDecimal diffAmount = rowSet.getBigDecimal("diffAmount");
    		BigDecimal diffRate = rowSet.getBigDecimal("diffRate");
    		
    		detailMap.put("company", project);
    		detailMap.put("aimCost", aimCost);
    		detailMap.put("dynamicCost", dynamicCost);
    		detailMap.put("diffAmount", diffAmount);
    		detailMap.put("diffRate", diffRate);
    		
    		map.put(project, detailMap);
    	}
    	
    	return map;
    }
	private String getGroupDataSql(int year, int month) {
		StringBuilder sql = new StringBuilder("");
		sql.append(" select a.company, sum(case when a.aimCost is null then 0 else a.aimCost end) aimCost,");
		sql.append(" sum(case when a.dynamicCost is null then 0 else a.dynamicCost end) dynamicCost,");
		sql.append(" sum(case when a.diffAmount is null then 0 else a.diffAmount end) diffAmount,");
		sql.append(" case when sum(a.aimCost)=0 then 0 else round(sum(a.diffAmount)/sum(a.aimCost), 3) end diffRate");
		sql.append(" from (");
		sql.append(" select org.fname_l2 company,total.CFCostAccount costAccName, total.CFCostAccountNumber costAccNum,");
		sql.append(" round(total.CFAimCost, 3) aimCost, round(total.CFSumBC, 3) dynamicCost,");
		sql.append(" round(total.CFDiffAmount,3) diffAmount, round(total.CFDiffRate,3) diffRate");
		sql.append(" from CT_AIM_ProjectDCET total");
		sql.append(" left join CT_AIM_ProjectDynamicCost cost on cost.fid=total.FParentID");
		sql.append(" left join t_fdc_curproject curProject on curProject.fid=cost.CFCurProjectID");
		sql.append(" left join t_org_baseUnit org on org.fid=curProject.FFullOrgUnit");
		sql.append(" where 1=1");
		sql.append(" and cost.CFIsLatest='1'");
		sql.append(" and  cost.fyear='"+year+"'");
		sql.append(" and cost.fmonth='"+month+"'");
		sql.append(" order by total.fseq");
		sql.append(" ) a group by  a.company");
		
		
		return sql.toString();
	}
}