/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
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
import com.kingdee.eas.fdc.basedata.ISpecialtyType;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeFactory;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class SpecialtyTypeEditUI extends AbstractSpecialtyTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SpecialtyTypeEditUI.class);
    
    /**
     * output class constructor
     */
    public SpecialtyTypeEditUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
//		setBtnStatus();		
		prmtChangeType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ChangeTypeQuery");
		prmtChangeType.setEditable(false);
	}

	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.SPECIALTYTYPE));
	}

	public void loadFields() {
		super.loadFields();
		setTitle();
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
//			if(OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())){				
//				this.btnRemove.setEnabled(false);
//				this.menuItemRemove.setEnabled(false);
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
//					this.btnCancel.setEnabled(false);//���ð�ť���ɼ�    			
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
//			if(OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())){				
//				this.btnAddNew.setEnabled(false);
////				this.btnEdit.setEnabled(false);
//				this.btnRemove.setEnabled(false);
//				this.btnCancel.setVisible(false);
//				this.btnCancelCancel.setVisible(false);
//				this.menuItemAddNew.setEnabled(false);
////				this.menuItemEdit.setEnabled(false);
//				this.menuItemRemove.setEnabled(false);
//			}
//		}
//	}
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
		sic.add(new SelectorItemInfo("changeType.*"));
		return sic;
	}
	/**
	 * У��ֵ����ĺϷ���
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		
		// �����Ƿ�Ϊ��
		KDTextField txtNumber = this.getNumberCtrl();
		if (txtNumber.getText() == null || txtNumber.getText().trim().length() < 1) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		}
		// �����Ƿ�Ϊ��
		KDBizMultiLangBox txtName = getNameCtrl();
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(txtName, getEditData(), "name");
		if (flag) {
			txtName.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}
//		super.verifyInput(e);
//		��������Ƿ�Ϊ��
		if (this.prmtChangeType.getValue()==null) {
			prmtChangeType.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.CHANGETYPE_ISNULL);
		}
		Object obj=getUIContext().get(UIContext.OWNER);
		if(!(obj instanceof SpecialtyTypeListUI))return;
		KDTable table=((SpecialtyTypeListUI)obj).getMainTable();
		String type=prmtChangeType.getValue().toString();
		FDCBaseTypeValidator.validate(table,txtNumber,bizName,type,"changeType.name", getSelectBOID());
	}
   
	/**
	 * output actionCancel_actionPerformed
	 */
//	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
//		if(this.editData!=null&&this.editData.getId()!=null){
//			ISpecialtyType iSpecialtyType = SpecialtyTypeFactory.getRemoteInstance();
//			if(iSpecialtyType.disEnabled(new ObjectUuidPK(editData.getId()))){
//				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
//				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
//		        loadFields();
//				setSave(true);
//		        setSaved(true);
//				this.btnCancelCancel.setVisible(true);
//				this.btnCancelCancel.setEnabled(true);
//				this.btnCancel.setVisible(false);
//				this.chkIsEnabled.setSelected(false);
//			}
//		}
//	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
//	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
//		if(this.editData!=null&&this.editData.getId()!=null){
//			ISpecialtyType iSpecialtyType = SpecialtyTypeFactory.getRemoteInstance();
//			if(iSpecialtyType.enabled(new ObjectUuidPK(editData.getId()))){
//				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
//				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
//		        loadFields();
//				setSave(true);
//		        setSaved(true);
//				this.btnCancel.setVisible(true);
//				this.btnCancel.setEnabled(true);
//				this.btnCancelCancel.setVisible(false);
//				this.chkIsEnabled.setSelected(true);
//			}
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
		SpecialtyTypeInfo specialtyTypeInfo = new SpecialtyTypeInfo();
		specialtyTypeInfo.setIsEnabled(isEnabled);
		return specialtyTypeInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SpecialtyTypeFactory.getRemoteInstance();
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