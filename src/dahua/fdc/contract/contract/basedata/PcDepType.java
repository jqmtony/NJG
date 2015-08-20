package com.kingdee.eas.fdc.contract.basedata;

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
import com.kingdee.eas.fdc.contract.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class PcDepType extends DataBase implements IPcDepType
{
    public PcDepType()
    {
        super();
        registerInterface(IPcDepType.class, this);
    }
    public PcDepType(Context ctx)
    {
        super(ctx);
        registerInterface(IPcDepType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6E190484");
    }
    private PcDepTypeController getController() throws BOSException
    {
        return (PcDepTypeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PcDepTypeInfo getPcDepTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPcDepTypeInfo(getContext(), pk);
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
    public PcDepTypeInfo getPcDepTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPcDepTypeInfo(getContext(), pk, selector);
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
    public PcDepTypeInfo getPcDepTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPcDepTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PcDepTypeCollection getPcDepTypeCollection() throws BOSException
    {
        try {
            return getController().getPcDepTypeCollection(getContext());
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
    public PcDepTypeCollection getPcDepTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPcDepTypeCollection(getContext(), view);
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
    public PcDepTypeCollection getPcDepTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getPcDepTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}