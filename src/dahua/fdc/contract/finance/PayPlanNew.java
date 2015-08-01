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
import java.util.List;
import com.kingdee.eas.framework.IDataBase;

public class PayPlanNew extends DataBase implements IPayPlanNew
{
    public PayPlanNew()
    {
        super();
        registerInterface(IPayPlanNew.class, this);
    }
    public PayPlanNew(Context ctx)
    {
        super(ctx);
        registerInterface(IPayPlanNew.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A633823E");
    }
    private PayPlanNewController getController() throws BOSException
    {
        return (PayPlanNewController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public PayPlanNewInfo getPayPlanNewInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPayPlanNewInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public PayPlanNewInfo getPayPlanNewInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPayPlanNewInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public PayPlanNewInfo getPayPlanNewInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPayPlanNewInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public PayPlanNewCollection getPayPlanNewCollection() throws BOSException
    {
        try {
            return getController().getPayPlanNewCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public PayPlanNewCollection getPayPlanNewCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPayPlanNewCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public PayPlanNewCollection getPayPlanNewCollection(String oql) throws BOSException
    {
        try {
            return getController().getPayPlanNewCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����-User defined method
     *@param info info
     *@return
     */
    public PayPlanNewInfo caculate(PayPlanNewInfo info) throws BOSException, EASBizException
    {
        try {
            return getController().caculate(getContext(), info);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���ȱ��ʱ����-User defined method
     *@param scheduleId ����id
     */
    public void onScheduleChange(String scheduleId) throws BOSException, EASBizException
    {
        try {
            getController().onScheduleChange(getContext(), scheduleId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���¸���滮-User defined method
     *@param payPlanNewId payPlanNewId
     */
    public void updateBySchedule(String payPlanNewId) throws BOSException, EASBizException
    {
        try {
            getController().updateBySchedule(getContext(), payPlanNewId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *����ǰ���-User defined method
     *@param info info
     */
    public void checkForCalculate(PayPlanNewInfo info) throws BOSException, EASBizException
    {
        try {
            getController().checkForCalculate(getContext(), info);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *��ȡ���������Ľ��ȼ���-User defined method
     *@param projectID projectID
     *@return
     */
    public List getFDCSchTasksListByProjectID(String projectID) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSchTasksListByProjectID(getContext(), projectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}