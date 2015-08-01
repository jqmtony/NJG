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
     *取值-System defined method
     *@param pk 取值
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
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
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
     *取值-System defined method
     *@param oql 取值
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
     *取集合-System defined method
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
     *取集合-System defined method
     *@param view 取集合
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
     *取集合-System defined method
     *@param oql 取集合
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
     *引入集团模板-User defined method
     *@param companyId 公司id
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