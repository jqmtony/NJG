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

public class ContractPayItem extends CoreBillEntryBase implements IContractPayItem
{
    public ContractPayItem()
    {
        super();
        registerInterface(IContractPayItem.class, this);
    }
    public ContractPayItem(Context ctx)
    {
        super(ctx);
        registerInterface(IContractPayItem.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("967CFACE");
    }
    private ContractPayItemController getController() throws BOSException
    {
        return (ContractPayItemController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ContractPayItemInfo getContractPayItemInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractPayItemInfo(getContext(), pk);
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
    public ContractPayItemInfo getContractPayItemInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractPayItemInfo(getContext(), pk, selector);
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
    public ContractPayItemInfo getContractPayItemInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractPayItemInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ContractPayItemCollection getContractPayItemCollection() throws BOSException
    {
        try {
            return getController().getContractPayItemCollection(getContext());
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
    public ContractPayItemCollection getContractPayItemCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractPayItemCollection(getContext(), view);
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
    public ContractPayItemCollection getContractPayItemCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractPayItemCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}