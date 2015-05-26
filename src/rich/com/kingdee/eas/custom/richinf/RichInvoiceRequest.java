package com.kingdee.eas.custom.richinf;

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
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.custom.richinf.app.*;

public class RichInvoiceRequest extends CoreBillBase implements IRichInvoiceRequest
{
    public RichInvoiceRequest()
    {
        super();
        registerInterface(IRichInvoiceRequest.class, this);
    }
    public RichInvoiceRequest(Context ctx)
    {
        super(ctx);
        registerInterface(IRichInvoiceRequest.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4140BC2C");
    }
    private RichInvoiceRequestController getController() throws BOSException
    {
        return (RichInvoiceRequestController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RichInvoiceRequestCollection getRichInvoiceRequestCollection() throws BOSException
    {
        try {
            return getController().getRichInvoiceRequestCollection(getContext());
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
    public RichInvoiceRequestCollection getRichInvoiceRequestCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRichInvoiceRequestCollection(getContext(), view);
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
    public RichInvoiceRequestCollection getRichInvoiceRequestCollection(String oql) throws BOSException
    {
        try {
            return getController().getRichInvoiceRequestCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public RichInvoiceRequestInfo getRichInvoiceRequestInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRichInvoiceRequestInfo(getContext(), pk);
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
    public RichInvoiceRequestInfo getRichInvoiceRequestInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRichInvoiceRequestInfo(getContext(), pk, selector);
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
    public RichInvoiceRequestInfo getRichInvoiceRequestInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRichInvoiceRequestInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}