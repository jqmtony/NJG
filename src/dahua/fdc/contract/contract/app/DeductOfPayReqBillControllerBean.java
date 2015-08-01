package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;

public class DeductOfPayReqBillControllerBean extends AbstractDeductOfPayReqBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.DeductOfPayReqBillControllerBean");

	protected void _reCalcAmount(Context ctx, String payReqID) throws BOSException, EASBizException
	{
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter;
		FilterItemCollection items;
		SelectorItemCollection selector;
		
		selector=new SelectorItemCollection();
		selector.add("createTime");
		selector.add("contractId");
		selector.add("exchangeRate");
		PayRequestBillInfo payBill=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(payReqID)),selector);

		Timestamp createTime = payBill.getCreateTime();
		String contractId=payBill.getContractId();
		filter=new FilterInfo();
		items=filter.getFilterItems();
		items.add(new FilterItemInfo("payRequestBill.id",payReqID));
		view.setFilter(filter);
//		SelectorItemCollection selector=new SelectorItemCollection();
//		selector.add("*");

		
		HashMap map=new HashMap();
		DeductOfPayReqBillCollection c = DeductOfPayReqBillFactory.getLocalInstance(ctx).getDeductOfPayReqBillCollection(view);
		DeductOfPayReqBillInfo info;
		for (Iterator iter = c.iterator(); iter.hasNext();)
		{
			info= (DeductOfPayReqBillInfo) iter.next();
			info.setAllPaidAmt(new BigDecimal("0"));
			//by tim_gao 增加原币别
			info.setAllPaidOriAmt(FDCHelper.ZERO);
			info.setAllReqOriAmt(FDCHelper.ZERO);
			info.setAllReqAmt(new BigDecimal("0"));
			info.setAmount(new BigDecimal("0"));
			info.setOriginalAmount(new BigDecimal("0"));
			map.put(info.getDeductType().getId(),info);
			
		}
/*
 * 处理所有分录中 相同合同 的扣款项以得到相应扣款类型的金额
 */		
		view=new EntityViewInfo();
		filter=new FilterInfo();
		items=filter.getFilterItems();
		items.add(new FilterItemInfo("parent.payRequestBill.contractId",contractId));
		view.setFilter(filter);
		selector=view.getSelector();
		selector.add("parent.deductType.id");
		selector.add("parent.payRequestBill.createTime");
		selector.add("parent.payRequestBill.id");
		selector.add("parent.hasPaid");
		selector.add("deductBillEntry.deductAmt");
		selector.add("deductBillEntry.deductOriginalAmt");	
		selector.add("deductBillEntry.exRate");
		DeductOfPayReqBillEntryCollection deductOfPayReqBillEntryCollection = DeductOfPayReqBillEntryFactory.getLocalInstance(ctx).getDeductOfPayReqBillEntryCollection(view);
		DeductOfPayReqBillEntryInfo entryInfo;
		for (Iterator iter = deductOfPayReqBillEntryCollection.iterator(); iter.hasNext();)
		{
			entryInfo = (DeductOfPayReqBillEntryInfo) iter.next();
			DeductOfPayReqBillInfo parent=entryInfo.getParent();
			info=(DeductOfPayReqBillInfo)map.get(parent.getDeductType().getId());
			if(info==null) continue;
			BigDecimal amount = FDCHelper.toBigDecimal(entryInfo.getDeductBillEntry().getDeductAmt());
			BigDecimal originalAmt = FDCHelper.toBigDecimal(entryInfo.getDeductBillEntry().getDeductOriginalAmt());
			if(originalAmt.compareTo(FDCHelper.ZERO)==0){
				if(entryInfo.getDeductBillEntry().getExRate()==null||entryInfo.getDeductBillEntry().getExRate().compareTo(FDCHelper.ZERO)==0)
					originalAmt = amount;
				else
					originalAmt = amount.divide(entryInfo.getDeductBillEntry().getExRate(),2);
			}
				
//			本申请单以前的则计算累计申请及累计实付，否则计算发生额，因为发生额的时间与本次同
//			if(parent.getPayRequestBill().getCreateTime().before(createTime)){
//				info.setAllReqAmt(info.getAllReqAmt().add(amount));
//				// by tim_gao 原币
//				info.setAllReqOriAmt(FDCHelper.add(info.getAllReqOriAmt(), originalAmt));
//				if(parent.isHasPaid()){
//					info.setAllPaidAmt(info.getAllPaidAmt().add(amount));
//					info.setAllPaidOriAmt(info.getAllPaidOriAmt().add(amount));
//				}
//			}else{
//				if(parent.getPayRequestBill().getId().toString().equals(payReqID)){
//					info.setAmount(info.getAmount().add(amount));
//					info.setOriginalAmount(info.getOriginalAmount().add(originalAmt));
//				}				
//			}
			// by tim_gao 原币
			if(parent.getPayRequestBill().getCreateTime().before(createTime)){
				info.setAllReqAmt(info.getAllReqAmt().add(amount));
				
				info.setAllReqOriAmt(FDCHelper.add(info.getAllReqOriAmt(), originalAmt));
				if(parent.isHasPaid()){
					info.setAllPaidAmt(info.getAllPaidAmt().add(amount));
					
					/* modified by zhaoqin for R140227-0281 on 2014/03/21 start */
					//info.setAllPaidOriAmt(info.getAllPaidOriAmt().add(amount));
					info.setAllPaidOriAmt(info.getAllPaidOriAmt().add(originalAmt));
					/* modified by zhaoqin for R140227-0281 on 2014/03/21 end */
				}
			}else{
				if(parent.getPayRequestBill().getId().toString().equals(payReqID)){
					info.setAmount(info.getAmount().add(amount));
					info.setOriginalAmount(info.getOriginalAmount().add(originalAmt));
				}	
				
			}
			
			
			
		}
		
		selector=new SelectorItemCollection();
		selector.add("amount");
		selector.add("originalAmount");
		
		selector.add("allPaidAmt");
		selector.add("allReqAmt");
		// by tim_gao 增加原币别的
		selector.add("allReqOriAmt");
		selector.add("allPaidOriAmt");
		Collection values = map.values();
		for (Iterator iter = values.iterator(); iter.hasNext();)
		{
			info = (DeductOfPayReqBillInfo) iter.next();
			DeductOfPayReqBillFactory.getLocalInstance(ctx).updatePartial(info, selector); 
			
		}
	}


}