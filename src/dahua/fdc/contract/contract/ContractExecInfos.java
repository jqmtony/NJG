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

public class ContractExecInfos extends FDCBill implements IContractExecInfos
{
    public ContractExecInfos()
    {
        super();
        registerInterface(IContractExecInfos.class, this);
    }
    public ContractExecInfos(Context ctx)
    {
        super(ctx);
        registerInterface(IContractExecInfos.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E97B39A7");
    }
    private ContractExecInfosController getController() throws BOSException
    {
        return (ContractExecInfosController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ContractExecInfosInfo getContractExecInfosInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractExecInfosInfo(getContext(), pk);
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
    public ContractExecInfosInfo getContractExecInfosInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractExecInfosInfo(getContext(), pk, selector);
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
    public ContractExecInfosInfo getContractExecInfosInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractExecInfosInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ContractExecInfosCollection getContractExecInfosCollection() throws BOSException
    {
        try {
            return getController().getContractExecInfosCollection(getContext());
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
    public ContractExecInfosCollection getContractExecInfosCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractExecInfosCollection(getContext(), view);
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
    public ContractExecInfosCollection getContractExecInfosCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractExecInfosCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新合同-User defined method
     *@param type 类型
     *@param contractId 合同ID
     */
    public void updateContract(String type, String contractId) throws BOSException, EASBizException
    {
        try {
            getController().updateContract(getContext(), type, contractId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新变更-User defined method
     *@param type 类型
     *@param contractId 合同ID
     */
    public void updateChange(String type, String contractId) throws BOSException, EASBizException
    {
        try {
            getController().updateChange(getContext(), type, contractId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新结算-User defined method
     *@param type 类型
     *@param contractId 合同ID
     */
    public void updateSettle(String type, String contractId) throws BOSException, EASBizException
    {
        try {
            getController().updateSettle(getContext(), type, contractId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新付款-User defined method
     *@param type 类型
     *@param contractId 合同ID
     */
    public void updatePayment(String type, String contractId) throws BOSException, EASBizException
    {
        try {
            getController().updatePayment(getContext(), type, contractId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步原有合同数据-User defined method
     */
    public void synOldContract() throws BOSException, EASBizException
    {
        try {
            getController().synOldContract(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新被补充合同-User defined method
     *@param type 审批/反审批
     *@param contractId 合同ID
     */
    public void updateSuppliedContract(String type, String contractId) throws BOSException, EASBizException
    {
        try {
            getController().updateSuppliedContract(getContext(), type, contractId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}