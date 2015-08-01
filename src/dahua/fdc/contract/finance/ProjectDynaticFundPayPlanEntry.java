package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillEntryBase;
import com.kingdee.eas.framework.IBillEntryBase;

public class ProjectDynaticFundPayPlanEntry extends BillEntryBase implements IProjectDynaticFundPayPlanEntry
{
    public ProjectDynaticFundPayPlanEntry()
    {
        super();
        registerInterface(IProjectDynaticFundPayPlanEntry.class, this);
    }
    public ProjectDynaticFundPayPlanEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectDynaticFundPayPlanEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7A4BD682");
    }
    private ProjectDynaticFundPayPlanEntryController getController() throws BOSException
    {
        return (ProjectDynaticFundPayPlanEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public ProjectDynaticFundPayPlanEntryInfo getProjectDynaticFundPayPlanEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectDynaticFundPayPlanEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public ProjectDynaticFundPayPlanEntryInfo getProjectDynaticFundPayPlanEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectDynaticFundPayPlanEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql oql
     *@return
     */
    public ProjectDynaticFundPayPlanEntryInfo getProjectDynaticFundPayPlanEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectDynaticFundPayPlanEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值集-System defined method
     *@return
     */
    public ProjectDynaticFundPayPlanEntryCollection getProjectDynaticFundPayPlanEntryCollection() throws BOSException
    {
        try {
            return getController().getProjectDynaticFundPayPlanEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值集-System defined method
     *@param view view
     *@return
     */
    public ProjectDynaticFundPayPlanEntryCollection getProjectDynaticFundPayPlanEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectDynaticFundPayPlanEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值集-System defined method
     *@param oql oql
     *@return
     */
    public ProjectDynaticFundPayPlanEntryCollection getProjectDynaticFundPayPlanEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getProjectDynaticFundPayPlanEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}