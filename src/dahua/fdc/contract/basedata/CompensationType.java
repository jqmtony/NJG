package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class CompensationType extends FDCDataBase implements ICompensationType
{
    public CompensationType()
    {
        super();
        registerInterface(ICompensationType.class, this);
    }
    public CompensationType(Context ctx)
    {
        super(ctx);
        registerInterface(ICompensationType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A4EF1DA6");
    }
    private CompensationTypeController getController() throws BOSException
    {
        return (CompensationTypeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public CompensationTypeInfo getCompensationTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensationTypeInfo(getContext(), pk);
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
    public CompensationTypeInfo getCompensationTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensationTypeInfo(getContext(), pk, selector);
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
    public CompensationTypeInfo getCompensationTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensationTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CompensationTypeCollection getCompensationTypeCollection() throws BOSException
    {
        try {
            return getController().getCompensationTypeCollection(getContext());
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
    public CompensationTypeCollection getCompensationTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCompensationTypeCollection(getContext(), view);
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
    public CompensationTypeCollection getCompensationTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getCompensationTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}