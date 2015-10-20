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

public class ContractBillRevise extends FDCBill implements IContractBillRevise
{
    public ContractBillRevise()
    {
        super();
        registerInterface(IContractBillRevise.class, this);
    }
    public ContractBillRevise(Context ctx)
    {
        super(ctx);
        registerInterface(IContractBillRevise.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7B31700C");
    }
    private ContractBillReviseController getController() throws BOSException
    {
        return (ContractBillReviseController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ContractBillReviseCollection getContractBillReviseCollection() throws BOSException
    {
        try {
            return getController().getContractBillReviseCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view view
     *@return
     */
    public ContractBillReviseCollection getContractBillReviseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractBillReviseCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql oql
     *@return
     */
    public ContractBillReviseCollection getContractBillReviseCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractBillReviseCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public ContractBillReviseInfo getContractBillReviseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillReviseInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public ContractBillReviseInfo getContractBillReviseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillReviseInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql oql
     *@return
     */
    public ContractBillReviseInfo getContractBillReviseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillReviseInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *合同归档-User defined method
     *@param cbInfo cbInfo
     *@param storeNumber 界面归档编号
     *@return
     */
    public boolean contractBillStore(ContractBillReviseInfo cbInfo, String storeNumber) throws BOSException, EASBizException
    {
        try {
            return getController().contractBillStore(getContext(), cbInfo, storeNumber);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}