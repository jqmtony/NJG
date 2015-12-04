package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class PcontractTrackBillEntry extends CoreBillEntryBase implements IPcontractTrackBillEntry
{
    public PcontractTrackBillEntry()
    {
        super();
        registerInterface(IPcontractTrackBillEntry.class, this);
    }
    public PcontractTrackBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPcontractTrackBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("957FA127");
    }
    private PcontractTrackBillEntryController getController() throws BOSException
    {
        return (PcontractTrackBillEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PcontractTrackBillEntryInfo getPcontractTrackBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPcontractTrackBillEntryInfo(getContext(), pk);
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
    public PcontractTrackBillEntryInfo getPcontractTrackBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPcontractTrackBillEntryInfo(getContext(), pk, selector);
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
    public PcontractTrackBillEntryInfo getPcontractTrackBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPcontractTrackBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PcontractTrackBillEntryCollection getPcontractTrackBillEntryCollection() throws BOSException
    {
        try {
            return getController().getPcontractTrackBillEntryCollection(getContext());
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
    public PcontractTrackBillEntryCollection getPcontractTrackBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPcontractTrackBillEntryCollection(getContext(), view);
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
    public PcontractTrackBillEntryCollection getPcontractTrackBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPcontractTrackBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}