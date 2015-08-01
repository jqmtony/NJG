/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.AcctAccreditSchemeFactory;
import com.kingdee.eas.fdc.basedata.AcctAccreditSchemeInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class AcctAccreditSchemeUI extends AbstractAcctAccreditSchemeUI
{
    private static final Logger logger = CoreUIObject.getLogger(AcctAccreditSchemeUI.class);
    
    /**
     * output class constructor
     */
    public AcctAccreditSchemeUI() throws Exception
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
    public void onLoad() throws Exception {
    	super.onLoad();
    	actionCancelCancel.setVisible(false);
    	actionCancel.setVisible(false);
    	txtName.setMaxLength(255);
    	txtNumber.setMaxLength(80);
    	txtDescription.setMaxLength(255);
    	txtName.setRequired(true);
    	txtNumber.setRequired(true);
    }
    protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return txtName;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected IObjectValue createNewData() {
		AcctAccreditSchemeInfo info=new AcctAccreditSchemeInfo();
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AcctAccreditSchemeFactory.getRemoteInstance();
	}
	
	public static void showUI(IUIObject ui,String id,String oprtState) throws UIException{
		UIContext uiContext = new UIContext(ui);
		uiContext.put(UIContext.ID, id);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(AcctAccreditSchemeUI.class.getName(), uiContext, null, oprtState);
		uiWindow.show();
	}
	
}