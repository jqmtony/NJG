package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.bpm.common.StringUtilBPM;
import com.kingdee.eas.bpmdemo.JBrowserHelper.JFrameBrowser;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPLocator;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoap;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;

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
		   	else
		   	{
		   		this.btnSubmit.setEnabled(true);             //�ύ
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
	protected boolean isContinueAddNew() {
		return false;
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
	    	super.actionSave_actionPerformed(e);
	    	super.actionSubmit_actionPerformed(e);
	    	
			   
//			    String [] str1 = new String[3];
//			   	EASLoginProxy login = new EASLoginProxyServiceLocator().getEASLogin(new URL("http://127.0.0.1:56898/ormrpc/services/EASLogin"));
//			   	WSContext  ws = login.login("kd-user", "kduser", "eas", "kd_002", "l2", 1);
//			    if(ws.getSessionId()!=null){
//			    	WSgetInfoFacadeSrvProxy pay = new WSgetInfoFacadeSrvProxyServiceLocator().getWSgetInfoFacade(new URL("http://127.0.0.1:56898/ormrpc/services/WSgetInfoFacade"));
//			    	str1 = pay.getbillInfo("", editData.getId().toString());
//			    	MsgBox.showInfo(str1[0] + str1[1] + str1[2]);
//			    }			    
	    	String url = StringUtilBPM.getBPMServerURL()+"?bsid=ERP&boid="+editData.getId().toString()+"&btid=HTJS01&userid="+SysContext.getSysContext().getUserName()+"";
	    	creatFrame(url);
	    	//editData.setState(FDCBillStateEnum.SAVED);
	    	
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
