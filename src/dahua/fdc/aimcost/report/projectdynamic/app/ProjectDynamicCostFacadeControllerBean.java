package com.kingdee.eas.fdc.aimcost.report.projectdynamic.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class ProjectDynamicCostFacadeControllerBean extends AbstractProjectDynamicCostFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.report.projectdynamic.app.ProjectDynamicCostFacadeControllerBean");
    
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
    	initColoum(header, "costAccountName", 150, false);
    	initColoum(header, "costAccountNumber", 150, false);
    	initColoum(header, "faimCost", 150, false);
    	initColoum(header, "fdynamicCost", 150, false);
    	initColoum(header, "fdiffAmount", 150, false);
    	initColoum(header, "fdiffRate", 150, false);
    	initColoum(header, "saimCost", 150, false);
    	initColoum(header, "sdynamicCost", 150, false);
    	initColoum(header, "sdiffAmount", 150, false);
    	initColoum(header, "sdiffRate", 150, false);

    	header.setLabels(new Object[][]{
    			{"成本科目","成本科目编码", 
    			 firstMonth,firstMonth,firstMonth,firstMonth,
    			 secondMonth,secondMonth,secondMonth,secondMonth
    			},
    			{"成本科目","成本科目编码", 
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
		CurProjectInfo project = (CurProjectInfo) params.getObject("curProject");
		
		String preMonthSql = getMonthDataSql(preYear, preMonth, project);
    	RptRowSet preRowSet = executeQuery(preMonthSql, null, ctx);
    	params.setObject("preMonthMap", rowSetToMap(preRowSet));
    	
    	String curMonthSql = getMonthDataSql(year, month, project);
    	RptRowSet curMonthRowSet = executeQuery(curMonthSql, null, ctx);
    	params.setObject("curMonthMap", rowSetToMap(curMonthRowSet));
    	
    	return params;
    }

    private Map rowSetToMap(RptRowSet rowSet) {
    	TreeMap map = new TreeMap();
    	
    	while(rowSet.next()) {
    		Map detailMap = new HashMap();
    		String costAccName = rowSet.getString("costAccName");
    		String costAccNum = rowSet.getString("costAccNum");
    		BigDecimal aimCost = rowSet.getBigDecimal("aimCost");
    		BigDecimal dynamicCost = rowSet.getBigDecimal("dynamicCost");
    		BigDecimal diffAmount = rowSet.getBigDecimal("diffAmount");
    		BigDecimal diffRate = rowSet.getBigDecimal("diffRate");
    		
    		detailMap.put("costAccName", costAccName);
    		detailMap.put("costAccNum", costAccNum);
    		detailMap.put("aimCost", aimCost);
    		detailMap.put("dynamicCost", dynamicCost);
    		detailMap.put("diffAmount", diffAmount);
    		detailMap.put("diffRate", diffRate);
    		
    		map.put(costAccNum, detailMap);
    	}
    	
    	return map;
    }
	private String getMonthDataSql(int year, int month, CurProjectInfo project) {
		StringBuilder sb = new StringBuilder("");
		sb.append(" select total.CFCostAccount costAccName, total.CFCostAccountNumber costAccNum,");
		sb.append(" round(total.CFAimCost, 3) aimCost, round(total.CFSumBC, 3) dynamicCost,");
		sb.append(" round(total.CFDiffAmount,3) diffAmount, round(total.CFDiffRate,3) diffRate");
		sb.append(" from CT_AIM_ProjectDCET total");
		sb.append(" left join CT_AIM_ProjectDynamicCost cost on cost.fid=total.FParentID");
		sb.append(" where 1=1");
		sb.append(" and cost.CFCurProjectID='"+project.getId()+"'");
		sb.append(" and cost.CFIsLatest='1' ");
		sb.append(" and cost.FYear='"+year+"' and cost.FMonth='"+month+"'");
		sb.append(" order by total.fseq");
		
		return sb.toString();
	}
	private String getFirstDay(Date date, String format) {
    	Calendar cal = Calendar.getInstance();
    	cal.clear();
    	cal.setTime(date);
    	int year = cal.get(Calendar.YEAR);
    	int month = cal.get(Calendar.MONTH) + 1;
    	int minDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
    	
    	cal.clear();
    	cal.set(Calendar.YEAR, year);
    	cal.set(Calendar.MONTH, month-1);
    	cal.set(Calendar.DAY_OF_MONTH, minDay);
    	SimpleDateFormat formatter = new SimpleDateFormat(format);
    	String firstDay = formatter.format(cal.getTime());
    	
    	return firstDay;
    }
    
    private Date getPreMonthDate(Date date) {
    	Calendar cal = Calendar.getInstance();
    	cal.clear();
    	cal.setTime(date);
    	cal.add(Calendar.MONTH, -1);
    	cal.set(Calendar.DAY_OF_MONTH, 1);
    	
    	return cal.getTime();
    }
    private String getLastDay(Date date, String format) {
    	Calendar cal = Calendar.getInstance();
    	cal.clear();
    	cal.setTime(date);
    	int year = cal.get(Calendar.YEAR);
    	int month = cal.get(Calendar.MONTH) + 1;
    	int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    	
    	cal.clear();
    	cal.set(Calendar.YEAR, year);
    	cal.set(Calendar.MONTH, month-1);
    	cal.set(Calendar.DAY_OF_MONTH, maxDay);
    	SimpleDateFormat formatter = new SimpleDateFormat(format);
    	String lastDay = formatter.format(cal.getTime());
    	
    	return lastDay;
    }
}