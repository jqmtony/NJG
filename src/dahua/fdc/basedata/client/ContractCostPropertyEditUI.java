/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.fdc.basedata.ContractCostPropertyFactory;
import com.kingdee.eas.fdc.basedata.ContractCostPropertyInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.framework.ICoreBase;

public class ContractCostPropertyEditUI extends AbstractContractCostPropertyEditUI {
	public ContractCostPropertyEditUI() throws Exception {
		super();
	}

	public IObjectValue createNewData() {
		ContractCostPropertyInfo contractCostPropertyInfo = new ContractCostPropertyInfo();
		contractCostPropertyInfo.setIsEnabled(isEnabled);
		return contractCostPropertyInfo;
	}

	public ICoreBase getBizInterface() throws Exception {
		return ContractCostPropertyFactory.getRemoteInstance();
	}

	protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return txtName;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	/**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("CU.id"));
        return sic;
    } 
}