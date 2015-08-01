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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.IFDCNoCostSplitBill;
import com.kingdee.eas.fdc.contract.app.*;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBill;

public class SettNoCostSplit extends FDCNoCostSplitBill implements ISettNoCostSplit
{
    public SettNoCostSplit()
    {
        super();
        registerInterface(ISettNoCostSplit.class, this);
    }
    public SettNoCostSplit(Context ctx)
    {
        super(ctx);
        registerInterface(ISettNoCostSplit.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2E637F3F");
    }
    private SettNoCostSplitController getController() throws BOSException
    {
        return (SettNoCostSplitController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public SettNoCostSplitInfo getSettNoCostSplitInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSettNoCostSplitInfo(getContext(), pk);
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
    public SettNoCostSplitInfo getSettNoCostSplitInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSettNoCostSplitInfo(getContext(), pk, selector);
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
    public SettNoCostSplitInfo getSettNoCostSplitInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSettNoCostSplitInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public SettNoCostSplitCollection getSettNoCostSplitCollection() throws BOSException
    {
        try {
            return getController().getSettNoCostSplitCollection(getContext());
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
    public SettNoCostSplitCollection getSettNoCostSplitCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSettNoCostSplitCollection(getContext(), view);
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
    public SettNoCostSplitCollection getSettNoCostSplitCollection(String oql) throws BOSException
    {
        try {
            return getController().getSettNoCostSplitCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}