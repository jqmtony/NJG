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

public class TargetInfoEntry extends CoreBase implements ITargetInfoEntry
{
    public TargetInfoEntry()
    {
        super();
        registerInterface(ITargetInfoEntry.class, this);
    }
    public TargetInfoEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITargetInfoEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("AD0D38A1");
    }
    private TargetInfoEntryController getController() throws BOSException
    {
        return (TargetInfoEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TargetInfoEntryInfo getTargetInfoEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTargetInfoEntryInfo(getContext(), pk);
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
    public TargetInfoEntryInfo getTargetInfoEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTargetInfoEntryInfo(getContext(), pk, selector);
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
    public TargetInfoEntryInfo getTargetInfoEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTargetInfoEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TargetInfoEntryCollection getTargetInfoEntryCollection() throws BOSException
    {
        try {
            return getController().getTargetInfoEntryCollection(getContext());
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
    public TargetInfoEntryCollection getTargetInfoEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTargetInfoEntryCollection(getContext(), view);
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
    public TargetInfoEntryCollection getTargetInfoEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTargetInfoEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}