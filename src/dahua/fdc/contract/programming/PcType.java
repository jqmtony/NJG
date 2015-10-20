package com.kingdee.eas.fdc.contract.programming;

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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.programming.app.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class PcType extends CoreBillBase implements IPcType
{
    public PcType()
    {
        super();
        registerInterface(IPcType.class, this);
    }
    public PcType(Context ctx)
    {
        super(ctx);
        registerInterface(IPcType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B37DC0FD");
    }
    private PcTypeController getController() throws BOSException
    {
        return (PcTypeController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PcTypeCollection getPcTypeCollection() throws BOSException
    {
        try {
            return getController().getPcTypeCollection(getContext());
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
    public PcTypeCollection getPcTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPcTypeCollection(getContext(), view);
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
    public PcTypeCollection getPcTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getPcTypeCollection(getContext(), oql);
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
    public PcTypeInfo getPcTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPcTypeInfo(getContext(), pk);
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
    public PcTypeInfo getPcTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPcTypeInfo(getContext(), pk, selector);
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
    public PcTypeInfo getPcTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPcTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}