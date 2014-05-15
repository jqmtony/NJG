package com.kingdee.eas.port.markesupplier.subill;

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
import com.kingdee.eas.port.markesupplier.subill.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class MarketSupplierStockEntry extends CoreBillEntryBase implements IMarketSupplierStockEntry
{
    public MarketSupplierStockEntry()
    {
        super();
        registerInterface(IMarketSupplierStockEntry.class, this);
    }
    public MarketSupplierStockEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierStockEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D4717A64");
    }
    private MarketSupplierStockEntryController getController() throws BOSException
    {
        return (MarketSupplierStockEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MarketSupplierStockEntryInfo getMarketSupplierStockEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockEntryInfo(getContext(), pk);
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
    public MarketSupplierStockEntryInfo getMarketSupplierStockEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockEntryInfo(getContext(), pk, selector);
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
    public MarketSupplierStockEntryInfo getMarketSupplierStockEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierStockEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarketSupplierStockEntryCollection getMarketSupplierStockEntryCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierStockEntryCollection(getContext());
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
    public MarketSupplierStockEntryCollection getMarketSupplierStockEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierStockEntryCollection(getContext(), view);
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
    public MarketSupplierStockEntryCollection getMarketSupplierStockEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierStockEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}