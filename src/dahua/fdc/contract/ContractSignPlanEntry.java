package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class ContractSignPlanEntry extends CoreBillEntryBase implements IContractSignPlanEntry
{
    public ContractSignPlanEntry()
    {
        super();
        registerInterface(IContractSignPlanEntry.class, this);
    }
    public ContractSignPlanEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IContractSignPlanEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("52AF323F");
    }
    private ContractSignPlanEntryController getController() throws BOSException
    {
        return (ContractSignPlanEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ContractSignPlanEntryInfo getContractSignPlanEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractSignPlanEntryInfo(getContext(), pk);
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
    public ContractSignPlanEntryInfo getContractSignPlanEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractSignPlanEntryInfo(getContext(), pk, selector);
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
    public ContractSignPlanEntryInfo getContractSignPlanEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractSignPlanEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ContractSignPlanEntryCollection getContractSignPlanEntryCollection() throws BOSException
    {
        try {
            return getController().getContractSignPlanEntryCollection(getContext());
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
    public ContractSignPlanEntryCollection getContractSignPlanEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractSignPlanEntryCollection(getContext(), view);
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
    public ContractSignPlanEntryCollection getContractSignPlanEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractSignPlanEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}