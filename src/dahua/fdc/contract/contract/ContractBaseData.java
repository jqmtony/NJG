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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;
import com.kingdee.eas.framework.IDataBase;

public class ContractBaseData extends DataBase implements IContractBaseData
{
    public ContractBaseData()
    {
        super();
        registerInterface(IContractBaseData.class, this);
    }
    public ContractBaseData(Context ctx)
    {
        super(ctx);
        registerInterface(IContractBaseData.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("82098988");
    }
    private ContractBaseDataController getController() throws BOSException
    {
        return (ContractBaseDataController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ContractBaseDataInfo getContractBaseDataInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBaseDataInfo(getContext(), pk);
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
    public ContractBaseDataInfo getContractBaseDataInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBaseDataInfo(getContext(), pk, selector);
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
    public ContractBaseDataInfo getContractBaseDataInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBaseDataInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ContractBaseDataCollection getContractBaseDataCollection() throws BOSException
    {
        try {
            return getController().getContractBaseDataCollection(getContext());
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
    public ContractBaseDataCollection getContractBaseDataCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractBaseDataCollection(getContext(), view);
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
    public ContractBaseDataCollection getContractBaseDataCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractBaseDataCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}