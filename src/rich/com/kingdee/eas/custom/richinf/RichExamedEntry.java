package com.kingdee.eas.custom.richinf;

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
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.custom.richinf.app.*;

public class RichExamedEntry extends CoreBillEntryBase implements IRichExamedEntry
{
    public RichExamedEntry()
    {
        super();
        registerInterface(IRichExamedEntry.class, this);
    }
    public RichExamedEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IRichExamedEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0FC258CA");
    }
    private RichExamedEntryController getController() throws BOSException
    {
        return (RichExamedEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public RichExamedEntryInfo getRichExamedEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRichExamedEntryInfo(getContext(), pk);
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
    public RichExamedEntryInfo getRichExamedEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRichExamedEntryInfo(getContext(), pk, selector);
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
    public RichExamedEntryInfo getRichExamedEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRichExamedEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RichExamedEntryCollection getRichExamedEntryCollection() throws BOSException
    {
        try {
            return getController().getRichExamedEntryCollection(getContext());
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
    public RichExamedEntryCollection getRichExamedEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRichExamedEntryCollection(getContext(), view);
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
    public RichExamedEntryCollection getRichExamedEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getRichExamedEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}