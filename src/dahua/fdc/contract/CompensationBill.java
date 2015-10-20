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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class CompensationBill extends FDCBill implements ICompensationBill
{
    public CompensationBill()
    {
        super();
        registerInterface(ICompensationBill.class, this);
    }
    public CompensationBill(Context ctx)
    {
        super(ctx);
        registerInterface(ICompensationBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("40116BBC");
    }
    private CompensationBillController getController() throws BOSException
    {
        return (CompensationBillController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public CompensationBillCollection getCompensationBillCollection() throws BOSException
    {
        try {
            return getController().getCompensationBillCollection(getContext());
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
    public CompensationBillCollection getCompensationBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCompensationBillCollection(getContext(), view);
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
    public CompensationBillCollection getCompensationBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getCompensationBillCollection(getContext(), oql);
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
    public CompensationBillInfo getCompensationBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensationBillInfo(getContext(), pk);
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
    public CompensationBillInfo getCompensationBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensationBillInfo(getContext(), pk, selector);
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
    public CompensationBillInfo getCompensationBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensationBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}