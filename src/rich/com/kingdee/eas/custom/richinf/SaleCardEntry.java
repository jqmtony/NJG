package com.kingdee.eas.custom.richinf;

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
import com.kingdee.eas.custom.richinf.app.*;

public class SaleCardEntry extends CoreBillEntryBase implements ISaleCardEntry
{
    public SaleCardEntry()
    {
        super();
        registerInterface(ISaleCardEntry.class, this);
    }
    public SaleCardEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISaleCardEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6259986D");
    }
    private SaleCardEntryController getController() throws BOSException
    {
        return (SaleCardEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public SaleCardEntryInfo getSaleCardEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSaleCardEntryInfo(getContext(), pk);
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
    public SaleCardEntryInfo getSaleCardEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSaleCardEntryInfo(getContext(), pk, selector);
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
    public SaleCardEntryInfo getSaleCardEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSaleCardEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SaleCardEntryCollection getSaleCardEntryCollection() throws BOSException
    {
        try {
            return getController().getSaleCardEntryCollection(getContext());
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
    public SaleCardEntryCollection getSaleCardEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSaleCardEntryCollection(getContext(), view);
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
    public SaleCardEntryCollection getSaleCardEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSaleCardEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}