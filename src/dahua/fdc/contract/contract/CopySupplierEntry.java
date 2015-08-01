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

public class CopySupplierEntry extends CoreBillEntryBase implements ICopySupplierEntry
{
    public CopySupplierEntry()
    {
        super();
        registerInterface(ICopySupplierEntry.class, this);
    }
    public CopySupplierEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ICopySupplierEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("169D3FB6");
    }
    private CopySupplierEntryController getController() throws BOSException
    {
        return (CopySupplierEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public CopySupplierEntryInfo getCopySupplierEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCopySupplierEntryInfo(getContext(), pk);
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
    public CopySupplierEntryInfo getCopySupplierEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCopySupplierEntryInfo(getContext(), pk, selector);
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
    public CopySupplierEntryInfo getCopySupplierEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCopySupplierEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public CopySupplierEntryCollection getCopySupplierEntryCollection() throws BOSException
    {
        try {
            return getController().getCopySupplierEntryCollection(getContext());
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
    public CopySupplierEntryCollection getCopySupplierEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCopySupplierEntryCollection(getContext(), view);
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
    public CopySupplierEntryCollection getCopySupplierEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getCopySupplierEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}