package com.kingdee.eas.fdc.contract;

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
import com.kingdee.eas.fdc.contract.app.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class PcontractTrackBill extends CoreBillBase implements IPcontractTrackBill
{
    public PcontractTrackBill()
    {
        super();
        registerInterface(IPcontractTrackBill.class, this);
    }
    public PcontractTrackBill(Context ctx)
    {
        super(ctx);
        registerInterface(IPcontractTrackBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F049860B");
    }
    private PcontractTrackBillController getController() throws BOSException
    {
        return (PcontractTrackBillController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PcontractTrackBillCollection getPcontractTrackBillCollection() throws BOSException
    {
        try {
            return getController().getPcontractTrackBillCollection(getContext());
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
    public PcontractTrackBillCollection getPcontractTrackBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPcontractTrackBillCollection(getContext(), view);
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
    public PcontractTrackBillCollection getPcontractTrackBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getPcontractTrackBillCollection(getContext(), oql);
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
    public PcontractTrackBillInfo getPcontractTrackBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPcontractTrackBillInfo(getContext(), pk);
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
    public PcontractTrackBillInfo getPcontractTrackBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPcontractTrackBillInfo(getContext(), pk, selector);
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
    public PcontractTrackBillInfo getPcontractTrackBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPcontractTrackBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审核-User defined method
     *@param model model
     */
    public void audit(PcontractTrackBillInfo model) throws BOSException
    {
        try {
            getController().audit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审核-User defined method
     *@param model model
     */
    public void unaudit(PcontractTrackBillInfo model) throws BOSException
    {
        try {
            getController().unaudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *修订-User defined method
     *@param model model
     */
    public void fix(PcontractTrackBillInfo model) throws BOSException
    {
        try {
            getController().fix(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}