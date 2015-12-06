package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ProjectDynamicCostEntry extends CoreBillEntryBase implements IProjectDynamicCostEntry
{
    public ProjectDynamicCostEntry()
    {
        super();
        registerInterface(IProjectDynamicCostEntry.class, this);
    }
    public ProjectDynamicCostEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectDynamicCostEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BF1D2179");
    }
    private ProjectDynamicCostEntryController getController() throws BOSException
    {
        return (ProjectDynamicCostEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ProjectDynamicCostEntryInfo getProjectDynamicCostEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectDynamicCostEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public ProjectDynamicCostEntryInfo getProjectDynamicCostEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectDynamicCostEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public ProjectDynamicCostEntryInfo getProjectDynamicCostEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectDynamicCostEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ProjectDynamicCostEntryCollection getProjectDynamicCostEntryCollection() throws BOSException
    {
        try {
            return getController().getProjectDynamicCostEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public ProjectDynamicCostEntryCollection getProjectDynamicCostEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectDynamicCostEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public ProjectDynamicCostEntryCollection getProjectDynamicCostEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getProjectDynamicCostEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}