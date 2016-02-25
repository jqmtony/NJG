package com.kingdee.eas.fdc.gcftbiaoa;

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
import com.kingdee.eas.fdc.gcftbiaoa.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class Confirquantites extends CoreBillBase implements IConfirquantites
{
    public Confirquantites()
    {
        super();
        registerInterface(IConfirquantites.class, this);
    }
    public Confirquantites(Context ctx)
    {
        super(ctx);
        registerInterface(IConfirquantites.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("77AF44C8");
    }
    private ConfirquantitesController getController() throws BOSException
    {
        return (ConfirquantitesController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ConfirquantitesCollection getConfirquantitesCollection() throws BOSException
    {
        try {
            return getController().getConfirquantitesCollection(getContext());
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
    public ConfirquantitesCollection getConfirquantitesCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getConfirquantitesCollection(getContext(), view);
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
    public ConfirquantitesCollection getConfirquantitesCollection(String oql) throws BOSException
    {
        try {
            return getController().getConfirquantitesCollection(getContext(), oql);
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
    public ConfirquantitesInfo getConfirquantitesInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getConfirquantitesInfo(getContext(), pk);
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
    public ConfirquantitesInfo getConfirquantitesInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getConfirquantitesInfo(getContext(), pk, selector);
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
    public ConfirquantitesInfo getConfirquantitesInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getConfirquantitesInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批-User defined method
     *@param model model
     */
    public void actionAudit(ConfirquantitesInfo model) throws BOSException
    {
        try {
            getController().actionAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批-User defined method
     *@param model model
     */
    public void actionUnAudit(ConfirquantitesInfo model) throws BOSException
    {
        try {
            getController().actionUnAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}