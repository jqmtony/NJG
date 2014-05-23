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

public class ProjectFee extends TreeBase implements IProjectFee
{
    public ProjectFee()
    {
        super();
        registerInterface(IProjectFee.class, this);
    }
    public ProjectFee(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectFee.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("50324398");
    }
    private ProjectFeeController getController() throws BOSException
    {
        return (ProjectFeeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ProjectFeeInfo getProjectFeeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectFeeInfo(getContext(), pk);
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
    public ProjectFeeInfo getProjectFeeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectFeeInfo(getContext(), pk, selector);
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
    public ProjectFeeInfo getProjectFeeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectFeeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ProjectFeeCollection getProjectFeeCollection() throws BOSException
    {
        try {
            return getController().getProjectFeeCollection(getContext());
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
    public ProjectFeeCollection getProjectFeeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectFeeCollection(getContext(), view);
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
    public ProjectFeeCollection getProjectFeeCollection(String oql) throws BOSException
    {
        try {
            return getController().getProjectFeeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}