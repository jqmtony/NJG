package com.kingdee.eas.port.markesupplier.subill;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.port.markesupplier.subill.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class MarketSupplierStock extends CoreBillBase implements IMarketSupplierStock
{
    public MarketSupplierStock()
    {
        super();
        registerInterface(IMarketSupplierStock.class, this);
    }
    public MarketSupplierStock(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierStock.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5ABA1AAE");
    }
    private MarketSupplierStockController getController() throws BOSException
    {
        return (MarketSupplierStockController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarketSupplierStockCollection getMarketSupplierStockCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierStockCollection(getContext());
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
    public MarketSupplierStockCollection getMarketSupplierStockCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierStockCollection(getContext(), view);
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
    public MarketSupplierStockCollection getMarketSupplierStockCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierStockCollection(getContext(), oql);
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
    public MarketSupplierStockInfo getMarketSupplierStockInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockInfo(getContext(), pk);
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
    public MarketSupplierStockInfo getMarketSupplierStockInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockInfo(getContext(), pk, selector);
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
    public MarketSupplierStockInfo getMarketSupplierStockInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *核准-User defined method
     *@param model model
     */
    public void audit(MarketSupplierStockInfo model) throws BOSException
    {
        try {
            getController().audit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反核准-User defined method
     *@param model model
     */
    public void unAudit(MarketSupplierStockInfo model) throws BOSException
    {
        try {
            getController().unAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步主数据-User defined method
     *@param objectValue objectValue
     */
    public void addToSysSupplier(IObjectValue objectValue) throws BOSException, EASBizException
    {
        try {
            getController().addToSysSupplier(getContext(), objectValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}