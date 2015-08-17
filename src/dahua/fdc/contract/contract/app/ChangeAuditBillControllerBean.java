package com.kingdee.eas.fdc.contract.app;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditBillType;
import com.kingdee.eas.fdc.contract.ChangeAuditUtil;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeEntryCollection;
import com.kingdee.eas.fdc.contract.ContractChangeEntryFactory;
import com.kingdee.eas.fdc.contract.ContractChangeEntryInfo;
import com.kingdee.eas.fdc.contract.ContractChangeException;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.CopySupplierEntryFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IConChangeSplit;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.IContractChangeBill;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.SupplierContentEntryCollection;
import com.kingdee.eas.fdc.contract.SupplierContentEntryFactory;
import com.kingdee.eas.fdc.contract.SupplierContentEntryInfo;
import com.kingdee.eas.fdc.contract.client.AbstractSplitInvokeStrategy;
import com.kingdee.eas.fdc.contract.client.ConChangeSplitEditUI;
import com.kingdee.eas.fdc.contract.client.SplitInvokeStrategyFactory;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.ConPayPlanFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusException;
import com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusUtil;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;

public class ChangeAuditBillControllerBean extends AbstractChangeAuditBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ChangeAuditBillControllerBean");
    protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
	throws BOSException, EASBizException {
    	ChangeAuditBillInfo info = (ChangeAuditBillInfo)model;
    	if(info.getChangeState()==null)
    		info.setChangeState(ChangeBillStateEnum.Saved);
    	if(info.getAuditType()!=null)
    		info.setAuditTypeName(info.getAuditType().getName());
    	if(info.getJobType()!=null)
    		info.setJobTypeName(info.getJobType().getName());
    	if(info.getSpecialtyType()!=null)
    		info.setSpecialtyTypeName(info.getSpecialtyType().getName());
    	super._save(ctx, pk, info);
    	
    	if(info.getSuppEntry()!=null&&info.getSuppEntry().size()>0){
    		if(!isGenerateAfterAudit(ctx)){//中渝模式，审批后生成
    			//原系统中，变更签证申请提交后自动生成的指令单为保存状态，需要改为和审批单状态一致，即已提交状态。
    			//by Cassiel_peng   2009-7-8
    			ChangeBill(ctx,model,FDCBillStateEnum.SAVED);
    		}
    	}
    	updateSplit(ctx, info);
    }
    
    protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	ChangeAuditBillInfo info = (ChangeAuditBillInfo)model;
    	if(info.getChangeState()==null)
    		info.setChangeState(ChangeBillStateEnum.Saved);
    	if(info.getAuditType()!=null)
    		info.setAuditTypeName(info.getAuditType().getName());
    	if(info.getJobType()!=null)
    		info.setJobTypeName(info.getJobType().getName());
    	if(info.getSpecialtyType()!=null)
    		info.setSpecialtyTypeName(info.getSpecialtyType().getName());
    	
    	
    	IObjectPK _save = super._save(ctx, info);
    	if(info.getSuppEntry()!=null&&info.getSuppEntry().size()>0){
    		if(isGenerateAfterAudit(ctx)){//中渝模式，审批后生成
    			return super._save(ctx, info);
    		}
    		//原系统中，变更签证申请提交后自动生成的指令单为保存状态，需要改为和审批单状态一致，即已提交状态。
    		//by Cassiel_peng   2009-7-8
    		ChangeBill(ctx,model,FDCBillStateEnum.SAVED);
    	}
    	updateSplit(ctx, info);
    	return _save;
    }
    
    protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
	throws BOSException, EASBizException {
    	ChangeAuditBillInfo info = (ChangeAuditBillInfo)model;
    	// modify by lihaiou. 2013.07.30.fix bug R130716-0291
		if (info.getChangeState() != ChangeBillStateEnum.Auditting) {
			info.setChangeState(ChangeBillStateEnum.Submit);
		}
		// modify end
    	if(info.getAuditType()!=null)
    		info.setAuditTypeName(info.getAuditType().getName());
    	if(info.getJobType()!=null)
    		info.setJobTypeName(info.getJobType().getName());
    	if(info.getSpecialtyType()!=null)
    		info.setSpecialtyTypeName(info.getSpecialtyType().getName());
    	super._submit(ctx, pk, info);
    	if(info.getSuppEntry()!=null&&info.getSuppEntry().size()>0){
    		if(isGenerateAfterAudit(ctx)){//中渝模式，审批后生成
    			return;
    		}
    		//目前系统中，变更签证申请提交后自动生成的指令单为保存状态，需要改为和审批单状态一致，即已提交状态。 by Cassiel_peng
    		ChangeBill(ctx,model,FDCBillStateEnum.SUBMITTED);
    	}
    	updateSplit(ctx, info);
    }
    /**
     * by Cassiel_peng
     */
    protected IObjectPK _submit(Context ctx, IObjectValue model)
	throws BOSException, EASBizException {
    	ChangeAuditBillInfo info = (ChangeAuditBillInfo)model;
    	
    	//提交前检查
		checkBillForSubmit( ctx,info);
		
    	ChangeSupplierEntryCollection infoCollection=info.getSuppEntry();
    	FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    	for(int i=0;i<infoCollection.size();i++){
    		ChangeSupplierEntryInfo entryInfo=(ChangeSupplierEntryInfo)infoCollection.get(i);
    		//①、此次修改后的金额
    		BigDecimal oriCostAmount=FDCHelper.toBigDecimal(entryInfo.getCostAmount(),2);
    		/**
    		 * 成本类拆分
    		 */
    		if(entryInfo.getContractBill()!=null&&entryInfo.getContractBill().isIsCoseSplit()){
        		try {
        			//根据变更签证确认的id检测该变单据是否已经被拆分   未作拆分后续操作不再进行，提高性能   
        			if (entryInfo.getContractChange() == null) {
						continue;
					}
        			String contractChangeId=entryInfo.getContractChange().getId().toString();//变更签证确认ID
        			boolean isCostSplit = false;
        			FilterInfo filter=null;
        			builder.clear();
        			builder.appendSql("select fid from t_con_conchangesplit where FContractChangeID =? ");
        			builder.addParam(contractChangeId);
        			IRowSet rowSet1=builder.executeQuery();
        			while(rowSet1.next()){
        				isCostSplit=true;
        			}
        			if(isCostSplit){//如果被拆分过了
        				builder.clear();
        				builder.appendSql("select famount from t_con_conchangesplit where FContractChangeID=? ");
        				builder.addParam(contractChangeId);
        				IRowSet rowSet2=builder.executeQuery(ctx);
        				while(rowSet2.next()){
        					//②、获取变更签证确认的拆分合计金额
        					BigDecimal splitCostAmountSum=FDCHelper.toBigDecimal(rowSet2.getBigDecimal("famount"),2);
        					if(oriCostAmount!=null&&!oriCostAmount.equals(splitCostAmountSum)){
        						//清除付款拆分         根据变更签证确认Id找到引用了这个变更签证确认的付款拆分分录的头   
        						builder.clear();
        						builder.appendSql("select distinct(fparentid) from  T_FNC_PaymentSplitEntry  where  fcostbillid=? ");
        						builder.addParam(contractChangeId);
        						IRowSet rowSet3=builder.executeQuery();
        						while(rowSet3.next()){
        							filter=new FilterInfo();
        							filter.appendFilterItem("costBillId", contractChangeId);
        							PaymentSplitEntryFactory.getLocalInstance(ctx).delete(filter);//删除分录
        							filter=new FilterInfo();
        							filter.appendFilterItem("id", rowSet3.getString("fparentid"));
        							PaymentSplitFactory.getLocalInstance(ctx).delete(filter);//删除头
        						}
        						//清除结算拆分        根据变更签证确认Id找到引用了这个变更签证确认的结算拆分分录的头
        						builder.clear();
        						builder.appendSql("select distinct(fparentid) from  T_CON_SettlementCostSplitEntry  where  fcostbillid=? ");
        						builder.addParam(contractChangeId);
        						IRowSet rowSet4=builder.executeQuery();
        						while(rowSet4.next()){
        							filter=new FilterInfo();
        							filter.appendFilterItem("costBillId", contractChangeId);
        							SettlementCostSplitEntryFactory.getLocalInstance(ctx).delete(filter);
        							filter=new FilterInfo();
        							filter.appendFilterItem("id", rowSet4.getString("fparentid"));
        							SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);
        						}
        						//清除变更拆分      变更拆分需要根据指令单的ID来删除
        						filter=new FilterInfo();
        						filter.appendFilterItem("parent.contractChange.id", contractChangeId);
        						ConChangeSplitEntryFactory.getLocalInstance(ctx).delete(filter);//删除分录
        						filter=new FilterInfo();
        						filter.appendFilterItem("contractChange.id", contractChangeId);
        						ConChangeSplitFactory.getLocalInstance(ctx).delete(filter);//删除头
        					}
        				}
        			}
    			} catch (Exception e) {
    				e.printStackTrace();
    				throw new BOSException(e);
    			}
    		}
    		/**
			 * 非成本类拆分
			 */
    		if(entryInfo.getContractBill()!=null&&!entryInfo.getContractBill().isIsCoseSplit()){
    			try {
    				if (entryInfo.getContractChange() == null) {
						continue;
					}
    				String contractChangeId=entryInfo.getContractChange().getId().toString();//变更签证确认ID
//    				FDCSplitClientHelper.isBillSplited(entryInfo.getContractChange().getId().toString(),"T_CON_ConChangeNoCostSplit", "FContractChangeID");
    				boolean isCostSplit=false;
    				builder.clear();
        			builder.appendSql("select fid from T_CON_ConChangeNoCostSplit where FContractChangeID =? ");
        			builder.addParam(contractChangeId);
        			IRowSet rowSet1=builder.executeQuery();
        			while(rowSet1.next()){
        				isCostSplit=true;
        			}
    				FilterInfo filter =null;
    				if(isCostSplit){
    					builder.clear();
    					builder.appendSql("select famount from T_CON_ConChangeNoCostSplit where FContractChangeID=? ");
    					builder.addParam(contractChangeId);
    					IRowSet rowSet=builder.executeQuery(ctx);
    					while(rowSet.next()){
    						//②、获取变更签证确认的拆分合计金额
    						BigDecimal splitCostAmountSum=FDCHelper.toBigDecimal(rowSet.getBigDecimal("famount"),2);
    						if(oriCostAmount!=null&&!oriCostAmount.equals(splitCostAmountSum)){
        						//清除付款拆分         根据变更签证确认Id找到引用了这个变更签证确认的付款拆分分录的头   
        						builder.clear();
        						builder.appendSql("select distinct(fparentid) from  T_FNC_PaymentNoCostSplitEntry  where  fcostbillid=? ");
        						builder.addParam(contractChangeId);
        						IRowSet rowSet3=builder.executeQuery();
        						while(rowSet3.next()){
        							filter=new FilterInfo();
        							filter.appendFilterItem("costBillId", contractChangeId);
        							PaymentNoCostSplitEntryFactory.getLocalInstance(ctx).delete(filter);//删除分录
        							filter=new FilterInfo();
        							filter.appendFilterItem("id", rowSet3.getString("fparentid"));
        							PaymentNoCostSplitFactory.getLocalInstance(ctx).delete(filter);//删除头
        						}
        						//清除结算拆分        根据变更签证确认Id找到引用了这个变更签证确认的结算拆分分录的头
        						builder.clear();
        						builder.appendSql("select distinct(fparentid) from  T_CON_SettNoCostSplitEntry  where  fcostbillid=? ");
        						builder.addParam(contractChangeId);
        						IRowSet rowSet4=builder.executeQuery();
        						while(rowSet4.next()){
        							filter=new FilterInfo();
        							filter.appendFilterItem("costBillId", contractChangeId);
        							SettNoCostSplitEntryFactory.getLocalInstance(ctx).delete(filter);
        							filter=new FilterInfo();
        							filter.appendFilterItem("id", rowSet4.getString("fparentid"));
        							SettNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
        						}
        						//清除变更拆分      变更拆分需要根据指令单的ID来删除
        						filter=new FilterInfo();
        						filter.appendFilterItem("parent.contractChange.id", contractChangeId);
        						ConChangeNoCostSplitEntryFactory.getLocalInstance(ctx).delete(filter);//删除分录
        						filter=new FilterInfo();
        						filter.appendFilterItem("contractChange.id", contractChangeId);
        						ConChangeNoCostSplitFactory.getLocalInstance(ctx).delete(filter);//删除头
    						}
    					}
    				}
    			} catch (Exception e) {
    				e.printStackTrace();
    				throw new BOSException(e);
    			}
    		}
    	}
		
    	info.setChangeState(ChangeBillStateEnum.Submit);
    	if(info.getAuditType()!=null)
    		info.setAuditTypeName(info.getAuditType().getName());
    	if(info.getJobType()!=null)
    		info.setJobTypeName(info.getJobType().getName());
    	if(info.getSpecialtyType()!=null)
    		info.setSpecialtyTypeName(info.getSpecialtyType().getName());
    	
    	if(info.getSuppEntry()!=null&&info.getSuppEntry().size()>0){
    		if(isGenerateAfterAudit(ctx)){//中渝模式，审批后生成
    			return super._submit(ctx, info);
    		}
    		//原系统中，变更签证申请提交后自动生成的指令单为保存状态，需要改为和审批单状态一致，即已提交状态。
    		//by Cassiel_peng   2009-7-8
    		ChangeBill(ctx,model,FDCBillStateEnum.SUBMITTED);
    	}
    	
    	 IObjectPK _submit = super._submit(ctx, info);
    	updateSplit(ctx, info);
    	return _submit;
    }
    
    private void updateSplit(Context ctx,ChangeAuditBillInfo info) throws BOSException, EASBizException{
    	String oql = "select id,contractBill.id,contractBill.number,contractBill.name where changeAudit.id='"+info.getId()+"'";
    	IContractChangeBill iBill = ContractChangeBillFactory.getLocalInstance(ctx);
    	IConChangeSplit localInstance = ConChangeSplitFactory.getLocalInstance(ctx);
		ContractChangeBillCollection contractChangeBillColl = iBill.getContractChangeBillCollection(oql);
		
		Set<String> idSet = new HashSet<String>();
		for (int i = 0; i < contractChangeBillColl.size(); i++) {
			ContractChangeBillInfo contractChangeBillInfo = contractChangeBillColl.get(i);
			idSet.add(contractChangeBillInfo.getId().toString());
		}
		
		oql = "select contractChange.id where sourceBillId='"+info.getId()+"'";
		ConChangeSplitCollection conChangeSplitColl = localInstance.getConChangeSplitCollection(oql);
		for (int i = 0; i < conChangeSplitColl.size(); i++) {
			ConChangeSplitInfo conChangeSplitInfo = conChangeSplitColl.get(i);
			if(conChangeSplitInfo.getContractChange()==null)
				continue;
			if(!idSet.contains(conChangeSplitInfo.getContractChange().getId().toString()))
				localInstance.delete(new ObjectUuidPK(conChangeSplitInfo.getId()));
		}
    }

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		ChangeAuditBillInfo info = (ChangeAuditBillInfo)super._getValue(ctx,pk);
		ChangeSupplierEntryCollection c = info.getSuppEntry();
		for(int i=0;i<c.size();i++){
			FilterInfo filter = new FilterInfo();
			FilterItemCollection filterItems = filter.getFilterItems();
			filterItems.add(new FilterItemInfo("parent.id", c.get(i).getId().toString()));
			SupplierContentEntryFactory.getLocalInstance(ctx).delete(filter);
			CopySupplierEntryFactory.getLocalInstance(ctx).delete(filter);
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("changeAudit.id",info.getId().toString()));
		view.setFilter(filter);
		IContractChangeBill localInstance = ContractChangeBillFactory.getLocalInstance(ctx);
		ContractChangeBillCollection contractChangeBillColl = localInstance.getContractChangeBillCollection(view);
		for (int i = 0; i < contractChangeBillColl.size(); i++) 
			ConChangeSplitFactory.getLocalInstance(ctx).delete("where contractChange.id='"+contractChangeBillColl.get(i).getId()+"'");
		
		localInstance.delete(filter);
		
		super._delete(ctx, pk);
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		for(int i=0;i<arrayPK.length;i++){			
			ChangeAuditBillInfo info= getChangeAuditBillInfo(ctx,arrayPK[i]);
			ChangeSupplierEntryCollection c = info.getSuppEntry();
			for(int j=0;j<c.size();j++){
				info.getSuppEntry().removeObject(j);
			}
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			FilterItemCollection filterItems = filter.getFilterItems();
			filterItems.add(new FilterItemInfo("changeAudit.id",info.getId().toString()));
			view.setFilter(filter);
			IContractChangeBill localInstance = ContractChangeBillFactory.getLocalInstance(ctx);
			ContractChangeBillCollection contractChangeBillColl = localInstance.getContractChangeBillCollection(view);
			for (int i1 = 0; i1 < contractChangeBillColl.size(); i1++) 
				ConChangeSplitFactory.getLocalInstance(ctx).delete("where contractChange.id='"+contractChangeBillColl.get(i1).getId()+"'");
			
			localInstance.delete(filter);
		}
		super._delete(ctx, arrayPK);
	}
    
	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
	throws BOSException, EASBizException {
		ChangeAuditBillInfo info = (ChangeAuditBillInfo)model;
		if(info.getAuditType()!=null)
    		info.setAuditTypeName(info.getAuditType().getName());
    	if(info.getJobType()!=null)
    		info.setJobTypeName(info.getJobType().getName());
    	if(info.getSpecialtyType()!=null)
    		info.setSpecialtyTypeName(info.getSpecialtyType().getName());
//		removeDetailEntries(ctx, info);		
		super._update(ctx, pk, info);
	}
	
	private void removeDetailEntries(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ChangeAuditBillInfo info = (ChangeAuditBillInfo) model;
		ChangeSupplierEntryCollection c = info.getSuppEntry();
		for(int i=0;i<c.size();i++){
			FilterInfo filter = new FilterInfo();
			FilterItemCollection filterItems = filter.getFilterItems();
			if(c.get(i).getId()!=null){
				filterItems.add(new FilterItemInfo("parent.id", c.get(i).getId().toString()));
				SupplierContentEntryFactory.getLocalInstance(ctx).delete(filter);
			}
		}
	}
	
	private void ChangeBill(Context ctx, IObjectValue model, FDCBillStateEnum state) throws BOSException, EASBizException {
		ChangeAuditBillInfo info = (ChangeAuditBillInfo) model;
		if(info.getBillType()!=null&&info.getBillType().equals(ChangeAuditBillType.ChangeAuditRequest))
			return;
		ChangeSupplierEntryCollection c = info.getSuppEntry();
		//之前系统中如果变更签证申请下发单位分录为空是不允许提交的,现在中渝模式下是没有此限制的,故必须处理为空的情况 by Cassiel_peng 2009-9-25
		if(c!=null){
			for(int i=0;i<c.size();i++){			
				ChangeSupplierEntryInfo entry = c.get(i);
				if(entry.getContractChange()==null){ 
					ContractChangeBillInfo change = new ContractChangeBillInfo();
					change.setConductTime(FDCHelper.getCurrentDate());
					change.setSettleTime(FDCHelper.getCurrentDate());	    	
					change.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
					change.setSourceType(info.getSourceType());
//					change.setOrgUnit(ContextUtil.getCurrentCostUnit(ctx).castToFullOrgUnitInfo());
					change.setOrgUnit(info.getOrgUnit());
					change.setChangeAudit(info);
					if(info.getNumber()!=null)
						change.setChangeAuditNumber(info.getNumber());
					change.setState(state);
					change.setChangeType(info.getAuditType());
					if(info.getAuditTypeName()!=null)
						change.setChangeTypeName(info.getAuditTypeName());
					//效成本金额只登记在第1个变更签证确认上，并且在变更签证确认上增加“无效成本金额”、“无效成本原因”  20080324
					if(i==0){
						change.setInvalidCostReason(info.getInvalidCostReason());
						change.setCostNouse(info.getCostNouse());
					}else{
						change.setInvalidCostReason(null);
						change.setCostNouse(GlUtils.zero);
					}
					//期间
					change.setBookedDate(info.getBookedDate());
					change.setPeriod(info.getPeriod());
					
					change.setChangeReason(info.getChangeReason());
					change.setChangeSubject(info.getChangeSubject());
					change.setConductDept(info.getConductDept());
					change.setUrgentDegree(info.getUrgentDegree());
					change.setCurProject(info.getCurProject());
					if(info.getCurProjectName()!=null)
						change.setCurProjectName(info.getCurProjectName());
					change.setJobType(info.getJobType());
					if(info.getJobTypeName()!=null)
						change.setJobTypeName(info.getJobTypeName());
					change.setSpecialtyType(info.getSpecialtyType());
					if(info.getSpecialtyTypeName()!=null)
						change.setSpecialtyTypeName(info.getSpecialtyTypeName());
					change.setGraphCount(info.getGraphCount());
					change.setConductTime(info.getCreateTime());
					change.setMainSupp(entry.getMainSupp());
					change.setContractBill(entry.getContractBill());
					if(entry.getContractBill()!=null&&entry.getContractBill().getNumber()!=null){
						change.setContractBillNumber(entry.getContractBill().getNumber());
						change.setIsConSetted(entry.getContractBill().isHasSettled());
						change.setIsCostSplit(entry.getContractBill().isIsCoseSplit());
					}
					change.setBalanceType(entry.getBalanceType());
					change.setIsDeduct(entry.isIsDeduct());
					change.setDeductAmount(entry.getDeductAmount());
					change.setDeductReason(entry.getDeductReason());
					//原币金额
					change.setAmount(entry.getCostAmount());
					change.setOriginalAmount(entry.getOriCostAmount());
					//汇率
					change.setCurrency(entry.getCurrency());
					change.setExRate(entry.getExRate());
					//增加原始联系单号 eric_wang 2010.05.31
					change.setOriginalContactNum(entry.getOriginalContactNum());
					change.setIsSureChangeAmt(entry.isIsSureChangeAmt());
					change.setIsImportChange(info.isIsImportChange());
					change.setConstructPrice(entry.getConstructPrice());
					// 默认值
					change.setOriBalanceAmount(null);
					// change.setBalanceAmount(FDCHelper.ZERO);
					SupplierContentEntryCollection coll = new SupplierContentEntryCollection();
					EntityViewInfo vit = new EntityViewInfo();
					FilterInfo fit = new FilterInfo();
					FilterItemCollection itt = fit.getFilterItems();	
					if(info.getId()!=null&&entry.getId()!=null){
						itt.add(new FilterItemInfo("parent.id", entry.getId().toString(),CompareType.EQUALS));
						vit.setFilter(fit);
						vit.getSelector().add("content.*");
						SorterItemInfo sortName = new SorterItemInfo("seq");
		                sortName.setSortType(SortType.ASCEND);
						vit.getSorter().add(sortName);
						coll = SupplierContentEntryFactory.getLocalInstance(ctx).getSupplierContentEntryCollection(vit);
					}else{
						coll = entry.getEntrys();
					}
					ContractChangeEntryCollection entrycoll = change.getEntrys();
					for(int j=0;j<coll.size();j++){
						SupplierContentEntryInfo con = coll.get(j);
						ContractChangeEntryInfo test = new ContractChangeEntryInfo();
						test.setNumber(con.getContent().getNumber());
						test.setChangeContent(con.getContent().getChangeContent());
						test.setIsBack(con.getContent().isIsBack());
						test.setSeq(con.getContent().getSeq());
						entrycoll.add(test);
					}
					change.setName(info.getName()+"_"+(i+1));
					String orgId = ContextUtil.getCurrentCostUnit(ctx).getId().toString();
					String billNumber = null;
					/***
					 * 变更签证确认生成时，调用编码规则报错，请检查编码规则是否有问题,例如：顺序号是否已经使用完
					 * 异常被吃掉，使不应该的，这里输出后台日至，备查
					 */
					try {
						billNumber = FDCHelper.getNumberByCodingRule(ctx, change, orgId);
					} catch (Exception e1){
						logger.error("变更签证确认生成时，调用编码规则报错，请检查编码规则是否有问题,例如：顺序号是否已经使用完！", e1);
						if(e1 instanceof BOSException)
							throw (BOSException)e1;
						if(e1 instanceof EASBizException)
							throw (EASBizException)e1;
					}
					if(billNumber == null) {			
						billNumber = info.getNumber()+"_"+(i+1);
					}			
					change.setNumber(billNumber);
					//中渝增加字段
					if(info.getConductUnit()!=null){
						change.setConductUnit(info.getConductUnit());
					}
					if(info.getOffer()!=null){
						change.setOffer(info.getOffer());
					}
					if(info.getConstrUnit()!=null){
						change.setConstrUnit(info.getConstrUnit());
					}
					if(info.getConstrSite()!=null){
						change.setConstrSite(info.getConstrSite());
					}
					ContractChangeBillFactory.getLocalInstance(ctx).addnew(change);
					entry.setContractChange(change);
					//审批的变更签证申请生成指令单之后不会将指令单和下发单位分录关联起来，所以在此处下发单位分录自己维护。
					//否则会审批一次生成一 次     by Cassiel_peng  2009-9-26
					if(entry!=null&&entry.getId()!=null){
						SelectorItemCollection _selector=new SelectorItemCollection();
						_selector.add("contractChange");
						ChangeSupplierEntryFactory.getLocalInstance(ctx).updatePartial(entry, _selector);
					}
				}
				else{
					ContractChangeBillInfo change = entry.getContractChange();
//					for(i=0;i<change.getEntrys().size();i++){
//						change.getEntrys().removeObject(i);
//						i--;
//					}
					FilterInfo fi = new FilterInfo();
					FilterItemCollection it = fi.getFilterItems();	
					if(change.getId()!=null){
						it.add(new FilterItemInfo("parent.id", change.getId().toString(),CompareType.EQUALS));
						ContractChangeEntryFactory.getLocalInstance(ctx).delete(fi);
					}
					if(info.getNumber()!=null)
						change.setChangeAuditNumber(info.getNumber());
					//效成本金额只登记在第1个变更签证确认上，并且在变更签证确认上增加“无效成本金额”、“无效成本原因”  20080324
					if(i==0){
						change.setInvalidCostReason(info.getInvalidCostReason());
						change.setCostNouse(info.getCostNouse());
					}else{
						change.setInvalidCostReason(null);
						change.setCostNouse(GlUtils.zero);
					}
					
					
					change.setName(info.getName()+"_"+(i+1));
					String orgId = ContextUtil.getCurrentCostUnit(ctx).getId().toString();
					String billNumber = null;
					/***
					 * 变更签证确认生成时，调用编码规则报错，请检查编码规则是否有问题,例如：顺序号是否已经使用完
					 * 异常被吃掉，使不应该的，这里输出后台日至，备查
					 */
					try {
						billNumber = FDCHelper.getNumberByCodingRule(ctx, change, orgId);
					} catch (Exception e1){
						logger.error("变更签证确认生成时，调用编码规则报错，请检查编码规则是否有问题,例如：顺序号是否已经使用完！", e1);
						if(e1 instanceof BOSException)
							throw (BOSException)e1;
						if(e1 instanceof EASBizException)
							throw (EASBizException)e1;
					}
					if(billNumber == null) {			
						billNumber = info.getNumber()+"_"+(i+1);
					}			
					change.setNumber(billNumber);
					
					//期间
					change.setBookedDate(info.getBookedDate());
					change.setPeriod(info.getPeriod());
					change.setSourceType(info.getSourceType());
					
					change.setChangeType(info.getAuditType());
					if(info.getAuditTypeName()!=null)
						change.setChangeTypeName(info.getAuditTypeName());
					change.setChangeReason(info.getChangeReason());
					change.setChangeSubject(info.getChangeSubject());
					change.setConductDept(info.getConductDept());
					change.setUrgentDegree(info.getUrgentDegree());
					change.setCurProject(info.getCurProject());
					if(info.getCurProjectName()!=null)
						change.setCurProjectName(info.getCurProjectName());
					change.setJobType(info.getJobType());
					if(info.getJobTypeName()!=null)
						change.setJobTypeName(info.getJobTypeName());
					change.setState(state);
					change.setSpecialtyType(info.getSpecialtyType());
					if(info.getSpecialtyTypeName()!=null)
						change.setSpecialtyTypeName(info.getSpecialtyTypeName());
					change.setGraphCount(info.getGraphCount());
					change.setConductTime(info.getCreateTime());
					change.setMainSupp(entry.getMainSupp());
					change.setContractBill(entry.getContractBill());
					if(entry.getContractBill()!=null&&entry.getContractBill().getNumber()!=null){
						change.setContractBillNumber(entry.getContractBill().getNumber());
						change.setIsConSetted(entry.getContractBill().isHasSettled());
						change.setIsCostSplit(entry.getContractBill().isIsCoseSplit());
					}
					change.setBalanceType(entry.getBalanceType());
					change.setIsDeduct(entry.isIsDeduct());
					change.setDeductAmount(entry.getDeductAmount());
					change.setDeductReason(entry.getDeductReason());
					//原币金额
					change.setAmount(entry.getCostAmount());
					change.setOriginalAmount(entry.getOriCostAmount());
					//汇率
					change.setCurrency(entry.getCurrency());
					change.setExRate(entry.getExRate());
					//原始联系单号 eric_wang 2010.05.31
					change.setOriginalContactNum(entry.getOriginalContactNum());
					change.setIsSureChangeAmt(entry.isIsSureChangeAmt());
					change.setIsImportChange(info.isIsImportChange());
					change.setConstructPrice(entry.getConstructPrice());
					
					EntityViewInfo vit = new EntityViewInfo();
					FilterInfo fit = new FilterInfo();
					FilterItemCollection itt = fit.getFilterItems();	
					if(entry.getId()!=null)
						itt.add(new FilterItemInfo("parent.id", entry.getId().toString(),CompareType.EQUALS));
					vit.setFilter(fit);
					vit.getSelector().add("content.*");
					SorterItemInfo sortName = new SorterItemInfo("seq");
	                sortName.setSortType(SortType.ASCEND);
					vit.getSorter().add(sortName);
					SupplierContentEntryCollection coll = SupplierContentEntryFactory.getLocalInstance(ctx).getSupplierContentEntryCollection(vit);
					for(int j=0;j<coll.size();j++){
						SupplierContentEntryInfo con = coll.get(j);
						ContractChangeEntryInfo test = new ContractChangeEntryInfo();
						test.setNumber(con.getContent().getNumber());
						test.setChangeContent(con.getContent().getChangeContent());
						test.setIsBack(con.getContent().isIsBack());
						test.setSeq(con.getContent().getSeq());
						test.setParent(change);
						ContractChangeEntryFactory.getLocalInstance(ctx).addnew(test);
					}
					//中渝增加字段
					if(info.getConductUnit()!=null){
						change.setConductUnit(info.getConductUnit());
					}
					if(info.getOffer()!=null){
						change.setOffer(info.getOffer());
					}
					if(info.getConstrUnit()!=null){
						change.setConstrUnit(info.getConstrUnit());
					}
					if(info.getConstrSite()!=null){
						change.setConstrSite(info.getConstrSite());
					}
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("changeType");
					selector.add("changeReason");
					selector.add("changeSubject");
					selector.add("conductDept");
					selector.add("urgentDegree");
					selector.add("curProject");
					selector.add("jobType");
					selector.add("specialtyType");
					selector.add("graphCount");
					selector.add("mainSupp");
					selector.add("contractBill");
					selector.add("balanceType");
					selector.add("isDeduct");
					selector.add("deductAmount");
					selector.add("deductReason");
//					selector.add("entrys.*");
					selector.add("amount");
					selector.add("contractBillNumber");
					selector.add("conductTime");
					selector.add("state");
					selector.add("changeAuditNumber");
					selector.add("changeTypeName");
					selector.add("curProjectName");
					selector.add("jobTypeName");
					selector.add("specialtyTypeName");
					selector.add("costNouse");
					selector.add("invalidCostReason");
					selector.add("originalAmount");
					selector.add("bookedDate");
					selector.add("period");
					selector.add("isConSetted");
					selector.add("isCostSplit");
					selector.add("conductUnit");
					selector.add("offer");
					selector.add("constrUnit");
					selector.add("constrSite");
					selector.add("originalContactNum");
					selector.add("isSureChangeAmt");
					selector.add("isImportChange");
					selector.add("constructPrice");
					selector.add("name");
					selector.add("number");
					ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(change, selector);
				}
			}
		}
//		if(info.getId()!=null){
//		FilterInfo filter=new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("changeAudit.id",info.getId().toString(),CompareType.EQUALS));
//		ContractChangeBillFactory.getLocalInstance(ctx).delete(filter);
//		}
	}

	protected void _register(Context ctx, Set idSet) throws BOSException, EASBizException {
		for (Iterator iter = idSet.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			_register4WF(ctx, new ObjectUuidPK(BOSUuid.read(id)));
//			ChangeAuditBillInfo billInfo = new ChangeAuditBillInfo();
//			billInfo.setId(BOSUuid.read(id));
//			billInfo.setChangeState(ChangeBillStateEnum.Register);
//			SelectorItemCollection selector = new SelectorItemCollection();
//			selector.add("changeState");
//			_updatePartial(ctx, billInfo, selector);

		}		
	}

	protected void _disPatch(Context ctx, Set idSet) throws BOSException, EASBizException {
		for (Iterator iter = idSet.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			_disPatch4WF(ctx, new ObjectUuidPK(BOSUuid.read(id)));
//			ChangeAuditBillInfo billInfo = new ChangeAuditBillInfo();
//			billInfo.setId(BOSUuid.read(id));
//			billInfo.setChangeState(ChangeBillStateEnum.Announce);
//			SelectorItemCollection selector = new SelectorItemCollection();
//			selector.add("changeState");
//			_updatePartial(ctx, billInfo, selector);
		}	
		
	}

	protected void _aheadDisPatch(Context ctx, Set idSet) throws BOSException, EASBizException {
		for (Iterator iter = idSet.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			IObjectPK pk = new ObjectUuidPK(BOSUuid.read(id));
			_aheadDisPatch4WF(ctx,pk);
//			ChangeAuditBillInfo info = (ChangeAuditBillInfo)super._getValue(ctx,pk);
//			if(info.getSuppEntry().size()>0){
//	    		ChangeBill(ctx,info,FDCBillStateEnum.ANNOUNCE);
//	    	}
//			info.setChangeState(ChangeBillStateEnum.AheadDisPatch);
//			SelectorItemCollection selector = new SelectorItemCollection();
//			selector.add("changeState");
//			_updatePartial(ctx, info, selector);			
		}			
	}
	
	/**
	 * 作废
	 */
	protected void _cancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		ChangeAuditBillInfo billInfo = new ChangeAuditBillInfo();

		billInfo.setId(BOSUuid.read(pk.toString()));
		billInfo.setState(FDCBillStateEnum.INVALID);
		billInfo.setChangeState(ChangeBillStateEnum.INVALID);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("changeState");
		
		_updatePartial(ctx, billInfo, selector);
	}

	/**
	 * 生效
	 */
	protected void _cancelCancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		ChangeAuditBillInfo billInfo = new ChangeAuditBillInfo();

		billInfo.setId(BOSUuid.read(pk.toString()));
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		billInfo.setChangeState(ChangeBillStateEnum.Audit);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("changeState");
		
		_updatePartial(ctx, billInfo, selector);
		
	}

	protected void _setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {		
		ChangeAuditBillInfo billInfo = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(billId));
		billInfo.setChangeState(ChangeBillStateEnum.Auditting);
		billInfo.setState(FDCBillStateEnum.AUDITTING);
		int num = billInfo.getSuppEntry().size();
		ChangeSupplierEntryCollection c = billInfo.getSuppEntry();
		if(num>0){
			for(int i=0; i<num; i++){
				ChangeSupplierEntryInfo entry = c.get(i);
				if(entry.getContractChange()!=null){
					ContractChangeBillInfo change = entry.getContractChange();
					ContractChangeBillFactory.getLocalInstance(ctx).setAudittingStatus(change.getId());
					/*change.setState(FDCBillStateEnum.AUDITTING);
					SelectorItemCollection sele = new SelectorItemCollection();
					sele.add("state");
					ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(change, sele);*/
				}				
			}
		}
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("changeState");
		selector.add("state");
		_updatePartial(ctx, billInfo, selector);
	}

	protected void _setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {ChangeAuditBillInfo billInfo = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(billId));
		billInfo.setChangeState(ChangeBillStateEnum.Submit);
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		int num = billInfo.getSuppEntry().size();
		ChangeSupplierEntryCollection c = billInfo.getSuppEntry();
		if(num>0){
			for(int i=0; i<num; i++){
				ChangeSupplierEntryInfo entry = c.get(i);
				if(entry.getContractChange()!=null){
					ContractChangeBillInfo change = entry.getContractChange();
					ContractChangeBillFactory.getLocalInstance(ctx).setSubmitStatus(change.getId());
					/*change.setState(FDCBillStateEnum.AUDITTING);
					SelectorItemCollection sele = new SelectorItemCollection();
					sele.add("state");
					ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(change, sele);*/
					}				
			}
		}
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("changeState");
		selector.add("state");
		_updatePartial(ctx, billInfo, selector);
	}
	
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
	EASBizException {
		
//		ChangeAuditBillInfo billInfo = new ChangeAuditBillInfo();
		ChangeAuditBillInfo billInfo = ChangeAuditBillFactory.getLocalInstance(ctx)
			.getChangeAuditBillInfo(new ObjectUuidPK(billId));

		checkBillForAudit( ctx,billId,billInfo);
		
		vefySettleAmount(ctx, billInfo);
		
//		billInfo.setId(billId);
		billInfo.setChangeState(ChangeBillStateEnum.Audit);
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		billInfo.setAuditTime(new Date());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("changeState");
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		ChangeSupplierEntryCollection c = billInfo.getSuppEntry();
		//之前系统中如果变更签证申请下发单位分录为空是不允许提交的,现在中渝模式下是没有此限制,故必须处理为空的情况  by Cassiel_peng 2009-9-25
		if(c!=null){
			int num = billInfo.getSuppEntry().size();
			if(num>0){
				Set set=new HashSet();
				for(int i=0;i<num;i++){
					ChangeSupplierEntryInfo entry = c.get(i);
					set.add(entry.getContractBill().getId().toString());
				}
				EntityViewInfo view=new EntityViewInfo();
				view.setFilter(new FilterInfo());
				view.getFilter().getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
				view.getSelector().add("hasSettled");
				view.getSelector().add("number");
				ContractBillCollection cons=ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
				boolean hasSettled=false;
				String conNumber="";
				for(int i=0;i<cons.size();i++){
					if(cons.get(i).isHasSettled()){
						hasSettled=true;
						conNumber+="、“"+cons.get(i).getNumber()+"”";
					}
				}
				if(hasSettled){
					throw new EASBizException(new NumericExceptionSubItem("111","审批不通过：变更签证申请登记下发单位内存在已结算的合同，合同编码："+conNumber.substring(1)));
				}
				List list = new ArrayList();
				for(int i=0; i<num; i++){
					ChangeSupplierEntryInfo entry = c.get(i);
					if(entry.getContractChange()!=null){
						ContractChangeBillInfo change = entry.getContractChange();
						list.add(change.getId().toString());
//					change.setState(FDCBillStateEnum.AUDITTED);
//					change.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
//					change.setAuditTime(new Date());
//					SelectorItemCollection sele = new SelectorItemCollection();
//					sele.add("auditor");
//					sele.add("auditTime");
//					sele.add("state");
//					ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(change, sele);
					}				
				}
				
				// modified by zhaoqin for R130711-0196,R130704-0246 on 2013/07/16 start
				// boolean isGenerateAfterAuidt = FDCUtils.getBooleanValue4FDCParamByKey(ctx, null,
				// FDCConstants.FDC_PARAM_AUTOCHANGETOPROJECTVISA);
				boolean isGenerateAfterAuidt = FDCUtils.getBooleanValue4FDCParamByKey(ctx, null,
						FDCConstants.FDC_PARAM_GENERATEAFTERAUDIT);
				// modified by zhaoqin for R130711-0196,R130704-0246 on 2013/07/16 end
				
				if(c!=null&&c.size()>0){
					if(isGenerateAfterAuidt){
						ChangeBill(ctx, billInfo, FDCBillStateEnum.SAVED);
					}
				}
				//2008-11-19 改为ContractChangeBill的审批方法
				if(list!=null && list.size()>0){
					if(!isGenerateAfterAuidt){
						ContractChangeBillFactory.getLocalInstance(ctx).audit(list);
					}
				}
			}
		}
		_updatePartial(ctx, billInfo, selector);
		
		
		updatePeriod(ctx, billId);
		
		if(billInfo.getBillType()!=null&&!billInfo.getBillType().equals(ChangeAuditBillType.ChangeAuditRequest))
			runBathBill(ctx,billInfo,null);
	}
	
	private void runBathBill(Context ctx,ChangeAuditBillInfo billInfo,String actionName) throws BOSException, EASBizException{
		String oql = "where changeAudit.id='"+billInfo.getId()+"'";
		List selectedIdValues = new ArrayList();
		IContractChangeBill iBill = ContractChangeBillFactory.getLocalInstance(ctx);
		IConChangeSplit IConChangeSplit = ConChangeSplitFactory.getLocalInstance(ctx);
		ContractChangeBillCollection contractChangeBillColl = iBill.getContractChangeBillCollection(oql);
		
		ContractChangeBillInfo changeBillInfo[] = new ContractChangeBillInfo[contractChangeBillColl.size()];
		ObjectUuidPK objectPk[] = new ObjectUuidPK[contractChangeBillColl.size()];
		
		for (int i = 0; i < contractChangeBillColl.size(); i++) {
			ContractChangeBillInfo contractChangeBillInfo = contractChangeBillColl.get(i);
			selectedIdValues.add(contractChangeBillInfo.getId().toString());
			
			for (int j = 0; j < contractChangeBillInfo.getEntrys().size(); j++) {
				contractChangeBillInfo.getEntrys().get(j).setIsAllExe(Boolean.TRUE);
			}
			objectPk[i] = new ObjectUuidPK(contractChangeBillInfo.getId());
			changeBillInfo[i] = contractChangeBillInfo;
		}
		iBill.disPatch(FDCHelper.CollectionToArrayPK(selectedIdValues));
		iBill.visa(objectPk,contractChangeBillColl);
		
		oql = "select contractChange.id where sourceBillId='"+billInfo.getId()+"'";
		ConChangeSplitCollection conChangeSplitColl = IConChangeSplit.getConChangeSplitCollection(oql);
		for (int i = 0; i < conChangeSplitColl.size(); i++) 
			IConChangeSplit.audit(conChangeSplitColl.get(i).getId());
		
		for (int i = 0; i < changeBillInfo.length; i++) {
			ContractChangeBillInfo contractChangeBillInfo = changeBillInfo[i];
			
			BigDecimal seteeAmount = UIRuleUtil.getBigDecimal(contractChangeBillInfo.getOriginalAmount());
			contractChangeBillInfo.setBalanceAmount(seteeAmount);
			contractChangeBillInfo.setOriBalanceAmount(seteeAmount);
			
			BigDecimal balanceAmount =  FDCHelper.ZERO;
			if(contractChangeBillInfo.isHasSettled()){
				balanceAmount = contractChangeBillInfo.getBalanceAmount();
			}else{
				balanceAmount = contractChangeBillInfo.getAmount();
			}
			if (!isConChangeAuditInWF(ctx)) {
				contractChangeBillInfo.setHasSettled(true);
				contractChangeBillInfo.setSettleTime(DateTimeUtils.truncateDate(new Date()));
				
				ConChangeSplitFactory.getLocalInstance(ctx).changeSettle(contractChangeBillInfo);
				ContractChangeBillFactory.getLocalInstance(ctx).changeSettle(contractChangeBillInfo.getId());
				
				ContractChangeBillFactory.getLocalInstance(ctx).submitForWF(contractChangeBillInfo);
			}else{
				iBill.submit(contractChangeBillInfo);
			}
			
			try {
				synContractProgAmt(ctx,balanceAmount, contractChangeBillInfo, true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			/**
			 * 更新合同付款计划
			 * 这尼玛,中断后就脏数据了.不能回滚啊
			 */
			ConPayPlanFactory.getLocalInstance(ctx).importPayPlan(contractChangeBillInfo.getContractBill().getId().toString(), false);
		}
	}
	
	/**
	 * 启用变更结算工作流审批 by Cassiel_peng 2009-8-19
	 */
	private boolean isConChangeAuditInWF(Context ctx) {
		String companyID = ContextUtil.getCurrentCostUnit(ctx).getId().toString();
		boolean retValue=false;
		try {
			 retValue=FDCUtils.getDefaultFDCParamByKey(ctx,companyID, FDCConstants.FDC_PARAM_CHANGESETTAUDIT);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return retValue;
	}
	
	 /**
	  * 当合同未结算时(无最终结算或最终结算未审批)，规划余额=规划金额-（签约金额+变更金额），控制余额=控制金额-签约金额，
	  * 当合同已结算时(最终结算已审批)，规划余额=规划金额-结算金额，控制余额=控制金额-结算金额
    * @return
    * @throws BOSException 
    * @throws EASBizException 
    * @throws SQLException 
    */
	private void synContractProgAmt(Context ctx,BigDecimal oldChangeAmount,ContractChangeBillInfo model,boolean flag) throws EASBizException, BOSException, SQLException{
	  flag = true;
	   //变更结算之后的变更金额
	  BigDecimal newChangeAmount = model.getBalanceAmount();
	  SelectorItemCollection sictc = new SelectorItemCollection();
  	  sictc.add("*");
  	  sictc.add("amount");
  	  ContractChangeBillInfo contractChangeBillInfo = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillInfo(new ObjectUuidPK(model.getId()), sictc);
  	  //变更结算之前的变更金额
	  BigDecimal oldChangeAmt = oldChangeAmount;
	  if(oldChangeAmount == null){
		 oldChangeAmt = contractChangeBillInfo.getAmount();
	  }
  	  BOSUuid contractBillId = model.getContractBill().getId();
  	  SelectorItemCollection sic = new SelectorItemCollection();
  	  sic.add("*");
  	  sic.add("programmingContract.*");
  	  IContractBill serviceCon = null;
  	  IProgrammingContract service = null;
  	  ContractBillInfo contractBillInfo = null;
		//	FDCSQLBuilder builder = null;
  	  serviceCon = ContractBillFactory.getLocalInstance(ctx);
	  service = ProgrammingContractFactory.getLocalInstance(ctx);
		//	builder = new FDCSQLBuilder();
	  contractBillInfo = serviceCon.getContractBillInfo(new ObjectUuidPK(contractBillId.toString()), sic);
	  ProgrammingContractInfo pcInfo = contractBillInfo.getProgrammingContract();
  	  if(pcInfo == null) return;
  	  // 规划余额
	  BigDecimal balanceAmt = pcInfo.getBalance();
	  // 控制余额
	  BigDecimal controlBalanceAmt = pcInfo.getControlBalance();
	  //变更单预算本币金额
	  BigDecimal changeAmount = model.getAmount();
	  //框架合约签约金额
	  BigDecimal changeAmountProg = pcInfo.getChangeAmount();
	  //差额
	  BigDecimal otherChangeAmount = FDCHelper.ZERO;
	  if(flag){
		  pcInfo.setBalance(FDCHelper.subtract(FDCHelper.add(balanceAmt, oldChangeAmt), newChangeAmount));
		  pcInfo.setControlBalance(FDCHelper.subtract(FDCHelper.add(controlBalanceAmt, oldChangeAmt), newChangeAmount));
		  pcInfo.setChangeAmount(FDCHelper.add(FDCHelper.subtract(changeAmountProg, oldChangeAmt), newChangeAmount));
		  otherChangeAmount = FDCHelper.subtract(FDCHelper.add(FDCHelper.subtract(changeAmountProg, oldChangeAmt), newChangeAmount), changeAmountProg);
	  }
  	  SelectorItemCollection sict = new SelectorItemCollection();
  	  sict.add("balance");
  	  sict.add("controlBalance");
	  sict.add("signUpAmount");
	  sict.add("changeAmount");
	  sict.add("settleAmount");
	  sict.add("srcId");
  	  service.updatePartial(pcInfo, sict);
	  //更新其他的合约规划版本金额
	  String progId = pcInfo.getId().toString();
	  while (progId != null) {
		  String nextVersionProgId = getNextVersionProg(ctx,progId);
		  if (nextVersionProgId != null) {
			  pcInfo = service.getProgrammingContractInfo(new ObjectUuidPK(nextVersionProgId), sict);
			  pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(),otherChangeAmount));
			  pcInfo.setControlBalance(FDCHelper.subtract(pcInfo.getControlBalance(),otherChangeAmount));
			  pcInfo.setChangeAmount(FDCHelper.add(pcInfo.getChangeAmount(),otherChangeAmount));
			  service.updatePartial(pcInfo, sict);
			  progId = pcInfo.getId().toString();
		} else {
			  progId = null;
		}
	  }
	}
  
	private  String getNextVersionProg(Context ctx,String nextProgId) throws BOSException, SQLException{
		String tempId = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		IRowSet rowSet = null;
		builder.appendSql(" select fid from t_con_programmingContract where  ");
		builder.appendParam("FSrcId", nextProgId);
		rowSet = builder.executeQuery();
		while(rowSet.next()){
			tempId = rowSet.getString("fid");
		}
		return tempId ;
	}
	
    private void vefySettleAmount(Context ctx,ChangeAuditBillInfo billInfo) throws EASBizException, BOSException {
    	String oql = "where changeAudit='"+billInfo.getId()+"'";
    	IContractChangeBill iBill = ContractChangeBillFactory.getLocalInstance(ctx);
		ContractChangeBillCollection contractChangeBillColl = iBill.getContractChangeBillCollection(oql);
		
		for (int i = 0; i < contractChangeBillColl.size(); i++) {
			ContractChangeBillInfo contractChangeBillInfo = contractChangeBillColl.get(i);
			BigDecimal balanceAmount =  FDCHelper.ZERO;
			if(contractChangeBillInfo.isHasSettled()){
				balanceAmount = contractChangeBillInfo.getBalanceAmount();
			}else{
				balanceAmount = contractChangeBillInfo.getAmount();
			}
	    	String org = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
	    	String paramValue = ParamManager.getParamValue(ctx, new ObjectUuidPK(org), "FDC228_ISSTRICTCONTROL");
		  	//严格控制时校验
		  	if(contractChangeBillInfo.getContractBill()!=null && "0".equals(paramValue)){
		  		BOSUuid id = contractChangeBillInfo.getContractBill().getId();
			  	SelectorItemCollection selector = new SelectorItemCollection();
			  	selector.add("programmingContract.balance");
			  
			  	ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(id), selector);
			  
			  	if(contractBillInfo.getProgrammingContract()!=null && FDCHelper.compareTo(contractChangeBillInfo.getBalanceAmount(), FDCHelper.add(balanceAmount, contractBillInfo.getProgrammingContract().getBalance()))>0){
			  		throw new EASBizException(new NumericExceptionSubItem("100","合同变更结算金额不能大于合约规划的控制余额！"));
			  	}
		  	}
		}
	}
	
	/**
	 * 根据当前项目成本期间更新暂缓单据的业务日期和订立期间
	 * @param ctx
	 * @param billId
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void updatePeriod(Context ctx, BOSUuid billId) throws EASBizException, BOSException {
		
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("isRespite");
		selectors.add("curProject.id");
		selectors.add("curProject.fullOrgUnit.id");
		selectors.add("bookedDate");
		selectors.add("period.*");
		ChangeAuditBillInfo billInfo = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillInfo(new ObjectUuidPK(billId), selectors);
		
		String companyID = billInfo.getCurProject().getFullOrgUnit().getId().toString();
		boolean isInCorporation = FDCUtils.IsInCorporation(ctx, companyID);
		if (isInCorporation) {
			//启用月结统一按以下逻辑处理
			String prjId = billInfo.getCurProject().getId().toString();
			//成本期间
			PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, true);
			PeriodInfo billPeriod = billInfo.getPeriod();
			PeriodInfo shouldPeriod = null;//最终所在期间
			Date bookedDate = DateTimeUtils.truncateDate(billInfo.getBookedDate());
			
			if(finPeriod==null){
				throw new EASBizException(new NumericExceptionSubItem("100","单据所对应的组织没有当前时间的期间，请先设置！"));
			}
			/***************
			 * （1）当工程量确认单上的“业务日期”和“业务期间”大于工程项目成本财务“当前期间”时，“业务期间”不变
			 * （2）当工程量确认单上的“业务日期”和“业务期间”小于等于工程项目成本财务“当前期间”时，“业务期间”更新为工程项目成本财务“当前期间”
			 *	
			 *	原理与拆分保存时相同，期间老出问题
			 */
			if (billPeriod != null && billPeriod.getNumber() > finPeriod.getNumber()) {
				if (bookedDate.before(billPeriod.getBeginDate())) {
					bookedDate = billPeriod.getBeginDate();
				} else if (bookedDate.after(billPeriod.getEndDate())) {
					bookedDate = billPeriod.getEndDate();
				}
				shouldPeriod = billPeriod;
			} else if (finPeriod != null) {
				if (bookedDate.before(finPeriod.getBeginDate())) {
					bookedDate = finPeriod.getBeginDate();
				} else if (bookedDate.after(finPeriod.getEndDate())) {
					bookedDate = finPeriod.getEndDate();
				}
				shouldPeriod = finPeriod;
			}
			
			//更新变更签证申请的业务日期，变更期间和暂缓状态
			
			selectors = new SelectorItemCollection();
			selectors.add("period");
			selectors.add("bookedDate");
			selectors.add("isRespite");
			billInfo.setBookedDate(bookedDate);
			billInfo.setPeriod(shouldPeriod);
			billInfo.setIsRespite(false);
			_updatePartial(ctx, billInfo, selectors);
		}
	}
	
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,EASBizException{
//		ChangeAuditBillInfo billInfo = new ChangeAuditBillInfo();
		ChangeAuditBillInfo billInfo = ChangeAuditBillFactory.getLocalInstance(ctx)
		.getChangeAuditBillInfo(new ObjectUuidPK(billId));
		
		//反审核校验
		checkBillForUnAudit( ctx,billId,billInfo);
		
//		billInfo.setId(billId);
		billInfo.setChangeState(ChangeBillStateEnum.Submit);
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("changeState");
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		int num = billInfo.getSuppEntry().size();
		ChangeSupplierEntryCollection c = billInfo.getSuppEntry();
		if(num>0){
			List list = new ArrayList();
			for(int i=0; i<num; i++){
				ChangeSupplierEntryInfo entry = c.get(i);
				if(entry.getContractChange()!=null){
					ContractChangeBillInfo change = entry.getContractChange();
					
					//检查是否已经被拆分
					FilterInfo filterSett = new FilterInfo();
					filterSett.getFilterItems().add(
							new FilterItemInfo("contractChange.id", change.getId().toString()));
					filterSett.getFilterItems().add(
							new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					boolean hasSettleSplit = false;
					if (ConChangeSplitFactory.getLocalInstance(ctx).exists(filterSett)
							|| ConChangeNoCostSplitFactory.getLocalInstance(ctx)
							.exists(filterSett)) {
						hasSettleSplit = true;
					}
					if(hasSettleSplit){
						throw new ContractChangeException(ContractChangeException.HASSPLIT);
					}
					list.add(change.getId().toString());
//					change.setState(FDCBillStateEnum.SAVED);
//					change.setAuditor(null);
//					change.setAuditTime(null);
//					SelectorItemCollection sele = new SelectorItemCollection();
//					sele.add("auditor");
//					sele.add("auditTime");
//					sele.add("state");
//					ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(change, sele);
				}
			}
			//2008-11-19 改为ContractChangeBill的反审批方法
			if(list!=null && list.size()>0){
				boolean isGenerateAfterAuidt=FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_GENERATEAFTERAUDIT);
				if(!isGenerateAfterAuidt){
					ContractChangeBillFactory.getLocalInstance(ctx).unAudit(list);
				}
			}
		}
		_updatePartial(ctx, billInfo, selector);

//		runBathBill(ctx,billInfo, "UNAUDIT");
	}

	protected void _register4WF(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		ChangeAuditBillInfo billInfo = (ChangeAuditBillInfo)super._getValue(ctx,pk);
		billInfo.setChangeState(ChangeBillStateEnum.Register);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("changeState");
		_updatePartial(ctx, billInfo, selector);
	}

	protected void _disPatch4WF(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		ChangeAuditBillInfo billInfo = (ChangeAuditBillInfo)super._getValue(ctx,pk);
		billInfo.setChangeState(ChangeBillStateEnum.Announce);
		SelectorItemCollection selector = new SelectorItemCollection();
		//TODO 大华，提交状态时，提交下发需要将单据状太改为已审批
		
		selector.add("changeState");
		_updatePartial(ctx, billInfo, selector);
	}

	protected void _aheadDisPatch4WF(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		ChangeAuditBillInfo info = (ChangeAuditBillInfo)super._getValue(ctx,pk);
		if(info.getSuppEntry().size()>0){
    		ChangeBill(ctx,info,FDCBillStateEnum.ANNOUNCE);
    	}
		info.setChangeState(ChangeBillStateEnum.AheadDisPatch);
		//TODO 大华，提交状态时，提交下发需要将单据状太改为已审批
		
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("changeState");
		_updatePartial(ctx, info, selector);	
	}
	
	
    private SelectorItemCollection getSic(){
		// 此过滤为详细信息定义
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("period.id"));
        sic.add(new SelectorItemInfo("period.beginDate"));	
        sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.displayName"));	
        
        return sic;
    }
    
	//提交时校验单据期间不能在工程项目的当前期间之前
    private void checkBillForSubmit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		
		//不能落于当前成本期间之前
    	ChangeAuditBillInfo contractBill = (ChangeAuditBillInfo)model;
		
		String comId = null;
		if( contractBill.getCurProject().getFullOrgUnit()==null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("fullOrgUnit.id");
			CurProjectInfo curProject = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(new ObjectUuidPK(contractBill.getCurProject().getId().toString()),sic);
			
			comId = curProject.getFullOrgUnit().getId().toString();
		}else{
			comId = contractBill.getCurProject().getFullOrgUnit().getId().toString();
		}
		
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,contractBill.getCurProject().getId().toString(),true);
			if(bookedPeriod!=null && contractBill.getPeriod()!=null && contractBill.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate())){
				//单据期间不能在工程项目的当前期间之前 CNTPERIODBEFORE
				throw new ContractException(ContractException.CNTPERIODBEFORE);
			}
		}
		
		
		ChangeSupplierEntryCollection c = contractBill.getSuppEntry();
		for(int i=0;i<c.size();i++){			
			ChangeSupplierEntryInfo entry = c.get(i);
			//entry.getContractBill();
			
			//单据期间不能在合同的当前期间之前
			if(entry.getContractBill()!=null){
		    	// 此过滤为详细信息定义
		        SelectorItemCollection sic = new SelectorItemCollection();
		        sic.add(new SelectorItemInfo("period.beginDate"));	

		        ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(entry.getContractBill().getId().toString()),sic);
				//单据期间不能在合同的当前期间之前
		        //2008-11-26 增加对contractBillInfo.getPeriod()是否为空的判断
				if(contractBill.getPeriod()!=null && contractBillInfo.getPeriod()!=null &&contractBill.getPeriod().getBeginDate()!=null&&
						contractBill.getPeriod().getBeginDate().before(contractBillInfo.getPeriod().getBeginDate())){
					//单据期间不能在合同的当前期间之前 CNTPERIODBEFORE
					throw new ContractException(ContractException.CNTPERIODBEFORECON);
				}
			}
		}
	}
	
	//审核校验
	private void checkBillForAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
    
		ChangeAuditBillInfo model = (ChangeAuditBillInfo)billInfo;

        if(model==null|| model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null){
        	model= this.getChangeAuditBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
        }

		//检查功能是否已经结束初始化
		String comId = model.getCurProject().getFullOrgUnit().getId().toString();
			
		//是否启用财务一体化
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = model.getCurProject().getId().toString();	
			//没有结束初始化
			if(!ProjectPeriodStatusUtil._isClosed(ctx,curProject)){
				throw new ProjectPeriodStatusException(ProjectPeriodStatusException.ISNOT_INIT,new Object[]{model.getCurProject().getDisplayName()});
			}	
			//成本已经月结
			PeriodInfo costPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
			
			if (costPeriod == null) {
				// 工程项目月结状态的期间不能为空。
				throw new ProjectPeriodStatusException(ProjectPeriodStatusException.PERIOD_CNT_EMPTY, new Object[] { model.getCurProject()
						.getDisplayName() });
			}
			
			if(model.getPeriod().getBeginDate().after(costPeriod.getEndDate())){
				throw new  ContractException(ContractException.AUD_AFTERPERIOD,new Object[]{model.getNumber()});
			}
		}
	}
	
	/**
	 * 反审批校验
	 * 
	 * @param ctx
	 * @param billId
	 * @param billInfo
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void checkBillForUnAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {

		checkContractChangeRef(ctx, billId.toString());
		
		ChangeAuditBillInfo model = (ChangeAuditBillInfo)billInfo;

        if(model==null|| model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null){
        	model= this.getChangeAuditBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
        }
		//检查功能是否已经结束初始化
		String comId = model.getCurProject().getFullOrgUnit().getId().toString();
			
		//是否启用财务一体化
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = model.getCurProject().getId().toString();

			//单据期间在工程项目当前期间之前，不能反审核
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
			if(model.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate())){
				throw new  ContractException(ContractException.CNTPERIODBEFORE);
			}	
			
//			if(ProjectPeriodStatusUtil._isEnd(ctx,curProject)){
//				throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_HASEND,new Object[]{model.getCurProject().getDisplayName()});
//			}	
		}
	}
	
	private boolean isGenerateAfterAudit(Context ctx) throws BOSException,
			EASBizException {
		return FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_GENERATEAFTERAUDIT);
	}
	
	/**
	 * 检查是否启用参数FDC222_GENERATEAFTERAUDIT，如果启用，反审批时要检查是否生成了指令单
	 * 
	 * @author owen_wen 2011-03-09
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void checkContractChangeRef(Context ctx, String billId) throws BOSException, EASBizException {
		// R090827-093:如果参数"指令单是否必须再审批单审批后才能生成"启用，那么用户要反审批审批单，则必须要先删除指令单 by
		// Cassiel_peng 2009-9-24
		boolean isGenerateAfterAuidt = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_GENERATEAFTERAUDIT);
		if (isGenerateAfterAuidt) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("changeAudit.id", billId));
			if (ContractChangeBillFactory.getLocalInstance(ctx).exists(filter)) {
				throw new EASBizException(new NumericExceptionSubItem("001", "进行此操作之前请先删除对应的变更签证确认！"));
			}
		}
	}
	
	
	/**
	 * 框架合约之规划余额
	 * 
	 * @param contractIdSet
	 * @return
	 */
	private String getConAmountSql(Set contractIdSet) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT CB.FID CONID, CB.FNUMBER CONNUMBER, PC.FID PROID                       \n");
		sql.append(" ,ISNULL(SUM(PC.FBalance), 0) AMOUNT FROM T_CON_ProgrammingContract PC        \n");
		sql.append("	INNER JOIN T_CON_ContractBill CB ON CB.FSrcProID = PC.FID                 \n");
		sql.append("	WHERE CB.FID IN ").append(FDCUtils.buildBillIds(contractIdSet)).append("  \n");
		sql.append(" GROUP BY CB.FID, CB.FNUMBER, PC.FID");
		return sql.toString();
	}

	protected Object _checkAmount(Context ctx, IObjectPK pk, Map contractMap) throws BOSException, EASBizException {
		StringBuffer result = new StringBuffer();
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx, getConAmountSql(contractMap.keySet()));
		IRowSet rs = fdcSB.executeQuery();

		Map pcMappingMap = new HashMap();
		Map conDetailMap = new HashMap(); // key: 合同 id -- value: 合同 number

		try {
			while (rs.next()) {
				String proId = rs.getString("PROID");
				String conId = rs.getString("CONID");
				BigDecimal proAmount = rs.getBigDecimal("AMOUNT"); // 框架合约规划余额
				String conNumber = rs.getString("CONNUMBER");
				conDetailMap.put(conId, conNumber);

				// 同一框架合约下合同测算金额进行汇总
				ProConMapping mapping = (ProConMapping) pcMappingMap.get(proId);
				if (mapping == null) {
					Set conSet = new HashSet();
					conSet.add(conId);
					mapping = new ProConMapping(proId, conSet, proAmount);
					pcMappingMap.put(proId, mapping);
				} else {
					mapping.conSet.add(conId);
				}

				// 变更签证申请合同测算金额
				BigDecimal conBudgetAmount = (BigDecimal) contractMap.get(conId);
				if (conBudgetAmount != null) {
					mapping.conBudgetAmountTotal = conBudgetAmount.add(mapping.conBudgetAmountTotal);
				}
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}

		for (Iterator it = pcMappingMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			ProConMapping mapping = (ProConMapping) entry.getValue();
			if (!mapping.isCheckAmountPass()) {
				result.append("合同 ");
				for (Iterator it2 = mapping.conSet.iterator(); it2.hasNext();) {
					String conId = (String) it2.next();
					String conNumber = (String) conDetailMap.get(conId);
					result.append(" [").append(conNumber).append("] ");
				}
				result.append("\n变更测算金额大于框架合约的规划余额，是否提交?\n\n");
			}
		}

		return result.toString();
	}

	/**
	 * 合同与框架合约映射
	 * 
	 * @author yufan_yang
	 * 
	 */
	class ProConMapping {
		String proId;
		Set conSet;
		BigDecimal proBalanceAmount; // 框架合约规划余额
		BigDecimal conBudgetAmountTotal; // 框架下合同测算金额累计

		ProConMapping(String _proId, Set _conSet, BigDecimal _proBalanceAmount) {
			proId = _proId;
			conSet = _conSet;
			proBalanceAmount = _proBalanceAmount == null ? FDCHelper.ZERO : _proBalanceAmount;
			conBudgetAmountTotal = FDCHelper.ZERO;
		}

		boolean isCheckAmountPass() {
			return proBalanceAmount.compareTo(conBudgetAmountTotal) >= 0;
		}

		void meger(ProConMapping p) {
			conSet.addAll(p.conSet);
		}

		public int hashCode() {
			return proId == null ? "".hashCode() : proId.hashCode();
		}

		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof ProConMapping)) {
				return false;
			}
			return proId.equals(((ProConMapping) obj).proId);
		}
	}
}