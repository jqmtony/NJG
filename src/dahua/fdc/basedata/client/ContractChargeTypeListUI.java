/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.log.LogUtil;
import com.kingdee.eas.fdc.basedata.ContractChargeTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractCostPropertyInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ContractChargeTypeListUI extends AbstractContractChargeTypeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractChargeTypeListUI.class);
    
    /**
     * output class constructor
     */
    public ContractChargeTypeListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }


	protected FDCDataBaseInfo getBaseDataInfo() {
		return new ContractChargeTypeInfo();
	}

	protected String getEditUIName() {
		return ContractChargeTypeEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractChargeTypeFactory.getRemoteInstance();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String id = this.getSelectedKeyValue();
		FilterInfo filterContract = new FilterInfo();
		filterContract.getFilterItems().add(new FilterItemInfo("conChargeType.id",id));
		FilterInfo filterWithoutText = new FilterInfo();
		filterWithoutText.getFilterItems().add(new FilterItemInfo("conChargeType.id",id));
		if(ContractBillFactory.getRemoteInstance().exists(filterContract)
				||ContractWithoutTextFactory.getRemoteInstance().exists(filterWithoutText)){
			MsgBox.showError(this,"该记录已被引用，不允许删除！");
			SysUtil.abort();
		}
		
		super.actionRemove_actionPerformed(e);
	}

	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		ContractChargeTypeInfo info = (ContractChargeTypeInfo) getBaseDataInfo();
		IObjectPK pk = LogUtil.beginLog(null, "1",info.getBOSType(), null, "false", "ContractChargeType_cancel" );
		super.actionCancel_actionPerformed(e);
		LogUtil.afterLog(null, pk );
		
	}
	
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		ContractChargeTypeInfo info = (ContractChargeTypeInfo) getBaseDataInfo();
		IObjectPK pk = LogUtil.beginLog(null, "1",info.getBOSType(), null, "true", "ContractChargeType_cancelcancel" );
		super.actionCancelCancel_actionPerformed(e);
		LogUtil.afterLog(null, pk );
	}
}