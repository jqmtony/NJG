package com.kingdee.eas.fdc.contract.web;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractWebHelper {
	/**
	 * 取得成本信息以及合同相关信息
	 * @param ctx
	 * @param contractId
	 * @return
	 * @throws BOSException
	 */
	public static Map getCostInfo(Context ctx,String contractId) throws BOSException{
		Set acctSet=getSplitAccts(ctx, contractId);
		if(acctSet==null||acctSet.size()==0){
			return new HashMap();
		}
		Map costMap=mergeCostData(ctx, getHappendCost(ctx, acctSet), getAcctAimCostData(ctx, acctSet), getAcctAdjustCostData(ctx, acctSet),getConSplitData(ctx, acctSet,contractId));
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("id",acctSet,CompareType.INCLUDE));
		view.getSelector().add("id");
		view.getSelector().add("longNumber");
		view.getSelector().add("isLeaf");
		view.getSelector().add("name");
		view.getSelector().add("curProject.id");
		view.getSelector().add("curProject.longNumber");
		view.getSelector().add("curProject.name");
		view.getSorter().add(new SorterItemInfo("curProject.longNumber"));
		view.getSorter().add(new SorterItemInfo("longNumber"));
		CostAccountCollection accts=null;
		if(ctx!=null){
			accts=CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		}else{
			accts=CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
		}
		costMap.put("CostAccountCollection", accts);
		return costMap;
	}
	
	private static Set getSplitAccts(Context ctx,String contractId) throws BOSException{
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select distinct fcostAccountid from T_Con_ContractCostSplitEntry entry ");
		builder.appendSql(" inner join T_Con_ContractCostSplit head on entry.fparentid=head.fid ");
		builder.appendSql(" inner join T_FDC_CostAccount acct on acct.fid=entry.fcostaccountid ");
		builder.appendSql(" inner join T_FDC_CurProject prj on prj.fid=acct.fcurProject ");
		builder.appendSql(" where head.fcontractBillId=? and acct.fisleaf=1 and prj.fisleaf=1 ");
		builder.addParam(contractId);
		Set set=new HashSet();
		try{
			IRowSet rowSet=builder.executeQuery();
			while(rowSet.next()){
				set.add(rowSet.getString("fcostAccountid"));
			}
		}catch (SQLException e) {
			throw new BOSException(e);
		}
		return set;
	}
	private static Map getHappendCost(Context ctx,Set acctSet) throws BOSException{
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select acctId, sum(amount) as amount from (");
		builder.appendSql(" select entry.FCostAccountId acctId ,sum(entry.famount) amount ");
		builder.appendSql(" from T_AIM_CostSplitEntry entry ");
		builder.appendSql(" inner join T_AIM_CostSplit head on entry.FParentID=head.FId ");
		builder.appendSql(" inner join T_CON_ContractBill con on head.FCostBillID=con.FID  ");
		builder.appendSql(" where FCostBillType=? and head.FIsInvalid=0 And FIsProduct=0 and (fhasSettled is null or fhasSettled=0) and ");
		builder.addParam(CostSplitBillTypeEnum.CONTRACTSPLIT_VALUE);
		builder.appendParam("entry.fcostaccountid", acctSet.toArray());
		builder.appendSql("group by FCostAccountId ");
		
		builder.appendSql(" union all ");
		
		//get changsplit 
		builder.appendSql(" select entry.FCostAccountId acctId ,sum(entry.famount) amount ");
		builder.appendSql(" from T_AIM_CostSplitEntry entry ");
		builder.appendSql(" inner join T_AIM_CostSplit head on entry.FParentID=head.FId ");
		builder.appendSql(" inner join T_CON_ContractChangeBill change on head.FCostBillID=change.FID ");
		builder.appendSql(" inner join T_CON_ContractBill con on change.FContractBillID=con.FID ");
		builder.appendSql(" where FCostBillType=? and head.FIsInvalid=0 And FIsProduct=0 and (con.fhasSettled=0 or con.fhasSettled is null) and ");
		builder.addParam(CostSplitBillTypeEnum.CNTRCHANGESPLIT_VALUE);
		builder.appendParam("entry.fcostaccountid", acctSet.toArray());
		builder.appendSql("group by FCostAccountId ");

		builder.appendSql(" union all ");
		
		//get settlement
		builder.appendSql(" select entry.FCostAccountId acctId ,sum(entry.famount) amount ");
		builder.appendSql(" from T_AIM_CostSplitEntry entry ");
		builder.appendSql(" inner join T_AIM_CostSplit head on entry.FParentID=head.FId ");
		builder.appendSql(" inner join T_CON_ContractSettlementBill settle on head.FCostBillID=settle.FID ");
		builder.appendSql(" where FCostBillType=? and head.FIsInvalid=0 And FIsProduct=0 and settle.FIsSettled=1 and settle.FIsFinalSettle=1 and ");
		builder.addParam(CostSplitBillTypeEnum.SETTLEMENTSPLIT_VALUE);
		builder.appendParam("entry.fcostaccountid", acctSet.toArray());
		builder.appendSql(" group by FCostAccountId ");
		
		builder.appendSql(" union all ");
		
		//get contract withoutext 
		builder.appendSql(" select entry.FCostAccountId acctId ,sum(entry.famount) amount ");
		builder.appendSql(" from T_AIM_CostSplitEntry entry ");
		builder.appendSql(" inner join T_AIM_CostSplit head on entry.FParentID=head.FId ");
		builder.appendSql(" where FCostBillType=? and head.FIsInvalid=0 And FIsProduct=0  and ");
		builder.addParam(CostSplitBillTypeEnum.NOTEXTCONSPLIT_VALUE);
		builder.appendParam("entry.fcostaccountid", acctSet.toArray());
		builder.appendSql(" group by FCostAccountId ");
		
		builder.appendSql(") t ");
		builder.appendSql(" group by acctId ");
		Map happendMap=new HashMap();
		try {
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				String acctId=rowSet.getString("acctId");
				BigDecimal amount = rowSet.getBigDecimal("amount");
				happendMap.put(acctId, FDCHelper.add(happendMap.get(acctId),amount));
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		
		
		return happendMap;
	}
	
	private static ContractBillCollection getContracts(Context ctx,Set contractIdSet) throws BOSException{
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("id",contractIdSet,CompareType.INCLUDE));
		view.getSorter().add(new SorterItemInfo("hasSettled"));
		view.getSorter().add(new SorterItemInfo("number"));
		view.getSelector().add("id");
		view.getSelector().add("number");
		view.getSelector().add("name");
		view.getSelector().add("amount");
		view.getSelector().add("settleAmt");
		view.getSelector().add("hasSettled");
		view.getSelector().add("settleAmt");
		if(ctx!=null){
			return ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
		}else{
			return ContractBillFactory.getRemoteInstance().getContractBillCollection(view);
		}
	}
	private static Map getConSplitData(Context ctx,Set acctSet,String contractId) throws BOSException{
		Map conSplitMap = new HashMap();
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql(" select entry.fcostaccountid as acctid,sum(entry.FAmount) FAmount from T_CON_ContractCostSplitEntry entry ");
			builder.appendSql(" inner join T_CON_ContractCostSplit split on entry.FParentID = split.FID ");
			builder.appendSql(" inner join T_FDC_CurProject prj on split.FCurProjectID = prj.FID ");
			builder.appendSql(" inner join T_FDC_CostAccount account on account.FID = entry.FCostAccountID ");
			builder.appendSql(" inner join T_CON_ContractBill con on con.fid = split.FContractBillID ");
			builder.appendSql(" where split.FIsInvalid=0 and entry.FIsLeaf = ? ");
			builder.addParam(new Integer(1));
			builder.appendParam(" and account.FID ",acctSet.toArray());
			builder.appendSql(" and con.FID=? ");
			//当存在多产品时、覆盖了
			builder.appendSql("  group by entry.fcostaccountid ");
			builder.addParam(contractId);
			IRowSet rowSet = builder.executeQuery();
			while(rowSet.next()){
				String acctId=rowSet.getString("acctid");
				conSplitMap.put(acctId,FDCHelper.toBigDecimal(rowSet.getBigDecimal("FAmount")));
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		return conSplitMap;
	}
	private static Map getAcctAimCostData(Context ctx, Set acctSet) throws BOSException {
		Map aimCostMap = new HashMap();
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select entry.fcostaccountid as acctid,sum(entry.fcostamount) as amount from T_AIM_AimCost head ");
			builder.appendSql(" inner join T_AIM_CostEntry entry on entry.fheadid=head.fid where ");
			builder.appendParam("entry.fcostAccountid", acctSet.toArray());
			builder.appendSql(" and head.fisLastVersion=1 group by entry.fcostaccountid ");
			final IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				String acctId = rowSet.getString("acctid");
				aimCostMap.put(acctId, FDCHelper.add(aimCostMap.get(acctId), rowSet.getBigDecimal("amount")));
			}

		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return aimCostMap;

	}
	
	private static Map getAcctAdjustCostData(Context ctx, Set acctSet) throws BOSException {
		Map dynamicCostMap = new HashMap();
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select FAccountID as acctid,FAdjustSumAmount as amount from T_AIM_DynamicCost dyn where ");
			builder.appendParam("dyn.faccountid", acctSet.toArray());
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				String acctId = rowSet.getString("acctid");
				dynamicCostMap.put(acctId, FDCHelper.add(dynamicCostMap.get(acctId), rowSet.getBigDecimal("amount")));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return dynamicCostMap;
	}
	/***
	 * 包含该合同的成本拆分信息
	 * 彭伶添加，不应该直接加参数，应该重载方法，增加参数
	 * @param ctx
	 * @param happendMap
	 * @param aimCostMap
	 * @param adjustMap
	 * @param conSplitMap
	 * @return
	 */
	private static Map mergeCostData(Context ctx,Map happendMap,Map aimCostMap,Map adjustMap,Map conSplitMap){
		if(aimCostMap==null){
			aimCostMap=new  HashMap();
		}
		if(adjustMap==null){
			adjustMap=new  HashMap();
		}
		if(conSplitMap==null){
			conSplitMap= new HashMap();
		}
		Set acctSet=new HashSet();
		Map costMap=new HashMap();
		costMap.put("contractInfos", happendMap.remove("contractInfos"));
		acctSet.addAll(aimCostMap.keySet());
		acctSet.addAll(adjustMap.keySet());
		acctSet.addAll(happendMap.keySet());
		acctSet.addAll(conSplitMap.keySet());
		for(Iterator iter=acctSet.iterator();iter.hasNext();){
			final Object key = iter.next();
			BigDecimal aimCost=(BigDecimal)aimCostMap.get(key);
			BigDecimal adjustCost=(BigDecimal)adjustMap.get(key);
			BigDecimal happenCost=(BigDecimal)happendMap.get(key);
			BigDecimal dyn = FDCNumberHelper.add(aimCost, adjustCost);
			BigDecimal intending= FDCNumberHelper.subtract(dyn, happenCost);
			BigDecimal conSplit=FDCHelper.toBigDecimal(conSplitMap.get(key));
			costMap.put(key+"_aim", aimCost);
			costMap.put(key+"_adjust", adjustCost);//dif
			costMap.put(key+"_happen", happenCost);
			costMap.put(key+"_dyn", dyn);
			costMap.put(key+"_intending", intending);
			costMap.put(key+"_conSplit", conSplit);
		}
		return costMap;
	}
	

	
	/**
	 * 取相关合同及其拆分信息
	 * @param ctx
	 * @param costAcctId
	 * @return
	 * @throws BOSException
	 */
	public static ContractBillCollection getRelationCon(Context ctx,String costAcctId) throws BOSException{
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql(" select head.FCostBillID contractId,entry.FCostAccountId acctId ,con.FHasSettled hasSettled,entry.famount amount ");
		builder.appendSql(" from T_AIM_CostSplitEntry entry ");
		builder.appendSql(" inner join T_AIM_CostSplit head on entry.FParentID=head.FId ");
		builder.appendSql(" inner join T_CON_ContractBill con on head.FCostBillID=con.FID  ");
		builder.appendSql(" where FCostBillType=? and head.FIsInvalid=0 And FIsProduct=0 and ");
		builder.addParam(CostSplitBillTypeEnum.CONTRACTSPLIT_VALUE);
		builder.appendParam("entry.fcostaccountid", costAcctId);
		Map contractSplitMap=new HashMap();
		Set contractIdSet=new HashSet();
		try {
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				String contractId=rowSet.getString("contractId");
				BigDecimal amount=rowSet.getBigDecimal("amount");
				contractIdSet.add(contractId);
				//合同拆分金额
				contractSplitMap.put(contractId, FDCHelper.add(contractSplitMap.get(contractId), amount));
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		if(contractIdSet==null||contractIdSet.size()==0){
			return new ContractBillCollection();
		}
		//getContract
		Map contractMap=new HashMap();
		ContractBillCollection cons=getContracts(ctx, contractIdSet);
		for(Iterator iter=cons.iterator();iter.hasNext();){
			ContractBillInfo info=(ContractBillInfo)iter.next();
			String id = info.getId().toString();
			info.setBigDecimal("splitAmt",(BigDecimal)contractSplitMap.get(id));
			contractMap.put(id, info);
		}
		//get settlement
		builder.clear();
		builder.appendSql(" select entry.famount amount,settle.fcontractBillId contractId");
		builder.appendSql(" from T_AIM_CostSplitEntry entry ");
		builder.appendSql(" inner join T_AIM_CostSplit head on entry.FParentID=head.FId ");
		builder.appendSql(" inner join T_CON_ContractSettlementBill settle on head.FCostBillID=settle.FID ");
		builder.appendSql(" where FCostBillType=? and head.FIsInvalid=0 And FIsProduct=0 and settle.FIsSettled=1 and settle.FIsFinalSettle=1 and ");
		builder.addParam(CostSplitBillTypeEnum.SETTLEMENTSPLIT_VALUE);
		builder.appendParam("entry.fcostaccountid", costAcctId);
		try {
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				String contractId=(String)rowSet.getString("contractId");
				BigDecimal amount = rowSet.getBigDecimal("amount");
				ContractBillInfo info=(ContractBillInfo)contractMap.get(contractId);
				info.setBigDecimal("settleSplitAmt", amount);
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		return cons;
	}
	/***
	 * 不包含合同的成本拆分数据信息
	 * @param ctx
	 * @param happendMap
	 * @param aimCostMap
	 * @param adjustMap
	 * @return
	 */
	private static Map mergeCostData(Context ctx,Map happendMap,Map aimCostMap,Map adjustMap){
		if(aimCostMap==null){
			aimCostMap=new  HashMap();
		}
		if(adjustMap==null){
			adjustMap=new  HashMap();
		}
		Set acctSet=new HashSet();
		Map costMap=new HashMap();
		costMap.put("contractInfos", happendMap.remove("contractInfos"));
		acctSet.addAll(aimCostMap.keySet());
		acctSet.addAll(adjustMap.keySet());
		acctSet.addAll(happendMap.keySet());
		for(Iterator iter=acctSet.iterator();iter.hasNext();){
			final Object key = iter.next();
			BigDecimal aimCost=(BigDecimal)aimCostMap.get(key);
			BigDecimal adjustCost=(BigDecimal)adjustMap.get(key);
			BigDecimal happenCost=(BigDecimal)happendMap.get(key);
			BigDecimal dyn = FDCNumberHelper.add(aimCost, adjustCost);
			BigDecimal intending= FDCNumberHelper.subtract(dyn, happenCost);
			costMap.put(key+"_aim", aimCost);
			costMap.put(key+"_adjust", adjustCost);//dif
			costMap.put(key+"_happen", happenCost);
			costMap.put(key+"_dyn", dyn);
			costMap.put(key+"_intending", intending);
		}
		return costMap;
	}
	public static Map getCostInfo(Context ctx,Set acctSet) throws BOSException{
		Map costMap=mergeCostData(ctx, getHappendCost(ctx, acctSet), getAcctAimCostData(ctx, acctSet), getAcctAdjustCostData(ctx, acctSet));
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("id",acctSet,CompareType.INCLUDE));
		view.getSelector().add("id");
		view.getSelector().add("longNumber");
		view.getSelector().add("isLeaf");
		view.getSelector().add("name");
		view.getSelector().add("curProject.id");
		view.getSelector().add("curProject.longNumber");
		view.getSelector().add("curProject.name");
		view.getSorter().add(new SorterItemInfo("curProject.longNumber"));
		view.getSorter().add(new SorterItemInfo("longNumber"));
		CostAccountCollection accts=null;
		if(ctx!=null){
			accts=CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		}else{
			accts=CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
		}
		costMap.put("CostAccountCollection", accts);
		return costMap;
	}
	
}
