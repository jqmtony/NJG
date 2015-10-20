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

public class ContractRevLandDeveloper extends CoreBase implements IContractRevLandDeveloper
{
    public ContractRevLandDeveloper()
    {
        super();
        registerInterface(IContractRevLandDeveloper.class, this);
    }
    public ContractRevLandDeveloper(Context ctx)
    {
        super(ctx);
        registerInterface(IContractRevLandDeveloper.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("16FF78A9");
    }
    private ContractRevLandDeveloperController getController() throws BOSException
    {
        return (ContractRevLandDeveloperController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ContractRevLandDeveloperInfo getContractRevLandDeveloperInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractRevLandDeveloperInfo(getContext(), pk);
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
    public ContractRevLandDeveloperInfo getContractRevLandDeveloperInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractRevLandDeveloperInfo(getContext(), oql);
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
    public ContractRevLandDeveloperInfo getContractRevLandDeveloperInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractRevLandDeveloperInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ContractRevLandDeveloperCollection getContractRevLandDeveloperCollection() throws BOSException
    {
        try {
            return getController().getContractRevLandDeveloperCollection(getContext());
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
    public ContractRevLandDeveloperCollection getContractRevLandDeveloperCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractRevLandDeveloperCollection(getContext(), view);
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
    public ContractRevLandDeveloperCollection getContractRevLandDeveloperCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractRevLandDeveloperCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}