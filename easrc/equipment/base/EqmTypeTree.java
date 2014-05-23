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

public class EqmTypeTree extends TreeBase implements IEqmTypeTree
{
    public EqmTypeTree()
    {
        super();
        registerInterface(IEqmTypeTree.class, this);
    }
    public EqmTypeTree(Context ctx)
    {
        super(ctx);
        registerInterface(IEqmTypeTree.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("20BF66D7");
    }
    private EqmTypeTreeController getController() throws BOSException
    {
        return (EqmTypeTreeController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public EqmTypeTreeInfo getEqmTypeTreeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getEqmTypeTreeInfo(getContext(), pk);
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
    public EqmTypeTreeInfo getEqmTypeTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getEqmTypeTreeInfo(getContext(), pk, selector);
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
    public EqmTypeTreeInfo getEqmTypeTreeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getEqmTypeTreeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public EqmTypeTreeCollection getEqmTypeTreeCollection() throws BOSException
    {
        try {
            return getController().getEqmTypeTreeCollection(getContext());
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
    public EqmTypeTreeCollection getEqmTypeTreeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getEqmTypeTreeCollection(getContext(), view);
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
    public EqmTypeTreeCollection getEqmTypeTreeCollection(String oql) throws BOSException
    {
        try {
            return getController().getEqmTypeTreeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}