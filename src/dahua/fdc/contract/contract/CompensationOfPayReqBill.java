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

public class CompensationOfPayReqBill extends CoreBase implements ICompensationOfPayReqBill
{
    public CompensationOfPayReqBill()
    {
        super();
        registerInterface(ICompensationOfPayReqBill.class, this);
    }
    public CompensationOfPayReqBill(Context ctx)
    {
        super(ctx);
        registerInterface(ICompensationOfPayReqBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("67F4B7E9");
    }
    private CompensationOfPayReqBillController getController() throws BOSException
    {
        return (CompensationOfPayReqBillController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public CompensationOfPayReqBillInfo getCompensationOfPayReqBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensationOfPayReqBillInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public CompensationOfPayReqBillInfo getCompensationOfPayReqBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensationOfPayReqBillInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public CompensationOfPayReqBillInfo getCompensationOfPayReqBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensationOfPayReqBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public CompensationOfPayReqBillCollection getCompensationOfPayReqBillCollection() throws BOSException
    {
        try {
            return getController().getCompensationOfPayReqBillCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public CompensationOfPayReqBillCollection getCompensationOfPayReqBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCompensationOfPayReqBillCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public CompensationOfPayReqBillCollection getCompensationOfPayReqBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getCompensationOfPayReqBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *������-User defined method
     *@param payReqID �������뵥ID
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