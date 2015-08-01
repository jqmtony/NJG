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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ConPayPlan extends FDCBill implements IConPayPlan
{
    public ConPayPlan()
    {
        super();
        registerInterface(IConPayPlan.class, this);
    }
    public ConPayPlan(Context ctx)
    {
        super(ctx);
        registerInterface(IConPayPlan.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D2099A5E");
    }
    private ConPayPlanController getController() throws BOSException
    {
        return (ConPayPlanController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ConPayPlanInfo getConPayPlanInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getConPayPlanInfo(getContext(), pk);
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
    public ConPayPlanInfo getConPayPlanInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getConPayPlanInfo(getContext(), pk, selector);
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
    public ConPayPlanInfo getConPayPlanInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getConPayPlanInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ConPayPlanCollection getConPayPlanCollection() throws BOSException
    {
        try {
            return getController().getConPayPlanCollection(getContext());
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
    public ConPayPlanCollection getConPayPlanCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getConPayPlanCollection(getContext(), view);
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
    public ConPayPlanCollection getConPayPlanCollection(String oql) throws BOSException
    {
        try {
            return getController().getConPayPlanCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *计算-User defined method
     *@param info info
     *@return
     */
    public ConPayPlanInfo caculate(ConPayPlanInfo info) throws BOSException, EASBizException
    {
        try {
            return getController().caculate(getContext(), info);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *导入付款规划-User defined method
     *@param contractBillId 合同ID
     *@param isFromSch 是否进度任务审批
     *@return
     */
    public IObjectPK importPayPlan(String contractBillId, boolean isFromSch) throws BOSException, EASBizException
    {
        try {
            return getController().importPayPlan(getContext(), contractBillId, isFromSch);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *进度变更时更新-User defined method
     *@param scheduleId scheduleId
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
     *月结-User defined method
     *@param info info
     */
    public void settleByMonth(ConPayPlanInfo info) throws BOSException
    {
        try {
            getController().settleByMonth(getContext(), info);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *计算付款规划未签订-User defined method
     *@param settleMonth 月结
     *@param programmingID 合约规划id
     */
    public void calPayPlanUnsign(int settleMonth, String programmingID) throws BOSException, EASBizException
    {
        try {
            getController().calPayPlanUnsign(getContext(), settleMonth, programmingID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}