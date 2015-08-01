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
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;
import com.kingdee.eas.framework.ICoreBase;

public class ContractLandDeveloper extends CoreBase implements IContractLandDeveloper
{
    public ContractLandDeveloper()
    {
        super();
        registerInterface(IContractLandDeveloper.class, this);
    }
    public ContractLandDeveloper(Context ctx)
    {
        super(ctx);
        registerInterface(IContractLandDeveloper.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("963F1AD2");
    }
    private ContractLandDeveloperController getController() throws BOSException
    {
        return (ContractLandDeveloperController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ContractLandDeveloperInfo getContractLandDeveloperInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractLandDeveloperInfo(getContext(), pk);
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
    public ContractLandDeveloperInfo getContractLandDeveloperInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractLandDeveloperInfo(getContext(), pk, selector);
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
    public ContractLandDeveloperInfo getContractLandDeveloperInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractLandDeveloperInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ContractLandDeveloperCollection getContractLandDeveloperCollection() throws BOSException
    {
        try {
            return getController().getContractLandDeveloperCollection(getContext());
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
    public ContractLandDeveloperCollection getContractLandDeveloperCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractLandDeveloperCollection(getContext(), view);
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
    public ContractLandDeveloperCollection getContractLandDeveloperCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractLandDeveloperCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}