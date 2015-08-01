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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class InvalidCostReason extends TreeBase implements IInvalidCostReason
{
    public InvalidCostReason()
    {
        super();
        registerInterface(IInvalidCostReason.class, this);
    }
    public InvalidCostReason(Context ctx)
    {
        super(ctx);
        registerInterface(IInvalidCostReason.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F042D116");
    }
    private InvalidCostReasonController getController() throws BOSException
    {
        return (InvalidCostReasonController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public InvalidCostReasonInfo getInvalidCostReasonInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInvalidCostReasonInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public InvalidCostReasonInfo getInvalidCostReasonInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInvalidCostReasonInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public InvalidCostReasonInfo getInvalidCostReasonInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInvalidCostReasonInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public InvalidCostReasonCollection getInvalidCostReasonCollection() throws BOSException
    {
        try {
            return getController().getInvalidCostReasonCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public InvalidCostReasonCollection getInvalidCostReasonCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInvalidCostReasonCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public InvalidCostReasonCollection getInvalidCostReasonCollection(String oql) throws BOSException
    {
        try {
            return getController().getInvalidCostReasonCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *∆Ù”√-User defined method
     *@param ctPK ctPK
     *@return
     */
    public boolean enabled(IObjectPK ctPK) throws BOSException, EASBizException
    {
        try {
            return getController().enabled(getContext(), ctPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Ω˚”√-User defined method
     *@param ctPK ctPK
     *@return
     */
    public boolean disEnabled(IObjectPK ctPK) throws BOSException, EASBizException
    {
        try {
            return getController().disEnabled(getContext(), ctPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}