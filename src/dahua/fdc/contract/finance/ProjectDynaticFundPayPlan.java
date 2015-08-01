package com.kingdee.eas.fdc.finance;

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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import java.util.Set;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ProjectDynaticFundPayPlan extends FDCBill implements IProjectDynaticFundPayPlan
{
    public ProjectDynaticFundPayPlan()
    {
        super();
        registerInterface(IProjectDynaticFundPayPlan.class, this);
    }
    public ProjectDynaticFundPayPlan(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectDynaticFundPayPlan.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("078223D0");
    }
    private ProjectDynaticFundPayPlanController getController() throws BOSException
    {
        return (ProjectDynaticFundPayPlanController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public ProjectDynaticFundPayPlanInfo getProjectDynaticFundPayPlanInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectDynaticFundPayPlanInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public ProjectDynaticFundPayPlanInfo getProjectDynaticFundPayPlanInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectDynaticFundPayPlanInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql oql
     *@return
     */
    public ProjectDynaticFundPayPlanInfo getProjectDynaticFundPayPlanInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectDynaticFundPayPlanInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值集-System defined method
     *@return
     */
    public ProjectDynaticFundPayPlanCollection getProjectDynaticFundPayPlanCollection() throws BOSException
    {
        try {
            return getController().getProjectDynaticFundPayPlanCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值集-System defined method
     *@param view view
     *@return
     */
    public ProjectDynaticFundPayPlanCollection getProjectDynaticFundPayPlanCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectDynaticFundPayPlanCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值集-System defined method
     *@param oql oql
     *@return
     */
    public ProjectDynaticFundPayPlanCollection getProjectDynaticFundPayPlanCollection(String oql) throws BOSException
    {
        try {
            return getController().getProjectDynaticFundPayPlanCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步-User defined method
     *@param curProjectIds 工程项目id集合
     */
    public void synData(Set curProjectIds) throws BOSException, EASBizException
    {
        try {
            getController().synData(getContext(), curProjectIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}