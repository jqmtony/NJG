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
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.IPaymentType;
import com.kingdee.eas.fdc.basedata.IProductType;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;

/**
 * ����:��Ʒ���ͱ༭����
 * 
 * @author jackwang date:2006-7-7
 * @version EAS5.1
 */
public class ProductTypeEditUI extends AbstractProductTypeEditUI {
	private static final Logger logger = CoreUIObject.getLogger(ProductTypeEditUI.class);

	/**
	 * output class constructor
	 */
	public ProductTypeEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setTitle();
//		setBtnStatus();		
//		PlanIndexType.setVisible(false);
//		contPlanIndexType.setVisible(false);
	}

	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.PRODUCTTYPE));
	}

//	private void setBtnStatus() {
//		if (STATUS_ADDNEW.equals(getOprtState())) {//����״̬
//			this.btnCancelCancel.setVisible(false);//���ð�ť���ɼ�
//			this.btnCancel.setVisible(false);//���ð�ť���ɼ�
//		} else if (STATUS_EDIT.equals(getOprtState())) {//�޸�״̬
//			if (this.editData.isIsEnabled()) {//�����ǰΪ����״̬
//				this.btnCancel.setVisible(true);//���ð�ť����    			
//				this.btnCancel.setEnabled(true);//���ð�ť����
//				this.btnCancelCancel.setVisible(false);//���ð�ť���ɼ�
//			} else {//�����ǰΪ����״̬
//				this.btnCancelCancel.setVisible(true);//���ð�ť�ɼ�
//				this.btnCancelCancel.setEnabled(true);//���ð�ť����    			
//				this.btnCancel.setVisible(false);//���ð�ť���ɼ�    			
//			}
//		} else if (STATUS_VIEW.equals(getOprtState())) {//�鿴״̬			
//			if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())){
//				if (this.editData.isIsEnabled()) {//�����ǰΪ����״̬
//					this.btnCancel.setVisible(true);//���ð�ť����    			
//					this.btnCancel.setEnabled(true);//���ð�ť����
//					this.btnCancelCancel.setVisible(false);//���ð�ť���ɼ�
//				} else {//�����ǰΪ����״̬
//					this.btnCancelCancel.setVisible(true);//���ð�ť�ɼ�
//					this.btnCancelCancel.setEnabled(true);//���ð�ť����    			
//					this.btnCancel.setVisible(false);//���ð�ť���ɼ�    			
//				}				
//				this.btnAddNew.setEnabled(true);
//				this.btnEdit.setEnabled(true);
//				this.menuItemAddNew.setEnabled(true);
//				this.menuItemEdit.setEnabled(true);
////				this.menuItemRemove.setEnabled(true);
//			}else{
//				this.btnAddNew.setEnabled(false);
//				this.btnEdit.setEnabled(false);
//				this.btnRemove.setEnabled(false);
//				this.btnCancel.setVisible(false);
//				this.btnCancelCancel.setVisible(false);
//				this.menuItemAddNew.setEnabled(false);
//				this.menuItemEdit.setEnabled(false);
//				this.menuItemRemove.setEnabled(false);
//			}			
//		}
//	}

	/**
	 * У��ֵ����ĺϷ���
	 */
//	protected void verifyInput(ActionEvent e) throws Exception {
//		// �����Ƿ�Ϊ��
//		if (this.txtNumber.getText() == null || this.txtNumber.getText().trim().equals("")) {
//			txtNumber.requestFocus(true);
//			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
//		}
//		// �����Ƿ�Ϊ��
//		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(bizName, this.editData, "name");
//		if (flag) {
//			bizName.requestFocus(true);
//			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
//		}
//		if (getOprtState().equals(OprtState.ADDNEW))
//		FDCBaseTypeValidator.validate(((ProductTypeListUI) getUIContext().get(UIContext.OWNER)).getMainTable(), txtNumber, bizName);
//	}

//	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
//		super.actionSubmit_actionPerformed(e);
//		this.txtNumber.requestFocus();
//	}
//    public void onShow() throws Exception
//    {
//        super.onShow();
//        this.txtNumber.requestFocus();
//    }

	/**
	 * output actionCancel_actionPerformed
	 */
//	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
//		IProductType iProductType = ProductTypeFactory.getRemoteInstance();
//		if(iProductType.disEnabled(new ObjectUuidPK(editData.getId()))){			
//			this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
//			setDataObject(getValue(new ObjectUuidPK(editData.getId())));
//			loadFields();
//			setSave(true);
//			setSaved(true);
//			this.btnCancelCancel.setVisible(true);
//			this.btnCancelCancel.setEnabled(true);
//			this.btnCancel.setVisible(false);
//			this.chkIsEnabled.setSelected(false);
//		}
//	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
//	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
//		IProductType iProductType = ProductTypeFactory.getRemoteInstance();
//		if(iProductType.enabled(new ObjectUuidPK(editData.getId()))){
//			this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
//			setDataObject(getValue(new ObjectUuidPK(editData.getId())));
//			loadFields();
//			setSave(true);
//			setSaved(true);
//			this.btnCancel.setVisible(true);
//			this.btnCancel.setEnabled(true);
//			this.btnCancelCancel.setVisible(false);
//			this.chkIsEnabled.setSelected(true);
//		}
//	}
//	protected void showResultMessage(String message) {
//		// setMessageText(EASResource.getString(message));
//		setMessageText(message);
//		// setMessageIcon(SHOW_MESSAGE_ICON_ERROR);
//		// setMessageBgcolor(SHOW_MESSAGE_BG_ERROR);
//		showMessage();
//	}
	protected IObjectValue createNewData() {
		ProductTypeInfo productTypeInfo = new ProductTypeInfo();
		productTypeInfo.setIsEnabled(isEnabled);
		return productTypeInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProductTypeFactory.getRemoteInstance();
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
}