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
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.TypeFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * 描述:付款方式编辑界面
 * 
 * @author jackwang date:2006-7-7
 * @version EAS5.1
 */
public class PaymentTypeEditUI extends AbstractPaymentTypeEditUI {
	private static final Logger logger = CoreUIObject.getLogger(PaymentTypeEditUI.class);

	/**
	 * output class constructor
	 */
	public PaymentTypeEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setTitle();
	}

	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.PAYMENTTYPE));
	}

	/**
	 * 校验值对象的合法性
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
//		// 编码是否为空
//		if (this.txtNumber.getText() == null || this.txtNumber.getText().trim().equals("")) {
//			txtNumber.requestFocus(true);
//			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
//		}
//		// 名称是否为空
//		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(bizName, this.editData, "name");
//		if (flag) {
//			bizName.requestFocus(true);
//			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
//		}
		super.verifyInput(e);
		FDCClientVerifyHelper.verifyEmpty(this, prmtType);
		if (getOprtState().equals(OprtState.ADDNEW))
		FDCBaseTypeValidator.validate(((PaymentTypeListUI) getUIContext().get(UIContext.OWNER)).getMainTable(), txtNumber, bizName, getSelectBOID());
	}

	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}
	protected IObjectValue createNewData() {
		PaymentTypeInfo paymentTypeInfo = new PaymentTypeInfo();
		paymentTypeInfo.setIsEnabled(isEnabled);
		String orgUnit = null;
		if(SysContext.getSysContext().getCurrentFIUnit()==null){
			FDCMsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.contract.ContractResource", "warnAddOrgFI"));
			SysUtil.abort();
		}
		orgUnit = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		try {
			if (FDCUtils.getDefaultFDCParamByKey(null, orgUnit,
					FDCConstants.FDC_PARAM_SIMPLEFINACIAL)) {
				paymentTypeInfo.setPayType(TypeFactory.getRemoteInstance()
						.getTypeInfo(
								new ObjectUuidPK(BOSUuid
										.read(PaymentTypeInfo.progressID))));
			}
		} catch (Exception e) {
			logger.error(e);
			handUIExceptionAndAbort(e);
		} 
		return paymentTypeInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PaymentTypeFactory.getRemoteInstance();
	}

	protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return bizName;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	/**
	 * 提交成功的显示接口
	 */
	protected void showSubmitSuccess() {
		// 2004-09-21 by Jerry
		// showSuccessMessage(FrameWorkClientUtils.strResource + "Msg_Save_OK");
		setMessageText(getClassAlise()
				+ " "
				+ EASResource.getString(FrameWorkClientUtils.strResource
						+ "Msg_Save_OK"));
		if (this.chkMenuItemSubmitAndAddNew.isSelected()) {
			setNextMessageText(getClassAlise()
					+ " "
					+ EASResource.getString(FrameWorkClientUtils.strResource
							+ "Msg_AddNew"));
		} else if (!this.chkMenuItemSubmitAndPrint.isSelected()
				&& this.chkMenuItemSubmitAndAddNew.isSelected()) {
			setNextMessageText(getClassAlise()
					+ " "
					+ EASResource.getString(FrameWorkClientUtils.strResource
							+ "Msg_AddNew"));
		} else {
			setNextMessageText(getClassAlise()
					+ " "
					+ EASResource.getString(FrameWorkClientUtils.strResource
							+ "Msg_Edit"));
		}
		setIsShowTextOnly(false);
		setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
		showMessage();
	}
}