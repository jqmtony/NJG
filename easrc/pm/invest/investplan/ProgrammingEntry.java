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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.port.pm.invest.investplan.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class ProgrammingEntry extends TreeBase implements IProgrammingEntry
{
    public ProgrammingEntry()
    {
        super();
        registerInterface(IProgrammingEntry.class, this);
    }
    public ProgrammingEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IProgrammingEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7D5CF726");
    }
    private ProgrammingEntryController getController() throws BOSException
    {
        return (ProgrammingEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ProgrammingEntryInfo getProgrammingEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingEntryInfo(getContext(), pk);
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
    public ProgrammingEntryInfo getProgrammingEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingEntryInfo(getContext(), pk, selector);
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
    public ProgrammingEntryInfo getProgrammingEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProgrammingEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ProgrammingEntryCollection getProgrammingEntryCollection() throws BOSException
    {
        try {
            return getController().getProgrammingEntryCollection(getContext());
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
    public ProgrammingEntryCollection getProgrammingEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProgrammingEntryCollection(getContext(), view);
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
    public ProgrammingEntryCollection getProgrammingEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getProgrammingEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}