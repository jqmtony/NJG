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

public class IndexMemo extends CoreBase implements IIndexMemo
{
    public IndexMemo()
    {
        super();
        registerInterface(IIndexMemo.class, this);
    }
    public IndexMemo(Context ctx)
    {
        super(ctx);
        registerInterface(IIndexMemo.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("65E4927A");
    }
    private IndexMemoController getController() throws BOSException
    {
        return (IndexMemoController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public IndexMemoInfo getIndexMemoInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getIndexMemoInfo(getContext(), pk);
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
    public IndexMemoInfo getIndexMemoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getIndexMemoInfo(getContext(), pk, selector);
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
    public IndexMemoInfo getIndexMemoInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getIndexMemoInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public IndexMemoCollection getIndexMemoCollection() throws BOSException
    {
        try {
            return getController().getIndexMemoCollection(getContext());
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
    public IndexMemoCollection getIndexMemoCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getIndexMemoCollection(getContext(), view);
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
    public IndexMemoCollection getIndexMemoCollection(String oql) throws BOSException
    {
        try {
            return getController().getIndexMemoCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}