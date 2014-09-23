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

public class ContractDemo extends CoreBillBase implements IContractDemo
{
    public ContractDemo()
    {
        super();
        registerInterface(IContractDemo.class, this);
    }
    public ContractDemo(Context ctx)
    {
        super(ctx);
        registerInterface(IContractDemo.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E7EF7D53");
    }
    private ContractDemoController getController() throws BOSException
    {
        return (ContractDemoController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ContractDemoCollection getContractDemoCollection() throws BOSException
    {
        try {
            return getController().getContractDemoCollection(getContext());
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
    public ContractDemoCollection getContractDemoCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractDemoCollection(getContext(), view);
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
    public ContractDemoCollection getContractDemoCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractDemoCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ContractDemoInfo getContractDemoInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractDemoInfo(getContext(), pk);
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
    public ContractDemoInfo getContractDemoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractDemoInfo(getContext(), pk, selector);
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
    public ContractDemoInfo getContractDemoInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractDemoInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}