/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CounterclaimTypeFactory;
import com.kingdee.eas.fdc.basedata.ICounterclaimType;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/** 
 * 描述:索赔类型
 * @author jackwang  date:2007-3-5 <p>
 * @version EAS5.3
 */
public class CounterclaimTypeListUI extends AbstractCounterclaimTypeListUI {
	private static final Logger logger = CoreUIObject.getLogger(CounterclaimTypeListUI.class);

	/**
	 * output class constructor
	 */
	public CounterclaimTypeListUI() throws Exception {
		super();
	}

	/**
	 * 
	 */
	public void onLoad() throws Exception {
		super.onLoad();
//		this.btnEnabled.setIcon(EASResource.getIcon("imgTbtn_staruse"));
//		this.btnDisEnabled.setIcon(EASResource.getIcon("imgTbtn_forbid"));
		
		this.btnEnabled.setVisible(false);
		this.btnDisEnabled.setVisible(false);
		
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
			this.btnCancel.setVisible(true);
			this.btnCancelCancel.setVisible(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(true);
			
			menuItemCancel.setVisible(true);
			menuItemCancelCancel.setVisible(true);
			menuBiz.setVisible(true);
			menuBiz.setEnabled(true);
			//			this.menuItemCancel.setv(true)
		} else {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);

			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
			
			menuItemCancel.setVisible(false);
			menuItemCancelCancel.setVisible(false);
		}
		//由于有查询方案，故不手动设置保存方案。
//		//设置可以保存当前样式
//		tHelper = new TablePreferencesHelper(this);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		super.actionView_actionPerformed(e);
	}

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}

	/**
	 * output actionImportData_actionPerformed
	 */
	public void actionImportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionImportData_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionExportData_actionPerformed
	 */
	public void actionExportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportData_actionPerformed(e);
	}

	/**
	 * output actionToExcel_actionPerformed
	 */
	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		super.actionToExcel_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionPublishReport_actionPerformed
	 */
	public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception {
		super.actionPublishReport_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		actionDisEnabled_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		actionEnabled_actionPerformed(e);
	}

	protected String getEditUIName() {
		return CounterclaimTypeEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CounterclaimTypeFactory.getRemoteInstance();
	}

	/**
	 * output actionEnabled_actionPerformed method
	 */
	public void actionEnabled_actionPerformed(ActionEvent e) throws Exception {
		IRow row = checkSelected(e);
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ICounterclaimType iCounterclaimType = CounterclaimTypeFactory.getRemoteInstance();
		if (iCounterclaimType.enabled(new ObjectUuidPK(id))) {
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
			showMessage();
		}
		actionRefresh_actionPerformed(e);
	}

	/**
	 * output actionDisEnabled_actionPerformed method
	 */
	public void actionDisEnabled_actionPerformed(ActionEvent e) throws Exception {
		IRow row = checkSelected(e);
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ICounterclaimType iCounterclaimType = CounterclaimTypeFactory.getRemoteInstance();
		if (iCounterclaimType.disEnabled(new ObjectUuidPK(id))) {
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
			showMessage();
		}
		actionRefresh_actionPerformed(e);
	}

	private IRow checkSelected(ActionEvent e) {
		IRow row = null;
		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			// 请先指定一条记录
			MsgBox.showWarning("请先指定一条记录!");
		} else {
			row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		}
		return row;
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		if (this.tblMain.getSelectManager().getActiveRowIndex() != -1) {
			// boolean status = false;
			if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled") != null) {
				boolean status = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled").getValue()).booleanValue();
				// 随着每一行规则的isEnabled的值改变，两个WBT的状态也改变
				changeWBTEnabeld(status);
			}
		} else {
			disabledWBT();

		}
	}

	/**
	 * 随着每一行规则的isEnabled的值改变，两个btn的状态也改变
	 * 
	 * @param isEnabled
	 *            boolean
	 */
	private void changeWBTEnabeld(boolean isEnabled) {
		this.actionCancelCancel.setEnabled(!isEnabled);
		this.actionCancel.setEnabled(isEnabled);

	}

	/**
	 * 把启用/禁止按钮disabled
	 */
	private void disabledWBT() {
		this.actionCancelCancel.setEnabled(false);
		this.actionCancel.setEnabled(false);

	}

	/**
	 * 检查是否编辑的是系统预设信息
	 */
	private boolean verifySysDataEdit() {
		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			return false;
		} else {
			int i = this.tblMain.getSelectManager().getActiveRowIndex();
			if (OrgConstants.SYS_CU_ID.equals(this.tblMain.getRow(i).getCell("CU.id").getValue().toString())) {
				MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Update_SysData"));
				return false;
			} else {
				return true;
			}
		}

	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
//		if (verifySysDataEdit()) {
			super.actionEdit_actionPerformed(e);
//		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
//		KDTSelectBlock selectBlock = null;
//		ICell cell = null;
//		for (int i = 0; i < tblMain.getSelectManager().size(); i++) {
//
//			selectBlock = tblMain.getSelectManager().get(i);
//			for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
//				IRow row = this.tblMain.getRow(j);
//
//				this.verifyNotAccepted(row);
//				cell = row.getCell(getKeyFieldName());
//				if (cell == null) {
//					MsgBox.showError(EASResource.getString(FrameWorkClientUtils.strResource + "Error_KeyField_Fail"));
//					SysUtil.abort();
//				}
//				Object keyValue = cell.getValue();
//
//				BOSUuid id = BOSUuid.read((String) keyValue);
//				getBizInterface().delete(new ObjectUuidPK(id));
//			}
//		}
//		actionRefresh_actionPerformed(e);
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * 
	 * 检查是否删除的是系统预设信息
	 */

	private void verifyNotAccepted(IRow row) {
		if (row.getCell("CU.id").getValue() != null && (OrgConstants.SYS_CU_ID.equals(row.getCell("CU.id").getValue().toString()))) {
			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Delete_SysData"));
			SysUtil.abort();
		}
	}
    protected String getEditUIModal()
    {
        // return UIFactoryName.MODEL;
        // return UIFactoryName.NEWWIN;
        // 2006-4-29 胡博要求加入根据配置项来读取UI打开方式。
        String openModel = UIConfigUtility.getOpenModel();
        if (openModel != null)
        {
            return openModel;
        }
        else
        {
            return UIFactoryName.MODEL;
        }
    }


	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：是否支持EAS高级统计(EAS800新增的功能)
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-4-1
	 */
	// @Override
	protected boolean isSupportEASPivot() {
		// return super.isSupportEASPivot();
		return false;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

}