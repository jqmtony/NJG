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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.bpm.app.*;
import com.kingdee.eas.framework.TreeBase;

public class ApproveBackSetTree extends TreeBase implements IApproveBackSetTree
{
    public ApproveBackSetTree()
    {
        super();
        registerInterface(IApproveBackSetTree.class, this);
    }
    public ApproveBackSetTree(Context ctx)
    {
        super(ctx);
        registerInterface(IApproveBackSetTree.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D6D723AD");
    }
    private ApproveBackSetTreeController getController() throws BOSException
    {
        return (ApproveBackSetTreeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ApproveBackSetTreeInfo getApproveBackSetTreeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getApproveBackSetTreeInfo(getContext(), pk);
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
    public ApproveBackSetTreeInfo getApproveBackSetTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getApproveBackSetTreeInfo(getContext(), pk, selector);
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
    public ApproveBackSetTreeInfo getApproveBackSetTreeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getApproveBackSetTreeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ApproveBackSetTreeCollection getApproveBackSetTreeCollection() throws BOSException
    {
        try {
            return getController().getApproveBackSetTreeCollection(getContext());
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
    public ApproveBackSetTreeCollection getApproveBackSetTreeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getApproveBackSetTreeCollection(getContext(), view);
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
    public ApproveBackSetTreeCollection getApproveBackSetTreeCollection(String oql) throws BOSException
    {
        try {
            return getController().getApproveBackSetTreeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}