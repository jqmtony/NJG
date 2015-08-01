package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ObjectBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IObjectBase;

public class BeforeAccountView extends ObjectBase implements IBeforeAccountView
{
    public BeforeAccountView()
    {
        super();
        registerInterface(IBeforeAccountView.class, this);
    }
    public BeforeAccountView(Context ctx)
    {
        super(ctx);
        registerInterface(IBeforeAccountView.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("02DED8C1");
    }
    private BeforeAccountViewController getController() throws BOSException
    {
        return (BeforeAccountViewController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public BeforeAccountViewInfo getBeforeAccountViewInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBeforeAccountViewInfo(getContext(), pk);
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
    public BeforeAccountViewInfo getBeforeAccountViewInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBeforeAccountViewInfo(getContext(), pk, selector);
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
    public BeforeAccountViewInfo getBeforeAccountViewInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBeforeAccountViewInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public BeforeAccountViewCollection getBeforeAccountViewCollection() throws BOSException
    {
        try {
            return getController().getBeforeAccountViewCollection(getContext());
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
    public BeforeAccountViewCollection getBeforeAccountViewCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBeforeAccountViewCollection(getContext(), view);
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
    public BeforeAccountViewCollection getBeforeAccountViewCollection(String oql) throws BOSException
    {
        try {
            return getController().getBeforeAccountViewCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���뼯��ģ��-User defined method
     *@param companyId ��˾id
     */
    public void impTemplate(String companyId) throws BOSException, EASBizException
    {
        try {
            getController().impTemplate(getContext(), companyId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}