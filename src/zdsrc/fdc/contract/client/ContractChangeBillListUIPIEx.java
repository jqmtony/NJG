package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.bpmdemo.JBrowserHelper.JFrameBrowser;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;

public class ContractChangeBillListUIPIEx extends ContractChangeBillListUI{

	public ContractChangeBillListUIPIEx() throws Exception {
		super();
		// TODO Auto-generated constructor stub
		
	}
	
//	public void actionAuditResult_actionPerformed(ActionEvent e)
//	throws Exception {
//
//		ContractChangeBillInfo info =ContractChangeBillFactory.getRemoteInstance().getContractChangeBillInfo(new ObjectUuidPK(this.getSelectedKeyValue()));
//		if("已审批".equals(info.getState().getAlias())||"审批中".equals(info.getState().getAlias()))
//		{
//			if(info.getId()!=null){
//				String url = info.getDescription();
//				creatFrame(url);
//			}
//		}
//
//	}
//
//	private void creatFrame(String url)
//	{
//		//获取MD5加密
//		JFrameBrowser jf = new JFrameBrowser();
//		jf.setJBrowserSize(720, 1200);
//		jf.setJBrwserOpenUrl(url);
//		jf.setTitle("BPM");
//		jf.OpenJBrowser(this);
//	}

}
