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
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractDetailDefFactory;
import com.kingdee.eas.fdc.basedata.ContractDetailDefInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.IChangeType;
import com.kingdee.eas.fdc.basedata.IContractDetailDef;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class ContractDetailDefEditUI extends AbstractContractDetailDefEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractDetailDefEditUI.class);
    
    /**
     * output class constructor
     */
    public ContractDetailDefEditUI() throws Exception
    {
        super();
    }
	public void onLoad() throws Exception {
		super.onLoad();
		setTitle();
//		setBtnStatus();	
	}
	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.CONTRACTDETAILDEF));
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
//				this.menuItemRemove.setEnabled(true);
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
//		this.comboDataTypeEnum.setEditable(false);
//	}
    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("contractType.*"));
        sic.add(new SelectorItemInfo("dataTypeEnum"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("isMustInput"));
        return sic;
    }      
	/**
	 * 校验值对象的合法性
	 */
//	protected void verifyInput(ActionEvent e) throws Exception {
//		// 编码是否为空
//		if (this.txtNumber.getText() == null || this.txtNumber.getText().trim().equals("")) {
//			this.txtNumber.requestFocus();
//			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
//		}
//		// 名称是否为空
//		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(bizName, this.editData, "name");
//		if (flag) {
//			bizName.requestFocus();
//			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
//		}
//		if(prmtContractType.getValue()==null||"".equals(prmtContractType.getValue()))
//		{
//			prmtContractType.requestFocus();
//			throw new FDCBasedataException(FDCBasedataException.CONTRACTTYPE_IS_EMPTY);
//		}
//		if(comboDataTypeEnum.getSelectedIndex()<0){
//			comboDataTypeEnum.requestFocus(true);
//			throw new FDCBasedataException(FDCBasedataException.DATATYPE_IS_EMPTY);
//		}
//		if (!getOprtState().equals(OprtState.ADDNEW))return;
//		Object obj=this.getUIContext().get(UIContext.OWNER);
//		if(!(obj instanceof ContractDetailDefListUI))return;
//		KDTable table=((ContractDetailDefListUI)obj).getMainTable();
//		String number=txtNumber.getText();
//		for (int i = 0,count=table.getRowCount(); i < count; i++) {
//			if(number.equalsIgnoreCase((String)table.getCell(i,1).getValue())){
//				throw new FDCBasedataException(FDCBasedataException.NUMBER_ALREADY_EXIST, new Object[] { number });
//			}
//		}
//	}
 	/**
	 * output actionCancel_actionPerformed
	 */
//	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
//		if(this.editData!=null&&this.editData.getId()!=null){
//			IContractDetailDef iContractDetailDef = ContractDetailDefFactory.getRemoteInstance();
//			if(iContractDetailDef.disEnabled(new ObjectUuidPK(editData.getId()))){
//				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
//				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
//		        loadFields();
//				setSave(true);
//		        setSaved(true);
//				this.btnCancelCancel.setVisible(true);
//				this.btnCancelCancel.setEnabled(true);
//				this.btnCancel.setVisible(false);
//			}
//		}
//		
//	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
//	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
//		if(this.editData!=null&&this.editData.getId()!=null){
//			IContractDetailDef iContractDetailDef = ContractDetailDefFactory.getRemoteInstance();
//			if(iContractDetailDef.enabled(new ObjectUuidPK(editData.getId()))){
//				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
//				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
//		        loadFields();
//				setSave(true);
//		        setSaved(true);
//				this.btnCancel.setVisible(true);
//				this.btnCancel.setEnabled(true);
//				this.btnCancelCancel.setVisible(false);
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
		ContractDetailDefInfo contractDetailDefInfo = new ContractDetailDefInfo();
		contractDetailDefInfo.setIsEnabled(isEnabled);
		return contractDetailDefInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractDetailDefFactory.getRemoteInstance();
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
	protected void verifyInput(ActionEvent e) throws Exception {
    	FDCClientVerifyHelper.verifyEmpty(this, prmtContractType);
    	//FDCBaseDataEditUI中的提示是x,FDCClientVerifyHelper中的提示！，不一致所以屏蔽
    	//super.verifyInput(e);
    	FDCClientVerifyHelper.verifyEmpty(this, txtNumber);
    	FDCClientVerifyHelper.verifyEmpty(this, bizName);
        FDCClientVerifyHelper.verifyEmpty(this, comboDataTypeEnum);
    }
}