package com.kingdee.eas.port.pm.qa;

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
import com.kingdee.eas.port.pm.qa.app.*;
import com.kingdee.eas.framework.IDataBase;

public class OAType extends DataBase implements IOAType
{
    public OAType()
    {
        super();
        registerInterface(IOAType.class, this);
    }
    public OAType(Context ctx)
    {
        super(ctx);
        registerInterface(IOAType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("398C90AE");
    }
    private OATypeController getController() throws BOSException
    {
        return (OATypeController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public OATypeInfo getOATypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOATypeInfo(getContext(), pk);
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
    public OATypeInfo getOATypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOATypeInfo(getContext(), pk, selector);
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
    public OATypeInfo getOATypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOATypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public OATypeCollection getOATypeCollection() throws BOSException
    {
        try {
            return getController().getOATypeCollection(getContext());
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
    public OATypeCollection getOATypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOATypeCollection(getContext(), view);
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
    public OATypeCollection getOATypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getOATypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}