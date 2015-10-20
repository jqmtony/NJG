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
import com.kingdee.eas.fdc.basedata.IFDCNoCostSplitBillEntry;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntry;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class SettNoCostSplitEntry extends FDCNoCostSplitBillEntry implements ISettNoCostSplitEntry
{
    public SettNoCostSplitEntry()
    {
        super();
        registerInterface(ISettNoCostSplitEntry.class, this);
    }
    public SettNoCostSplitEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISettNoCostSplitEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7E754873");
    }
    private SettNoCostSplitEntryController getController() throws BOSException
    {
        return (SettNoCostSplitEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public SettNoCostSplitEntryInfo getSettNoCostSplitEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSettNoCostSplitEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public SettNoCostSplitEntryInfo getSettNoCostSplitEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSettNoCostSplitEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql oql
     *@return
     */
    public SettNoCostSplitEntryInfo getSettNoCostSplitEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSettNoCostSplitEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SettNoCostSplitEntryCollection getSettNoCostSplitEntryCollection() throws BOSException
    {
        try {
            return getController().getSettNoCostSplitEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view view
     *@return
     */
    public SettNoCostSplitEntryCollection getSettNoCostSplitEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSettNoCostSplitEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql oql
     *@return
     */
    public SettNoCostSplitEntryCollection getSettNoCostSplitEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSettNoCostSplitEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}