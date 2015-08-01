/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.log.LogUtil;
import com.kingdee.eas.fdc.basedata.ContractCostPropertyInfo;
import com.kingdee.eas.fdc.basedata.ContractPayPlanTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractPayPlanTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ContractPayPlanTypeListUI extends AbstractContractPayPlanTypeListUI {
	private static final Logger logger = CoreUIObject.getLogger(ContractPayPlanTypeListUI.class);

	/**
	 * output class constructor
	 */
	public ContractPayPlanTypeListUI() throws Exception {
		super();
	}

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new ContractPayPlanTypeInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractPayPlanTypeFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return ContractPayPlanTypeEditUI.class.getName();
	}
	
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		ContractPayPlanTypeInfo info = (ContractPayPlanTypeInfo) getBaseDataInfo();
		IObjectPK pk = LogUtil.beginLog(null, "1",info.getBOSType(), null, "false", "ContractPayPlanType_cancel" );
		super.actionCancel_actionPerformed(e);
		LogUtil.afterLog(null, pk );
	}
	
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		ContractPayPlanTypeInfo info = (ContractPayPlanTypeInfo) getBaseDataInfo();
		IObjectPK pk = LogUtil.beginLog(null, "1",info.getBOSType(), null, "true", "ContractPayPlanType_cancelcancel" );
		super.actionCancelCancel_actionPerformed(e);
		LogUtil.afterLog(null, pk );
	}

}