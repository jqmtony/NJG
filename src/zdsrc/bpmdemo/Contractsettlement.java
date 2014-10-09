package com.kingdee.eas.bpmdemo;

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
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.bpmdemo.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class Contractsettlement extends CoreBillBase implements IContractsettlement
{
    public Contractsettlement()
    {
        super();
        registerInterface(IContractsettlement.class, this);
    }
    public Contractsettlement(Context ctx)
    {
        super(ctx);
        registerInterface(IContractsettlement.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0173D879");
    }
    private ContractsettlementController getController() throws BOSException
    {
        return (ContractsettlementController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public ContractsettlementCollection getContractsettlementCollection() throws BOSException
    {
        try {
            return getController().getContractsettlementCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public ContractsettlementCollection getContractsettlementCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractsettlementCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public ContractsettlementCollection getContractsettlementCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractsettlementCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public ContractsettlementInfo getContractsettlementInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractsettlementInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public ContractsettlementInfo getContractsettlementInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractsettlementInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public ContractsettlementInfo getContractsettlementInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractsettlementInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}