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

public class MarketSplArea extends DataBase implements IMarketSplArea
{
    public MarketSplArea()
    {
        super();
        registerInterface(IMarketSplArea.class, this);
    }
    public MarketSplArea(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSplArea.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9E9AB82A");
    }
    private MarketSplAreaController getController() throws BOSException
    {
        return (MarketSplAreaController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MarketSplAreaInfo getMarketSplAreaInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSplAreaInfo(getContext(), pk);
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
    public MarketSplAreaInfo getMarketSplAreaInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSplAreaInfo(getContext(), pk, selector);
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
    public MarketSplAreaInfo getMarketSplAreaInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSplAreaInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarketSplAreaCollection getMarketSplAreaCollection() throws BOSException
    {
        try {
            return getController().getMarketSplAreaCollection(getContext());
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
    public MarketSplAreaCollection getMarketSplAreaCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSplAreaCollection(getContext(), view);
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
    public MarketSplAreaCollection getMarketSplAreaCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSplAreaCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}