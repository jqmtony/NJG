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

public class MarketSupplierAttachListTree extends TreeBase implements IMarketSupplierAttachListTree
{
    public MarketSupplierAttachListTree()
    {
        super();
        registerInterface(IMarketSupplierAttachListTree.class, this);
    }
    public MarketSupplierAttachListTree(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierAttachListTree.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("20AADC9F");
    }
    private MarketSupplierAttachListTreeController getController() throws BOSException
    {
        return (MarketSupplierAttachListTreeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MarketSupplierAttachListTreeInfo getMarketSupplierAttachListTreeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierAttachListTreeInfo(getContext(), pk);
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
    public MarketSupplierAttachListTreeInfo getMarketSupplierAttachListTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierAttachListTreeInfo(getContext(), pk, selector);
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
    public MarketSupplierAttachListTreeInfo getMarketSupplierAttachListTreeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierAttachListTreeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarketSupplierAttachListTreeCollection getMarketSupplierAttachListTreeCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierAttachListTreeCollection(getContext());
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
    public MarketSupplierAttachListTreeCollection getMarketSupplierAttachListTreeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierAttachListTreeCollection(getContext(), view);
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
    public MarketSupplierAttachListTreeCollection getMarketSupplierAttachListTreeCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierAttachListTreeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}