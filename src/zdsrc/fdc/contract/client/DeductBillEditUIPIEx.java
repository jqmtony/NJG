package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.net.URL;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.bpm.common.StringUtilBPM;
import com.kingdee.eas.bpmdemo.JBrowserHelper.JFrameBrowser;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPLocator;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoap;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.EASLoginProxy;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.EASLoginProxyServiceLocator;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.WSContext;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.WSgetInfoFacadeSrvProxy;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.WSgetInfoFacadeSrvProxyServiceLocator;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.finance.DeductBillFactory;
import com.kingdee.eas.fdc.finance.DeductBillInfo;
import com.kingdee.eas.fdc.finance.client.DeductBillEditUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

//�ۿ
public class DeductBillEditUIPIEx extends DeductBillEditUI{

	public DeductBillEditUIPIEx() throws Exception {
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
    	this.btnSubmit.setText("�ύBPM����");
    	this.btnSubmit.setToolTipText("�ύBPM����");
    	
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
         if(editData==null)
         {
    	  this.btnAuditResult.setEnabled(false);
    	  this.btnAttachment.setEnabled(false);
         }
    	}
    	else if(editData.getId()!=null)
     	{   
    		DeductBillInfo info = null;
			try {
				info = DeductBillFactory.getRemoteInstance().getDeductBillInfo(new ObjectUuidPK(editData.getId()));
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		if("����".equals(info.getState().getAlias()))
    		{
    			this.btnAuditResult.setEnabled(false);
           	    this.btnAttachment.setEnabled(false);
    		}
    		if("������".equals(info.getState().getAlias()))
    		{
    			this.btnAuditResult.setEnabled(true);
           	    this.btnAttachment.setEnabled(true);
    		}
    		else if("������".equals(info.getState().getAlias()))
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
	 * ����
	 * */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
    	this.btnAttachment.setEnabled(false);
    	this.btnAuditResult.setEnabled(false);
	}
	/*
	 * ɾ��
	 * */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			DeductBillInfo info =DeductBillFactory.getRemoteInstance().getDeductBillInfo(new ObjectUuidPK(editData.getId()));
			if("������".equals(info.getState().getAlias())||"������".equals(info.getState().getAlias()))
			{
				MsgBox.showInfo("�õ���״̬Ϊ:"+info.getState().getAlias()+",����ɾ����");
				SysUtil.abort();
			}
		}
		super.actionRemove_actionPerformed(e);
	}
	
	/*
	 * �޸�
	 * */
	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		if(editData.getId()!=null){
			DeductBillInfo info =DeductBillFactory.getRemoteInstance().getDeductBillInfo(new ObjectUuidPK(editData.getId()));
			if("������".equals(info.getState().getAlias())||"������".equals(info.getState().getAlias()))
			{
				MsgBox.showInfo("�õ���״̬Ϊ:"+info.getState().getAlias()+",�����޸ģ�");
				SysUtil.abort();
			}
		}
		super.actionEdit_actionPerformed(arg0);
    	this.btnRemove.setVisible(false);
    	this.btnRemove.setEnabled(false);
	}
	

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()==null)
		{
			actionSave_actionPerformed(e);
		    this.btnAttachment.setEnabled(false);
    	    this.btnAuditResult.setEnabled(false);
		}
		if(editData.getId()!=null)
		{  
			DeductBillInfo info =DeductBillFactory.getRemoteInstance().getDeductBillInfo(new ObjectUuidPK(editData.getId()));
		   if("������".equals(info.getState().getAlias()) && info.getDescription()!=null)
		   {
			   MsgBox.showInfo("�õ��������������У������ٴ��ύ��");
		   }else{
			   super.actionSubmit_actionPerformed(e);
			   String sql = " update T_FNC_DeductBill set fState='1SAVED' where fid='"+editData.getId()+"'";
			   FDCSQLBuilder bu = new FDCSQLBuilder();
			   bu.appendSql(sql);
			   bu.executeUpdate();
		    	String [] str1 = new String[3];
			   	EASLoginProxy login = new EASLoginProxyServiceLocator().getEASLogin(new URL("http://127.0.0.1:56898/ormrpc/services/EASLogin"));
			   	WSContext  ws = login.login("kd-user", "kduser", "eas", "kd_002", "l2", 1);
			    if(ws.getSessionId()!=null){
			    	WSgetInfoFacadeSrvProxy pay = new WSgetInfoFacadeSrvProxyServiceLocator().getWSgetInfoFacade(new URL("http://127.0.0.1:56898/ormrpc/services/WSgetInfoFacade"));
			    	str1 = pay.getbillInfo("", editData.getId().toString());
			    	MsgBox.showInfo(str1[0] + str1[1] + str1[2]);
			    	String url = StringUtilBPM.getBPMServerURL()+"?bsid=ERP&boid="+editData.getId().toString()+"&btid=HT01";
			    	str1 = pay.submitResult("", editData.getId().toString(), true, 1,url, editData.getId().toString());
			    	MsgBox.showInfo(str1[0]+str1[1]+str1[2]);
			    	str1 = pay.approveClose("", editData.getId().toString(), 1, "1", "",null);
			    	MsgBox.showInfo(str1[0]+str1[1]+str1[2]);
			    }
			  // String url = StringUtilBPM.getBPMServerURL()+"?bsid=ERP&boid="+editData.getId().toString()+"&btid=HT01&userid="+SysContext.getSysContext().getUserName()+"";
			  // creatFrame(url);
		   }
		}
	}
	
	/*
	 * ��������
	 * */
	public void actionAttachment_actionPerformed(ActionEvent e)throws Exception {
		String result = "";
		if(editData.getId()!=null){
			DeductBillInfo info =DeductBillFactory.getRemoteInstance().getDeductBillInfo(new ObjectUuidPK(editData.getId()));
			if("������".equals(info.getState().getAlias()))
			{
				BPMServiceForERPSoap  login = new BPMServiceForERPLocator().getBPMServiceForERPSoap();
				result = login.withdraw("HT01", info.getId().toString(), info.getSourceFunction());
			}else{
				MsgBox.showInfo("�õ��ݲ������������У����賷�����̣�");
			}
		}
		else
		{
			MsgBox.showInfo("�õ��ݲ������������У����賷�����̣�");
		}
	}
	

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	}
	/*
	 * ����
	 * */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			DeductBillInfo info =DeductBillFactory.getRemoteInstance().getDeductBillInfo(new ObjectUuidPK(editData.getId()));
		    String url = info.getDescription();
			if("������".equals(info.getState().getAlias()) && ("".equals(info.getDescription())||info.getDescription()==null))
			{
				super.actionAudit_actionPerformed(e);
			}else{
				if("������".equals(info.getState().getAlias())){
					MsgBox.showInfo("�õ��������������У����ܽ����˹�������");
				}else {
					MsgBox.showInfo("�õ���״̬Ϊ:"+info.getState().getAlias()+",����������");
				}
			}
		}
		
	}


	/*
	 * �鿴�������
	 * */
	public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			DeductBillInfo info =DeductBillFactory.getRemoteInstance().getDeductBillInfo(new ObjectUuidPK(editData.getId()));
	    	String url = info.getDescription();
			if("������".equals(info.getState().getAlias())||"������".equals(info.getState().getAlias()))
			{
				creatFrame(url);
			}else{
				MsgBox.showInfo("�õ���δ�����������̣������ѳ������̣�û�ж�Ӧ���̣�");
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
