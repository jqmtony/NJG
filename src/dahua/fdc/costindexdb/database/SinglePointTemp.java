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

public class SinglePointTemp extends DataBase implements ISinglePointTemp
{
    public SinglePointTemp()
    {
        super();
        registerInterface(ISinglePointTemp.class, this);
    }
    public SinglePointTemp(Context ctx)
    {
        super(ctx);
        registerInterface(ISinglePointTemp.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("66F13515");
    }
    private SinglePointTempController getController() throws BOSException
    {
        return (SinglePointTempController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SinglePointTempInfo getSinglePointTempInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSinglePointTempInfo(getContext(), pk);
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
    public SinglePointTempInfo getSinglePointTempInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSinglePointTempInfo(getContext(), pk, selector);
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
    public SinglePointTempInfo getSinglePointTempInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSinglePointTempInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SinglePointTempCollection getSinglePointTempCollection() throws BOSException
    {
        try {
            return getController().getSinglePointTempCollection(getContext());
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
    public SinglePointTempCollection getSinglePointTempCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSinglePointTempCollection(getContext(), view);
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
    public SinglePointTempCollection getSinglePointTempCollection(String oql) throws BOSException
    {
        try {
            return getController().getSinglePointTempCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}