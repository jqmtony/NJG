package com.kingdee.eas.fdc.contract.report.monthcontractchange.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;

public class MonContractChangeReportFacadeControllerBean extends AbstractMonContractChangeReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.report.monthcontractchange.app.MonContractChangeReportFacadeControllerBean");
    
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
    	initColoum(header, "changeNumber", 100, false);
    	initColoum(header, "changeContent", 100, false);
    	initColoum(header, "changeAmount", 100, false);
    	initColoum(header, "costAccount", 100, false);
    	initColoum(header, "splitAmount", 100, false);
    	initColoum(header, "splitScale", 100, false);
//    	initColoum(header, "auditTime", 100, false);

    	header.setLabels(new Object[][] {
    			{"id", "工程项目","合同类型", "合同名称", "合同编码", "乙方", "变更单编码","变更内容","变更签证金额",
    			 "拆分明细","拆分金额","拆分比例"},
    	},true);
    	params.setObject("header", header);

    	return params;
    }
    
    protected RptParams _query(Context ctx, RptParams params)
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
    	sb.append(" /*dialect*/select contract.fid id, curProject.fname_l2 curProject,contractType.fname_l2 typeName, contract.fname contractName,contract.fnumber contractNumber,");
    	sb.append(" partB.fname_l2 partBName, change.fnumber changeNumber, changeEntry.FChangeContent changeContent,change.famount changeAmount,");
    	sb.append(" account.fname_l2 costAccount, round(splitEntry.Famount, 2) splitAmount, round(splitEntry.FSplitScale, 2) splitScale");
    	sb.append(" from t_con_contractbill contract");
    	sb.append(" left join T_FDC_CurProject curProject on curProject.fid=contract.FCurProjectID");
    	sb.append(" left join T_FDC_ContractType contractType on contractType.fid=contract.FContractTypeID");
    	sb.append(" left join T_BD_Supplier partB on partB.fid=contract.FPartBID");
    	sb.append(" left join T_CON_ContractChangeBill change on change.fcontractbillid=contract.fid");
    	sb.append(" left join T_CON_ContractChangeEntry changeEntry on changeEntry.Fparentid=change.fid");
    	sb.append(" left join T_CON_ConChangeSplit split on split.FContractChangeID=change.fid");
    	sb.append(" left join T_CON_ConChangeSplitEntry splitEntry on splitEntry.Fparentid=split.fid");
    	sb.append(" left join T_FDC_CostAccount account on account.fid=splitEntry.Fcostaccountid");
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
    	sb.append(getMonChangeGahter(ctx, params, "本月合计", startDate, endDate));
    	sb.append(" union all");
    	sb.append(getMonChangeGahter(ctx, params, "上月合计", preFirstDay, preLastDay));
    	sb.append(" union all");
    	sb.append(getAllMonChangeGahter(ctx, params, "截至本月合计", endDate));
    	sb.append(" order by id,curProject, typeName");
    	return sb.toString();
    }
    
    /**
     * 获取某月的汇总
     * @param ctx
     * @param params
     * @param desc
     * @param startDate
     * @param endDate
     * @return
     */
    private String getMonChangeGahter(Context ctx, RptParams params, String desc, String startDate, String endDate) {
    	CurProjectInfo project = (CurProjectInfo) params.getObject("curProject");
    	ContractTypeInfo contractType = (ContractTypeInfo) params.getObject("contractType");
    	
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select a.id,a.curProject, a.typeName, a.contractName,a.contractNumber,a.partBName,a.changeNumber,a.changeContent,");
    	sb.append(" a.changeAmount,a.costAccount,b.splitAmount,b.splitScale");
    	sb.append(" from ");
    	sb.append(" (");
    	if(desc.equals("本月合计"))
    		sb.append("select 'zzzzzzzzzzzzzzza' id,");
    	else if(desc.equals("上月合计"))
    		sb.append("select 'zzzzzzzzzzzzzzzb' id,");
    	sb.append("    cast('"+desc+"' as NVARCHAR2(255)) as curProject,null typeName,null contractName,null contractNumber,null partBName,null changeNumber,");
    	sb.append("    null changeContent,round(sum(change.foriginalamount),2) changeAmount,null costAccount,null splitAmount,null splitScale");
    	sb.append("    from t_con_contractbill contract");
    	sb.append("    left join T_FDC_CurProject curProject on curProject.fid = contract.FCurProjectID");
    	sb.append("    left join T_FDC_ContractType contractType on contractType.fid = contract.FContractTypeID");
    	sb.append("    left join T_BD_Supplier partB on partB.fid = contract.FPartBID");
    	sb.append("    left join T_CON_ContractChangeBill change on change.fcontractbillid = contract.fid");
    	sb.append("    left join T_CON_ContractChangeEntry changeEntry on changeEntry.Fparentid = change.fid");
    	sb.append("    where 1 = 1");
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
    		sb.append("select 'zzzzzzzzzzzzzzza' id,");
    	else if(desc.equals("上月合计"))
    		sb.append("select 'zzzzzzzzzzzzzzzb' id,");
    	sb.append("     cast('"+desc+"' as NVARCHAR2(255)) as curProject,null typeName,null contractName,null contractNumber,null partBName,null changeNumber,");
    	sb.append("     null changeContent,null changeAmount,null costAccount,round(sum(splitEntry.Famount),2) splitAmount,null splitScale");
    	sb.append("     from t_con_contractbill contract");
    	sb.append("     left join T_FDC_CurProject curProject on curProject.fid = contract.FCurProjectID");
    	sb.append("     left join T_FDC_ContractType contractType on contractType.fid = contract.FContractTypeID");
    	sb.append("     left join T_BD_Supplier partB on partB.fid = contract.FPartBID");
    	sb.append("     left join T_CON_ContractChangeBill change on change.fcontractbillid = contract.fid");
    	sb.append("     left join T_CON_ContractChangeEntry changeEntry on changeEntry.Fparentid = change.fid");
    	sb.append("     left join T_CON_ConChangeSplit split on split.FContractChangeID = change.fid");
    	sb.append("     left join T_CON_ConChangeSplitEntry splitEntry on splitEntry.Fparentid = split.fid");
    	sb.append("     left join T_FDC_CostAccount account on account.fid = splitEntry.Fcostaccountid");
    	sb.append("     where 1=1");
    	if(contractType != null)
    		sb.append(" and contractType.fid='"+contractType.getId().toString()+"'");
    	if(project != null) 
    		sb.append(" and contract.FCurProjectID='"+project.getId().toString()+"'");
    	sb.append(" and to_char(contract.FAuditTime, 'yyyy-MM-dd') >='"+startDate+"'");
    	sb.append(" and splitEntry.FIdxApportionID is null");
    	sb.append(" and to_char(contract.FAuditTime, 'yyyy-MM-dd') <='"+endDate+"'");
    	sb.append(" and contract.FState='4AUDITTED'");
    	sb.append(" group by '"+desc+"'");
    	sb.append(" ) b on b.id=a.id");
    	return sb.toString();
    }
    
    /**
     * 获取截至本月的汇总
     * @param ctx
     * @param params
     * @param desc
     * @param startDate
     * @param endDate
     * @return
     */
    private String getAllMonChangeGahter(Context ctx, RptParams params, String desc, String endDate) {
    	CurProjectInfo project = (CurProjectInfo) params.getObject("curProject");
    	ContractTypeInfo contractType = (ContractTypeInfo) params.getObject("contractType");
    	
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select a.id,a.curProject, a.typeName, a.contractName,a.contractNumber,a.partBName,a.changeNumber,a.changeContent,");
    	sb.append(" a.changeAmount,a.costAccount,b.splitAmount,b.splitScale");
    	sb.append(" from ");
    	sb.append(" (");
    	sb.append("    select 'zzzzzzzzzzzzzzzc' id,");
    	sb.append("    cast('"+desc+"' as NVARCHAR2(255)) as curProject,null typeName,null contractName,null contractNumber,null partBName,null changeNumber,");
    	sb.append("    null changeContent,round(sum(change.foriginalamount),2) changeAmount,null costAccount,null splitAmount,null splitScale");
    	sb.append("    from t_con_contractbill contract");
    	sb.append("    left join T_FDC_CurProject curProject on curProject.fid = contract.FCurProjectID");
    	sb.append("    left join T_FDC_ContractType contractType on contractType.fid = contract.FContractTypeID");
    	sb.append("    left join T_BD_Supplier partB on partB.fid = contract.FPartBID");
    	sb.append("    left join T_CON_ContractChangeBill change on change.fcontractbillid = contract.fid");
    	sb.append("    left join T_CON_ContractChangeEntry changeEntry on changeEntry.Fparentid = change.fid");
    	sb.append("    where 1 = 1");
    	if(contractType != null)
    		sb.append(" and contractType.fid='"+contractType.getId().toString()+"'");
    	if(project != null) 
    		sb.append(" and contract.FCurProjectID='"+project.getId().toString()+"'");
//    	sb.append(" and to_char(contract.FAuditTime, 'yyyy-MM-dd') >='"+startDate+"'");
    	sb.append(" and to_char(contract.FAuditTime, 'yyyy-MM-dd') <='"+endDate+"'");
    	sb.append(" and contract.FState='4AUDITTED'");
    	sb.append(" group by '"+desc+"'");
    	sb.append(" ) a");
    	sb.append(" left join ");
    	sb.append(" (");
    	sb.append("     select 'zzzzzzzzzzzzzzzc' id,");
    	sb.append("     cast('"+desc+"' as NVARCHAR2(255)) as curProject,null typeName,null contractName,null contractNumber,null partBName,null changeNumber,");
    	sb.append("     null changeContent,null changeAmount,null costAccount,round(sum(splitEntry.Famount),2) splitAmount,null splitScale");
    	sb.append("     from t_con_contractbill contract");
    	sb.append("     left join T_FDC_CurProject curProject on curProject.fid = contract.FCurProjectID");
    	sb.append("     left join T_FDC_ContractType contractType on contractType.fid = contract.FContractTypeID");
    	sb.append("     left join T_BD_Supplier partB on partB.fid = contract.FPartBID");
    	sb.append("     left join T_CON_ContractChangeBill change on change.fcontractbillid = contract.fid");
    	sb.append("     left join T_CON_ContractChangeEntry changeEntry on changeEntry.Fparentid = change.fid");
    	sb.append("     left join T_CON_ConChangeSplit split on split.FContractChangeID = change.fid");
    	sb.append("     left join T_CON_ConChangeSplitEntry splitEntry on splitEntry.Fparentid = split.fid");
    	sb.append("     left join T_FDC_CostAccount account on account.fid = splitEntry.Fcostaccountid");
    	sb.append("     where 1=1");
    	if(contractType != null)
    		sb.append(" and contractType.fid='"+contractType.getId().toString()+"'");
    	if(project != null) 
    		sb.append(" and contract.FCurProjectID='"+project.getId().toString()+"'");
//    	sb.append(" and to_char(contract.FAuditTime, 'yyyy-MM-dd') >='"+startDate+"'");
    	sb.append(" and splitEntry.FIdxApportionID is null");
    	sb.append(" and to_char(contract.FAuditTime, 'yyyy-MM-dd') <='"+endDate+"'");
    	sb.append(" and contract.FState='4AUDITTED'");
    	sb.append(" group by '"+desc+"'");
    	sb.append(" ) b on b.id=a.id");
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