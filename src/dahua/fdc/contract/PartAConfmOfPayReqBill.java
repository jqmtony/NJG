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

public class PartAConfmOfPayReqBill extends CoreBase implements IPartAConfmOfPayReqBill
{
    public PartAConfmOfPayReqBill()
    {
        super();
        registerInterface(IPartAConfmOfPayReqBill.class, this);
    }
    public PartAConfmOfPayReqBill(Context ctx)
    {
        super(ctx);
        registerInterface(IPartAConfmOfPayReqBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1369848A");
    }
    private PartAConfmOfPayReqBillController getController() throws BOSException
    {
        return (PartAConfmOfPayReqBillController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PartAConfmOfPayReqBillInfo getPartAConfmOfPayReqBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPartAConfmOfPayReqBillInfo(getContext(), pk);
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
    public PartAConfmOfPayReqBillInfo getPartAConfmOfPayReqBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPartAConfmOfPayReqBillInfo(getContext(), pk, selector);
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
    public PartAConfmOfPayReqBillInfo getPartAConfmOfPayReqBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPartAConfmOfPayReqBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PartAConfmOfPayReqBillCollection getPartAConfmOfPayReqBillCollection() throws BOSException
    {
        try {
            return getController().getPartAConfmOfPayReqBillCollection(getContext());
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
    public PartAConfmOfPayReqBillCollection getPartAConfmOfPayReqBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPartAConfmOfPayReqBillCollection(getContext(), view);
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
    public PartAConfmOfPayReqBillCollection getPartAConfmOfPayReqBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getPartAConfmOfPayReqBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}