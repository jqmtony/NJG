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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.port.markesupplier.subase.app.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class SupplierInvoiceTypeTree extends TreeBase implements ISupplierInvoiceTypeTree
{
    public SupplierInvoiceTypeTree()
    {
        super();
        registerInterface(ISupplierInvoiceTypeTree.class, this);
    }
    public SupplierInvoiceTypeTree(Context ctx)
    {
        super(ctx);
        registerInterface(ISupplierInvoiceTypeTree.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A9371183");
    }
    private SupplierInvoiceTypeTreeController getController() throws BOSException
    {
        return (SupplierInvoiceTypeTreeController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public SupplierInvoiceTypeTreeInfo getSupplierInvoiceTypeTreeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierInvoiceTypeTreeInfo(getContext(), pk);
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
    public SupplierInvoiceTypeTreeInfo getSupplierInvoiceTypeTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierInvoiceTypeTreeInfo(getContext(), pk, selector);
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
    public SupplierInvoiceTypeTreeInfo getSupplierInvoiceTypeTreeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierInvoiceTypeTreeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public SupplierInvoiceTypeTreeCollection getSupplierInvoiceTypeTreeCollection() throws BOSException
    {
        try {
            return getController().getSupplierInvoiceTypeTreeCollection(getContext());
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
    public SupplierInvoiceTypeTreeCollection getSupplierInvoiceTypeTreeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSupplierInvoiceTypeTreeCollection(getContext(), view);
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
    public SupplierInvoiceTypeTreeCollection getSupplierInvoiceTypeTreeCollection(String oql) throws BOSException
    {
        try {
            return getController().getSupplierInvoiceTypeTreeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}