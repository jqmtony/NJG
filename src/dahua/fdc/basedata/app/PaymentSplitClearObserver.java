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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCostSplit;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ClearSplitFacadeFactory;
import com.kingdee.eas.fdc.finance.PaySplitVoucherRefersEnum;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 付款拆分观察者类, 用于更新自己
 * @author liupd
 *
 */
public class PaymentSplitClearObserver implements Observer {

	public void update(Observable o, Object arg) {
		ProjectObservable observable = (ProjectObservable)o;
		Map map=(Map)arg;
		if(map==null) return;
		try {
			
			IRowSet rowSet = getRefreshRowSet(map, observable.getCtx());
			FDCCostSplit fdcCostSplit=new FDCCostSplit(observable.getCtx());
			while (rowSet.next()) {
				String id = rowSet.getString("fid");
				String conID=rowSet.getString("conID");
				clearSplit(observable.getCtx(), id, conID, fdcCostSplit);
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	private void clearSplit(Context ctx,String splitId,String conId,FDCCostSplit fdcCostSplit) throws Exception{
		
		SelectorItemCollection _selector=new SelectorItemCollection();
		_selector.add("*");
		fdcCostSplit.setSelectorsEntry(_selector, false);
		SelectorItemCollection selector=(SelectorItemCollection)_selector.clone();
		selector.add("paymentBill.id");
		PaymentSplitInfo paySplitInfo = PaymentSplitFactory.getLocalInstance(ctx).getPaymentSplitInfo(new ObjectUuidPK(splitId), selector);
		ClearSplitFacadeFactory.getLocalInstance(ctx).clearNoTextSplit(conId);
		
		paySplitInfo.setId(BOSUuid.create(paySplitInfo.getBOSType()));
		for(Iterator iter=paySplitInfo.getEntrys().iterator();iter.hasNext();){
			PaymentSplitEntryInfo entry=(PaymentSplitEntryInfo)iter.next();
			entry.setId(null);
		}
		paySplitInfo.setState(FDCBillStateEnum.SAVED);
		paySplitInfo.setIsInvalid(false);
		paySplitInfo.setFivouchered(false);
		paySplitInfo.setVoucherRefer(PaySplitVoucherRefersEnum.NOTREFER);
		paySplitInfo.setVoucherReferId(null);
		fdcCostSplit.refreshConWithoutTextPaymentSplit(paySplitInfo, null);
		PaymentSplitFactory.getLocalInstance(ctx).save(paySplitInfo);
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
		String sql = "select distinct a.fid ,con.fid as conID from T_FNC_PaymentSplit a 	\n" +
		" inner join T_FNC_PaymentSplitEntry b on a.FID = b.FParentID 	\n" +
		" inner join T_FDC_CostAccount c on b.FCostAccountID = c.FID \n" +
		" inner join T_CAS_PaymentBill pay on pay.fid=a.fpaymentBillID \n"+
		" inner join T_CON_Contractwithouttext con on con.fid=pay.fcontractbillId \n"+
		" where  a.fstate<> ? and (";
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
		builder.appendSql(") ");
		return builder.executeQuery();
	}

}