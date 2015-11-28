package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.costindexdb.database.app.*;
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
import com.kingdee.eas.framework.ICoreBillBase;

public class BuildSplitBill extends CoreBillBase implements IBuildSplitBill
{
    public BuildSplitBill()
    {
        super();
        registerInterface(IBuildSplitBill.class, this);
    }
    public BuildSplitBill(Context ctx)
    {
        super(ctx);
        registerInterface(IBuildSplitBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C672055A");
    }
    private BuildSplitBillController getController() throws BOSException
    {
        return (BuildSplitBillController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BuildSplitBillCollection getBuildSplitBillCollection() throws BOSException
    {
        try {
            return getController().getBuildSplitBillCollection(getContext());
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
    public BuildSplitBillCollection getBuildSplitBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBuildSplitBillCollection(getContext(), view);
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
    public BuildSplitBillCollection getBuildSplitBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getBuildSplitBillCollection(getContext(), oql);
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
    public BuildSplitBillInfo getBuildSplitBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildSplitBillInfo(getContext(), pk);
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
    public BuildSplitBillInfo getBuildSplitBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildSplitBillInfo(getContext(), pk, selector);
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
    public BuildSplitBillInfo getBuildSplitBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBuildSplitBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}