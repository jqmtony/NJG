package com.kingdee.eas.fdc.aimcost.costkf;

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
import com.kingdee.eas.fdc.aimcost.costkf.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class CqgsBase extends DataBase implements ICqgsBase
{
    public CqgsBase()
    {
        super();
        registerInterface(ICqgsBase.class, this);
    }
    public CqgsBase(Context ctx)
    {
        super(ctx);
        registerInterface(ICqgsBase.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C4C9BB14");
    }
    private CqgsBaseController getController() throws BOSException
    {
        return (CqgsBaseController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public CqgsBaseInfo getCqgsBaseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCqgsBaseInfo(getContext(), pk);
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
    public CqgsBaseInfo getCqgsBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCqgsBaseInfo(getContext(), pk, selector);
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
    public CqgsBaseInfo getCqgsBaseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCqgsBaseInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public CqgsBaseCollection getCqgsBaseCollection() throws BOSException
    {
        try {
            return getController().getCqgsBaseCollection(getContext());
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
    public CqgsBaseCollection getCqgsBaseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCqgsBaseCollection(getContext(), view);
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
    public CqgsBaseCollection getCqgsBaseCollection(String oql) throws BOSException
    {
        try {
            return getController().getCqgsBaseCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}