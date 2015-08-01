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

public class GuerdonBill extends FDCBill implements IGuerdonBill
{
    public GuerdonBill()
    {
        super();
        registerInterface(IGuerdonBill.class, this);
    }
    public GuerdonBill(Context ctx)
    {
        super(ctx);
        registerInterface(IGuerdonBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B7408454");
    }
    private GuerdonBillController getController() throws BOSException
    {
        return (GuerdonBillController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public GuerdonBillCollection getGuerdonBillCollection() throws BOSException
    {
        try {
            return getController().getGuerdonBillCollection(getContext());
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
    public GuerdonBillCollection getGuerdonBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getGuerdonBillCollection(getContext(), view);
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
    public GuerdonBillCollection getGuerdonBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getGuerdonBillCollection(getContext(), oql);
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
    public GuerdonBillInfo getGuerdonBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getGuerdonBillInfo(getContext(), pk);
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
    public GuerdonBillInfo getGuerdonBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getGuerdonBillInfo(getContext(), pk, selector);
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
    public GuerdonBillInfo getGuerdonBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getGuerdonBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}