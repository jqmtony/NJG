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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ObjectBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;
import com.kingdee.eas.framework.IObjectBase;

public class ContractMoveHistory extends ObjectBase implements IContractMoveHistory
{
    public ContractMoveHistory()
    {
        super();
        registerInterface(IContractMoveHistory.class, this);
    }
    public ContractMoveHistory(Context ctx)
    {
        super(ctx);
        registerInterface(IContractMoveHistory.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F3BCC0F6");
    }
    private ContractMoveHistoryController getController() throws BOSException
    {
        return (ContractMoveHistoryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ContractMoveHistoryInfo getContractMoveHistoryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractMoveHistoryInfo(getContext(), pk);
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
    public ContractMoveHistoryInfo getContractMoveHistoryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractMoveHistoryInfo(getContext(), pk, selector);
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
    public ContractMoveHistoryInfo getContractMoveHistoryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractMoveHistoryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ContractMoveHistoryCollection getContractMoveHistoryCollection() throws BOSException
    {
        try {
            return getController().getContractMoveHistoryCollection(getContext());
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
    public ContractMoveHistoryCollection getContractMoveHistoryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractMoveHistoryCollection(getContext(), view);
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
    public ContractMoveHistoryCollection getContractMoveHistoryCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractMoveHistoryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}