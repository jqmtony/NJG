package com.kingdee.eas.port.pm.fi;

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
import com.kingdee.eas.port.pm.fi.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillEntryBase;
import com.kingdee.eas.framework.IBillEntryBase;

public class PayRequestBillConfirmEntry extends BillEntryBase implements IPayRequestBillConfirmEntry
{
    public PayRequestBillConfirmEntry()
    {
        super();
        registerInterface(IPayRequestBillConfirmEntry.class, this);
    }
    public PayRequestBillConfirmEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPayRequestBillConfirmEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("315220AF");
    }
    private PayRequestBillConfirmEntryController getController() throws BOSException
    {
        return (PayRequestBillConfirmEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PayRequestBillConfirmEntryInfo getPayRequestBillConfirmEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPayRequestBillConfirmEntryInfo(getContext(), pk);
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
    public PayRequestBillConfirmEntryInfo getPayRequestBillConfirmEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPayRequestBillConfirmEntryInfo(getContext(), pk, selector);
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
    public PayRequestBillConfirmEntryInfo getPayRequestBillConfirmEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPayRequestBillConfirmEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PayRequestBillConfirmEntryCollection getPayRequestBillConfirmEntryCollection() throws BOSException
    {
        try {
            return getController().getPayRequestBillConfirmEntryCollection(getContext());
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
    public PayRequestBillConfirmEntryCollection getPayRequestBillConfirmEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPayRequestBillConfirmEntryCollection(getContext(), view);
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
    public PayRequestBillConfirmEntryCollection getPayRequestBillConfirmEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPayRequestBillConfirmEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}