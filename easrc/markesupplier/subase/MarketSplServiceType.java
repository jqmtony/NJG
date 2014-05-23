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

public class MarketSplServiceType extends DataBase implements IMarketSplServiceType
{
    public MarketSplServiceType()
    {
        super();
        registerInterface(IMarketSplServiceType.class, this);
    }
    public MarketSplServiceType(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSplServiceType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("01B92552");
    }
    private MarketSplServiceTypeController getController() throws BOSException
    {
        return (MarketSplServiceTypeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MarketSplServiceTypeInfo getMarketSplServiceTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSplServiceTypeInfo(getContext(), pk);
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
    public MarketSplServiceTypeInfo getMarketSplServiceTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSplServiceTypeInfo(getContext(), pk, selector);
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
    public MarketSplServiceTypeInfo getMarketSplServiceTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSplServiceTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarketSplServiceTypeCollection getMarketSplServiceTypeCollection() throws BOSException
    {
        try {
            return getController().getMarketSplServiceTypeCollection(getContext());
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
    public MarketSplServiceTypeCollection getMarketSplServiceTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSplServiceTypeCollection(getContext(), view);
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
    public MarketSplServiceTypeCollection getMarketSplServiceTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSplServiceTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}