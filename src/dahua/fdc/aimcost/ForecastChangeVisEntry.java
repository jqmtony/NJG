package com.kingdee.eas.fdc.aimcost;

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
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ForecastChangeVisEntry extends CoreBillEntryBase implements IForecastChangeVisEntry
{
    public ForecastChangeVisEntry()
    {
        super();
        registerInterface(IForecastChangeVisEntry.class, this);
    }
    public ForecastChangeVisEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IForecastChangeVisEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("42F3CCF4");
    }
    private ForecastChangeVisEntryController getController() throws BOSException
    {
        return (ForecastChangeVisEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ForecastChangeVisEntryInfo getForecastChangeVisEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getForecastChangeVisEntryInfo(getContext(), pk);
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
    public ForecastChangeVisEntryInfo getForecastChangeVisEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getForecastChangeVisEntryInfo(getContext(), pk, selector);
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
    public ForecastChangeVisEntryInfo getForecastChangeVisEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getForecastChangeVisEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ForecastChangeVisEntryCollection getForecastChangeVisEntryCollection() throws BOSException
    {
        try {
            return getController().getForecastChangeVisEntryCollection(getContext());
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
    public ForecastChangeVisEntryCollection getForecastChangeVisEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getForecastChangeVisEntryCollection(getContext(), view);
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
    public ForecastChangeVisEntryCollection getForecastChangeVisEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getForecastChangeVisEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}