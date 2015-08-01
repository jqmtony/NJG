package com.kingdee.eas.fdc.contract.app;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostSplitException;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;

public class ConNoCostSplitControllerBean extends
		AbstractConNoCostSplitControllerBean
{
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.contract.app.ConNoCostSplitControllerBean");

	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException
	{
		super._save(ctx, pk, model);
	}

	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException
	{
		IObjectPK pk=super._save(ctx, model);
		ConNoCostSplitInfo splitBillInfo=null;
		SelectorItemCollection selectorGet = new SelectorItemCollection();
		selectorGet.add("id");
		selectorGet.add("contractBill.curProject.id");
		selectorGet.add("contractBill.period.number");
		selectorGet.add("period.id");
		selectorGet.add("period.number");
		splitBillInfo = super.getConNoCostSplitInfo(ctx, pk, selectorGet);
		String prjID = splitBillInfo.getContractBill().getCurProject().getId().toString();
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx,prjID, true);
		PeriodInfo contractPeriod = splitBillInfo.getContractBill().getPeriod();
		if(currentPeriod!=null&&splitBillInfo.getPeriod()==null){
			if(contractPeriod!=null&&contractPeriod.getNumber()>currentPeriod.getNumber()){
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder
						.appendSql("update T_CON_ConNoCostSplit set FPeriodID=? where fid=?");
				builder.addParam(contractPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();			
			}else{
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder
						.appendSql("update T_CON_ConNoCostSplit set FPeriodID=? where fid=?");
				builder.addParam(currentPeriod.getId().toString());
				builder.addParam(splitBillInfo.getId().toString());
				builder.executeUpdate();
			}
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("parent.id", pk.toString()));
    	view.getSorter().add(new SorterItemInfo("seq"));
    	view.setFilter(filter);    	
    	SelectorItemCollection selector = view.getSelector();
    	selector.add("id");
    	selector.add("seq");
    	selector.add("index");   
//    	selector.add("*");
    	
    	ConNoCostSplitEntryCollection entrys=ConNoCostSplitEntryFactory.getLocalInstance(ctx).getConNoCostSplitEntryCollection(view);
    	ConNoCostSplitEntryInfo entry=null;

    	for (Iterator iter = entrys.iterator(); iter.hasNext();){
			entry = (ConNoCostSplitEntryInfo) iter.next();			

			//entry.setSeq(entry.getIndex());		//使用index作为拆分组序号		jelon 12/27/2006		
			//entry.setSeq(entry.getIndex()*10000 + entry.getSeq()%10000);
			setEntrySeq(entry);
			
			ConNoCostSplitEntryFactory.getLocalInstance(ctx).updatePartial(entry,selector);
		}
		//处理拆分汇总
    	
//		更新合同的拆分状态
    	ConNoCostSplitInfo splitBill=(ConNoCostSplitInfo)model;				
		BOSUuid costBillId=splitBill.getContractBill().getId();	
		
		ContractBillInfo contractBill=new ContractBillInfo();
		contractBill.setId(costBillId);
		contractBill.setSplitState(splitBill.getSplitState());
		
		SelectorItemCollection selector2=new SelectorItemCollection();
		selector2.add("id");
		selector2.add("splitState");
		ContractBillFactory.getLocalInstance(ctx).updatePartial(contractBill, selector2);
		
		//驱动拆分工作流
		ContractBillFactory.getLocalInstance(ctx).split(new ObjectUuidPK(costBillId));
		
		return pk;
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException
	{
		for(int i=0;i<arrayPK.length;i++){
			ConNoCostSplitInfo splitBill=(ConNoCostSplitInfo)super._getValue(ctx,arrayPK[i]);			
			BOSUuid costBillId=splitBill.getContractBill().getId();
//			checkBeforeDelete(ctx,splitBill);
			//更新合同拆分状态
			ContractBillInfo contractBill=new ContractBillInfo();
			contractBill.setId(costBillId);
			contractBill.setSplitState(CostSplitStateEnum.NOSPLIT);
			
			SelectorItemCollection selector2=new SelectorItemCollection();
			selector2.add("id");
			selector2.add("splitState");
			ContractBillFactory.getLocalInstance(ctx).updatePartial(contractBill, selector2);
		}
		super._delete(ctx, arrayPK);
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException
	{
		ConNoCostSplitInfo splitBill=(ConNoCostSplitInfo)super._getValue(ctx,pk);			
		BOSUuid costBillId=splitBill.getContractBill().getId();
//		checkBeforeDelete(ctx,splitBill);
		//更新合同拆分状态
		ContractBillInfo contractBill=new ContractBillInfo();
		contractBill.setId(costBillId);
		contractBill.setSplitState(CostSplitStateEnum.NOSPLIT);
		
		SelectorItemCollection selector2=new SelectorItemCollection();
		selector2.add("id");
		selector2.add("splitState");
		ContractBillFactory.getLocalInstance(ctx).updatePartial(contractBill, selector2);
		super._delete(ctx, pk);
	}
	/**
	 * 如果单据已审批，则不允许删除
	 * @param ctx
	 * @param splitBill
	 * @throws BOSException
	 * @throws EASBizException
	 */
//	private void checkBeforeDelete(Context ctx,ConNoCostSplitInfo splitBill) throws BOSException,EASBizException {
//		if(splitBill == null || splitBill.getState() == null){
//			return;
//		}
//		if(FDCBillStateEnum.AUDITTED.equals(splitBill.getState())){
//			throw new CostSplitException(CostSplitException.ISAUDITTED);
//		}
//	}
	public void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("id", billId.toString());
		view.getSelector().add("splitState");
		view.getSelector().add("contractBill.state");
		ConNoCostSplitCollection c = getConNoCostSplitCollection(ctx, view);
		if(c.size()!=1){
			throw new EASBizException(EASBizException.CHECKEXIST); 
		}else{
			if(c.get(0).getContractBill().getState()!=FDCBillStateEnum.AUDITTED){
				throw new CostSplitException(CostSplitException.CONNOTAUDIT);
			}else if(c.get(0).getSplitState()!=CostSplitStateEnum.ALLSPLIT){
				throw new CostSplitException(CostSplitException.PARTSPLIT);
			}else{
				super._audit(ctx, billId);
			}
		}
		
	}
}