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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class BackdropColor extends DataBase implements IBackdropColor
{
    public BackdropColor()
    {
        super();
        registerInterface(IBackdropColor.class, this);
    }
    public BackdropColor(Context ctx)
    {
        super(ctx);
        registerInterface(IBackdropColor.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A153DEFB");
    }
    private BackdropColorController getController() throws BOSException
    {
        return (BackdropColorController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public BackdropColorInfo getBackdropColorInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBackdropColorInfo(getContext(), pk);
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
    public BackdropColorInfo getBackdropColorInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBackdropColorInfo(getContext(), pk, selector);
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
    public BackdropColorInfo getBackdropColorInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBackdropColorInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public BackdropColorCollection getBackdropColorCollection() throws BOSException
    {
        try {
            return getController().getBackdropColorCollection(getContext());
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
    public BackdropColorCollection getBackdropColorCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBackdropColorCollection(getContext(), view);
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
    public BackdropColorCollection getBackdropColorCollection(String oql) throws BOSException
    {
        try {
            return getController().getBackdropColorCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}