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
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;
import java.util.List;

public class ClearSplitFacade extends AbstractBizCtrl implements IClearSplitFacade
{
    public ClearSplitFacade()
    {
        super();
        registerInterface(IClearSplitFacade.class, this);
    }
    public ClearSplitFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IClearSplitFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E4771802");
    }
    private ClearSplitFacadeController getController() throws BOSException
    {
        return (ClearSplitFacadeController)getBizController();
    }
    /**
     *作废拆分-User defined method
     *@param contractID 合同id
     *@param isContract 是否作废合同拆分
     */
    public void clearAllSplit(String contractID, boolean isContract) throws BOSException, EASBizException
    {
        try {
            getController().clearAllSplit(getContext(), contractID, isContract);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *作废拆分-User defined method
     *@param contractID 合同id
     */
    public void clearSplitBill(String contractID) throws BOSException, EASBizException
    {
        try {
            getController().clearSplitBill(getContext(), contractID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *作废无文本拆分（提供给面积刷新的接口）-User defined method
     *@param id 无文本Id
     */
    public void clearNoTextSplit(String id) throws BOSException, EASBizException
    {
        try {
            getController().clearNoTextSplit(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *作废结算拆分以及引用了此结算拆分的付款拆分-User defined method
     *@param contractId 合同id
     */
    public void clearSettle(String contractId) throws BOSException, EASBizException
    {
        try {
            getController().clearSettle(getContext(), contractId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *付款拆分直接作废无文本合同拆分-User defined method
     *@param id 无文本Id
     */
    public void clearWithoutTextSplit(String id) throws BOSException, EASBizException
    {
        try {
            getController().clearWithoutTextSplit(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *作废付款拆分，更改合同状态-User defined method
     *@param idList 合同ID集合
     */
    public void clearPaymentSplit(List idList) throws BOSException, EASBizException
    {
        try {
            getController().clearPaymentSplit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *清除之后期间的合同相关的所有拆分（包括合同拆分，变更拆分，结算拆分，付款拆分，无文本合同付款拆分）-User defined method
     *@param contractID 合同ID
     *@param type 清除类型：合同、结算、付款
     *@return
     */
    public String clearPeriodConSplit(String contractID, String type) throws BOSException, EASBizException
    {
        try {
            return getController().clearPeriodConSplit(getContext(), contractID, type);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *作废变更拆分，以及后续的结算拆分，付款拆分-User defined method
     *@param changeId 变更ID
     */
    public void clearChangeSplit(String changeId) throws BOSException, EASBizException
    {
        try {
            getController().clearChangeSplit(getContext(), changeId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *得到工程项目下应该作废但是没有作废的合同-User defined method
     *@param idList 工程项目ID
     *@return
     */
    public List getToInvalidContract(List idList) throws BOSException, EASBizException
    {
        try {
            return getController().getToInvalidContract(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}