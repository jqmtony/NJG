package com.kingdee.eas.fdc.contract;

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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class PaymentLayout extends FDCBill implements IPaymentLayout
{
    public PaymentLayout()
    {
        super();
        registerInterface(IPaymentLayout.class, this);
    }
    public PaymentLayout(Context ctx)
    {
        super(ctx);
        registerInterface(IPaymentLayout.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("08C83275");
    }
    private PaymentLayoutController getController() throws BOSException
    {
        return (PaymentLayoutController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PaymentLayoutInfo getPaymentLayoutInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentLayoutInfo(getContext(), pk);
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
    public PaymentLayoutInfo getPaymentLayoutInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentLayoutInfo(getContext(), pk, selector);
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
    public PaymentLayoutInfo getPaymentLayoutInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentLayoutInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PaymentLayoutCollection getPaymentLayoutCollection() throws BOSException
    {
        try {
            return getController().getPaymentLayoutCollection(getContext());
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
    public PaymentLayoutCollection getPaymentLayoutCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPaymentLayoutCollection(getContext(), view);
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
    public PaymentLayoutCollection getPaymentLayoutCollection(String oql) throws BOSException
    {
        try {
            return getController().getPaymentLayoutCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}