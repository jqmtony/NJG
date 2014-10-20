package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.net.URL;

import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.bpmdemo.ChangeVisaAppFactory;
import com.kingdee.eas.bpmdemo.ChangeVisaAppInfo;
import com.kingdee.eas.bpmdemo.JBrowserHelper.JFrameBrowser;
import com.kingdee.eas.bpmdemo.client.ChangeVisaAppEditUI;
import com.kingdee.eas.bpmdemo.webservers.getInfoFacadeFactory;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPLocator;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoap;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.EASLoginProxyServiceLocator;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.EASLoginProxy;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.WSContext;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.WSgetInfoFacadeSrvProxyServiceLocator;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.WSgetInfoFacadeSrvProxy;
import com.kingdee.eas.common.client.SysContext;

public class ChangeAuditEditUIPIEx extends ChangeAuditEditUI{
    
	
	
	private ContractTypeInfo typeInfo;
	private boolean isShiGong = false;
	private boolean isFenBao = false;
	private boolean isZongBao = false;
	public ChangeAuditEditUIPIEx() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		InitButton();
		
	  	if(editData.getState()!=null)
	   	{
		   	if("保存".equals(editData.getState().getAlias()))   //保存
		   	{
		   		this.btnSubmit.setEnabled(true);             //提交
		   		this.btnAttachment.setEnabled(false);        //撤销
		    	this.btnAuditResult.setEnabled(false);       //审批结果查看
		   		
		   	}
		   	else if("已提交".equals(editData.getState().getAlias()))
		   	{
		   		this.btnSubmit.setEnabled(true);             //提交
		   		this.btnAttachment.setEnabled(false);        //撤销
		    	this.btnAuditResult.setEnabled(false);       //审批结果查看
		   		
		   	}
		   	else if("审批中".equals(editData.getState().getAlias()))
		   	{
		   		this.btnSubmit.setEnabled(false);             //提交
		   		this.btnAttachment.setEnabled(true);        //撤销
		    	this.btnAuditResult.setEnabled(true);       //审批结果查看
		   		
		   	}
		   	else if("已审批".equals(editData.getState().getAlias()))
		   	{
		   		this.btnSubmit.setEnabled(false);             //提交
		   		this.btnAttachment.setEnabled(false);        //撤销
		    	this.btnAuditResult.setEnabled(false);       //审批结果查看
		   	}
		   	}
		   	else
		   	{
		   		this.btnSubmit.setEnabled(true);             //提交
		   		this.btnAttachment.setEnabled(false);        //撤销
		    	this.btnAuditResult.setEnabled(false);       //审批结果查看
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
    	
    	this.btnSubmit.setText("提交BPM流程");
    	this.btnSubmit.setToolTipText("提交BPM流程");
    	btnWorkFlowG.setVisible(false);
    	this.btnAttachment.setText("撤销BPM流程");
    	this.btnAttachment.setToolTipText("撤销BPM流程");
    }
	
    /*
     * 新增
     * */ 
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionAddNew_actionPerformed(e);
    	this.btnAttachment.setEnabled(false);
    	this.btnAuditResult.setEnabled(false); 
	}
	
	/*
	 * 修改
	 * */
	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		// TODO Auto-generated method stub
		super.actionEdit_actionPerformed(arg0);
    	this.btnAttachment.setEnabled(false);
    	this.btnAuditResult.setEnabled(false); 
	}
	
	
	/**
     * 撤销流程
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
    	BPMServiceForERPSoap  login = new BPMServiceForERPLocator().getBPMServiceForERPSoap();
    	editData = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(new ObjectUuidPK(editData.getId()));
    	login.withdraw("BGQZ01", editData.getId().toString(), editData.getSourceFunction());
    }
    
    /**
     * 提交BMP
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSubmit_actionPerformed(e);
    	
    	//String[] xml = getInfoFacadeFactory.getRemoteInstance().GetbillInfo("",editData.getId().toString()); 
    	//String[] xml = getInfoFacadeFactory.getRemoteInstance().GetbillInfo("",editData.getId().toString());
    	//String [] str1= getInfoFacadeFactory.getRemoteInstance().ApproveClose("", editData.getId().toString(), 1, "1", "",null);
    	//MsgBox.showInfo(str1[0]+str1[1]+str1[2]);
//    	
//    	String [] str1 = new String[3];
//	   	EASLoginProxy login = new EASLoginProxyServiceLocator().getEASLogin(new URL("http://127.0.0.1:56898/ormrpc/services/EASLogin"));
//	   	WSContext  ws = login.login("kd-user", "kduser", "eas", "kd_002", "l2", 1);
//	    if(ws.getSessionId()!=null){
//	    	WSgetInfoFacadeSrvProxy pay = new WSgetInfoFacadeSrvProxyServiceLocator().getWSgetInfoFacade(new URL("http://127.0.0.1:56898/ormrpc/services/WSgetInfoFacade"));
//	    	str1 = pay.getbillInfo("", editData.getId().toString());
//	    	MsgBox.showInfo(str1[0] + str1[1] + str1[2]);
//	    	String url = "http://10.130.12.20/BPMStart.aspx?bsid=ERP&boid="+editData.getId().toString()+"&btid=FK01";
//	    	str1 = pay.submitResult("", editData.getId().toString(), true, 1,url, "dYkAAAAAmMgNbdH0");
//	    	MsgBox.showInfo(str1[0]+str1[1]+str1[2]);
//	    	str1 = pay.approveClose("", editData.getId().toString(), 1, "0", "",null);
//	    	MsgBox.showInfo(str1[0]+str1[1]+str1[2]);
//	    }
       
    	
       String u=editData.getId().toString();
       
	   String url = "http://10.130.12.20/BPMStart.aspx?bsid=ERP&boid="+u+"&btid=BGQZ01&userid="+SysContext.getSysContext().getUserName()+"";
       creatFrame(url);
       editData.setState(FDCBillStateEnum.SAVED);	
    	
    	
    }
    
    /**
     * 提交BMP
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSave_actionPerformed(e);
    }
    
    /**
     * 流程图
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
    	editData = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(new ObjectUuidPK(editData.getId()));
    	String url = editData.getDescription();
    	creatFrame(url);
    }
    
    /**
     * 审批结果查看
     */
    public void actionAuditResult_actionPerformed(ActionEvent e)throws Exception {
    	//String [] str1 = new String[3];
    	//String[] xml = getInfoFacadeFactory.getRemoteInstance().GetbillInfo("",editData.getId().toString());
    	//String [] str1= getInfoFacadeFactory.getRemoteInstance().ApproveClose("", "dYkAAAAAhPINbdH0", 1, "1", "",null);
    	//MsgBox.showInfo(str1[0]+str1[1]+str1[2]);
	   	//EASLoginProxy login = new EASLoginProxyServiceLocator().getEASLogin(new URL("http://127.0.0.1:56898/ormrpc/services/EASLogin"));
//	   	WSContext  ws = login.login("kd-user", "kduser", "eas", "kd_002", "l2", 1);
//	    if(ws.getSessionId()!=null){
//	    	WSgetInfoFacadeSrvProxy pay = new WSgetInfoFacadeSrvProxyServiceLocator().getWSgetInfoFacade(new URL("http://127.0.0.1:56898/ormrpc/services/WSgetInfoFacade"));
//	    	str1 = pay.getbillInfo("", editData.getId().toString());
//	    	MsgBox.showInfo(str1[0]+str1[1]+str1[2]);
//	    	String url = "http://10.130.12.20/BPMStart.aspx?bsid=ERP&boid="+editData.getId().toString()+"&btid=HT01";
//	    	str1 = pay.submitResult("", editData.getId().toString(), true, 1,url, "dYkAAAAAmMgNbdH0");
//	    	MsgBox.showInfo(str1[0]+str1[1]+str1[2]);
//	    	str1 = pay.approveClose("", editData.getId().toString(), 1, "0", "",null);
//	    	MsgBox.showInfo(str1[0]+str1[1]+str1[2]);
//	    }
    	
    	editData = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(new ObjectUuidPK(editData.getId()));
    	String url = editData.getDescription();
    	creatFrame(url);
    }

    private void creatFrame(String url)
    {
    	//获取MD5加密
//    	String md5 = MD5Helper.getMd5("blue", "20140813", "K2");
    	
    	JFrameBrowser jf = new JFrameBrowser();
    	jf.setJBrowserSize(720, 1200);
    	jf.setJBrwserOpenUrl(url);
    	
    	jf.setTitle("BPM");
    	
    	jf.OpenJBrowser(this);
    }

}
