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

public class MarketSupplierAttachList extends DataBase implements IMarketSupplierAttachList
{
    public MarketSupplierAttachList()
    {
        super();
        registerInterface(IMarketSupplierAttachList.class, this);
    }
    public MarketSupplierAttachList(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierAttachList.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("853A8D61");
    }
    private MarketSupplierAttachListController getController() throws BOSException
    {
        return (MarketSupplierAttachListController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public MarketSupplierAttachListInfo getMarketSupplierAttachListInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierAttachListInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public MarketSupplierAttachListInfo getMarketSupplierAttachListInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierAttachListInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public MarketSupplierAttachListInfo getMarketSupplierAttachListInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierAttachListInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public MarketSupplierAttachListCollection getMarketSupplierAttachListCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierAttachListCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public MarketSupplierAttachListCollection getMarketSupplierAttachListCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierAttachListCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public MarketSupplierAttachListCollection getMarketSupplierAttachListCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierAttachListCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}