package com.kingdee.eas.port.equipment.base;

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
import com.kingdee.eas.port.equipment.base.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class InitialConnection extends DataBase implements IInitialConnection
{
    public InitialConnection()
    {
        super();
        registerInterface(IInitialConnection.class, this);
    }
    public InitialConnection(Context ctx)
    {
        super(ctx);
        registerInterface(IInitialConnection.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E6B39740");
    }
    private InitialConnectionController getController() throws BOSException
    {
        return (InitialConnectionController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public InitialConnectionInfo getInitialConnectionInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInitialConnectionInfo(getContext(), pk);
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
    public InitialConnectionInfo getInitialConnectionInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInitialConnectionInfo(getContext(), pk, selector);
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
    public InitialConnectionInfo getInitialConnectionInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInitialConnectionInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public InitialConnectionCollection getInitialConnectionCollection() throws BOSException
    {
        try {
            return getController().getInitialConnectionCollection(getContext());
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
    public InitialConnectionCollection getInitialConnectionCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInitialConnectionCollection(getContext(), view);
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
    public InitialConnectionCollection getInitialConnectionCollection(String oql) throws BOSException
    {
        try {
            return getController().getInitialConnectionCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}