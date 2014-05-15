package com.kingdee.eas.port.markesupplier.subase;

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
import com.kingdee.eas.port.markesupplier.subase.app.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class MarktQuaLevel extends DataBase implements IMarktQuaLevel
{
    public MarktQuaLevel()
    {
        super();
        registerInterface(IMarktQuaLevel.class, this);
    }
    public MarktQuaLevel(Context ctx)
    {
        super(ctx);
        registerInterface(IMarktQuaLevel.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("061CC058");
    }
    private MarktQuaLevelController getController() throws BOSException
    {
        return (MarktQuaLevelController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public MarktQuaLevelInfo getMarktQuaLevelInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarktQuaLevelInfo(getContext(), pk);
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
    public MarktQuaLevelInfo getMarktQuaLevelInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarktQuaLevelInfo(getContext(), pk, selector);
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
    public MarktQuaLevelInfo getMarktQuaLevelInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarktQuaLevelInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public MarktQuaLevelCollection getMarktQuaLevelCollection() throws BOSException
    {
        try {
            return getController().getMarktQuaLevelCollection(getContext());
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
    public MarktQuaLevelCollection getMarktQuaLevelCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarktQuaLevelCollection(getContext(), view);
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
    public MarktQuaLevelCollection getMarktQuaLevelCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarktQuaLevelCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}