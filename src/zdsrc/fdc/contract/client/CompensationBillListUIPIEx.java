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
    	this.btnAttachment.setText("撤销BPM流程");
    	this.btnAttachment.setToolTipText("撤销BPM流程");
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
	 * 新增
	 * */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}
	/*
	 * 删除
	 * */
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(this.getSelectedKeyValue()!=null){
		CompensationBillInfo info = CompensationBillFactory.getRemoteInstance().getCompensationBillInfo(new ObjectUuidPK(this.getSelectedKeyValue()));
		if(info.getId()!=null){
			//ContractBillInfo info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(editData.getId()));
			if("已审批".equals(info.getState().getAlias())||"审批中".equals(info.getState().getAlias()))
			{
				MsgBox.showInfo("该单据状态为:"+info.getState().getAlias()+",不能删除！");
				SysUtil.abort();
			}
		}
		}
		super.actionRemove_actionPerformed(e);
	}
	
	/*
	 * 修改
	 * */
	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		if(this.getSelectedKeyValue()!=null)
		{
		CompensationBillInfo info = CompensationBillFactory.getRemoteInstance().getCompensationBillInfo(new ObjectUuidPK(this.getSelectedKeyValue()));
		if(info.getId()!=null){
			//ContractBillInfo info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(editData.getId()));
			if("已审批".equals(info.getState().getAlias())||"审批中".equals(info.getState().getAlias()))
			{
				MsgBox.showInfo("该单据状态为:"+info.getState().getAlias()+",不能修改！");
				SysUtil.abort();
			}
		}
		}
		super.actionEdit_actionPerformed(arg0);
		
    	this.btnRemove.setVisible(false);
    	this.btnRemove.setEnabled(false);
	}
	

	
	/*
	 * 撤销流程
	 * */
	public void actionAttachment_actionPerformed(ActionEvent e)throws Exception {
		if(this.getSelectedKeyValue()!=null)
		{
		CompensationBillInfo info = CompensationBillFactory.getRemoteInstance().getCompensationBillInfo(new ObjectUuidPK(this.getSelectedKeyValue()));
		String result = "";
		if(info.getId()!=null){
			//ContractBillInfo info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(editData.getId()));
			if("审批中".equals(info.getState().getAlias()))
			{
				BPMServiceForERPSoap  login = new BPMServiceForERPLocator().getBPMServiceForERPSoap();
				result = login.withdraw("WY01", info.getId().toString(), info.getSourceFunction());
			}else{
				MsgBox.showInfo("该单据不在审批流程中，无需撤销流程！");
			}
		}
		}
	}
	
	/*
	 * 审批
	 * */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		
	}


	/*
	 * 查看审批结果
	 * */
	public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception {
		if(this.getSelectedKeyValue()!=null)
		{
		CompensationBillInfo info = CompensationBillFactory.getRemoteInstance().getCompensationBillInfo(new ObjectUuidPK(this.getSelectedKeyValue()));
		if(info.getId()!=null){
			// String url = StringUtilBPM.getBPMServerURL()+"?bsid=ERP&boid="+info.getId().toString()+"&btid=WY01&userid="+SysContext.getSysContext().getUserName()+"";
			//ContractBillInfo info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(editData.getId()));
	    	String url = info.getDescription();
			if("已审批".equals(info.getState().getAlias())||"审批中".equals(info.getState().getAlias()))
			{
				creatFrame(url);
			}
			else if("保存".equals(info.getState().getAlias()))
			{
				url = StringUtilBPM.getBPMServerURL()+"?bsid=ERP&boid="+info.getId().toString()+"&btid=WY01&userid="+SysContext.getSysContext().getUserName()+"";
				creatFrame(url);
			}
			else{
				MsgBox.showInfo("该单据未发起审批流程，或者已撤销流程，没有对应流程！");
			}
		}
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
