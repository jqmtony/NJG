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

public class RichCustomWriteOffEntry extends CoreBillEntryBase implements IRichCustomWriteOffEntry
{
    public RichCustomWriteOffEntry()
    {
        super();
        registerInterface(IRichCustomWriteOffEntry.class, this);
    }
    public RichCustomWriteOffEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IRichCustomWriteOffEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B8D6DE07");
    }
    private RichCustomWriteOffEntryController getController() throws BOSException
    {
        return (RichCustomWriteOffEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public RichCustomWriteOffEntryInfo getRichCustomWriteOffEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRichCustomWriteOffEntryInfo(getContext(), pk);
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
    public RichCustomWriteOffEntryInfo getRichCustomWriteOffEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRichCustomWriteOffEntryInfo(getContext(), pk, selector);
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
    public RichCustomWriteOffEntryInfo getRichCustomWriteOffEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRichCustomWriteOffEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RichCustomWriteOffEntryCollection getRichCustomWriteOffEntryCollection() throws BOSException
    {
        try {
            return getController().getRichCustomWriteOffEntryCollection(getContext());
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
    public RichCustomWriteOffEntryCollection getRichCustomWriteOffEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRichCustomWriteOffEntryCollection(getContext(), view);
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
    public RichCustomWriteOffEntryCollection getRichCustomWriteOffEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getRichCustomWriteOffEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}