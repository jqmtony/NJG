package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class ProjectDynaticFundPayPlanDetailEntry extends PayPlanDataBase implements IProjectDynaticFundPayPlanDetailEntry
{
    public ProjectDynaticFundPayPlanDetailEntry()
    {
        super();
        registerInterface(IProjectDynaticFundPayPlanDetailEntry.class, this);
    }
    public ProjectDynaticFundPayPlanDetailEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectDynaticFundPayPlanDetailEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("04F78CB1");
    }
    private ProjectDynaticFundPayPlanDetailEntryController getController() throws BOSException
    {
        return (ProjectDynaticFundPayPlanDetailEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public ProjectDynaticFundPayPlanDetailEntryInfo getProjectDynaticFundPayPlanDetailEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectDynaticFundPayPlanDetailEntryInfo(getContext(), pk);
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
    public ProjectDynaticFundPayPlanDetailEntryInfo getProjectDynaticFundPayPlanDetailEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectDynaticFundPayPlanDetailEntryInfo(getContext(), pk, selector);
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
    public ProjectDynaticFundPayPlanDetailEntryInfo getProjectDynaticFundPayPlanDetailEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectDynaticFundPayPlanDetailEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值集-System defined method
     *@return
     */
    public ProjectDynaticFundPayPlanDetailEntryCollection getProjectDynaticFundPayPlanDetailEntryCollection() throws BOSException
    {
        try {
            return getController().getProjectDynaticFundPayPlanDetailEntryCollection(getContext());
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
    public ProjectDynaticFundPayPlanDetailEntryCollection getProjectDynaticFundPayPlanDetailEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectDynaticFundPayPlanDetailEntryCollection(getContext(), view);
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
    public ProjectDynaticFundPayPlanDetailEntryCollection getProjectDynaticFundPayPlanDetailEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getProjectDynaticFundPayPlanDetailEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}