package com.kingdee.eas.fdc.basedata.client;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.eas.framework.client.CoreUI;

public class FDCBillDataFillListener implements KDTDataFillListener{

	KDTable tblMain;
	CoreUI ui;
	public FDCBillDataFillListener(KDTable tblMain,CoreUI ui){
		this.tblMain = tblMain;
		this.ui = ui;
	}
	
	public void afterDataFill(KDTDataRequestEvent e) {
		FDCClientHelper.initTable(tblMain);
		FDCClientHelper.tblMainAfterDataFill(e,tblMain,ui);
	}
	
}
