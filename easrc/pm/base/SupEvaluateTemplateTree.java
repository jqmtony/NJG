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

public class SupEvaluateTemplateTree extends TreeBase implements ISupEvaluateTemplateTree
{
    public SupEvaluateTemplateTree()
    {
        super();
        registerInterface(ISupEvaluateTemplateTree.class, this);
    }
    public SupEvaluateTemplateTree(Context ctx)
    {
        super(ctx);
        registerInterface(ISupEvaluateTemplateTree.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6F93007E");
    }
    private SupEvaluateTemplateTreeController getController() throws BOSException
    {
        return (SupEvaluateTemplateTreeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SupEvaluateTemplateTreeInfo getSupEvaluateTemplateTreeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSupEvaluateTemplateTreeInfo(getContext(), pk);
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
    public SupEvaluateTemplateTreeInfo getSupEvaluateTemplateTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSupEvaluateTemplateTreeInfo(getContext(), pk, selector);
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
    public SupEvaluateTemplateTreeInfo getSupEvaluateTemplateTreeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSupEvaluateTemplateTreeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SupEvaluateTemplateTreeCollection getSupEvaluateTemplateTreeCollection() throws BOSException
    {
        try {
            return getController().getSupEvaluateTemplateTreeCollection(getContext());
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
    public SupEvaluateTemplateTreeCollection getSupEvaluateTemplateTreeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSupEvaluateTemplateTreeCollection(getContext(), view);
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
    public SupEvaluateTemplateTreeCollection getSupEvaluateTemplateTreeCollection(String oql) throws BOSException
    {
        try {
            return getController().getSupEvaluateTemplateTreeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}