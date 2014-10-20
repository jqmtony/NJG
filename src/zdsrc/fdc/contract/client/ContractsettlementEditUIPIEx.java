package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.bpmdemo.ContractsettlementFactory;
import com.kingdee.eas.bpmdemo.JBrowserHelper.JFrameBrowser;
import com.kingdee.eas.bpmdemo.webservers.getInfoFacadeFactory;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPLocator;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoap;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.util.client.MsgBox;

public class ContractsettlementEditUIPIEx extends ContractSettlementBillEditUI{
     
	private ContractTypeInfo typeInfo;
	private boolean isShiGong = false;
	private boolean isFenBao = false;
	private boolean isZongBao = false;
	
	
	public ContractsettlementEditUIPIEx() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		InitButton();
	   	if(editData.getState()!=null)
	   	{
		   	if("����".equals(editData.getState().getAlias()))   //����
		   	{
		   		this.btnSubmit.setEnabled(true);             //�ύ
		   		this.btnAttachment.setEnabled(false);        //����
		    	this.btnAuditResult.setEnabled(false);       //��������鿴
		   		
		   	}
		   	else if("���ύ".equals(editData.getState().getAlias()))
		   	{
		   		this.btnSubmit.setEnabled(true);             //�ύ
		   		this.btnAttachment.setEnabled(false);        //����
		    	this.btnAuditResult.setEnabled(false);       //��������鿴
		   		
		   	}
		   	else if("������".equals(editData.getState().getAlias()))
		   	{
		   		this.btnSubmit.setEnabled(false);             //�ύ
		   		this.btnAttachment.setEnabled(true);        //����
		    	this.btnAuditResult.setEnabled(true);       //��������鿴
		   		
		   	}
		   	else if("������".equals(editData.getState().getAlias()))
		   	{
		   		this.btnSubmit.setEnabled(false);             //�ύ
		   		this.btnAttachment.setEnabled(false);        //����
		    	this.btnAuditResult.setEnabled(false);       //��������鿴
		   	}
		   	}
		   	else
		   	{
		   		this.btnSubmit.setEnabled(true);             //�ύ
		   		this.btnAttachment.setEnabled(false);        //����
		    	this.btnAuditResult.setEnabled(false);       //��������鿴
		   	}
    	this.chkMenuItemSubmitAndAddNew.setSelected(false);
	   	this.chkMenuItemSubmitAndAddNew.setEnabled(false);
	   	this.btnCopy.setVisible(false);
	   	this.btnPrint.setVisible(false);
	   	this.btnPrintPreview.setVisible(false);
	   	this.btnPre.setVisible(false);
	   	this.btnNext.setVisible(false);
	   	this.btnLast.setVisible(false);
	   	this.btnFirst.setVisible(false);
	}
	
	
	 private void InitButton()
	    {
	    	this.actionCreateTo.setVisible(false);
	    	this.actionCreateFrom.setVisible(false);
	    	this.actionMultiapprove.setVisible(false);
	    	this.actionNextPerson.setVisible(false);
	    	
	    	this.btnSubmit.setText("�ύBPM����");
	    	this.btnSubmit.setToolTipText("�ύBPM����");
	    	btnWorkFlowG.setVisible(false);
	    	this.btnAttachment.setEnabled(false);
	    	this.btnAttachment.setText("����BPM����");
	    	this.btnAttachment.setToolTipText("����BPM����");
	    }
	 
	 
	 /**
	     * ��������
	     */
	    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
	    	BPMServiceForERPSoap  login = new BPMServiceForERPLocator().getBPMServiceForERPSoap();
	    	editData = ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillInfo(new ObjectUuidPK(editData.getId()));
	    	login.withdraw("HTJS01", editData.getId().toString(), editData.getSourceFunction());
	    }
	    
	    
	    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
	    	super.actionSubmit_actionPerformed(e);
	    	
	    	//String[] xml = getInfoFacadeFactory.getRemoteInstance().GetbillInfo("",editData.getId().toString());
	    	//String [] str1= getInfoFacadeFactory.getRemoteInstance().ApproveClose("", "dYkAAAAAhPINbdH0", 1, "1", "",null);
	    	//MsgBox.showInfo(str1[0]+str1[1]+str1[2]);
	    	String url = "http://10.130.12.20/BPMStart.aspx?bsid=ERP&boid="+editData.getId().toString()+"&btid=HTJS01&userid="+SysContext.getSysContext().getUserName()+"";
	    	creatFrame(url);
	    	editData.setState(FDCBillStateEnum.SAVED);
	    	
	    }
	    
	    /**
	     * �ύBMP
	     */
	    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
	    	super.actionSave_actionPerformed(e);
	    }
	    
	    /**
	     * ����ͼ
	     */
	    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
	    	
	    	editData = ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillInfo(new ObjectUuidPK(editData.getId()));
	    	String url = editData.getDescription();
	    	creatFrame(url);
	    }
	    
	    /**
	     * ��������鿴
	     */
	    public void actionAuditResult_actionPerformed(ActionEvent e)throws Exception {
	    	String [] str1 = new String[3]; 
	    	editData =ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillInfo(new ObjectUuidPK(editData.getId())); 
	    	String url = editData.getDescription();
	    	creatFrame(url);
	    }
	    
	    
	    private void creatFrame(String url)
	    {
	    	//��ȡMD5����
//	    	String md5 = MD5Helper.getMd5("blue", "20140813", "K2");
	    	
	    	JFrameBrowser jf = new JFrameBrowser();
	    	jf.setJBrowserSize(720, 1200);
	    	jf.setJBrwserOpenUrl(url);
	    	
	    	jf.setTitle("BPM");
	    	
	    	jf.OpenJBrowser(this);
	    }
    
}