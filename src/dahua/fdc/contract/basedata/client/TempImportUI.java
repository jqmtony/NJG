/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.basedata.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TempImportUI extends AbstractTempImportUI
{
    private static final Logger logger = CoreUIObject.getLogger(TempImportUI.class);
    
    /**
     * output class constructor
     */
    public TempImportUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	menuBar.setVisible(false);
		toolBar.setVisible(false);
		actionConfirm.setEnabled(true);
		actionExit.setEnabled(true);
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat("YYYY-MM-DD");
    	tblMain.getColumn("lastUpdateTime").getStyleAttributes().setNumberFormat("YYYY-MM-DD");
    	tblMain.getColumn("isEnabled").getStyleAttributes().setHided(true);
    	tblMain.getColumn("description").getStyleAttributes().setHided(true);
    	
    }
    
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
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
		KDTable kdtEntrys = (KDTable)getUIContext().get("kdtEntrys"); // 被导入的合约规划
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent",getSelectedKeyValue()));
		EntityViewInfo evi = new EntityViewInfo();
		evi.setSelector(getTemplateEntrySelector());
		evi.setFilter(filter);
		ProgrammingTemplateEntireCollection templateEntryColl = 
			ProgrammingTemplateEntireFactory.getRemoteInstance().getProgrammingTemplateEntireCollection(evi);
		ProgrammingTemplateEntireInfo templateEntry = null;
		IRow row = null;
		IRow rowTemp = null;
		if(kdtEntrys.getRowCount3()>0){
			Map<String,IRow> rowMaps = new HashMap<String,IRow>();
			for (int i = kdtEntrys.getRowCount3()-1; i >=0 ; i--) {
				row = kdtEntrys.getRow(i);
				rowMaps.put((String)row.getCell("longNumber").getValue(),row);
			}
			kdtEntrys.removeRows();
			for (int i = 0, size = templateEntryColl.size(); i < size; i++) {
				templateEntry = templateEntryColl.get(i);
				row = kdtEntrys.addRow();
				row.getCell("longNumber").setValue(templateEntry.getLongNumber());
				row.getCell("pcname").setValue(templateEntry.getName());
				if(rowMaps.containsKey(templateEntry.getLongNumber())){
					rowTemp = rowMaps.get(templateEntry.getLongNumber());
					//costDays  startDays
					row.getCell("costDays").setValue(rowTemp.getCell("costDays").getValue());
					row.getCell("startDays").setValue(rowTemp.getCell("startDays").getValue());
				}
			}
		}else{
			for (int i = 0, size = templateEntryColl.size(); i < size; i++) {
				templateEntry = templateEntryColl.get(i);
				row = kdtEntrys.addRow();
				row.getCell("longNumber").setValue(templateEntry.getLongNumber());
				row.getCell("pcname").setValue(templateEntry.getName());
			}
		}
		MsgBox.showInfo(this, "导入成功");
		disposeUIWindow();
	}
    
    private SelectorItemCollection getTemplateEntrySelector() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("name");
		sic.add("number");
		sic.add("id");
		sic.add("level");
		sic.add("longNumber");
		sic.add("displayName");
		sic.add("sortNumber");
		return sic;
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
		return ProgrammingTemplateFactory.getRemoteInstance();
	}

	@Override
	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}

}