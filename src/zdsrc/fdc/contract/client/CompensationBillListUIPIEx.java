package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.bpm.common.StringUtilBPM;
import com.kingdee.eas.bpmdemo.JBrowserHelper.JFrameBrowser;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPLocator;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoap;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class CompensationBillListUIPIEx extends CompensationBillListUI{

	public CompensationBillListUIPIEx() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	private ContractTypeInfo typeInfo;
	private boolean isShiGong = false;
	private boolean isFenBao = false;
	private boolean isZongBao = false;
	public void onLoad() throws Exception {
		super.onLoad();
		InitButton();
	}

    private void InitButton()
    {
    	this.btnAttachment.setText("����BPM����");
    	this.btnAttachment.setToolTipText("����BPM����");
	   	this.btnPrint.setVisible(false);
	   	this.btnPrintPreview.setVisible(false);
    	this.actionCreateTo.setVisible(false);
    	this.actionMultiapprove.setVisible(false);
    	this.actionNextPerson.setVisible(false);
    	this.btnWorkFlowG.setVisible(false);
    	

    }
    
	protected boolean isContinueAddNew() {
		return false;
	}
	
	protected void afterSubmitAddNew() {
	}
	
	
	/*
	 * ����
	 * */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}
	/*
	 * ɾ��
	 * */
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(this.getSelectedKeyValue()!=null){
		CompensationBillInfo info = CompensationBillFactory.getRemoteInstance().getCompensationBillInfo(new ObjectUuidPK(this.getSelectedKeyValue()));
		if(info.getId()!=null){
			//ContractBillInfo info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(editData.getId()));
			if("������".equals(info.getState().getAlias())||"������".equals(info.getState().getAlias()))
			{
				MsgBox.showInfo("�õ���״̬Ϊ:"+info.getState().getAlias()+",����ɾ����");
				SysUtil.abort();
			}
		}
		}
		super.actionRemove_actionPerformed(e);
	}
	
	/*
	 * �޸�
	 * */
	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		if(this.getSelectedKeyValue()!=null)
		{
		CompensationBillInfo info = CompensationBillFactory.getRemoteInstance().getCompensationBillInfo(new ObjectUuidPK(this.getSelectedKeyValue()));
		if(info.getId()!=null){
			//ContractBillInfo info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(editData.getId()));
			if("������".equals(info.getState().getAlias())||"������".equals(info.getState().getAlias()))
			{
				MsgBox.showInfo("�õ���״̬Ϊ:"+info.getState().getAlias()+",�����޸ģ�");
				SysUtil.abort();
			}
		}
		}
		super.actionEdit_actionPerformed(arg0);
		
    	this.btnRemove.setVisible(false);
    	this.btnRemove.setEnabled(false);
	}
	

	
	/*
	 * ��������
	 * */
	public void actionAttachment_actionPerformed(ActionEvent e)throws Exception {
		if(this.getSelectedKeyValue()!=null)
		{
		CompensationBillInfo info = CompensationBillFactory.getRemoteInstance().getCompensationBillInfo(new ObjectUuidPK(this.getSelectedKeyValue()));
		String result = "";
		if(info.getId()!=null){
			//ContractBillInfo info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(editData.getId()));
			if("������".equals(info.getState().getAlias()))
			{
				BPMServiceForERPSoap  login = new BPMServiceForERPLocator().getBPMServiceForERPSoap();
				result = login.withdraw("WY01", info.getId().toString(), info.getSourceFunction());
			}else{
				MsgBox.showInfo("�õ��ݲ������������У����賷�����̣�");
			}
		}
		}
	}
	
	/*
	 * ����
	 * */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		
	}


	/*
	 * �鿴�������
	 * */
	public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception {
		if(this.getSelectedKeyValue()!=null)
		{
		CompensationBillInfo info = CompensationBillFactory.getRemoteInstance().getCompensationBillInfo(new ObjectUuidPK(this.getSelectedKeyValue()));
		if(info.getId()!=null){
			// String url = StringUtilBPM.getBPMServerURL()+"?bsid=ERP&boid="+info.getId().toString()+"&btid=WY01&userid="+SysContext.getSysContext().getUserName()+"";
			//ContractBillInfo info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(editData.getId()));
	    	String url = info.getDescription();
			if("������".equals(info.getState().getAlias())||"������".equals(info.getState().getAlias()))
			{
				creatFrame(url);
			}
			else if("����".equals(info.getState().getAlias()))
			{
				url = StringUtilBPM.getBPMServerURL()+"?bsid=ERP&boid="+info.getId().toString()+"&btid=WY01&userid="+SysContext.getSysContext().getUserName()+"";
				creatFrame(url);
			}
			else{
				MsgBox.showInfo("�õ���δ�����������̣������ѳ������̣�û�ж�Ӧ���̣�");
			}
		}
		}
	}
	
	private void creatFrame(String url)
    {
    	//��ȡMD5����
    	JFrameBrowser jf = new JFrameBrowser();
    	jf.setJBrowserSize(720, 1200);
    	jf.setJBrwserOpenUrl(url);
    	jf.setTitle("BPM");
    	jf.OpenJBrowser(this);
    }

}
