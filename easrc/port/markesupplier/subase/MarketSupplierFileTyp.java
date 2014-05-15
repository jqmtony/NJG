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

public class MarketSupplierFileTyp extends DataBase implements IMarketSupplierFileTyp
{
    public MarketSupplierFileTyp()
    {
        super();
        registerInterface(IMarketSupplierFileTyp.class, this);
    }
    public MarketSupplierFileTyp(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierFileTyp.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8BFECB51");
    }
    private MarketSupplierFileTypController getController() throws BOSException
    {
        return (MarketSupplierFileTypController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MarketSupplierFileTypInfo getMarketSupplierFileTypInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierFileTypInfo(getContext(), pk);
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
    public MarketSupplierFileTypInfo getMarketSupplierFileTypInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierFileTypInfo(getContext(), pk, selector);
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
    public MarketSupplierFileTypInfo getMarketSupplierFileTypInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierFileTypInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarketSupplierFileTypCollection getMarketSupplierFileTypCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierFileTypCollection(getContext());
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
    public MarketSupplierFileTypCollection getMarketSupplierFileTypCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierFileTypCollection(getContext(), view);
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
    public MarketSupplierFileTypCollection getMarketSupplierFileTypCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierFileTypCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}