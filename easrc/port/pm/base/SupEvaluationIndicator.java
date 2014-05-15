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
     *ȡֵ-System defined method
     *@param pk ȡֵ
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
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
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
     *ȡֵ-System defined method
     *@param oql ȡֵ
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
     *ȡ����-System defined method
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
     *ȡ����-System defined method
     *@param view ȡ����
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
     *ȡ����-System defined method
     *@param oql ȡ����
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