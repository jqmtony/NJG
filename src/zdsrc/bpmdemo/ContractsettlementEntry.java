package com.kingdee.eas.bpmdemo;

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
import com.kingdee.eas.bpmdemo.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ContractsettlementEntry extends CoreBillEntryBase implements IContractsettlementEntry
{
    public ContractsettlementEntry()
    {
        super();
        registerInterface(IContractsettlementEntry.class, this);
    }
    public ContractsettlementEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IContractsettlementEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("57ADA379");
    }
    private ContractsettlementEntryController getController() throws BOSException
    {
        return (ContractsettlementEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ContractsettlementEntryInfo getContractsettlementEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractsettlementEntryInfo(getContext(), pk);
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
    public ContractsettlementEntryInfo getContractsettlementEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractsettlementEntryInfo(getContext(), pk, selector);
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
    public ContractsettlementEntryInfo getContractsettlementEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractsettlementEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ContractsettlementEntryCollection getContractsettlementEntryCollection() throws BOSException
    {
        try {
            return getController().getContractsettlementEntryCollection(getContext());
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
    public ContractsettlementEntryCollection getContractsettlementEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractsettlementEntryCollection(getContext(), view);
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
    public ContractsettlementEntryCollection getContractsettlementEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractsettlementEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}