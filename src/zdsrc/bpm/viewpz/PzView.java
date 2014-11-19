package com.kingdee.eas.bpm.viewpz;

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
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.eas.bpm.viewpz.app.*;

public class PzView extends DataBase implements IPzView
{
    public PzView()
    {
        super();
        registerInterface(IPzView.class, this);
    }
    public PzView(Context ctx)
    {
        super(ctx);
        registerInterface(IPzView.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A174A7C7");
    }
    private PzViewController getController() throws BOSException
    {
        return (PzViewController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PzViewInfo getPzViewInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPzViewInfo(getContext(), pk);
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
    public PzViewInfo getPzViewInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPzViewInfo(getContext(), pk, selector);
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
    public PzViewInfo getPzViewInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPzViewInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PzViewCollection getPzViewCollection() throws BOSException
    {
        try {
            return getController().getPzViewCollection(getContext());
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
    public PzViewCollection getPzViewCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPzViewCollection(getContext(), view);
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
    public PzViewCollection getPzViewCollection(String oql) throws BOSException
    {
        try {
            return getController().getPzViewCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}