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

public class MarketSupplierReviewGatherEntry extends CoreBillEntryBase implements IMarketSupplierReviewGatherEntry
{
    public MarketSupplierReviewGatherEntry()
    {
        super();
        registerInterface(IMarketSupplierReviewGatherEntry.class, this);
    }
    public MarketSupplierReviewGatherEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierReviewGatherEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("970BE857");
    }
    private MarketSupplierReviewGatherEntryController getController() throws BOSException
    {
        return (MarketSupplierReviewGatherEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MarketSupplierReviewGatherEntryInfo getMarketSupplierReviewGatherEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierReviewGatherEntryInfo(getContext(), pk);
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
    public MarketSupplierReviewGatherEntryInfo getMarketSupplierReviewGatherEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierReviewGatherEntryInfo(getContext(), pk, selector);
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
    public MarketSupplierReviewGatherEntryInfo getMarketSupplierReviewGatherEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierReviewGatherEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarketSupplierReviewGatherEntryCollection getMarketSupplierReviewGatherEntryCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierReviewGatherEntryCollection(getContext());
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
    public MarketSupplierReviewGatherEntryCollection getMarketSupplierReviewGatherEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierReviewGatherEntryCollection(getContext(), view);
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
    public MarketSupplierReviewGatherEntryCollection getMarketSupplierReviewGatherEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierReviewGatherEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}