package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.cp.bc.ExpenseTypeCollection;
import com.kingdee.eas.cp.bc.ExpenseTypeFactory;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.BgControlItemMapCollection;
import com.kingdee.eas.ma.budget.BgControlSchemeCollection;
import com.kingdee.eas.ma.budget.BgControlSchemeFactory;
import com.kingdee.eas.ma.budget.BgCtrlParamCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgItemCollection;
import com.kingdee.eas.ma.budget.BgItemFactory;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.IBgControlFacade;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AccActOnLoadBgGatherFacadeControllerBean extends AbstractAccActOnLoadBgGatherFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.AccActOnLoadBgGatherFacadeControllerBean");
    protected IRowSet _getPrintData(Context ctx, Set idSet)throws BOSException
    {
        return null;
    }
    protected RptParams _init(Context ctx, RptParams params)throws BOSException, EASBizException
	{
	    RptParams pp = new RptParams();
	    return pp;
	}
    private void initColoum(RptTableHeader header,RptTableColumn col,String name,int width,boolean isHide){
    	col= new RptTableColumn(name);
    	col.setWidth(width);
	    col.setHided(isHide);
	    header.addColumn(col);
    }
    protected RptParams _createTempTable(Context ctx, RptParams params)    throws BOSException, EASBizException
	{
	    RptTableHeader header = new RptTableHeader();
	    RptTableColumn col = null;
	    initColoum(header,col,"id",100,true);
	    initColoum(header,col,"name",150,false);
	    initColoum(header,col,"bgAmount",150,false);
	    initColoum(header,col,"requestAmount",150,false);
	    initColoum(header,col,"payAmount",150,false);
	    initColoum(header,col,"onLoadUnPayAmount",150,false);
	    initColoum(header,col,"requestUnOnLoadAmount",150,false);
	    initColoum(header,col,"canRequestAmount",150,false);
	    initColoum(header,col,"useRate",150,false);
	    initColoum(header,col,"remark",150,false);
	    header.setLabels(new Object[][]{ 
	    		{
	    			"id","预算项目","本期可用预算数","本期累计申请金额","本期已付金额","截至本期已占用未付款金额","截至本期已申请未占用金额","当前可申请/占用预算余额","预算使用百分比","备注"
	    		}
	    		,
	    		{
	    			"id","预算项目","(A)","(B=C+D+E)","(C)","(D)","(E)","(F=A-C-D)","(G=C/A)","备注"
	    		}
	    },true);
	    params.setObject("header", header);
	    return params;
	}
    protected BgCtrlResultCollection getBgCtrlResult(Context ctx,RptParams params,String bgItemNumber) throws BOSException, EASBizException{
		PaymentBillInfo pay=new PaymentBillInfo();
		CompanyOrgUnitInfo company=(CompanyOrgUnitInfo) params.getObject("costedCompany");
		CostCenterOrgUnitInfo costedDept=(CostCenterOrgUnitInfo) params.getObject("costedDept");
		CurrencyInfo currency=CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("select * from where name='人民币'").get(0);
		StringBuffer sb = new StringBuffer();
		sb.append(" select bgItem.fid id,bgItem.fnumber number,bgItem.fname_l2 name from T_BG_BgItem bgItem where bgItem.fnumber in(");
    	sb.append(" select SUBSTRING(max(map.fbillItemCombinValue),charindex(':',max(map.fbillItemCombinValue))+1,length(max(map.fbillItemCombinValue))-charindex(':',max(map.fbillItemCombinValue))) from T_BG_BgControlItemMap map left join T_BG_BgControlScheme scheme on scheme.fid=map.FBgCtrlSchemeID");
    	sb.append(" where scheme.fboName='com.kingdee.eas.fi.cas.app.PaymentBill' and scheme.fisValid=1 and scheme.fisSysDefault=0");
    	if(costedDept!=null){
    		sb.append(" and scheme.fcostCenterId='"+costedDept.getId().toString()+"'");
    	}
    	if(bgItemNumber!=null){
    		sb.append(" and map.fbgItemCombinKey in("+bgItemNumber+")");
    	}
    	sb.append(" group by map.fbgItemCombinKey)");
    	RptRowSet rowSet = executeQuery(sb.toString(), null, ctx);
    	while(rowSet.next()){
    		PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
    		entry.setAmount(FDCHelper.ZERO);
    		entry.setLocalAmt(FDCHelper.ZERO);
            entry.setActualAmt(FDCHelper.ZERO);
            entry.setActualLocAmt(FDCHelper.ZERO);
            entry.setCurrency(currency);
        	entry.setOutBgItemId(rowSet.getString("id"));
        	entry.setOutBgItemNumber(rowSet.getString("number"));
        	entry.setOutBgItemName(rowSet.getString("name"));
            entry.setCostCenter(costedDept);
            pay.getEntries().add(entry);
    	}
		pay.setCompany(company);
		pay.setCostCenter(costedDept);
		pay.setBizDate(FDCCommonServerHelper.getServerTimeStamp());
		pay.setCurrency(currency);
		
		IBgControlFacade iBgControlFacade = BgControlFacadeFactory.getLocalInstance(ctx);
		BgCtrlResultCollection coll = iBgControlFacade.getBudget("com.kingdee.eas.fi.cas.app.PaymentBill", new BgCtrlParamCollection(), pay);
		
		return coll;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	String bgItemNumber=null;
    	Map bgItemMap=new HashMap();
    	Map onLoadUnPayAmount=new HashMap();
    	Map requestUnOnLoadAmount=new HashMap();
    	if(params.getObject("bgItem")!=null){
    		bgItemNumber="";
    		Object[] bgItemObj=((Object[])params.getObject("bgItem"));
    		for(int i=0;i<bgItemObj.length;i++){
	    		BgItemInfo bgItemInfo=(BgItemInfo)bgItemObj[i];
	    		Set set=new HashSet(); 
	    		set.add(bgItemInfo.getNumber());
	    		bgItemMap.put(bgItemInfo.getNumber(), set);
            	if(i==0){
            		bgItemNumber=bgItemNumber+"'"+bgItemInfo.getNumber()+"'";
            	}else{
            		bgItemNumber=bgItemNumber+",'"+bgItemInfo.getNumber()+"'";
            	}
            }
    	}
      	params.setObject("bgCtrlResult", getBgCtrlResult(ctx,params,bgItemNumber));
      	
    	CostCenterOrgUnitInfo costedDept = (CostCenterOrgUnitInfo) params.getObject("costedDept");
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select distinct SUBSTRING(t2.fformula,10,charindex(',',t2.fformula)-11) bgItemNumber,SUBSTRING(t1.fformula,10,charindex(',',t1.fformula)-11) comBgItemNumber from T_BG_BgTemplateCtrlSetting t1 left join T_BG_BgTemplateCtrlSetting t2 on t1.fgroupno=t2.fgroupno");
    	sb.append(" where t1.fgroupno!='-1' and t2.fgroupno!='-1' and SUBSTRING(t2.fformula,10,charindex(',',t2.fformula)-11)!=SUBSTRING(t1.fformula,10,charindex(',',t1.fformula)-11)");
    	if(costedDept!=null){
    		sb.append(" and t1.FOrgUnitId='"+costedDept.getId().toString()+"'");
    	}
    	if(bgItemNumber!=null){
    		sb.append(" and SUBSTRING(t2.fformula,10,charindex(',',t2.fformula)-11) in("+bgItemNumber+")");
    	}
    	RptRowSet rowSet = executeQuery(sb.toString(), null,ctx);
    	while(rowSet.next()){
    		if(bgItemMap.containsKey(rowSet.getString("bgItemNumber"))){
    			((Set)bgItemMap.get(rowSet.getString("bgItemNumber"))).add(rowSet.getString("comBgItemNumber"));
    		}
    		if(bgItemNumber.indexOf(rowSet.getString("comBgItemNumber"))<0){
    			bgItemNumber=bgItemNumber+",'"+rowSet.getString("comBgItemNumber")+"'";
    		}
    	}
    	RptRowSet amountRowSet = executeQuery(getAmountSql(ctx,params,bgItemNumber), null,ctx);
    	while(amountRowSet.next()){
    		if(amountRowSet.getString("type").equals("onLoadUnPayAmount")){
    			onLoadUnPayAmount.put(amountRowSet.getString("bgItemNumber"), amountRowSet.getBigDecimal("amount"));
    		}else{
    			requestUnOnLoadAmount.put(amountRowSet.getString("bgItemNumber"), amountRowSet.getBigDecimal("amount"));
    		}
    	}
    	params.setObject("bgItemMap", bgItemMap);
    	params.setObject("onLoadUnPayAmount", onLoadUnPayAmount);
    	params.setObject("requestUnOnLoadAmount", requestUnOnLoadAmount);
		return params;
    }
    protected String getAmountSql(Context ctx,RptParams params,String bgItem){
    	CostCenterOrgUnitInfo costedDept = (CostCenterOrgUnitInfo) params.getObject("costedDept");
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select 'onLoadUnPayAmount' type,sum(t.amount) amount,t.bgItemNumber from(select sum(entry.frequestAmount-isnull(entry.factPayAmount,0)) amount,bgItem.fnumber bgItemNumber from T_CON_PayRequestBill bill left join T_CON_PayRequestBillBgEntry entry on entry.fheadid=bill.fid ");
    	sb.append(" left join T_BG_BgItem bgItem on bgItem.fid=entry.fbgitemid");
    	sb.append(" where bill.fhasClosed=0 and bill.famount is not null and bill.fstate in('3AUDITTING','4AUDITTED')");
    	if(costedDept!=null){
    		sb.append(" and bill.FCostedDeptId='"+costedDept.getId().toString()+"'");
    	}
    	if(bgItem!=null){
    		sb.append(" and bgItem.fnumber in("+bgItem+")");
    	}
    	sb.append(" group by bgItem.fnumber)t group by t.bgItemNumber");
    	
    	sb.append(" union all select 'requestUnOnLoadAmount' type,sum(t.amount) amount,t.bgItemNumber from(select sum(entry.frequestAmount-isnull(entry.factPayAmount,0)) amount,bgItem.fnumber bgItemNumber from T_CON_PayRequestBill bill left join T_CON_PayRequestBillBgEntry entry on entry.fheadid=bill.fid ");
    	sb.append(" left join T_BG_BgItem bgItem on bgItem.fid=entry.fbgitemid");
    	sb.append(" where (bill.fhasClosed=1 or bill.fstate not in('3AUDITTING','4AUDITTED'))");
    	if(costedDept!=null){
    		sb.append(" and bill.FCostedDeptId='"+costedDept.getId().toString()+"'");
    	}
    	if(bgItem!=null){
    		sb.append(" and bgItem.fnumber in("+bgItem+")");
    	}
    	sb.append(" group by bgItem.fnumber)t group by t.bgItemNumber");
    	return sb.toString();
    }
}