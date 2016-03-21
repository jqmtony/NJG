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

public class IndoorengEntry extends CoreBillEntryBase implements IIndoorengEntry
{
    public IndoorengEntry()
    {
        super();
        registerInterface(IIndoorengEntry.class, this);
    }
    public IndoorengEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IIndoorengEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C07D8FCE");
    }
    private IndoorengEntryController getController() throws BOSException
    {
        return (IndoorengEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public IndoorengEntryInfo getIndoorengEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getIndoorengEntryInfo(getContext(), pk);
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
    public IndoorengEntryInfo getIndoorengEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getIndoorengEntryInfo(getContext(), pk, selector);
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
    public IndoorengEntryInfo getIndoorengEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getIndoorengEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public IndoorengEntryCollection getIndoorengEntryCollection() throws BOSException
    {
        try {
            return getController().getIndoorengEntryCollection(getContext());
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
    public IndoorengEntryCollection getIndoorengEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getIndoorengEntryCollection(getContext(), view);
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
    public IndoorengEntryCollection getIndoorengEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getIndoorengEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}