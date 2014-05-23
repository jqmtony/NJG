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

public class SupEvaluateTemplate extends DataBase implements ISupEvaluateTemplate
{
    public SupEvaluateTemplate()
    {
        super();
        registerInterface(ISupEvaluateTemplate.class, this);
    }
    public SupEvaluateTemplate(Context ctx)
    {
        super(ctx);
        registerInterface(ISupEvaluateTemplate.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FC5578C0");
    }
    private SupEvaluateTemplateController getController() throws BOSException
    {
        return (SupEvaluateTemplateController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SupEvaluateTemplateInfo getSupEvaluateTemplateInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSupEvaluateTemplateInfo(getContext(), pk);
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
    public SupEvaluateTemplateInfo getSupEvaluateTemplateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSupEvaluateTemplateInfo(getContext(), pk, selector);
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
    public SupEvaluateTemplateInfo getSupEvaluateTemplateInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSupEvaluateTemplateInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SupEvaluateTemplateCollection getSupEvaluateTemplateCollection() throws BOSException
    {
        try {
            return getController().getSupEvaluateTemplateCollection(getContext());
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
    public SupEvaluateTemplateCollection getSupEvaluateTemplateCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSupEvaluateTemplateCollection(getContext(), view);
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
    public SupEvaluateTemplateCollection getSupEvaluateTemplateCollection(String oql) throws BOSException
    {
        try {
            return getController().getSupEvaluateTemplateCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}