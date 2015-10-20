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

public class SupplierContentEntry extends CoreBillEntryBase implements ISupplierContentEntry
{
    public SupplierContentEntry()
    {
        super();
        registerInterface(ISupplierContentEntry.class, this);
    }
    public SupplierContentEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISupplierContentEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0F7EDA20");
    }
    private SupplierContentEntryController getController() throws BOSException
    {
        return (SupplierContentEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SupplierContentEntryInfo getSupplierContentEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierContentEntryInfo(getContext(), pk);
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
    public SupplierContentEntryInfo getSupplierContentEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierContentEntryInfo(getContext(), pk, selector);
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
    public SupplierContentEntryInfo getSupplierContentEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierContentEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SupplierContentEntryCollection getSupplierContentEntryCollection() throws BOSException
    {
        try {
            return getController().getSupplierContentEntryCollection(getContext());
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
    public SupplierContentEntryCollection getSupplierContentEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSupplierContentEntryCollection(getContext(), view);
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
    public SupplierContentEntryCollection getSupplierContentEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSupplierContentEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}