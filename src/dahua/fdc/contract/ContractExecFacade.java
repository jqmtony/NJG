package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;
import java.util.Set;

public class ContractExecFacade extends AbstractBizCtrl implements IContractExecFacade
{
    public ContractExecFacade()
    {
        super();
        registerInterface(IContractExecFacade.class, this);
    }
    public ContractExecFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IContractExecFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4014ACB8");
    }
    private ContractExecFacadeController getController() throws BOSException
    {
        return (ContractExecFacadeController)getBizController();
    }
    /**
     *长城要求：在合同执行情况表最后一列后增加一列“累计已完工工程量”-User defined method
     *@param fullOrgUnitID 实体财务组织ID
     *@param idSet 合同ID集合
     *@return
     */
    public Map _getCompleteAmt(String fullOrgUnitID, Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController()._getCompleteAmt(getContext(), fullOrgUnitID, idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *长城要求在"合同执行情况表"里显示"累计已完工工程量"-User defined method
     *@param idSet 合同ID集合
     *@return
     */
    public Map _getCompletePrjAmtForNoTextContract(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController()._getCompletePrjAmtForNoTextContract(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *累计已完工工程量取数方法-User defined method
     *@param orgId2ContractIdSet key为组织id， value为合同id的集合
     *@return
     */
    public Map getCompleteAmt(Map orgId2ContractIdSet) throws BOSException, EASBizException
    {
        try {
            return getController().getCompleteAmt(getContext(), orgId2ContractIdSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *得到合同下的累计票金额本币-User defined method
     *@param idSet 合同ID集合
     *@return
     */
    public Map getAllInvoiceAmt(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().getAllInvoiceAmt(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *得到合同下的累计票金额原币-User defined method
     *@param idSet 合同id
     *@return
     */
    public Map getAllInvoiceOriAmt(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().getAllInvoiceOriAmt(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获得调整金额-User defined method
     *@param idSet 合同ID
     *@return
     */
    public Map getAdjustSumMap(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().getAdjustSumMap(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *付款单发票金额本币-User defined method
     *@param idSet ID
     *@return
     */
    public Map getInvoiceAmt(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().getInvoiceAmt(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *付款单发票金额原币-User defined method
     *@param idSet ID
     *@return
     */
    public Map getInvoiceOriAmt(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().getInvoiceOriAmt(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}