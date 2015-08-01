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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class PayPlanTemplate extends DataBase implements IPayPlanTemplate
{
    public PayPlanTemplate()
    {
        super();
        registerInterface(IPayPlanTemplate.class, this);
    }
    public PayPlanTemplate(Context ctx)
    {
        super(ctx);
        registerInterface(IPayPlanTemplate.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E60DA69C");
    }
    private PayPlanTemplateController getController() throws BOSException
    {
        return (PayPlanTemplateController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PayPlanTemplateInfo getPayPlanTemplateInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPayPlanTemplateInfo(getContext(), pk);
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
    public PayPlanTemplateInfo getPayPlanTemplateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPayPlanTemplateInfo(getContext(), pk, selector);
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
    public PayPlanTemplateInfo getPayPlanTemplateInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPayPlanTemplateInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PayPlanTemplateCollection getPayPlanTemplateCollection() throws BOSException
    {
        try {
            return getController().getPayPlanTemplateCollection(getContext());
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
    public PayPlanTemplateCollection getPayPlanTemplateCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPayPlanTemplateCollection(getContext(), view);
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
    public PayPlanTemplateCollection getPayPlanTemplateCollection(String oql) throws BOSException
    {
        try {
            return getController().getPayPlanTemplateCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}