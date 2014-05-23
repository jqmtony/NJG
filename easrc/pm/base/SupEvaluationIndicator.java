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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.port.pm.base.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class SupEvaluationIndicator extends DataBase implements ISupEvaluationIndicator
{
    public SupEvaluationIndicator()
    {
        super();
        registerInterface(ISupEvaluationIndicator.class, this);
    }
    public SupEvaluationIndicator(Context ctx)
    {
        super(ctx);
        registerInterface(ISupEvaluationIndicator.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("840C8E86");
    }
    private SupEvaluationIndicatorController getController() throws BOSException
    {
        return (SupEvaluationIndicatorController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SupEvaluationIndicatorInfo getSupEvaluationIndicatorInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSupEvaluationIndicatorInfo(getContext(), pk);
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
    public SupEvaluationIndicatorInfo getSupEvaluationIndicatorInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSupEvaluationIndicatorInfo(getContext(), pk, selector);
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
    public SupEvaluationIndicatorInfo getSupEvaluationIndicatorInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSupEvaluationIndicatorInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SupEvaluationIndicatorCollection getSupEvaluationIndicatorCollection() throws BOSException
    {
        try {
            return getController().getSupEvaluationIndicatorCollection(getContext());
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
    public SupEvaluationIndicatorCollection getSupEvaluationIndicatorCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSupEvaluationIndicatorCollection(getContext(), view);
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
    public SupEvaluationIndicatorCollection getSupEvaluationIndicatorCollection(String oql) throws BOSException
    {
        try {
            return getController().getSupEvaluationIndicatorCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}