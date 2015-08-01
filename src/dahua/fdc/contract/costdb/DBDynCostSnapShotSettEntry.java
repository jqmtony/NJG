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
import com.kingdee.eas.fdc.costdb.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class DBDynCostSnapShotSettEntry extends CoreBase implements IDBDynCostSnapShotSettEntry
{
    public DBDynCostSnapShotSettEntry()
    {
        super();
        registerInterface(IDBDynCostSnapShotSettEntry.class, this);
    }
    public DBDynCostSnapShotSettEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IDBDynCostSnapShotSettEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("286E2AD6");
    }
    private DBDynCostSnapShotSettEntryController getController() throws BOSException
    {
        return (DBDynCostSnapShotSettEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public DBDynCostSnapShotSettEntryInfo getDBDynCostSnapShotSettEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDBDynCostSnapShotSettEntryInfo(getContext(), pk);
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
    public DBDynCostSnapShotSettEntryInfo getDBDynCostSnapShotSettEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDBDynCostSnapShotSettEntryInfo(getContext(), pk, selector);
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
    public DBDynCostSnapShotSettEntryInfo getDBDynCostSnapShotSettEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDBDynCostSnapShotSettEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public DBDynCostSnapShotSettEntryCollection getDBDynCostSnapShotSettEntryCollection() throws BOSException
    {
        try {
            return getController().getDBDynCostSnapShotSettEntryCollection(getContext());
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
    public DBDynCostSnapShotSettEntryCollection getDBDynCostSnapShotSettEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDBDynCostSnapShotSettEntryCollection(getContext(), view);
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
    public DBDynCostSnapShotSettEntryCollection getDBDynCostSnapShotSettEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getDBDynCostSnapShotSettEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}