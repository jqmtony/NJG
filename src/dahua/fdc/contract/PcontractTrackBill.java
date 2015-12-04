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
     *ȡ����-System defined method
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
     *ȡ����-System defined method
     *@param view ȡ����
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
     *ȡ����-System defined method
     *@param oql ȡ����
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
     *ȡֵ-System defined method
     *@param pk ȡֵ
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
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
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
     *ȡֵ-System defined method
     *@param oql ȡֵ
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
     *���-User defined method
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
     *�����-User defined method
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
     *�޶�-User defined method
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