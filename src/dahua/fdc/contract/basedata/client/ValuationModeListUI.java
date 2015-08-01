/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.ValuationModeFactory;
import com.kingdee.eas.fdc.basedata.ValuationModeInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 计价模式
 * @author liangliang_ye
 */
public class ValuationModeListUI extends AbstractValuationModeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ValuationModeListUI.class);
    
    public ValuationModeListUI() throws Exception
    {
        super();
    }

    protected  boolean isGroupOrg(){
		return SysContext.getSysContext().getCurrentCtrlUnit().getId().toString().equals(OrgConstants.DEF_CU_ID);
    	
    }
    
    protected void initWorkButton(){
    	super.initWorkButton();
    	
    	boolean isGroupOrg = isGroupOrg();
    	
		actionAddNew.setEnabled(isGroupOrg);
		actionEdit.setEnabled(isGroupOrg);
		actionRemove.setEnabled(isGroupOrg);
    	btnCancel.setEnabled(isGroupOrg);
    	btnCancelCancel.setEnabled(isGroupOrg);
    }
    /**
     * 禁用操作
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
		checkSelected();
        super.actionCancel_actionPerformed(e);

    }

    /**
     * 启用操作
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);

    }

    
    public void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception{
    	super.tblMain_tableSelectChanged(e);
    	
    	String id = getSelectedKeyValue();
    	ValuationModeInfo info = ValuationModeFactory.getRemoteInstance().getValuationModeInfo(new ObjectUuidPK(id));
        actionCancel.setEnabled(info.isIsEnabled());
        actionCancelCancel.setEnabled(!info.isIsEnabled());
    }

	protected FDCDataBaseInfo getBaseDataInfo() {
		FDCDataBaseInfo object = new FDCDataBaseInfo();
		return object;
	}

	protected boolean isSystemDefaultData(int activeRowIndex) {
		return false;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		super.actionEdit_actionPerformed(e);
	}
    
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		super.actionRemove_actionPerformed(e);
	}

	protected String getEditUIName() {
	    return ValuationModeEditUI.class.getName();
	}


	protected ICoreBase getBizInterface() throws Exception {
		return ValuationModeFactory.getRemoteInstance();
	}


}