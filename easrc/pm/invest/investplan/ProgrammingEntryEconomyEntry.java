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

public class ProgrammingEntryEconomyEntry extends CoreBase implements IProgrammingEntryEconomyEntry
{
    public ProgrammingEntryEconomyEntry()
    {
        super();
        registerInterface(IProgrammingEntryEconomyEntry.class, this);
    }
    public ProgrammingEntryEconomyEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IProgrammingEntryEconomyEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D1CC0B1A");
    }
    private ProgrammingEntryEconomyEntryController getController() throws BOSException
    {
        return (ProgrammingEntryEconomyEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ProgrammingEntryEconomyEntryInfo getProgrammingEntryEconomyEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingEntryEconomyEntryInfo(getContext(), pk);
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
    public ProgrammingEntryEconomyEntryInfo getProgrammingEntryEconomyEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingEntryEconomyEntryInfo(getContext(), pk, selector);
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
    public ProgrammingEntryEconomyEntryInfo getProgrammingEntryEconomyEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingEntryEconomyEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ProgrammingEntryEconomyEntryCollection getProgrammingEntryEconomyEntryCollection() throws BOSException
    {
        try {
            return getController().getProgrammingEntryEconomyEntryCollection(getContext());
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
    public ProgrammingEntryEconomyEntryCollection getProgrammingEntryEconomyEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProgrammingEntryEconomyEntryCollection(getContext(), view);
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
    public ProgrammingEntryEconomyEntryCollection getProgrammingEntryEconomyEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getProgrammingEntryEconomyEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}