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

public class TargetInfo extends CoreBase implements ITargetInfo
{
    public TargetInfo()
    {
        super();
        registerInterface(ITargetInfo.class, this);
    }
    public TargetInfo(Context ctx)
    {
        super(ctx);
        registerInterface(ITargetInfo.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4825AA51");
    }
    private TargetInfoController getController() throws BOSException
    {
        return (TargetInfoController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TargetInfoInfo getTargetInfoInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTargetInfoInfo(getContext(), pk);
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
    public TargetInfoInfo getTargetInfoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTargetInfoInfo(getContext(), pk, selector);
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
    public TargetInfoInfo getTargetInfoInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTargetInfoInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TargetInfoCollection getTargetInfoCollection() throws BOSException
    {
        try {
            return getController().getTargetInfoCollection(getContext());
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
    public TargetInfoCollection getTargetInfoCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTargetInfoCollection(getContext(), view);
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
    public TargetInfoCollection getTargetInfoCollection(String oql) throws BOSException
    {
        try {
            return getController().getTargetInfoCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *因需要带出上次的开工和开盘数据，故设此方法-User defined method
     *@param prjId 项目Id
     *@return
     */
    public TargetInfoEntryCollection getInitedCollection(String prjId) throws BOSException
    {
        try {
            return getController().getInitedCollection(getContext(), prjId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}