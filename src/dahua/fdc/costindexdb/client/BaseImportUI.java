/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.costindexdb.database.BasePointTempEntryCollection;
import com.kingdee.eas.fdc.costindexdb.database.BasePointTempEntryFactory;
import com.kingdee.eas.fdc.costindexdb.database.BasePointTempEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class BaseImportUI extends AbstractBaseImportUI
{
    private static final Logger logger = CoreUIObject.getLogger(BaseImportUI.class);
    
    /**
     * output class constructor
     */
    public BaseImportUI() throws Exception
    {
        super();
    }

    @Override
    public void onLoad() throws Exception {
    	super.onLoad();
    	actionConfirm.setEnabled(true);
		actionExit.setEnabled(true);
    }
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	if (e.getClickCount() == 2) {
			if (e.getType() == 0) {
				return;
			} else {
				executeImport();
			}
		}
    }

    
    public void actionConfirm_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
		executeImport();
    }
    
    private void executeImport() throws Exception {
    	if (MsgBox.OK != MsgBox.showConfirm2(this, "从模板导入将覆盖当前数据，继续么？")) {
			return;
		}
    	KDTable table = (KDTable)getUIContext().get("kdtable");
    	BasePointTempEntryCollection btcoll = null;
    	btcoll=BasePointTempEntryFactory.getRemoteInstance().getBasePointTempEntryCollection("select pointName,unitBase.id,unitBase.name,unitBase.number where parent.id='"+getSelectedKeyValue()+"'");
    	IRow row = null;
    	BasePointTempEntryInfo beinfo = null;
    	table.removeRows();
    	for (int i = 0; i < btcoll.size(); i++) {
    		beinfo = btcoll.get(i);
    		row = table.addRow();
    		row.getCell("pointName").setValue(beinfo.getPointName());
    		row.getCell("baseUnit").setValue(beinfo.getUnitBase());
    		row.getCell("isCombo").setValue(Boolean.FALSE);
    		row.getCell("isModel").setValue(Boolean.FALSE);
		}
    	MsgBox.showInfo(this, "导入成功");
		disposeUIWindow();
    }

    /**
     * output actionExit_actionPerformed
     */
    public void actionExit_actionPerformed(ActionEvent e) throws Exception
    {
    	this.disposeUIWindow();
    }

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}

}