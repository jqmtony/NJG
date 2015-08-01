package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 结算拆分调用策略
 * @author liupd
 *
 */
public class 
ConSettleSplitInvokeStrategy extends AbstractSplitInvokeStrategy {

	public ConSettleSplitInvokeStrategy(String billId, CoreUIObject parentUI) {
		super(billId, parentUI);
	}

	protected String getBillPropName() {
		return "settlementBill.id";
	}

	protected ICoreBase getBizInterface() throws Exception {
		if(isContractCostSplit()){
			return SettlementCostSplitFactory.getRemoteInstance();
		}else{
			return SettNoCostSplitFactory.getRemoteInstance();
		}
	}

	protected String getSplitEditUIName() throws Exception{
		if(isContractCostSplit()){
			return SettlementCostSplitEditUI.class.getName();
		}else{
			return SettlementNoCostEditUI.class.getName();
		}
	}

	protected String getContractBillId() throws Exception {
		
		SelectorItemCollection selectors = new SelectorItemCollection();
		
		selectors.add(CONTRACT_BILL_ID);
		
		ContractSettlementBillInfo contractSettlementBillInfo = ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillInfo(new ObjectUuidPK(getBillId()));
		
		return contractSettlementBillInfo.getContractBill().getId().toString();
	}

	protected BigDecimal getSettlePrice() throws Exception {

		SelectorItemCollection selectors = new SelectorItemCollection();

		selectors.add("settlePrice");

		ContractSettlementBillInfo contractSettlementBillInfo = ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillInfo(new ObjectUuidPK(getBillId()));

		return contractSettlementBillInfo.getSettlePrice();

	}
	/**
	 * 新需求R101014-252，要求拆分前要检查合同拆分和变更拆分是否已经审批。
	 * @Modified owen_wen 2010-12-03
	 */
	protected void checkBeforeInvoke() throws Exception {
		super.checkBeforeInvoke();
//		checkIsCostSplit();
		if(isContractCostSplit()){
			checkIsContractAllCostSplit();
			FDCSplitClientHelper.checkAndShowMsgBeforeSplit(this.getContractBillId(), null, true);
			// FDCSplitClientHelper.autoChangeSettle(getBillId());//JF提示修改

			boolean b = false;
			BigDecimal conPrice = FDCUtils.getConAndChange(null, this.getContractBillId());
			BigDecimal settlePrice = this.getSettlePrice();
			b = FDCHelper.subtract(settlePrice, conPrice).compareTo(FDCHelper.ZERO) == 0;
			if (b) {
				FDCSplitClientHelper.autoChangeSettle2(getBillId());
			} else {
				FDCSplitClientHelper.autoChangeSettle3(getBillId());
			}
		}else{
			FDCSplitClientHelper.checkAndShowMsgBeforeSplit(this.getContractBillId(), null, false);
			checkIsContractAllNoCostSplit();
		}
//		checkIsAmtWithoutCost();
	}

}
