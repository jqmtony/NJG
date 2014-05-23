package com.kingdee.eas.port.pm.base;

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
import com.kingdee.eas.port.pm.base.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class SupEvaluationIndicatorTree extends TreeBase implements ISupEvaluationIndicatorTree
{
    public SupEvaluationIndicatorTree()
    {
        super();
        registerInterface(ISupEvaluationIndicatorTree.class, this);
    }
    public SupEvaluationIndicatorTree(Context ctx)
    {
        super(ctx);
        registerInterface(ISupEvaluationIndicatorTree.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("769DC344");
    }
    private SupEvaluationIndicatorTreeController getController() throws BOSException
    {
        return (SupEvaluationIndicatorTreeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SupEvaluationIndicatorTreeInfo getSupEvaluationIndicatorTreeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSupEvaluationIndicatorTreeInfo(getContext(), pk);
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
    public SupEvaluationIndicatorTreeInfo getSupEvaluationIndicatorTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSupEvaluationIndicatorTreeInfo(getContext(), pk, selector);
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
    public SupEvaluationIndicatorTreeInfo getSupEvaluationIndicatorTreeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSupEvaluationIndicatorTreeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SupEvaluationIndicatorTreeCollection getSupEvaluationIndicatorTreeCollection() throws BOSException
    {
        try {
            return getController().getSupEvaluationIndicatorTreeCollection(getContext());
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
    public SupEvaluationIndicatorTreeCollection getSupEvaluationIndicatorTreeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSupEvaluationIndicatorTreeCollection(getContext(), view);
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
    public SupEvaluationIndicatorTreeCollection getSupEvaluationIndicatorTreeCollection(String oql) throws BOSException
    {
        try {
            return getController().getSupEvaluationIndicatorTreeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}