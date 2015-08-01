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

public class ConChangeNoCostSplit extends FDCNoCostSplitBill implements IConChangeNoCostSplit
{
    public ConChangeNoCostSplit()
    {
        super();
        registerInterface(IConChangeNoCostSplit.class, this);
    }
    public ConChangeNoCostSplit(Context ctx)
    {
        super(ctx);
        registerInterface(IConChangeNoCostSplit.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E7C97E15");
    }
    private ConChangeNoCostSplitController getController() throws BOSException
    {
        return (ConChangeNoCostSplitController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public ConChangeNoCostSplitInfo getConChangeNoCostSplitInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getConChangeNoCostSplitInfo(getContext(), pk);
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
    public ConChangeNoCostSplitInfo getConChangeNoCostSplitInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getConChangeNoCostSplitInfo(getContext(), pk, selector);
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
    public ConChangeNoCostSplitInfo getConChangeNoCostSplitInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getConChangeNoCostSplitInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ConChangeNoCostSplitCollection getConChangeNoCostSplitCollection() throws BOSException
    {
        try {
            return getController().getConChangeNoCostSplitCollection(getContext());
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
    public ConChangeNoCostSplitCollection getConChangeNoCostSplitCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getConChangeNoCostSplitCollection(getContext(), view);
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
    public ConChangeNoCostSplitCollection getConChangeNoCostSplitCollection(String oql) throws BOSException
    {
        try {
            return getController().getConChangeNoCostSplitCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}