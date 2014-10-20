package com.kingdee.eas.bpm;

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
import com.kingdee.eas.bpm.app.*;
import com.kingdee.eas.framework.IDataBase;

public class ApproveBackSet extends DataBase implements IApproveBackSet
{
    public ApproveBackSet()
    {
        super();
        registerInterface(IApproveBackSet.class, this);
    }
    public ApproveBackSet(Context ctx)
    {
        super(ctx);
        registerInterface(IApproveBackSet.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("91D08B6F");
    }
    private ApproveBackSetController getController() throws BOSException
    {
        return (ApproveBackSetController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ApproveBackSetInfo getApproveBackSetInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getApproveBackSetInfo(getContext(), pk);
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
    public ApproveBackSetInfo getApproveBackSetInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getApproveBackSetInfo(getContext(), pk, selector);
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
    public ApproveBackSetInfo getApproveBackSetInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getApproveBackSetInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ApproveBackSetCollection getApproveBackSetCollection() throws BOSException
    {
        try {
            return getController().getApproveBackSetCollection(getContext());
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
    public ApproveBackSetCollection getApproveBackSetCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getApproveBackSetCollection(getContext(), view);
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
    public ApproveBackSetCollection getApproveBackSetCollection(String oql) throws BOSException
    {
        try {
            return getController().getApproveBackSetCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}