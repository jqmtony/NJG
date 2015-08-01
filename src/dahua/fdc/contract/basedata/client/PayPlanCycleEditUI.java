/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PayPlanCycleFactory;
import com.kingdee.eas.fdc.basedata.PayPlanCycleInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class PayPlanCycleEditUI extends AbstractPayPlanCycleEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(PayPlanCycleEditUI.class);
    private String orgID = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
    
    /**
     * output class constructor
     */
    public PayPlanCycleEditUI() throws Exception
    {
        super();
        this.txtNumber.setEnabled(false);
    }

	protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		// TODO Auto-generated method stub
		return null;
	}

	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return txtNumber;
	}

	protected IObjectValue createNewData() {
		PayPlanCycleInfo obj = new PayPlanCycleInfo();
		obj.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		try {
			obj.setCreateTime(FDCDateHelper.getServerTimeStamp());
			obj.setIsEnabled(false);
		} catch (BOSException e) {
			logger.error(e.getMessage(), e);
			this.handUIExceptionAndAbort(e);
		}
		return obj;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PayPlanCycleFactory.getRemoteInstance();
	}

	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, "付款计划周期");
	}
	
	protected void beforeStoreFields(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.beforeStoreFields(e);
		FDCClientVerifyHelper.verifyEmpty(this, txtNumber);
		FDCClientVerifyHelper.verifyEmpty(this, txtName);
//		FDCClientVerifyHelper.verifyEmpty(this, prmtOrg);
		FDCClientVerifyHelper.verifyEmpty(this, comboCycle);
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		
	}
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
		super.actionAddNew_actionPerformed(e);
		this.addNew(e);
		ICodingRuleManager iCoding = CodingRuleManagerFactory.getRemoteInstance();
		if (iCoding.isAddView(editData, orgID)) {
			getNumberByCodingRule(editData, orgID);
		} else if (iCoding.isUseIntermitNumber(editData, orgID)) {
			txtNumber.setEnabled(true);
		}
	}

	/**
	 * 描述：保存后为连续新增做准备
	 * @param e
	 * @throws Exception
	 * @throws BOSException
	 * @throws EASBizException
	 * @Author：jian_cao
	 * @CreateTime：2013-4-15
	 */
	private void addNew(ActionEvent e) throws Exception, BOSException, EASBizException {
		FullOrgUnitInfo _org = (FullOrgUnitInfo)this.prmtOrg.getValue();
		comboCycle.setSelectedIndex(2);
		Object o = getUIContext().get("selectedOrg");
		FullOrgUnitInfo org = null;
		if(o!=null){
			org = ((OrgStructureInfo)o).getUnit();
		}else{
			org=_org;
		}
		this.prmtOrg.setValue(org);
		if(org!=null){
			prmtOrg.setText(org.getName());
		}
		this.txtNumber.setEnabled(true);
	}

	/**
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI#actionSubmit_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.addNew(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData==null||this.editData.getId()==null){
			return;
		}
		String id = this.editData.getId().toString();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select FIsEnabled from T_FDC_PayPlanCycle where FID = ?  ");
		builder.addParam(id);
		IRowSet rowSet = builder.executeQuery();
		if( rowSet!= null&& rowSet.size()==1){
			rowSet.next();
			boolean isEnabled = rowSet.getBoolean("FIsEnabled");
			if(isEnabled){//启用状态的禁止修改
				MsgBox.showWarning("该单据已被启用，不允许修改！");
				SysUtil.abort();
			}
		}
		
		super.actionEdit_actionPerformed(e);
		this.txtNumber.setEnabled(false);
		this.prmtOrg.setEnabled(false);
		this.prmtOrg.setEditable(false);
	}
	public void onLoad() throws Exception {
		
		this.menuBar.setVisible(false);
		this.prmtOrg.setEnabled(false);
		this.prmtOrg.setEditable(false);
		super.onLoad();
		setTitle();
		
		
		if(STATUS_ADDNEW.equals(getOprtState())){
			comboCycle.setSelectedIndex(2);
			Object o = getUIContext().get("selectedOrg");
			FullOrgUnitInfo org = null;
			if(o!=null){
				org = ((OrgStructureInfo)o).getUnit();
			}
			this.prmtOrg.setValue(org);
			if(org!=null){
				prmtOrg.setText(org.getName());
			}
		}
		txtNumber.setMaxLength(80);
		txtName.setMaxLength(80);
		txtDescription.setMaxLength(255);
		btnCancelCancel.setVisible(false);
		btnCancel.setVisible(false);
		btnRemove.setVisible(false);
		ICodingRuleManager iCoding = CodingRuleManagerFactory.getRemoteInstance();
		if(iCoding.isExist(editData, orgID)){
			if (STATUS_ADDNEW.equals(getOprtState()) && iCoding.isAddView(editData, orgID)){
				getNumberByCodingRule(editData,orgID);
				txtNumber.setEditable(false);
			}
			else if(iCoding.isUseIntermitNumber(editData, orgID)||!iCoding.isModifiable(editData, orgID))
			{
				txtNumber.setEditable(true);
			}
		}
		//启用状态的修改按钮置灰
		if(this.editData==null||this.editData.getId()==null){
			txtNumber.setEnabled(true);
			return;
		}
		String id = this.editData.getId().toString();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select FIsEnabled from T_FDC_PayPlanCycle where FID = ?  ");
		builder.addParam(id);
		IRowSet rowSet = builder.executeQuery();
		if( rowSet!= null&& rowSet.size()==1){
			rowSet.next();
			boolean isEnabled = rowSet.getBoolean("FIsEnabled");
			if(isEnabled){
				btnEdit.setEnabled(false);
				txtNumber.setEditable(false);
			}
		}
	}
	
	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);
		this.txtNumber.setText(number);
	}
}