package com.kingdee.eas.fdc.material.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.master.material.MaterialCollection;
import com.kingdee.eas.basedata.master.material.MaterialFactory;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.material.MaterialConfirmBillCollection;
import com.kingdee.eas.fdc.material.MaterialConfirmBillEntryCollection;
import com.kingdee.eas.fdc.material.MaterialConfirmBillEntryFactory;
import com.kingdee.eas.fdc.material.MaterialConfirmBillEntryInfo;
import com.kingdee.eas.fdc.material.MaterialConfirmBillFactory;
import com.kingdee.eas.fdc.material.MaterialConfirmBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class MaterialRptFacadeControllerBean extends AbstractMaterialRptFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.material.app.MaterialRptFacadeControllerBean");
    
    /**
     * 获取材料合同台帐
     */
    protected RetValue _getMaterialContractValues(Context ctx, RetValue param)throws BOSException, EASBizException
    {
    	Set curProjectIds = (HashSet)param.get("curProjectIds");
    	Set supplierIds = (HashSet)param.get("supplierIds");
    	
    	RetValue retValue = new RetValue();
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("mainContractBill.curProject",curProjectIds,CompareType.INCLUDE));
    	filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
    	if(supplierIds!=null&&supplierIds.size()>0){
    		filter.getFilterItems().add(new FilterItemInfo("materialContractBill.partB",supplierIds,CompareType.INCLUDE));
    	}
    	
    	view.getSelector().add("*");
    	
    	view.getSelector().add("payRequestBill.id");
    	view.getSelector().add("mainContractBill.number");
    	view.getSelector().add("mainContractBill.name");
    	view.getSelector().add("mainContractBill.partB.number");
    	view.getSelector().add("mainContractBill.partB.name");
    	
    	view.getSelector().add("materialContractBill.number");
    	view.getSelector().add("materialContractBill.name");
    	view.getSelector().add("materialContractBill.partB.number");
    	view.getSelector().add("materialContractBill.partB.name");
    	
    	view.getSelector().add("entry.*");
    	view.getSelector().add("entry.material.number");
    	view.getSelector().add("entry.material.name");
    	view.getSelector().add("entry.material.pricePrecision");
    	view.getSelector().add("entry.material.baseUnit.name");
    	view.getSelector().add("entry.material.baseUnit.number");
    	view.getSelector().add("entry.material.baseUnit.qtyPrecision");
    	
    	MaterialConfirmBillCollection materialConfirmBills = MaterialConfirmBillFactory.getLocalInstance(ctx).getMaterialConfirmBillCollection(view);
    	
    	Set confirmBillIds = new HashSet();
    	Map materialConfirmBillMap = new HashMap();
    	
    	for(Iterator it = materialConfirmBills.iterator();it.hasNext();){
    		MaterialConfirmBillInfo billInfo = (MaterialConfirmBillInfo)it.next();
    		billInfo.setPaidAmt(FDCHelper.ZERO);
    		confirmBillIds.add(billInfo.getId().toString());
    		materialConfirmBillMap.put(billInfo.getId().toString(), billInfo);
    		
    		billInfo.getEntrys().clear();
    	}
    	if(confirmBillIds.size()>0)
    	{
    		view.getSelector().clear();
    		view.getSelector().add("*");
    		view.getSelector().add("parent.id");
        	view.getSelector().add("material.number");
        	view.getSelector().add("material.model");
        	view.getSelector().add("material.name");
        	view.getSelector().add("material.pricePrecision");
        	view.getSelector().add("material.baseUnit.name");
        	view.getSelector().add("material.baseUnit.number");
        	view.getSelector().add("material.baseUnit.qtyPrecision");
        	view.getFilter().getFilterItems().clear();
        	view.getFilter().getFilterItems().add(new FilterItemInfo("parent",confirmBillIds,CompareType.INCLUDE));
    		MaterialConfirmBillEntryCollection confirmBills_entrys = MaterialConfirmBillEntryFactory.getLocalInstance(ctx).getMaterialConfirmBillEntryCollection(view);
    		
    		for(Iterator it = confirmBills_entrys.iterator();it.hasNext();){
        		MaterialConfirmBillEntryInfo billInfo_Entry = (MaterialConfirmBillEntryInfo)it.next();
        		if(materialConfirmBillMap.containsKey(billInfo_Entry.getParent().getId().toString())){
        			MaterialConfirmBillInfo billInfo = (MaterialConfirmBillInfo)materialConfirmBillMap.get(billInfo_Entry.getParent().getId().toString());
        			billInfo.getEntrys().add(billInfo_Entry);
        		}
        	}
    		
    		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    		builder.setPrepareStatementSql("select confirmEntry.fconfirmBillid as confirmBillId,sum(paybill.flocalamount) as paidAmt from T_CON_PayReqConfirmEntry confirmEntry inner join t_cas_paymentbill paybill on confirmEntry.fparentid=paybill.ffdcpayreqid where paybill.fbillstatus=15 and ");
    		builder.appendParam("confirmEntry.fconfirmBillid", confirmBillIds.toArray());
    		builder.appendSql("group by confirmEntry.fconfirmBillid");
    		
    		try{
    			IRowSet rowSet = builder.executeQuery();
    			while(rowSet.next()){
    				String key = rowSet.getString("confirmBillId");
    				BigDecimal paidAmt = rowSet.getBigDecimal("paidAmt");
    				if(materialConfirmBillMap.containsKey(key)){
    					MaterialConfirmBillInfo billInfo = (MaterialConfirmBillInfo)materialConfirmBillMap.get(key);
    					billInfo.setPaidAmt(paidAmt);
    				}
    			}
    		}catch(Exception e){
    			e.printStackTrace();
    		}        	
    	}
    	
    	
    	
    	
    	for(Iterator it = materialConfirmBills.iterator();it.hasNext();){
    		MaterialConfirmBillInfo billInfo = (MaterialConfirmBillInfo)it.next();
    		BigDecimal paidAmt = FDCHelper.toBigDecimal(billInfo.getPaidAmt());
    		
    		/***
    		 * 这里需要将已付金额分配到各个材料上
    		 */
    		
    		if(paidAmt.compareTo(FDCHelper.ZERO)>0){
    			for(int i=0;i<billInfo.getEntrys().size();i++){
    				MaterialConfirmBillEntryInfo entryInfo = billInfo.getEntrys().get(i);
    				if(entryInfo.getAmount().compareTo(paidAmt)<=0){
    					entryInfo.put("paidAmt", entryInfo.getAmount());
    					paidAmt = paidAmt.subtract(entryInfo.getAmount());
    				}else{
    					entryInfo.put("paidAmt", paidAmt);
    					break;
    				}
    			}
    		}
    	}
    	
    	
    	retValue.put("MaterialConfirmBillCollection", materialConfirmBills);
        return retValue;
    }
    
    /**
     * 项目甲供材料台账
     */
	protected RetValue _getPartAMaterialValues(Context ctx, RetValue param)
			throws BOSException, EASBizException {
		
		//取所有相关的物料信息	
		
		RetValue retValue = new RetValue();
		Set curPrjSet = (HashSet)param.get("prjs");
		if(curPrjSet.size() == 0 ){
			return retValue;
		}	
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select a.fmaterialid,m.fnumber, m.fname_l2,m.fmodel,m.unitName,a.mainContractBillId,a.fcurProjectId,a.contractName,a.auditQty,a.fquantity,a.auditQty-a.fquantity notenterplan"+
						" from (select mytable.fmaterialid,mytable.mainContractBillId,mytable.fcurProjectId,mytable.contractName,sum(mytable.fquantity) fquantity,sum(mytable.auditQty) auditQty  from ("+
						" select fmaterialid,mainContractBillId,fcurProjectId,contractName,entry.fquantity fquantity,0 auditQty from t_pam_materialconfirmbillentry entry "+ 
                        " right join"+
                        " (select confirmbill.fid fid ,confirmbill.fmaincontractbillid mainContractbillId,contractbill.fcurprojectid fcurProjectId,"+
                        " contractbill.fname contractName "+
                        " from t_pam_materialconfirmbill  confirmbill " +
                        " left join t_con_contractbill contractbill " +
                        " on confirmbill.fmaincontractbillid = contractbill.fid where");
                        builder.appendParam("contractbill.fcurprojectid", curPrjSet.toArray());		  
                		builder.appendSql(" and ");
                		builder.appendParam("confirmbill.fstate",FDCBillStateEnum.AUDITTED_VALUE);
      builder.appendSql(" ) newbill  on entry.fparentid = newbill.fid    union all  "+
    		 		   " select fmaterialid,mainContractBillId,fCurProjectId,contractName,0 fquantity,entry.fAUDITQUANTITY auditQty from t_pam_materialenterplanentry entry       right join (select enterplan.fid fid,enterplan.fcontractbillid mainContractBillId ,enterplan.fcurprojectid,  contractbill.fname contractName "+ 
    		 		   " from  t_pam_materialEnterPlanbill enterplan "+ 
                       " left join t_con_contractbill contractbill " + 
                       " on enterplan.fcontractbillid = contractbill.fid where ");
     builder.appendParam(" enterplan.FCurProjectID", curPrjSet.toArray());
     builder.appendSql(" and ");	
     builder.appendParam(" enterplan.fstate",FDCBillStateEnum.AUDITTED_VALUE);
     builder.appendSql(" ) newbill on  entry.fparentid = newbill.fid ) mytable "+ 
    		 		  " group by mytable.fmaterialid,mytable.fcurProjectId,mytable.mainContractBillId,mytable.contractName) a "+
    		 		  " left join (select m1.fid fid,m1.fnumber fnumber,m1.fname_l2 fname_l2,m1.fmodel fmodel,u.fname_l2 unitName  from t_bd_material m1  left join T_BD_MeasureUnit u  on m1.fbaseunit = u.fid ) m "+
    		 		  " on a.fmaterialid = m.fid");
    if(param.get("custParam") != null){
	   Map custParm = (Map) param.get("custParam");
	   builder.appendSql(builderSqlByCustParam(custParm));
   }

     
     builder.appendSql(" order by fnumber");
   /* Added By Owen_wen 2010-12-08 目的是取进场计划与材料确认单之间的差值，以工程项目为纬度，每个物料一条记录。
    
    为了便于理解上述复杂的SQL语句，将产生的SQL语句示例如下：
    
select a.fmaterialid,m.fnumber, m.fname_l2,m.fmodel,m.unitName,a.mainContractBillId,a.fcurProjectId,a.contractName,a.auditQty,a.fquantity,a.auditQty-a.fquantity notenterplan 
from (
			select mytable.fmaterialid,mytable.mainContractBillId,mytable.fcurProjectId,mytable.contractName,sum(mytable.fquantity) fquantity,sum(mytable.auditQty) auditQty  
			from  (
				select fmaterialid,mainContractBillId,fcurProjectId,contractName,entry.fquantity fquantity,0 auditQty 
				from t_pam_materialconfirmbillentry entry  
				right join (
						select confirmbill.fid fid ,confirmbill.fmaincontractbillid mainContractbillId,contractbill.fcurprojectid fcurProjectId, contractbill.fname contractName  
						from t_pam_materialconfirmbill  confirmbill  
						left join t_con_contractbill contractbill  on confirmbill.fmaincontractbillid = contractbill.fid where contractbill.fcurprojectid in ('/T9eM8pAQwaunIMX1kmykPnl6Ss=') and  confirmbill.fstate= '4AUDITTED' 
					) newbill  on entry.fparentid = newbill.fid    
				union all   
				select fmaterialid,mainContractBillId,fCurProjectId,contractName,0 fquantity,entry.fAUDITQUANTITY auditQty 
				from t_pam_materialenterplanentry entry       
				right join (
					select enterplan.fid fid,enterplan.fcontractbillid mainContractBillId ,enterplan.fcurprojectid,  contractbill.fname contractName  
					from  t_pam_materialEnterPlanbill enterplan  
					left join t_con_contractbill contractbill  on enterplan.fcontractbillid = contractbill.fid where   enterplan.FCurProjectID in ('/T9eM8pAQwaunIMX1kmykPnl6Ss=') and   enterplan.fstate= '4AUDITTED' 
				) newbill on  entry.fparentid = newbill.fid 
			) mytable  group by mytable.fmaterialid,mytable.fcurProjectId,mytable.mainContractBillId,mytable.contractName
) a  
left join (
				select m1.fid fid,m1.fnumber fnumber,m1.fname_l2 fname_l2,m1.fmodel fmodel,u.fname_l2 unitName  
				from t_bd_material m1  
				left join T_BD_MeasureUnit u  on m1.fbaseunit = u.fid 
	) m  on a.fmaterialid = m.fid
    */
  
        IRowSet  rs  = builder.executeQuery();
        retValue.put("rs", rs);
		return retValue;
	}
	
	
	private String builderSqlByCustParam(Map param){
		StringBuffer sql = new StringBuffer();
		boolean isFirstCondition = true;		
			
		Map custParm = param;
		if (custParm.get("mainContractId") != null && custParm.get("mainContractId") != "") {
			if (isFirstCondition) {
				sql.append(" where ");
				isFirstCondition = false;
			} else {
				sql.append(" and ");
			}
			sql.append(" a.mainContractBillId = '");
			sql.append(custParm.get("mainContractId"));
			sql.append("'");

		}
			 
			 if (((Boolean) custParm.get("isLike")).booleanValue()) {
			if (custParm.get("materialName") != null && custParm.get("materialName") != "") {
				if (isFirstCondition) {
					sql.append(" where ");
					isFirstCondition = false;
				} else {
					sql.append(" and ");
				}
				sql.append("m.fname_l2 like '%");
				sql.append(custParm.get("materialName"));
				sql.append("%'");
			}
			if (custParm.get("materialNumber") != null && custParm.get("materialNumber") != "") {
				if (isFirstCondition) {
					sql.append(" where ");
					isFirstCondition = false;
				} else {
					sql.append(" and ");
				}
				sql.append("m.fnumber like '%");
				sql.append(custParm.get("materialNumber"));
				sql.append("%'");
			}
			if (custParm.get("model") != null && custParm.get("model") != "") {
				if (isFirstCondition) {
					sql.append(" where ");
					isFirstCondition = false;
				} else {
					sql.append(" and ");
				}
				sql.append("m.fmodel like '%");
				sql.append(custParm.get("model"));
				sql.append("%'");
			}
			if (custParm.get("unitName") != null && custParm.get("unitName") != "") {
				if (isFirstCondition) {
					sql.append(" where ");
					isFirstCondition = false;
				} else {
					sql.append(" and ");
				}
				sql.append("m.unitName like '%");
				sql.append(custParm.get("unitName"));
				sql.append("%'");
			}
		} else {
			if (custParm.get("materialName") != null && custParm.get("materialName") != "") {
				if (isFirstCondition) {
					sql.append(" where ");
					isFirstCondition = false;
				} else {
					sql.append(" and ");
				}
				sql.append("m.fname_l2 ='");
				sql.append(custParm.get("materialName"));
				sql.append("'");
			}
			if (custParm.get("materialNumber") != null && custParm.get("materialNumber") != "") {
				if (isFirstCondition) {
					sql.append(" where ");
					isFirstCondition = false;
				} else {
					sql.append(" and ");
				}
				sql.append("m.fnumber ='");
				sql.append(custParm.get("materialNumber"));
				sql.append("'");
			}
			if (custParm.get("model") != null && custParm.get("model") != "") {
				if (isFirstCondition) {
					sql.append(" where ");
					isFirstCondition = false;
				} else {
					sql.append(" and ");
				}
				sql.append("m.fmodel ='");
				sql.append(custParm.get("model"));
				sql.append("'");
			}
			if (custParm.get("unitName") != null && custParm.get("unitName") != "") {
				if (isFirstCondition) {
					sql.append(" where ");
					isFirstCondition = false;
				} else {
					sql.append(" and ");
				}
				sql.append("m.unitName ='");
				sql.append(custParm.get("unitName"));
				sql.append("'");
			}
		}

		if (custParm.get("planNumber") != null) {
			if (isFirstCondition) {
				sql.append(" where ");
				isFirstCondition = false;
			} else {
				sql.append(" and ");
			}
			sql.append("a.auditQty ");
			sql.append(custParm.get("planNumberCondition"));
			sql.append(custParm.get("planNumber"));
		}
		if (custParm.get("enterNumber") != null) {
			if (isFirstCondition) {
				sql.append(" where ");
				isFirstCondition = false;
			} else {
				sql.append(" and ");
			}
			sql.append("a.fquantity ");
			sql.append(custParm.get("enterNumberCondition"));
			sql.append(custParm.get("enterNumber"));
		}
		if (custParm.get("notEnterNumber") != null) {
			if (isFirstCondition) {
				sql.append(" where ");
				isFirstCondition = false;
			} else {
				sql.append(" and ");
			}
			sql.append("a.auditQty-a.fquantity");
			sql.append(custParm.get("notEnterNumberCondition"));
			sql.append(custParm.get("notEnterNumber"));
		}

		return sql.toString();			 
	}
	
	/**
	 * 项目甲供材料月报表
	 */
	protected RetValue _getMaterialContractMonthValues(Context ctx,
			RetValue param) throws BOSException, EASBizException {
		Date selectDate = (Date)param.get("selectDate");
		Date monthStartDate = FDCDateHelper.getFirstDayOfMonth(selectDate);
		Date monthEndDate   = FDCDateHelper.getLastDayOfMonth(selectDate);
		Date YearStartDate  = FDCDateHelper.getFirstYearDate(selectDate);
		Date YearEndDate  = FDCDateHelper.getLastYearDate(selectDate);
		Set curProjectIds = (HashSet)param.get("curProjectIds");
    	Set supplierIds = (HashSet)param.get("supplierIds");
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select monthTable.fmaincontractbillid,monthTable.fmaterialid \n");
    	builder.appendSql(", case isnull(monthTable.quantity,0) when 0 then 0 else monthTable.amount/monthTable.quantity end as price \n");
    	builder.appendSql(", monthTable.quantity,monthTable.amount\n");
    	builder.appendSql(", lmTable.LMquantity, lmTable.lmAmount\n");
    	builder.appendSql(", sumTable.sumquantity, sumTable.sumAmount\n");
    	builder.appendSql("from (\n");
    	builder.appendSql("	select mc.fmaincontractbillid,mce.fmaterialid,sum(mce.fquantity) as quantity,sum(mce.famount) as amount " +
    			"from t_pam_materialconfirmbillentry mce " +
    			"inner join t_pam_materialconfirmbill mc on mce.fparentid=mc.fid\n");
    	builder.appendSql("	inner join t_con_contractbill mainContract on mc.fmaincontractbillid=mainContract.Fid \n");
    	builder.appendSql("	where mc.fsupplyDate<=?\n");
    	builder.addParam(new Timestamp(FDCDateHelper.getSQLEnd(monthEndDate).getTime()));
    	builder.appendSql("	and mc.fsupplyDate>=?\n");
    	builder.addParam(new Timestamp(FDCDateHelper.getSQLBegin(monthStartDate).getTime()));
    	builder.appendSql(" and \n");
    	builder.appendParam("mainContract.Fcurprojectid", curProjectIds.toArray());
    	builder.appendSql(" and \n");
    	builder.appendParam("mainContract.fstate", FDCBillStateEnum.AUDITTED_VALUE);
    	if(supplierIds!=null&&supplierIds.size()>0){
    		builder.appendSql(" and \n");
        	builder.appendParam("mainContract.FPartBid", supplierIds.toArray());
    	}
    	builder.appendSql("	group by mc.fmaincontractbillid,mce.fmaterialid \n");
    	builder.appendSql(") monthTable left join (\n"); //monthTable 本月发生
    	builder.appendSql("	select mc.fmaincontractbillid,mce.fmaterialid, sum(mce.fquantity) as LMquantity,sum(mce.famount) as lmAmount " +
    			"from t_pam_materialconfirmbillentry mce " +
    			"inner join t_pam_materialconfirmbill mc on mce.fparentid=mc.fid\n");
    	builder.appendSql("	inner join t_con_contractbill mainContract on mc.fmaincontractbillid=mainContract.Fid \n");
    	builder.appendSql("	where mc.fsupplyDate<?\n");
    	builder.addParam(new Timestamp(FDCDateHelper.getSQLBegin(monthStartDate).getTime()));
    	builder.appendSql(" and \n");
    	builder.appendParam("mainContract.Fcurprojectid", curProjectIds.toArray());
    	builder.appendSql(" and \n");
    	builder.appendParam("mainContract.fstate", FDCBillStateEnum.AUDITTED_VALUE);
    	if(supplierIds!=null&&supplierIds.size()>0){
    		builder.appendSql(" and \n");
        	builder.appendParam("mainContract.FPartBid", supplierIds.toArray());
    	}
    	builder.appendSql("	group by mc.fmaincontractbillid,mce.fmaterialid");
    	// lmTable 上月累计
    	builder.appendSql(") lmTable on (monthTable.fmaincontractbillid = lmTable.fmaincontractbillid and monthTable.fmaterialid=lmTable.fmaterialid)\n");
    	builder.appendSql("left join (\n");
    	builder.appendSql("	select mc.fmaincontractbillid,mce.fmaterialid, sum(mce.fquantity) as sumquantity,sum(mce.famount) as sumAmount " +
    			"from t_pam_materialconfirmbillentry mce " +
    			"inner join t_pam_materialconfirmbill mc on mce.fparentid=mc.fid\n");
    	builder.appendSql("	inner join t_con_contractbill mainContract on mc.fmaincontractbillid=mainContract.Fid \n");
    	builder.appendSql("	where mc.fsupplyDate<=?\n");
    	builder.addParam(new Timestamp(FDCDateHelper.getSQLEnd(YearEndDate).getTime()));
    	builder.appendSql("	and mc.fsupplyDate>=?\n");
    	builder.addParam(new Timestamp(FDCDateHelper.getSQLEnd(YearStartDate).getTime()));
    	builder.appendSql(" and \n");
    	builder.appendParam("mainContract.Fcurprojectid", curProjectIds.toArray());
    	builder.appendSql(" and \n");
    	builder.appendParam("mainContract.fstate", FDCBillStateEnum.AUDITTED_VALUE);
    	if(supplierIds!=null&&supplierIds.size()>0){
    		builder.appendSql(" and \n");
        	builder.appendParam("mainContract.FPartBid", supplierIds.toArray());
    	}
    	builder.appendSql("	group by mc.fmaincontractbillid,mce.fmaterialid \n");
    	// sumTable 本年累计
    	builder.appendSql(") sumTable on (monthTable.fmaincontractbillid = sumTable.fmaincontractbillid and monthTable.fmaterialid=sumTable.fmaterialid) \n");
    	
    	builder.appendSql(" order by monthTable.fmaincontractbillid,monthTable.fmaterialid ");
    	
    	IRowSet rs = builder.executeQuery();
    	RetValue retValue = new RetValue();
    	ArrayList mainContractList = new ArrayList();
    	Map mainContractMap = new HashMap();
    	Map materialIds     = new HashMap();
    	try{
    		while(rs.next()){
    			String mainContractId = rs.getString("fmaincontractbillid");
    			String materialId     = rs.getString("fmaterialid");
    			String key = mainContractId+"_"+materialId;
    			ArrayList mainContractMaterial = null;
    			if(!mainContractMap.containsKey(mainContractId)){
    				mainContractMaterial = new ArrayList();
    				mainContractMap.put(mainContractId, mainContractMaterial);
    				mainContractList.add(mainContractId);
    			}else{
    				mainContractMaterial = (ArrayList)mainContractMap.get(mainContractId);
    			}
    			mainContractMaterial.add(materialId);
    			materialIds.put(materialId, Boolean.TRUE);
    			RetValue valueMap = new RetValue();
    			valueMap.setBigDecimal("quantity", rs.getBigDecimal("quantity"));
    			valueMap.setBigDecimal("price", rs.getBigDecimal("price"));
    			valueMap.setBigDecimal("amount", rs.getBigDecimal("amount"));
    			valueMap.setBigDecimal("LMAmount", rs.getBigDecimal("lmAmount"));
    			valueMap.setBigDecimal("LMQuantity", rs.getBigDecimal("lmquantity"));
    			valueMap.setBigDecimal("sumAmount", rs.getBigDecimal("sumAmount"));
    			valueMap.setBigDecimal("sumQuantity", rs.getBigDecimal("sumquantity"));
    				
    			retValue.put(key, valueMap);
    		}
    		retValue.put("mainContractList", mainContractList);
    		retValue.put("mainContractMap", mainContractMap);
    		EntityViewInfo view = new EntityViewInfo();
    		FilterInfo filter   = new FilterInfo();
    		view.setFilter(filter);
    		filter.getFilterItems().add(new FilterItemInfo("id",new HashSet(mainContractMap.keySet()),CompareType.INCLUDE));
    		view.getSelector().add("id");
    		view.getSelector().add("number");
    		view.getSelector().add("name");
    		view.getSelector().add("partB.number");
    		view.getSelector().add("partB.name");
    		
    		ContractBillCollection contracts = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
    		Map contractBillMap = new HashMap();
    		for(Iterator it = contracts.iterator();it.hasNext();){
    			ContractBillInfo info = (ContractBillInfo)it.next();
    			contractBillMap.put(info.getId().toString(), info);
    		}
    		retValue.put("contractBillMap", contractBillMap);
    		view.getSelector().clear();
    		view.getSelector().add("id");
    		view.getSelector().add("number");
        	view.getSelector().add("model");
        	view.getSelector().add("name");
        	view.getSelector().add("pricePrecision");
        	view.getSelector().add("baseUnit.name");
        	view.getSelector().add("baseUnit.number");
        	view.getSelector().add("baseUnit.qtyPrecision");
        	view.getFilter().getFilterItems().clear();
        	view.getFilter().getFilterItems().add(new FilterItemInfo("id",new HashSet(materialIds.keySet()),CompareType.INCLUDE));
        	
        	MaterialCollection materials = MaterialFactory.getLocalInstance(ctx).getMaterialCollection(view);
        	Map materialMap = new HashMap();
        	for(Iterator it=materials.iterator();it.hasNext();){
        		MaterialInfo info = (MaterialInfo)it.next();
        		materialMap.put(info.getId().toString(), info);
        	}
        	retValue.put("materialMap", materialMap);
    	}catch(Exception e){
    		logger.debug(e);
    	}
		return retValue;
	}
}