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

public class ContractBillReviseEntry extends CoreBillEntryBase implements IContractBillReviseEntry
{
    public ContractBillReviseEntry()
    {
        super();
        registerInterface(IContractBillReviseEntry.class, this);
    }
    public ContractBillReviseEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IContractBillReviseEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("12AECFC6");
    }
    private ContractBillReviseEntryController getController() throws BOSException
    {
        return (ContractBillReviseEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public ContractBillReviseEntryInfo getContractBillReviseEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillReviseEntryInfo(getContext(), pk);
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
    public ContractBillReviseEntryInfo getContractBillReviseEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillReviseEntryInfo(getContext(), pk, selector);
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
    public ContractBillReviseEntryInfo getContractBillReviseEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillReviseEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ContractBillReviseEntryCollection getContractBillReviseEntryCollection() throws BOSException
    {
        try {
            return getController().getContractBillReviseEntryCollection(getContext());
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
    public ContractBillReviseEntryCollection getContractBillReviseEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractBillReviseEntryCollection(getContext(), view);
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
    public ContractBillReviseEntryCollection getContractBillReviseEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractBillReviseEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}