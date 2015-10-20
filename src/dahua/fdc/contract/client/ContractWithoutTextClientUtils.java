package com.kingdee.eas.fdc.contract.client;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.finance.DeductBillEntryFactory;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

public class ContractWithoutTextClientUtils {

	public static void checkRef(CoreUIObject ui, String id) {
		boolean existsDeductBill = false;
		boolean existsPayReqBill = false;
		try {
			FilterInfo filter2 = new FilterInfo();
			filter2.getFilterItems().add(new FilterItemInfo("contractId", id));
			existsDeductBill = DeductBillEntryFactory.getRemoteInstance().exists(filter2);
			
			//¸¶¿îµ¥ contractBillId
			filter2 = new FilterInfo();
			filter2.getFilterItems().add(new FilterItemInfo("contractBillId", id));
			existsPayReqBill = PaymentBillFactory.getRemoteInstance().exists(filter2);

		} catch (Exception e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		} 
		
		if(existsDeductBill || existsPayReqBill) {
			MsgBox.showWarning(ui, ContractClientUtils.getRes("exist_ref"));
			SysUtil.abort();
		}
	}
}
