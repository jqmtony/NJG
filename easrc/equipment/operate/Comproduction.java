package com.kingdee.eas.port.equipment.operate;

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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.port.equipment.operate.app.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class Comproduction extends CoreBillBase implements IComproduction
{
    public Comproduction()
    {
        super();
        registerInterface(IComproduction.class, this);
    }
    public Comproduction(Context ctx)
    {
        super(ctx);
        registerInterface(IComproduction.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("232E84DB");
    }
    private ComproductionController getController() throws BOSException
    {
        return (ComproductionController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ComproductionCollection getComproductionCollection() throws BOSException
    {
        try {
            return getController().getComproductionCollection(getContext());
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
    public ComproductionCollection getComproductionCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getComproductionCollection(getContext(), view);
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
    public ComproductionCollection getComproductionCollection(String oql) throws BOSException
    {
        try {
            return getController().getComproductionCollection(getContext(), oql);
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
    public ComproductionInfo getComproductionInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getComproductionInfo(getContext(), pk);
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
    public ComproductionInfo getComproductionInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getComproductionInfo(getContext(), pk, selector);
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
    public ComproductionInfo getComproductionInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getComproductionInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审核-User defined method
     *@param model model
     */
    public void actionAudit(ComproductionInfo model) throws BOSException
    {
        try {
            getController().actionAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审核-User defined method
     *@param model model
     */
    public void actionUnAudit(ComproductionInfo model) throws BOSException
    {
        try {
            getController().actionUnAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}