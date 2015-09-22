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

public class BasePointTemp extends DataBase implements IBasePointTemp
{
    public BasePointTemp()
    {
        super();
        registerInterface(IBasePointTemp.class, this);
    }
    public BasePointTemp(Context ctx)
    {
        super(ctx);
        registerInterface(IBasePointTemp.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D7E1E6EC");
    }
    private BasePointTempController getController() throws BOSException
    {
        return (BasePointTempController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public BasePointTempInfo getBasePointTempInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBasePointTempInfo(getContext(), pk);
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
    public BasePointTempInfo getBasePointTempInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBasePointTempInfo(getContext(), pk, selector);
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
    public BasePointTempInfo getBasePointTempInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBasePointTempInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BasePointTempCollection getBasePointTempCollection() throws BOSException
    {
        try {
            return getController().getBasePointTempCollection(getContext());
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
    public BasePointTempCollection getBasePointTempCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBasePointTempCollection(getContext(), view);
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
    public BasePointTempCollection getBasePointTempCollection(String oql) throws BOSException
    {
        try {
            return getController().getBasePointTempCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}