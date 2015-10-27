package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitException;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCostSplit;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSplitBillInfo;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICostAccountWithAccount;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.ISettlementCostSplit;
import com.kingdee.eas.fdc.contract.ISettlementCostSplitEntry;
import com.kingdee.eas.fdc.contract.SettlementCostSplitCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitException;
import com.kingdee.eas.framework.AbstractBillEntryBaseInfo;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.util.app.ContextUtil;

public class SettlementCostSplitControllerBean extends AbstractSettlementCostSplitControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.SettlementCostSplitControllerBean");
    
    protected String _getLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        SettlementCostSplitInfo info = (SettlementCostSplitInfo)super._getValue(ctx,pk);
        String retValue = "";
        if(info.getSettlementBill()!= null)
        {
        	String id = info.getSettlementBill().getId().toString();
        	ContractSettlementBillInfo test = ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillInfo(new ObjectUuidPK(BOSUuid.read(id)));	
	        if(test.getNumber()!=null){
        		retValue = test.getNumber();
	            if(test.getName()!=null){
	            	retValue = retValue + " " + test.getName();
	            }
	        }
        }
        return retValue;
    }
    
    protected void _audit(Context ctx, BOSUuid billId)throws BOSException, EASBizException
    {
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("id", billId.toString());
		view.getSelector().add("splitState");
		view.getSelector().add("settlementBill.state");
		SettlementCostSplitCollection c = getSettlementCostSplitCollection(ctx, view);
		if(c.size()!=1){
			throw new EASBizException(EASBizException.CHECKEXIST); 
		}else{
			if(c.get(0).getSettlementBill().getState()!=FDCBillStateEnum.AUDITTED){
				throw new CostSplitException(CostSplitException.CONNOTAUDIT);
			}else if(c.get(0).getSplitState()!=CostSplitStateEnum.ALLSPLIT){
				throw new CostSplitException(CostSplitException.PARTSPLIT);
			}else{
				super._audit(ctx, billId);
			}
		}
		
	}
    
    
    protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
    		throws BOSException, EASBizException {
    	super._submit(ctx, pk, model);
    	updateIS(ctx, new ObjectUuidPK(pk.toString()));
    }
    
    protected void _unAudit(Context ctx, BOSUuid billId)throws BOSException, EASBizException
    {
    	super._unAudit(ctx, billId);
    }
    protected void _audit(Context ctx, List idList)throws BOSException, EASBizException
    {
    	super._audit(ctx, idList);
    }
    protected void _unAudit(Context ctx, List idList)throws BOSException, EASBizException
    {
    	super._unAudit(ctx, idList);
    }
    
    protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException
    {
       	SettlementCostSplitInfo splitBill=(SettlementCostSplitInfo)model;				
		collectCostSplit(ctx, CostSplitBillTypeEnum.SETTLEMENTSPLIT, splitBill.getSettlementBill(), splitBill.getId(), splitBill.getEntrys());
    	super._save(ctx, pk, model);
    }

    protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	SettlementCostSplitInfo fDCBillInfo = ((SettlementCostSplitInfo) model);
    	if(fDCBillInfo.getCreateTime()!=null){
    		fDCBillInfo.getCreateTime().setNanos(0);
    	}
    	fDCBillInfo.setIslastVerThisPeriod(true);
		IObjectPK pk=super._save(ctx, fDCBillInfo);
		SettlementCostSplitInfo splitBillInfo=null;
		SelectorItemCollection selectorGet = new SelectorItemCollection();
		selectorGet.add("id");
		selectorGet.add("settlementBill.id");
		selectorGet.add("settlementBill.CU.id");
		selectorGet.add("settlementBill.contractBill.curProject.id");
		selectorGet.add("SettlementBill.period.number");
		selectorGet.add("period.id");
		selectorGet.add("period.number");
		selectorGet.add("splitState");
		splitBillInfo = super.getSettlementCostSplitInfo(ctx, pk, selectorGet);
		String prjID = splitBillInfo.getSettlementBill().getContractBill().getCurProject().getId().toString();
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx,prjID, true);
		if(currentPeriod!=null){
			PeriodInfo contractPeriod = splitBillInfo.getSettlementBill().getPeriod();
			if(contractPeriod!=null&&contractPeriod.getNumber()>currentPeriod.getNumber()){
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder
						.appendSql("update T_CON_SettlementCostSplit set FPeriodID=? where fid=?");
				builder.addParam(contractPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();	
				
				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_CON_SettlementCostSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fsettlementbillid=?");
				builder.addParam(contractPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.addParam(splitBillInfo.getSettlementBill().getId().toString());
				builder.executeUpdate();
			}else{
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder
						.appendSql("update T_CON_SettlementCostSplit set FPeriodID=? where fid=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();
				
				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_CON_SettlementCostSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fsettlementbillid=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.addParam(splitBillInfo.getSettlementBill().getId().toString());
				builder.executeUpdate();
			}
		}else if(splitBillInfo.getPeriod()!=null){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.clear();
			builder.appendSql("update T_CON_SettlementCostSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fsettlementbillid=?");
			builder.addParam(splitBillInfo.getPeriod().getId().toString());
			builder.addParam(splitBillInfo.getId().toString());
			builder.addParam(splitBillInfo.getSettlementBill().getId().toString());
			builder.executeUpdate();
		}
		
		
		//处理拆分汇总
    	SettlementCostSplitInfo splitBill=(SettlementCostSplitInfo)model;	
		splitBill.setId(BOSUuid.read(pk.toString()));
		updateEntrySeq(ctx,splitBill);
		BOSUuid costBillId=splitBill.getSettlementBill().getId();
		collectCostSplit(ctx, CostSplitBillTypeEnum.SETTLEMENTSPLIT, splitBillInfo.getSettlementBill(), splitBill.getId(), splitBill.getEntrys());
		//更新结算单的拆分状态
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("update T_Con_ContractSettlementBill set FSplitState=? where fid=?");
		builder.addParam(splitBill.getSplitState().getValue());
		builder.addParam(splitBill.getSettlementBill().getId().toString());
		builder.execute();
		
		//驱动拆分工作流
		// ContractSettlementBillFactory.getLocalInstance(ctx).split(new ObjectUuidPK(costBillId));	// modified by zhaoqin for R130812-0006	
		
		
		
		//TODO 暂时这样玩
		updateIS(ctx, new ObjectUuidPK(pk.toString()));
		
		return pk;
	}
    
    private void updateIS(Context ctx,ObjectUuidPK pk) throws EASBizException, BOSException{
    	ISettlementCostSplit ISettlementCostSplit = SettlementCostSplitFactory.getLocalInstance(ctx);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("contractBill.curProject.isWholeAgeStage"); 
		sic.add("contractBill.programmingContract.id"); 
		sic.add("entrys.product.id"); 
		sic.add("entrys.costAccount.curProject.id"); 
		sic.add("isCostSplit"); 
		sic.add("isTeamSplit"); 
		sic.add("isProductSplit"); 
		SettlementCostSplitInfo conCostSplitInfo = ISettlementCostSplit.getSettlementCostSplitInfo(pk, sic);
		
		Set<String> curProjectSet = new HashSet<String>();
		Set<String> productSet = new HashSet<String>();
		for (int i = 0; i <conCostSplitInfo.getEntrys().size(); i++) {
			SettlementCostSplitEntryInfo entryInfo = conCostSplitInfo.getEntrys().get(i);
			if(entryInfo.getCostAccount()==null||entryInfo.getCostAccount().getCurProject()==null)
				continue;
			curProjectSet.add(entryInfo.getCostAccount().getCurProject().getId().toString());
			if(entryInfo.getProduct()==null)
				continue;
			productSet.add(entryInfo.getProduct().getId().toString());
		}
		conCostSplitInfo.setIsCostSplit(conCostSplitInfo.getEntrys().size()>=2?true:false);
		conCostSplitInfo.setIsTeamSplit(curProjectSet.size()>=2?true:false);
		conCostSplitInfo.setIsProductSplit(productSet.size()>0?true:false);
		ISettlementCostSplit.updatePartial(conCostSplitInfo, sic);
    }
    /**
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#_submit(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectValue)
	 */
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		SettlementCostSplitInfo fDCBillInfo = ((SettlementCostSplitInfo) model);
		if (fDCBillInfo.getCreateTime() != null) {
			fDCBillInfo.getCreateTime().setNanos(0);
		}
		fDCBillInfo.setIslastVerThisPeriod(true);
		IObjectPK pk = super._submit(ctx, fDCBillInfo);
		SettlementCostSplitInfo splitBillInfo = null;
		SelectorItemCollection selectorGet = new SelectorItemCollection();
		selectorGet.add("id");
		selectorGet.add("settlementBill.id");
		selectorGet.add("settlementBill.CU.id");
		selectorGet.add("settlementBill.contractBill.curProject.id");
		selectorGet.add("SettlementBill.period.number");
		selectorGet.add("period.id");
		selectorGet.add("period.number");
		selectorGet.add("splitState");
		splitBillInfo = super.getSettlementCostSplitInfo(ctx, pk, selectorGet);
		String prjID = splitBillInfo.getSettlementBill().getContractBill().getCurProject().getId().toString();
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, prjID, true);
		if (currentPeriod != null) {
			PeriodInfo contractPeriod = splitBillInfo.getSettlementBill().getPeriod();
			if (contractPeriod != null && contractPeriod.getNumber() > currentPeriod.getNumber()) {
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.appendSql("update T_CON_SettlementCostSplit set FPeriodID=? where fid=?");
				builder.addParam(contractPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();

				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder
						.appendSql("update T_CON_SettlementCostSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fsettlementbillid=?");
				builder.addParam(contractPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.addParam(splitBillInfo.getSettlementBill().getId().toString());
				builder.executeUpdate();
			} else {
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.appendSql("update T_CON_SettlementCostSplit set FPeriodID=? where fid=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();

				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder
						.appendSql("update T_CON_SettlementCostSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fsettlementbillid=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.addParam(splitBillInfo.getSettlementBill().getId().toString());
				builder.executeUpdate();
			}
		} else if (splitBillInfo.getPeriod() != null) {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.clear();
			builder
					.appendSql("update T_CON_SettlementCostSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and Fsettlementbillid=?");
			builder.addParam(splitBillInfo.getPeriod().getId().toString());
			builder.addParam(splitBillInfo.getId().toString());
			builder.addParam(splitBillInfo.getSettlementBill().getId().toString());
			builder.executeUpdate();
		}

		//处理拆分汇总
		SettlementCostSplitInfo splitBill = (SettlementCostSplitInfo) model;
		splitBill.setId(BOSUuid.read(pk.toString()));
		updateEntrySeq(ctx, splitBill);
		BOSUuid costBillId = splitBill.getSettlementBill().getId();
		collectCostSplit(ctx, CostSplitBillTypeEnum.SETTLEMENTSPLIT, splitBillInfo.getSettlementBill(), splitBill.getId(), splitBill
				.getEntrys());
		//更新结算单的拆分状态
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_Con_ContractSettlementBill set FSplitState=? where fid=?");
		builder.addParam(splitBill.getSplitState().getValue());
		builder.addParam(splitBill.getSettlementBill().getId().toString());
		builder.execute();

		//驱动拆分工作流
		ContractSettlementBillFactory.getLocalInstance(ctx).split(new ObjectUuidPK(costBillId));
		
		updateIS(ctx, new ObjectUuidPK(pk.toString()));
		return pk;
	}
    protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException
    {
    	//做重复性检查
    	checkCostSplitDup(ctx, model);
    	return super._addnew(ctx, model);
    }
	private void checkCostSplitDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		SettlementCostSplitInfo billInfo=(SettlementCostSplitInfo)model;
		FilterInfo filter = new FilterInfo();
		//合同
		filter.getFilterItems().add(new FilterItemInfo("settlementBill.id", billInfo.getSettlementBill().getId().toString()));		
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));				
		//非当前拆分
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", billInfo.getId().toString(),CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new CostSplitException(CostSplitException.COSTSPLIT_DUP);
		}
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException
	{
		if(arrayPK==null||arrayPK.length==0){
			return;
		}
		Set idSet=new HashSet();
		for(int i=0;i<arrayPK.length;i++){
			idSet.add(arrayPK[i].toString());
		}
    	EntityViewInfo view=new EntityViewInfo();
    	view.setFilter(new FilterInfo());
    	view.getFilter().getFilterItems().add(new FilterItemInfo("id",idSet,CompareType.INCLUDE));
		SelectorItemCollection selector=new SelectorItemCollection();
    	selector.add("settlementBill.id");
    	
    	SettlementCostSplitCollection splits=getSettlementCostSplitCollection(ctx, view);
    	idSet.clear();
		for(int i=0;i<splits.size();i++){
			SettlementCostSplitInfo splitBill=splits.get(i);				
			BOSUuid costBillId=splitBill.getSettlementBill().getId();
			idSet.add(costBillId.toString());
			deleteCostSplit(ctx, CostSplitBillTypeEnum.SETTLEMENTSPLIT, costBillId);
		}
		//更新拆分状态
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("update T_Con_ContractSettlementBill set FSplitState=? where ");
		builder.addParam(CostSplitStateEnum.NOSPLIT_VALUE);
		builder.appendParam("fid", idSet.toArray());
		builder.execute();
		super._delete(ctx, arrayPK);
	}

	protected void _traceData(Context ctx, List idList) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		int size = idList.size();
		for (int i = 0; i < size; i++) {
			String id = idList.get(i).toString();
			setAccoutForSplit(ctx, id);
		}
	}
	private void getAccForEntry(SettlementCostSplitEntryInfo info,
			CostAccountInfo costAcc,
			ICostAccountWithAccount iCostAccountWithAccount,
			CostAccountWithAccountCollection entryColl,
			CostAccountWithAccountInfo entryInfo,
			ISettlementCostSplitEntry iSettlementCostSplitEntry, ICostAccount iCostAccount)
			throws EASBizException, BOSException {
		EntityViewInfo entryView = new EntityViewInfo();
		FilterInfo entryFilter = new FilterInfo();
		entryFilter.getFilterItems().add(
				new FilterItemInfo("costAccount.id", costAcc.getId()));
		if (!iCostAccountWithAccount.exists(entryFilter)) {
			if (costAcc.getLevel() == 1) {
				throw new PaymentSplitException(
						PaymentSplitException.NO_ACCOUNT);
			} else {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("id");
				selector.add("level");
				selector.add("parent.*");
				selector.add("curProject.name");
				selector.add("fullOrgUnit.name");
				if (costAcc.getParent()!=null&&costAcc.getParent().getId() != null) {
					CostAccountInfo parent = iCostAccount.getCostAccountInfo(
							new ObjectUuidPK(costAcc.getParent().getId()),
							selector);
					getAccForEntry(info, parent, iCostAccountWithAccount,
							entryColl, entryInfo, iSettlementCostSplitEntry,
							iCostAccount);
				} else {
					throw new PaymentSplitException(
							PaymentSplitException.ACCOUNT_WRONG);
				}
			}
		}
		
		entryView.setFilter(entryFilter);
		entryColl = iCostAccountWithAccount
				.getCostAccountWithAccountCollection(entryView);
		if (entryColl.size() == 1) {
			entryInfo = entryColl.get(0);
			if (entryInfo.getAccount() != null) {
				info.setAccountView(entryInfo.getAccount());
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("accountView");
				iSettlementCostSplitEntry.updatePartial(info, selector);
			}
		}
	}
	private void setAccoutForSplit(Context ctx, String id)
			throws EASBizException, BOSException {
		ICostAccountWithAccount iCostAccountWithAccount = CostAccountWithAccountFactory
				.getLocalInstance(ctx);
		ISettlementCostSplitEntry iSettlementCostSplitEntry = SettlementCostSplitEntryFactory
				.getLocalInstance(ctx);
		SettlementCostSplitEntryCollection coll;
		CostAccountWithAccountCollection entryColl = null;
		CostAccountWithAccountInfo entryInfo = new CostAccountWithAccountInfo();
		ICostAccount iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id", id));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		view.setFilter(filter);
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("seq"));
		view.getSelector().add(new SelectorItemInfo("costAccount.*"));
		view.getSelector().add(new SelectorItemInfo("costAccount.parent.id"));
		view.getSelector().add(new SelectorItemInfo("accountView.*"));
		coll = iSettlementCostSplitEntry.getSettlementCostSplitEntryCollection(view);
		int entrySize = coll.size();
		for (int j = 0; j < entrySize; j++) {
			SettlementCostSplitEntryInfo info = coll.get(j);
			CostAccountInfo costAcc = info.getCostAccount();
			getAccForEntry(info, costAcc, iCostAccountWithAccount, entryColl,
					entryInfo, iSettlementCostSplitEntry, iCostAccount);
		}
	}

	protected void _afterVoucher(Context ctx, IObjectCollection sourceBillCollection) throws BOSException, EASBizException {
		
	}
	
	private void checkBeforeSplit(Context ctx,ContractSettlementBillInfo billInfo) throws BOSException, EASBizException{
		
	}
	protected void _autoSplit4(Context ctx, BOSUuid billID) throws BOSException, EASBizException {
		BigDecimal totalAmout = FDCHelper.ZERO;
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("contractBill.isCoseSplit");
		selector.add("isFinalSettle");
		ContractSettlementBillInfo billInfo = ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillInfo(new ObjectUuidPK(billID), selector);
		checkBeforeSplit(ctx, billInfo);
		// 变更结算
//		if (billInfo!=null&&BooleanEnum.TRUE.equals(billInfo.getIsFinalSettle())&&billInfo.getContractBill()!=null&&billInfo.getContractBill().isIsCoseSplit()) {
//			FDCBillFacadeFactory.getLocalInstance(ctx).autoChangeSettle(billID.toString(), true);
//		}
		SettlementCostSplitInfo info = (SettlementCostSplitInfo)createNewData(ctx,billID);
		importCostSplit(ctx,billInfo,info);
		
		for (Iterator iter = info.getEntrys().iterator(); iter.hasNext();) {
			FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo) iter.next();
			if (entry.getLevel() == 0) {
				totalAmout = FDCHelper.add(totalAmout, entry.get("contractAmt"));
				totalAmout = FDCHelper.add(totalAmout, entry.get("changeAmt"));
			}
		}
		if (FDCHelper.compareTo(totalAmout, FDCHelper.ZERO) > 0) {
			for (Iterator iter = info.getEntrys().iterator(); iter.hasNext();) {
				FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo) iter.next();
				// modified by zhaoqin on 2013/10/28, 增加归属金额的精度
				entry.setAmount(FDCHelper.divide(FDCHelper.multiply(entry.getAmount(), info.getAmount()), totalAmout, 10,
						BigDecimal.ROUND_HALF_UP));
			}
		}
		afterImport(ctx,info);
		FDCSplitBillEntryCollection colls = new FDCSplitBillEntryCollection();
		BigDecimal contractAmt = FDCHelper.ZERO;
		BigDecimal changeAmt = FDCHelper.ZERO;
		BigDecimal grtSplitAmt = FDCHelper.ZERO;
		BigDecimal amount = FDCHelper.ZERO;
		BigDecimal splitScale = FDCHelper.ZERO;
		SettlementCostSplitEntryCollection entrys = (SettlementCostSplitEntryCollection)info.getEntrys().cast(SettlementCostSplitEntryCollection.class);
		
		for(Iterator iter=entrys.iterator();iter.hasNext();){
			SettlementCostSplitEntryInfo entry = (SettlementCostSplitEntryInfo)iter.next();
			if (entry.getLevel() == 0) { 
				if (entry.getContractAmt() instanceof BigDecimal) {
					contractAmt = contractAmt.add(entry
							.getContractAmt());
				}
				if (entry.getChangeAmt() instanceof BigDecimal) {
					changeAmt = changeAmt.add(entry.getChangeAmt());
				}
				if (entry.getGrtSplitAmt() != null) {
					grtSplitAmt = grtSplitAmt.add(entry
							.getGrtSplitAmt());
				}
				if (entry.getAmount() != null) {
					amount = amount.add(entry.getAmount());
				}
				if (entry.getSplitScale()!=null){
					splitScale=splitScale.add(entry.getSplitScale());
				}
			}
			colls.add(entry);
		}
		apptAmount(ctx, info, colls);
		//汇总分录
		SettlementCostSplitEntryInfo firstEntry = new SettlementCostSplitEntryInfo();
		firstEntry.setId(BOSUuid.create(firstEntry.getBOSType()));
		firstEntry.setSeq(1);
		firstEntry.setIndex(1);
		firstEntry.setLevel(-1000);
		firstEntry.setParent(info);
		firstEntry.setContractAmt(contractAmt);
		firstEntry.setChangeAmt(changeAmt);
		firstEntry.setGrtSplitAmt(grtSplitAmt);
		firstEntry.setAmount(amount);
		firstEntry.setSplitScale(splitScale);
		
		info.getEntrys().clear();
		info.getEntrys().add(firstEntry);
		SettlementCostSplitEntryCollection newEntrys = new SettlementCostSplitEntryCollection();
		for(Iterator iter=colls.iterator();iter.hasNext();){
			SettlementCostSplitEntryInfo newEntry = (SettlementCostSplitEntryInfo)iter.next();
			newEntrys.add(newEntry);
			BigDecimal grtAmt = FDCHelper.divide(FDCHelper.multiply(newEntry.getAmount(), info.getSettlementBill().getQualityGuarante()), info.getSettlementBill().getSettlePrice(), 10,BigDecimal.ROUND_HALF_EVEN);
			newEntry.setGrtSplitAmt(grtAmt);
		}
		info.getEntrys().addCollection(newEntrys);
		if(info.getEntrys()!=null&&info.getEntrys().size()>0){
			_save(ctx, info);
		}
	}
	private void afterImport(Context ctx,SettlementCostSplitInfo info) throws BOSException, EASBizException{
		boolean isOneSplit = true;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id",info.getContractBill().getId().toString()));
		isOneSplit = !ConChangeSplitFactory.getLocalInstance(ctx).exists(filter);
		boolean isFinalSettle = BooleanEnum.TRUE.equals(info.getSettlementBill().getIsFinalSettle());
		
		for (int i = 0; i < info.getEntrys().size(); i++) {
			SettlementCostSplitEntryInfo entry = info.getEntrys().get(i);
			BigDecimal amount = entry.getAmount();
			BigDecimal splitScale = FDCHelper.ZERO;
			BigDecimal grtAmt = FDCHelper.ZERO;

			if (entry.getLevel() == 0) {
				if (isOneSplit) {
					// modified by zhaoqin on 2013/10/28, 增加归属金额的精度
					amount = FDCHelper.divide(FDCHelper.multiply(info.getAmount(), entry.getSplitScale()), FDCHelper.ONE_HUNDRED, 10, BigDecimal.ROUND_HALF_UP);
					entry.setAmount(amount);
				} else if(!isFinalSettle){
					// modified by zhaoqin on 2013/10/28, 增加归属金额的精度
					amount = FDCHelper.divide(FDCHelper.multiply(info.getAmount(), entry.getSplitScale()), FDCHelper.ONE_HUNDRED, 10, BigDecimal.ROUND_HALF_UP);
					entry.setAmount(amount);
				}else {
					if (FDCHelper.toBigDecimal(info.getAmount()).compareTo(FDCHelper.ZERO) != 0) {
						splitScale = FDCHelper.divide(FDCHelper.multiply(amount, FDCHelper.ONE_HUNDRED), info.getAmount(), 10, BigDecimal.ROUND_HALF_UP);
					}
					entry.setSplitScale(splitScale);
				}
			} else {
				entry.setSplitScale(null);
			}
			grtAmt = FDCHelper.divide(FDCHelper.multiply(entry.getAmount(), info.getSettlementBill().getQualityGuarante()), info.getSettlementBill().getSettlePrice(), 10, BigDecimal.ROUND_HALF_EVEN);
			entry.setGrtSplitAmt(grtAmt);

		}
	}
	protected void importCostSplit(Context ctx,FDCBillInfo costBillInfo,FDCSplitBillInfo splitBillInfo) throws BOSException, EASBizException {
		importContractCostSplist(ctx,splitBillInfo);
		//判断条件出错导致未加载变更
		//if(BooleanEnum.TRUE.equals(costBillInfo.get("isFinalSettle"))){
		if(new Integer("1").equals(costBillInfo.get("isFinalSettle"))){
			importChangeCostSplit(ctx, splitBillInfo);
		}
	}
	private int groupIndex = 1;	
	protected void loadCostSplit(Context ctx,CostSplitBillTypeEnum costBillType,FDCSplitBillInfo info,
			SettlementCostSplitEntryCollection entrys) {
		SettlementCostSplitInfo splitInfo = (SettlementCostSplitInfo)info;
		SettlementCostSplitEntryInfo entry=null;
		
		if (costBillType.equals(CostSplitBillTypeEnum.CONTRACTSPLIT)) {
			// contractAmt
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				entry = (SettlementCostSplitEntryInfo) iter.next();
				Object amount = entry.get("amount");
				entry.put("contractAmt", amount);
				entry.put("apportionValue", amount);
				entry.put("apportionValueTotal", amount);
				entry.put("otherRatioTotal", amount);
				if (entry.getLevel() >= 0) {
					groupIndex++;				
				}
				entry.setIndex(groupIndex);		
				splitInfo.getEntrys().add(entry);
			}
		} else if (costBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)) {
			// changeAmt
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				entry = (SettlementCostSplitEntryInfo) iter.next();
				Object amount = entry.get("amount");
				entry.put("changeAmt", amount);
				entry.put("apportionValue", amount);
				entry.put("apportionValueTotal", amount);
				entry.put("otherRatioTotal", amount);
				if (entry.getLevel() >= 0) {
					groupIndex++;				
				}
				entry.setIndex(groupIndex);		
				splitInfo.getEntrys().add(entry);
			}
		}
	}
	protected SettlementCostSplitEntryInfo createNewDetailData(Context ctx) throws BOSException, EASBizException{
		return new SettlementCostSplitEntryInfo();
	}
	protected FDCSplitBillInfo createNewData(Context ctx, BOSUuid billID) throws BOSException, EASBizException {
		SettlementCostSplitInfo objectValue = new SettlementCostSplitInfo();
		objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo) (ContextUtil.getCurrentUserInfo(ctx)));
		objectValue.setCreateTime(new Timestamp(System.currentTimeMillis()));
		objectValue.setIsInvalid(false);
		String costBillID = billID.toString();
		ContractSettlementBillInfo settlementBillInfo = null;

		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
		selectors.add("name");
		selectors.add("settlePrice");
		selectors.add("isFinalSettle");
		selectors.add("contractBill.id");
		selectors.add("contractBill.amount");
		selectors.add("curProject.id");
		selectors.add("qualityGuarante");
		selectors.add("contractBill.isMeasureContract");
		settlementBillInfo = ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillInfo(new ObjectUuidPK(BOSUuid.read(costBillID)), selectors);

		objectValue.setSettlementBill(settlementBillInfo);
		if (settlementBillInfo.getContractBill() != null) {
			objectValue.setContractBill(settlementBillInfo.getContractBill());
		}
		if (settlementBillInfo.getCurProject() != null) {
			objectValue.setCurProject(settlementBillInfo.getCurProject());
		}
		objectValue.setAmount(settlementBillInfo.getSettlePrice());
		objectValue.setIsInvalid(false);
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
		objectValue.setSplitState(CostSplitStateEnum.ALLSPLIT);
		return objectValue;
	}
	protected void importContractCostSplist(Context ctx, FDCSplitBillInfo info) throws BOSException, EASBizException{
		loadCostSplit(ctx,CostSplitBillTypeEnum.CONTRACTSPLIT,info,
				getCostSplitEntryCollection(ctx,
						CostSplitBillTypeEnum.CONTRACTSPLIT,info.getContractBill().getId(), info.getId()));
	}
	private void importChangeCostSplit(Context ctx, FDCSplitBillInfo info) throws BOSException, EASBizException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractChange.contractBill.id",
						info.getContractBill().getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("state",
						FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
		view.setFilter(filter);
		SelectorItemCollection sic = view.getSelector();
		sic.add(new SelectorItemInfo("contractChange.contractBill.id"));

		ConChangeSplitCollection coll = null;
			coll = ConChangeSplitFactory.getLocalInstance(ctx)
					.getConChangeSplitCollection(view);

		if (coll == null || coll.size() == 0) {
			return;
		}

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			ConChangeSplitInfo item = (ConChangeSplitInfo) iter.next();
			loadCostSplit(ctx,CostSplitBillTypeEnum.CNTRCHANGESPLIT,info,
					getCostSplitEntryCollection(ctx,
							CostSplitBillTypeEnum.CNTRCHANGESPLIT, item.getContractChange().getId(),info.getId()));
		}

	}
	protected SelectorItemCollection setSelectorsEntry(Context ctx,SelectorItemCollection sic, boolean isEntry) throws BOSException, EASBizException{
		if(fdcCostSplit==null){
			fdcCostSplit=new FDCCostSplit(ctx);
		}
		return fdcCostSplit.setSelectorsEntry(sic, isEntry);
	}
	
	/**
	 * 描述：分摊金额（调用FDCCostSplit接口）
	 * @return
	 */
    protected FDCSplitBillEntryCollection apptAmount(Context ctx,SettlementCostSplitInfo info, FDCSplitBillEntryCollection entrys){
    	Map amtMap = new HashMap();
    	//用以计算产品占科目的比例
    	for(Iterator iter=entrys.iterator();iter.hasNext();){
    		FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo)iter.next();
    		if(entry.getProduct()==null){
    			String key = entry.getCostAccount().getId().toString();
    			if(amtMap.containsKey(key)){
    				amtMap.put(key, FDCHelper.add(amtMap.get(key),entry.getAmount()));
    			}else{
    				amtMap.put(key, entry.getAmount());
    			}
    		}
    		
    	}
    	
    	BigDecimal amount = FDCHelper.ZERO;
    	BigDecimal splitScale = FDCHelper.ZERO;
    	FDCSplitBillEntryInfo topEntry = null;
    	FDCSplitBillEntryInfo acctEntry = null;
//    	for(Iterator iter=entrys.iterator();iter.hasNext();){
//    		topEntry = (FDCSplitBillEntryInfo)iter.next();
//	    	if(topEntry.getLevel()!=0){
//	    		BigDecimal acctAmount = FDCHelper.toBigDecimal(amtMap.get(topEntry.getCostAccount().getId().toString()));
//	    		amount = FDCHelper.divide(FDCHelper.multiply(topEntry.getAmount(), acctEntry.getAmount()),acctAmount,2,BigDecimal.ROUND_HALF_UP);
//	    		topEntry.setDirectAmount(amount);
//	    		topEntry.setDirectAmountTotal(acctAmount);
//	    	}else{
//	    		amount = FDCHelper.multiply(topEntry.getSplitScale(), info.getAmount()).divide(FDCHelper.ONE_HUNDRED,2,BigDecimal.ROUND_HALF_UP);
//	    		
//	    		acctEntry=topEntry;
//	    		topEntry.setSplitScale(FDCHelper.divide(amount.multiply(FDCHelper.ONE_HUNDRED), info.getAmount(),10,BigDecimal.ROUND_HALF_UP));
//	    		topEntry.setDirectAmount(amount);
//	    		topEntry.setDirectAmountTotal(amount);
//	    	}
//    		topEntry.setAmount(amount);	
//    	}
    	for(Iterator iter=entrys.iterator();iter.hasNext();){
    		topEntry = (FDCSplitBillEntryInfo)iter.next();
	    	
			fdcCostSplit.apptAmount(entrys,topEntry);
    	}
    	return entrys;
    }
	/**
	 * 通过传入的拆分单据的类型(变更拆分，结算拆分等)及对应的合同ID得到拆分分录
	 * @param splitBillType	
	 * @param costBillUuId	拆分单据的CostBillUuid，如结算BOSUuid，变更BOSUuid等
	 * @return
	 */
	protected SettlementCostSplitEntryCollection getCostSplitEntryCollection(Context ctx,CostSplitBillTypeEnum splitBillType, BOSUuid costBillID,BOSUuid splitBillID) throws BOSException, EASBizException{

		String costBillId=costBillID.toString();

		if(costBillId==null){
			return new SettlementCostSplitEntryCollection();
		}
		AbstractObjectCollection coll=null;
		AbstractBillEntryBaseInfo item=null;  
		EntityViewInfo view = new EntityViewInfo();
		String filterField=null;
		FilterInfo filter = new FilterInfo();	
    	SelectorItemCollection sic=view.getSelector();  
    	setSelectorsEntry(ctx,sic,true);
    	view.getSelector().add("parent.id");
    	view.getSorter().add(new SorterItemInfo("seq"));
    	

    	if(splitBillType.equals(CostSplitBillTypeEnum.CONTRACTSPLIT)){
    		filterField="parent.contractBill.id";
	    	filter.getFilterItems().add(new FilterItemInfo(filterField, costBillId));
	    	filter.getFilterItems().add(
					new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));			
	    	view.setFilter(filter);
			coll = ContractCostSplitEntryFactory.getLocalInstance(ctx).getContractCostSplitEntryCollection(view);
    	}
    	else if(splitBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)){
			filterField="parent.contractChange.id";
			filter.getFilterItems().add(new FilterItemInfo(filterField, costBillId.toString()));
			filter.getFilterItems().add(
					new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));			
			view.setFilter(filter);
			coll = ConChangeSplitEntryFactory.getLocalInstance(ctx).getFDCSplitBillEntryCollection(view);
			
		}else{
			//其它拆分单,以后提供支持
			return new SettlementCostSplitEntryCollection();
		}
				
    	SettlementCostSplitEntryCollection entrys=new SettlementCostSplitEntryCollection();
    	SettlementCostSplitEntryInfo entry=null;
    	SettlementCostSplitInfo parent = new SettlementCostSplitInfo();
    	parent.setId(splitBillID);
		//TODO 变更做了科目调整时，结算引入是否合并合同与变更???
		for (Iterator iter = coll.iterator(); iter.hasNext();)		{
			item = (AbstractBillEntryBaseInfo) iter.next();	
			item.setId(null);
			entry=createNewDetailData(ctx);							
			entry.putAll(item);	
			
			BOSUuid splitBillId = item.getObjectValue("parent")==null?null:item.getObjectValue("parent").getBOSUuid("id");
			if(splitBillId!=null){
				entry.setSplitBillId(splitBillId);
			}
			entry.setCostBillId(costBillID);
			entry.setParent(parent);
			entrys.add(entry);
		}	
//		splitBillInfo.put("entrys", entrys);		
		return entrys;		
	}
	
}