/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CounterclaimTypeFactory;
import com.kingdee.eas.fdc.basedata.CounterclaimTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.ICounterclaimType;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述:索赔类型
 * 
 * @author jackwang date:2007-3-5
 *         <p>
 * @version EAS5.3
 */
public class CounterclaimTypeEditUI extends AbstractCounterclaimTypeEditUI {
	private static final Logger logger = CoreUIObject.getLogger(CounterclaimTypeEditUI.class);

	/**
	 * output class constructor
	 */
	public CounterclaimTypeEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setTitle();
		setBtnStatus();
	}

	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.COUNTERCLAIMTYPE));
	}

	private void setBtnStatus() {
		if (STATUS_ADDNEW.equals(getOprtState())) {// 新增状态
			this.actionCancelCancel.setVisible(false);// 启用按钮不可见
			this.actionCancel.setVisible(false);// 禁用按钮不可见
		} else if (STATUS_EDIT.equals(getOprtState())) {// 修改状态
			if (this.editData.isIsEnabled()) {// 如果当前为启用状态
				this.actionCancel.setVisible(true);// 禁用按钮可用
				this.actionCancel.setEnabled(true);// 禁用按钮可用
				this.actionCancelCancel.setVisible(false);// 启用按钮不可见
			} else {// 如果当前为禁用状态
				this.actionCancelCancel.setVisible(true);// 启用按钮可见
				this.actionCancelCancel.setEnabled(true);// 启用按钮可用
				this.actionCancel.setVisible(false);// 禁用按钮不可见
			}
			if(OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())){	
				this.btnRemove.setEnabled(false);
			}
		} else if (STATUS_VIEW.equals(getOprtState())) {// 查看状态
			if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
				if (this.editData.isIsEnabled()) {// 如果当前为启用状态
					this.actionCancel.setVisible(true);// 禁用按钮可用
					this.actionCancel.setEnabled(true);// 禁用按钮可用
					this.actionCancelCancel.setVisible(false);// 启用按钮不可见
				} else {// 如果当前为禁用状态
					this.actionCancelCancel.setVisible(true);// 启用按钮可见
					this.actionCancelCancel.setEnabled(true);// 启用按钮可用
					this.actionCancel.setEnabled(false);// 禁用按钮不可见
				}
				this.btnAddNew.setEnabled(true);
				this.btnEdit.setEnabled(true);
				this.menuItemAddNew.setEnabled(true);
				this.menuItemEdit.setEnabled(true);
				// this.menuItemRemove.setEnabled(true);
			} else {
				this.btnAddNew.setEnabled(false);
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
				this.actionCancel.setVisible(false);
				this.actionCancelCancel.setVisible(false);
				this.menuItemAddNew.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}
//			if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
//				this.btnAddNew.setEnabled(false);
////				this.btnEdit.setEnabled(false);
//				this.btnRemove.setEnabled(false);
//				this.actionCancel.setVisible(false);
//				this.actionCancelCancel.setVisible(false);
//				this.menuItemAddNew.setEnabled(false);
//				this.menuItemEdit.setEnabled(false);
//				this.menuItemRemove.setEnabled(false);
//			}
		}
	}

	/**
	 * output getSelectors method
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("CU.id"));
		return sic;
	}

	/**
	 * 校验值对象的合法性
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		// 编码是否为空
		if (this.txtNumber.getText() == null || this.txtNumber.getText().trim().equals("")) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
			//MsgBox.showWarning(this,"");
		}
		// 名称是否为空
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(bizName, this.editData, "name");
		if (flag) {
			bizName.requestFocus(true);
			//throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}

		if (getOprtState().equals(OprtState.ADDNEW) || getOprtState().equals(OprtState.EDIT))
			FDCBaseTypeValidator.validate(((CounterclaimTypeListUI) getUIContext().get(UIContext.OWNER)).getMainTable(), txtNumber, bizName, getSelectBOID());
	
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}



	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		this.txtNumber.requestFocus();
		if (getOprtState().equals(OprtState.ADDNEW) || getOprtState().equals(OprtState.EDIT))
			((FDCBaseDataListUI) getUIContext().get(UIContext.OWNER)).getMainTable().removeRows();
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.txtNumber.requestFocus();
		if (getOprtState().equals(OprtState.ADDNEW) || getOprtState().equals(OprtState.EDIT))
			((CounterclaimTypeListUI) getUIContext().get(UIContext.OWNER)).getMainTable().removeRows();
	}
	
    public void onShow() throws Exception
    {
        super.onShow();
        this.txtNumber.requestFocus();
    }

	/**
	 * output actionFirst_actionPerformed
	 */
	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		super.actionFirst_actionPerformed(e);
	}

	/**
	 * output actionPre_actionPerformed
	 */
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		super.actionPre_actionPerformed(e);
	}

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		if(OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())){	
			this.btnRemove.setEnabled(false);
		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData!=null&&this.editData.getId()!=null){
			ICounterclaimType iCounterclaimType = CounterclaimTypeFactory.getRemoteInstance();
			if (iCounterclaimType.disEnabled(new ObjectUuidPK(editData.getId()))) {
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
				loadFields();
				setSave(true);
				setSaved(true);
				this.actionCancelCancel.setVisible(true);
				this.actionCancelCancel.setEnabled(true);
				this.actionCancel.setVisible(false);
				this.actionCancel.setEnabled(false);
				this.chkIsEnabled.setSelected(false);
			}
		}
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData!=null&&this.editData.getId()!=null){
			ICounterclaimType iCounterclaimType = CounterclaimTypeFactory.getRemoteInstance();
			if (iCounterclaimType.enabled(new ObjectUuidPK(editData.getId()))) {
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
				loadFields();
				setSave(true);
				setSaved(true);
				this.actionCancel.setVisible(true);
				this.actionCancel.setEnabled(true);
				this.actionCancelCancel.setVisible(false);
				this.actionCancelCancel.setEnabled(false);
				this.chkIsEnabled.setSelected(true);
			}
		}
	}

	protected void showResultMessage(String message) {
		// setMessageText(EASResource.getString(message));
		setMessageText(message);
		// setMessageIcon(SHOW_MESSAGE_ICON_ERROR);
		// setMessageBgcolor(SHOW_MESSAGE_BG_ERROR);
		showMessage();
	}

	protected IObjectValue createNewData() {
		CounterclaimTypeInfo counterclaimTypeInfo = new CounterclaimTypeInfo();
		counterclaimTypeInfo.setIsEnabled(true);
		return counterclaimTypeInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return CounterclaimTypeFactory.getRemoteInstance();
	}

}