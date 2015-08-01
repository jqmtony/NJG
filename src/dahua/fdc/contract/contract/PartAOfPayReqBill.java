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

public class PartAOfPayReqBill extends CoreBase implements IPartAOfPayReqBill
{
    public PartAOfPayReqBill()
    {
        super();
        registerInterface(IPartAOfPayReqBill.class, this);
    }
    public PartAOfPayReqBill(Context ctx)
    {
        super(ctx);
        registerInterface(IPartAOfPayReqBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FE27AA27");
    }
    private PartAOfPayReqBillController getController() throws BOSException
    {
        return (PartAOfPayReqBillController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PartAOfPayReqBillInfo getPartAOfPayReqBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPartAOfPayReqBillInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public PartAOfPayReqBillInfo getPartAOfPayReqBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPartAOfPayReqBillInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public PartAOfPayReqBillInfo getPartAOfPayReqBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPartAOfPayReqBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PartAOfPayReqBillCollection getPartAOfPayReqBillCollection() throws BOSException
    {
        try {
            return getController().getPartAOfPayReqBillCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public PartAOfPayReqBillCollection getPartAOfPayReqBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPartAOfPayReqBillCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public PartAOfPayReqBillCollection getPartAOfPayReqBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getPartAOfPayReqBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}