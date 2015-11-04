package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitException;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCCostSplit;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.util.FdcObjectCollectionUtil;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.basedata.IProgrammingTemp;
import com.kingdee.eas.fdc.contract.basedata.ProgrammingTempCollection;
import com.kingdee.eas.fdc.contract.basedata.ProgrammingTempFactory;
import com.kingdee.eas.fdc.contract.basedata.ProgrammingTempInfo;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.invite.app.ContractCostSplitInviteHelper;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class ContractCostSplitControllerBean extends AbstractContractCostSplitControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractCostSplitControllerBean");
    
    protected String _getLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        ContractCostSplitInfo info = (ContractCostSplitInfo)super._getValue(ctx,pk);
        String retValue = "";
        if(info.getContractBill()!= null)
        {
        	String id = info.getContractBill().getId().toString();
        	ContractBillInfo test = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(id)));	
	        if(test.getNumber()!=null){
        		retValue = test.getNumber();
	            if(test.getName()!=null){
	            	retValue = retValue + " " + test.getName();
	            }
	        }
        }
        return retValue;
    }

	/* （非 Javadoc）
	 * @see com.kingdee.eas.framework.app.CoreBillBaseControllerBean#_save(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectValue)
	 */
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		//return super._save(ctx, model);
		ContractCostSplitInfo info = (ContractCostSplitInfo)model;
		//是否量价合同
		boolean isMeasureContract=info.getBoolean("isMeasureContract");
		info.setIslastVerThisPeriod(true);
		
		
		if(info.getId()!=null){
			synUpdateBillByRelation(ctx, info.getId(), false);
		}
		IObjectPK pk=super._save(ctx, info);
		ContractCostSplitInfo splitBillInfo=null;
		SelectorItemCollection selectorGet = new SelectorItemCollection();
		selectorGet.add("id");
		selectorGet.add("contractBill.id");
		selectorGet.add("contractBill.curProject.id");
		selectorGet.add("contractBill.period.number");
		selectorGet.add("contractBill.CU.id");
		selectorGet.add("period.id");
		selectorGet.add("period.number");
		splitBillInfo = getContractCostSplitInfo(ctx, pk, selectorGet);
		String prjID = splitBillInfo.getContractBill().getCurProject().getId().toString();
		PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx,prjID, true);
		if(currentPeriod!=null){
			PeriodInfo contractPeriod = splitBillInfo.getContractBill().getPeriod();
			if(contractPeriod!=null&&contractPeriod.getNumber()>currentPeriod.getNumber()){
				updatePeriod(ctx, splitBillInfo, contractPeriod);
			}else{
				updatePeriod(ctx, splitBillInfo, currentPeriod);
			}
		}else if(splitBillInfo.getPeriod()!=null){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.clear();
			builder.appendSql("update T_CON_ContractCostSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and FContractBillid=?");
			builder.addParam(splitBillInfo.getPeriod().getId().toString());
			builder.addParam(splitBillInfo.getId().toString());
			builder.addParam(splitBillInfo.getContractBill().getId().toString());
			builder.executeUpdate();
		}
		
		//处理拆分汇总
		ContractCostSplitInfo splitBill=(ContractCostSplitInfo)model;
		splitBill.setId(BOSUuid.read(pk.toString()));
		updateEntrySeq(ctx,splitBill);
		//处理拆分汇总
		BOSUuid costBillId=splitBill.getContractBill().getId();
		collectCostSplit(ctx, CostSplitBillTypeEnum.CONTRACTSPLIT, splitBillInfo.getContractBill(),splitBill.getId(),splitBill.getEntrys());
		
//		更新合同的拆分状态
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("update T_CON_ContractBill set fsplitState=?,fisMeasureContract=? where fid=?");
		builder.addParam(splitBill.getSplitState().getValue());
		builder.addParam(Boolean.valueOf(isMeasureContract));
		builder.addParam(costBillId.toString());
		builder.execute();
		//驱动拆分工作流,这里一定是单据ID
		ContractBillFactory.getLocalInstance(ctx).split(new ObjectUuidPK(costBillId));
		//拆分保存更新对应招标立项下对应的招标预先拆分的状态为已拆分
		ContractCostSplitInviteHelper.setInvitePreSplitState(ctx, pk, true);
		synUpdateBillByRelation(ctx, info.getId(), true);
		return pk;
	}

	/**优化代码结构，重构抽取方法，设置更新期间 -by neo
	 * @param ctx
	 * @param splitBillInfo
	 * @param contractPeriod
	 * @throws BOSException
	 */
	private void updatePeriod(Context ctx, ContractCostSplitInfo splitBillInfo,PeriodInfo period) throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_CON_ContractCostSplit set FPeriodID=? where fid=?");
		builder.addParam(period.getId().toString());
		builder.addParam(splitBillInfo.getId().toString());
		builder.executeUpdate();
		builder = new FDCSQLBuilder(ctx);
		builder.clear();
		builder.appendSql("update T_CON_ContractCostSplit set FIslastVerThisPeriod=0 where FPeriodID=? and fid<>? and FContractBillid=?");
		builder.addParam(period.getId().toString());
		builder.addParam(splitBillInfo.getId().toString());
		builder.addParam(splitBillInfo.getContractBill().getId().toString());
		builder.executeUpdate();
	}



	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.contract.app.AbstractContractCostSplitControllerBean#_delete(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectPK)
	 */
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		//基类已实现调用 _delete(Context ctx, IObjectPK[] arrayPK)
		ContractCostSplitInviteHelper.setInvitePreSplitState(ctx, pk, false);
		ContractCostSplitInfo info = getContractCostSplitInfo(ctx, pk);
		synUpdateBillByRelation(ctx, info.getId(), false);
		super._delete(ctx, pk);
	}



	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.contract.app.AbstractContractCostSplitControllerBean#_delete(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectPK[])
	 */
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		
		ContractCostSplitInviteHelper.setInvitePreSplitState(ctx, arrayPK, false);
		
		// TODO 自动生成方法存根
		if(arrayPK==null||arrayPK.length==0){
			return;
		}
		Set set=new HashSet(arrayPK.length);
		for(int i=0;i<arrayPK.length;i++){
			set.add(arrayPK[i].toString());
		}
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("id",set,CompareType.INCLUDE));
		view.getSelector().add("contractBill.id");
		
		ContractCostSplitCollection splits=getContractCostSplitCollection(ctx, view);
		set.clear();
		for(int i=0; i<splits.size(); i++){			
			BOSUuid costBillId=splits.get(i).getContractBill().getId();
//			checkBeforeDelete(ctx,splitBill);
			//删除汇总
			deleteCostSplit(ctx,CostSplitBillTypeEnum.CONTRACTSPLIT,costBillId);
			set.add(costBillId.toString());
		}
		//更新合同拆分状态
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("update T_CON_ContractBill set ");
		builder.appendParam("fsplitState",CostSplitStateEnum.NOSPLIT_VALUE);
		builder.appendSql(" , ");
		builder.appendParam("fisMeasureContract",new Integer(0));
		builder.appendSql(" where ");
		builder.appendParam("fid",set.toArray());
		builder.execute();
		
		for(int i=0;i<arrayPK.length;i++){
			synUpdateBillByRelation(ctx, BOSUuid.read(arrayPK[i].toString()), false);
		}
		super._delete(ctx, arrayPK);
	}

	/**
	 * 如果单据已审批，则不允许删除
	 * @param ctx
	 * @param splitBill
	 * @throws BOSException
	 * @throws EASBizException
	 */
//	private void checkBeforeDelete(Context ctx,ContractCostSplitInfo splitBill) throws BOSException,EASBizException {
//		if(splitBill == null || splitBill.getState() == null){
//			return;
//		}
//		if(FDCBillStateEnum.AUDITTED.equals(splitBill.getState())){
//			throw new CostSplitException(CostSplitException.ISAUDITTED);
//		}
//	}


	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean#_save(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectPK, com.kingdee.bos.dao.IObjectValue)
	 */
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		super._save(ctx, pk, model);	
		//拆分保存更新对应招标立项下对应的招标预先拆分的状态为已拆分
		ContractCostSplitInviteHelper.setInvitePreSplitState(ctx, pk, true);
	}
	
	public void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("id", billId.toString());
		view.getSelector().add("splitState");
		view.getSelector().add("contractBill.state");
		ContractCostSplitCollection c = getContractCostSplitCollection(ctx, view);
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
	
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._unAudit(ctx, billId);
		
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("contractBill.state");
		sic.add("splitState");
        sic.add(new SelectorItemInfo("contractBill.orgUnit.id"));
		
		ContractCostSplitInfo splitBill=(ContractCostSplitInfo)super._getValue(ctx,new ObjectUuidPK(billId.toString()),sic);
		boolean splitBeforeAudit = FDCUtils.getBooleanValue4FDCParamByKey(ctx, splitBill.getContractBill().getOrgUnit().getId().toString(),
				FDCConstants.FDC_PARAM_SPLITBFAUDIT);
		//该合同已经还没有拆分，不能审核
		if(splitBeforeAudit ){
//			MsgBox.showWarning(this, "该合同已经进行了拆分，不能进行修改");
//			SysUtil.abort();
			throw new ContractException(ContractException.SPLITBEFOREAUDIT);
		}
	}

	protected SelectorItemCollection setSelectorsEntry(Context ctx,SelectorItemCollection sic, boolean isEntry) throws BOSException, EASBizException{
		if(fdcCostSplit==null){
			fdcCostSplit=new FDCCostSplit(ctx);
		}
		return fdcCostSplit.setSelectorsEntry(sic, isEntry);
	}
	
	protected void _autoSplit4(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		
		//先根据比例更新科目金额
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.clear();
		builder.appendSql("update T_Con_ContractCostSplitEntry set FAmount= \n");
		builder.appendSql(" round( \n");
		builder.appendSql(" (FSplitScale*(select FAmount from T_Con_ContractBill where FID=(select FContractBillID from T_Con_ContractCostSplit where FID=T_Con_ContractCostSplitEntry.FParentID)) \n");
		builder.appendSql(" )/100,10) \n");
		builder.appendSql("where FLevel=0 and FParentID in ( \n");
		builder.appendSql("  select split.fid from T_Con_ContractCostSplit split \n");
		builder.appendSql("  inner join T_Con_ContractBill bill on bill.fid=split.FContractBillID \n");
		builder.appendSql("  where bill.FAmount>0 and bill.fid=? \n");
		builder.appendSql(" )");
		builder.addParam(billId.toString());
		builder.executeUpdate();
		builder.clear();
		
		
		AbstractObjectCollection coll=null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();	
    	SelectorItemCollection sic=view.getSelector();  
    	setSelectorsEntry(ctx,sic,true);
    	view.getSorter().add(new SorterItemInfo("seq"));
    	filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id", billId.toString()));
    	filter.getFilterItems().add(
				new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));			
    	view.setFilter(filter);
		coll = ContractCostSplitEntryFactory.getLocalInstance(ctx).getContractCostSplitEntryCollection(view);
		FDCSplitBillEntryCollection colls = new FDCSplitBillEntryCollection();
		for(Iterator iter=coll.iterator();iter.hasNext();){
			colls.add((FDCSplitBillEntryInfo)iter.next());
		}
		colls = apptAmount(ctx,colls);
		CoreBaseCollection coreBaseCollection = new CoreBaseCollection();
		for(Iterator iter=colls.iterator();iter.hasNext();){
			coreBaseCollection.add((FDCSplitBillEntryInfo)iter.next());
		}
		ContractCostSplitEntryFactory.getLocalInstance(ctx).update(coreBaseCollection);
		
		//更新表头金额，状态不更新
		builder.appendSql("update t_con_contractcostsplit set famount=( \n");
		builder.appendSql("	select sum(famount) from t_con_contractcostsplitentry where fparentid=t_con_contractcostsplit.fid and fisleaf=1 \n");
		builder.appendSql("	), foriginalamount=( \n");
		builder.appendSql("	select sum(famount) from t_con_contractcostsplitentry where fparentid=t_con_contractcostsplit.fid and fisleaf=1 \n");
		builder.appendSql("	) \n");
		builder.appendSql("	where fcontractbillid=? \n");
		builder.addParam(billId.toString());
		builder.executeUpdate();
		builder.clear();

		///////////////////////////////////////////////////////////////////////
		// 自动拆分，要更新全项目动态成本表上“已发生成本”，同_save操作
		///////////////////////////////////////////////////////////////////////

		EntityViewInfo tempView = new EntityViewInfo();
		FilterInfo tempFilter = new FilterInfo();
		SelectorItemCollection tempSic = tempView.getSelector();
		setSelectorsEntry(ctx, tempSic, true);
		// tempView.getSorter().add(new SorterItemInfo("seq"));
		tempFilter.getFilterItems().add(new FilterItemInfo("contractBill.id", billId.toString()));
		tempFilter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		tempView.setFilter(tempFilter);

		tempSic.add("*");
		tempSic.add("entrys.*");
		tempSic.add("creator.id");
		tempSic.add("creator.number");
		tempSic.add("creator.name");
		tempSic.add("auditor.id");
		tempSic.add("auditor.number");
		tempSic.add("auditor.name");
		tempSic.add("contractBill.id");
		tempSic.add("contractBill.number");
		tempSic.add("contractBill.name");
		tempSic.add("contractBill.state");
		tempSic.add("contractBill.amount");
		tempSic.add("contractBill.isMeasureContract");
		tempSic.add("contractBill.orgUnit.id");
		tempSic.add("contractBill.orgUnit.number");
		tempSic.add("contractBill.orgUnit.name");
		tempSic.add("entrys.apportionType.id");
		tempSic.add("entrys.apportionType.number");
		tempSic.add("entrys.apportionType.name");
		tempSic.add("entrys.costAccount.id");
		tempSic.add("entrys.costAccount.number");
		tempSic.add("entrys.costAccount.name");
		tempSic.add("entrys.costAccount.curProject.id");
		tempSic.add("entrys.costAccount.curProject.number");
		tempSic.add("entrys.costAccount.curProject.name");
		tempSic.add("entrys.product.id");
		tempSic.add("entrys.product.number");
		tempSic.add("entrys.product.name");
		//		tempSic.add("id");
		//		tempSic.add("createTime");
		//		tempSic.add("auditTime");
		//		tempSic.add("amount");
		//		tempSic.add("isConfirm");
		//		tempSic.add("state");
		//		tempSic.add("entrys.id");
		//		tempSic.add("entrys.seq");
		//		tempSic.add("entrys.level");
		//		tempSic.add("entrys.number");
		//		tempSic.add("entrys.otherRatioTotal");
		//		tempSic.add("entrys.price");
		//		tempSic.add("entrys.splitScale");
		//		tempSic.add("entrys.splitType");
		//		tempSic.add("entrys.workLoad");
		//		tempSic.add("entrys.apportionValueTotal");
		//		tempSic.add("entrys.directAmountTotal");
		//		tempSic.add("entrys.amount");

		setSelectorsEntry(ctx, tempSic, false);
		//清除掉集合中的重复值
		FdcObjectCollectionUtil.clearDuplicate(tempSic);

		// 处理拆分汇总
		ContractCostSplitCollection splitBillClos = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitCollection(tempView);
		if (FdcObjectCollectionUtil.isNotEmpty(splitBillClos) && splitBillClos.size() == 1) {
			ContractCostSplitInfo splitBillInfo = (ContractCostSplitInfo) splitBillClos.get(0);

			updateEntrySeq(ctx, splitBillInfo);
			//处理拆分汇总
			collectCostSplit(ctx, CostSplitBillTypeEnum.CONTRACTSPLIT, splitBillInfo.getContractBill(), splitBillInfo.getId(),
					splitBillInfo.getEntrys());
		}
	}
	
	/**
	 * 描述：分摊金额（调用FDCCostSplit接口）
	 * @return
	 */
    protected FDCSplitBillEntryCollection apptAmount(Context ctx,FDCSplitBillEntryCollection entrys){
    	    	
    	FDCSplitBillEntryInfo topEntry = null;
    	
    	for(Iterator iter=entrys.iterator();iter.hasNext();){
    		topEntry = (FDCSplitBillEntryInfo)iter.next();
	    	
			fdcCostSplit.apptAmount(entrys,topEntry);
    	}
    	return entrys;
    }
    /**
	 * 检查合同是否关联框架合约
	 * @param ctx
	 * @param contractId
	 * @return
	 * @throws BOSException
	 */
    protected String _checkIsExistProg(Context ctx, String contractId)
    		throws BOSException {
    	String flag = null;
    	String temp = null;
		String sql = "select fprogrammingcontract from t_con_contractbill where fid='"+ contractId +"'";
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx, sql.toString());
		IRowSet rs = fdcSB.executeQuery();
		try {
			if(rs.next()){
				flag = rs.getString("fprogrammingcontract");
				sql = "select fid from T_CON_PROGRAMMINGCONTRACT where fid='"+flag+"'";
				fdcSB = new FDCSQLBuilder(ctx, sql.toString());
				rs = fdcSB.executeQuery();
				if(rs.next()){
					temp = flag;
				}
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new BOSException(e);
		}
		return temp;
    }
    
    
	/**
	 * 1 .当合同未结算时(无最终结算或最终结算未审批)，规划余额=规划金额-（签约金额+变更金额），控制余额=控制金额-签约金额 2
	 * .当合同已结算时(最终结算已审批)，规划余额=规划金额-结算金额，控制余额=控制金额-结算金额 3
	 * .反写时点在合同单据审批通过时、变更签证申请审批通过时、变更签证确认结算时、合同结算审批通过时。 4.
	 * 合同修订审批后，规划余额=规划金额-（修订后的签约金额+变更金额），控制余额=控制金额-修订后的签约金额。
	 * 
	 * @param ctx
	 * @param billId
	 * @param flag 为真 则扣减余额,为false 则返还金额
	 * @throws EASBizException 
	 * @throws EASBizException
	 * @throws BOSException 
	 * @throws BOSException
	 * @throws SQLException
	 * @throws SQLException
	 */
	public static void synUpdateBillByRelation(Context ctx, BOSUuid billId, boolean flag) throws EASBizException, BOSException{
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("contractBill.curProject.isWholeAgeStage"); 
		sic.add("contractBill.programmingContract.id"); 
		sic.add("entrys.product.id"); 
		sic.add("entrys.costAccount.curProject.id"); 
		sic.add("isCostSplit"); 
		sic.add("isTeamSplit"); 
		sic.add("isProductSplit"); 
		
		Map<String, BigDecimal> VALUEMAP = new HashMap<String, BigDecimal>();
		IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
		IProgrammingTemp Itemp = ProgrammingTempFactory.getLocalInstance(ctx);
		String oql = "select programmings.id,amount where parent.id='"+billId+"'";
		
		if(billId.getType().equals(new ContractCostSplitInfo().getBOSType())){
			ContractCostSplitInfo conCostSplitInfo = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitInfo(new ObjectUuidPK(billId),sic);
			boolean IsWholeAgeStage = conCostSplitInfo.getContractBill().getCurProject().isIsWholeAgeStage();
			Set<String> curProjectSet = new HashSet<String>();
			Set<String> productSet = new HashSet<String>();
			Set<String> accountSet = new HashSet<String>();
			for (int i = 0; i <conCostSplitInfo.getEntrys().size(); i++) {
				ContractCostSplitEntryInfo entryInfo = conCostSplitInfo.getEntrys().get(i);
				if(entryInfo.getCostAccount()!=null)
					accountSet.add(entryInfo.getCostAccount().getId().toString());
				if(entryInfo.getCostAccount()!=null&&entryInfo.getCostAccount().getCurProject()!=null)
					curProjectSet.add(entryInfo.getCostAccount().getCurProject().getId().toString());
				if(entryInfo.getProduct()!=null)
					productSet.add(entryInfo.getProduct().getId().toString());
			}
			conCostSplitInfo.setIsCostSplit(accountSet.size()>=2?true:false);
			conCostSplitInfo.setIsTeamSplit(curProjectSet.size()>=2?true:false);
			conCostSplitInfo.setIsProductSplit(productSet.size()>0?true:false);
			ContractCostSplitFactory.getLocalInstance(ctx).updatePartial(conCostSplitInfo, sic);
			if(!IsWholeAgeStage||conCostSplitInfo.getContractBill().getProgrammingContract()!=null)
				return;
			
			ContractCostSplitEntryCollection coll = ContractCostSplitEntryFactory.getLocalInstance(ctx).getContractCostSplitEntryCollection(oql);
			for (int i = 0; i < coll.size(); i++) {
				ContractCostSplitEntryInfo entryInfo = coll.get(i);
				
				if(entryInfo.getProgrammings()==null)
					continue;
				entryInfo.getProgrammings();
				
				String progId = entryInfo.getProgrammings().getId().toString();
				if(VALUEMAP.get(progId)==null)
					VALUEMAP.put(progId,UIRuleUtil.getBigDecimal(entryInfo.getAmount()));
				else
					VALUEMAP.put(progId,FDCHelper.add(VALUEMAP.get(progId), entryInfo.getAmount()));
			}
		}else if(billId.getType().equals(new ConChangeSplitInfo().getBOSType())){
			ConChangeSplitInfo conCostSplitInfo = ConChangeSplitFactory.getLocalInstance(ctx).getConChangeSplitInfo(new ObjectUuidPK(billId),sic);
			boolean IsWholeAgeStage = conCostSplitInfo.getContractBill().getCurProject().isIsWholeAgeStage();
			Set<String> curProjectSet = new HashSet<String>();
			Set<String> productSet = new HashSet<String>();
			Set<String> accountSet = new HashSet<String>();
			for (int i = 0; i <conCostSplitInfo.getEntrys().size(); i++) {
				ConChangeSplitEntryInfo entryInfo = conCostSplitInfo.getEntrys().get(i);
				if(entryInfo.getCostAccount()!=null)
					accountSet.add(entryInfo.getCostAccount().getId().toString());
				if(entryInfo.getCostAccount()!=null&&entryInfo.getCostAccount().getCurProject()!=null)
					curProjectSet.add(entryInfo.getCostAccount().getCurProject().getId().toString());
				if(entryInfo.getProduct()!=null)
					productSet.add(entryInfo.getProduct().getId().toString());
			}
			conCostSplitInfo.setIsCostSplit(accountSet.size()>=2?true:false);
			conCostSplitInfo.setIsTeamSplit(curProjectSet.size()>=2?true:false);
			conCostSplitInfo.setIsProductSplit(productSet.size()>0?true:false);
			ConChangeSplitFactory.getLocalInstance(ctx).updatePartial(conCostSplitInfo, sic);
			
			if(!IsWholeAgeStage||conCostSplitInfo.getContractBill().getProgrammingContract()!=null)
				return;
			ConChangeSplitEntryCollection coll = ConChangeSplitEntryFactory.getLocalInstance(ctx).getConChangeSplitEntryCollection(oql);
			for (int i = 0; i < coll.size(); i++) {
				ConChangeSplitEntryInfo entryInfo = coll.get(i);
				
				if(entryInfo.getProgrammings()==null)
					continue;
				entryInfo.getProgrammings();
				
				String progId = entryInfo.getProgrammings().getId().toString();
				if(VALUEMAP.get(progId)==null)
					VALUEMAP.put(progId,UIRuleUtil.getBigDecimal(entryInfo.getAmount()));
				else
					VALUEMAP.put(progId,FDCHelper.add(VALUEMAP.get(progId), entryInfo.getAmount()));
			}
		}else{
			return;
		}
		
		Iterator<Entry<String, BigDecimal>> iterator = VALUEMAP.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, BigDecimal> entity = iterator.next();
			
			String key = entity.getKey();
			BigDecimal value = entity.getValue();
			
			SelectorItemCollection sict = new SelectorItemCollection();
			sict.add("name");
			sict.add("balance");
			ProgrammingContractInfo progInfo = service.getProgrammingContractInfo(new ObjectUuidPK(key),sict);
			BigDecimal banlance = UIRuleUtil.getBigDecimal(progInfo.getBalance());
			
			if(flag&&banlance.compareTo(value)==-1){
				throw new EASBizException(new NumericExceptionSubItem("100","["+progInfo.getName()+"] 余额不足。\n余额："+banlance+"\n本次累计拆分："+value));
			}
		}
		SelectorItemCollection sict = new SelectorItemCollection();
		sict.add("balance");
		sict.add("controlBalance");
		sict.add("signUpAmount");
		sict.add("changeAmount");
		sict.add("settleAmount");
		sict.add("srcId");
		
		
		iterator = VALUEMAP.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, BigDecimal> entity = iterator.next();
			
			String key = entity.getKey();
			BigDecimal value = entity.getValue();
			
			ProgrammingContractInfo pcInfo = service.getProgrammingContractInfo(new ObjectUuidPK(key),sict);
			
			// 规划余额
			BigDecimal balanceAmt = pcInfo.getBalance();
			// 框架合约签约金额
			BigDecimal signUpAmount = pcInfo.getSignUpAmount();
			BigDecimal otherSignUpAmount = FDCHelper.ZERO;
			if (flag) {
				pcInfo.setBalance(FDCHelper.subtract(balanceAmt, value));
				otherSignUpAmount = FDCHelper.subtract(FDCHelper.add(signUpAmount, value), signUpAmount);
				
				ProgrammingTempInfo tempInfo = new ProgrammingTempInfo();
				tempInfo.setSubAmount(value);
				tempInfo.setProgrammingId(key);
				tempInfo.setNumber(billId.toString());
				
				Itemp.addnew(tempInfo);
				
				service.updatePartial(pcInfo, sict);
				
				updateHisVersionAmount(ctx, service, pcInfo.getId().toString(), sict, otherSignUpAmount);
			} else {
				reqSubAmount(ctx, service, Itemp, sict, billId);
			}
		}
	}
	
	public static void reqSubAmount(Context ctx,IProgrammingContract service,IProgrammingTemp Itemp,SelectorItemCollection sict,BOSUuid billId) throws EASBizException, BOSException{
		String deleteOql = "select programmingId,subAmount where number='"+billId+"'";
		ProgrammingTempCollection programmingColl = Itemp.getProgrammingTempCollection(deleteOql);
		
		for (int i = 0; i < programmingColl.size(); i++) {
			ProgrammingTempInfo tempInfo = programmingColl.get(0);
			
			ProgrammingContractInfo pcInfo = service.getProgrammingContractInfo(new ObjectUuidPK(tempInfo.getProgrammingId()), sict);
			// 规划余额
			BigDecimal balanceAmt = pcInfo.getBalance();
			// 框架合约签约金额
			BigDecimal signUpAmount = pcInfo.getSignUpAmount();
			BigDecimal otherSignUpAmount = FDCHelper.ZERO;
			
			BigDecimal amount = UIRuleUtil.getBigDecimal(tempInfo.getSubAmount());
			
			pcInfo.setBalance(FDCHelper.add(balanceAmt, amount));
			
			service.updatePartial(pcInfo, sict);
			otherSignUpAmount = FDCHelper.subtract(FDCHelper.subtract(signUpAmount, amount), signUpAmount);			
			Itemp.delete("where number='"+billId+"' and programmingId='"+tempInfo.getProgrammingId()+"'");
			
			updateHisVersionAmount(ctx, service, pcInfo.getId().toString(), sict, otherSignUpAmount);
		}
	}
	
	public static void updateHisVersionAmount(Context ctx,IProgrammingContract service,String progId,SelectorItemCollection sict,BigDecimal otherSignUpAmount) throws EASBizException, BOSException{
		Set<String> idSet = new HashSet<String>();
		getNewVersionAllProgId(ctx,idSet, progId);
		Iterator<String> iterator2 = idSet.iterator();
		while(iterator2.hasNext()){
			String nextVersionProgId = iterator2.next();
			ProgrammingContractInfo pcInfo = service.getProgrammingContractInfo(new ObjectUuidPK(nextVersionProgId), sict);
			pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(), otherSignUpAmount));
			service.updatePartial(pcInfo, sict);
		}
	}
	
	public static FDCSQLBuilder builder = null;

	public static void getNewVersionAllProgId(Context ctx,Set<String> idSet,String progId){
		if(builder==null)
			builder = new FDCSQLBuilder(ctx);
		builder.clear();
		String tempId = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select fid from t_con_programmingContract where  ");
		builder.appendParam("FSrcId", progId);
		try {
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				tempId = rowSet.getString("fid");
				if(UIRuleUtil.isNotNull(tempId))
					idSet.add(tempId);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(tempId!=null)
			getNewVersionAllProgId(ctx, idSet, tempId);
	}
	
	public static void getAfterVersionAllProgId(Context ctx,Set<String> idSet,String progId){
		if(builder==null)
			builder = new FDCSQLBuilder(ctx);
		builder.clear();
		String tempId = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select FSrcId from t_con_programmingContract where  ");
		builder.appendParam("fid", progId);
		try {
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				tempId = rowSet.getString("fid");
				if(UIRuleUtil.isNotNull(tempId))
					idSet.add(tempId);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(tempId!=null)
			getAfterVersionAllProgId(ctx, idSet, progId);
	}
    
}