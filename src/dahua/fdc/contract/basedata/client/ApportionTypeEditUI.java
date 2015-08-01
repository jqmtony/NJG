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
import com.kingdee.eas.basedata.master.material.Material;
import com.kingdee.eas.basedata.master.material.client.MaterialClientTools;
import com.kingdee.eas.basedata.master.material.client.MaterialEditUI;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.AdjustTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.IAdjustType;
import com.kingdee.eas.fdc.basedata.IApportionType;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ����:ָ��༭����
 * 
 * @author jackwang date:2006-7-7
 * @version EAS5.1
 */
public class ApportionTypeEditUI extends AbstractApportionTypeEditUI {
	private static final Logger logger = CoreUIObject.getLogger(ApportionTypeEditUI.class);

	/**
	 * output class constructor
	 */
	public ApportionTypeEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setTitle();
		setBtnStatus();	
		
		//������λ�Ĺ����������ó������ұ�Ľṹ
		MaterialClientTools.setMeasureUnitF7(this, kdBizMeasureUnit);
		this.kdBizMeasureUnit.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
		
		this.kdBizTargetType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7TargetTypeQuery");
		this.kdBizTargetType.setEditable(false);
		this.kdBizMeasureUnit.setEditable(false);
		
		kdBizMeasureUnit.setEditable(true);
		kdBizTargetType.setEditable(true);
	}

	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.APPORTIONTYPE));
	}

	private void setBtnStatus() {
		if (STATUS_ADDNEW.equals(getOprtState())) {//����״̬
			this.btnCancelCancel.setVisible(false);//���ð�ť���ɼ�
			this.btnCancel.setVisible(false);//���ð�ť���ɼ�
		} else if (STATUS_EDIT.equals(getOprtState())) {//�޸�״̬
			if (this.editData.isIsEnabled()) {//�����ǰΪ����״̬
				this.btnCancel.setVisible(true);//���ð�ť����    			
				this.btnCancel.setEnabled(true);//���ð�ť����
				this.btnCancelCancel.setVisible(false);//���ð�ť���ɼ�
			} else {//�����ǰΪ����״̬
				this.btnCancelCancel.setVisible(true);//���ð�ť�ɼ�
				this.btnCancelCancel.setEnabled(true);//���ð�ť����    			
				this.btnCancel.setVisible(false);//���ð�ť���ɼ�    			
			}
			if(OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())){				
				this.btnRemove.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}
			if(this.editData.getId().toString().equals("qHQt0wEMEADgAAaOoKgTuzW0boA=")){
				this.chkIsForCostApportion.setEnabled(false);
			}
		} else if (STATUS_VIEW.equals(getOprtState())) {//�鿴״̬			
			if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())){
				if (this.editData.isIsEnabled()) {//�����ǰΪ����״̬
					this.btnCancel.setVisible(true);//���ð�ť����    			
					this.btnCancel.setEnabled(true);//���ð�ť����
					this.btnCancelCancel.setVisible(false);//���ð�ť���ɼ�
				} else {//�����ǰΪ����״̬
					this.btnCancelCancel.setVisible(true);//���ð�ť�ɼ�
					this.btnCancelCancel.setEnabled(true);//���ð�ť����    			
					this.btnCancel.setVisible(false);//���ð�ť���ɼ�    			
				}				
				this.btnAddNew.setEnabled(true);
				this.btnEdit.setEnabled(true);
				this.menuItemAddNew.setEnabled(true);
				this.menuItemEdit.setEnabled(true);
//				this.menuItemRemove.setEnabled(true);
			}else{
				this.btnAddNew.setEnabled(false);
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
				this.btnCancel.setVisible(false);
				this.btnCancelCancel.setVisible(false);
				this.menuItemAddNew.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}	
			if(this.editData.getId().toString().equals("qHQt0wEMEADgAAaOoKgTuzW0boA=")){
				this.chkIsForCostApportion.setEnabled(false);
			}
		}
		
		chkIsForCostApportion.setEnabled(!this.getOprtState().equals(OprtState.VIEW));
	}
    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("CU.id"));
        sic.add(new SelectorItemInfo("targetType.*"));
        sic.add(new SelectorItemInfo("measureUnit.*"));
        sic.add(new SelectorItemInfo("forCostApportion"));
        sic.add(new SelectorItemInfo("forGather"));
        return sic;
    } 
	/**
	 * У��ֵ����ĺϷ���
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
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
		
		super.verifyInput(e);
		
//		 ָ�������Ƿ�Ϊ��
		if (this.kdBizTargetType.getValue()==null) {
			kdBizTargetType.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.TARGETTYPE_ISNULL);
		}
		//������λ�Ƿ�Ϊ��
//		if(kdBizMeasureUnit.getValue()==null){
//			throw new FDCBasedataException(FDCBasedataException.MEASUREUNIT_ISNULL);
//		}
		if (getOprtState().equals(OprtState.ADDNEW))
		FDCBaseTypeValidator.validate(((ApportionTypeListUI) getUIContext().get(UIContext.OWNER)).getMainTable(), txtNumber, bizName,this.kdBizTargetType.getValue().toString(),"targetType.Name", getSelectBOID());
	}
	/**
	 * output actionCancel_actionPerformed
	 */
//	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
//		if(this.editData!=null&&this.editData.getId()!=null){
//			IApportionType iApportionType = ApportionTypeFactory.getRemoteInstance();
//			if(iApportionType.disEnabled(new ObjectUuidPK(editData.getId()))){
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
//			IApportionType iApportionType = ApportionTypeFactory.getRemoteInstance();
//			if(iApportionType.enabled(new ObjectUuidPK(editData.getId()))){
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
		ApportionTypeInfo apportionTypeInfo = new ApportionTypeInfo();
		apportionTypeInfo.setIsEnabled(isEnabled);
//		this.txtNumber.setFocusable(true);
		return apportionTypeInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ApportionTypeFactory.getRemoteInstance();
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
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_EDIT.equals(this.oprtState)) {
			chkIsForCostApportion.setEnabled(true);
		}
	}
	
}