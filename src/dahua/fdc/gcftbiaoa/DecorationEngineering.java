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

public class DecorationEngineering extends CoreBillBase implements IDecorationEngineering
{
    public DecorationEngineering()
    {
        super();
        registerInterface(IDecorationEngineering.class, this);
    }
    public DecorationEngineering(Context ctx)
    {
        super(ctx);
        registerInterface(IDecorationEngineering.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D5DC901C");
    }
    private DecorationEngineeringController getController() throws BOSException
    {
        return (DecorationEngineeringController)getBizController();
    }
    /**
     *ȡ����-System defined method
     *@return
     */
    public DecorationEngineeringCollection getDecorationEngineeringCollection() throws BOSException
    {
        try {
            return getController().getDecorationEngineeringCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param view ȡ����
     *@return
     */
    public DecorationEngineeringCollection getDecorationEngineeringCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDecorationEngineeringCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ����-System defined method
     *@param oql ȡ����
     *@return
     */
    public DecorationEngineeringCollection getDecorationEngineeringCollection(String oql) throws BOSException
    {
        try {
            return getController().getDecorationEngineeringCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public DecorationEngineeringInfo getDecorationEngineeringInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDecorationEngineeringInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public DecorationEngineeringInfo getDecorationEngineeringInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDecorationEngineeringInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public DecorationEngineeringInfo getDecorationEngineeringInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDecorationEngineeringInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *���-User defined method
     *@param model model
     */
    public void aduit(DecorationEngineeringInfo model) throws BOSException, EASBizException
    {
        try {
            getController().aduit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *�����-User defined method
     *@param model model
     */
    public void unAudit(DecorationEngineeringInfo model) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}