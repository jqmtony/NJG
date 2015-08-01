package com.kingdee.eas.fdc.basedata;

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
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillEntryBase;
import com.kingdee.eas.framework.IBillEntryBase;

public class FDCNoCostSplitBillEntry extends BillEntryBase implements IFDCNoCostSplitBillEntry
{
    public FDCNoCostSplitBillEntry()
    {
        super();
        registerInterface(IFDCNoCostSplitBillEntry.class, this);
    }
    public FDCNoCostSplitBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCNoCostSplitBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9D9B1F52");
    }
    private FDCNoCostSplitBillEntryController getController() throws BOSException
    {
        return (FDCNoCostSplitBillEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public FDCNoCostSplitBillEntryInfo getFDCNoCostSplitBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCNoCostSplitBillEntryInfo(getContext(), pk);
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
    public FDCNoCostSplitBillEntryInfo getFDCNoCostSplitBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCNoCostSplitBillEntryInfo(getContext(), pk, selector);
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
    public FDCNoCostSplitBillEntryInfo getFDCNoCostSplitBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCNoCostSplitBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public FDCNoCostSplitBillEntryCollection getFDCNoCostSplitBillEntryCollection() throws BOSException
    {
        try {
            return getController().getFDCNoCostSplitBillEntryCollection(getContext());
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
    public FDCNoCostSplitBillEntryCollection getFDCNoCostSplitBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCNoCostSplitBillEntryCollection(getContext(), view);
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
    public FDCNoCostSplitBillEntryCollection getFDCNoCostSplitBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCNoCostSplitBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}