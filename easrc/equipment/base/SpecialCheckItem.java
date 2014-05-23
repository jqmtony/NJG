package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.port.equipment.base.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class SpecialCheckItem extends TreeBase implements ISpecialCheckItem
{
    public SpecialCheckItem()
    {
        super();
        registerInterface(ISpecialCheckItem.class, this);
    }
    public SpecialCheckItem(Context ctx)
    {
        super(ctx);
        registerInterface(ISpecialCheckItem.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("77774DC4");
    }
    private SpecialCheckItemController getController() throws BOSException
    {
        return (SpecialCheckItemController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SpecialCheckItemInfo getSpecialCheckItemInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSpecialCheckItemInfo(getContext(), pk);
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
    public SpecialCheckItemInfo getSpecialCheckItemInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSpecialCheckItemInfo(getContext(), pk, selector);
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
    public SpecialCheckItemInfo getSpecialCheckItemInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSpecialCheckItemInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SpecialCheckItemCollection getSpecialCheckItemCollection() throws BOSException
    {
        try {
            return getController().getSpecialCheckItemCollection(getContext());
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
    public SpecialCheckItemCollection getSpecialCheckItemCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSpecialCheckItemCollection(getContext(), view);
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
    public SpecialCheckItemCollection getSpecialCheckItemCollection(String oql) throws BOSException
    {
        try {
            return getController().getSpecialCheckItemCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}