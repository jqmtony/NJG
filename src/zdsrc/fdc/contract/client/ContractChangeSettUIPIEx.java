package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;

import com.kingdee.eas.bpmdemo.JBrowserHelper.JFrameBrowser;
import com.kingdee.eas.common.client.SysContext;

public class ContractChangeSettUIPIEx extends ContractChangeSettUI{

	public ContractChangeSettUIPIEx() throws Exception {
		super();
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
		super.btnConfirm_actionPerformed(arg0);
		String url = "http://10.130.12.20/BPMStart.aspx?bsid=ERP&boid="+editData.getId().toString()+"&btid=BGJS01&userid="+SysContext.getSysContext().getUserName()+"";
    	creatFrame(url);
	}
	
	 private void creatFrame(String url)
	    {
	    	//获取MD5加密
//	    	String md5 = MD5Helper.getMd5("blue", "20140813", "K2");
	    	
	    	JFrameBrowser jf = new JFrameBrowser();
	    	jf.setJBrowserSize(720, 1200);
	    	jf.setJBrwserOpenUrl(url);
	    	
	    	jf.setTitle("BPM");
	    	
	    	jf.OpenJBrowser(this);
	    }

}
