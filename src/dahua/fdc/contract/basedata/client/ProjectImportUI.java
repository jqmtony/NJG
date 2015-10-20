/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProjectImportUI extends AbstractProjectImportUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectImportUI.class);
    
    /**
     * output class constructor
     */
    public ProjectImportUI() throws Exception {
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
    	tblMain.getColumn("name").setWidth(300);
    }
    /**
     * output actionConfirm_actionPerformed
     */
    public void actionConfirm_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
		executeImport();
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

    private void executeImport() throws Exception {
		if (MsgBox.OK != MsgBox.showConfirm2(this, "从模板导入将覆盖当前数据，继续么？")) {
			return;
		}
		KDTable kdtEntrys = (KDTable)getUIContext().get("kdtEntrys"); // 被导入的合约规划
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("PROGRAMMING",getSelectedKeyValue()));
		EntityViewInfo evi = new EntityViewInfo();
		evi.setSelector(getTemplateEntrySelector());
		evi.setFilter(filter);
		ProgrammingContractCollection pcColl = 
			ProgrammingContractFactory.getRemoteInstance().getProgrammingContractCollection(evi);
		ProgrammingContractInfo pcinfo = null;
		IRow row = null;
		kdtEntrys.removeRows();
		for (int i = 0, size = pcColl.size(); i < size; i++) {
			pcinfo = pcColl.get(i);
			row = kdtEntrys.addRow();
			row.getCell("longNumber").setValue(pcinfo.getLongNumber());
			row.getCell("pcname").setValue(pcinfo.getName());
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
    
    @Override
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
    	EntityViewInfo newviewInfo = (EntityViewInfo) viewInfo.clone();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("state","4AUDITTED"));
    	filter.getFilterItems().add(new FilterItemInfo("isLatest","1"));
    	filter.setMaskString("#0 and #1");
    	if(newviewInfo.getFilter()!=null){
    		try {
				newviewInfo.getFilter().mergeFilter(filter,"and");
			} catch (BOSException e) {
				handUIException(e);
			}
    	}else{
    		newviewInfo.setFilter(filter);
    	}
    	return super.getQueryExecutor(queryPK, newviewInfo);
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
		return ProgrammingFactory.getRemoteInstance();
	}

	@Override
	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}

}