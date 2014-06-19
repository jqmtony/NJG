package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class ContractWebServiceFacade extends AbstractBizCtrl implements IContractWebServiceFacade
{
    public ContractWebServiceFacade()
    {
        super();
        registerInterface(IContractWebServiceFacade.class, this);
    }
    public ContractWebServiceFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IContractWebServiceFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E3EE6728");
    }
    private ContractWebServiceFacadeController getController() throws BOSException
    {
        return (ContractWebServiceFacadeController)getBizController();
    }
    /**
     *设置合同-User defined method
     *@param contractBill 合同
     */
    public void setContract(ContractBillInfo contractBill) throws BOSException, EASBizException
    {
        try {
            getController().setContract(getContext(), contractBill);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *验证-User defined method
     *@param contractBill 合同
     *@return
     */
    public boolean validate(ContractBillInfo contractBill) throws BOSException, EASBizException
    {
        try {
            return getController().validate(getContext(), contractBill);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}