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

public class RichInvoiceRequestEntry extends CoreBillEntryBase implements IRichInvoiceRequestEntry
{
    public RichInvoiceRequestEntry()
    {
        super();
        registerInterface(IRichInvoiceRequestEntry.class, this);
    }
    public RichInvoiceRequestEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IRichInvoiceRequestEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A4EA17A6");
    }
    private RichInvoiceRequestEntryController getController() throws BOSException
    {
        return (RichInvoiceRequestEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public RichInvoiceRequestEntryInfo getRichInvoiceRequestEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRichInvoiceRequestEntryInfo(getContext(), pk);
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
    public RichInvoiceRequestEntryInfo getRichInvoiceRequestEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRichInvoiceRequestEntryInfo(getContext(), pk, selector);
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
    public RichInvoiceRequestEntryInfo getRichInvoiceRequestEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRichInvoiceRequestEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RichInvoiceRequestEntryCollection getRichInvoiceRequestEntryCollection() throws BOSException
    {
        try {
            return getController().getRichInvoiceRequestEntryCollection(getContext());
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
    public RichInvoiceRequestEntryCollection getRichInvoiceRequestEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRichInvoiceRequestEntryCollection(getContext(), view);
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
    public RichInvoiceRequestEntryCollection getRichInvoiceRequestEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getRichInvoiceRequestEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}