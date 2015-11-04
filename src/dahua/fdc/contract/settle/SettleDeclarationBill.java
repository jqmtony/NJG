package com.kingdee.eas.fdc.contract.settle;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.settle.app.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.ICoreBillBase;

public class SettleDeclarationBill extends CoreBillBase implements ISettleDeclarationBill
{
    public SettleDeclarationBill()
    {
        super();
        registerInterface(ISettleDeclarationBill.class, this);
    }
    public SettleDeclarationBill(Context ctx)
    {
        super(ctx);
        registerInterface(ISettleDeclarationBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("19B1A7B4");
    }
    private SettleDeclarationBillController getController() throws BOSException
    {
        return (SettleDeclarationBillController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SettleDeclarationBillCollection getSettleDeclarationBillCollection() throws BOSException
    {
        try {
            return getController().getSettleDeclarationBillCollection(getContext());
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
    public SettleDeclarationBillCollection getSettleDeclarationBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSettleDeclarationBillCollection(getContext(), view);
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
    public SettleDeclarationBillCollection getSettleDeclarationBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getSettleDeclarationBillCollection(getContext(), oql);
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
    public SettleDeclarationBillInfo getSettleDeclarationBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSettleDeclarationBillInfo(getContext(), pk);
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
    public SettleDeclarationBillInfo getSettleDeclarationBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSettleDeclarationBillInfo(getContext(), pk, selector);
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
    public SettleDeclarationBillInfo getSettleDeclarationBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSettleDeclarationBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *送审-User defined method
     *@param model model
     */
    public void InTrial(BOSUuid model) throws BOSException
    {
        try {
            getController().InTrial(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审定-User defined method
     *@param model model
     */
    public void Approved(BOSUuid model) throws BOSException
    {
        try {
            getController().Approved(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审核-User defined method
     *@param model model
     */
    public void Audit(BOSUuid model) throws BOSException
    {
        try {
            getController().Audit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审核-User defined method
     *@param model model
     */
    public void UnAudit(BOSUuid model) throws BOSException
    {
        try {
            getController().UnAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}