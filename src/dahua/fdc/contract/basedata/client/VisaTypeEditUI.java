/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.VisaTypeFactory;
import com.kingdee.eas.fdc.basedata.VisaTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class VisaTypeEditUI extends AbstractVisaTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(VisaTypeEditUI.class);
    
    /**
     * output class constructor
     */
    public VisaTypeEditUI() throws Exception
    {
        super();
    }

	/**
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI#onLoad()
	 */
	public void onLoad() throws Exception {

		super.onLoad();
		//…Ë÷√title
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,
				FDCBaseDataClientUtils.VISATYPE));

	}

	/**
	 * output actionEdit_actionPerformed
	 */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
        if(OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())){				
			this.btnRemove.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
		}
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

	protected IObjectValue createNewData() {
		VisaTypeInfo objectValue = new VisaTypeInfo();
		objectValue.setIsEnabled(isEnabled);
		return new VisaTypeInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return VisaTypeFactory.getRemoteInstance();
	}

	 public SelectorItemCollection getSelectors(){
		 SelectorItemCollection ret = super.getSelectors();
		 ret.add(new SelectorItemInfo("CU"));
		 return ret;
	 }
	
}