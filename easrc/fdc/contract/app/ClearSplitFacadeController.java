package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ClearSplitFacadeController extends BizController
{
    public void clearAllSplit(Context ctx, String contractID, boolean isContract) throws BOSException, EASBizException, RemoteException;
    public void clearSplitBill(Context ctx, String contractID) throws BOSException, EASBizException, RemoteException;
    public void clearNoTextSplit(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public void clearSettle(Context ctx, String contractId) throws BOSException, EASBizException, RemoteException;
    public void clearWithoutTextSplit(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public void clearPaymentSplit(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public String clearPeriodConSplit(Context ctx, String contractID, String type) throws BOSException, EASBizException, RemoteException;
    public void clearChangeSplit(Context ctx, String changeId) throws BOSException, EASBizException, RemoteException;
    public List getToInvalidContract(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
}