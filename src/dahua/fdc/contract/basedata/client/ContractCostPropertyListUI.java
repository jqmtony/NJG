/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.base.log.LogUtil;
import com.kingdee.eas.fdc.basedata.ContractCostPropertyFactory;
import com.kingdee.eas.fdc.basedata.ContractCostPropertyInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class ContractCostPropertyListUI extends AbstractContractCostPropertyListUI {
	public ContractCostPropertyListUI() throws Exception {
		super();
	}

	public ICoreBase getBizInterface() throws Exception {
		return ContractCostPropertyFactory.getRemoteInstance();
	}

	public String getEditUIName() {
		return ContractCostPropertyEditUI.class.getName();
	}

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new ContractCostPropertyInfo();
	}
	
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		ContractCostPropertyInfo info = (ContractCostPropertyInfo) getBaseDataInfo();
		IObjectPK pk = LogUtil.beginLog(null, "1",info.getBOSType(), null, "true", "ContractCostProperty_cancel" );
		super.actionCancel_actionPerformed(e);
		LogUtil.afterLog(null, pk );
	}
	
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		ContractCostPropertyInfo info = (ContractCostPropertyInfo) getBaseDataInfo();
		IObjectPK pk = LogUtil.beginLog(null, "1",info.getBOSType(), null, "false", "ContractCostProperty_cancel" );
		super.actionCancelCancel_actionPerformed(e);
		LogUtil.afterLog(null, pk );
	}
}