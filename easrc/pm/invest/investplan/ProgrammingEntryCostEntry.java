package com.kingdee.eas.port.pm.invest.investplan;

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
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.port.pm.invest.investplan.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class ProgrammingEntryCostEntry extends CoreBase implements IProgrammingEntryCostEntry
{
    public ProgrammingEntryCostEntry()
    {
        super();
        registerInterface(IProgrammingEntryCostEntry.class, this);
    }
    public ProgrammingEntryCostEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IProgrammingEntryCostEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4F1A141F");
    }
    private ProgrammingEntryCostEntryController getController() throws BOSException
    {
        return (ProgrammingEntryCostEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ProgrammingEntryCostEntryInfo getProgrammingEntryCostEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingEntryCostEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public ProgrammingEntryCostEntryInfo getProgrammingEntryCostEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingEntryCostEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public ProgrammingEntryCostEntryInfo getProgrammingEntryCostEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingEntryCostEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ProgrammingEntryCostEntryCollection getProgrammingEntryCostEntryCollection() throws BOSException
    {
        try {
            return getController().getProgrammingEntryCostEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public ProgrammingEntryCostEntryCollection getProgrammingEntryCostEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProgrammingEntryCostEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public ProgrammingEntryCostEntryCollection getProgrammingEntryCostEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getProgrammingEntryCostEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}