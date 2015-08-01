package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.IncomeAccountInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import com.kingdee.eas.fdc.basedata.IncomeAccountCollection;
import java.util.List;
import java.util.Set;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface IncomeAccountFacadeController extends BizController
{
    public void assignOrgsIncomeAccount(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException, RemoteException;
    public void disAssignOrgsIncomeAccount(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException, RemoteException;
    public void disAssignSelectOrgsIncomeAccount(Context ctx, IncomeAccountCollection IncomeAccounts) throws BOSException, EASBizException, RemoteException;
    public List assignProjsIncomeAccount(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException, RemoteException;
    public void disAssignProjsIncomeAccount(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException, RemoteException;
    public void disAssignSelectProjsIncomeAccount(Context ctx, IncomeAccountCollection IncomeAccounts) throws BOSException, EASBizException, RemoteException;
    public List assignOrgProject(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException, RemoteException;
    public void disAssignOrgProject(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException, RemoteException;
    public ArrayList disAssignSelectOrgProject(Context ctx, IncomeAccountCollection IncomeAccounts) throws BOSException, EASBizException, RemoteException;
    public void handleEnterDB(Context ctx, IncomeAccountInfo model, boolean isEnterDB) throws BOSException, EASBizException, RemoteException;
    public List assignToNextLevel(Context ctx, IObjectPK orgPK, Set nodeIds, boolean isProjectSet) throws BOSException, EASBizException, RemoteException;
}