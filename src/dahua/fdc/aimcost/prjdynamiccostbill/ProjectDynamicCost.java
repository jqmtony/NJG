package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class ProjectDynamicCost extends CoreBillBase implements IProjectDynamicCost
{
    public ProjectDynamicCost()
    {
        super();
        registerInterface(IProjectDynamicCost.class, this);
    }
    public ProjectDynamicCost(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectDynamicCost.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("75CD9A79");
    }
    private ProjectDynamicCostController getController() throws BOSException
    {
        return (ProjectDynamicCostController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ProjectDynamicCostCollection getProjectDynamicCostCollection() throws BOSException
    {
        try {
            return getController().getProjectDynamicCostCollection(getContext());
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
    public ProjectDynamicCostCollection getProjectDynamicCostCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectDynamicCostCollection(getContext(), view);
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
    public ProjectDynamicCostCollection getProjectDynamicCostCollection(String oql) throws BOSException
    {
        try {
            return getController().getProjectDynamicCostCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ProjectDynamicCostInfo getProjectDynamicCostInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectDynamicCostInfo(getContext(), pk);
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
    public ProjectDynamicCostInfo getProjectDynamicCostInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectDynamicCostInfo(getContext(), pk, selector);
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
    public ProjectDynamicCostInfo getProjectDynamicCostInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectDynamicCostInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审核-User defined method
     *@param model model
     */
    public void audit(ProjectDynamicCostInfo model) throws BOSException
    {
        try {
            getController().audit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审核-User defined method
     *@param model model
     */
    public void unAudit(ProjectDynamicCostInfo model) throws BOSException
    {
        try {
            getController().unAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}