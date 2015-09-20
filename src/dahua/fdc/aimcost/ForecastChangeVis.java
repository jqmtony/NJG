package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class ForecastChangeVis extends CoreBillBase implements IForecastChangeVis
{
    public ForecastChangeVis()
    {
        super();
        registerInterface(IForecastChangeVis.class, this);
    }
    public ForecastChangeVis(Context ctx)
    {
        super(ctx);
        registerInterface(IForecastChangeVis.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B4926E1E");
    }
    private ForecastChangeVisController getController() throws BOSException
    {
        return (ForecastChangeVisController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ForecastChangeVisCollection getForecastChangeVisCollection() throws BOSException
    {
        try {
            return getController().getForecastChangeVisCollection(getContext());
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
    public ForecastChangeVisCollection getForecastChangeVisCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getForecastChangeVisCollection(getContext(), view);
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
    public ForecastChangeVisCollection getForecastChangeVisCollection(String oql) throws BOSException
    {
        try {
            return getController().getForecastChangeVisCollection(getContext(), oql);
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
    public ForecastChangeVisInfo getForecastChangeVisInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getForecastChangeVisInfo(getContext(), pk);
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
    public ForecastChangeVisInfo getForecastChangeVisInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getForecastChangeVisInfo(getContext(), pk, selector);
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
    public ForecastChangeVisInfo getForecastChangeVisInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getForecastChangeVisInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批-User defined method
     *@param model model
     */
    public void actionAudit(ForecastChangeVisInfo model) throws BOSException
    {
        try {
            getController().actionAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批-User defined method
     *@param model model
     */
    public void actionUnAudit(ForecastChangeVisInfo model) throws BOSException
    {
        try {
            getController().actionUnAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}