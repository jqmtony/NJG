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

public class ReceType extends DataBase implements IReceType
{
    public ReceType()
    {
        super();
        registerInterface(IReceType.class, this);
    }
    public ReceType(Context ctx)
    {
        super(ctx);
        registerInterface(IReceType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0C73A9A5");
    }
    private ReceTypeController getController() throws BOSException
    {
        return (ReceTypeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ReceTypeInfo getReceTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getReceTypeInfo(getContext(), pk);
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
    public ReceTypeInfo getReceTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getReceTypeInfo(getContext(), pk, selector);
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
    public ReceTypeInfo getReceTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getReceTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ReceTypeCollection getReceTypeCollection() throws BOSException
    {
        try {
            return getController().getReceTypeCollection(getContext());
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
    public ReceTypeCollection getReceTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getReceTypeCollection(getContext(), view);
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
    public ReceTypeCollection getReceTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getReceTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}