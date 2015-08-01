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

public class PayPlanByMonthBase extends CoreBillEntryBase implements IPayPlanByMonthBase
{
    public PayPlanByMonthBase()
    {
        super();
        registerInterface(IPayPlanByMonthBase.class, this);
    }
    public PayPlanByMonthBase(Context ctx)
    {
        super(ctx);
        registerInterface(IPayPlanByMonthBase.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C549BD78");
    }
    private PayPlanByMonthBaseController getController() throws BOSException
    {
        return (PayPlanByMonthBaseController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PayPlanByMonthBaseInfo getPayPlanByMonthBaseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPayPlanByMonthBaseInfo(getContext(), pk);
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
    public PayPlanByMonthBaseInfo getPayPlanByMonthBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPayPlanByMonthBaseInfo(getContext(), pk, selector);
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
    public PayPlanByMonthBaseInfo getPayPlanByMonthBaseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPayPlanByMonthBaseInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PayPlanByMonthBaseCollection getPayPlanByMonthBaseCollection() throws BOSException
    {
        try {
            return getController().getPayPlanByMonthBaseCollection(getContext());
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
    public PayPlanByMonthBaseCollection getPayPlanByMonthBaseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPayPlanByMonthBaseCollection(getContext(), view);
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
    public PayPlanByMonthBaseCollection getPayPlanByMonthBaseCollection(String oql) throws BOSException
    {
        try {
            return getController().getPayPlanByMonthBaseCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}