package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractExecStateEnum;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;

public class ContractStateHelper {

	public static void synToExecState(Context ctx, String contractId, ContractExecStateEnum execState) throws EASBizException, BOSException {
		ContractBillInfo info = new ContractBillInfo();
		
		info.setId(BOSUuid.read(contractId));
		info.setExecState(execState);
		
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
		selectors.add("execState");
		
		ContractBillFactory.getLocalInstance(ctx).updatePartial(info, selectors);
	}
	
	public static void synToExecState(Context ctx, String contractId) throws EASBizException, BOSException {
		
		//取合同对应的付款�?
		EntityViewInfo paymentView = new EntityViewInfo();
		FilterInfo paymentFilter = new FilterInfo();
		paymentFilter.getFilterItems().add(new FilterItemInfo("contractBillId", contractId));
		paymentView.setFilter(paymentFilter);
		paymentView.getSelector().add("actPayAmt");
		
		PaymentBillCollection paymentBillCollection = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection(paymentView);
		
//		BigDecimal actualPayAmtTotal = FDCConstants.B0;
		BigDecimal actualPayAmtTotal = FDCHelper.ZERO;
		for (Iterator iter = paymentBillCollection.iterator(); iter.hasNext();) {
			PaymentBillInfo element = (PaymentBillInfo) iter.next();
			actualPayAmtTotal = actualPayAmtTotal.add(element.getActPayAmt());
		}
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("hasSettled");
		selector.add("settleAmt");
		
		//取合同信�?
		ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectStringPK(contractId));
		boolean settled = contractBillInfo.isHasSettled();
		BigDecimal settleAmt = contractBillInfo.getSettleAmt();
		
		/*
		 * 计算状�??
		 * �?
		 * 	合同的状态：
�?	已签�?		—�?? 已经签署合同，但还没有支付任何款�?
�?	未结�?		—�?? 已经支付款项，但还没有结�?
�?	已结�?		—�?? 已经结算，但还没有付�?
�?	已付�?		—�?? 本合同的款项，已经全部付�?

		 */
		ContractExecStateEnum toState = null;
		
		if(!settled && actualPayAmtTotal.signum() > 0) {
			toState = ContractExecStateEnum.NOTSETTLE;
		}else if(settled && actualPayAmtTotal.compareTo(settleAmt) < 0) {
			toState = ContractExecStateEnum.SETTLED;
		}
		else if(settled && actualPayAmtTotal.compareTo(settleAmt) >= 0) {
			toState = ContractExecStateEnum.PAYED;
		}
		
		//设置状�??
		if(toState != null)
		ContractStateHelper.synToExecState(ctx, contractId, toState);
	}
}
