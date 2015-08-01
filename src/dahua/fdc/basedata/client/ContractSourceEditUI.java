/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.ContractSourceFactory;
import com.kingdee.eas.fdc.basedata.ContractSourceInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class ContractSourceEditUI extends AbstractContractSourceEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractSourceEditUI.class);
    
    /**
     * output class constructor
     */
    public ContractSourceEditUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
		super.onLoad();
		setTitle();
	}

	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.CONTRACTSOURCE));
	}
	/**
	 * output getSelectors method
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("*"));
		return sic;
	}		
	protected IObjectValue createNewData() {
		ContractSourceInfo contractSourceInfo = new ContractSourceInfo();
		FDCBaseDataClientUtils.setFieldsNull(contractSourceInfo);
		contractSourceInfo.setIsEnabled(isEnabled);
		return contractSourceInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractSourceFactory.getRemoteInstance();
	}

	protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return bizName;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

}