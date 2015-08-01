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
 * 描述:指标编辑界面
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
		
		//计量单位的过滤条件设置成左数右表的结构
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
		if (STATUS_ADDNEW.equals(getOprtState())) {//新增状态
			this.btnCancelCancel.setVisible(false);//启用按钮不可见
			this.btnCancel.setVisible(false);//禁用按钮不可见
		} else if (STATUS_EDIT.equals(getOprtState())) {//修改状态
			if (this.editData.isIsEnabled()) {//如果当前为启用状态
				this.btnCancel.setVisible(true);//禁用按钮可用    			
				this.btnCancel.setEnabled(true);//禁用按钮可用
				this.btnCancelCancel.setVisible(false);//启用按钮不可见
			} else {//如果当前为禁用状态
				this.btnCancelCancel.setVisible(true);//启用按钮可见
				this.btnCancelCancel.setEnabled(true);//启用按钮可用    			
				this.btnCancel.setVisible(false);//禁用按钮不可见    			
			}
			if(OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())){				
				this.btnRemove.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}
			if(this.editData.getId().toString().equals("qHQt0wEMEADgAAaOoKgTuzW0boA=")){
				this.chkIsForCostApportion.setEnabled(false);
			}
		} else if (STATUS_VIEW.equals(getOprtState())) {//查看状态			
			if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())){
				if (this.editData.isIsEnabled()) {//如果当前为启用状态
					this.btnCancel.setVisible(true);//禁用按钮可用    			
					this.btnCancel.setEnabled(true);//禁用按钮可用
					this.btnCancelCancel.setVisible(false);//启用按钮不可见
				} else {//如果当前为禁用状态
					this.btnCancelCancel.setVisible(true);//启用按钮可见
					this.btnCancelCancel.setEnabled(true);//启用按钮可用    			
					this.btnCancel.setVisible(false);//禁用按钮不可见    			
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
		
//		 指标类型是否为空
		if (this.kdBizTargetType.getValue()==null) {
			kdBizTargetType.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.TARGETTYPE_ISNULL);
		}
		//计量单位是否为空
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