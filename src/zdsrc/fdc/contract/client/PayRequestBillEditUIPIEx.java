package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.net.URL;


import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.bpmdemo.JBrowserHelper.JFrameBrowser;
import com.kingdee.eas.bpmdemo.webservers.getInfoFacadeFactory;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPLocator;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoap;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.EASLoginProxy;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.EASLoginProxyServiceLocator;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.WSContext;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.WSgetInfoFacadeSrvProxy;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.WSgetInfoFacadeSrvProxyServiceLocator;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class PayRequestBillEditUIPIEx extends PayRequestBillEditUI{
	
	private ContractTypeInfo typeInfo;
	private boolean isShiGong = false;
	private boolean isFenBao = false;
	private boolean isZongBao = false;
	
	public PayRequestBillEditUIPIEx() throws Exception {
		super();
	}
	

	public void onLoad() throws Exception {
		super.onLoad();
		InitButton();
	}

    private void InitButton()
    {
    	this.btnAttachment.setText("撤销BPM流程");
    	this.btnAttachment.setToolTipText("撤销BPM流程");
    	this.btnSubmit.setText("提交BPM流程");
    	this.btnSubmit.setToolTipText("提交BPM流程");
    	
    	this.chkMenuItemSubmitAndAddNew.setSelected(false);
	   	this.chkMenuItemSubmitAndAddNew.setEnabled(false);
	   	this.btnPrint.setVisible(false);
	   	this.btnPrintPreview.setVisible(false);
	   	this.btnPre.setVisible(false);
	   	this.btnNext.setVisible(false);
	   	this.btnLast.setVisible(false);
	   	this.btnFirst.setVisible(false);
    	this.actionCreateTo.setVisible(false);
    	this.actionCreateFrom.setVisible(false);
    	this.actionMultiapprove.setVisible(false);
    	this.actionNextPerson.setVisible(false);
    	this.btnWorkFlowG.setVisible(false);
    	if(editData.getId()==null||editData.getId().equals(""))
    	{
         if(editData.getState()==null)
         {
    	  this.btnAuditResult.setEnabled(false);
    	  this.btnAttachment.setEnabled(false);
         }
         else if("保存".equals(editData.getState().getAlias()))
         {
        	 this.btnAuditResult.setEnabled(true);
       	     this.btnAttachment.setEnabled(false);
         }
    	}else if(editData.getId()!=null&&editData.getState()==null)
     	{
     		this.btnAuditResult.setEnabled(false);
       	    this.btnAttachment.setEnabled(false);
     	}else if(editData.getId()!=null||editData.getState()==null)
     	{   
    		if("审批中".equals(editData.getState().getAlias()))
    		{
    			this.btnAuditResult.setEnabled(true);
           	    this.btnAttachment.setEnabled(true);
    		}
    		else if("已审批".equals(editData.getState().getAlias()))
    		{
    			this.btnAuditResult.setEnabled(true);
           	    this.btnAttachment.setEnabled(false);
    		}else if("保存".equals(editData.getState().getAlias()))
    		{
    			this.btnAuditResult.setEnabled(true);
           	    this.btnAttachment.setEnabled(false);
    		}
    		else
    		{
     		this.btnAuditResult.setEnabled(false);
       	    this.btnAttachment.setEnabled(false);
    		}
     	}
    	

    }
    
	protected boolean isContinueAddNew() {
		return false;
	}
	
	protected void afterSubmitAddNew() {
	}
	
	
	/*
	 * 新增
	 * */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}
	/*
	 * 删除
	 * */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			PayRequestBillInfo info = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(editData.getId()));
			if("已审批".equals(info.getState().getAlias())||"审批中".equals(info.getState().getAlias()))
			{
				MsgBox.showInfo("该单据状态为:"+info.getState().getAlias()+",不能删除！");
				SysUtil.abort();
			}
		}
		super.actionRemove_actionPerformed(e);
	}
	
	/*
	 * 修改
	 * */
	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionEdit_actionPerformed(arg0);
		if(editData.getId()!=null){
			ContractWithoutTextInfo info = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(editData.getId()));
			if("已审批".equals(info.getState().getAlias())||"审批中".equals(info.getState().getAlias()))
			{
				MsgBox.showInfo("该单据状态为:"+info.getState().getAlias()+",不能修改！");
				SysUtil.abort();
			}
		}
    	this.btnRemove.setVisible(false);
    	this.btnRemove.setEnabled(false);
	}
	

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()==null)
			actionSave_actionPerformed(e);
		if(editData.getId()!=null)
		{  
			PayRequestBillInfo info = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(editData.getId()));
		  // if("审批中".equals(info.getState().getAlias()) && info.getDescription()!=null)
			if("审批中".equals(info.getState().getAlias()))
		   {
			   MsgBox.showInfo("该单据在审批流程中，不能再次提交！");
		   }else{
			   super.actionSubmit_actionPerformed(e);
			   String sql = " update t_con_payrequestbill set fState='1SAVED' where fid='"+editData.getId()+"'";
			   FDCSQLBuilder bu = new FDCSQLBuilder();
			   bu.appendSql(sql);
			   bu.executeUpdate();
		    	
//			   String [] str1 = new String[3];
//			   	EASLoginProxy login = new EASLoginProxyServiceLocator().getEASLogin(new URL("http://127.0.0.1:56898/ormrpc/services/EASLogin"));
//			   	WSContext  ws = login.login("kd-user", "kduser", "eas", "kd_002", "l2", 1);
//			    if(ws.getSessionId()!=null){
//			    	WSgetInfoFacadeSrvProxy pay = new WSgetInfoFacadeSrvProxyServiceLocator().getWSgetInfoFacade(new URL("http://127.0.0.1:56898/ormrpc/services/WSgetInfoFacade"));
//			    	str1 = pay.getbillInfo("", editData.getId().toString());
//			    	MsgBox.showInfo(str1[0] + str1[1] + str1[2]);
//			    	String url = "http://10.130.12.20/BPMStart.aspx?bsid=ERP&boid="+editData.getId().toString()+"&btid=FK01";
//			    	str1 = pay.submitResult("", editData.getId().toString(), true, 1,url, editData.getId().toString());
//			    	MsgBox.showInfo(str1[0]+str1[1]+str1[2]);
//			    	str1 = pay.approveClose("", editData.getId().toString(), 1, "1", "",null);
//			    	MsgBox.showInfo(str1[0]+str1[1]+str1[2]);
//			    }
			   
			   String url = "http://10.130.12.20/BPMStart.aspx?bsid=ERP&boid="+editData.getId().toString()+"&btid=FK01&userid="+SysContext.getSysContext().getUserName()+"";
			   creatFrame(url);
		   }
		}
	}
	
	/*
	 * 撤销流程
	 * */
	public void actionAttachment_actionPerformed(ActionEvent e)throws Exception {
		String result = "";
		if(editData.getId()!=null){
			PayRequestBillInfo info = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(editData.getId()));
			if("审批中".equals(info.getState().getAlias()))
			{
				BPMServiceForERPSoap  login = new BPMServiceForERPLocator().getBPMServiceForERPSoap();
				result = login.withdraw("FK01", info.getId().toString(), info.getSourceFunction());
			}else{
				MsgBox.showInfo("该单据不在审批流程中，无需撤销流程！");
			}
		}
	}
	

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}
	/*
	 * 审批
	 * */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			PayRequestBillInfo info = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(editData.getId()));
	    	String url = info.getDescription();
			//if("审批中".equals(info.getState().getAlias()) && ("".equals(info.getDescription())||info.getDescription()==null))
	    	if("审批中".equals(info.getState().getAlias()))
			{
				super.actionAudit_actionPerformed(e);
			}else{
				if("审批中".equals(info.getState().getAlias())){
					MsgBox.showInfo("该单据在审批流程中，不能进行人工审批！");
				}else {
					MsgBox.showInfo("该单据状态为:"+info.getState().getAlias()+",不能审批！");
				}
			}
		}
		
	}


	/*
	 * 查看审批结果
	 * */
	public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			PayRequestBillInfo info = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(editData.getId()));
	    	//String url = info.getDescription();
			String url = "http://10.130.12.20/BPMStart.aspx?bsid=ERP&boid="+editData.getId().toString()+"&btid=FK01&userid="+SysContext.getSysContext().getUserName()+"";
//			if("已审批".equals(info.getState().getAlias())||"审批中".equals(info.getState().getAlias()))
//			{
				creatFrame(url);
//			}else{
//				MsgBox.showInfo("该单据未发起审批流程，或者已撤销流程，没有对应流程！");
//			}
		}
	}
	
	private void creatFrame(String url)
    {
    	//获取MD5加密
    	JFrameBrowser jf = new JFrameBrowser();
    	jf.setJBrowserSize(720, 1200);
    	jf.setJBrwserOpenUrl(url);
    	jf.setTitle("BPM");
    	jf.OpenJBrowser(this);
    }
}
