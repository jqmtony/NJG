package com.kingdee.eas.fdc.aimcost.costkf;

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
import com.kingdee.eas.fdc.aimcost.costkf.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class Uses extends DataBase implements IUses
{
    public Uses()
    {
        super();
        registerInterface(IUses.class, this);
    }
    public Uses(Context ctx)
    {
        super(ctx);
        registerInterface(IUses.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D1C5FF95");
    }
    private UsesController getController() throws BOSException
    {
        return (UsesController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public UsesInfo getUsesInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getUsesInfo(getContext(), pk);
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
    public UsesInfo getUsesInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getUsesInfo(getContext(), pk, selector);
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
    public UsesInfo getUsesInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getUsesInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public UsesCollection getUsesCollection() throws BOSException
    {
        try {
            return getController().getUsesCollection(getContext());
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
    public UsesCollection getUsesCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getUsesCollection(getContext(), view);
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
    public UsesCollection getUsesCollection(String oql) throws BOSException
    {
        try {
            return getController().getUsesCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}