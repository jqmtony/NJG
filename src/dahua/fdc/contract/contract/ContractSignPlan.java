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

public class ContractSignPlan extends FDCBill implements IContractSignPlan
{
    public ContractSignPlan()
    {
        super();
        registerInterface(IContractSignPlan.class, this);
    }
    public ContractSignPlan(Context ctx)
    {
        super(ctx);
        registerInterface(IContractSignPlan.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FB39A5F3");
    }
    private ContractSignPlanController getController() throws BOSException
    {
        return (ContractSignPlanController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ContractSignPlanInfo getContractSignPlanInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractSignPlanInfo(getContext(), pk);
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
    public ContractSignPlanInfo getContractSignPlanInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractSignPlanInfo(getContext(), pk, selector);
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
    public ContractSignPlanInfo getContractSignPlanInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractSignPlanInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ContractSignPlanCollection getContractSignPlanCollection() throws BOSException
    {
        try {
            return getController().getContractSignPlanCollection(getContext());
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
    public ContractSignPlanCollection getContractSignPlanCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractSignPlanCollection(getContext(), view);
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
    public ContractSignPlanCollection getContractSignPlanCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractSignPlanCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}