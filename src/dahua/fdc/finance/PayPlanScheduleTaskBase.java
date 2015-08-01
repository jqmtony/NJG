package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class PayPlanScheduleTaskBase extends CoreBillEntryBase implements IPayPlanScheduleTaskBase
{
    public PayPlanScheduleTaskBase()
    {
        super();
        registerInterface(IPayPlanScheduleTaskBase.class, this);
    }
    public PayPlanScheduleTaskBase(Context ctx)
    {
        super(ctx);
        registerInterface(IPayPlanScheduleTaskBase.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B1E16DCF");
    }
    private PayPlanScheduleTaskBaseController getController() throws BOSException
    {
        return (PayPlanScheduleTaskBaseController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PayPlanScheduleTaskBaseInfo getPayPlanScheduleTaskBaseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPayPlanScheduleTaskBaseInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public PayPlanScheduleTaskBaseInfo getPayPlanScheduleTaskBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPayPlanScheduleTaskBaseInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public PayPlanScheduleTaskBaseInfo getPayPlanScheduleTaskBaseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPayPlanScheduleTaskBaseInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public PayPlanScheduleTaskBaseCollection getPayPlanScheduleTaskBaseCollection(String oql) throws BOSException
    {
        try {
            return getController().getPayPlanScheduleTaskBaseCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public PayPlanScheduleTaskBaseCollection getPayPlanScheduleTaskBaseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPayPlanScheduleTaskBaseCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PayPlanScheduleTaskBaseCollection getPayPlanScheduleTaskBaseCollection() throws BOSException
    {
        try {
            return getController().getPayPlanScheduleTaskBaseCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}