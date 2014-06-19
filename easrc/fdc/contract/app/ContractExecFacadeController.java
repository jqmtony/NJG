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
import java.util.Map;
import com.kingdee.bos.framework.*;
import java.util.Set;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ContractExecFacadeController extends BizController
{
    public Map _getCompleteAmt(Context ctx, String fullOrgUnitID, Set idSet) throws BOSException, EASBizException, RemoteException;
    public Map _getCompletePrjAmtForNoTextContract(Context ctx, Set idSet) throws BOSException, EASBizException, RemoteException;
    public Map getCompleteAmt(Context ctx, Map orgId2ContractIdSet) throws BOSException, EASBizException, RemoteException;
}