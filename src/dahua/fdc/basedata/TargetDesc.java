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
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class TargetDesc extends CoreBase implements ITargetDesc
{
    public TargetDesc()
    {
        super();
        registerInterface(ITargetDesc.class, this);
    }
    public TargetDesc(Context ctx)
    {
        super(ctx);
        registerInterface(ITargetDesc.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("48234434");
    }
    private TargetDescController getController() throws BOSException
    {
        return (TargetDescController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TargetDescInfo getTargetDescInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTargetDescInfo(getContext(), pk);
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
    public TargetDescInfo getTargetDescInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTargetDescInfo(getContext(), pk, selector);
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
    public TargetDescInfo getTargetDescInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTargetDescInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TargetDescCollection getTargetDescCollection() throws BOSException
    {
        try {
            return getController().getTargetDescCollection(getContext());
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
    public TargetDescCollection getTargetDescCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTargetDescCollection(getContext(), view);
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
    public TargetDescCollection getTargetDescCollection(String oql) throws BOSException
    {
        try {
            return getController().getTargetDescCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}