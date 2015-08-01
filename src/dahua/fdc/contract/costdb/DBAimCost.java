package com.kingdee.eas.fdc.costdb;

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
import com.kingdee.eas.framework.CoreBillBase;
import com.kingdee.eas.fdc.costdb.app.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.framework.ICoreBillBase;

public class DBAimCost extends CoreBillBase implements IDBAimCost
{
    public DBAimCost()
    {
        super();
        registerInterface(IDBAimCost.class, this);
    }
    public DBAimCost(Context ctx)
    {
        super(ctx);
        registerInterface(IDBAimCost.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("675AE052");
    }
    private DBAimCostController getController() throws BOSException
    {
        return (DBAimCostController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public DBAimCostInfo getDBAimCostInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDBAimCostInfo(getContext(), pk);
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
    public DBAimCostInfo getDBAimCostInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDBAimCostInfo(getContext(), pk, selector);
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
    public DBAimCostCollection getDBAimCostCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDBAimCostCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public DBAimCostCollection getDBAimCostCollection() throws BOSException
    {
        try {
            return getController().getDBAimCostCollection(getContext());
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
    public DBAimCostCollection getDBAimCostCollection(String oql) throws BOSException
    {
        try {
            return getController().getDBAimCostCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批（批量）-User defined method
     *@param billId 单据ID
     */
    public void audit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批（批量）-User defined method
     *@param idList idList
     */
    public void audit(List idList) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批（批量）-User defined method
     *@param billId 单据ID
     */
    public void unaudit(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().unaudit(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批（批量）-User defined method
     *@param idList 单据ID列表
     */
    public void unaudit(List idList) throws BOSException, EASBizException
    {
        try {
            getController().unaudit(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}