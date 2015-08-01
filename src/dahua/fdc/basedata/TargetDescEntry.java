package com.kingdee.eas.fdc.basedata;

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
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class TargetDescEntry extends CoreBase implements ITargetDescEntry
{
    public TargetDescEntry()
    {
        super();
        registerInterface(ITargetDescEntry.class, this);
    }
    public TargetDescEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITargetDescEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BC03549E");
    }
    private TargetDescEntryController getController() throws BOSException
    {
        return (TargetDescEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TargetDescEntryInfo getTargetDescEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTargetDescEntryInfo(getContext(), pk);
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
    public TargetDescEntryInfo getTargetDescEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTargetDescEntryInfo(getContext(), pk, selector);
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
    public TargetDescEntryInfo getTargetDescEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTargetDescEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TargetDescEntryCollection getTargetDescEntryCollection() throws BOSException
    {
        try {
            return getController().getTargetDescEntryCollection(getContext());
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
    public TargetDescEntryCollection getTargetDescEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTargetDescEntryCollection(getContext(), view);
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
    public TargetDescEntryCollection getTargetDescEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTargetDescEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}