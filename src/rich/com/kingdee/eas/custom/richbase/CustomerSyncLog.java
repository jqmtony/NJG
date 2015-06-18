package com.kingdee.eas.custom.richbase;

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
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.custom.richbase.app.*;
import com.kingdee.eas.framework.IDataBase;

public class CustomerSyncLog extends DataBase implements ICustomerSyncLog
{
    public CustomerSyncLog()
    {
        super();
        registerInterface(ICustomerSyncLog.class, this);
    }
    public CustomerSyncLog(Context ctx)
    {
        super(ctx);
        registerInterface(ICustomerSyncLog.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("90108935");
    }
    private CustomerSyncLogController getController() throws BOSException
    {
        return (CustomerSyncLogController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public CustomerSyncLogInfo getCustomerSyncLogInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerSyncLogInfo(getContext(), pk);
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
    public CustomerSyncLogInfo getCustomerSyncLogInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerSyncLogInfo(getContext(), pk, selector);
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
    public CustomerSyncLogInfo getCustomerSyncLogInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerSyncLogInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public CustomerSyncLogCollection getCustomerSyncLogCollection() throws BOSException
    {
        try {
            return getController().getCustomerSyncLogCollection(getContext());
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
    public CustomerSyncLogCollection getCustomerSyncLogCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCustomerSyncLogCollection(getContext(), view);
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
    public CustomerSyncLogCollection getCustomerSyncLogCollection(String oql) throws BOSException
    {
        try {
            return getController().getCustomerSyncLogCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步客户信息-User defined method
     *@return
     */
    public String syncCustomer() throws BOSException
    {
        try {
            return getController().syncCustomer(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}