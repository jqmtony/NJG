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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;
import com.kingdee.eas.framework.ICoreBase;

public class GuerdonOfPayReqBill extends CoreBase implements IGuerdonOfPayReqBill
{
    public GuerdonOfPayReqBill()
    {
        super();
        registerInterface(IGuerdonOfPayReqBill.class, this);
    }
    public GuerdonOfPayReqBill(Context ctx)
    {
        super(ctx);
        registerInterface(IGuerdonOfPayReqBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B32DB881");
    }
    private GuerdonOfPayReqBillController getController() throws BOSException
    {
        return (GuerdonOfPayReqBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public GuerdonOfPayReqBillInfo getGuerdonOfPayReqBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getGuerdonOfPayReqBillInfo(getContext(), pk);
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
    public GuerdonOfPayReqBillInfo getGuerdonOfPayReqBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getGuerdonOfPayReqBillInfo(getContext(), pk, selector);
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
    public GuerdonOfPayReqBillInfo getGuerdonOfPayReqBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getGuerdonOfPayReqBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public GuerdonOfPayReqBillCollection getGuerdonOfPayReqBillCollection() throws BOSException
    {
        try {
            return getController().getGuerdonOfPayReqBillCollection(getContext());
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
    public GuerdonOfPayReqBillCollection getGuerdonOfPayReqBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getGuerdonOfPayReqBillCollection(getContext(), view);
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
    public GuerdonOfPayReqBillCollection getGuerdonOfPayReqBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getGuerdonOfPayReqBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *重算金额-User defined method
     *@param payReqID 付款申请单ID
     */
    public void reCalcAmount(String payReqID) throws BOSException, EASBizException
    {
        try {
            getController().reCalcAmount(getContext(), payReqID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}