package com.kingdee.eas.fdc.gcftbiaoa;

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
import com.kingdee.eas.fdc.gcftbiaoa.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBillBase;

public class IndoorEngineering extends CoreBillBase implements IIndoorEngineering
{
    public IndoorEngineering()
    {
        super();
        registerInterface(IIndoorEngineering.class, this);
    }
    public IndoorEngineering(Context ctx)
    {
        super(ctx);
        registerInterface(IIndoorEngineering.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("38843319");
    }
    private IndoorEngineeringController getController() throws BOSException
    {
        return (IndoorEngineeringController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public IndoorEngineeringCollection getIndoorEngineeringCollection() throws BOSException
    {
        try {
            return getController().getIndoorEngineeringCollection(getContext());
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
    public IndoorEngineeringCollection getIndoorEngineeringCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getIndoorEngineeringCollection(getContext(), view);
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
    public IndoorEngineeringCollection getIndoorEngineeringCollection(String oql) throws BOSException
    {
        try {
            return getController().getIndoorEngineeringCollection(getContext(), oql);
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
    public IndoorEngineeringInfo getIndoorEngineeringInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getIndoorEngineeringInfo(getContext(), pk);
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
    public IndoorEngineeringInfo getIndoorEngineeringInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getIndoorEngineeringInfo(getContext(), pk, selector);
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
    public IndoorEngineeringInfo getIndoorEngineeringInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getIndoorEngineeringInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审核-User defined method
     *@param model model
     */
    public void aduit(IndoorEngineeringInfo model) throws BOSException, EASBizException
    {
        try {
            getController().aduit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审核-User defined method
     *@param model model
     */
    public void unAudit(IndoorEngineeringInfo model) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}