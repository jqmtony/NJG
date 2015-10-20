package com.kingdee.eas.fdc.contract.client;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 变更拆分调用策略
 * @author liupd
 *
 */
public class ConChangeSplitInvokeStrategy extends AbstractSplitInvokeStrategy {

	public ConChangeSplitInvokeStrategy(String billId, CoreUIObject parentUI) {
		super(billId, parentUI);
	}

	protected String getBillPropName() {
		return "contractChange.id";
	}

	protected ICoreBase getBizInterface() throws Exception {
		if(isContractCostSplit()){
			return ConChangeSplitFactory.getRemoteInstance();
		}else{
			return ConChangeNoCostSplitFactory.getRemoteInstance();
		}
	}

	protected String getSplitEditUIName() throws Exception{
		String name = ConChangeSplitEditUI.class.getName();
		if(!isContractCostSplit()){
			name=ConChangeNoCostEditUI.class.getName();
		}
		return name;
	}
	
	protected void checkBeforeInvoke() throws Exception {
//		checkIsCostSplit();
		checkIsAmtWithoutCost();
	
	}

	protected String getContractBillId() throws Exception {
		
		SelectorItemCollection selectors = new SelectorItemCollection();
		
		selectors.add(CONTRACT_BILL_ID);
		
		ContractChangeBillInfo contractChangeBillInfo = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillInfo(new ObjectUuidPK(getBillId()));
		
		return contractChangeBillInfo.getContractBill().getId().toString();
	}
}
