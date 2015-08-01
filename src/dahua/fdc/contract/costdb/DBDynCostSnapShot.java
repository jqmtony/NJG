package com.kingdee.eas.fdc.costdb;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ObjectBase;
import com.kingdee.eas.fdc.costdb.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IObjectBase;

public class DBDynCostSnapShot extends ObjectBase implements IDBDynCostSnapShot
{
    public DBDynCostSnapShot()
    {
        super();
        registerInterface(IDBDynCostSnapShot.class, this);
    }
    public DBDynCostSnapShot(Context ctx)
    {
        super(ctx);
        registerInterface(IDBDynCostSnapShot.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FB1EFB8A");
    }
    private DBDynCostSnapShotController getController() throws BOSException
    {
        return (DBDynCostSnapShotController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public DBDynCostSnapShotCollection getDBDynCostSnapShotCollection() throws BOSException
    {
        try {
            return getController().getDBDynCostSnapShotCollection(getContext());
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
    public DBDynCostSnapShotCollection getDBDynCostSnapShotCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDBDynCostSnapShotCollection(getContext(), view);
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
    public DBDynCostSnapShotCollection getDBDynCostSnapShotCollection(String oql) throws BOSException
    {
        try {
            return getController().getDBDynCostSnapShotCollection(getContext(), oql);
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
    public DBDynCostSnapShotInfo getDBDynCostSnapShotInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDBDynCostSnapShotInfo(getContext(), pk);
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
    public DBDynCostSnapShotInfo getDBDynCostSnapShotInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDBDynCostSnapShotInfo(getContext(), pk, selector);
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
    public DBDynCostSnapShotInfo getDBDynCostSnapShotInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDBDynCostSnapShotInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}