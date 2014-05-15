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

public class MarketSupplierBusinessMode extends DataBase implements IMarketSupplierBusinessMode
{
    public MarketSupplierBusinessMode()
    {
        super();
        registerInterface(IMarketSupplierBusinessMode.class, this);
    }
    public MarketSupplierBusinessMode(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierBusinessMode.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("863FF2E1");
    }
    private MarketSupplierBusinessModeController getController() throws BOSException
    {
        return (MarketSupplierBusinessModeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MarketSupplierBusinessModeInfo getMarketSupplierBusinessModeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierBusinessModeInfo(getContext(), pk);
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
    public MarketSupplierBusinessModeInfo getMarketSupplierBusinessModeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierBusinessModeInfo(getContext(), pk, selector);
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
    public MarketSupplierBusinessModeInfo getMarketSupplierBusinessModeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierBusinessModeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarketSupplierBusinessModeCollection getMarketSupplierBusinessModeCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierBusinessModeCollection(getContext());
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
    public MarketSupplierBusinessModeCollection getMarketSupplierBusinessModeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierBusinessModeCollection(getContext(), view);
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
    public MarketSupplierBusinessModeCollection getMarketSupplierBusinessModeCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierBusinessModeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}