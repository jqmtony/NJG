package com.kingdee.eas.fdc.basedata;

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
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ContractPayPlanType extends FDCDataBase implements IContractPayPlanType
{
    public ContractPayPlanType()
    {
        super();
        registerInterface(IContractPayPlanType.class, this);
    }
    public ContractPayPlanType(Context ctx)
    {
        super(ctx);
        registerInterface(IContractPayPlanType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FF502627");
    }
    private ContractPayPlanTypeController getController() throws BOSException
    {
        return (ContractPayPlanTypeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ContractPayPlanTypeInfo getContractPayPlanTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractPayPlanTypeInfo(getContext(), pk);
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
    public ContractPayPlanTypeInfo getContractPayPlanTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractPayPlanTypeInfo(getContext(), pk, selector);
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
    public ContractPayPlanTypeInfo getContractPayPlanTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractPayPlanTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ContractPayPlanTypeCollection getContractPayPlanTypeCollection() throws BOSException
    {
        try {
            return getController().getContractPayPlanTypeCollection(getContext());
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
    public ContractPayPlanTypeCollection getContractPayPlanTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractPayPlanTypeCollection(getContext(), view);
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
    public ContractPayPlanTypeCollection getContractPayPlanTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractPayPlanTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Ω˚”√-User defined method
     *@param pk pk
     *@param model model
     *@return
     */
    public boolean disEnabled(IObjectPK pk, IObjectValue model) throws BOSException, EASBizException
    {
        try {
            return getController().disEnabled(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *∆Ù”√-User defined method
     *@param pk pk
     *@param model model
     *@return
     */
    public boolean enabled(IObjectPK pk, IObjectValue model) throws BOSException, EASBizException
    {
        try {
            return getController().enabled(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}