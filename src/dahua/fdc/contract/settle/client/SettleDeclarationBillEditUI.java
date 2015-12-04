/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.settle.client;

import java.awt.event.*;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillFactory;
import com.kingdee.eas.fdc.contract.settle.app.BillStateEnum;
import com.kingdee.eas.fdc.contract.settle.app.TrialStatusEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.swing.KDComboBox;

@SuppressWarnings("serial")
public class SettleDeclarationBillEditUI extends AbstractSettleDeclarationBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SettleDeclarationBillEditUI.class);
    
    public SettleDeclarationBillEditUI() throws Exception
    {
        super();
        prmtcontractNumber.setDisplayFormat("$number$");
    	prmtcontractNumber.setEditFormat("$number$");
    	prmtcontractNumber.setCommitFormat("$number$");
        
    }
    public void onLoad() throws Exception {
    	
//    	this.btnCopyFrom.setVisible(true);
//    	this.btnCopyFrom.setEnabled(true);

        this.btnRemove.setVisible(false);
        this.btnCopy.setVisible(false);
        this.btnAttachment.setVisible(false);
        this.btnPrint.setVisible(false);
        this.btnPrintPreview.setVisible(false);
        this.btnFirst.setVisible(false);
        this.btnPre.setVisible(false);
        this.btnNext.setVisible(false);
        this.btnLast.setVisible(false);
        this.btnAuditResult.setVisible(false);
        this.btnMultiapprove.setVisible(false);
        this.btnNextPerson.setVisible(false);
//
        this.contstate.setEnabled(false);
        this.contbillState.setEnabled(false);

        this.chkisVersion.setEnabled(false);
        this.contversion.setEnabled(false);
    	super.onLoad();
    	initUI();
    	if (getOprtState().equals(OprtState.ADDNEW)) {
    	      

    	      this.actionAddNew.setEnabled(false);
    	      this.actionEdit.setEnabled(false);
    	      this.actionSave.setEnabled(true);
    	      this.actionRemove.setEnabled(false);
    	      this.actionSubmit.setEnabled(false);
    	      this.actionAudit.setEnabled(false);
    	      this.actionUnAudit.setEnabled(false);
    	      this.btnAudit.setEnabled(false);
    	      this.btnUnAudit.setEnabled(false);
    	      this.btnInTrial.setEnabled(false);
    	      this.btnApproved.setEnabled(false);

    	    }
    	if (getOprtState().equals(OprtState.VIEW)) {
    		
    	      this.actionAddNew.setEnabled(false);
    	      this.actionEdit.setEnabled(false);
    	      this.actionSave.setEnabled(false);
    	      this.actionRemove.setEnabled(false);
    	      this.actionSubmit.setEnabled(false);
    	      this.actionAudit.setEnabled(false);
    	      this.actionUnAudit.setEnabled(false);
    	      
    	      this.btnAudit.setEnabled(false);
    	      this.btnUnAudit.setEnabled(false);
    	      this.btnInTrial.setEnabled(false);
    	      this.btnApproved.setEnabled(false);
    	      
    	      if (this.editData.getBillState().equals(BillStateEnum.AUDIT)&&this.editData.isIsVersion()) {
    	    	  this.actionAddNew.setEnabled(false);
        	      this.actionEdit.setEnabled(false);
        	      this.actionSave.setEnabled(false);
        	      this.actionRemove.setEnabled(false);
        	      this.actionSubmit.setEnabled(false);
        	      this.actionAudit.setEnabled(false);
        	      this.actionUnAudit.setEnabled(true);
        	      
        	      this.btnAudit.setEnabled(false);
        	      this.btnUnAudit.setEnabled(true);
        	      this.btnInTrial.setEnabled(true);
        	      this.btnApproved.setEnabled(false);
     		 }
    	    }
    	
    	 if ((getOprtState().equals(OprtState.EDIT)) && (this.editData.isIsVersion())) {
    	      if (this.editData.getBillState().equals(BillStateEnum.SAVE)) {
    	        this.actionAddNew.setEnabled(false);
    	        this.actionEdit.setEnabled(false);
    	        this.actionSave.setEnabled(true);
    	        this.actionRemove.setEnabled(false);
    	        this.actionSubmit.setEnabled(true);
    	        this.actionAudit.setEnabled(false);
    	        this.actionUnAudit.setEnabled(false);

    	        this.btnAddNew.setEnabled(false);
    	        this.btnEdit.setEnabled(false);
    	        this.btnSave.setEnabled(true);
    	        this.btnRemove.setEnabled(false);
    	        this.btnSubmit.setEnabled(true);
    	        this.btnAudit.setEnabled(false);
    	        this.btnUnAudit.setEnabled(false);
    	        this.btnInTrial.setEnabled(false);
    	        this.btnApproved.setEnabled(false);
    	      }
    	      if (this.editData.getBillState().equals(BillStateEnum.SUBMIT)) {
    	        this.actionAddNew.setEnabled(false);
    	        this.actionEdit.setEnabled(false);
    	        this.actionSave.setEnabled(false);
    	        this.actionRemove.setEnabled(false);
    	        this.actionSubmit.setEnabled(false);
    	        this.actionAudit.setEnabled(true);
    	        this.actionUnAudit.setEnabled(false);

    	        this.btnAddNew.setEnabled(false);
    	        this.btnEdit.setEnabled(false);
    	        this.btnSave.setEnabled(false);
    	        this.btnRemove.setEnabled(false);
    	        this.btnSubmit.setEnabled(false);
    	        this.btnAudit.setEnabled(true);
    	        this.btnUnAudit.setEnabled(false);
    	        this.btnInTrial.setEnabled(false);
    	        this.btnApproved.setEnabled(false);
    	      }

    	      if (this.editData.getBillState().equals(BillStateEnum.AUDIT))
    	      {
    	        if (this.editData.getState().equals(TrialStatusEnum.Review)) {
    	          this.actionAddNew.setEnabled(false);
    	          this.actionEdit.setEnabled(false);
    	          this.actionSave.setEnabled(false);
    	          this.actionRemove.setEnabled(false);
    	          this.actionSubmit.setEnabled(false);
    	          this.actionAudit.setEnabled(false);
    	          this.actionUnAudit.setEnabled(true);

    	          this.btnAddNew.setEnabled(false);
    	          this.btnEdit.setEnabled(false);
    	          this.btnSave.setEnabled(false);
    	          this.btnRemove.setEnabled(false);
    	          this.btnSubmit.setEnabled(false);
    	          this.btnAudit.setEnabled(false);
    	          this.btnUnAudit.setEnabled(true);
    	          this.btnInTrial.setEnabled(true);
    	          this.btnApproved.setEnabled(false);
    	        }
    	        if (this.editData.getState().equals(TrialStatusEnum.InTrial)) {
    	          this.actionAddNew.setEnabled(false);
    	          this.actionEdit.setEnabled(false);
    	          this.actionSave.setEnabled(false);
    	          this.actionRemove.setEnabled(false);
    	          this.actionSubmit.setEnabled(false);
    	          this.actionAudit.setEnabled(false);
    	          this.actionUnAudit.setEnabled(false);

    	          this.btnAddNew.setEnabled(false);
    	          this.btnEdit.setEnabled(false);
    	          this.btnSave.setEnabled(false);
    	          this.btnRemove.setEnabled(false);
    	          this.btnSubmit.setEnabled(false);
    	          this.btnAudit.setEnabled(false);
    	          this.btnUnAudit.setEnabled(false);
    	          this.btnInTrial.setEnabled(false);
    	          this.btnApproved.setEnabled(true);
    	        }
    	        if (this.editData.getState().equals(TrialStatusEnum.Approved)) {
    	          this.actionAddNew.setEnabled(false);
    	          this.actionEdit.setEnabled(false);
    	          this.actionSave.setEnabled(false);
    	          this.actionRemove.setEnabled(false);
    	          this.actionSubmit.setEnabled(false);
    	          this.actionAudit.setEnabled(false);
    	          this.actionUnAudit.setEnabled(false);

    	          this.btnAddNew.setEnabled(false);
    	          this.btnEdit.setEnabled(false);
    	          this.btnSave.setEnabled(false);
    	          this.btnRemove.setEnabled(false);
    	          this.btnSubmit.setEnabled(false);
    	          this.btnAudit.setEnabled(false);
    	          this.btnUnAudit.setEnabled(false);
    	          this.btnInTrial.setEnabled(false);
    	          this.btnApproved.setEnabled(false);
    	        }
    	      }
    	    }
    	 kDPanel4.setEnabled(false);//工程部确认信息
    	 kDPanel3.setEnabled(false);//合约审算确认信息
    	 contapprovalAmount.setEnabled(false);//审定金额
    	
    	 if (getOprtState().equals("工程部确认信息")) {
    		 kDPanel4.setEnabled(true);
    	 }
    	 if (getOprtState().equals("合约审算确认信息")) {
    		 kDPanel3.setEnabled(true);
    	 }
    	 if (getOprtState().equals("审定金额")) {
    		 contapprovalAmount.setEnabled(true);
    	 }
    	 
    	 
    }
    public boolean checkBeforeWindowClosing() {
    	return super.checkBeforeWindowClosing();
    }

    public void onShow() throws Exception {
    	super.onShow();
    }
    
    public void loadFields()
    {
        super.loadFields();
//        controlStateEditUI(this, this.state, actionEdit, actionRemove, actionSave, actionSubmit, actionAudit, actionUnAudit);
        
    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void initUI() throws Exception{
    	 this.txtdecAmount.setRequired(true);
    	    this.txtapprovalAmount.setRequired(true);
    	    this.btnAudit.setEnabled(true);
    	    this.btnUnAudit.setEnabled(true);
    	    this.btnInTrial.setEnabled(true);
    	    this.btnApproved.setEnabled(true);
    	    this.contstate.setEnabled(false);
    	    this.contbillState.setEnabled(false);
    	    
//    	    prmtcontractNumber.setDisplayFormat("$name$");
//        	prmtcontractNumber.setEditFormat("$name$");
//        	prmtcontractNumber.setCommitFormat("$number$");
        	
        	this.txtdecAmount.setHorizontalAlignment(2);		
            this.txtdecAmount.setDataType(1);		
            this.txtdecAmount.setSupportedEmpty(true);		
            this.txtdecAmount.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
            this.txtdecAmount.setMaximumValue( new java.math.BigDecimal(1.0E18));		
            this.txtdecAmount.setPrecision(2);		
            this.txtdecAmount.setRequired(true);
            this.txtapprovalAmount.setHorizontalAlignment(2);		
            this.txtapprovalAmount.setDataType(1);		
            this.txtapprovalAmount.setSupportedEmpty(true);		
            this.txtapprovalAmount.setMinimumValue( new java.math.BigDecimal(-1.0E18));		
            this.txtapprovalAmount.setMaximumValue( new java.math.BigDecimal(1.0E18));		
            this.txtapprovalAmount.setPrecision(2);		
            this.txtapprovalAmount.setRequired(true);
            
            
            this.txtdecAmount.setValue(editData.getDecAmount());
            this.txtapprovalAmount.setValue(editData.getApprovalAmount());
//    	kdtE1.addRows(16);
    	
//    	KDTable table = new KDTable();
//    	String [] columnKeys  = new String[]{"a","b","c","d"};
//    	String [] head = new String[]{"a1","b1","c1","d1"};
        
        if(getOprtState().equals(OprtState.ADDNEW)){
        	String [][] E1 = new String[][] {{null,"竣工图", null, ""},{null,"竣工结算书", null, ""},{null,"开（竣）工报告", null, ""},{null,"技术核定单", null, ""}
        	,{null,"经济签证单：原件", null, ""},{null,"工作联系单", null, ""},{null,"材料核价单", null, ""},{null,"设计变更通知单", null, ""}
        	,{null,"供货签收凭证原件", null, ""},{null,"供货结算汇总表", null, ""},{null,"竣工结算计算书", null, ""}};
        	String [][] E2 = new String[][] {{null,"合同文件附件", null, ""},{null,"工作联系单：原件", null, ""},{null,"设计交底", null, ""},{null,"技术核定单", null, ""}};
        	
        	KDTableHelper.initTable(kdtE1, null , null, E1);
        	KDTableHelper.initTable(kdtE2, null , null, E2);
        }
	}
    
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
    	if(UIRuleUtil.isNull(this.txtdecAmount.getText())){//申报金额
			
			MsgBox.showWarning("申报金额不能为空！");SysUtil.abort();
		}
//    	if(UIRuleUtil.isNull(this.txtapprovalAmount.getText())){//审定金额
//			
//			MsgBox.showWarning("审定金额不能为空！");SysUtil.abort();
//		}
    	
    	
    	
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {//新增
    	super.actionAddNew_actionPerformed(e);
    	this.actionAddNew.setEnabled(true);//新增
		this.actionEdit.setEnabled(false);//修改
    	this.actionSave.setEnabled(true);//保存
    	this.actionRemove.setEnabled(false);//删除
    	this.actionSubmit.setEnabled(false);//提交
    	this.actionAudit.setEnabled(false);//审批
    	this.actionUnAudit.setEnabled(false);//反审批
    	this.btnInTrial.setEnabled(false);
        this.btnApproved.setEnabled(false);
        this.billState.setSelectedItem(BillStateEnum.TEMPORARILYSAVED);
        this.state.setSelectedItem(TrialStatusEnum.Review);
    	
    }
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {//保存
//    	this.billState.setSelectedItem(com.kingdee.eas.fdc.contract.settle.app.BillStateEnum.SAVE);
    	this.billState.setSelectedItem(BillStateEnum.SAVE);
        this.state.setSelectedItem(TrialStatusEnum.Review);
        String sql = " update CT_CON_SettleDeclarationBill set CFIsVersion=0 where CFContractNumberID ='" + this.editData.getContractNumber().getId().toString() + "'";
        new FDCSQLBuilder().appendSql(sql).execute();
        this.chkisVersion.setSelected(true);
    	super.actionSave_actionPerformed(e);
    	
    	
    	this.actionAddNew.setEnabled(false);//新增
		this.actionEdit.setEnabled(true);//修改
    	this.actionSave.setEnabled(false);//保存
    	this.actionRemove.setEnabled(true);//删除
    	this.actionSubmit.setEnabled(true);//提交
    	this.actionAudit.setEnabled(false);//审批
    	this.actionUnAudit.setEnabled(false);//反审批
    	this.btnInTrial.setEnabled(false);
        this.btnApproved.setEnabled(false);
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {//修改
    	if (!this.editData.isIsVersion()) {
    	      MsgBox.showWarning("不是最新版本，不能修改！");
    	      SysUtil.abort();
    	    }
    	    if (this.editData.isIsVersion())
    	    {
    	      if (this.billState.getSelectedItem().equals(BillStateEnum.SUBMIT)) {
    	        MsgBox.showWarning("单据状态为已提交，不能修改！"); SysUtil.abort();
    	      }
    	      if (this.billState.getSelectedItem().equals(BillStateEnum.AUDIT)) {
    	        MsgBox.showWarning("单据状态为已审批，不能修改！"); SysUtil.abort();
    	      }

    	      super.actionEdit_actionPerformed(e);
    	    }
	this.actionAddNew.setEnabled(false);//新增
	this.actionEdit.setEnabled(false);//修改
	this.actionSave.setEnabled(true);//保存
	this.actionRemove.setEnabled(true);//删除
	this.actionSubmit.setEnabled(true);//提交
	this.actionAudit.setEnabled(false);//审批
	this.actionUnAudit.setEnabled(false);//反审批
	this.btnInTrial.setEnabled(false);
    this.btnApproved.setEnabled(false);
    this.billState.setSelectedItem(BillStateEnum.TEMPORARILYSAVED);
    this.state.setSelectedItem(TrialStatusEnum.Review);
	
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {//提交
    	
        this.billState.setSelectedItem(BillStateEnum.SUBMIT);
        this.state.setSelectedItem(TrialStatusEnum.Review);
        super.actionSave_actionPerformed(e);
        this.actionAddNew.setEnabled(false);
        this.actionEdit.setEnabled(false);
        this.actionSave.setEnabled(false);
        this.actionRemove.setEnabled(false);
        this.actionSubmit.setEnabled(false);
        this.actionAudit.setEnabled(true);
        this.actionUnAudit.setEnabled(false);
        this.btnInTrial.setEnabled(false);
        this.btnApproved.setEnabled(false);
//        this.state.setSelectedItem(TrialStatusEnum.Review);
        
    }
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {//审核
//    	if(WorkflowXRHelper.checkInProInst(editData.getId().toString())){
//			MsgBox.showWarning("此单据记录有流程正在运行!");
//			SysUtil.abort();
//		}
    	super.actionAudit_actionPerformed(e);
    	SettleDeclarationBillFactory.getRemoteInstance().Audit(editData);
    	refEditUI();
    	this.actionAddNew.setEnabled(false);//新增
    	this.actionEdit.setEnabled(false);//修改
    	this.actionSave.setEnabled(false);//保存
    	this.actionRemove.setEnabled(false);//删除
    	this.actionSubmit.setEnabled(false);//提交
    	this.actionAudit.setEnabled(false);//审批
    	btnAudit.setEnabled(false);
    	this.actionUnAudit.setEnabled(true);//反审批
    	this.btnInTrial.setEnabled(true);
    	this.btnApproved.setEnabled(false);
    }
    
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {//反审批
    	
//    	if(WorkflowXRHelper.checkInProInst(editData.getId().toString())){
//			MsgBox.showWarning("此单据记录有流程正在运行!");
//			SysUtil.abort();
//		}
    	super.actionUnAudit_actionPerformed(e);
    	SettleDeclarationBillFactory.getRemoteInstance().UnAudit(editData);
    	refEditUI();
    	this.actionAddNew.setEnabled(false);//新增
    	this.actionEdit.setEnabled(true);//修改
    	this.actionSave.setEnabled(true);//保存
    	this.actionRemove.setEnabled(true);//删除
    	this.actionSubmit.setEnabled(false);//提交
    	this.actionAudit.setEnabled(false);//审批
    	btnAudit.setEnabled(false);
    	this.actionUnAudit.setEnabled(false);//反审批
    	btnUnAudit.setEnabled(false);
    	this.btnInTrial.setEnabled(false);
    	this.btnApproved.setEnabled(false);
    }
    
    public void actionInTrial_actionPerformed(ActionEvent e) throws Exception {//送审
    	SettleDeclarationBillFactory.getRemoteInstance().InTrial(editData);
    	super.actionInTrial_actionPerformed(e);
    	 this.actionAddNew.setEnabled(false);
    	    this.actionEdit.setEnabled(false);
    	    this.actionSave.setEnabled(false);
    	    this.actionRemove.setEnabled(false);
    	    this.actionSubmit.setEnabled(false);
    	    this.actionAudit.setEnabled(false);
    	    this.actionUnAudit.setEnabled(false);
    	    this.btnInTrial.setEnabled(false);
    	    this.btnApproved.setEnabled(true);
    	refEditUI();
    }
    
    public void actionApproved_actionPerformed(ActionEvent e) throws Exception {//审定
    	SettleDeclarationBillFactory.getRemoteInstance().Approved(editData);
    	super.actionApproved_actionPerformed(e);
    	this.btnInTrial.setEnabled(false);
        this.btnApproved.setEnabled(false);
    	refEditUI();
    }
    
    public static void controlStateEditUI(CoreUIObject ui, KDComboBox billState, IItemAction actionEdit, 
			IItemAction actionRemove, IItemAction actionSave,IItemAction actionSubmit, IItemAction actionAudit, IItemAction actionUnAudit){
//		if(billState.getSelectedItem()!=null){
//			actionEdit.setEnabled(false);
//			actionSave.setEnabled(false);
//			actionSubmit.setEnabled(false);
//			actionUnAudit.setEnabled(false);
//			actionAudit.setEnabled(false);
//			actionRemove.setEnabled(false);
//			
//			String BillState = ui.getOprtState();
//			
//			if(billState.getSelectedItem().equals(com.kingdee.eas.fdc.contract.settle.app.BillStateEnum.ADDNEW)){
//				actionSave.setEnabled(true);
//				actionSubmit.setEnabled(true);
//			}
//			if(billState.getSelectedItem().equals(com.kingdee.eas.fdc.contract.settle.app.BillStateEnum.TEMPORARILYSAVED)){
//				if(BillState.equals("ADDNEW")||BillState.equals("EDIT")){
//					actionSubmit.setEnabled(true);
//					actionSave.setEnabled(true);
//				}else{
//					actionEdit.setEnabled(true);
//				}
//			}
//			if(billState.getSelectedItem().equals(com.kingdee.eas.fdc.contract.settle.app.BillStateEnum.SUBMIT)){
//				if(BillState.equals("ADDNEW")||BillState.equals("EDIT")){
//					actionSubmit.setEnabled(true);
//					actionSave.setEnabled(true);
//				}else{
//					actionEdit.setEnabled(true);
//					actionAudit.setEnabled(true);
//				}
//			}
//			if(billState.getSelectedItem().equals(com.kingdee.eas.fdc.contract.settle.app.BillStateEnum.AUDIT)){
//				actionUnAudit.setEnabled(true);
//			}
//		}
    }
    
    /**
     * 审批、反审批后刷新编辑界面[这样点击保存不会把之前数据保存进数据库]
     * @throws Exception
     */
	private void refEditUI() throws Exception{
    	ObjectUuidPK pk = new ObjectUuidPK(editData.getId());
        setDataObject(getValue(pk));//重新给editDate赋值
        loadFields();  //调用loadFields刷新界面结果
    	setSaved(true);
    	
//    	controlStateEditUI(this, this.state, actionEdit, actionRemove, actionSave, actionSubmit, actionAudit, actionUnAudit);
    }
    
    

    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillFactory.getRemoteInstance();
    }

    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return null;
    }

    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillInfo objectValue = new com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        
        ContractBillInfo Info = (ContractBillInfo) getUIContext().get("orderInfo");//两个界面传值
        objectValue.setContractNumber(Info);
        
        objectValue.setBillState(com.kingdee.eas.fdc.contract.settle.app.BillStateEnum.ADDNEW);
        objectValue.setState(com.kingdee.eas.fdc.contract.settle.app.TrialStatusEnum.Review);
//        objectValue.setIsVersion(true);
        
        try
        {
        	AdminOrgUnitInfo currentAdminUnit = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo(new ObjectUuidPK(Info.getId().toString()));
        }
        catch (EASBizException e)
        {
          AdminOrgUnitInfo currentAdminUnit;
          e.printStackTrace();
        } catch (BOSException e) {
          e.printStackTrace();
        }
        String oql = "select id where contractNumber.id='" + Info.getId().toString() + "'";
        try {
          objectValue.setVersion(SettleDeclarationBillFactory.getRemoteInstance().getSettleDeclarationBillCollection(oql).size() + 1);
        } catch (BOSException e) {
          e.printStackTrace();
        }
        
//        objectValue.setStrategicPact(Info);//带出
//        objectValue.setSupplier(Info.getPartB());
//        objectValue.setOrganization(Info.getOrgUnit());
//        objectValue.setStatus(XRBillStatusEnum.TEMPORARILYSAVED);
        
////        FullOrgUnitInfo Info1 = (FullOrgUnitInfo)getUIContext().get("ID");//从ListUI传过来
//        try {
//        	AdminOrgUnitInfo currentAdminUnit = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo(new ObjectUuidPK(Info.getId().toString()));
//        	String oql = "select id where group.id='"+Info.getId().toString()+"'";//查出所有相同事业部的单据
////			objectValue.setVersion(SettleDeclarationBillFactory.getRemoteInstance().getSettleDeclarationBillCollection(oql).size()+1);//单据版本=单据总数+1
//        } catch (EASBizException e) {
//			e.printStackTrace();
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
        return objectValue;
    }

}