package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.costindexdb.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class BuildPriceIndex extends CoreBillBase implements IBuildPriceIndex
{
    public BuildPriceIndex()
    {
        super();
        registerInterface(IBuildPriceIndex.class, this);
    }
    public BuildPriceIndex(Context ctx)
    {
        super(ctx);
        registerInterface(IBuildPriceIndex.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4FFB9A31");
    }
    private BuildPriceIndexController getController() throws BOSException
    {
        return (BuildPriceIndexController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BuildPriceIndexCollection getBuildPriceIndexCollection() throws BOSException
    {
        try {
            return getController().getBuildPriceIndexCollection(getContext());
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
    public BuildPriceIndexCollection getBuildPriceIndexCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBuildPriceIndexCollection(getContext(), view);
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
    public BuildPriceIndexCollection getBuildPriceIndexCollection(String oql) throws BOSException
    {
        try {
            return getController().getBuildPriceIndexCollection(getContext(), oql);
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
    public BuildPriceIndexInfo getBuildPriceIndexInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildPriceIndexInfo(getContext(), pk);
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
    public BuildPriceIndexInfo getBuildPriceIndexInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildPriceIndexInfo(getContext(), pk, selector);
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
    public BuildPriceIndexInfo getBuildPriceIndexInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildPriceIndexInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}