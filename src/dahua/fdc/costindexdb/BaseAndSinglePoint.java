package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.costindexdb.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class BaseAndSinglePoint extends CoreBillBase implements IBaseAndSinglePoint
{
    public BaseAndSinglePoint()
    {
        super();
        registerInterface(IBaseAndSinglePoint.class, this);
    }
    public BaseAndSinglePoint(Context ctx)
    {
        super(ctx);
        registerInterface(IBaseAndSinglePoint.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4B9566A8");
    }
    private BaseAndSinglePointController getController() throws BOSException
    {
        return (BaseAndSinglePointController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BaseAndSinglePointCollection getBaseAndSinglePointCollection() throws BOSException
    {
        try {
            return getController().getBaseAndSinglePointCollection(getContext());
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
    public BaseAndSinglePointCollection getBaseAndSinglePointCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBaseAndSinglePointCollection(getContext(), view);
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
    public BaseAndSinglePointCollection getBaseAndSinglePointCollection(String oql) throws BOSException
    {
        try {
            return getController().getBaseAndSinglePointCollection(getContext(), oql);
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
    public BaseAndSinglePointInfo getBaseAndSinglePointInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBaseAndSinglePointInfo(getContext(), pk);
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
    public BaseAndSinglePointInfo getBaseAndSinglePointInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBaseAndSinglePointInfo(getContext(), pk, selector);
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
    public BaseAndSinglePointInfo getBaseAndSinglePointInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBaseAndSinglePointInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审核-User defined method
     *@param model model
     */
    public void audit(BaseAndSinglePointInfo model) throws BOSException
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
    public void unAdudit(BaseAndSinglePointInfo model) throws BOSException
    {
        try {
            getController().unAdudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *修订-User defined method
     *@param model model
     */
    public void refix(BaseAndSinglePointInfo model) throws BOSException
    {
        try {
            getController().refix(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}