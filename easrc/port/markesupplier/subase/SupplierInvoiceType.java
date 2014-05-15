package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.port.markesupplier.subase.app.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class SupplierInvoiceType extends DataBase implements ISupplierInvoiceType
{
    public SupplierInvoiceType()
    {
        super();
        registerInterface(ISupplierInvoiceType.class, this);
    }
    public SupplierInvoiceType(Context ctx)
    {
        super(ctx);
        registerInterface(ISupplierInvoiceType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BFCCD445");
    }
    private SupplierInvoiceTypeController getController() throws BOSException
    {
        return (SupplierInvoiceTypeController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public SupplierInvoiceTypeInfo getSupplierInvoiceTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierInvoiceTypeInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public SupplierInvoiceTypeInfo getSupplierInvoiceTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierInvoiceTypeInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public SupplierInvoiceTypeInfo getSupplierInvoiceTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierInvoiceTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public SupplierInvoiceTypeCollection getSupplierInvoiceTypeCollection() throws BOSException
    {
        try {
            return getController().getSupplierInvoiceTypeCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public SupplierInvoiceTypeCollection getSupplierInvoiceTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSupplierInvoiceTypeCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public SupplierInvoiceTypeCollection getSupplierInvoiceTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getSupplierInvoiceTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}