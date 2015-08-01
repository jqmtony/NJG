/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CostTargetFactory;
import com.kingdee.eas.fdc.basedata.DeductAccountEntrysFactory;
import com.kingdee.eas.fdc.basedata.DeductTypeFactory;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.ICostTarget;
import com.kingdee.eas.fdc.basedata.IDeductType;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述:扣款类型编辑
 * @author jackwang  date:2006-8-29 <p>
 * @version EAS5.1
 */
public class DeductTypeEditUI extends AbstractDeductTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(DeductTypeEditUI.class);
    
    /**
     * output class constructor
     */
    public DeductTypeEditUI() throws Exception
    {
        super();
    }
	public void onLoad() throws Exception {
		super.onLoad();
		setTitle();
//		setBtnStatus();		
	}
	
	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.DEDUCTTYPE));
	}

//	private void setBtnStatus() {
//		if (STATUS_ADDNEW.equals(getOprtState())) {//新增状态
//			this.btnCancelCancel.setVisible(false);//启用按钮不可见
//			this.btnCancel.setVisible(false);//禁用按钮不可见
//		} else if (STATUS_EDIT.equals(getOprtState())) {//修改状态
//			if (this.editData.isIsEnabled()) {//如果当前为启用状态
//				this.btnCancel.setVisible(true);//禁用按钮可用    			
//				this.btnCancel.setEnabled(true);//禁用按钮可用
//				this.btnCancelCancel.setVisible(false);//启用按钮不可见
//			} else {//如果当前为禁用状态
//				this.btnCancelCancel.setVisible(true);//启用按钮可见
//				this.btnCancelCancel.setEnabled(true);//启用按钮可用    			
//				this.btnCancel.setVisible(false);//禁用按钮不可见    			
//			}
//			if(OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())){				
//				this.btnRemove.setEnabled(false);
//				this.menuItemRemove.setEnabled(false);
//			}
//		} else if (STATUS_VIEW.equals(getOprtState())) {//查看状态			
//			if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())){
//				if (this.editData.isIsEnabled()) {//如果当前为启用状态
//					this.btnCancel.setVisible(true);//禁用按钮可用    			
//					this.btnCancel.setEnabled(true);//禁用按钮可用
//					this.btnCancelCancel.setVisible(false);//启用按钮不可见
//				} else {//如果当前为禁用状态
//					this.btnCancelCancel.setVisible(true);//启用按钮可见
//					this.btnCancelCancel.setEnabled(true);//启用按钮可用    			
//					this.btnCancel.setVisible(false);//禁用按钮不可见    			
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
//				this.menuItemEdit.setEnabled(false);
//				this.menuItemRemove.setEnabled(false);
//			}
//		}
//	}
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
        return sic;
    }
	
	/**
	 * 校验值对象的合法性
	 */
//	protected void verifyInput(ActionEvent e) throws Exception {
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
//		if (getOprtState().equals(OprtState.ADDNEW))
//		FDCBaseTypeValidator.validate(((DeductTypeListUI) getUIContext().get(UIContext.OWNER)).getMainTable(), txtNumber, bizName);
//	}
   
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
        if(OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())){				
			this.btnRemove.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
		}
    }

   

	protected IObjectValue createNewData() {
		DeductTypeInfo deductTypeInfo = new DeductTypeInfo();
		FDCBaseDataClientUtils.setFieldsNull(deductTypeInfo);
		deductTypeInfo.setIsEnabled(isEnabled);
		return deductTypeInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {

		return DeductTypeFactory.getRemoteInstance();
	}
	/**
	 * output actionCancel_actionPerformed
	 */
//	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
//		if(this.editData!=null&&this.editData.getId()!=null){
//			IDeductType iDeductType = DeductTypeFactory.getRemoteInstance();
//			if(iDeductType.disEnabled(new ObjectUuidPK(editData.getId()))){			
//				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
//				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
//				loadFields();
//				setSave(true);
//				setSaved(true);
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
//			IDeductType iDeductType = DeductTypeFactory.getRemoteInstance();
//			if(iDeductType.enabled(new ObjectUuidPK(editData.getId()))){
//				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
//				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
//				loadFields();
//				setSave(true);
//				setSaved(true);
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
	protected FDCDataBaseInfo getEditData() {
		return editData;
	}
	protected KDBizMultiLangBox getNameCtrl() {
		return bizName;
	}
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (editData != null && editData.getId() != null) {
			String id = editData.getId().toString();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems()
					.add(
							new FilterItemInfo("deductType.id", id,
									CompareType.INCLUDE));
			if (DeductAccountEntrysFactory.getRemoteInstance().exists(filter)) {
				String error = "部分数据不能删除,具体原因见详细信息!";
				String msg = "已发生业务不能删除!";
				MsgBox.showDetailAndOK(this, error, msg, 0);
				SysUtil.abort();
			}
		}
		super.actionRemove_actionPerformed(e);
	}
}