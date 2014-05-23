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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class MarketSupplierReviewGather extends CoreBillBase implements IMarketSupplierReviewGather
{
    public MarketSupplierReviewGather()
    {
        super();
        registerInterface(IMarketSupplierReviewGather.class, this);
    }
    public MarketSupplierReviewGather(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierReviewGather.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("869980DB");
    }
    private MarketSupplierReviewGatherController getController() throws BOSException
    {
        return (MarketSupplierReviewGatherController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarketSupplierReviewGatherCollection getMarketSupplierReviewGatherCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierReviewGatherCollection(getContext());
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
    public MarketSupplierReviewGatherCollection getMarketSupplierReviewGatherCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierReviewGatherCollection(getContext(), view);
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
    public MarketSupplierReviewGatherCollection getMarketSupplierReviewGatherCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierReviewGatherCollection(getContext(), oql);
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
    public MarketSupplierReviewGatherInfo getMarketSupplierReviewGatherInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierReviewGatherInfo(getContext(), pk);
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
    public MarketSupplierReviewGatherInfo getMarketSupplierReviewGatherInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierReviewGatherInfo(getContext(), pk, selector);
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
    public MarketSupplierReviewGatherInfo getMarketSupplierReviewGatherInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierReviewGatherInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批-User defined method
     *@param model model
     */
    public void audit(MarketSupplierReviewGatherInfo model) throws BOSException
    {
        try {
            getController().audit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批-User defined method
     *@param model model
     */
    public void unAudit(MarketSupplierReviewGatherInfo model) throws BOSException
    {
        try {
            getController().unAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}