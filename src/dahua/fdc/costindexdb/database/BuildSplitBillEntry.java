package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.costindexdb.database.app.*;
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

public class BuildSplitBillEntry extends CoreBillEntryBase implements IBuildSplitBillEntry
{
    public BuildSplitBillEntry()
    {
        super();
        registerInterface(IBuildSplitBillEntry.class, this);
    }
    public BuildSplitBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IBuildSplitBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("969D5B38");
    }
    private BuildSplitBillEntryController getController() throws BOSException
    {
        return (BuildSplitBillEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public BuildSplitBillEntryInfo getBuildSplitBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildSplitBillEntryInfo(getContext(), pk);
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
    public BuildSplitBillEntryInfo getBuildSplitBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildSplitBillEntryInfo(getContext(), pk, selector);
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
    public BuildSplitBillEntryInfo getBuildSplitBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildSplitBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BuildSplitBillEntryCollection getBuildSplitBillEntryCollection() throws BOSException
    {
        try {
            return getController().getBuildSplitBillEntryCollection(getContext());
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
    public BuildSplitBillEntryCollection getBuildSplitBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBuildSplitBillEntryCollection(getContext(), view);
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
    public BuildSplitBillEntryCollection getBuildSplitBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getBuildSplitBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}