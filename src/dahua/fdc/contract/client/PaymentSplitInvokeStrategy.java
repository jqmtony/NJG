package com.kingdee.eas.fdc.contract.client;

import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.client.PaymentNoCostSplitEditUI;
import com.kingdee.eas.fdc.finance.client.PaymentSplitClientVerify;
import com.kingdee.eas.fdc.finance.client.PaymentSplitEditUI;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.CoreUI;

/**
 * 付款拆分调用策略
 * @author liupd
 *
 */
public class PaymentSplitInvokeStrategy extends AbstractSplitInvokeStrategy {


	
	public PaymentSplitInvokeStrategy(String billId, CoreUIObject parentUI) {
		super(billId, parentUI);
	}

	protected String getBillPropName() {
		return "paymentBill.id";
	}

	protected ICoreBase getBizInterface() throws BOSException {
		try {
			if (isContractCostSplit()) 
				return PaymentSplitFactory.getRemoteInstance();
			else
				return PaymentNoCostSplitFactory.getRemoteInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
	}

	protected String getSplitEditUIName() throws Exception {
		if (isContractCostSplit()) {
			if (PayReqUtils.isConWithoutTxt(getContractBillId())) {
				return "com.kingdee.eas.fdc.finance.client.PaymentSplitWithoutTxtConEditUI";
			} else {
				return PaymentSplitEditUI.class.getName();
			}
		} else {
			if (PayReqUtils.isConWithoutTxt(getContractBillId())) {
				return "com.kingdee.eas.fdc.finance.client.PaymentNoCostSplitWithoutTxtConEditUI";
			}
			return PaymentNoCostSplitEditUI.class.getName();
		}
	}

	private String contractBillId=null;
	public void invokeSplit() throws Exception {
		init();
		super.invokeSplit();
	}
	
	private PaymentBillInfo info=null;
	private Map paramMap=null;
	private void init() throws EASBizException, BOSException {
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
		selectors.add("contractBillId");
		selectors.add("company.id");
		selectors.add("fdcPayType.id");
		selectors.add("fdcPayType.payType.id");
		info= PaymentBillFactory.getRemoteInstance().getPaymentBillInfo(new ObjectUuidPK(getBillId()),selectors);
		contractBillId=info.getContractBillId();
		paramMap=FDCUtils.getDefaultFDCParam(null, info.getCompany().getId().toString());
	}

	protected String getContractBillId() throws Exception {
		return contractBillId;
	}

	protected void checkBeforeInvoke() throws Exception {
		super.checkBeforeInvoke();
		String contractId=getContractBillId();
		//无文本不做处理
		if(PayReqUtils.isConWithoutTxt(contractId)){
			return;
		}
		//结算款处理前检查是否可以拆分
		if(info.getFdcPayType()!=null&&info.getFdcPayType().getPayType()!=null&&info.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)){
			boolean isFinance=FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_FINACIAL);
			boolean isAdjustModel=FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_ADJUSTVOURCHER);
			PaymentSplitClientVerify.checkBeforeSplitSettle((CoreUI)this.getParentUI(), info.getId().toString(),isFinance,isAdjustModel);
		}

		if(isContractCostSplit()) {
			checkIsContractAllCostSplit();
			
			FDCSplitClientHelper.checkAndShowMsgBeforeSplit(this.getContractBillId(), null, true);
		}
		else {
			checkIsContractAllNoCostSplit();
			FDCSplitClientHelper.checkAndShowMsgBeforeSplit(this.getContractBillId(), null, false);
		}
		
	}
}
