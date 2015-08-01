package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class VoucherForProject extends FDCBill implements IVoucherForProject
{
    public VoucherForProject()
    {
        super();
        registerInterface(IVoucherForProject.class, this);
    }
    public VoucherForProject(Context ctx)
    {
        super(ctx);
        registerInterface(IVoucherForProject.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DBCAE6AC");
    }
    private VoucherForProjectController getController() throws BOSException
    {
        return (VoucherForProjectController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public VoucherForProjectInfo getVoucherForProjectInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getVoucherForProjectInfo(getContext(), pk);
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
    public VoucherForProjectInfo getVoucherForProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getVoucherForProjectInfo(getContext(), pk, selector);
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
    public VoucherForProjectInfo getVoucherForProjectInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getVoucherForProjectInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public VoucherForProjectCollection getVoucherForProjectCollection() throws BOSException
    {
        try {
            return getController().getVoucherForProjectCollection(getContext());
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
    public VoucherForProjectCollection getVoucherForProjectCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getVoucherForProjectCollection(getContext(), view);
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
    public VoucherForProjectCollection getVoucherForProjectCollection(String oql) throws BOSException
    {
        try {
            return getController().getVoucherForProjectCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}