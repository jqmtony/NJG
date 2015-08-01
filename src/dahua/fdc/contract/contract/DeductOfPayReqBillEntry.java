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

public class DeductOfPayReqBillEntry extends CoreBase implements IDeductOfPayReqBillEntry
{
    public DeductOfPayReqBillEntry()
    {
        super();
        registerInterface(IDeductOfPayReqBillEntry.class, this);
    }
    public DeductOfPayReqBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IDeductOfPayReqBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("97C05080");
    }
    private DeductOfPayReqBillEntryController getController() throws BOSException
    {
        return (DeductOfPayReqBillEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public DeductOfPayReqBillEntryInfo getDeductOfPayReqBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDeductOfPayReqBillEntryInfo(getContext(), pk);
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
    public DeductOfPayReqBillEntryInfo getDeductOfPayReqBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDeductOfPayReqBillEntryInfo(getContext(), pk, selector);
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
    public DeductOfPayReqBillEntryInfo getDeductOfPayReqBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDeductOfPayReqBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public DeductOfPayReqBillEntryCollection getDeductOfPayReqBillEntryCollection() throws BOSException
    {
        try {
            return getController().getDeductOfPayReqBillEntryCollection(getContext());
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
    public DeductOfPayReqBillEntryCollection getDeductOfPayReqBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDeductOfPayReqBillEntryCollection(getContext(), view);
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
    public DeductOfPayReqBillEntryCollection getDeductOfPayReqBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getDeductOfPayReqBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}