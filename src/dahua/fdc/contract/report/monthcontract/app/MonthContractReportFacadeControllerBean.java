package com.kingdee.eas.fdc.contract.report.monthcontract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.access.Simple;
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
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class MonthContractReportFacadeControllerBean extends AbstractMonthContractReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.report.monthcontract.app.MonthContractReportFacadeControllerBean");
    
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
    	
    	initColoum(header, "id", 100, true);
    	initColoum(header, "curProject", 150, false);
    	initColoum(header, "typeName", 75, false);
    	initColoum(header, "contractName", 250, false);
    	initColoum(header, "contractNumber", 200, false);
    	initColoum(header, "partBName", 200, false);
    	initColoum(header, "cotractAmount", 100, false);
    	initColoum(header, "signDate", 100, false);
    	initColoum(header, "contractPropert", 100, false);
    	initColoum(header, "costAccount", 100, false);
    	initColoum(header, "splitAmount", 100, false);
    	initColoum(header, "splitScale", 100, false);
    	initColoum(header, "auditTime", 100, true);
    	
    	header.setLabels(new Object[][] {
    			{"id", "工程项目","合同类型", "合同名称", "合同编码", "乙方", "合同金额","签约日期","合同性质","拆分明细","拆分金额","拆分比例", "合同审批日期"},
    	},true);
    	params.setObject("header", header);
    	
    	return params;
    }
    
    protected RptParams _query(Context ctx, RptParams params, int from, int i)
    		throws BOSException, EASBizException {
    	String sql = getSql(ctx, params);
    	RptRowSet rowSet = executeQuery(sql, null, ctx);
    	params.setObject("rowSet", rowSet);
    	return params;
    }
    
    private String getSql(Context ctx, RptParams params) {
    	Date date = (Date) params.getObject("date");
    	String startDate = getFirstDay(date, "yyyy-MM-dd");
    	String endDate = getLastDay(date, "yyyy-MM-dd");
    	
    	String preFirstDay = getFirstDay(getPreMonthDate(date), "yyyy-MM-dd");
    	String preLastDay = getLastDay(getPreMonthDate(date), "yyyy-MM-dd");
    	
    	CurProjectInfo project = (CurProjectInfo) params.getObject("curProject");
    	ContractTypeInfo contractType = (ContractTypeInfo) params.getObject("contractType");
    	
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" /*dialect*/select contract.fid id, curProject.fname_l2 curProject,contractType.fname_l2 typeName, contract.fname contractName, ");
    	sb.append(" contract.fnumber contractNumber,partB.fname_l2 partBName, round(contract.FAmount, 2) cotractAmount, ");
    	sb.append(" to_char(contract.FSignDate,'yyyy-MM-dd') signDate,");
    	sb.append(" case contract.FContractPropert when 'DIRECT' then '直接合同' when 'THREE_PARTY' then '三方合同' when 'SUPPLY' then '补充合同' end contractPropert, ");
    	sb.append(" costAccount.fname_l2 costAccount, splitEntry.FAmount splitAmount, round(splitEntry.FSplitScale, 2) splitScale, to_char(contract.FAuditTime,'yyyy-MM-dd') auditTime");
    	sb.append(" from t_con_contractbill contract");
    	sb.append(" left join T_FDC_CurProject curProject on curProject.fid=contract.FCurProjectID");
    	sb.append(" left join T_FDC_ContractType contractType on contractType.fid=contract.FContractTypeID");
    	sb.append(" left join T_BD_Supplier partB on partB.fid=contract.FPartBID");
    	sb.append(" left join T_CON_ContractCostSplit split on split.fcontractbillid=contract.fid ");
    	sb.append(" left join T_CON_ContractCostSplitEntry splitEntry on splitEntry.Fparentid=split.fid ");
    	sb.append(" left join T_FDC_CostAccount costAccount on costAccount.fid=splitEntry.Fcostaccountid ");
    	
    	sb.append(" where 1=1");
    	if(contractType != null)
    		sb.append(" and contractType.fid='"+contractType.getId().toString()+"'");
    	if(project != null) 
    		sb.append(" and contract.FCurProjectID='"+project.getId().toString()+"'");
    	sb.append(" and contract.FState='4AUDITTED'");
    	sb.append(" and splitEntry.FIdxApportionID is null");
    	sb.append(" and to_char(contract.FAuditTime, 'yyyy-MM-dd') >='"+startDate+"'");
    	sb.append(" and to_char(contract.FAuditTime, 'yyyy-MM-dd') <='"+endDate+"'");
    	
    	sb.append(" union all");
    	sb.append(getMonthContractGatherSql(ctx, params, "本月合计", startDate, endDate));
    	sb.append(" union all");
    	sb.append(getMonthContractGatherSql(ctx, params, "上月合计", preFirstDay, preLastDay));
    	sb.append(" union all");
    	sb.append(getAllContractGatherSql(ctx, params, "截止到本月合计", endDate));
    	sb.append(" order by id,curProject, typeName");
		return sb.toString();
    }
    
    /**
     * 某月合计
     * @param ctx
     * @param params
     * @param desc
     * @param startDate
     * @param endDate
     * @return
     */
    private String getMonthContractGatherSql(Context ctx, RptParams params, String desc, String startDate, String endDate) {
    	CurProjectInfo project = (CurProjectInfo) params.getObject("curProject");
    	ContractTypeInfo contractType = (ContractTypeInfo) params.getObject("contractType");
    	
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select a.id id, a.curProject curProject, a.typeName typeName, a.contractName contractName,a.contractNumber contractNumber, ");
        sb.append(" a.partBName,a.cotractAmount,a.signDate,a.contractPropert,a.costAccount,b.splitAmount,a.splitScale,a.auditTime");
    	sb.append(" from (");
    	if(desc.equals("本月合计"))
    		sb.append("select 'zzzzzzzzzzzza' id,");//为了排序
    	else if(desc.equals("上月合计"))
    		sb.append("select 'zzzzzzzzzzzzb' id,");
    	sb.append(" cast('"+desc+"' as NVARCHAR2(255)) as curProject,null typeName, null contractName, ");
    	sb.append(" null contractNumber,null partBName, round(sum(contract.FAmount), 2) cotractAmount, ");
    	sb.append(" null signDate,");
    	sb.append(" null contractPropert, ");
    	sb.append(" null costAccount, null splitAmount, null splitScale, null auditTime");
    	sb.append(" from t_con_contractbill contract");
    	sb.append(" left join T_FDC_CurProject curProject on curProject.fid=contract.FCurProjectID");
    	sb.append(" left join T_FDC_ContractType contractType on contractType.fid=contract.FContractTypeID");
    	sb.append(" where 1=1");
    	if(contractType != null)
    		sb.append(" and contractType.fid='"+contractType.getId().toString()+"'");
    	if(project != null) 
    		sb.append(" and contract.FCurProjectID='"+project.getId().toString()+"'");
    	sb.append(" and to_char(contract.FAuditTime, 'yyyy-MM-dd') >='"+startDate+"'");
    	sb.append(" and to_char(contract.FAuditTime, 'yyyy-MM-dd') <='"+endDate+"'");
    	sb.append(" and contract.FState='4AUDITTED'");
    	sb.append(" group by '"+desc+"'");
    	sb.append(" ) a");
    	sb.append(" left join ");
    	sb.append(" (");
    	if(desc.equals("本月合计"))
    		sb.append("select 'zzzzzzzzzzzza' id,");
    	else if(desc.equals("上月合计"))
    		sb.append("select 'zzzzzzzzzzzzb' id,");
    	sb.append("    cast('"+desc+"' as NVARCHAR2(255)) as curProject,null typeName, null contractName, ");
    	sb.append("    null contractNumber,null partBName, null cotractAmount, ");
    	sb.append("    null signDate,");
    	sb.append("    null contractPropert, ");
    	sb.append("    null costAccount, round(sum(splitEntry.famount),2) splitAmount, null splitScale, null auditTime");
    	sb.append("    from t_con_contractbill contract");
    	sb.append("    left join T_FDC_CurProject curProject on curProject.fid=contract.FCurProjectID");
    	sb.append("    left join T_FDC_ContractType contractType on contractType.fid=contract.FContractTypeID");
    	sb.append("    left join T_CON_ContractCostSplit split on split.fcontractbillid=contract.fid ");
    	sb.append("    left join T_CON_ContractCostSplitEntry splitEntry on splitEntry.Fparentid=split.fid ");
    	sb.append("    left join T_FDC_CostAccount costAccount on costAccount.fid=splitEntry.Fcostaccountid ");
    	sb.append("    where 1=1");
    	if(contractType != null)
    		sb.append(" and contractType.fid='"+contractType.getId().toString()+"'");
    	if(project != null) 
    		sb.append(" and contract.FCurProjectID='"+project.getId().toString()+"'");
    	sb.append(" and contract.FState='4AUDITTED'");
    	sb.append(" and splitEntry.FIdxApportionID is null");
    	sb.append(" and to_char(contract.FAuditTime, 'yyyy-MM-dd') >='"+startDate+"'");
    	sb.append(" and to_char(contract.FAuditTime, 'yyyy-MM-dd') <='"+endDate+"'");
    	sb.append(" group by '"+desc+"'");
    	sb.append(" ) b on a.id=b.id");
    	return sb.toString();
    }

    /**
     * 获取到截止到本月底合计
     * @param ctx
     * @param params
     * @param desc
     * @param endDate
     * @return
     */
    private String getAllContractGatherSql(Context ctx, RptParams params, String desc, String endDate) {
    	CurProjectInfo project = (CurProjectInfo) params.getObject("curProject");
    	ContractTypeInfo contractType = (ContractTypeInfo) params.getObject("contractType");
    	
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select a.id id, a.curProject curProject, a.typeName typeName, a.contractName contractName,a.contractNumber contractNumber, ");
        sb.append(" a.partBName,a.cotractAmount,a.signDate,a.contractPropert,a.costAccount,b.splitAmount,a.splitScale,a.auditTime");
    	sb.append(" from (");
    	sb.append(" select 'zzzzzzzzzzzzc' id,cast('"+desc+"' as NVARCHAR2(255)) as curProject,null typeName, null contractName, ");
    	sb.append(" null contractNumber,null partBName, round(sum(contract.FAmount), 2) cotractAmount, ");
    	sb.append(" null signDate,");
    	sb.append(" null contractPropert, ");
    	sb.append(" null costAccount, null splitAmount, null splitScale, null auditTime");
    	sb.append(" from t_con_contractbill contract");
    	sb.append(" left join T_FDC_CurProject curProject on curProject.fid=contract.FCurProjectID");
    	sb.append(" left join T_FDC_ContractType contractType on contractType.fid=contract.FContractTypeID");
    	sb.append(" where 1=1");
    	if(contractType != null)
    		sb.append(" and contractType.fid='"+contractType.getId().toString()+"'");
    	if(project != null) 
    		sb.append(" and contract.FCurProjectID='"+project.getId().toString()+"'");
//    	sb.append(" and to_char(contract.FAuditTime, 'yyyy-MM-dd') >='"+startDate+"'");
    	sb.append(" and to_char(contract.FAuditTime, 'yyyy-MM-dd') <='"+endDate+"'");
    	sb.append(" group by '"+desc+"'");
    	sb.append(" ) a");
    	sb.append(" left join ");
    	sb.append(" (");
    	sb.append("    select 'zzzzzzzzzzzzc' id,cast('"+desc+"' as NVARCHAR2(255)) as curProject,null typeName, null contractName, ");
    	sb.append("    null contractNumber,null partBName, null cotractAmount, ");
    	sb.append("    null signDate,");
    	sb.append("    null contractPropert, ");
    	sb.append("    null costAccount, round(sum(splitEntry.famount),2) splitAmount, null splitScale, null auditTime");
    	sb.append("    from t_con_contractbill contract");
    	sb.append("    left join T_FDC_CurProject curProject on curProject.fid=contract.FCurProjectID");
    	sb.append("    left join T_FDC_ContractType contractType on contractType.fid=contract.FContractTypeID");
    	sb.append("    left join T_CON_ContractCostSplit split on split.fcontractbillid=contract.fid ");
    	sb.append("    left join T_CON_ContractCostSplitEntry splitEntry on splitEntry.Fparentid=split.fid ");
    	sb.append("    left join T_FDC_CostAccount costAccount on costAccount.fid=splitEntry.Fcostaccountid ");
    	sb.append("    where 1=1");
    	if(contractType != null)
    		sb.append(" and contractType.fid='"+contractType.getId().toString()+"'");
    	if(project != null) 
    		sb.append(" and contract.FCurProjectID='"+project.getId().toString()+"'");
    	sb.append(" and splitEntry.FIdxApportionID is null");
//    	sb.append(" and to_char(contract.FAuditTime, 'yyyy-MM-dd') >='"+startDate+"'");
    	sb.append(" and to_char(contract.FAuditTime, 'yyyy-MM-dd') <='"+endDate+"'");
    	sb.append(" group by '"+desc+"'");
    	sb.append(" ) b on a.id=b.id");
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