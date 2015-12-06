package com.kingdee.eas.fdc.earlywarn;

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

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface DHWarnMsgFacadeController extends BizController
{
    public void programmingWarnMsg(Context ctx) throws BOSException, RemoteException;
    public void dhScheduleWarnMsg(Context ctx) throws BOSException, RemoteException;
    public void programmingGZWarnMsg(Context ctx, String billId, int day) throws BOSException, RemoteException;
    public void settleDeclarationWarnMsg(Context ctx) throws BOSException, RemoteException;
    public void aimCostDiffWarnMsg(Context ctx) throws BOSException, RemoteException;
}