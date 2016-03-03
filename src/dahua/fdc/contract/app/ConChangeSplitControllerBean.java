package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitException;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCCostSplit;
import com.kingdee.eas.fdc.basedata.FDCCostSplitAdapter;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitAdapter;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.FDCSplitBillInfo;
import com.kingdee.eas.fdc.contract.ClearSplitFacadeFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractExecInfosFactory;
import com.kingdee.eas.fdc.contract.ContractExecInfosInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.framework.AbstractBillEntryBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class ConChangeSplitControllerBean extends AbstractConChangeSplitControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ConChangeSplitControllerBean");
    protected String _getLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        ConChangeSplitInfo info = (ConChangeSplitInfo)super._getValue(ctx,pk);
        String retValue = "";
        if(info.getContractChange()!= null)
        {
        	String id = info.getContractChange().getId().toString();
        	ContractChangeBillInfo test = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillInfo(new ObjectUuidPK(BOSUuid.read(id)));	
	        if(test.getNumber()!=null){
        		retValue = test.getNumber();
	            if(test.getName()!=null){
	            	retValue = retValue + " " + test.getName();
	            }
	        }
        }
        return retValue;
    }

    protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		//return super._save(ctx, model);
    	ConChangeSplitInfo info = (ConChangeSplitInfo)model;
    	info.setIslastVerThisPeriod(true);
    	
    	//如果是从变更审批走，则在保存不扣减合约规划金额
    	if(UIRuleUtil.isNull(info.getSourceBillId())&&info.getId()!=null){
    		ContractCostSplitControllerBean.synUpdateBillByRelation(ctx, info.getId(), false);
		}
    	IObjectPK pk=super._save(ctx, info);
    	
    	//保存更新
    	if(UIRuleUtil.isNull(info.getSourceBillId()))
    		update(ctx, info);
    	
		return pk;
	}
    
    private void update(Context ctx,ConChangeSplitInfo info) throws EASBizException, BOSException{
    	ContractCostSplitControllerBean.synUpdateBillByRelation(ctx, info.getId(), true);
    	
    	ConChangeSplitInfo splitBillInfo=null;
		SelectorItemCollection selectorGet = new SelectorItemCollection();
		selectorGet.add("id");
		selectorGet.add("contractChange.id");
		selectorGet.add("contractChange.curProject.id");
		selectorGet.add("contractChange.period.number");
		selectorGet.add("contractChange.CU.id");
		selectorGet.add("period.id");
		selectorGet.add("period.number");
		selectorGet.add("splitState");
		splitBillInfo = super.getConChangeSplitInfo(ctx, new ObjectUuidPK(info.getId()), selectorGet);
		String prjID = splitBillInfo.getContractChange().getCurProject().getId().toString();
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx,prjID, true);
		if(currentPeriod!=null){
			PeriodInfo contractPeriod = splitBillInfo.getContractChange().getPeriod();
			if(contractPeriod!=null&&contractPeriod.getNumber()>currentPeriod.getNumber()){
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder
						.appendSql("update T_CON_ConChangeSplit set FPeriodID=? where fid=?");
				builder.addParam(contractPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();	
				
				
				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_CON_ConChangeSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and FContractChangeid=?");
				builder.addParam(contractPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.addParam(splitBillInfo.getContractChange().getId().toString());
				builder.executeUpdate();
			}else{
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder
						.appendSql("update T_CON_ConChangeSplit set FPeriodID=? where fid=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();
				
				
				builder = new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("update T_CON_ConChangeSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and FContractChangeid=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.addParam(splitBillInfo.getContractChange().getId().toString());
				builder.executeUpdate();
			}
		}else if(splitBillInfo.getPeriod()!=null){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.clear();
			builder.appendSql("update T_CON_ConChangeSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and FContractChangeid=?");
			builder.addParam(splitBillInfo.getPeriod().getId().toString());
			builder.addParam(splitBillInfo.getId().toString());
			builder.addParam(splitBillInfo.getContractChange().getId().toString());
			builder.executeUpdate();
		}
    	ConChangeSplitInfo splitBill=info;		
		splitBill.setId(splitBill.getId());
		updateEntrySeq(ctx,splitBill);
    			
		collectCostSplit(ctx, CostSplitBillTypeEnum.CNTRCHANGESPLIT, splitBillInfo.getContractChange(), splitBill.getId(), splitBill.getEntrys());
		
		//更新变更单的拆分状态
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("update T_Con_ContractChangeBill set FSplitState=? where fid=?");
		builder.addParam(splitBill.getSplitState().getValue());
		builder.addParam(splitBill.getContractChange().getId().toString());
		builder.execute();
		//驱动拆分工作流,注意此处传递的单据的ID一定是单据ID
		ContractChangeBillFactory.getLocalInstance(ctx).costChangeSplit(splitBill.getContractChange().getId());
    }

	public IObjectPK[] _delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException {
		//统一在基类中实现
		return super._delete(ctx, filter);
	}


	public void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
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
    	selector.add("contractChange.id");
    	ConChangeSplitCollection splits=getConChangeSplitCollection(ctx, view);
    	idSet.clear();
		for(int i=0;i<splits.size();i++){			
			ConChangeSplitInfo splitBill=splits.get(i);	
			BOSUuid costBillId=splitBill.getContractChange().getId();
			idSet.add(costBillId.toString());
			deleteCostSplit(ctx, CostSplitBillTypeEnum.CNTRCHANGESPLIT, costBillId);
		}
		
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("update T_Con_ContractChangeBill set FSplitState=? where ");
		builder.addParam(CostSplitStateEnum.NOSPLIT_VALUE);
		builder.appendParam("fid", idSet.toArray());
		builder.execute();
		
		for(int i=0;i<arrayPK.length;i++){
			ContractCostSplitControllerBean.synUpdateBillByRelation(ctx, BOSUuid.read(arrayPK[i].toString()), false);
		}
		super._delete(ctx, arrayPK);
	}
    
	public void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("id", billId.toString());
		view.getSelector().add("splitState");
		view.getSelector().add("contractChange.state");
		ConChangeSplitCollection c = getConChangeSplitCollection(ctx, view);
		if(c.size()!=1){
			throw new EASBizException(EASBizException.CHECKEXIST); 
		}else{
			if(!((c.get(0).getContractChange().getState().equals(FDCBillStateEnum.AUDITTED))
			||(c.get(0).getContractChange().getState().equals(FDCBillStateEnum.ANNOUNCE))
			||(c.get(0).getContractChange().getState().equals(FDCBillStateEnum.VISA)))){
				throw new CostSplitException(CostSplitException.CONNOTAUDIT);
			}else if(c.get(0).getSplitState()!=CostSplitStateEnum.ALLSPLIT){
				throw new CostSplitException(CostSplitException.PARTSPLIT);
			}else{
				super._audit(ctx, billId);
			}
		}
		
		ConChangeSplitInfo info = getConChangeSplitInfo(ctx, new ObjectUuidPK(billId));
    	//保存更新
    	if(UIRuleUtil.isNotNull(info.getSourceBillId()))
    		update(ctx, info);
	}
	
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		String sbid = getConChangeSplitInfo(ctx, new ObjectUuidPK(billId)).getSourceBillId();
		if(UIRuleUtil.isNotNull(sbid)){
			IRowSet rs = DbUtil.executeQuery(ctx,"select fid from T_CON_ChangeAuditBill where fid='"+sbid+"' and fchangestate in('6Announce','7Visa')");
			if(rs.size() == 1)
				throw new EASBizException(new NumericExceptionSubItem("111","此拆分单据已做签证，不能反审批！"));
		}
		super._unAudit(ctx, billId);
	}
    
	protected void _autoSplit(Context ctx, IObjectPK changePK)
			throws BOSException, EASBizException {
		if(changePK==null) return;
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("balanceAmount");
		selector.add("hasSettled");
		selector.add("amount");
		selector.add("contractBill.id");
		selector.add("contractBill.isCoseSplit");
		selector.add("curProject");
		ContractChangeBillInfo info = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillInfo(changePK,selector);
		String contractId = info.getContractBill().getId().toString();
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		String companyId = FDCHelper.getCurCompanyId(ctx, info.getCurProject().getId().toString());
		boolean isFinacial = FDCUtils.IsFinacial(ctx, companyId);
		if(isFinacial){
			//如果启用了一体化的话,则先作废变更拆分然后生成一张新的变更拆分,最后重新拆分
			if(info.getContractBill().isIsCoseSplit()){
				EntityViewInfo view=new EntityViewInfo();
				view.getSelector().add("*");
				view.getSelector().add("entrys.*");
				view.setFilter(new FilterInfo());
				view.getFilter().appendFilterItem("contractChange.id", changePK.toString());
				view.getFilter().getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
				ConChangeSplitCollection col = ConChangeSplitFactory.getLocalInstance(ctx).getConChangeSplitCollection(view);
				if(col!=null&&col.size()>0){
					ConChangeSplitInfo change=col.get(0);
					ClearSplitFacadeFactory.getLocalInstance(ctx).clearChangeSplit(changePK.toString());
					//将拆分重新保存
					change.setId(BOSUuid.create(change.getBOSType()));
					for(int i=0;i<change.getEntrys().size();i++){
						ConChangeSplitEntryInfo entry=change.getEntrys().get(i);
						entry.setId(null);
					}
					ConChangeSplitFactory.getLocalInstance(ctx).save(change);
				}
				boolean isSeparate = FDCUtils.getBooleanValue4FDCParamByKey(ctx, companyId, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
				if(isSeparate){
					BigDecimal latestPrice = FDCUtils.getContractLastAmt(ctx, contractId);
					BigDecimal workload = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoad(contractId);
					if (latestPrice.compareTo(FDCHelper.toBigDecimal(workload, 2)) == -1) {
						throw new EASBizException(new NumericExceptionSubItem("111",
								"合同最新造价小于合同的工程确认单已审批累计金额，请先修改工程量确认单！"));
					}
				}
			} else{
				EntityViewInfo view=new EntityViewInfo();
				view.getSelector().add("*");
				view.getSelector().add("entrys.*");
				view.setFilter(new FilterInfo());
				view.getFilter().appendFilterItem("contractChange.id", changePK.toString());
				view.getFilter().getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
				ConChangeNoCostSplitCollection col = ConChangeNoCostSplitFactory.getLocalInstance(ctx).getConChangeNoCostSplitCollection(view);
				if(col!=null&&col.size()>0){
					ConChangeNoCostSplitInfo change=col.get(0);
					ClearSplitFacadeFactory.getLocalInstance(ctx).clearChangeSplit(changePK.toString());
					//将拆分重新保存
					change.setId(BOSUuid.create(change.getBOSType()));
					for(int i=0;i<change.getEntrys().size();i++){
						ConChangeNoCostSplitEntryInfo entry=change.getEntrys().get(i);
						entry.setId(null);
					}
					ConChangeNoCostSplitFactory.getLocalInstance(ctx).save(change);
				}
			}
		}
		if(!info.isHasSettled()) return;
			//用结算金额按替换原来的拆分
		String splitId = null;
		if(info.getContractBill().isIsCoseSplit()){
			builder.clear();
			builder.appendSql("select split.fid as splitId,entry.fid as fid,entry.famount as amount from T_CON_ConChangeSplit split ");
			builder.appendSql("inner join T_CON_ConChangeSplitEntry entry on split.fid=entry.fparentid ");
			builder.appendSql("where entry.flevel=0 and split.FContractChangeID=? and split.fstate<>? order by entry.fseq");
			
			builder.addParam(changePK.toString());
			builder.addParam(FDCBillStateEnum.INVALID_VALUE);
			try{
				IRowSet rowSet=builder.executeQuery();
				if(rowSet.size()>0){
					HashMap map=new HashMap(rowSet.size());
					BigDecimal sum=FDCHelper.ZERO;
					while(rowSet.next()){
						BigDecimal amount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
						sum=sum.add(amount);
						map.put(rowSet.getString("fid"), amount);
						splitId=rowSet.getString("splitId");
					}
					if(sum.compareTo(FDCHelper.ZERO)==0){
						//科目平分
//						BigDecimal amount=info.getBalanceAmount().divide(BigDecimal.valueOf(rowSet.size()), 2,BigDecimal.ROUND_HALF_UP);
						BigDecimal amount=FDCHelper.divide(info.getBalanceAmount(), BigDecimal.valueOf(rowSet.size()), 2, BigDecimal.ROUND_HALF_UP);
						Object key=null;
						for(Iterator iter=map.keySet().iterator();iter.hasNext();){
							key=iter.next();
							map.put(key, amount);
						}
						
						//余额
//						amount=amount.add(info.getBalanceAmount().subtract(amount.multiply(BigDecimal.valueOf(rowSet.size()))));
						amount=FDCHelper.subtract(FDCHelper.add(amount, info.getBalanceAmount()), FDCHelper.multiply(amount, BigDecimal.valueOf(rowSet.size())));
						map.put(key, amount);
						
					}else{
						BigDecimal sumTemp=FDCHelper.ZERO;
						BigDecimal amount=FDCHelper.ZERO;
						BigDecimal bigAmount=FDCHelper.MIN_DECIMAL;
						Object key=null;
						Object bigKey=null;
						for(Iterator iter=map.keySet().iterator();iter.hasNext();){
							//比例分摊
							key=iter.next();
//							amount=(BigDecimal)map.get(key);
							amount=FDCHelper.toBigDecimal(map.get(key));
							if(amount.compareTo(bigAmount)>0){
								bigAmount=amount;
								bigKey=key;
							}
//							amount=amount.multiply(info.getBalanceAmount()).divide(sum, 2, BigDecimal.ROUND_HALF_UP);
							amount=FDCHelper.divide(FDCHelper.multiply(amount, info.getBalanceAmount()), sum, 2, BigDecimal.ROUND_HALF_UP);
							map.put(key, amount);
//							sumTemp=sumTemp.add(amount);
							sumTemp=FDCHelper.add(sumTemp, amount);
						}
						//余额处理
//						amount=info.getBalanceAmount().subtract(sumTemp);
						amount=FDCHelper.subtract(info.getBalanceAmount(), sumTemp);
//						amount=((BigDecimal)map.get(bigKey)).add(amount);
						amount=FDCHelper.add(map.get(bigKey), amount);
						map.put(bigKey, amount);
					}
					for(Iterator iter=map.keySet().iterator();iter.hasNext();){
						builder.clear();
						Object key=iter.next();
						Object object = map.get(key);
	//					if(FDCHelper.toBigDecimal(object).signum()<0){
	//						//负数i
	//						continue;
	//					}
						builder.appendSql("update T_CON_ConChangeSplitEntry set famount=? where fid=?");
						builder.addParam(object);
						builder.addParam(key);
						builder.execute();
					}
					
				}
				if(splitId!=null){
					builder.clear();
					builder.appendSql("update T_CON_ConChangeSplit set famount=? where fid=?");
					builder.addParam(info.getBalanceAmount());
					builder.addParam(splitId);
					builder.execute();
					FDCCostSplitAdapter  fdcostSplit=new  FDCCostSplitAdapter(ctx);
					fdcostSplit.refreshConChange(splitId);
					//结算拆分的面积刷新有问题
	/*				
					//如果有结算拆分刷新结算拆分
					builder.clear();
					builder.appendSql("select split.fid as fid from T_CON_SettlementCostSplit split inner join T_CON_ContractSettlementBill settle on split.FSettlementBillID=settle.fid  where settle.FContractBillID=?");
					builder.addParam(info.getContractBill().getId().toString());
					rowSet= builder.executeQuery();
					while(rowSet.next()){
						fdcostSplit.refreshSettlement(rowSet.getString("fid"));
					}
	*/	
				}
			}catch(Exception e){
				throw new BOSException(e);
			}
		} else {
			builder.clear();
			builder.appendSql("select split.fid as splitId,entry.fid as fid,entry.famount as amount from T_CON_ConChangeNoCostSplit split ");
			builder.appendSql("inner join T_CON_ChangeNoSplitEntry entry on split.fid=entry.fparentid ");
			builder.appendSql("where entry.flevel=0 and split.FContractChangeID=? and split.fstate<>? order by entry.fseq");
			
			builder.addParam(changePK.toString());
			builder.addParam(FDCBillStateEnum.INVALID_VALUE);
			try{
				IRowSet rowSet=builder.executeQuery();
				if(rowSet.size()>0){
					HashMap map=new HashMap(rowSet.size());
					BigDecimal sum=FDCHelper.ZERO;
					while(rowSet.next()){
						BigDecimal amount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
//						sum=sum.add(amount);
						sum=FDCHelper.add(sum, amount);
						map.put(rowSet.getString("fid"), amount);
						splitId=rowSet.getString("splitId");
					}
					if(sum.compareTo(FDCHelper.ZERO)==0){
						//科目平分
//						BigDecimal amount=info.getBalanceAmount().divide(BigDecimal.valueOf(rowSet.size()), 2,BigDecimal.ROUND_HALF_UP);
						BigDecimal amount=FDCHelper.divide(info.getBalanceAmount(), BigDecimal.valueOf(rowSet.size()),2,BigDecimal.ROUND_HALF_UP);
						Object key=null;
						for(Iterator iter=map.keySet().iterator();iter.hasNext();){
							key=iter.next();
							map.put(key, amount);
						}
						
						//余额
//						amount=amount.add(info.getBalanceAmount().subtract(amount.multiply(BigDecimal.valueOf(rowSet.size()))));
						amount=FDCHelper.subtract(FDCHelper.add(amount, info.getBalanceAmount()), FDCHelper.multiply(amount, BigDecimal.valueOf(rowSet.size())));
						map.put(key, amount);
						
					}else{
						BigDecimal sumTemp=FDCHelper.ZERO;
						BigDecimal amount=FDCHelper.ZERO;
						BigDecimal bigAmount=FDCHelper.MIN_DECIMAL;
						Object key=null;
						Object bigKey=null;
						for(Iterator iter=map.keySet().iterator();iter.hasNext();){
							//比例分摊
							key=iter.next();
							amount=(BigDecimal)map.get(key);
							if(amount.compareTo(bigAmount)>0){
								bigAmount=amount;
								bigKey=key;
							}
//							amount=amount.multiply(info.getBalanceAmount()).divide(sum, 2, BigDecimal.ROUND_HALF_UP);
							amount=FDCHelper.divide(FDCHelper.multiply(amount, info.getBalanceAmount()), sum, 2, BigDecimal.ROUND_HALF_UP);
							map.put(key, amount);
//							sumTemp=sumTemp.add(amount);
							sumTemp=FDCHelper.add(sumTemp, amount);
						}
						//余额处理
//						amount=info.getBalanceAmount().subtract(sumTemp);
						amount=FDCHelper.subtract(info.getBalanceAmount(), sumTemp);
//						amount=((BigDecimal)map.get(bigKey)).add(amount);
						amount=FDCHelper.add((BigDecimal)map.get(bigKey), amount);
						map.put(bigKey, amount);
					}
					for(Iterator iter=map.keySet().iterator();iter.hasNext();){
						builder.clear();
						Object key=iter.next();
						Object object = map.get(key);
	//					if(FDCHelper.toBigDecimal(object).signum()<0){
	//						//负数i
	//						continue;
	//					}
						builder.appendSql("update T_CON_ChangeNoSplitEntry set famount=? where fid=?");
						builder.addParam(object);
						builder.addParam(key);
						builder.execute();
					}
					
				}
				if(splitId!=null){
					builder.clear();
					builder.appendSql("update T_CON_ConChangeNoCostSplit set famount=? where fid=?");
					builder.addParam(info.getBalanceAmount());
					builder.addParam(splitId);
					builder.execute();
					FDCNoCostSplitAdapter  fdcNoCostSplit =new  FDCNoCostSplitAdapter(ctx);
					fdcNoCostSplit.refreshConNoCostChange(splitId);
					//结算拆分的面积刷新有问题
	/*				
					//如果有结算拆分刷新结算拆分
					builder.clear();
					builder.appendSql("select split.fid as fid from T_CON_SettlementCostSplit split inner join T_CON_ContractSettlementBill settle on split.FSettlementBillID=settle.fid  where settle.FContractBillID=?");
					builder.addParam(info.getContractBill().getId().toString());
					rowSet= builder.executeQuery();
					while(rowSet.next()){
						fdcostSplit.refreshSettlement(rowSet.getString("fid"));
					}
	*/	
				}
			}catch(Exception e){
				throw new BOSException(e);
			}
		}
		//变更结算时根据变更单数据更新合同执行表相关信息
//		String contractId=info.getContractBill().getId().toString();
		ContractExecInfosFactory.getLocalInstance(ctx)
			.updateChange(ContractExecInfosInfo.EXECINFO_AUDIT,contractId);
		
		// splitId为空说明，无拆分分录
		if (splitId == null) {
			info.put("noSplit", Boolean.TRUE);
		}
		//变更拆分
		updateChangeState(ctx, changePK, info);
	}

	/**
	 * 描述：查询出变更拆分的数据，更新拆分状态
	 * @param ctx
	 * @param changePK
	 * @param info
	 * @throws BOSException
	 * @Author：liu_ling
	 * @CreateTime：2012-1-31
	 */
	protected void updateChangeState(Context ctx, IObjectPK changePK, ContractChangeBillInfo info) throws BOSException {
		BigDecimal oriBanalceAmount = info.getBalanceAmount();
		
//		boolean flag = true;
		//		try {
		//			flag = FDCUtils.getDefaultFDCParamByKey(ctx,null, FDCConstants.FDC_PARAM_IMPORTCONSPLIT);
		//		} catch (EASBizException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		String splitState = "3ALLSPLIT";
		if (!FDCHelper.isZero(oriBanalceAmount)) {
			// 金额不为0且无分录，则为部分拆分 by hpw
			if (info.get("noSplit") != null) {
				splitState = "2PARTSPLIT";
			}
		}
		//		if(flag){
		//			if (oriBanalceAmount != null && oriBanalceAmount.compareTo(info.getAmount()) == 0) {
		//				splitState = "3ALLSPLIT";
		//			} else {
		//				splitState = "2PARTSPLIT";
		//			}
		//		}else{
		//			if (((oriBanalceAmount == null &&info.getAmount()==null)
		//				||("").equals(oriBanalceAmount)&&("").equals(info.getAmount())
		//			) || oriBanalceAmount.compareTo(info.getAmount()) == 0) {
		//				splitState = "3ALLSPLIT";
		//			} else {
		//				splitState = "2PARTSPLIT";
		//			}
		//		}
		
		 
		if (info.getContractBill().isIsCoseSplit()) {
			// 查出变更拆分数据并更新拆分状态
			ConChangeSplitCollection ccsColl = ConChangeSplitFactory.getLocalInstance(ctx).getConChangeSplitCollection(
					"where contractChange.id = '" + changePK.toString() + "'");
			if (ccsColl != null && ccsColl.size() > 0) {
				ConChangeSplitInfo splitInfo = ccsColl.get(0);
				String sql = "update T_CON_ConChangeSplit set fsplitstate = ? where fid = ? and fstate <> '9INVALID'";
				//DbUtil.execute(ctx, sql);
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.appendSql(sql);
				builder.addParam(splitState);
				builder.addParam(splitInfo.getId().toString());
				builder.execute();
			}
		} else {
			// 查出变更拆分数据并更新拆分状态
			ConChangeNoCostSplitCollection ccsColl = ConChangeNoCostSplitFactory.getLocalInstance(ctx).getConChangeNoCostSplitCollection(
					"where contractChange.id = '" + changePK.toString() + "'");
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			if (ccsColl != null && ccsColl.size() > 0) {
				ConChangeNoCostSplitInfo splitInfo = ccsColl.get(0);
				String sql = "update T_CON_ConChangeNoCostSplit set fsplitstate = ? where fid = ? and fstate <> '9INVALID'";
				//DbUtil.execute(ctx, sql);
				builder.appendSql(sql);
				builder.addParam(splitState);
				builder.addParam(splitInfo.getId().toString());
				builder.execute();
			}
		}
	}
	
	/**
	 * 
	 * 描述：生成变更成本拆分数据
	 * 
	 * @param ctx
	 * @param contractChange
	 * @return 变更成本拆分
	 * @throws BOSException
	 * @throws EASBizException
	 * @Author：liu_ling
	 * @CreateTime：2012-1-31 
	 *                       同ContractChangeBillControllerBean.createConChangeSplitInfo
	 */
	protected ConChangeSplitInfo createConChangeSplitInfo(Context ctx, String contractChange) throws BOSException, EASBizException {
		ConChangeSplitInfo info = new ConChangeSplitInfo();
		ContractChangeBillInfo contractChangeInfo = null;
		info.setState(FDCBillStateEnum.SAVED);
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("*");
		selectors.add("curProject.*");
		selectors.add("contractBill.*");
		selectors.add("period.id");
		selectors.add("period.number");
		if (contractChange != null) {
			contractChangeInfo = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillInfo(new ObjectUuidPK(BOSUuid.read(contractChange)), selectors);
			info.setContractChange(contractChangeInfo);
			if (contractChangeInfo.getContractBill() != null)
				info.setContractBill(contractChangeInfo.getContractBill());
			if (contractChangeInfo.getCurProject() != null)
				info.setCurProject(contractChangeInfo.getCurProject());
		}
		info.setCreator((UserInfo) ContextUtil.getCurrentUserInfo(ctx));
		info.setCreateTime(new Timestamp(new Date().getTime()));
		info.setSplitState(CostSplitStateEnum.ALLSPLIT);
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, contractChangeInfo.getCurProject().getId().toString(), true);
		PeriodInfo contractPeriod = contractChangeInfo.getPeriod();
		if (contractPeriod != null && contractPeriod.getNumber() > currentPeriod.getNumber()) {
			info.setPeriod(contractPeriod);
			info.setIslastVerThisPeriod(false);
		} else {
			info.setPeriod(currentPeriod);
			info.setIslastVerThisPeriod(true);
		}
		info.setIsInvalid(false);
		return info;
	}

	/**
	 * 
	 * 描述：生成变更非成本拆分数据
	 * 
	 * @param ctx
	 * @param contractChange
	 * @return 变更非成本拆分
	 * @throws BOSException
	 * @throws EASBizException
	 * @Author：liu_ling
	 * @CreateTime：2012-1-31
	 */
	protected ConChangeNoCostSplitInfo createConChangeNoSplitInfo(Context ctx, String contractChange) throws BOSException, EASBizException {
		ConChangeNoCostSplitInfo info = new ConChangeNoCostSplitInfo();
		ContractChangeBillInfo contractChangeInfo = null;
		info.setState(FDCBillStateEnum.SAVED);
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("*");
		selectors.add("curProject.*");
		selectors.add("contractBill.*");
		selectors.add("period.id");
		selectors.add("period.number");
		if (contractChange != null) {
			contractChangeInfo = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillInfo(new ObjectUuidPK(BOSUuid.read(contractChange)), selectors);
			info.setContractChange(contractChangeInfo);
			if (contractChangeInfo.getContractBill() != null)
				info.setContractBill(contractChangeInfo.getContractBill());
			if (contractChangeInfo.getCurProject() != null)
				info.setCurProject(contractChangeInfo.getCurProject());
		}
		info.setCreator((UserInfo) ContextUtil.getCurrentUserInfo(ctx));
		info.setCreateTime(new Timestamp(new Date().getTime()));
		info.setSplitState(CostSplitStateEnum.ALLSPLIT);
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, contractChangeInfo.getCurProject().getId().toString(), true);
		PeriodInfo contractPeriod = contractChangeInfo.getPeriod();
		if (contractPeriod != null && contractPeriod.getNumber() > currentPeriod.getNumber()) {
			info.setPeriod(contractPeriod);
		} else {
			info.setPeriod(currentPeriod);
		}
		info.setIsInvalid(false);
		return info;
	}
	
	protected void _changeSettle(Context ctx,IObjectValue changeInfo)
			throws BOSException, EASBizException {
		ContractChangeBillInfo info =(ContractChangeBillInfo)changeInfo;
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("exRate");
		selector.add("balanceAmount");
		selector.add("oriBalanceAmount");
		selector.add("hasSettled");
		selector.add("amount");
		selector.add("settleTime");
		selector.add("forSettAfterSign");
		ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(info,selector);
		
		
		// 当为变更结算为0时,自动完全拆分
		if (FDCHelper.isZero(info.getBalanceAmount())) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractChange.id", info.getId().toString()));
			if (info.isIsCostSplit()) { // 判断是否成本拆分
				if (!ConChangeSplitFactory.getLocalInstance(ctx).exists(filter)) {
					// 生成变更成本拆分数据
					ConChangeSplitInfo costSplitInfo = createConChangeSplitInfo(ctx, info.getId().toString());
					ConChangeSplitFactory.getLocalInstance(ctx).save(costSplitInfo);
				}
			} else {
				if (!ConChangeNoCostSplitFactory.getLocalInstance(ctx).exists(filter)) {
					// 生成变更非成本拆分数据
					ConChangeNoCostSplitInfo noCostSplitInfo = createConChangeNoSplitInfo(ctx, info.getId().toString());
					ConChangeNoCostSplitFactory.getLocalInstance(ctx).save(noCostSplitInfo);
				}
			}

		}
		
		
		_autoSplit(ctx, new ObjectUuidPK(info.getId()));
		//变更结算时根据变更单数据更新合同执行表相关信息 
		//方法移至_autoSplit()，因为在结算拆分时反写变更结算，也必须重新更新变更。 --2009-05-23
		//判断是不是有科目小于0的情况
		String contractId=info.getContractBill().getId().toString();
		boolean isCostSplit = info.getContractBill().isIsCoseSplit();
//		ContractExecInfosFactory.getLocalInstance(ctx)
//			.updateChange(ContractExecInfosInfo.EXECINFO_AUDIT,contractId);
		if(ContextUtil.getCurrentOrgUnit(ctx)!=null&&ContextUtil.getCurrentOrgUnit(ctx).getId()!=null	){
			//参数：允许科目总和为负的变更拆分
			boolean noCtrl=false;
			String comPK=ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
			HashMap hmParamIn = new HashMap();
	        hmParamIn.put(FDCConstants.FDC_PARAM_NOCHANGESPLITACCTCTRL, comPK);
	        try{
	        	HashMap hmAllParam = ParamControlFactory.getLocalInstance(ctx).getParamHashMap(hmParamIn);
	            Object theValue = hmAllParam.get(FDCConstants.FDC_PARAM_NOCHANGESPLITACCTCTRL);
	            if(theValue != null){
	            	noCtrl=Boolean.valueOf(theValue.toString()).booleanValue();
	    		}
	            if(noCtrl){
	            	return;
	            }
	        }catch(Exception e){
	        	this.logger.error("check acct sum err:"+e.getMessage(), e);
	        	throw new BOSException(e);
	        }
		}
		Map map=new HashMap();
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select fcostaccountid,sum(famount) famount from (\n");
		builder.appendSql("(select fcostaccountid,famount from T_CON_ContractCostSplitEntry where fparentid in (select fid from T_CON_ContractCostSplit conSplit where conSplit.fcontractBillId=? and fstate<>?) and ");
		builder.addParam(contractId);
		builder.addParam(FDCBillStateEnum.INVALID_VALUE);
		builder.appendSql(" ((fisleaf=1 and (fproductid is null or fsplitType is null or fsplitType<>? )) or (fisleaf=0 and fproductid is null and fsplitType=?))");
		builder.addParam(CostSplitTypeEnum.PRODSPLIT_VALUE);
		builder.addParam(CostSplitTypeEnum.PRODSPLIT_VALUE);
		builder.appendSql("\n) \nunion all ");
		builder.appendSql("(\nselect fcostaccountid,famount from T_CON_ConChangeSplitEntry where fparentid in (select fid from T_CON_ConChangeSplit changeSplit where changeSplit.fcontractBillId=? and fstate<>?) and ");
		builder.addParam(contractId);
		builder.addParam(FDCBillStateEnum.INVALID_VALUE);
		//changeId不存在时=1使得不改变sql而取到所有值
		builder.appendSql(" ((fisleaf=1 and (fproductid is null or fsplitType is null or fsplitType<>? )) or (fisleaf=0 and fproductid is null and fsplitType=?))");
		builder.addParam(CostSplitTypeEnum.PRODSPLIT_VALUE);
		builder.addParam(CostSplitTypeEnum.PRODSPLIT_VALUE);
		builder.appendSql(")\n)t group by fcostaccountid");
		IRowSet rowSet=builder.executeQuery();
		try{
			while(rowSet.next()){
//			String key = rowSet.getString("fcostaccountid");
//			map.put(key, FDCNumberHelper.add(rowSet.getBigDecimal("famount"), map.get(key)));
				BigDecimal tmpAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("famount"));
				if(tmpAmt.signum()<0){
					throw new EASBizException(new NumericExceptionSubItem("101","自动拆分变更后存在科目金额之和小于零的情况,操作取消!"));
				}
			}
		}catch (SQLException e) {
			throw new BOSException(e);
		}
	}

	protected void _autoSplit4(Context ctx, BOSUuid billID) throws BOSException, EASBizException {
		checkBeforeAutoSplit(ctx,billID);
		ConChangeSplitInfo info = (ConChangeSplitInfo)createNewData(ctx,billID);
		importCostSplit(ctx,info);
		FDCSplitBillEntryCollection colls = new FDCSplitBillEntryCollection();
		for(Iterator iter=info.getEntrys().iterator();iter.hasNext();){
			colls.add((FDCSplitBillEntryInfo)iter.next());
		}
		colls = apptAmount(ctx,info,colls);
		info.getEntrys().clear();
		ConChangeSplitEntryCollection entrys = new ConChangeSplitEntryCollection();
		for(Iterator iter=colls.iterator();iter.hasNext();){
			entrys.add((ConChangeSplitEntryInfo)iter.next());
		}
		info.getEntrys().addCollection(entrys);
		if(info.getEntrys()!=null&&info.getEntrys().size()>0){
			_save(ctx, info);
		}
	}
	private void checkBeforeAutoSplit(Context ctx, BOSUuid billID) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select split.fsplitstate from t_con_contractcostsplit split ");
		builder.appendSql("inner join t_con_contractchangebill bill on bill.fcontractbillid=split.fcontractbillid ");
		builder.appendSql("where bill.fid=? ");
		builder.addParam(billID.toString());
		IRowSet rs = builder.executeQuery();
		String msg ="启用了参数：\"合同变更、合同结算的拆分是否自动引用合同拆分的成本科目、拆分比例\"，合同未拆分或部分拆分，操作取消！";
		try {
			if(rs!=null&&rs.next()){
				String splitState = rs.getString("fsplitstate");
				if(!CostSplitStateEnum.ALLSPLIT_VALUE.equals(splitState)){
					throw new EASBizException(new NumericExceptionSubItem("101",msg));
				}
			}else{
				throw new EASBizException(new NumericExceptionSubItem("101",msg));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
	}

	protected void importCostSplit(Context ctx,ConChangeSplitInfo info) throws BOSException, EASBizException {
		importContractCostSplist(ctx,info);
	}
		
	protected void loadCostSplit(CostSplitBillTypeEnum costBillType,
			ConChangeSplitEntryCollection conChangeSplitEntryCollection) {
		
	}
	protected ConChangeSplitEntryInfo createNewDetailData(Context ctx) throws BOSException, EASBizException{
		return new ConChangeSplitEntryInfo();
	}
	protected ConChangeSplitInfo createNewData(Context ctx,BOSUuid billID) throws BOSException, EASBizException{
    	ConChangeSplitInfo objectValue = new ConChangeSplitInfo();
    	String contractChange = billID.toString();
    	ContractChangeBillInfo contractChangeInfo=null;
    	        
        SelectorItemCollection selectors = new SelectorItemCollection();
        selectors.add("id");
		selectors.add("number");
		selectors.add("name");
		selectors.add("amount");
		selectors.add("balanceAmount");
		selectors.add("hasSettled");
		selectors.add("contractBill.id");
		selectors.add("curProject.id");
		//不支持
//		selectors.add("contractBill.isMeasureContract");
        if(contractChange != null){
	        try {
	        	contractChangeInfo = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillInfo(new ObjectUuidPK(BOSUuid.read(contractChange)),selectors);
			} catch (Exception e) {
				throw new BOSException(e);
			}
			objectValue.setContractChange(contractChangeInfo);
			if(contractChangeInfo.getContractBill()!=null)
				objectValue.setContractBill(contractChangeInfo.getContractBill());
			if(contractChangeInfo.getCurProject()!=null)
				objectValue.setCurProject(contractChangeInfo.getCurProject());
        }
		objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(ContextUtil.getCurrentUserInfo(ctx)));
		objectValue.setCreateTime(new Timestamp(System.currentTimeMillis()));
		objectValue.setIsInvalid(false);
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
		objectValue.setSplitState(CostSplitStateEnum.ALLSPLIT);
		if (contractChangeInfo.isHasSettled()) {
			objectValue.setAmount(contractChangeInfo.getBalanceAmount());
		} else {
			objectValue.setAmount(contractChangeInfo.getAmount());
		}
    	return objectValue;
    }
	protected void importContractCostSplist(Context ctx, ConChangeSplitInfo info) throws BOSException, EASBizException{
		loadCostSplit(CostSplitBillTypeEnum.CONTRACTSPLIT,
				getCostSplitEntryCollection(ctx,
						CostSplitBillTypeEnum.CONTRACTSPLIT, info));
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
    protected FDCSplitBillEntryCollection apptAmount(Context ctx,FDCSplitBillInfo info, FDCSplitBillEntryCollection entrys){
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
    	FDCSplitBillEntryInfo topEntry = null;
    	for(Iterator iter=entrys.iterator();iter.hasNext();){
    		topEntry = (FDCSplitBillEntryInfo)iter.next();
    		if(topEntry.getLevel()==0){
				amount = FDCHelper.divide(FDCHelper.multiply(info.getAmount(), topEntry.getSplitScale()),FDCHelper.ONE_HUNDRED,10,BigDecimal.ROUND_HALF_UP);
				topEntry.setAmount(amount);
			}else{
				topEntry.setAmount(FDCHelper.ZERO);
			}
    		topEntry.setAmount(amount);	
    	}
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
	protected ConChangeSplitEntryCollection getCostSplitEntryCollection(Context ctx,CostSplitBillTypeEnum splitBillType, ConChangeSplitInfo info) throws BOSException, EASBizException{

		String costBillId=info.getId().toString();

		if(costBillId==null){
			return new ConChangeSplitEntryCollection();
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
	    	filter.getFilterItems().add(new FilterItemInfo(filterField, info.getContractBill().getId().toString()));
	    	filter.getFilterItems().add(
					new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));			
	    	view.setFilter(filter);
			coll = ContractCostSplitEntryFactory.getLocalInstance(ctx).getContractCostSplitEntryCollection(view);
    	}
    	else if(splitBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)){
			filterField="parent.contractChange.id";
			filter.getFilterItems().add(new FilterItemInfo(filterField, costBillId));
			filter.getFilterItems().add(
					new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));			
			view.setFilter(filter);
			coll = ConChangeSplitEntryFactory.getLocalInstance(ctx).getFDCSplitBillEntryCollection(view);
			
		}else{
			//其它拆分单,以后提供支持
			return new ConChangeSplitEntryCollection();
		}
				
    	ConChangeSplitEntryCollection entrys=new ConChangeSplitEntryCollection();
    	ConChangeSplitEntryInfo entry=null;
  	
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
			entry.setCostBillId(info.getId());
			entrys.add(entry);
		}	
		info.put("entrys", entrys);		
		return entrys;		
	}
}