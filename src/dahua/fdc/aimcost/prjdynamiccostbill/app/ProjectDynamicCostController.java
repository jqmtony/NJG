package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.ProjectDynamicCostCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ProjectDynamicCostController extends CoreBillBaseController
{
    public ProjectDynamicCostCollection getProjectDynamicCostCollection(Context ctx) throws BOSException, RemoteException;
    public ProjectDynamicCostCollection getProjectDynamicCostCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ProjectDynamicCostCollection getProjectDynamicCostCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public ProjectDynamicCostInfo getProjectDynamicCostInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ProjectDynamicCostInfo getProjectDynamicCostInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ProjectDynamicCostInfo getProjectDynamicCostInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, ProjectDynamicCostInfo model) throws BOSException, RemoteException;
    public void unAudit(Context ctx, ProjectDynamicCostInfo model) throws BOSException, RemoteException;
}