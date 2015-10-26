package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.costindexdb.database.app.*;
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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class IndexType extends DataBase implements IIndexType
{
    public IndexType()
    {
        super();
        registerInterface(IIndexType.class, this);
    }
    public IndexType(Context ctx)
    {
        super(ctx);
        registerInterface(IIndexType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1D50E4E5");
    }
    private IndexTypeController getController() throws BOSException
    {
        return (IndexTypeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public IndexTypeInfo getIndexTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getIndexTypeInfo(getContext(), pk);
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
    public IndexTypeInfo getIndexTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getIndexTypeInfo(getContext(), pk, selector);
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
    public IndexTypeInfo getIndexTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getIndexTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public IndexTypeCollection getIndexTypeCollection() throws BOSException
    {
        try {
            return getController().getIndexTypeCollection(getContext());
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
    public IndexTypeCollection getIndexTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getIndexTypeCollection(getContext(), view);
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
    public IndexTypeCollection getIndexTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getIndexTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}