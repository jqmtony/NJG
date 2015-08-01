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

public class ConChangeNoCostSplitEntry extends FDCNoCostSplitBillEntry implements IConChangeNoCostSplitEntry
{
    public ConChangeNoCostSplitEntry()
    {
        super();
        registerInterface(IConChangeNoCostSplitEntry.class, this);
    }
    public ConChangeNoCostSplitEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IConChangeNoCostSplitEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B84B1F5D");
    }
    private ConChangeNoCostSplitEntryController getController() throws BOSException
    {
        return (ConChangeNoCostSplitEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public ConChangeNoCostSplitEntryInfo getConChangeNoCostSplitEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getConChangeNoCostSplitEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public ConChangeNoCostSplitEntryInfo getConChangeNoCostSplitEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getConChangeNoCostSplitEntryInfo(getContext(), pk, selector);
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
    public ConChangeNoCostSplitEntryInfo getConChangeNoCostSplitEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getConChangeNoCostSplitEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ConChangeNoCostSplitEntryCollection getConChangeNoCostSplitEntryCollection() throws BOSException
    {
        try {
            return getController().getConChangeNoCostSplitEntryCollection(getContext());
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
    public ConChangeNoCostSplitEntryCollection getConChangeNoCostSplitEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getConChangeNoCostSplitEntryCollection(getContext(), view);
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
    public ConChangeNoCostSplitEntryCollection getConChangeNoCostSplitEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getConChangeNoCostSplitEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}