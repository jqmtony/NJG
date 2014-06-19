/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MeasureStageFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class MeasureStageListUI extends AbstractMeasureStageListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MeasureStageListUI.class);
    
    /**
     * output class constructor
     */
    public MeasureStageListUI() throws Exception
    {
        super();
    }    

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new MeasureStageInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MeasureStageFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return MeasureStageEditUI.class.getName();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		tblMain.checkParsed();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.VIRTUAL_MODE_PAGE);
		
		if (!OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.btnCancel.setEnabled(false);
			this.btnCancelCancel.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEdit();
		super.actionEdit_actionPerformed(e);
	}
	private void checkBeforeEdit() throws Exception{
		List idList = ContractClientUtils.getSelectedIdValues(
				this.tblMain, getKeyFieldName());
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("measureStage.id",FDCHelper.list2Set(idList),CompareType.INCLUDE));
//		if(AimCostFactory.getRemoteInstance().exists(filter) || MeasureCostFactory.getRemoteInstance().exists(filter)){
//			FDCMsgBox.showWarning(this, "测算阶段被目标成本测算或目标成本引用，不能进行修改操作!");
//			this.abort();
//		}
	}
	
	private void checkBeforeDisEnabled() throws Exception{
		List idList = ContractClientUtils.getSelectedIdValues(
				this.tblMain, getKeyFieldName());
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("measureStage.id",FDCHelper.list2Set(idList),CompareType.INCLUDE));
//		if(AimCostFactory.getRemoteInstance().exists(filter) || MeasureCostFactory.getRemoteInstance().exists(filter)){
//			FDCMsgBox.showWarning(this, "测算阶段被目标成本测算或目标成本引用，不能禁用!");
//			this.abort();
//		}
	}
	protected void setIsEnabled(boolean flag) throws Exception {
		super.setIsEnabled(flag);
		//记录日志用
		if(flag){
			MeasureStageFactory.getRemoteInstance().enabled(null);
		}else{
			MeasureStageFactory.getRemoteInstance().disEnabled(null);
		}
	}
}