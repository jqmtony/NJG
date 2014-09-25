package com.kingdee.eas.bpm.selectors;

import java.util.Date;

import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.bpm.BillBaseSelector;

public class GCFKQKB implements BillBaseSelector{

	public String[] ApproveClose(Context ctx, String strBSID,
			IObjectValue billInfo, int procInstID,
			String processInstanceResult, String strComment, Date dtTime) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] GetbillInfo(Context ctx, String strBSID,
			IObjectValue billInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] SubmitResult(Context ctx, String strBSID,
			IObjectValue billInfo, boolean success, int procInstID,
			String procURL, String strMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	public SelectorItemCollection getSelectors() {
		// TODO Auto-generated method stub
		return null;
	}

}
