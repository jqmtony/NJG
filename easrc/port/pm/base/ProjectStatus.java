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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.port.pm.base.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class ProjectStatus extends DataBase implements IProjectStatus
{
    public ProjectStatus()
    {
        super();
        registerInterface(IProjectStatus.class, this);
    }
    public ProjectStatus(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectStatus.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EB0CF1CA");
    }
    private ProjectStatusController getController() throws BOSException
    {
        return (ProjectStatusController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ProjectStatusInfo getProjectStatusInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectStatusInfo(getContext(), pk);
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
    public ProjectStatusInfo getProjectStatusInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectStatusInfo(getContext(), pk, selector);
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
    public ProjectStatusInfo getProjectStatusInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectStatusInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ProjectStatusCollection getProjectStatusCollection() throws BOSException
    {
        try {
            return getController().getProjectStatusCollection(getContext());
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
    public ProjectStatusCollection getProjectStatusCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectStatusCollection(getContext(), view);
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
    public ProjectStatusCollection getProjectStatusCollection(String oql) throws BOSException
    {
        try {
            return getController().getProjectStatusCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}