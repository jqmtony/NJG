package com.kingdee.eas.fdc.basedata.app;

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
import com.kingdee.eas.fdc.basedata.AcctAccreditUserCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface AcctAccreditFacadeController extends BizController
{
    public void saveAcctAccreditUsers(Context ctx, AcctAccreditUserCollection accreditUsers) throws BOSException, EASBizException, RemoteException;
    public Set getPrjAccreditAcctSet(Context ctx, String prjId) throws BOSException, RemoteException;
    public Set getOrgAccreditAcctSet(Context ctx, String orgUnitId) throws BOSException, RemoteException;
    public Map fetchData(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
}