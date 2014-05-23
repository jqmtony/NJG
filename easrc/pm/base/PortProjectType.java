package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.port.pm.base.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class PortProjectType extends TreeBase implements IPortProjectType
{
    public PortProjectType()
    {
        super();
        registerInterface(IPortProjectType.class, this);
    }
    public PortProjectType(Context ctx)
    {
        super(ctx);
        registerInterface(IPortProjectType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("41C50451");
    }
    private PortProjectTypeController getController() throws BOSException
    {
        return (PortProjectTypeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PortProjectTypeInfo getPortProjectTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPortProjectTypeInfo(getContext(), pk);
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
    public PortProjectTypeInfo getPortProjectTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPortProjectTypeInfo(getContext(), pk, selector);
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
    public PortProjectTypeInfo getPortProjectTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPortProjectTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PortProjectTypeCollection getPortProjectTypeCollection() throws BOSException
    {
        try {
            return getController().getPortProjectTypeCollection(getContext());
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
    public PortProjectTypeCollection getPortProjectTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPortProjectTypeCollection(getContext(), view);
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
    public PortProjectTypeCollection getPortProjectTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getPortProjectTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}