package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCSplitBillEntry;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.aimcost.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntry;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ForecastChangeVisSplitEntry extends FDCSplitBillEntry implements IForecastChangeVisSplitEntry
{
    public ForecastChangeVisSplitEntry()
    {
        super();
        registerInterface(IForecastChangeVisSplitEntry.class, this);
    }
    public ForecastChangeVisSplitEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IForecastChangeVisSplitEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BECD9A36");
    }
    private ForecastChangeVisSplitEntryController getController() throws BOSException
    {
        return (ForecastChangeVisSplitEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ForecastChangeVisSplitEntryInfo getForecastChangeVisSplitEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getForecastChangeVisSplitEntryInfo(getContext(), pk);
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
    public ForecastChangeVisSplitEntryInfo getForecastChangeVisSplitEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getForecastChangeVisSplitEntryInfo(getContext(), pk, selector);
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
    public ForecastChangeVisSplitEntryInfo getForecastChangeVisSplitEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getForecastChangeVisSplitEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ForecastChangeVisSplitEntryCollection getForecastChangeVisSplitEntryCollection() throws BOSException
    {
        try {
            return getController().getForecastChangeVisSplitEntryCollection(getContext());
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
    public ForecastChangeVisSplitEntryCollection getForecastChangeVisSplitEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getForecastChangeVisSplitEntryCollection(getContext(), view);
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
    public ForecastChangeVisSplitEntryCollection getForecastChangeVisSplitEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getForecastChangeVisSplitEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}