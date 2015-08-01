/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.costdb.IMarketInfoType;
import com.kingdee.eas.fdc.costdb.MarketInfoTypeFactory;
import com.kingdee.eas.fdc.costdb.MarketInfoTypeInfo;
import com.kingdee.eas.fdc.invite.FDCInviteException;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MarketInfoTypeEditUI extends AbstractMarketInfoTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketInfoTypeEditUI.class);
    
	private OrgUnitInfo currentOrg = SysContext.getSysContext()
		.getCurrentOrgUnit();
    /**
     * output class constructor
     */
    public MarketInfoTypeEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output txtNumber_actionPerformed method
     */
    protected void txtNumber_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.txtNumber_actionPerformed(e);
    }

    /**
     * output txtParentNumber_actionPerformed method
     */
    protected void txtParentNumber_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.txtParentNumber_actionPerformed(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {	
    	checkCtrlUnit();
		if(!editData.getOrg().getId().toString().equals(currentOrg.getId().toString())){
			MsgBox.showWarning(this,"不能在其他组织建立的类型下新增类型！");
			SysUtil.abort();
		}
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkCtrlUnit();
        super.actionEdit_actionPerformed(e);
		String id = editData.getId().toString();
		String[] tables = new String[]{"t_cdb_marketinfo"};
		IMarketInfoType iMarketType = MarketInfoTypeFactory.getRemoteInstance();
		if(iMarketType.exists("select id where parent.id ='"+id+"' ")){
//			this.actionEdit.setEnabled(false);
			this.txtNumber.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		Object[] newTables = iMarketType.getRelateData(id, tables);
		if(newTables.length>0){
//			this.actionEdit.setEnabled(false);
			this.txtNumber.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	checkCtrlUnit();

        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

	protected IObjectValue createNewData() {
		MarketInfoTypeInfo objectValue = new MarketInfoTypeInfo();
		objectValue.setOrg((FullOrgUnitInfo) SysContext.getSysContext()
				.getCurrentOrgUnit());
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MarketInfoTypeFactory.getRemoteInstance();
	}

	public void loadFields() {
		super.loadFields();
		Map map = getUIContext();
		MarketInfoTypeInfo fatherMarketInfoTypeInfo = (MarketInfoTypeInfo) map
				.get(UIContext.PARENTNODE);
		if(null != fatherMarketInfoTypeInfo){
			String fatherLongNumber = fatherMarketInfoTypeInfo.getLongNumber();
			fatherLongNumber = fatherLongNumber.replaceAll("!",".");
			this.txtParentNumber.setText(fatherLongNumber);
		}
	}

	public void onLoad() throws Exception {
//		if(!MarketHelper.checkOrgPerm(this,this.currentOrg.getId().toString()))
//			this.abort();
		
		this.btnAttachment.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		this.btnCopy.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnSave.setVisible(false);
		this.menuItemAddNew.setEnabled(false);
		this.menuItemEdit.setEnabled(false);
		this.menuItemRemove.setEnabled(false);
		this.txtParentNumber.setEditable(false);
		this.txtParentNumber.setEnabled(false);
		this.conParentNumber.setEnabled(false);
		this.isDefault.setVisible(false);
		this.isDefault.setEnabled(false);
		super.onLoad();
		String orgId = "";
		String currentOrgId = currentOrg.getId().toString();
		if(editData.getOrg()!=null){
			orgId = editData.getOrg().getId().toString();
		}
		if(orgId.equals(currentOrgId) && !editData.getBoolean("isDefault")){
			this.actionRemove.setEnabled(true);
			this.actionEdit.setEnabled(true);
//			this.actionAddNew.setEnabled(true);
		}else{
			this.actionRemove.setEnabled(false);
			this.actionEdit.setEnabled(false);
//			this.actionAddNew.setEnabled(false);
		}
		if(editData.getId()!=null){
			if(this.getBizInterface().exists("select id where parent.id ='"+editData.getId().toString()+"' ")){
				this.txtNumber.setEnabled(false);
			}
		}
		if(this.getOprtState().equals(STATUS_ADDNEW)){
			this.actionEdit.setEnabled(false);
		}
		if(this.getOprtState().equals(STATUS_EDIT)){
			this.actionEdit.setEnabled(false);
			String id = editData.getId().toString();
			String[] tables = new String[]{"t_cdb_marketinfo"};
			IMarketInfoType iMarketType = MarketInfoTypeFactory.getRemoteInstance();
			if(iMarketType.exists("select id where parent.id ='"+id+"' ")){
//				this.actionEdit.setEnabled(false);
				this.txtNumber.setEnabled(false);
				this.actionRemove.setEnabled(false);
			}
			Object[] newTables = iMarketType.getRelateData(id, tables);
			if(newTables.length>0){
//				this.actionEdit.setEnabled(false);
				this.txtNumber.setEnabled(false);
				this.actionRemove.setEnabled(false);
			}
		}
		if(this.getOprtState().equals(STATUS_VIEW)){
			if(editData.isIsDefault()){
				this.actionEdit.setEnabled(false);
				this.actionRemove.setEnabled(false);
			}
			
			String id = editData.getId().toString();
			String[] tables = new String[]{"t_cdb_marketinfo"};
			IMarketInfoType iMarketType = MarketInfoTypeFactory.getRemoteInstance();
			if(iMarketType.exists("select id where parent.id ='"+id+"' ")){
//				this.actionEdit.setEnabled(false);
				this.txtNumber.setEnabled(false);
				this.actionRemove.setEnabled(false);
			}
			Object[] newTables = iMarketType.getRelateData(id, tables);
			if(newTables.length>0){
//				this.actionEdit.setEnabled(false);
				this.txtNumber.setEnabled(false);
				this.actionRemove.setEnabled(false);
			}
		}
		if((STATUS_VIEW.equals(this.getOprtState()) || STATUS_EDIT.equals(this.getOprtState()))){
			if(editData.getParent() != null){
				this.txtParentNumber.setText(editData.getParent().getLongNumber());
			}else{
				this.txtParentNumber.setText(null);
			}
			
		}
		this.txtName.setMaxLength(30);
		this.txtNumber.setMaxLength(30);
		this.txtDescription.setMaxLength(255);
		this.setUITitle("资料类型");

	}
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("org.*"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("parent.*"));
        sic.add(new SelectorItemInfo("longNumber"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("isDefault"));
        return sic;
    } 
	public void onShow() throws Exception {
		super.onShow();
		//父节点编码不可录
		this.txtParentNumber.setEditable(false);
		this.txtParentNumber.setEnabled(false);
		this.conParentNumber.setEnabled(false);
	}
	//空值验证
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		Map map = getUIContext();
		Object parentObj = map.get(UIContext.PARENTNODE);
		// 编码是否为空
		if (this.txtNumber.getText() == null
				|| this.txtNumber.getText().trim().equals("")) {
//			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
			MsgBox.showWarning(this,"本级编码不能为空！");
			SysUtil.abort();
		} 
		if(this.txtName.getText()==null
				|| this.txtName.getText().trim().equals("")){
			MsgBox.showWarning(this,"本级名称不能为空！");
			SysUtil.abort();
		}
		FilterInfo filterInfo = new FilterInfo();
		String temp = this.txtNumber.getText().trim();
		if(this.txtParentNumber.getText().trim()!=null 
				&& !this.txtParentNumber.getText().trim().equals("")){
			temp = this.txtParentNumber.getText().trim() + "!"+this.txtNumber.getText().trim();
		}
		editData.setLongNumber(temp);
		filterInfo.getFilterItems().add(new FilterItemInfo("longnumber",temp));
//		filterInfo.getFilterItems.add();
		if(editData.getId()!=null)
			filterInfo.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(),CompareType.NOTEQUALS));
		if (getBizInterface().exists(filterInfo)) {
			throw new FDCInviteException(
					FDCInviteException.NUMBER_IS_OVER_IN_ONE_ORG);
		}
		filterInfo = new FilterInfo();
		String txtName = this.txtName.getText().trim();
		filterInfo.getFilterItems().add(new FilterItemInfo("name",txtName));
		if(editData.getId()!=null)
			filterInfo.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(),CompareType.NOTEQUALS));
		if (getBizInterface().exists(filterInfo)) {
			MsgBox.showWarning(this,"名称不能重复！");
			SysUtil.abort();
		}
	}
    public void checkCtrlUnit(){
    	if(!currentOrg.getId().toString().equals(OrgConstants.SYS_CU_ID)
    			&& !currentOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)){

        	if(!this.currentOrg.isIsCU() ){
        		MsgBox.showWarning(this,"只有集团和管理单元才可以维护类型！");
        		SysUtil.abort();
        	}
    	}

    }

}