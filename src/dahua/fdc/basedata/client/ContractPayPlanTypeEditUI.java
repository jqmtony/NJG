/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.ContractPayPlanTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractPayPlanTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ContractPayPlanTypeEditUI extends AbstractContractPayPlanTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractPayPlanTypeEditUI.class);
    
    /**
     * output class constructor
     */
    public ContractPayPlanTypeEditUI() throws Exception
    {
        super();
    }

	protected FDCDataBaseInfo getEditData() {
		return (FDCDataBaseInfo) editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return txtName;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected IObjectValue createNewData() {
		return new ContractPayPlanTypeInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractPayPlanTypeFactory.getRemoteInstance();
	}
}