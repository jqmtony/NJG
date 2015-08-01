/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.ProjectStatusFactory;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述:项目状态
 * 
 * @author jackwang date:2007-1-30
 *         <p>
 * @version EAS5.2
 */
public class ProjectStatusListUI extends AbstractProjectStatusListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ProjectStatusListUI.class);

	/**
	 * output class constructor
	 */
	public ProjectStatusListUI() throws Exception {
		super();
	}

	/**
	 * 
	 */
//	public void onLoad() throws Exception {
//		super.onLoad();
//		this.btnEnabled.setIcon(EASResource.getIcon("imgTbtn_staruse"));
//		this.btnDisEnabled.setIcon(EASResource.getIcon("imgTbtn_forbid"));
//		tblMain.getSelectManager().setSelectMode(
//				KDTSelectManager.MULTIPLE_ROW_SELECT);
//		if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext()
//				.getCurrentCtrlUnit().getId().toString())) {
//			this.btnAddNew.setEnabled(true);
//			this.btnEdit.setEnabled(true);
//			this.btnRemove.setEnabled(true);
//			// this.btnCancel.setVisible(true);
//			// this.btnCancelCancel.setVisible(true);
//			this.btnEnabled.setVisible(true);
//			this.btnDisEnabled.setVisible(true);
//			this.menuItemAddNew.setEnabled(true);
//			this.menuItemEdit.setEnabled(true);
//			this.menuItemRemove.setEnabled(true);
//			// this.menuItemCancel.setv(true)
//		} else {
//			this.btnAddNew.setEnabled(false);
//			this.btnEdit.setEnabled(false);
//			this.btnRemove.setEnabled(false);
//			this.btnEnabled.setVisible(false);
//			this.btnDisEnabled.setVisible(false);
//			// this.btnCancel.setVisible(false);
//			// this.btnCancelCancel.setVisible(false);
//			this.menuItemAddNew.setEnabled(false);
//			this.menuItemEdit.setEnabled(false);
//			this.menuItemRemove.setEnabled(false);
//		}
//		this.tblMain.getSelectManager().setSelectMode(
//				KDTSelectManager.ROW_SELECT);
//	}

//	protected String getPrintPageTitle() {
//		return getUITitle();
//	}

	
//	public boolean isSystemDefaultData(int activeRowIndex) {
//		String number = (String) tblMain.getCell(activeRowIndex,"number").getValue();
//		if (number.equals("FDC001") || number.equals("FDC002") || number.equals("FDC003") || number.equals("FDC004") || number.equals("FDC005")
//				|| number.equals("FDC006")) {
//			return true;
//		}
//		return false;
//	}

	protected String getEditUIName() {
		return ProjectStatusEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProjectStatusFactory.getRemoteInstance();
	}

	/**
	 * output actionEnabled_actionPerformed method
	 */
//	public void actionEnabled_actionPerformed(ActionEvent e) throws Exception {
//		IRow row = checkSelected(e);
//		if (row == null)
//			return;
//		String id = row.getCell("id").getValue().toString().trim();
//		IProjectStatus iProjectStatus = ProjectStatusFactory
//				.getRemoteInstance();
//		if (iProjectStatus.enabled(new ObjectUuidPK(id))) {
//			setMessageText(EASResource.getString(
//					FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
//			showMessage();
//		}
//		actionRefresh_actionPerformed(e);
//	}

	/**
	 * output actionDisEnabled_actionPerformed method
	 */
//	public void actionDisEnabled_actionPerformed(ActionEvent e)
//			throws Exception {
//		IRow row = checkSelected(e);
//		if (row == null)
//			return;
//		String id = row.getCell("id").getValue().toString().trim();
//		IProjectStatus iProjectStatus = ProjectStatusFactory
//				.getRemoteInstance();
//		if (iProjectStatus.disEnabled(new ObjectUuidPK(id))) {
//			setMessageText(EASResource.getString(
//					FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,
//					"DisEnabled_OK"));
//			showMessage();
//		}
//		actionRefresh_actionPerformed(e);
//	}

//	private IRow checkSelected(ActionEvent e) {
//		IRow row = null;
//		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
//			// 请先指定一条记录
//			MsgBox.showWarning("请先指定一条记录!");
//		} else {
//			row = this.tblMain.getRow(this.tblMain.getSelectManager()
//					.getActiveRowIndex());
//		}
//		return row;
//	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
//	protected void tblMain_tableSelectChanged(
//			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
//			throws Exception {
//		if (this.tblMain.getSelectManager().getActiveRowIndex() != -1) {
//			// boolean status = false;
//			if (this.tblMain.getRow(
//					this.tblMain.getSelectManager().getActiveRowIndex())
//					.getCell("isEnabled") != null) {
//				boolean status = ((Boolean) this.tblMain.getRow(
//						this.tblMain.getSelectManager().getActiveRowIndex())
//						.getCell("isEnabled").getValue()).booleanValue();
//				// 随着每一行规则的isEnabled的值改变，两个WBT的状态也改变
//				changeWBTEnabeld(status);
//			}
//			// if
//			// (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("cu.id")
//			// != null) {
//			// boolean isEnabled =
//			// this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("cu.id").getValue().toString().equals(
//			// SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
//			// changeEditEnabeld(isEnabled);
//			// } else {
//			// disabledEdit();
//			// }
//		} else {
//			disabledWBT();
//
//		}
//	}

//	/**
//	 * 随着每一行规则的isEnabled的值改变，两个btn的状态也改变
//	 * 
//	 * @param isEnabled
//	 *            boolean
//	 */
//	private void changeWBTEnabeld(boolean isEnabled) {
//		this.btnEnabled.setEnabled(!isEnabled);
//		this.btnDisEnabled.setEnabled(isEnabled);
//
//	}
//
//	/**
//	 * 把启用/禁止按钮disabled
//	 */
//	private void disabledWBT() {
//		this.btnEnabled.setEnabled(false);
//		this.btnDisEnabled.setEnabled(false);
//
//	}

	/**
	 * 检查是否编辑的是系统预设信息
	 */
//	private boolean verifySysDataEdit() {
//		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
//			return false;
//		} else {
//			int i = this.tblMain.getSelectManager().getActiveRowIndex();
//			if (OrgConstants.SYS_CU_ID.equals(this.tblMain.getRow(i).getCell(
//					"CU.id").getValue().toString())) {
//				MsgBox
//						.showWarning(com.kingdee.eas.util.client.EASResource
//								.getString(
//										"com.kingdee.eas.fdc.basedata.FDCBaseDataResource",
//										"Update_SysData"));
//				return false;
//			} else {
//				return true;
//			}
//		}
//
//	}

	/**
	 * 
	 * 检查是否删除的是系统预设信息
	 */

//	private void verifyNotAccepted(IRow row) {
//		if (row.getCell("CU.id").getValue() != null
//				&& (OrgConstants.SYS_CU_ID.equals(row.getCell("CU.id")
//						.getValue().toString()))) {
//			MsgBox.showWarning(com.kingdee.eas.util.client.EASResource
//					.getString(
//							"com.kingdee.eas.fdc.basedata.FDCBaseDataResource",
//							"Delete_SysData"));
//			SysUtil.abort();
//		}
//	}

//	protected String getEditUIModal() {
//		// return UIFactoryName.MODEL;
//		// return UIFactoryName.NEWWIN;
//		// 2006-4-29 胡博要求加入根据配置项来读取UI打开方式。
//		String openModel = UIConfigUtility.getOpenModel();
//		if (openModel != null) {
//			return openModel;
//		} else {
//			return UIFactoryName.MODEL;
//		}
//	}

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new ProjectStatusInfo();
	}
}