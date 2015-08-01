package com.kingdee.eas.fdc.contract.client;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 合同拆分调用策略类
 * @author liupd
 *
 */
public class ContractSplitInvokeStrategy extends AbstractSplitInvokeStrategy {

	public ContractSplitInvokeStrategy(String billId, CoreUIObject parentUI) {
		super(billId, parentUI);
	}

	protected String getBillPropName() {
		return "contractBill.id";
	}

	protected String getSplitEditUIName() throws Exception{
		if(isContractCostSplit()) {
			return com.kingdee.eas.fdc.contract.client.ContractCostSplitEditUI.class.getName();
		}
		else {
			return com.kingdee.eas.fdc.contract.client.ConNoCostSplitEditUI.class.getName();
		}
		
	}

	protected ICoreBase getBizInterface() throws Exception {
		if(isContractCostSplit()) {
			return ContractCostSplitFactory.getRemoteInstance();
		}
		else {
			return ConNoCostSplitFactory.getRemoteInstance();
		}
	}

	protected void checkBeforeInvoke() throws Exception {
		super.checkBeforeInvoke();
		checkIsAmtWithoutCost();
	}
	
	/**
	 * 获取关联的合同ID
	 * @return
	 * @throws Exception
	 */
	protected String getContractBillId() throws Exception {
		return getBillId();
	}
}
