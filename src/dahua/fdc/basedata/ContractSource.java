package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class ContractSource extends FDCDataBase implements IContractSource
{
    public ContractSource()
    {
        super();
        registerInterface(IContractSource.class, this);
    }
    public ContractSource(Context ctx)
    {
        super(ctx);
        registerInterface(IContractSource.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9AB1D73F");
    }
    private ContractSourceController getController() throws BOSException
    {
        return (ContractSourceController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ContractSourceInfo getContractSourceInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractSourceInfo(getContext(), pk);
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
    public ContractSourceInfo getContractSourceInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractSourceInfo(getContext(), pk, selector);
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
    public ContractSourceInfo getContractSourceInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractSourceInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ContractSourceCollection getContractSourceCollection() throws BOSException
    {
        try {
            return getController().getContractSourceCollection(getContext());
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
    public ContractSourceCollection getContractSourceCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractSourceCollection(getContext(), view);
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
    public ContractSourceCollection getContractSourceCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractSourceCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}