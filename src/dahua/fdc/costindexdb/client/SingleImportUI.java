/**
 * output package name
 */
package com.kingdee.eas.fdc.costindexdb.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.costindexdb.database.SinglePointTempEntryCollection;
import com.kingdee.eas.fdc.costindexdb.database.SinglePointTempEntryFactory;
import com.kingdee.eas.fdc.costindexdb.database.SinglePointTempEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SingleImportUI extends AbstractSingleImportUI
{
    private static final Logger logger = CoreUIObject.getLogger(SingleImportUI.class);
    
    /**
     * output class constructor
     */
    public SingleImportUI() throws Exception
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

    /**
     * output actionConfirm_actionPerformed
     */
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
    	SinglePointTempEntryCollection btcoll = null;
    	btcoll=SinglePointTempEntryFactory.getRemoteInstance().getSinglePointTempEntryCollection("select pointName,baseUnit.id,baseUnit.name,baseUnit.number,costAcount.id,costAcount.name,costAcount.number where parent.id='"+getSelectedKeyValue()+"'");
    	IRow row = null;
    	SinglePointTempEntryInfo beinfo = null;
    	table.removeRows();
    	for (int i = 0; i < btcoll.size(); i++) {
    		beinfo = btcoll.get(i);
    		row = table.addRow();
    		row.getCell("pointName").setValue(beinfo.getPointName());
    		row.getCell("baseUnit").setValue(beinfo.getBaseUnit());
    		row.getCell("costAccount").setValue(beinfo.getCostAcount());
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