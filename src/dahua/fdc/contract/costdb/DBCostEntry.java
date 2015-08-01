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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.costdb.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class DBCostEntry extends DataBase implements IDBCostEntry
{
    public DBCostEntry()
    {
        super();
        registerInterface(IDBCostEntry.class, this);
    }
    public DBCostEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IDBCostEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("22A4C645");
    }
    private DBCostEntryController getController() throws BOSException
    {
        return (DBCostEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public DBCostEntryInfo getDBCostEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDBCostEntryInfo(getContext(), pk);
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
    public DBCostEntryInfo getDBCostEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDBCostEntryInfo(getContext(), pk, selector);
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
    public DBCostEntryInfo getDBCostEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDBCostEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public DBCostEntryCollection getDBCostEntryCollection() throws BOSException
    {
        try {
            return getController().getDBCostEntryCollection(getContext());
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
    public DBCostEntryCollection getDBCostEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDBCostEntryCollection(getContext(), view);
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
    public DBCostEntryCollection getDBCostEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getDBCostEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}