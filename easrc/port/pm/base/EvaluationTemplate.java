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

public class EvaluationTemplate extends DataBase implements IEvaluationTemplate
{
    public EvaluationTemplate()
    {
        super();
        registerInterface(IEvaluationTemplate.class, this);
    }
    public EvaluationTemplate(Context ctx)
    {
        super(ctx);
        registerInterface(IEvaluationTemplate.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6352DDD7");
    }
    private EvaluationTemplateController getController() throws BOSException
    {
        return (EvaluationTemplateController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public EvaluationTemplateInfo getEvaluationTemplateInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getEvaluationTemplateInfo(getContext(), pk);
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
    public EvaluationTemplateInfo getEvaluationTemplateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getEvaluationTemplateInfo(getContext(), pk, selector);
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
    public EvaluationTemplateInfo getEvaluationTemplateInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getEvaluationTemplateInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public EvaluationTemplateCollection getEvaluationTemplateCollection() throws BOSException
    {
        try {
            return getController().getEvaluationTemplateCollection(getContext());
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
    public EvaluationTemplateCollection getEvaluationTemplateCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getEvaluationTemplateCollection(getContext(), view);
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
    public EvaluationTemplateCollection getEvaluationTemplateCollection(String oql) throws BOSException
    {
        try {
            return getController().getEvaluationTemplateCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}