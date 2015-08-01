package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.bos.dao.IObjectCollection;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.RetValue;

public class PaymentSplitFacade extends AbstractBizCtrl implements IPaymentSplitFacade
{
    public PaymentSplitFacade()
    {
        super();
        registerInterface(IPaymentSplitFacade.class, this);
    }
    public PaymentSplitFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IPaymentSplitFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D11591BD");
    }
    private PaymentSplitFacadeController getController() throws BOSException
    {
        return (PaymentSplitFacadeController)getBizController();
    }
    /**
     *付款明细表取数-User defined method
     *@param paramValue paramValue
     *@return
     */
    public RetValue getPaymentSplit(ParamValue paramValue) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentSplit(getContext(), paramValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *工程实际投入明细表-User defined method
     *@param paramValue 参数
     *@return
     */
    public RetValue getWorkLoadConfirmSplitDatas(Map paramValue) throws BOSException, EASBizException
    {
        try {
            return getController().getWorkLoadConfirmSplitDatas(getContext(), paramValue);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *这个方法是为了提高性能做的不太易用,可支持其他使用不太高,慎用 -User defined method
     *@param enumConIdsMap 根据类型存储的合同ID的MAP
     *@param isAdd 是否根据科目合并金额值科目上
     *@param selector 值太多性能会差，所以要限制
     *@param costIdandConIdMap 拆分单据对应拆分来源MAP
     *@param transSelector 转型分录用到的selector
     *@param spiltClass 不同拆分类型的拆分类
     *@return
     */
    public IObjectCollection getPaymentSplitEntryByParam(Map enumConIdsMap, boolean isAdd, IObjectCollection selector, Map costIdandConIdMap, Map transSelector, IObjectValue spiltClass) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentSplitEntryByParam(getContext(), enumConIdsMap, isAdd, selector, costIdandConIdMap, transSelector, spiltClass);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}