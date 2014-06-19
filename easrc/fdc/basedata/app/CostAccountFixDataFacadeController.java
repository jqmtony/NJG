package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface CostAccountFixDataFacadeController extends BizController
{
    public void fixOrgSourseID(Context ctx, BOSUuid orgID, List parentOrgList) throws BOSException, EASBizException, RemoteException;
    public void fixPrjSourseID(Context ctx, BOSUuid projectID, List parentNodeList) throws BOSException, EASBizException, RemoteException;
}