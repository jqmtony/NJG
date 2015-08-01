package com.kingdee.eas.fdc.basedata.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.ParamValue;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import java.util.List;
import com.kingdee.eas.fdc.basedata.RetValue;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface CostAccountFacadeController extends BizController
{
    public void assignOrgsCostAccount(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException, RemoteException;
    public void disAssignOrgsCostAccount(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException, RemoteException;
    public void disAssignSelectOrgsCostAccount(Context ctx, CostAccountCollection costAccounts) throws BOSException, EASBizException, RemoteException;
    public List assignProjsCostAccount(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException, RemoteException;
    public void disAssignProjsCostAccount(Context ctx, IObjectPK projectPK) throws BOSException, EASBizException, RemoteException;
    public void disAssignSelectProjsCostAccount(Context ctx, CostAccountCollection costAccounts) throws BOSException, EASBizException, RemoteException;
    public List assignOrgProject(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException, RemoteException;
    public void disAssignOrgProject(Context ctx, IObjectPK orgPK) throws BOSException, EASBizException, RemoteException;
    public ArrayList disAssignSelectOrgProject(Context ctx, CostAccountCollection costAccounts) throws BOSException, EASBizException, RemoteException;
    public void handleEnterDB(Context ctx, CostAccountInfo model, boolean isEnterDB) throws BOSException, EASBizException, RemoteException;
    public List assignToNextLevel(Context ctx, IObjectPK orgPK, Set nodeIds, boolean isProjectSet) throws BOSException, EASBizException, RemoteException;
    public boolean updateStageData(Context ctx, Set idSet, boolean isChecked) throws BOSException, RemoteException;
    public boolean checkCurrentCostAccountIsDetailsNode(Context ctx, String id, String orgId) throws BOSException, RemoteException;
    public void updateToLeafNode(Context ctx, Set idSet) throws BOSException, RemoteException;
    public List assignCostAccountBatch(Context ctx, Set orgSet, Set projectSet, Set costAccountList, Map isAddMap) throws BOSException, RemoteException;
    public List disAssignAccountBatch(Context ctx, Set orgSet, Set projectSet, Map costAccountList) throws BOSException, RemoteException;
    public RetValue updateAccountStage(Context ctx, ParamValue paramValue) throws BOSException, EASBizException, RemoteException;
}