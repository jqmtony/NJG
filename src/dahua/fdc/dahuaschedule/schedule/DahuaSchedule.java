package com.kingdee.eas.fdc.dahuaschedule.schedule;

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
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.dahuaschedule.schedule.app.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class DahuaSchedule extends CoreBillBase implements IDahuaSchedule
{
    public DahuaSchedule()
    {
        super();
        registerInterface(IDahuaSchedule.class, this);
    }
    public DahuaSchedule(Context ctx)
    {
        super(ctx);
        registerInterface(IDahuaSchedule.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BD91F978");
    }
    private DahuaScheduleController getController() throws BOSException
    {
        return (DahuaScheduleController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public DahuaScheduleCollection getDahuaScheduleCollection() throws BOSException
    {
        try {
            return getController().getDahuaScheduleCollection(getContext());
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
    public DahuaScheduleCollection getDahuaScheduleCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDahuaScheduleCollection(getContext(), view);
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
    public DahuaScheduleCollection getDahuaScheduleCollection(String oql) throws BOSException
    {
        try {
            return getController().getDahuaScheduleCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public DahuaScheduleInfo getDahuaScheduleInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDahuaScheduleInfo(getContext(), pk);
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
    public DahuaScheduleInfo getDahuaScheduleInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDahuaScheduleInfo(getContext(), pk, selector);
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
    public DahuaScheduleInfo getDahuaScheduleInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDahuaScheduleInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}