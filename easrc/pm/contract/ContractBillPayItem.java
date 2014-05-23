package com.kingdee.eas.port.pm.contract;

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
import com.kingdee.eas.port.pm.contract.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ContractBillPayItem extends CoreBillEntryBase implements IContractBillPayItem
{
    public ContractBillPayItem()
    {
        super();
        registerInterface(IContractBillPayItem.class, this);
    }
    public ContractBillPayItem(Context ctx)
    {
        super(ctx);
        registerInterface(IContractBillPayItem.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C7930A42");
    }
    private ContractBillPayItemController getController() throws BOSException
    {
        return (ContractBillPayItemController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ContractBillPayItemInfo getContractBillPayItemInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillPayItemInfo(getContext(), pk);
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
    public ContractBillPayItemInfo getContractBillPayItemInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillPayItemInfo(getContext(), pk, selector);
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
    public ContractBillPayItemInfo getContractBillPayItemInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillPayItemInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ContractBillPayItemCollection getContractBillPayItemCollection() throws BOSException
    {
        try {
            return getController().getContractBillPayItemCollection(getContext());
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
    public ContractBillPayItemCollection getContractBillPayItemCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractBillPayItemCollection(getContext(), view);
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
    public ContractBillPayItemCollection getContractBillPayItemCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractBillPayItemCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}