package com.kingdee.eas.bpmdemo;

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
import com.kingdee.eas.bpmdemo.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ChangeVisaAppEntry extends CoreBillEntryBase implements IChangeVisaAppEntry
{
    public ChangeVisaAppEntry()
    {
        super();
        registerInterface(IChangeVisaAppEntry.class, this);
    }
    public ChangeVisaAppEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IChangeVisaAppEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A777E6C0");
    }
    private ChangeVisaAppEntryController getController() throws BOSException
    {
        return (ChangeVisaAppEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ChangeVisaAppEntryInfo getChangeVisaAppEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeVisaAppEntryInfo(getContext(), pk);
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
    public ChangeVisaAppEntryInfo getChangeVisaAppEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeVisaAppEntryInfo(getContext(), pk, selector);
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
    public ChangeVisaAppEntryInfo getChangeVisaAppEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeVisaAppEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ChangeVisaAppEntryCollection getChangeVisaAppEntryCollection() throws BOSException
    {
        try {
            return getController().getChangeVisaAppEntryCollection(getContext());
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
    public ChangeVisaAppEntryCollection getChangeVisaAppEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getChangeVisaAppEntryCollection(getContext(), view);
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
    public ChangeVisaAppEntryCollection getChangeVisaAppEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getChangeVisaAppEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}