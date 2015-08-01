/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.MeasureIndexFactory;
import com.kingdee.eas.fdc.basedata.MeasureIndexInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class MeasureIndexListUI extends AbstractMeasureIndexListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MeasureIndexListUI.class);
    
    /**
     * output class constructor
     */
    public MeasureIndexListUI() throws Exception
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
		// TODO Auto-generated method stub
		super.onLoad();
		
		//非集团不能操作
		if (!OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.btnCancel.setEnabled(false);
			this.btnCancelCancel.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
			this.btnCancel.setEnabled(false);
			this.btnCancelCancel.setEnabled(false);
		}
		
	}

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new MeasureIndexInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MeasureIndexFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return MeasureIndexEditUI.class.getName();
	}
	public void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e){
		if (!OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
			this.btnCancel.setEnabled(false);
			this.btnCancelCancel.setEnabled(false);
		}
	}
	
	protected void setIsEnabled(boolean flag) throws Exception {
		super.setIsEnabled(flag);
		//记录日志用
		if(flag){
			MeasureIndexFactory.getRemoteInstance().enabled(null);
		}else{
			MeasureIndexFactory.getRemoteInstance().disEnabled(null);
		}
	}
}