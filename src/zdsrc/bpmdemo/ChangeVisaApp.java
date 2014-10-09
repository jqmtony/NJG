package com.kingdee.eas.bpmdemo;

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
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.bpmdemo.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class ChangeVisaApp extends CoreBillBase implements IChangeVisaApp
{
    public ChangeVisaApp()
    {
        super();
        registerInterface(IChangeVisaApp.class, this);
    }
    public ChangeVisaApp(Context ctx)
    {
        super(ctx);
        registerInterface(IChangeVisaApp.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("178064D2");
    }
    private ChangeVisaAppController getController() throws BOSException
    {
        return (ChangeVisaAppController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ChangeVisaAppCollection getChangeVisaAppCollection() throws BOSException
    {
        try {
            return getController().getChangeVisaAppCollection(getContext());
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
    public ChangeVisaAppCollection getChangeVisaAppCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getChangeVisaAppCollection(getContext(), view);
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
    public ChangeVisaAppCollection getChangeVisaAppCollection(String oql) throws BOSException
    {
        try {
            return getController().getChangeVisaAppCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ChangeVisaAppInfo getChangeVisaAppInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeVisaAppInfo(getContext(), pk);
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
    public ChangeVisaAppInfo getChangeVisaAppInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeVisaAppInfo(getContext(), pk, selector);
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
    public ChangeVisaAppInfo getChangeVisaAppInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeVisaAppInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}