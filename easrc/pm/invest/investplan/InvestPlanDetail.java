package com.kingdee.eas.port.pm.invest.investplan;

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
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.port.pm.invest.investplan.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class InvestPlanDetail extends CoreBillBase implements IInvestPlanDetail
{
    public InvestPlanDetail()
    {
        super();
        registerInterface(IInvestPlanDetail.class, this);
    }
    public InvestPlanDetail(Context ctx)
    {
        super(ctx);
        registerInterface(IInvestPlanDetail.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9613BABC");
    }
    private InvestPlanDetailController getController() throws BOSException
    {
        return (InvestPlanDetailController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public InvestPlanDetailCollection getInvestPlanDetailCollection() throws BOSException
    {
        try {
            return getController().getInvestPlanDetailCollection(getContext());
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
    public InvestPlanDetailCollection getInvestPlanDetailCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInvestPlanDetailCollection(getContext(), view);
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
    public InvestPlanDetailCollection getInvestPlanDetailCollection(String oql) throws BOSException
    {
        try {
            return getController().getInvestPlanDetailCollection(getContext(), oql);
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
    public InvestPlanDetailInfo getInvestPlanDetailInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInvestPlanDetailInfo(getContext(), pk);
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
    public InvestPlanDetailInfo getInvestPlanDetailInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInvestPlanDetailInfo(getContext(), pk, selector);
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
    public InvestPlanDetailInfo getInvestPlanDetailInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInvestPlanDetailInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}