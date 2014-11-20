package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;

import com.kingdee.eas.bpm.common.StringUtilBPM;
import com.kingdee.eas.bpmdemo.JBrowserHelper.JFrameBrowser;
import com.kingdee.eas.common.client.SysContext;

public class ContractChangeSettUIPIEx extends ContractChangeSettUI{

	public ContractChangeSettUIPIEx() throws Exception {
		super();
	}
	

	public void onLoad() throws Exception {
		super.onLoad();
		this.btnConfirm.setText("提交BPM流程");
		this.btnConfirm.setToolTipText("提交BPM流程");
	}
	protected boolean isContinueAddNew() {
		return false;
	}
	
	protected void btnConfirm_actionPerformed(ActionEvent arg0)
			throws Exception {
		super.btnConfirm_actionPerformed(arg0);
//		   
//		   String [] str1 = new String[3];
//		   	EASLoginProxy login = new EASLoginProxyServiceLocator().getEASLogin(new URL("http://127.0.0.1:56898/ormrpc/services/EASLogin"));
//		   	WSContext  ws = login.login("kd-user", "kduser", "eas", "kd_002", "l2", 1);
//		    if(ws.getSessionId()!=null){
//		    	WSgetInfoFacadeSrvProxy pay = new WSgetInfoFacadeSrvProxyServiceLocator().getWSgetInfoFacade(new URL("http://127.0.0.1:56898/ormrpc/services/WSgetInfoFacade"));
//		    	str1 = pay.getbillInfo("", editData.getId().toString());
//		    	MsgBox.showInfo(str1[0] + str1[1] + str1[2]);
//		    	String url = StringUtilBPM.getBPMServerURL()+"?bsid=ERP&boid="+editData.getId().toString()+"&btid=BGJS01";
//		    	str1 = pay.submitResult("", editData.getId().toString(), true, 1,url, editData.getId().toString());
//		    	MsgBox.showInfo(str1[0]+str1[1]+str1[2]);
//		    	str1 = pay.approveClose("", editData.getId().toString(), 1, "1", "",null);
//		    	MsgBox.showInfo(str1[0]+str1[1]+str1[2]);
//		    }
		
		String url = StringUtilBPM.getBPMServerURL()+"?bsid=ERP&boid="+editData.getId().toString()+"&btid=BGJS01&userid="+SysContext.getSysContext().getUserName()+"";
    	creatFrame(url);
	}
	
	private void creatFrame(String url)
	 {
	    	JFrameBrowser jf = new JFrameBrowser();
	    	jf.setJBrowserSize(720, 1200);
	    	jf.setJBrwserOpenUrl(url);
	    	jf.setTitle("BPM");
	    	jf.OpenJBrowser(this);
	 }

}
