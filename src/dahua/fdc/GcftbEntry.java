package com.kingdee.eas.fdc.gcftbiaoa;

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
import com.kingdee.eas.fdc.gcftbiaoa.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class GcftbEntry extends CoreBillEntryBase implements IGcftbEntry
{
    public GcftbEntry()
    {
        super();
        registerInterface(IGcftbEntry.class, this);
    }
    public GcftbEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IGcftbEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("58157E21");
    }
    private GcftbEntryController getController() throws BOSException
    {
        return (GcftbEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public GcftbEntryInfo getGcftbEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getGcftbEntryInfo(getContext(), pk);
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
    public GcftbEntryInfo getGcftbEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getGcftbEntryInfo(getContext(), pk, selector);
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
    public GcftbEntryInfo getGcftbEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getGcftbEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public GcftbEntryCollection getGcftbEntryCollection() throws BOSException
    {
        try {
            return getController().getGcftbEntryCollection(getContext());
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
    public GcftbEntryCollection getGcftbEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getGcftbEntryCollection(getContext(), view);
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
    public GcftbEntryCollection getGcftbEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getGcftbEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}