package com.kingdee.eas.bpm;

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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.bpm.app.*;
import com.kingdee.eas.framework.IDataBase;

public class BPMServerConfig extends DataBase implements IBPMServerConfig
{
    public BPMServerConfig()
    {
        super();
        registerInterface(IBPMServerConfig.class, this);
    }
    public BPMServerConfig(Context ctx)
    {
        super(ctx);
        registerInterface(IBPMServerConfig.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F3297283");
    }
    private BPMServerConfigController getController() throws BOSException
    {
        return (BPMServerConfigController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public BPMServerConfigInfo getBPMServerConfigInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBPMServerConfigInfo(getContext(), pk);
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
    public BPMServerConfigInfo getBPMServerConfigInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBPMServerConfigInfo(getContext(), pk, selector);
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
    public BPMServerConfigInfo getBPMServerConfigInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBPMServerConfigInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BPMServerConfigCollection getBPMServerConfigCollection() throws BOSException
    {
        try {
            return getController().getBPMServerConfigCollection(getContext());
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
    public BPMServerConfigCollection getBPMServerConfigCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBPMServerConfigCollection(getContext(), view);
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
    public BPMServerConfigCollection getBPMServerConfigCollection(String oql) throws BOSException
    {
        try {
            return getController().getBPMServerConfigCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}