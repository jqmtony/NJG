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

public interface IgetInfoFacade extends IBizCtrl
{
    public String[] GetbillInfo(String strBSID, String strBOID) throws BOSException;
    public String[] GetrRelatedBillInfo(String strBTID, String strBOID, String strRelatedCode) throws BOSException;
    public String[] SubmitResult(String strBSID, String strBOID, boolean bSuccess, int iProcInstID, String procURL, String strMessage) throws BOSException;
    public String[] ApproveClose(String strBSID, String strBOID, int iProcInstID, String eProcessInstanceResult, String strComment, Date dtTime) throws BOSException;
    public String[] ApproveBack(String strBTID, String strBOID, String strXML) throws BOSException;
    public String[] GetcurProject() throws BOSException;
    public String[] GetDemo() throws BOSException;
    public String[] Getpoint(String pointID) throws BOSException;
    public void GetProgressReport(String Domxml) throws BOSException;
    public void GetTaskEvalation(String Domxml) throws BOSException;
}