package com.kingdee.eas.bpm;

import java.rmi.RemoteException;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public interface BillBaseSelector {
	public SelectorItemCollection getSelectors();
	public String[] SubmitResult(Context ctx, String strBSID,IObjectValue billInfo, boolean success, int procInstID, String procURL,String strMessage);
	public String[] ApproveClose(Context ctx, String strBSID,IObjectValue billInfo,int procInstID, String processInstanceResult, String strComment,Date dtTime);
	public String[] GetbillInfo(Context ctx, String strBSID,IObjectValue billInfo) ;
	public String[] GetrRelatedBillInfo(Context ctx, String strBTID,IObjectValue billInfo, String strRelatedCode) ;
	public String[] ApproveBack(Context ctx, String strBTID, String strBOID, String strXML);
}
