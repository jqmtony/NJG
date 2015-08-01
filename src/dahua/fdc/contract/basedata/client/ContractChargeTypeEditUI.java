/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.ContractChargeTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ContractChargeTypeEditUI extends AbstractContractChargeTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractChargeTypeEditUI.class);
    
    /**
     * output class constructor
     */
    public ContractChargeTypeEditUI() throws Exception
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

    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("*"));
        return sic;
    }  
	protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return bizName;
	}

	protected IObjectValue createNewData() {
		ContractChargeTypeInfo conChargeInfo = new ContractChargeTypeInfo();
		conChargeInfo.setIsEnabled(isEnabled);
		return conChargeInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractChargeTypeFactory.getRemoteInstance();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getId() == null || StringUtil.isEmptyString(editData.getId().toString())) {
			return;
		}
		String id = editData.getId().toString();
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

	public void loadFields() {
		super.loadFields();
		if(STATUS_ADDNEW.equals(this.getOprtState())){
			this.setUITitle("合同费用项目--新增");
		}
		else if(STATUS_VIEW.equals(this.getOprtState())){
			this.setUITitle("合同费用项目--查看");
		}
		else if(STATUS_EDIT.equals(this.getOprtState())){
			this.setUITitle("合同费用项目--编辑");
		}
	}

}