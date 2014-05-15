package com.kingdee.eas.port.pm.project;

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
import com.kingdee.eas.port.pm.project.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class PortProject extends TreeBase implements IPortProject
{
    public PortProject()
    {
        super();
        registerInterface(IPortProject.class, this);
    }
    public PortProject(Context ctx)
    {
        super(ctx);
        registerInterface(IPortProject.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6752A6CD");
    }
    private PortProjectController getController() throws BOSException
    {
        return (PortProjectController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PortProjectInfo getPortProjectInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPortProjectInfo(getContext(), pk);
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
    public PortProjectInfo getPortProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPortProjectInfo(getContext(), pk, selector);
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
    public PortProjectInfo getPortProjectInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPortProjectInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PortProjectCollection getPortProjectCollection() throws BOSException
    {
        try {
            return getController().getPortProjectCollection(getContext());
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
    public PortProjectCollection getPortProjectCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPortProjectCollection(getContext(), view);
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
    public PortProjectCollection getPortProjectCollection(String oql) throws BOSException
    {
        try {
            return getController().getPortProjectCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}