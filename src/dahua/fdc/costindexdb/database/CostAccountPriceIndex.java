package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.costindexdb.database.app.*;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class CostAccountPriceIndex extends DataBase implements ICostAccountPriceIndex
{
    public CostAccountPriceIndex()
    {
        super();
        registerInterface(ICostAccountPriceIndex.class, this);
    }
    public CostAccountPriceIndex(Context ctx)
    {
        super(ctx);
        registerInterface(ICostAccountPriceIndex.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F4C16702");
    }
    private CostAccountPriceIndexController getController() throws BOSException
    {
        return (CostAccountPriceIndexController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public CostAccountPriceIndexInfo getCostAccountPriceIndexInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCostAccountPriceIndexInfo(getContext(), pk);
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
    public CostAccountPriceIndexInfo getCostAccountPriceIndexInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCostAccountPriceIndexInfo(getContext(), pk, selector);
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
    public CostAccountPriceIndexInfo getCostAccountPriceIndexInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCostAccountPriceIndexInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public CostAccountPriceIndexCollection getCostAccountPriceIndexCollection() throws BOSException
    {
        try {
            return getController().getCostAccountPriceIndexCollection(getContext());
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
    public CostAccountPriceIndexCollection getCostAccountPriceIndexCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCostAccountPriceIndexCollection(getContext(), view);
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
    public CostAccountPriceIndexCollection getCostAccountPriceIndexCollection(String oql) throws BOSException
    {
        try {
            return getController().getCostAccountPriceIndexCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}