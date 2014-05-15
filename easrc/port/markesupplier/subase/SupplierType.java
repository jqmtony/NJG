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

public class SupplierType extends DataBase implements ISupplierType
{
    public SupplierType()
    {
        super();
        registerInterface(ISupplierType.class, this);
    }
    public SupplierType(Context ctx)
    {
        super(ctx);
        registerInterface(ISupplierType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F592CCBC");
    }
    private SupplierTypeController getController() throws BOSException
    {
        return (SupplierTypeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SupplierTypeInfo getSupplierTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierTypeInfo(getContext(), pk);
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
    public SupplierTypeInfo getSupplierTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierTypeInfo(getContext(), pk, selector);
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
    public SupplierTypeInfo getSupplierTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SupplierTypeCollection getSupplierTypeCollection() throws BOSException
    {
        try {
            return getController().getSupplierTypeCollection(getContext());
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
    public SupplierTypeCollection getSupplierTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSupplierTypeCollection(getContext(), view);
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
    public SupplierTypeCollection getSupplierTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getSupplierTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}