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

public class EvaluationIndicators extends DataBase implements IEvaluationIndicators
{
    public EvaluationIndicators()
    {
        super();
        registerInterface(IEvaluationIndicators.class, this);
    }
    public EvaluationIndicators(Context ctx)
    {
        super(ctx);
        registerInterface(IEvaluationIndicators.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("68C13261");
    }
    private EvaluationIndicatorsController getController() throws BOSException
    {
        return (EvaluationIndicatorsController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public EvaluationIndicatorsInfo getEvaluationIndicatorsInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getEvaluationIndicatorsInfo(getContext(), pk);
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
    public EvaluationIndicatorsInfo getEvaluationIndicatorsInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getEvaluationIndicatorsInfo(getContext(), pk, selector);
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
    public EvaluationIndicatorsInfo getEvaluationIndicatorsInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getEvaluationIndicatorsInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public EvaluationIndicatorsCollection getEvaluationIndicatorsCollection() throws BOSException
    {
        try {
            return getController().getEvaluationIndicatorsCollection(getContext());
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
    public EvaluationIndicatorsCollection getEvaluationIndicatorsCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getEvaluationIndicatorsCollection(getContext(), view);
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
    public EvaluationIndicatorsCollection getEvaluationIndicatorsCollection(String oql) throws BOSException
    {
        try {
            return getController().getEvaluationIndicatorsCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}