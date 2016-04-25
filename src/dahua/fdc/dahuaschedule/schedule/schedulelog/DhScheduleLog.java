package com.kingdee.eas.fdc.dahuaschedule.schedule.schedulelog;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.dahuaschedule.schedule.schedulelog.app.*;
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

public class DhScheduleLog extends DataBase implements IDhScheduleLog
{
    public DhScheduleLog()
    {
        super();
        registerInterface(IDhScheduleLog.class, this);
    }
    public DhScheduleLog(Context ctx)
    {
        super(ctx);
        registerInterface(IDhScheduleLog.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A1CC5F92");
    }
    private DhScheduleLogController getController() throws BOSException
    {
        return (DhScheduleLogController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public DhScheduleLogInfo getDhScheduleLogInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDhScheduleLogInfo(getContext(), pk);
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
    public DhScheduleLogInfo getDhScheduleLogInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDhScheduleLogInfo(getContext(), pk, selector);
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
    public DhScheduleLogInfo getDhScheduleLogInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDhScheduleLogInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public DhScheduleLogCollection getDhScheduleLogCollection() throws BOSException
    {
        try {
            return getController().getDhScheduleLogCollection(getContext());
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
    public DhScheduleLogCollection getDhScheduleLogCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDhScheduleLogCollection(getContext(), view);
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
    public DhScheduleLogCollection getDhScheduleLogCollection(String oql) throws BOSException
    {
        try {
            return getController().getDhScheduleLogCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}