package com.kingdee.eas.fdc.costindexdb;

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
import com.kingdee.eas.fdc.costindexdb.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class BuildPriceIndexEntry extends CoreBillEntryBase implements IBuildPriceIndexEntry
{
    public BuildPriceIndexEntry()
    {
        super();
        registerInterface(IBuildPriceIndexEntry.class, this);
    }
    public BuildPriceIndexEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IBuildPriceIndexEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("96D234C1");
    }
    private BuildPriceIndexEntryController getController() throws BOSException
    {
        return (BuildPriceIndexEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public BuildPriceIndexEntryInfo getBuildPriceIndexEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildPriceIndexEntryInfo(getContext(), pk);
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
    public BuildPriceIndexEntryInfo getBuildPriceIndexEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildPriceIndexEntryInfo(getContext(), pk, selector);
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
    public BuildPriceIndexEntryInfo getBuildPriceIndexEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildPriceIndexEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BuildPriceIndexEntryCollection getBuildPriceIndexEntryCollection() throws BOSException
    {
        try {
            return getController().getBuildPriceIndexEntryCollection(getContext());
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
    public BuildPriceIndexEntryCollection getBuildPriceIndexEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBuildPriceIndexEntryCollection(getContext(), view);
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
    public BuildPriceIndexEntryCollection getBuildPriceIndexEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getBuildPriceIndexEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}