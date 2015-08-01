/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.IPaymentType;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述:付款方式叙事簿界面
 * 
 * @author jackwang date:2006-7-7
 * @version EAS5.1
 */
public class PaymentTypeListUI extends AbstractPaymentTypeListUI {
	private static final Logger logger = CoreUIObject.getLogger(PaymentTypeListUI.class);

	/**
	 * output class constructor
	 */
	public PaymentTypeListUI() throws Exception {
		super();
	}

	/**
	 * 
	 */
//	public void onLoad() throws Exception {
//		super.onLoad();
//		this.btnEnabled.setIcon(EASResource.getIcon("imgTbtn_staruse"));
//		this.btnDisEnabled.setIcon(EASResource.getIcon("imgTbtn_forbid"));
//		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
//		if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())){
//			this.btnAddNew.setEnabled(true);
//			this.btnEdit.setEnabled(true);
//			this.btnRemove.setEnabled(true);
//			this.btnEnabled.setVisible(true);
//			this.btnDisEnabled.setVisible(true);
//			this.menuItemAddNew.setEnabled(true);
//			this.menuItemEdit.setEnabled(true);
//			this.menuItemRemove.setEnabled(true);
////			this.menuItemCancel.setv(true)
//		}else{
//			this.btnAddNew.setEnabled(false);
//			this.btnEdit.setEnabled(false);
//			this.btnRemove.setEnabled(false);
//			this.btnEnabled.setVisible(false);
//			this.btnDisEnabled.setVisible(false);
//			this.menuItemAddNew.setEnabled(false);
//			this.menuItemEdit.setEnabled(false);
//			this.menuItemRemove.setEnabled(false);
//		}
//		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
//	}


	protected String getEditUIName() {
		return PaymentTypeEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PaymentTypeFactory.getRemoteInstance();
	}

	/**
	 * output actionEnabled_actionPerformed method
	 */
//	public void actionEnabled_actionPerformed(ActionEvent e) throws Exception {
//		IRow row = checkSelected(e);
//		if (row == null)
//			return;
//		String id = row.getCell("id").getValue().toString().trim();
//		IPaymentType iPaymentType = PaymentTypeFactory.getRemoteInstance();
//		if(iPaymentType.enabled(new ObjectUuidPK(id))){
//			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
//			showMessage();
//		}		
//		actionRefresh_actionPerformed(e);
//	}

	/**
	 * output actionDisEnabled_actionPerformed method
	 */
//	public void actionDisEnabled_actionPerformed(ActionEvent e) throws Exception {
//		IRow row = checkSelected(e);
//		if (row == null)
//			return;
//		String id = row.getCell("id").getValue().toString().trim();
//		IPaymentType iPaymentType = PaymentTypeFactory.getRemoteInstance();
//		if(iPaymentType.disEnabled(new ObjectUuidPK(id))){
//			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
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
//			row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
//		}
//		return row;
//	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
//	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
//		if (this.tblMain.getSelectManager().getActiveRowIndex() != -1) {
//			// boolean status = false;
//			if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled") != null) {
//				boolean status = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled").getValue()).booleanValue();
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

	/**
	 * 随着每一行规则的isEnabled的值改变，两个btn的状态也改变
	 * 
	 * @param isEnabled
	 *            boolean
	 */
//	private void changeWBTEnabeld(boolean isEnabled) {
//		this.btnEnabled.setEnabled(!isEnabled);
//		this.btnDisEnabled.setEnabled(isEnabled);
//
//	}

	/**
	 * 把启用/禁止按钮disabled
	 */
//	private void disabledWBT() {
//		this.btnEnabled.setEnabled(false);
//		this.btnDisEnabled.setEnabled(false);
//
//	}
	/**
	 * output actionRemove_actionPerformed
	 */
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new PaymentTypeInfo();
	}
}