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
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.settle.SettleDeclarationBillFactory;
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
        
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	initUI();
    	if(getOprtState().equals(OprtState.ADDNEW)){
    		this.billState.setSelectedItem(com.kingdee.eas.fdc.contract.settle.app.BillStateEnum.ADDNEW);
    		this.state.setSelectedItem(com.kingdee.eas.fdc.contract.settle.app.TrialStatusEnum.Review);
    		
    		prmtcontractNumber.setDisplayFormat("$name$");
    		prmtcontractNumber.setEditFormat("$name$");
    		prmtcontractNumber.setCommitFormat("$number$");
    	}
    }
    public void onShow() throws Exception {
    	super.onShow();
    }
    
    public void loadFields()
    {
        super.loadFields();
        controlStateEditUI(this, this.state, actionEdit, actionRemove, actionSave, actionSubmit, actionAudit, actionUnAudit);
    }

    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void initUI() throws Exception{
    	this.txtdecAmount.setRequired(true);
    	this.txtapprovalAmount.setRequired(true);
    	btnAudit.setEnabled(true);
    	btnUnAudit.setEnabled(true);
    	btnInTrial.setEnabled(true);
    	btnApproved.setEnabled(true);
    	contstate.setEnabled(false);//����״̬
    	contbillState.setEnabled(false);//����״̬
//    	kdtE1.addRows(16);
    	
//    	KDTable table = new KDTable();
//    	String [] columnKeys  = new String[]{"a","b","c","d"};
//    	String [] head = new String[]{"a1","b1","c1","d1"};
    	String [][] E1 = new String[][] {{null,"����ͼ", null, ""},{null,"����������", null, ""},{null,"��������������", null, ""},{null,"�����˶���", null, ""}
    										,{null,"����ǩ֤����ԭ��", null, ""},{null,"������ϵ��", null, ""},{null,"���Ϻ˼۵�", null, ""},{null,"��Ʊ��֪ͨ��", null, ""}
    										,{null,"����ǩ��ƾ֤ԭ��", null, ""},{null,"����������ܱ�", null, ""},{null,"�������������", null, ""}};
    	String [][] E2 = new String[][] {{null,"��ͬ�ļ�����", null, ""},{null,"������ϵ����ԭ��", null, ""},{null,"��ƽ���", null, ""},{null,"�����˶���", null, ""}};

    	KDTableHelper.initTable(kdtE1, null , null, E1);
    	KDTableHelper.initTable(kdtE2, null , null, E2);
	}
    
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
    	if(UIRuleUtil.isNull(this.txtdecAmount.getText())){//�걨���
			
			MsgBox.showWarning("�걨����Ϊ�գ�");SysUtil.abort();
		}
    	if(UIRuleUtil.isNull(this.txtapprovalAmount.getText())){//�󶨽��
			
			MsgBox.showWarning("�󶨽���Ϊ�գ�");SysUtil.abort();
		}
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {//����
    	super.actionAddNew_actionPerformed(e);
    	this.actionAddNew.setEnabled(false);//����
		this.actionEdit.setEnabled(false);//�޸�
    	this.actionSave.setEnabled(true);//����
    	this.actionRemove.setEnabled(false);//ɾ��
    	this.actionSubmit.setEnabled(false);//�ύ
    	this.actionAudit.setEnabled(false);//����
    	this.actionUnAudit.setEnabled(false);//������
    	this.billState.setSelectedItem(com.kingdee.eas.fdc.contract.settle.app.BillStateEnum.TEMPORARILYSAVED);
    	
    }
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {//����
    	super.actionSave_actionPerformed(e);
    	this.actionAddNew.setEnabled(false);//����
		this.actionEdit.setEnabled(true);//�޸�
    	this.actionSave.setEnabled(false);//����
    	this.actionRemove.setEnabled(true);//ɾ��
    	this.actionSubmit.setEnabled(true);//�ύ
    	this.actionAudit.setEnabled(false);//����
    	this.actionUnAudit.setEnabled(false);//������
    	this.billState.setSelectedItem(com.kingdee.eas.fdc.contract.settle.app.BillStateEnum.SAVE);
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {//�޸�
    	if(this.billState.getSelectedItem().equals(com.kingdee.eas.fdc.contract.settle.app.BillStateEnum.SUBMIT)){
			MsgBox.showWarning("����״̬Ϊ���ύ�������޸ģ�");SysUtil.abort();
		}
		if(this.billState.getSelectedItem().equals(com.kingdee.eas.fdc.contract.settle.app.BillStateEnum.AUDIT)){
			MsgBox.showWarning("����״̬Ϊ�������������޸ģ�");SysUtil.abort();
		}
//		if(WorkflowXRHelper.checkInProInst(editData.getId().toString())){
//			MsgBox.showWarning("�˵��ݼ�¼��������������!");
//			SysUtil.abort();
//		}
	super.actionEdit_actionPerformed(e);
	this.actionAddNew.setEnabled(false);//����
	this.actionEdit.setEnabled(false);//�޸�
	this.actionSave.setEnabled(true);//����
	this.actionRemove.setEnabled(true);//ɾ��
	this.actionSubmit.setEnabled(true);//�ύ
	this.actionAudit.setEnabled(false);//����
	this.actionUnAudit.setEnabled(false);//������
	this.billState.setSelectedItem(com.kingdee.eas.fdc.contract.settle.app.BillStateEnum.TEMPORARILYSAVED);
	
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {//�ύ
    	this.actionAudit.setEnabled(true);//����
    	this.billState.setSelectedItem(com.kingdee.eas.fdc.contract.settle.app.BillStateEnum.SUBMIT);
    	super.actionSubmit_actionPerformed(e);
    	this.actionAddNew.setEnabled(false);//����
    	this.actionEdit.setEnabled(false);//�޸�
    	this.actionSave.setEnabled(false);//����
    	this.actionRemove.setEnabled(false);//ɾ��
    	this.actionSubmit.setEnabled(false);//�ύ
    	this.actionAudit.setEnabled(true);//����
    	this.actionUnAudit.setEnabled(false);//������
    	this.setOprtState("VIEW");
    }
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {//����
    	super.actionAudit_actionPerformed(e);
//    	if(WorkflowXRHelper.checkInProInst(editData.getId().toString())){
//			MsgBox.showWarning("�˵��ݼ�¼��������������!");
//			SysUtil.abort();
//		}
    	SettleDeclarationBillFactory.getRemoteInstance().Audit(editData);
    	refEditUI();
    	this.actionAddNew.setEnabled(false);//����
    	this.actionEdit.setEnabled(false);//�޸�
    	this.actionSave.setEnabled(false);//����
    	this.actionRemove.setEnabled(false);//ɾ��
    	this.actionSubmit.setEnabled(false);//�ύ
    	this.actionAudit.setEnabled(false);//����
    	btnAudit.setEnabled(false);
    	this.actionUnAudit.setEnabled(true);//������
    }
    
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {//������
    	super.actionUnAudit_actionPerformed(e);
//    	if(WorkflowXRHelper.checkInProInst(editData.getId().toString())){
//			MsgBox.showWarning("�˵��ݼ�¼��������������!");
//			SysUtil.abort();
//		}
    	SettleDeclarationBillFactory.getRemoteInstance().UnAudit(editData);
    	refEditUI();
    	this.actionAddNew.setEnabled(false);//����
    	this.actionEdit.setEnabled(true);//�޸�
    	this.actionSave.setEnabled(true);//����
    	this.actionRemove.setEnabled(true);//ɾ��
    	this.actionSubmit.setEnabled(false);//�ύ
    	this.actionAudit.setEnabled(false);//����
    	btnAudit.setEnabled(false);
    	this.actionUnAudit.setEnabled(false);//������
    	btnUnAudit.setEnabled(false);
    }
    
    public void actionInTrial_actionPerformed(ActionEvent e) throws Exception {//����
    	super.actionInTrial_actionPerformed(e);
    	SettleDeclarationBillFactory.getRemoteInstance().InTrial(editData);
    	refEditUI();
    }
    
    public void actionApproved_actionPerformed(ActionEvent e) throws Exception {//��
    	super.actionApproved_actionPerformed(e);
    	SettleDeclarationBillFactory.getRemoteInstance().Approved(editData);
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
     * ��������������ˢ�±༭����[����������治���֮ǰ���ݱ�������ݿ�]
     * @throws Exception
     */
	private void refEditUI() throws Exception{
    	ObjectUuidPK pk = new ObjectUuidPK(editData.getId());
        setDataObject(getValue(pk));//���¸�editDate��ֵ
        loadFields();  //����loadFieldsˢ�½�����
    	setSaved(true);
    	
    	controlStateEditUI(this, this.state, actionEdit, actionRemove, actionSave, actionSubmit, actionAudit, actionUnAudit);
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
        
        ContractBillInfo Info = (ContractBillInfo) getUIContext().get("orderInfo");//�������洫ֵ
        objectValue.setContractNumber(Info);
//        objectValue.setStrategicPact(Info);//����
//        objectValue.setSupplier(Info.getPartB());
//        objectValue.setOrganization(Info.getOrgUnit());
//        objectValue.setStatus(XRBillStatusEnum.TEMPORARILYSAVED);
        
////        FullOrgUnitInfo Info1 = (FullOrgUnitInfo)getUIContext().get("ID");//��ListUI������
//        try {
//        	AdminOrgUnitInfo currentAdminUnit = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo(new ObjectUuidPK(Info.getId().toString()));
//        	String oql = "select id where group.id='"+Info.getId().toString()+"'";//���������ͬ��ҵ���ĵ���
////			objectValue.setVersion(SettleDeclarationBillFactory.getRemoteInstance().getSettleDeclarationBillCollection(oql).size()+1);//���ݰ汾=��������+1
//        } catch (EASBizException e) {
//			e.printStackTrace();
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
        return objectValue;
    }

}