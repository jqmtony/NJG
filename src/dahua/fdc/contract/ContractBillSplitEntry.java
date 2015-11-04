package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCSplitBillEntry;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntry;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class ContractBillSplitEntry extends FDCSplitBillEntry implements IContractBillSplitEntry
{
    public ContractBillSplitEntry()
    {
        super();
        registerInterface(IContractBillSplitEntry.class, this);
    }
    public ContractBillSplitEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IContractBillSplitEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EA36AA8C");
    }
    private ContractBillSplitEntryController getController() throws BOSException
    {
        return (ContractBillSplitEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ContractBillSplitEntryInfo getContractBillSplitEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillSplitEntryInfo(getContext(), pk);
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
    public ContractBillSplitEntryInfo getContractBillSplitEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillSplitEntryInfo(getContext(), pk, selector);
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
    public ContractBillSplitEntryInfo getContractBillSplitEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillSplitEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ContractBillSplitEntryCollection getContractBillSplitEntryCollection() throws BOSException
    {
        try {
            return getController().getContractBillSplitEntryCollection(getContext());
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
    public ContractBillSplitEntryCollection getContractBillSplitEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractBillSplitEntryCollection(getContext(), view);
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
    public ContractBillSplitEntryCollection getContractBillSplitEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractBillSplitEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}