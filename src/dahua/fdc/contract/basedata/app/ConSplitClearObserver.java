package com.kingdee.eas.fdc.basedata.app;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCostSplit;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ClearSplitFacadeFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.SettlementCostSplitCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitInfo;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 合同拆分观察者类, 用于更新自己
 * @author liupd
 *
 */
public class ConSplitClearObserver implements Observer {

	public void update(Observable o, Object arg) {
		ProjectObservable observalbe = (ProjectObservable)o;
		Map map=(Map)arg;
		if(map==null) return;
		try {
			IRowSet rowSet = getRefreshRowSet(map, observalbe.getCtx());
			FDCCostSplit fdcCostSplit=new FDCCostSplit(observalbe.getCtx());
			while (rowSet.next()) {
				String contractSplitId = rowSet.getString("fid");
				String contractId = rowSet.getString("fcontractBillId");
				clearSplit(observalbe.getCtx(), contractSplitId, contractId,fdcCostSplit);
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void clearSplit(Context ctx,String contractSplitId,String contractId,FDCCostSplit fdcCostSplit) throws Exception{
		//作废之前取得所有要刷新的拆分单的ID
		if(contractSplitId==null||contractId==null){
			return;
		}
		
		SelectorItemCollection _selector=new SelectorItemCollection();
		_selector.add("*");
		fdcCostSplit.setSelectorsEntry(_selector, false);
		SelectorItemCollection selector=(SelectorItemCollection)_selector.clone();
		selector.add("contractBill.id");
		ContractCostSplitInfo contractCostSplitInfo = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitInfo(new ObjectUuidPK(contractSplitId), selector);
		
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("contractChange.contractBill.id", contractId);
		view.getFilter().getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
		view.put("selector", _selector.clone());
		view.getSelector().add("contractChange.id");
		ConChangeSplitCollection changeSplitInfos = ConChangeSplitFactory.getLocalInstance(ctx).getConChangeSplitCollection(view);
		
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("settlementBill.contractBill.id", contractId);
		view.getFilter().getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
		view.put("selector", _selector.clone());
		view.getSelector().add("settlementBill.id");
		view.getSelector().add("settlementBill.settlePrice");
		view.getSelector().add("settlementBill.qualityGuarante");
		SettlementCostSplitCollection settlementCostSplits = SettlementCostSplitFactory.getLocalInstance(ctx).getSettlementCostSplitCollection(view);
		
		ClearSplitFacadeFactory.getLocalInstance(ctx).clearAllSplit(contractId, true);
		
		addNewContractSplit(ctx, contractCostSplitInfo,fdcCostSplit);
		addNewChangeSplit(ctx, changeSplitInfos,fdcCostSplit);
		addNewSettleSplit(ctx, settlementCostSplits,fdcCostSplit);
		//addNew
	}
	
	private void addNewContractSplit(Context ctx,ContractCostSplitInfo contractCostSplitInfo,FDCCostSplit fdcCostSplit) throws Exception{
		contractCostSplitInfo.setId(BOSUuid.create(contractCostSplitInfo.getBOSType()));
		for(Iterator iter=contractCostSplitInfo.getEntrys().iterator();iter.hasNext();){
			ContractCostSplitEntryInfo entry=(ContractCostSplitEntryInfo)iter.next();
			entry.setId(null);
			entry.setParent(contractCostSplitInfo);
		}
		fdcCostSplit.refreshApportionAmount(contractCostSplitInfo, null);//不用更新
		ContractCostSplitFactory.getLocalInstance(ctx).save(contractCostSplitInfo);
	}
	
	private void addNewChangeSplit(Context ctx,ConChangeSplitCollection changeSplitInfos,FDCCostSplit fdcCostSplit) throws Exception{

		for(Iterator iter=changeSplitInfos.iterator();iter.hasNext();){
			ConChangeSplitInfo info=(ConChangeSplitInfo)iter.next();
			info.setId(null);
			for(Iterator iter2=info.getEntrys().iterator();iter2.hasNext();){
				ConChangeSplitEntryInfo entry=(ConChangeSplitEntryInfo)iter2.next();
				entry.setId(null);
				entry.setParent(info);
			}
			fdcCostSplit.refreshApportionAmount(info, null);//不用更新
			ConChangeSplitFactory.getLocalInstance(ctx).save(info);
		}
	}
	
	private void addNewSettleSplit(Context ctx,SettlementCostSplitCollection settlementCostSplits,FDCCostSplit fdcCostSplit) throws Exception{
		for(Iterator iter=settlementCostSplits.iterator();iter.hasNext();){
			SettlementCostSplitInfo info=(SettlementCostSplitInfo)iter.next();
			info.setId(null);
			for(Iterator iter2=info.getEntrys().iterator();iter2.hasNext();){
				SettlementCostSplitEntryInfo entry=(SettlementCostSplitEntryInfo)iter2.next();
				entry.setId(null);
				entry.setParent(info);
			}
			fdcCostSplit.refreshSettlementSplitApportionAmount(info, null);
			SettlementCostSplitFactory.getLocalInstance(ctx).save(info);
		}
	}
	/* param格式：param内
	 * refreshSrcList:list
	 * List内放置为子map：
	 * 1.projId 工程项目在ID
	 * 2.productId 产品ID 如果没有产品则为空
	 * 3.apportionsId 指标IDList
	 * @see com.kingdee.eas.fdc.basedata.app.AbstractProjectIndexDataControllerBean#_idxRefresh(com.kingdee.bos.Context, java.util.Map)
	 */
	private IRowSet getRefreshRowSet(Map param,Context ctx) throws BOSException {
		List refreshSrcList = (List) param.get("refreshSrcList");
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String sql = "select distinct a.fid,a.fcontractBillId from T_CON_ContractCostSplit a 						\n" 
				+ "inner join T_CON_ContractCostSplitEntry b on a.FID = b.FParentID 			\n"
				+ "inner join T_FDC_CostAccount C on b.FCostAccountID = c.FID 				\n" 
				+ "where a.fstate<> ? and ( ";
		builder.appendSql(sql);
		builder.addParam(FDCBillStateEnum.INVALID_VALUE);

		//构造合同查询
		int index=0;
		for (Iterator iter = refreshSrcList.iterator(); iter.hasNext();index++) {
			Map dataMap = (Map) iter.next();
			String prjId = (String) dataMap.get("projId");
			if(prjId==null){
				continue;
			}
			String productId = (String) dataMap.get("productId");
			Set apportions = (Set) dataMap.get("apportionsId");
			if(index==0){
				builder.appendSql("(c.FCurProject  = ? ");
			}else{
				builder.appendSql(" or (c.FCurProject  = ? ");
			}
			builder.addParam(prjId);
			Boolean includeAllProduct=(Boolean)dataMap.get("includeAllProduct");
			if(includeAllProduct==null||!includeAllProduct.booleanValue()){
				if (productId != null) {
					builder.appendParam("and b.fproductid", productId);
				}else{
					builder.appendSql("and b.fproductid is null");
				}
			}
			if(apportions!=null&&apportions.size()>0){ 
				builder.appendParam("and b.fidxApportionId", apportions.toArray()); 
			}
			builder.appendSql(") ");

		}
		
		builder.appendSql(" or a.fcontractBillId in (");
		builder.appendSql("select distinct con.fid from T_CON_ConChangeSplit a1 \n" 
				+ "inner join T_CON_ConChangeSplitEntry b1 on a1.FID = b1.FParentID \n"
				+ "inner join T_FDC_CostAccount c1 on b1.FCostAccountID = c1.FID \n" 
				+ "inner join T_CON_ContractChangeBill change on a1.fcontractchangeid=change.fid \n"
				+ "inner join T_CON_ContractBill con on con.fid=change.fcontractBillId \n" 
				+ "where a1.fstate<> ? and (\n");
		builder.addParam(FDCBillStateEnum.INVALID_VALUE);
		index=0;
		for (Iterator iter = refreshSrcList.iterator(); iter.hasNext();index++) {
			Map dataMap = (Map) iter.next();
			String prjId = (String) dataMap.get("projId");
			if(prjId==null){
				continue;
			}
			String productId = (String) dataMap.get("productId");
			Set apportions = (Set) dataMap.get("apportionsId");
			if(index==0){
				builder.appendSql("(c1.FCurProject  = ? ");
			}else{
				builder.appendSql(" or (c1.FCurProject  = ? ");
			}
			builder.addParam(prjId);
			Boolean includeAllProduct=(Boolean)dataMap.get("includeAllProduct");
			if(includeAllProduct==null||!includeAllProduct.booleanValue()){
				if (productId != null) {
					builder.appendParam("and b1.fproductid", productId);
				}else{
					builder.appendSql("and b1.fproductid is null");
				}
			}
			
			
			if(apportions!=null&&apportions.size()>0){ 
				builder.appendParam("and b1.fidxApportionId", apportions.toArray()); 
			}
			builder.appendSql(") ");

		}
		builder.appendSql("))) ");
		return builder.executeQuery();
	}
}