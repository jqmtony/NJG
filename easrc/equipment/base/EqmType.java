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

public class EqmType extends DataBase implements IEqmType
{
    public EqmType()
    {
        super();
        registerInterface(IEqmType.class, this);
    }
    public EqmType(Context ctx)
    {
        super(ctx);
        registerInterface(IEqmType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("05DCF399");
    }
    private EqmTypeController getController() throws BOSException
    {
        return (EqmTypeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public EqmTypeInfo getEqmTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getEqmTypeInfo(getContext(), pk);
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
    public EqmTypeInfo getEqmTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getEqmTypeInfo(getContext(), pk, selector);
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
    public EqmTypeInfo getEqmTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getEqmTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public EqmTypeCollection getEqmTypeCollection() throws BOSException
    {
        try {
            return getController().getEqmTypeCollection(getContext());
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
    public EqmTypeCollection getEqmTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getEqmTypeCollection(getContext(), view);
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
    public EqmTypeCollection getEqmTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getEqmTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}