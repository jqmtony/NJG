/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.ChangeReasonFactory;
import com.kingdee.eas.fdc.basedata.ChangeReasonInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;

/**
 * 描述:变更原因编辑界面
 * 
 * @author jackwang date:2006-7-7
 * @version EAS5.1
 */

public class ChangeReasonEditUI extends AbstractChangeReasonEditUI {
	private static final Logger logger = CoreUIObject.getLogger(ChangeReasonEditUI.class);

	/**
	 * output class constructor
	 */
	public ChangeReasonEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setTitle();
	}
	
	public void storeFields() {
		this.editData.setChangeType((ChangeTypeInfo)prmtChangeReason.getData());
		super.storeFields();		
	}
	
    public void loadFields() {
    	super.loadFields();
    	
    	if(this.getOprtState().equals(OprtState.ADDNEW)){
    		return;
    	}else{
    		try {
    			if(editData!=null&&editData.getChangeType()!=null){
    				ChangeTypeInfo info = (ChangeTypeInfo) ChangeTypeFactory.getRemoteInstance().getValue(new ObjectUuidPK(this.editData.getChangeType().getId()));
        			this.prmtChangeReason.setUserObject(info);
            		this.prmtChangeReason.setData(info);
    			}    			
    		} catch (Exception e) {
    			e.printStackTrace();
				handUIExceptionAndAbort(e);
    		}
    	}
    	
    	
    }
	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.CHANGEREASON));
	}

    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("name"));
      //  sic.add(new SelectorItemInfo("changeReasonType"));
        sic.add(new SelectorItemInfo("changeType"));
        sic.add(new SelectorItemInfo("*"));
        return sic;
    }     
	protected IObjectValue createNewData() {
		ChangeReasonInfo changeReasonInfo = new ChangeReasonInfo();
		changeReasonInfo.setIsEnabled(isEnabled);
		return changeReasonInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ChangeReasonFactory.getRemoteInstance();
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

	/**
	 * 重写了父类的方法，不需要调用FDCBaseTypeValidator.validate(...)方法
	 * 
	 * 变更类型改为必录字段
	 * 
	 * @author owen_wen 2011-04-05
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		// 编码是否为空
		KDTextField txtNumber = this.getNumberCtrl();
		if (txtNumber.getText() == null || txtNumber.getText().trim().length() < 1) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		}
		// 名称是否为空
		KDBizMultiLangBox txtName = getNameCtrl();
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(txtName, getEditData(), "name");
		if (flag) {
			txtName.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}
		
		if (prmtChangeReason.getValue() == null) {
			prmtChangeReason.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.CHANGETYPE_ISNULL);
		}
	}
}