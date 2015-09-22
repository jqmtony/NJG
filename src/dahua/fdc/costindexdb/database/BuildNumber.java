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

public class BuildNumber extends DataBase implements IBuildNumber
{
    public BuildNumber()
    {
        super();
        registerInterface(IBuildNumber.class, this);
    }
    public BuildNumber(Context ctx)
    {
        super(ctx);
        registerInterface(IBuildNumber.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("38776AD0");
    }
    private BuildNumberController getController() throws BOSException
    {
        return (BuildNumberController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public BuildNumberInfo getBuildNumberInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildNumberInfo(getContext(), pk);
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
    public BuildNumberInfo getBuildNumberInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildNumberInfo(getContext(), pk, selector);
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
    public BuildNumberInfo getBuildNumberInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildNumberInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BuildNumberCollection getBuildNumberCollection() throws BOSException
    {
        try {
            return getController().getBuildNumberCollection(getContext());
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
    public BuildNumberCollection getBuildNumberCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBuildNumberCollection(getContext(), view);
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
    public BuildNumberCollection getBuildNumberCollection(String oql) throws BOSException
    {
        try {
            return getController().getBuildNumberCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}