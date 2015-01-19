package com.kingdee.eas.bpmdemo.webservers;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.Date;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface getInfoFacadeController extends BizController
{
    public String[] GetbillInfo(Context ctx, String strBSID, String strBOID) throws BOSException, RemoteException;
    public String[] GetrRelatedBillInfo(Context ctx, String strBTID, String strBOID, String strRelatedCode) throws BOSException, RemoteException;
    public String[] SubmitResult(Context ctx, String strBSID, String strBOID, boolean bSuccess, int iProcInstID, String procURL, String strMessage) throws BOSException, RemoteException;
    public String[] ApproveClose(Context ctx, String strBSID, String strBOID, int iProcInstID, String eProcessInstanceResult, String strComment, Date dtTime) throws BOSException, RemoteException;
    public String[] ApproveBack(Context ctx, String strBTID, String strBOID, String strXML) throws BOSException, RemoteException;
    public String[] GetcurProject(Context ctx) throws BOSException, RemoteException;
    public String[] GetDemo(Context ctx) throws BOSException, RemoteException;
    public String[] Getpoint(Context ctx, String pointID) throws BOSException, RemoteException;
}