package com.kingdee.eas.fdc.basedata.app;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCostSplitAdapter;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 合同变更拆分观察者类, 用于更新自己
 * @author liupd
 *
 */
public class ConChangeSplitObserver implements Observer {

	public void update(Observable o, Object arg) {
		ProjectObservable observable = (ProjectObservable)o;
		Map map=(Map)arg;
		if(map==null) return;
		try {
			IRowSet rowSet = getRefreshRowSet(map, observable.getCtx());
			FDCCostSplitAdapter adp = new FDCCostSplitAdapter(observable.getCtx());
			while (rowSet.next()) {
				String id = rowSet.getString("fid");
				adp.refreshConChange(id);
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
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
		builder.appendSql("select distinct a1.fid from T_CON_ConChangeSplit a1 \n" 
				+ "inner join T_CON_ConChangeSplitEntry b1 on a1.FID = b1.FParentID \n"
				+ "inner join T_FDC_CostAccount c1 on b1.FCostAccountID = c1.FID \n" 
				+ "inner join T_CON_ContractChangeBill change on a1.fcontractchangeid=change.fid \n"
				+ "inner join T_CON_ContractBill con on con.fid=change.fcontractBillId \n" 
				+ "where a1.fstate<> ? and (\n");
		builder.addParam(FDCBillStateEnum.INVALID_VALUE);
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
		builder.appendSql(") ");
		return builder.executeQuery();
	}

}
