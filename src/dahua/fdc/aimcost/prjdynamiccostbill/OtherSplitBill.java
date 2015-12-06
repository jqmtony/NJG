package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

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
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class OtherSplitBill extends CoreBillBase implements IOtherSplitBill
{
    public OtherSplitBill()
    {
        super();
        registerInterface(IOtherSplitBill.class, this);
    }
    public OtherSplitBill(Context ctx)
    {
        super(ctx);
        registerInterface(IOtherSplitBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("245D16B7");
    }
    private OtherSplitBillController getController() throws BOSException
    {
        return (OtherSplitBillController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public OtherSplitBillCollection getOtherSplitBillCollection() throws BOSException
    {
        try {
            return getController().getOtherSplitBillCollection(getContext());
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
    public OtherSplitBillCollection getOtherSplitBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOtherSplitBillCollection(getContext(), view);
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
    public OtherSplitBillCollection getOtherSplitBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getOtherSplitBillCollection(getContext(), oql);
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
    public OtherSplitBillInfo getOtherSplitBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherSplitBillInfo(getContext(), pk);
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
    public OtherSplitBillInfo getOtherSplitBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherSplitBillInfo(getContext(), pk, selector);
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
    public OtherSplitBillInfo getOtherSplitBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherSplitBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审核-User defined method
     *@param model model
     */
    public void audit(OtherSplitBillInfo model) throws BOSException
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
    public void unAudit(OtherSplitBillInfo model) throws BOSException
    {
        try {
            getController().unAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}