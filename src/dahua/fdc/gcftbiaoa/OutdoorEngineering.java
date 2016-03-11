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

public class OutdoorEngineering extends CoreBillBase implements IOutdoorEngineering
{
    public OutdoorEngineering()
    {
        super();
        registerInterface(IOutdoorEngineering.class, this);
    }
    public OutdoorEngineering(Context ctx)
    {
        super(ctx);
        registerInterface(IOutdoorEngineering.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F44B7C3E");
    }
    private OutdoorEngineeringController getController() throws BOSException
    {
        return (OutdoorEngineeringController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public OutdoorEngineeringCollection getOutdoorEngineeringCollection() throws BOSException
    {
        try {
            return getController().getOutdoorEngineeringCollection(getContext());
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
    public OutdoorEngineeringCollection getOutdoorEngineeringCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOutdoorEngineeringCollection(getContext(), view);
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
    public OutdoorEngineeringCollection getOutdoorEngineeringCollection(String oql) throws BOSException
    {
        try {
            return getController().getOutdoorEngineeringCollection(getContext(), oql);
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
    public OutdoorEngineeringInfo getOutdoorEngineeringInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOutdoorEngineeringInfo(getContext(), pk);
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
    public OutdoorEngineeringInfo getOutdoorEngineeringInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOutdoorEngineeringInfo(getContext(), pk, selector);
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
    public OutdoorEngineeringInfo getOutdoorEngineeringInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOutdoorEngineeringInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审核-User defined method
     *@param model model
     */
    public void aduit(OutdoorEngineeringInfo model) throws BOSException, EASBizException
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
    public void unAudit(OutdoorEngineeringInfo model) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}