package com.kingdee.eas.fdc.dahuaschedule.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.dahuaschedule.schedule.app.*;

public class DahuaScheduleEntry extends CoreBillEntryBase implements IDahuaScheduleEntry
{
    public DahuaScheduleEntry()
    {
        super();
        registerInterface(IDahuaScheduleEntry.class, this);
    }
    public DahuaScheduleEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IDahuaScheduleEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("CC8749DA");
    }
    private DahuaScheduleEntryController getController() throws BOSException
    {
        return (DahuaScheduleEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public DahuaScheduleEntryInfo getDahuaScheduleEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDahuaScheduleEntryInfo(getContext(), pk);
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
    public DahuaScheduleEntryInfo getDahuaScheduleEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDahuaScheduleEntryInfo(getContext(), pk, selector);
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
    public DahuaScheduleEntryInfo getDahuaScheduleEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDahuaScheduleEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public DahuaScheduleEntryCollection getDahuaScheduleEntryCollection() throws BOSException
    {
        try {
            return getController().getDahuaScheduleEntryCollection(getContext());
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
    public DahuaScheduleEntryCollection getDahuaScheduleEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDahuaScheduleEntryCollection(getContext(), view);
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
    public DahuaScheduleEntryCollection getDahuaScheduleEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getDahuaScheduleEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}