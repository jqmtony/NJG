/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
abstract public class FDCTreeBaseDataEditUI extends
		AbstractFDCTreeBaseDataEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FDCTreeBaseDataEditUI.class);
	protected FDCTreeBaseDataInfo baseDataInfo;
	static boolean canCancel = true;
	
	/**
	 * output class constructor
	 */
	public FDCTreeBaseDataEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		chkMenuItemSubmitAndAddNew.setSelected(true);
		chkMenuItemSubmitAndAddNew.setVisible(true);
		String oprtState2 = getOprtState();
		baseDataInfo = getEditData();
		if (baseDataInfo != null) {
			if (STATUS_VIEW.equals(oprtState2)) {
				boolean isEnabled = this.baseDataInfo.isIsEnabled();
				btnCancel.setVisible(isEnabled);
				actionCancel.setEnabled(isEnabled);
				btnCancelCancel.setVisible(!isEnabled);
				actionCancelCancel.setEnabled(!isEnabled);
				if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
					this.btnAddNew.setEnabled(true);
					this.btnEdit.setEnabled(true);
				} else {
					this.btnAddNew.setEnabled(false);
					this.btnEdit.setEnabled(false);
					this.btnRemove.setEnabled(false);
				}
			} else if (STATUS_ADDNEW.equals(oprtState2)) {
				btnCancel.setVisible(false);
				actionCancel.setEnabled(false);
				btnCancelCancel.setVisible(false);
				actionCancelCancel.setEnabled(false);
			} else {
				boolean isEnabled = this.baseDataInfo.isIsEnabled();
				btnCancel.setVisible(isEnabled);
				actionCancel.setEnabled(isEnabled);
				btnCancelCancel.setVisible(!isEnabled);
				actionCancelCancel.setEnabled(!isEnabled);
			}
		}
	}
	
	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.getNumberCtrl().requestFocus();
		if ((getOprtState().equals(OprtState.ADDNEW) || getOprtState().equals(OprtState.EDIT))
				&& getUIContext().get(UIContext.OWNER) instanceof ListUI)
			((ListUI) getUIContext().get(UIContext.OWNER)).getMainTable().removeRows();
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		setIsEnable(false);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		setIsEnable(true);
	}

	protected void setIsEnable(boolean flag) throws Exception {
		baseDataInfo = getEditData();
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
		if (!flag && !canCancel)
			if (isSystemDefaultData()) {
				MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
				return;
			}
		baseDataInfo.setIsEnabled(flag);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("isEnabled"));
		String message = null;
		if (flag) {
			getBizInterface().updatePartial(baseDataInfo, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK");
		} else {
			getBizInterface().updatePartial(baseDataInfo, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK");
		}
		setMessageText(message);
		showMessage();
		setDataObject(getValue(new ObjectUuidPK(baseDataInfo.getId())));
		loadFields();
		setSave(true);
		setSaved(true);

		this.btnCancelCancel.setVisible(!flag);
		this.btnCancelCancel.setEnabled(!flag);
		this.btnCancel.setVisible(flag);
		this.btnCancel.setEnabled(flag);
		// this.chkIsEnabled.setSelected(flag);

	}
	
	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);
		this.getNumberCtrl().requestFocus();
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (isSystemDefaultData()) {
			MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
			return;
		}
		super.actionEdit_actionPerformed(e);
		this.getNumberCtrl().requestFocus();
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (isSystemDefaultData()) {
			MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
			return;
		}
		super.actionRemove_actionPerformed(e);
	}

	private FDCBaseDataClientCtrler ctrler = null;

	protected FDCBaseDataClientCtrler getCtrler() {
		if (ctrler == null) {
			try {
				ctrler = new FDCBaseDataClientCtrler(this, getBizInterface());
				ctrler.setControlType(getControlType());
			} catch (Exception e) {
				this.handUIExceptionAndAbort(e);
			}
		}
		return ctrler;
	}

	protected String getControlType() {
		return getCtrler().getControlType();
	}
	
	/**
	 * 判断是否系统预设数据
	 * 
	 * @return
	 */
	protected boolean isSystemDefaultData() {
		baseDataInfo = getEditData();
		if(baseDataInfo==null||baseDataInfo.getCU()==null||baseDataInfo.getId()==null){
			return false;
		}
		if (OrgConstants.SYS_CU_ID.equals(this.baseDataInfo.getCU().getId().toString())) {
			return true;
		}
		return false;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		// 编码是否为空
		KDTextField txtNumber = this.getNumberCtrl();
		if (txtNumber.getText() == null || txtNumber.getText().trim().length() < 1) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		}
		// 名称是否为空
		KDBizMultiLangBox txtName = getNameCtrl();
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(txtName, getEditData(), "name");
		if (flag) {
			txtName.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}
//		if (getOprtState().equals(OprtState.ADDNEW) || getOprtState().equals(OprtState.EDIT))
//			FDCBaseTypeValidator.validate(((ListUI) getUIContext().get(UIContext.OWNER)).getMainTable(), txtNumber, txtName, getSelectBOID());
	}
	
	protected abstract FDCTreeBaseDataInfo getEditData();

	protected abstract KDTextField getNumberCtrl();

	protected abstract KDBizMultiLangBox getNameCtrl();
}