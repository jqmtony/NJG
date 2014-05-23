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

public class EvaluationIndicatorsTree extends TreeBase implements IEvaluationIndicatorsTree
{
    public EvaluationIndicatorsTree()
    {
        super();
        registerInterface(IEvaluationIndicatorsTree.class, this);
    }
    public EvaluationIndicatorsTree(Context ctx)
    {
        super(ctx);
        registerInterface(IEvaluationIndicatorsTree.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E657019F");
    }
    private EvaluationIndicatorsTreeController getController() throws BOSException
    {
        return (EvaluationIndicatorsTreeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public EvaluationIndicatorsTreeInfo getEvaluationIndicatorsTreeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getEvaluationIndicatorsTreeInfo(getContext(), pk);
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
    public EvaluationIndicatorsTreeInfo getEvaluationIndicatorsTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getEvaluationIndicatorsTreeInfo(getContext(), pk, selector);
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
    public EvaluationIndicatorsTreeInfo getEvaluationIndicatorsTreeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getEvaluationIndicatorsTreeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public EvaluationIndicatorsTreeCollection getEvaluationIndicatorsTreeCollection() throws BOSException
    {
        try {
            return getController().getEvaluationIndicatorsTreeCollection(getContext());
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
    public EvaluationIndicatorsTreeCollection getEvaluationIndicatorsTreeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getEvaluationIndicatorsTreeCollection(getContext(), view);
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
    public EvaluationIndicatorsTreeCollection getEvaluationIndicatorsTreeCollection(String oql) throws BOSException
    {
        try {
            return getController().getEvaluationIndicatorsTreeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}